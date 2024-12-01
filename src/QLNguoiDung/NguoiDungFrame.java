package QLNguoiDung;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Admin
 */
public class NguoiDungFrame extends javax.swing.JFrame {

    /**
     * Creates new form NguoiDungFrame
     */
    public NguoiDungFrame() {
        initComponents();
        fetchData();
        
        // Đặt hành động khi nhấn X là đóng cửa sổ này mà không thoát ứng dụng
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        //Tim kiem
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fetchData(); // Call fetchData when text is inserted
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fetchData(); // Call fetchData when text is removed
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not needed for plain text fields
            }
        });
    }

    private void fetchData() {
        var server = "localhost";
        var user = "sa";
        var password = "123456";
        var db = "QUANLY";
        var port = 1433;

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setPortNumber(port);
        ds.setServerName(server);
        ds.setEncrypt(false);

        // Lấy cột tìm kiếm và từ khóa
        String selectedColumn = cbxTimKiem.getSelectedItem().toString();
        String keyword = txtTimKiem.getText().trim();

        // Xác định tên cột trong CSDL tương ứng với lựa chọn
        String dbColumn = switch (selectedColumn) {
            case "Mã người dùng" ->
                "MaNguoiDung";
            case "Tên người dùng" ->
                "TenNguoiDung";
            case "Email" ->
                "Email";
            case "Số điện thoại" ->
                "SoDienThoai";
            case "Địa chỉ" ->
                "DiaChi";
            case "Loại người dùng" ->
                "LoaiNguoiDung";
            default ->
                "";
        };

        // Câu truy vấn SQL, thêm điều kiện LIKE nếu có từ khóa
        String query = "SELECT * FROM NGUOIDUNG";
        if (!keyword.isEmpty() && !dbColumn.isEmpty()) {
            query += " WHERE " + dbColumn + " LIKE ?";
        }

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            if (!keyword.isEmpty() && !dbColumn.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tableNguoiDung.getModel();
                model.setRowCount(0);  // Xóa các hàng cũ

                // Duyệt qua từng dòng kết quả và thêm vào table model
                while (rs.next()) {
                    String maNguoiDung = rs.getString("MaNguoiDung");
                    String tenNguoiDung = rs.getString("TenNguoiDung");
                    String email = rs.getString("Email");
                    String soDienThoai = rs.getString("SoDienThoai");
                    String diaChi = rs.getString("DiaChi");
                    String loaiNguoiDung = rs.getString("LoaiNguoiDung");

                    model.addRow(new Object[]{maNguoiDung, tenNguoiDung, email, soDienThoai, diaChi, loaiNguoiDung});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    // Phương thức xóa người dùng
    private void xoaNguoiDung() {
        int row = tableNguoiDung.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để xóa!");
            return;
        }

        String maNguoiDung = (String) tableNguoiDung.getValueAt(row, 0); // Lấy mã người dùng từ cột đầu tiên
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa người dùng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sqlDeleteAccount = "DELETE FROM TAIKHOAN WHERE MaNguoiDung = ?";
            String sqlDeleteUser = "DELETE FROM NGUOIDUNG WHERE MaNguoiDung = ?";
            try (Connection conn = getConnection()) {
                conn.setAutoCommit(false); // Bắt đầu transaction

                try (PreparedStatement psDeleteAccount = conn.prepareStatement(sqlDeleteAccount); PreparedStatement psDeleteUser = conn.prepareStatement(sqlDeleteUser)) {

                    // Xóa tài khoản của người dùng
                    psDeleteAccount.setString(1, maNguoiDung);
                    psDeleteAccount.executeUpdate();

                    // Xóa người dùng
                    psDeleteUser.setString(1, maNguoiDung);
                    psDeleteUser.executeUpdate();

                    conn.commit(); // Xác nhận transaction
                    JOptionPane.showMessageDialog(this, "Xóa người dùng và tài khoản thành công!");
                    fetchData(); // Cập nhật bảng sau khi xóa
                } catch (SQLException e) {
                    conn.rollback(); // Hoàn tác nếu có lỗi
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa người dùng hoặc tài khoản!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void moManHinhThem() {
        new ThemNguoiDungFrame(this).setVisible(true);
    }

    // Phương thức mở màn hình sửa người dùng
    private void moManHinhSua() {
        int row = tableNguoiDung.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để sửa!");
            return;
        }

        String maNguoiDung = (String) tableNguoiDung.getValueAt(row, 0);
        String tenNguoiDung = (String) tableNguoiDung.getValueAt(row, 1);
        String email = (String) tableNguoiDung.getValueAt(row, 2);
        String soDienThoai = (String) tableNguoiDung.getValueAt(row, 3);
        String diaChi = (String) tableNguoiDung.getValueAt(row, 4);
        String loaiNguoiDung = (String) tableNguoiDung.getValueAt(row, 5);

        new SuaNguoiDungFrame(maNguoiDung, tenNguoiDung, email, soDienThoai, diaChi, loaiNguoiDung, this).setVisible(true);
    }

    public void refreshTable() {
        fetchData();
    }

    // Phương thức kết nối CSDL
    Connection getConnection() throws SQLException {
        var server = "localhost";  // Hoặc tên server của bạn
        var user = "sa";
        var password = "123456";
        var db = "QUANLY";
        var port = 1433;

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setPortNumber(port);
        ds.setServerName(server);
        ds.setEncrypt(false);
        return ds.getConnection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cbxTimKiem = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableNguoiDung = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Quản lý người dùng");

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_40px.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setFocusable(false);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_40px.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_40px.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName(""); // NOI18N

        cbxTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã người dùng", "Tên người dùng", "Email", "Số điện thoại", "Địa chỉ", "Loại người dùng" }));
        cbxTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTimKiemActionPerformed(evt);
            }
        });

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(cbxTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoi)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        tableNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã người dùng", "Tên người dùng", "Email", "Số điện thoại", "Địa chỉ", "Loại người dùng"
            }
        ));
        jScrollPane1.setViewportView(tableNguoiDung);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(76, 76, 76)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnSua)
                            .addComponent(btnXoa)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.getAccessibleContext().setAccessibleName("Tìm kiếm");
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        moManHinhThem();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        moManHinhSua();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaNguoiDung();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        txtTimKiem.setText("");
        fetchData();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void cbxTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTimKiemActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
        fetchData();
    }//GEN-LAST:event_txtTimKiemActionPerformed

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
            java.util.logging.Logger.getLogger(NguoiDungFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguoiDungFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguoiDungFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguoiDungFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NguoiDungFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbxTimKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableNguoiDung;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
