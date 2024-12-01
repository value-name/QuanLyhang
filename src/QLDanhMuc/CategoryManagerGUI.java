package QLDanhMuc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CategoryManagerGUI extends JFrame {
    private JTextField categoryIdField;
    private JTextField categoryNameField;
    private JButton addButton, updateButton, deleteButton, searchButton, backButton;
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private JTextField descriptionField;

    public CategoryManagerGUI() {
        setTitle("Quản lý danh mục sản phẩm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel categoryIdLabel = new JLabel("Mã danh mục:");
        JLabel categoryNameLabel = new JLabel("Tên danh mục:");
        JLabel descriptionLabel = new JLabel("Mô tả:");
        // Trong phương thức initUI():

        categoryIdField = new JTextField();
        categoryNameField = new JTextField();
        descriptionField = new JTextField();
        
        inputPanel.add(categoryIdLabel);
        inputPanel.add(categoryIdField);
        inputPanel.add(categoryNameLabel);
        inputPanel.add(categoryNameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);

        // Nút chức năng
        addButton = new JButton("Thêm");
        updateButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        searchButton = new JButton("Tìm kiếm");
        backButton = new JButton("Quay lại");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);
        
        // Bảng hiển thị danh mục sản phẩm
        String[] columnNames = {"Mã danh mục", "Tên danh mục", "Mô tả"};
        tableModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(categoryTable);

        // Thêm các thành phần vào frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Hành động cho các nút
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addCategory();
//            }
//        });
           addButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Tạo và hiển thị dialog thêm danh mục
        AddCategoryDialog addDialog = new AddCategoryDialog(CategoryManagerGUI.this);
        addDialog.setVisible(true);
    }
});

//        updateButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updateCategory();
//            }
//        });
            updateButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                   int selectedRow = categoryTable.getSelectedRow();
                         if (selectedRow == -1) {
                         JOptionPane.showMessageDialog(CategoryManagerGUI.this, "Vui lòng chọn một danh mục để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                           return;
               }

        // Lấy dữ liệu từ hàng được chọn
        String categoryId = tableModel.getValueAt(selectedRow, 0).toString();
        String categoryName = tableModel.getValueAt(selectedRow, 1).toString();
        String description = tableModel.getValueAt(selectedRow, 2).toString();

        // Tạo và hiển thị dialog chỉnh sửa danh mục
        EditCategoryDialog editDialog = new EditCategoryDialog(CategoryManagerGUI.this, categoryId, categoryName, description);
        editDialog.setVisible(true);

        // Sau khi đóng EditCategoryDialog, cập nhật lại danh sách
        loadCategories();
    }
});


//        deleteButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                deleteCategory();
//            }
//        });
            deleteButton.addActionListener(new ActionListener() {
                @Override
                 public void actionPerformed(ActionEvent e) {
                     deleteCategory(); // Gọi phương thức deleteCategory với xác nhận xóa
                 }
            });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCategory();
            }
        });
        backButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
              // Nạp lại toàn bộ danh mục
              loadCategories();
              // Xóa trường nhập liệu sau tìm kiếm (nếu cần)
               categoryIdField.setText("");      // Xóa ô "Mã danh mục"
               categoryNameField.setText("");   // Xóa ô "Tên danh mục"
               descriptionField.setText("");    // Xóa ô "Mô tả"
    }
});

        loadCategories();
    }

    public void loadCategories() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLY;username = sa; password=123456;encrypt=false");
            stmt = conn.createStatement();
            String query = "SELECT * FROM DanhMucSanPham";
            rs = stmt.executeQuery(query);

            tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            while (rs.next()) {
                Object[] row = {rs.getString("MaDanhMuc"), rs.getString("TenDanhMuc"), rs.getString("MoTa")};
                tableModel.addRow(row);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addCategory() {
        String categoryName = categoryNameField.getText();
        String categoryId = categoryIdField.getText();
        String description = descriptionField.getText();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLY;username = sa; password=123456;encrypt=false");            
            String query = "INSERT INTO DanhMucSanPham (MaDanhMuc, TenDanhMuc, MoTa) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, categoryId);
            stmt.setString(2, categoryName);
            stmt.setString(3, description);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");
            loadCategories();

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
    
    private void updateCategory() {
        String categoryId = categoryIdField.getText();
        String categoryName = categoryNameField.getText();
        String description = descriptionField.getText();
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLY;username = sa; password=123456;encrypt=false");
            String query = "UPDATE DanhMucSanPham SET TenDanhMuc = ?, MoTa = ? WHERE MaDanhMuc = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, categoryId);
            stmt.setString(2, categoryName);
            stmt.setString(3, description);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật danh mục thành công!");
            loadCategories();

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

//    private void deleteCategory() {
//        String categoryId = categoryIdField.getText();
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        try {
//            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLY;username = sa; password=123456;encrypt=false");
//            String query = "DELETE FROM DanhMucSanPham WHERE MaDanhMuc = ?";
//            stmt = conn.prepareStatement(query);
//            stmt.setString(1, categoryId);
//
//            stmt.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Xóa danh mục thành công!");
//            loadCategories();
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Lỗi xóa danh mục: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            try {
//                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
        private void deleteCategory() {
    int selectedRow = categoryTable.getSelectedRow();

    // Kiểm tra xem có dòng nào được chọn hay không
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy mã danh mục từ dòng được chọn trong bảng
    String categoryId = tableModel.getValueAt(selectedRow, 0).toString();

    // Hiển thị hộp thoại xác nhận xóa
    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa danh mục này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLY;username=sa;password=123456;encrypt=false");
            String query = "DELETE FROM DanhMucSanPham WHERE MaDanhMuc = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, categoryId);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa danh mục thành công!");
            loadCategories();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa danh mục: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    private void searchCategory() {
    String keyword = categoryNameField.getText().trim();
    if (keyword.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa để tìm kiếm.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QUANLY;username=sa;password=123456;encrypt=false");
        String query = "SELECT * FROM DanhMucSanPham WHERE TenDanhMuc LIKE ? OR MoTa LIKE ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" + keyword + "%");
        stmt.setString(2, "%" + keyword + "%");

        rs = stmt.executeQuery();

        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        boolean found = false;
        while (rs.next()) {
            Object[] row = {rs.getString("MaDanhMuc"), rs.getString("TenDanhMuc"), rs.getString("MoTa")};
            tableModel.addRow(row);
            found = true;
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy danh mục phù hợp với từ khóa: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm danh mục: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CategoryManagerGUI().setVisible(true);
            }
        });
    }
}
