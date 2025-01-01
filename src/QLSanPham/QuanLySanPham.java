package QLSanPham;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Admin
 */
import Home.TrangChu;
import java.awt.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLySanPham extends javax.swing.JFrame {
    private DefaultTableModel model;
    private Connection connection;
    private String loaiTaiKhoan;
    

    /**
     * Creates new form TImKiemSanPham_1
     */
     public QuanLySanPham(String loaiTaiKhoan) {
        initComponents();
        model = (DefaultTableModel) jTable2.getModel();
        connectDatabase();
        loadData();
        this.loaiTaiKhoan=loaiTaiKhoan;
        
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });
        
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrangChu(loaiTaiKhoan).setVisible(true);
                dispose(); // Đóng cửa sổ hiện tại
            }
        });
        jButton3.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt) {
        // Mở cửa sổ ThemSanPham1 khi nhấn nút "Thêm"
        new ThemSanPhamFrame().setVisible(true);
    }
 });
jButton5.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Lấy chỉ số dòng được chọn trong bảng
        int selectedRow = jTable2.getSelectedRow();

        if (selectedRow >= 0) {
            // Lấy mã sản phẩm từ cột "Mã sản phẩm" trong dòng được chọn
            String maSanPham = jTable2.getValueAt(selectedRow, 0).toString();

            // Kiểm tra sản phẩm hết hạn
            PreparedStatement checkStmt = null;
            try {
                String checkSql = "SELECT COUNT(*) AS SoLuongHetHan FROM SanPhamHetHan WHERE MaSanPham = ?";
                checkStmt = connection.prepareStatement(checkSql);
                checkStmt.setString(1, maSanPham);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int expiredCount = rs.getInt("SoLuongHetHan");
                        if (expiredCount > 0) {
                            JOptionPane.showMessageDialog(null, 
                                "Mã sản phẩm '" + maSanPham + "' có " + expiredCount + " sản phẩm đã hết hạn trong kho.",
                                "Cảnh báo", 
                                JOptionPane.WARNING_MESSAGE);
                            return; // Ngừng xử lý nếu có sản phẩm hết hạn
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kiểm tra sản phẩm hết hạn: " + ex.getMessage());
                return;
            } finally {
                // Đảm bảo đóng PreparedStatement
                if (checkStmt != null) {
                    try {
                        checkStmt.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            // Kiểm tra sản phẩm có trong bảng ChiTietDonHang
            PreparedStatement orderCheckStmt = null;
            try {
                String orderCheckSql = "SELECT COUNT(*) AS SoLuongDonHang FROM ChiTietDonHang WHERE MaSanPham = ?";
                orderCheckStmt = connection.prepareStatement(orderCheckSql);
                orderCheckStmt.setString(1, maSanPham);

                try (ResultSet rs = orderCheckStmt.executeQuery()) {
                    if (rs.next()) {
                        int orderCount = rs.getInt("SoLuongDonHang");
                        if (orderCount > 0) {
                            JOptionPane.showMessageDialog(null, 
                                "Mã sản phẩm '" + maSanPham + "' đang được sử dụng trong " + orderCount + " đơn hàng. Không thể xóa.",
                                "Cảnh báo", 
                                JOptionPane.WARNING_MESSAGE);
                            return; // Ngừng xử lý nếu sản phẩm liên quan đến đơn hàng
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kiểm tra đơn hàng liên quan: " + ex.getMessage());
                return;
            } finally {
                // Đảm bảo đóng PreparedStatement
                if (orderCheckStmt != null) {
                    try {
                        orderCheckStmt.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            // Xác nhận việc xóa sản phẩm
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                PreparedStatement preparedStatement = null;
                try {
                    // Câu lệnh SQL để xóa sản phẩm
                    String sql = "DELETE FROM SanPham WHERE MaSanPham = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, maSanPham);

                    // Thực thi câu lệnh xóa
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Sản phẩm đã được xóa.");
                        // Cập nhật lại bảng dữ liệu sau khi xóa
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm để xóa.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage());
                } finally {
                    // Đảm bảo đóng PreparedStatement
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm để xóa.");
        }
    }
});

        jButton4.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Lấy dòng đã chọn trong bảng
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy thông tin sản phẩm đã chọn
            String tenSanPham = (String) model.getValueAt(selectedRow, 1); // Tên sản phẩm
            double giaBan = (double) model.getValueAt(selectedRow, 2); // Giá bán
            int soLuongTon = (int) model.getValueAt(selectedRow, 3); // Số lượng tồn
            String moTaSanPham = (String) model.getValueAt(selectedRow, 4); // Mô tả
            String maDanhMuc = (String) model.getValueAt(selectedRow, 5); // Mã danh mục

            // Mở cửa sổ sửa và truyền dữ liệu vào
            new SuaSanPham(tenSanPham, giaBan, soLuongTon, moTaSanPham, maDanhMuc).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(QuanLySanPham.this, "Vui lòng chọn sản phẩm cần sửa.");
        }
    }
});

        jButton6.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        exportToExcel(); // Gọi hàm xuất file Excel
    }
});

    }
                

     // Thêm sản phẩm vào cơ sở dữ liệu

