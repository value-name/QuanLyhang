package QLDonHang;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.UUID;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Admin
 */
public class DonHangChiTietFrame extends javax.swing.JFrame {

    /**
     * Creates new form DonHangChiTietFrame
     */
    private String maDonHang;
    
    public DonHangChiTietFrame(String maDonHang) {
        initComponents();
        this.maDonHang = maDonHang;
        loadDonHangData();
        loadChiTietDonHangData();
        
        
        // Thêm WindowListener để xử lý sự kiện đóng cửa sổ
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Khi cửa sổ bị đóng, chỉ đóng cửa sổ này mà không thoát ứng dụng
                dispose();
            }
        });
        
        // Đặt hành động khi nhấn X là đóng cửa sổ này mà không thoát ứng dụng
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    
    public class DatabaseConnection {

        private static final String server = "localhost";
        private static final String user = "sa";
        private static final String password = "123456";
        private static final String db = "QUANLY";
        private static final int port = 1433;

        public static Connection getConnection() throws SQLServerException {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(user);
            ds.setPassword(password);
            ds.setDatabaseName(db);
            ds.setPortNumber(port);
            ds.setServerName(server);
            ds.setEncrypt(false);
            return ds.getConnection();
        }
    }
    
    // Phương thức tải thông tin đơn hàng
    private void loadDonHangData() {
        String query = "SELECT * FROM DonHang WHERE MaDonHang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maDonHang); // Gán mã đơn hàng vào truy vấn

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    txtMaDonHang.setText(rs.getString("MaDonHang"));
                    txtTTDH.setText(rs.getString("TrangThaiDonHang"));
                    txtPTTT.setText(rs.getString("PhuongThucThanhToan"));
                    txtTime.setText(rs.getString("NgayGiaoDich"));
                    loadTongTien(rs.getDouble("TongTien"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu đơn hàng!");
            e.printStackTrace();
        }
    }

    // Phương thức tải chi tiết sản phẩm trong đơn hàng
    private void loadChiTietDonHangData() {
        DefaultTableModel model = (DefaultTableModel) tableSPDH.getModel();
        model.setRowCount(0); // Xóa các hàng cũ

        String query = "SELECT c.MaSanPham, s.TenSanPham, c.SoLuong, c.DonGia, c.TongTien " +
                       "FROM ChiTietDonHang c " +
                       "JOIN SanPham s ON c.MaSanPham = s.MaSanPham " +
                       "WHERE c.MaDonHang = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maDonHang); // Gán mã đơn hàng vào truy vấn

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[] {
                        rs.getString("MaSanPham"),
                        rs.getString("TenSanPham"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("DonGia"),
                        rs.getDouble("TongTien")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu chi tiết đơn hàng!");
            e.printStackTrace();
        }
    }
    
     // Phương thức định dạng và hiển thị tổng tiền
    private void loadTongTien(double tongTien) {
        String formattedTongTien = String.format("%,.0f đ", tongTien); // Định dạng tiền tệ
        txtTongTien.setText(formattedTongTien);
        txtTongTien.setHorizontalAlignment(JLabel.RIGHT);  // Căn phải
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaDonHang = new javax.swing.JLabel();
        txtTTDH = new javax.swing.JLabel();
        txtTime = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSPDH = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JLabel();
        txtPTTT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Chi tiết đơn hàng");

        jLabel2.setText("Mã đơn hàng :");

        jLabel3.setText("Trạng thái đơn hàng :");

        jLabel4.setText("Phương thức thanh toán :");

        jLabel5.setText("Thời gian tạo :");

        txtMaDonHang.setText("Mã");

        txtTTDH.setText("TTDH");

        txtTime.setText("Time");

        tableSPDH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng tiền"
            }
        ));
        jScrollPane1.setViewportView(tableSPDH);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Tổng tiền :");

        txtTongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 51, 51));
        txtTongTien.setText("0 đ");

        txtPTTT.setText("PTTT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaDonHang))
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTTDH)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPTTT)
                    .addComponent(txtTime))
                .addGap(121, 121, 121))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(txtTongTien)
                .addGap(66, 66, 66))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtMaDonHang)
                    .addComponent(txtPTTT))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(txtTTDH)
                    .addComponent(txtTime))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTongTien))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableSPDH;
    private javax.swing.JLabel txtMaDonHang;
    private javax.swing.JLabel txtPTTT;
    private javax.swing.JLabel txtTTDH;
    private javax.swing.JLabel txtTime;
    private javax.swing.JLabel txtTongTien;
    // End of variables declaration//GEN-END:variables
}
