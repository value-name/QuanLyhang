package QLSanPhamHetHan;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SuaSanPhamHetHan extends JDialog {
    private JTextField txtSoLuongHetHan, txtThoiGianCapNhat;
    private JButton btnSua, btnHuy;
    private boolean isUpdated = false;

    public SuaSanPhamHetHan(JFrame parent, Connection connection, String maSanPhamHetHan, int soLuongHetHan, Timestamp thoiGianCapNhat) {
        super(parent, "Sửa Thông Tin Sản Phẩm Hết Hạn", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 10, 10));

        // Hiển thị số lượng hết hạn và thời gian cập nhật
        txtSoLuongHetHan = new JTextField(String.valueOf(soLuongHetHan));
        txtThoiGianCapNhat = new JTextField(thoiGianCapNhat.toString());
        txtThoiGianCapNhat.setEditable(false); // Chỉ xem, không sửa

        // Labels và inputs
        add(new JLabel("Số Lượng Hết Hạn:"));
        add(txtSoLuongHetHan);
        add(new JLabel("Thời Gian Cập Nhật:"));
        add(txtThoiGianCapNhat);

        // Nút chức năng
        btnSua = new JButton("Sửa");
        btnHuy = new JButton("Hủy");
        add(btnSua);
        add(btnHuy);

        // Sự kiện nút "Sửa"
        btnSua.addActionListener(e -> {
            try {
                int newSoLuongHetHan = Integer.parseInt(txtSoLuongHetHan.getText());

                // Xác nhận sửa
                int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận sửa đổi?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Bắt đầu transaction
                    connection.setAutoCommit(false);

                    // Truy vấn số lượng hết hạn cũ từ bảng SanPhamHetHan
                    String selectSql = "SELECT SoLuongHetHan, MaSanPham FROM SanPhamHetHan WHERE MaSanPhamHetHan = ?";
                    try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                        selectStmt.setString(1, maSanPhamHetHan);
                        ResultSet resultSet = selectStmt.executeQuery();
                        if (resultSet.next()) {
                            int oldSoLuongHetHan = resultSet.getInt("SoLuongHetHan");
                            String maSanPham = resultSet.getString("MaSanPham");

                            // Tính toán thay đổi số lượng
                            int delta = oldSoLuongHetHan - newSoLuongHetHan;

                            // Kiểm tra số lượng tồn
                            String checkSoLuongSql = "SELECT SoLuongTon FROM SanPham WHERE MaSanPham = ?";
                            try (PreparedStatement checkStmt = connection.prepareStatement(checkSoLuongSql)) {
                                checkStmt.setString(1, maSanPham);
                                ResultSet checkResultSet = checkStmt.executeQuery();
                                if (checkResultSet.next()) {
                                    int soLuongTon = checkResultSet.getInt("SoLuongTon");
                                    if (soLuongTon + delta < 0) {
                                        connection.rollback();
                                        JOptionPane.showMessageDialog(this, "Không thể sửa: Số lượng tồn không đủ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }

                            // Cập nhật số lượng hết hạn trong bảng SanPhamHetHan
                            String updateSanPhamHetHanSql = "UPDATE SanPhamHetHan SET SoLuongHetHan = ?, ThoiGianCapNhat = GETDATE() WHERE MaSanPhamHetHan = ?";
                            try (PreparedStatement updateStmt = connection.prepareStatement(updateSanPhamHetHanSql)) {
                                updateStmt.setInt(1, newSoLuongHetHan);
                                updateStmt.setString(2, maSanPhamHetHan);
                                int rowsUpdated = updateStmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    // Cập nhật số lượng sản phẩm trong bảng SanPham
                                    String updateSanPhamSql = "UPDATE SanPham SET SoLuongTon = SoLuongTon + ? WHERE MaSanPham = ?";
                                    try (PreparedStatement updateSanPhamStmt = connection.prepareStatement(updateSanPhamSql)) {
                                        updateSanPhamStmt.setInt(1, delta);
                                        updateSanPhamStmt.setString(2, maSanPham);
                                        updateSanPhamStmt.executeUpdate();
                                    }

                                    // Commit transaction
                                    connection.commit();
                                    JOptionPane.showMessageDialog(this, "Sửa thông tin sản phẩm hết hạn thành công!");
                                    isUpdated = true;
                                    dispose();
                                } else {
                                    connection.rollback();
                                    JOptionPane.showMessageDialog(this, "Không thể sửa thông tin sản phẩm hết hạn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            connection.rollback();
                            JOptionPane.showMessageDialog(this, "Không tìm thấy mã sản phẩm hết hạn cần sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        connection.rollback();
                        JOptionPane.showMessageDialog(this, "Lỗi khi sửa sản phẩm hết hạn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        connection.setAutoCommit(true); // Đặt lại chế độ tự động commit
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng hết hạn phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa sản phẩm hết hạn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sự kiện nút "Hủy"
        btnHuy.addActionListener(e -> dispose());
    }

    public boolean isUpdated() {
        return isUpdated;
    }
}
