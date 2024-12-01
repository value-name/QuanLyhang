package QLDanhMuc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditCategoryDialog extends JDialog {
    private JTextField categoryIdField;
    private JTextField categoryNameField;
    private JTextField descriptionField;
    private JButton confirmButton;
    private String categoryId;  // Lưu trữ mã danh mục để chỉnh sửa

    public EditCategoryDialog(JFrame parent, String categoryId, String categoryName, String description) {
        super(parent, "Chỉnh sửa danh mục", true);
        this.categoryId = categoryId;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 2));

        // Các trường nhập liệu
        add(new JLabel("Mã danh mục:"));
        categoryIdField = new JTextField(categoryId);
        categoryIdField.setEditable(false);  // Mã danh mục là cố định, không chỉnh sửa
        add(categoryIdField);

        add(new JLabel("Tên danh mục:"));
        categoryNameField = new JTextField(categoryName);
        add(categoryNameField);

        add(new JLabel("Mô tả:"));
        descriptionField = new JTextField(description);
        add(descriptionField);

        // Nút xác nhận
        confirmButton = new JButton("Xác nhận sửa");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCategory();
            }
        });
        add(new JLabel());  // Thêm khoảng trống
        add(confirmButton);
    }

    private void updateCategory() {
        String newCategoryName = categoryNameField.getText();
        String newDescription = descriptionField.getText();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLY;username=sa;password=123456;encrypt=false");
            String query = "UPDATE DanhMucSanPham SET TenDanhMuc = ?, MoTa = ? WHERE MaDanhMuc = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newCategoryName);
            stmt.setString(2, newDescription);
            stmt.setString(3, categoryId);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật danh mục thành công!");
            dispose();  // Đóng dialog sau khi cập nhật thành công
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật danh mục: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
