package QLSanPhamHetHan;

import Home.TrangChu;
import QLSanPhamHetHan.ThemSanPhamHetHan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


public class QuanLySanPhamHetHan extends JFrame {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QUANLY;encrypt=true;trustServerCertificate=true;";

    private static final String DB_USER = "sa"; // Thay bằng user của bạn
    private static final String DB_PASSWORD = "123456"; // Thay bằng password của bạn

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaSanPhamHetHan, txtMaSanPham, txtSoLuongHetHan;

    private Connection connection;
    
    private String loaiTaiKhoan;

    public QuanLySanPhamHetHan(String loaiTaiKhoan) {
         this.loaiTaiKhoan = loaiTaiKhoan;
        try {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    // Thiết lập giao diện
    setTitle("Quản Lý Sản Phẩm Hết Hạn");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Bảng hiển thị tồn kho
    tableModel = new DefaultTableModel(new String[]{"Mã Sản Phẩm Hết Hạn", "Mã Sản Phẩm", "Số Lượng Hết Hạn", "Thời Gian Cập Nhật"}, 0);
    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    // Panel nhập liệu
    JPanel panelInput = new JPanel(new GridLayout(4, 2, 10, 10));
    panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin Sản Phảm Hết Hạn"));

    txtMaSanPhamHetHan = new JTextField();
    txtMaSanPham = new JTextField();
    txtSoLuongHetHan = new JTextField();

    panelInput.add(new JLabel("Mã Sản Phẩm Hết Hạn:"));
    panelInput.add(txtMaSanPhamHetHan);
    panelInput.add(new JLabel("Mã Sản Phẩm:"));
    panelInput.add(txtMaSanPham);
    panelInput.add(new JLabel("Số Lượng Hết Hạn:"));
    panelInput.add(txtSoLuongHetHan);

    // Panel tìm kiếm
    JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    JTextField txtSearch = new JTextField(20);
    JButton btnSearch = new JButton("Tìm kiếm");

    panelSearch.add(new JLabel("Tìm kiếm:"));
    panelSearch.add(txtSearch);
    panelSearch.add(btnSearch);

    // Panel nút chức năng
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JButton btnAdd = new JButton("Thêm");
    JButton btnUpdate = new JButton("Sửa");
    JButton btnDelete = new JButton("Xóa");
    JButton btnRefresh = new JButton("Làm mới");
    JButton btnBack = new JButton("Quay lại");

    panelButtons.add(btnAdd);
    panelButtons.add(btnUpdate);
    panelButtons.add(btnDelete);
    panelButtons.add(btnRefresh);
    panelButtons.add(btnBack);

    // Thêm sự kiện cho nút
    btnAdd.addActionListener(e -> themSanPhamHetHan());
    btnUpdate.addActionListener(e -> suaSanPhamHetHan());
    btnDelete.addActionListener(e -> xoaSanPhamHetHan());
    btnRefresh.addActionListener(e -> hienThiSanPhamHetHan());
    btnSearch.addActionListener(e -> timKiemSanPhamHetHan(txtSearch.getText()));
    btnBack.addActionListener(e -> quayLai());


    // Layout chính
    setLayout(new BorderLayout(10, 10));
    add(scrollPane, BorderLayout.CENTER);
    add(panelInput, BorderLayout.NORTH);
    add(panelButtons, BorderLayout.SOUTH);
    add(panelSearch, BorderLayout.NORTH);

    // Hiển thị dữ liệu ban đầu
    hienThiSanPhamHetHan();
    }

    // Hiển thị danh sách tồn kho
    private void hienThiSanPhamHetHan() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM SanPhamHetHan";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getString("MaSanPhamHetHan"),
                        resultSet.getString("MaSanPham"),
                        resultSet.getInt("SoLuongHetHan"),
                        resultSet.getTimestamp("ThoiGianCapNhat")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi hiển thị tồn kho: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Thêm bản ghi tồn kho
    private void themSanPhamHetHan() {
        // Mở cửa sổ nhập liệu khi bấm nút "Thêm"
    ThemSanPhamHetHan dialog = new ThemSanPhamHetHan(this, connection);
dialog.setVisible(true);
hienThiSanPhamHetHan(); // Làm mới bảng sau khi dialog đóng

    }
    // Phương thức tìm kiếm
private void timKiemSanPhamHetHan(String keyword) {
    if (keyword.trim().isEmpty()) {
        // Kiểm tra nếu từ khóa tìm kiếm trống
        JOptionPane.showMessageDialog(this, "Không được bỏ trống", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return; // Dừng lại nếu từ khóa tìm kiếm trống
    }

    tableModel.setRowCount(0); // Xóa các dòng cũ trong bảng

    String sql = "SELECT * FROM SanPhamHetHan WHERE MaSanPhamHetHan LIKE ? OR MaSanPham LIKE ?";
    boolean found = false; // Biến kiểm tra có tìm thấy kết quả hay không

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        String searchPattern = "%" + keyword + "%";  // Tạo kiểu tìm kiếm LIKE
        statement.setString(1, searchPattern);
        statement.setString(2, searchPattern);
        
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                tableModel.addRow(new Object[] {
                    resultSet.getString("MaSanPhamHetHan"),
                    resultSet.getString("MaSanPham"),
                    resultSet.getInt("SoLuongHetHan"),
                    resultSet.getTimestamp("ThoiGianCapNhat")
                });
                found = true; // Đánh dấu là đã tìm thấy kết quả
            }

            if (!found) {
                // Nếu không tìm thấy dữ liệu
                JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


    // Sửa thông tin tồn kho
private void suaSanPhamHetHan() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy dữ liệu từ dòng được chọn
    String maSanPhamHetHan = tableModel.getValueAt(selectedRow, 0).toString();
    int soLuongHetHan = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());
    Timestamp thoiGianCapNhat = (Timestamp) tableModel.getValueAt(selectedRow, 3);

    // Mở dialog sửa
    SuaSanPhamHetHan dialog = new SuaSanPhamHetHan(this, connection, maSanPhamHetHan, soLuongHetHan, thoiGianCapNhat);
    dialog.setVisible(true);

    // Làm mới bảng nếu đã sửa
    if (dialog.isUpdated()) {
        hienThiSanPhamHetHan();
    }
}


    // Xóa bản ghi hết hạn
private void xoaSanPhamHetHan() {
    int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn trong bảng
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy Mã Sản Phẩm Hết Hạn từ dòng được chọn
    String maSanPhamHetHan = tableModel.getValueAt(selectedRow, 0).toString();

    // Hiển thị hộp thoại xác nhận
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Bạn có chắc chắn muốn xóa sản phẩm với mã: " + maSanPhamHetHan + "?", 
        "Xác nhận xóa", 
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM SanPhamHetHan WHERE MaSanPhamHetHan = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, maSanPhamHetHan);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm hết hạn thành công.");
                hienThiSanPhamHetHan(); // Cập nhật lại bảng sau khi xóa
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm cần xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void quayLai() {
    // Đóng cửa sổ hiện tại
    this.dispose();

    // Mở giao diện TrangChu
    new TrangChu(loaiTaiKhoan).setVisible(true);
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuanLySanPhamHetHan frame = new QuanLySanPhamHetHan("a");
            frame.setVisible(true);
        });
    }
}
