package QLTonKho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ThemTonKhoDialog extends JDialog {
    private JTextField txtMaTonKho, txtMaSanPham, txtSoLuongTon;
    private JButton btnSave, btnCancel;
    private Connection connection;

    public ThemTonKhoDialog(Frame parent, Connection connection) {
        super(parent, "Thêm Tồn Kho", true);
        this.connection = connection;

        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Mã Tồn Kho:"));
        txtMaTonKho = new JTextField();
        add(txtMaTonKho);

        add(new JLabel("Mã Sản Phẩm:"));
        txtMaSanPham = new JTextField();
        add(txtMaSanPham);

        add(new JLabel("Số Lượng Tồn:"));
        txtSoLuongTon = new JTextField();
        add(txtSoLuongTon);

        // Nút lưu
        btnSave = new JButton("Lưu");
        btnSave.addActionListener(e -> themTonKho());
        add(btnSave);

        // Nút hủy
        btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> this.dispose());
        add(btnCancel);

        setSize(300, 200);
        setLocationRelativeTo(parent);
    }

    private void themTonKho() {
        String maTonKho = txtMaTonKho.getText();
        String maSanPham = txtMaSanPham.getText();
        int soLuongTon;

        try {
            soLuongTon = Integer.parseInt(txtSoLuongTon.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn phải là số nguyên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO TonKho (MaTonKho, MaSanPham, SoLuongTon, ThoiGianCapNhat) VALUES (?, ?, ?, GETDATE())";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, maTonKho);
            statement.setString(2, maSanPham);
            statement.setInt(3, soLuongTon);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Thêm tồn kho thành công.");
            this.dispose(); // Đóng cửa sổ nhập liệu
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm tồn kho: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
