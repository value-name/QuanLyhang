package QLDanhMuc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCategoryDialog extends JDialog {
    private JTextField categoryIdField;
    private JTextField categoryNameField;
    private JTextField descriptionField;
    private JButton addButton;

    public AddCategoryDialog(JFrame parent) {
        super(parent, "Thêm danh mục mới", true);
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));

        JLabel categoryIdLabel = new JLabel("Mã danh mục:");
        categoryIdField = new JTextField();
        JLabel categoryNameLabel = new JLabel("Tên danh mục:");
        categoryNameField = new JTextField();
        JLabel descriptionLabel = new JLabel("Mô tả:");
        descriptionField = new JTextField();

        addButton = new JButton("Thêm");

        add(categoryIdLabel);
        add(categoryIdField);
        add(categoryNameLabel);
        add(categoryNameField);
        add(descriptionLabel);
        add(descriptionField);
        add(new JLabel()); // Placeholder
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCategory();
            }
        });

        setLocationRelativeTo(parent);
    }

    private void addCategory() {
        String categoryId = categoryIdField.getText();
        String categoryName = categoryNameField.getText();
        String description = descriptionField.getText();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLY;username=sa;password=123456;encrypt=false");
            String query = "INSERT INTO DanhMucSanPham (MaDanhMuc, TenDanhMuc, MoTa) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, categoryId);
            stmt.setString(2, categoryName);
            stmt.setString(3, description);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");
            dispose();

            // Gọi phương thức loadCategories của frame chính để cập nhật bảng
            if (getParent() instanceof CategoryManagerGUI) {
                ((CategoryManagerGUI) getParent()).loadCategories();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm danh mục: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
