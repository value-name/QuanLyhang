package QLTonKho;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.Timestamp;

class SuaTonKhoDialog extends JDialog {
    private JTextField txtSoLuongTon, txtThoiGianCapNhat;
    private JButton btnSua, btnHuy;
    private boolean isUpdated = false;

    public SuaTonKhoDialog(JFrame parent, Connection connection, String maTonKho, int soLuongTon, Timestamp thoiGianCapNhat) {
        super(parent, "Sửa Thông Tin Tồn Kho", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 10, 10));

        // Hiển thị số lượng tồn và ngày cập nhật
        txtSoLuongTon = new JTextField(String.valueOf(soLuongTon));
        txtThoiGianCapNhat = new JTextField(thoiGianCapNhat.toString());
        txtThoiGianCapNhat.setEditable(false); // Chỉ xem, không sửa

        // Labels và inputs
        add(new JLabel("Số Lượng Tồn:"));
        add(txtSoLuongTon);
        add(new JLabel("Ngày Cập Nhật:"));
        add(txtThoiGianCapNhat);

        // Nút chức năng
        btnSua = new JButton("Sửa");
        btnHuy = new JButton("Hủy");
        add(btnSua);
        add(btnHuy);

        // Sự kiện nút "Sửa"
        btnSua.addActionListener(e -> {
            try {
                int newSoLuongTon = Integer.parseInt(txtSoLuongTon.getText());

                // Xác nhận sửa
                int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận sửa đổi?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String sql = "UPDATE TonKho SET SoLuongTon = ?, ThoiGianCapNhat = GETDATE() WHERE MaTonKho = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, newSoLuongTon);
                        statement.setString(2, maTonKho);

                        int rowsUpdated = statement.executeUpdate();
                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(this, "Sửa thông tin tồn kho thành công!");
                            isUpdated = true;
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy mã tồn kho cần sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa tồn kho: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sự kiện nút "Hủy"
        btnHuy.addActionListener(e -> dispose());
    }

    public boolean isUpdated() {
        return isUpdated;
    }
}