// Sửa sản phẩm đã chọn

// Xóa sản phẩm đã chọn

private void connectDatabase() {
    try {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QUANLY;user=sa;password=123456;encrypt=true;trustServerCertificate=true;";
        connection = DriverManager.getConnection(url);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Kết nối cơ sở dữ liệu thất bại!");
    }
}

      private void loadData() {
        String query = "SELECT * FROM SanPham"; // Thay 'YourTable' bằng tên bảng 'SanPham'
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            // Xóa dữ liệu cũ trong bảng
            model.setRowCount(0);
            
            // Duyệt qua các bản ghi và thêm vào model
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getString("MaSanPham"),      // Sửa 'column1' thành 'MaSanPham'
                    resultSet.getString("TenSanPham"),     // Sửa 'column2' thành 'TenSanPham'
                    resultSet.getDouble("GiaBan"),          // Sửa theo đúng cột của bảng SanPham
                    resultSet.getInt("SoLuongTon"),
                    resultSet.getString("MoTaSanPham"),
                    resultSet.getString("MaDanhMuc"),
                };
                model.addRow(row);
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // In ra thông tin lỗi
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }
    
private void searchProduct() {
    // Lấy dữ liệu từ các trường nhập
    String maSP = jTextField1.getText().trim();
    String maDanhMuc = jTextField2.getText().trim();
    String tenSP = jTextField3.getText().trim();
    String giaBan = jTextField4.getText().trim();
    String soLuongTon = jTextField6.getText().trim();
    

    // Xóa dữ liệu trong model trước khi tìm kiếm
    model.setRowCount(0);

    try {
        // Bắt đầu xây dựng câu truy vấn SQL
        StringBuilder sql = new StringBuilder("SELECT * FROM SanPham");
        boolean hasConditions = false;

        // Kiểm tra các trường nhập và xây dựng câu lệnh SQL
        if (!maSP.isEmpty()) {
            sql.append(" WHERE MaSanPham LIKE '%").append(maSP).append("%'");
            hasConditions = true;
        }
        
        if (!tenSP.isEmpty()) {
            sql.append(hasConditions ? " AND " : " WHERE ");
            sql.append("TenSanPham LIKE '%").append(tenSP).append("%'");
            hasConditions = true;
        }
        if (!maDanhMuc.isEmpty()) {
            sql.append(hasConditions ? " AND " : " WHERE ");
            sql.append("MaDanhMuc LIKE '%").append(maDanhMuc).append("%'");
            hasConditions = true;
        }
        if (!giaBan.isEmpty()) {
            sql.append(hasConditions ? " AND " : " WHERE ");
            sql.append("GiaBan = ").append(Double.parseDouble(giaBan)); // Chuyển đổi giaBan thành kiểu double
            hasConditions = true;
        }
        if (!soLuongTon.isEmpty()) {
            sql.append(hasConditions ? " AND " : " WHERE ");
            sql.append("SoLuongTon = ").append(Integer.parseInt(soLuongTon)); // Chuyển đổi soLuongTon thành kiểu int
            hasConditions = true;
        }
        

        // Chuẩn bị câu lệnh PreparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

        // Thực hiện truy vấn
        ResultSet resultSet = preparedStatement.executeQuery();

        // Duyệt qua kết quả và thêm vào model
        while (resultSet.next()) {
            Object[] row = {
                resultSet.getString("MaSanPham"),
                resultSet.getString("TenSanPham"),
                resultSet.getDouble("GiaBan"),
                resultSet.getInt("SoLuongTon"),
                resultSet.getString("MoTaSanPham"),
                resultSet.getString("MaDanhMuc"),
            };
            model.addRow(row); // Thêm dòng mới vào model
        }

        // Đóng ResultSet và PreparedStatement
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng cho Giá Bán và Số Lượng Tồn.");
    }
}


