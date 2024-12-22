package QLSanPhamHetHan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ThemSanPhamHetHan extends JDialog {
    private JTextField txtMaSanPhamHetHan, txtMaSanPham, txtSoLuongHetHan;
    private JButton btnAdd, btnCancel;
    private Connection connection;

    public ThemSanPhamHetHan(Frame parent, Connection connection) {
        super(parent, "Thêm Sản Phẩm Hết Hạn", true);
        this.connection = connection;

        // Thiết lập giao diện
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Panel nhập liệu
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 10, 10));
        txtMaSanPhamHetHan = new JTextField();
        txtMaSanPham = new JTextField();
        txtSoLuongHetHan = new JTextField();

        panelInput.add(new JLabel("Mã Sản Phẩm Hết Hạn:"));
        panelInput.add(txtMaSanPhamHetHan);
        panelInput.add(new JLabel("Mã Sản Phẩm:"));
        panelInput.add(txtMaSanPham);
        panelInput.add(new JLabel("Số Lượng Hết Hạn:"));
        panelInput.add(txtSoLuongHetHan);

        // Panel nút bấm
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdd = new JButton("Thêm");
        btnCancel = new JButton("Hủy");
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);

        // Gắn các panel vào dialog
        add(panelInput, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Sự kiện nút
        btnAdd.addActionListener(e -> themSanPhamHetHan());
        btnCancel.addActionListener(e -> dispose());

        // Hiển thị dialog
        setVisible(true);
    }

    private void themSanPhamHetHan() {
        String maSanPhamHetHan = txtMaSanPhamHetHan.getText().trim();
        String maSanPham = txtMaSanPham.getText().trim();
        String soLuongText = txtSoLuongHetHan.getText().trim();

        // Kiểm tra dữ liệu đầu vào
        if (maSanPhamHetHan.isEmpty() || maSanPham.isEmpty() || soLuongText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int soLuongHetHan;
        try {
            soLuongHetHan = Integer.parseInt(soLuongText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng hết hạn phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String insertSql = "INSERT INTO SanPhamHetHan (MaSanPhamHetHan, MaSanPham, SoLuongHetHan, ThoiGianCapNhat) VALUES (?, ?, ?, GETDATE())";
        String updateSql = "UPDATE SanPham SET SoLuongTon = SoLuongTon - ? WHERE MaSanPham = ?";

        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Thêm sản phẩm hết hạn vào bảng SanPhamHetHan
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setString(1, maSanPhamHetHan);
                insertStmt.setString(2, maSanPham);
                insertStmt.setInt(3, soLuongHetHan);

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    // Cập nhật số lượng tồn trong bảng SanPham
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, soLuongHetHan);
                        updateStmt.setString(2, maSanPham);
                        int rowsUpdated = updateStmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            // Commit transaction
                            connection.commit();
                            JOptionPane.showMessageDialog(this, "Thêm sản phẩm hết hạn và cập nhật số lượng tồn thành công.");
                            dispose(); // Đóng dialog
                        } else {
                            // Nếu không cập nhật được số lượng tồn, rollback transaction
                            connection.rollback();
                            JOptionPane.showMessageDialog(this, "Không thể cập nhật số lượng tồn của sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    // Nếu không thêm được sản phẩm hết hạn, rollback transaction
                    connection.rollback();
                    JOptionPane.showMessageDialog(this, "Lỗi thêm sản phẩm hết hạn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                connection.rollback();
                JOptionPane.showMessageDialog(this, "Lỗi thực thi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                connection.setAutoCommit(true); // Đặt lại chế độ tự động commit
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