private void exportToExcel() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Lưu file Excel");

    // Định dạng ngày giờ hiện tại
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String timestamp = dateFormat.format(new java.util.Date());
    String defaultFileName = "SanPham_" + timestamp + ".xlsx";
    fileChooser.setSelectedFile(new java.io.File(defaultFileName));

    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
    
    int userSelection = fileChooser.showSaveDialog(this);
    
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx";
        }
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("SanPham");
            
            // Thêm dòng tiêu đề
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Mã Sản Phẩm");
            headerRow.createCell(1).setCellValue("Tên Sản Phẩm");
            headerRow.createCell(2).setCellValue("Giá Bán");
            headerRow.createCell(3).setCellValue("Số Lượng Tồn");
            headerRow.createCell(4).setCellValue("Mô Tả Sản Phẩm");
            headerRow.createCell(5).setCellValue("Mã Danh Mục");
            
            // Lấy dữ liệu từ cơ sở dữ liệu
            String query = "SELECT * FROM SanPham";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            int rowNum = 1; // Bắt đầu ghi dữ liệu từ dòng thứ 2
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getString("MaSanPham"));
                row.createCell(1).setCellValue(rs.getString("TenSanPham"));
                row.createCell(2).setCellValue(rs.getDouble("GiaBan"));
                row.createCell(3).setCellValue(rs.getInt("SoLuongTon"));
                row.createCell(4).setCellValue(rs.getString("MoTaSanPham"));
                row.createCell(5).setCellValue(rs.getString("MaDanhMuc"));
            }
            
            // Ghi dữ liệu ra file Excel
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(this, "Xuất file thành công: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Xuất file thất bại!");
        }
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ SẢN PHẨM");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ SẢN PHẨM");

        jLabel2.setText("Mã sản phẩm : ");

        jLabel3.setText("Mã danh mục : ");

        jLabel4.setText("Tên sản phẩm : ");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/rsz_search.png"))); // NOI18N
        jButton1.setText("Tìm");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, "", null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Giá bán", "Số lượng tồn", "Mô tả", "Mã danh mục	"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/rsz_back.png"))); // NOI18N
        jButton2.setText("Quay về");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Giá Bán : ");

        jLabel6.setText("Số Lượng Tồn : ");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_40px.png"))); // NOI18N
        jButton3.setText("Thêm");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_40px.png"))); // NOI18N
        jButton4.setText("Sửa");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_40px.png"))); // NOI18N
        jButton5.setText("Xóa");

        jButton6.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Downloads\\supported-platforms-excel-logo-png-3 (1).png")); // NOI18N
        jButton6.setText("Xuất file Excel");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jButton2)
                        .addGap(147, 147, 147)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(244, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6)
                                .addGap(81, 81, 81))))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySanPham("a").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
