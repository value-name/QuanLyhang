package LoginAndSignUp;



import java.sql.*;
import javax.swing.JOptionPane;



public class SignUp extends javax.swing.JFrame {

 
    public SignUp() {
        initComponents();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign Up");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Company Name");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("copyright © company name All rights reserved");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel2)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(64, 64, 64))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 400, 500);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setBackground(new java.awt.Color(0, 102, 102));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("ĐĂNG KÝ");

        jLabel5.setBackground(new java.awt.Color(102, 102, 102));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tên đăng nhập");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel6.setBackground(new java.awt.Color(102, 102, 102));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Email");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel7.setBackground(new java.awt.Color(102, 102, 102));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Mật khẩu");

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPasswordField1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel8.setText("Tôi đã có tài khoản");

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Đăng ký");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setForeground(new java.awt.Color(255, 51, 51));
        jButton2.setText("Đăng nhập");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5)
                                .addComponent(jTextField1)
                                .addComponent(jLabel6)
                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addComponent(jPasswordField1))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(400, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 126, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null); 
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                                       
    String tenDangNhap = jTextField1.getText(); 
    String matKhau = new String(jPasswordField1.getPassword()); 
    String email = jTextField2.getText();

    if (tenDangNhap.isEmpty() || matKhau.isEmpty() || email.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
        return;
    }

    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        JOptionPane.showMessageDialog(null, "Địa chỉ email không hợp lệ!");
        return;
    }

    // Kiểm tra độ dài, chữ in hoa và ký tự đặc biệt của mật khẩu
    if (matKhau.length() < 8 || !matKhau.matches(".*[A-Z].*") || !matKhau.matches(".*[!@#$%^&*()].*")) {
        JOptionPane.showMessageDialog(null, "Mật khẩu phải có ít nhất 8 ký tự, chứa ít nhất một chữ in hoa và một ký tự đặc biệt!");
        return;
    }

    try (Connection conn = DBConnect.getConnection()) {
        String checkUsernameQuery = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ?";
        try (PreparedStatement psCheck = conn.prepareStatement(checkUsernameQuery)) {
            psCheck.setString(1, tenDangNhap);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại!");
                return;
            }
        }

        String checkEmailQuery = "SELECT * FROM TaiKhoan WHERE Email = ?";
        try (PreparedStatement psCheckEmail = conn.prepareStatement(checkEmailQuery)) {
            psCheckEmail.setString(1, email);
            ResultSet rsEmail = psCheckEmail.executeQuery();
            if (rsEmail.next()) {
                JOptionPane.showMessageDialog(null, "Email đã được sử dụng!");
                return;
            }
        }

        String maNguoiDung = generateNewMaNguoiDung();

        String insertNguoiDungQuery = "INSERT INTO NguoiDung (MaNguoiDung, TenNguoiDung, Email, LoaiNguoiDung) VALUES (?, ?, ?, 'nhan vien')";
        try (PreparedStatement psInsertNguoiDung = conn.prepareStatement(insertNguoiDungQuery)) {
            psInsertNguoiDung.setString(1, maNguoiDung);
            psInsertNguoiDung.setString(2, tenDangNhap);
            psInsertNguoiDung.setString(3, email);
            psInsertNguoiDung.executeUpdate();
        }

        String insertTaiKhoanQuery = "INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, Email, MaNguoiDung, TrangThai, NgayTao, LoaiTaiKhoan) "
                + "VALUES (?, ?, ?, ?, ?, 'hoat dong', GETDATE(), 'nhan vien')";
        
        String maTaiKhoan = generateNewMaTaiKhoan(); 
        try (PreparedStatement psInsertTaiKhoan = conn.prepareStatement(insertTaiKhoanQuery)) {
            psInsertTaiKhoan.setString(1, maTaiKhoan);
            psInsertTaiKhoan.setString(2, tenDangNhap);
            psInsertTaiKhoan.setString(3, matKhau);
            psInsertTaiKhoan.setString(4, email);
            psInsertTaiKhoan.setString(5, maNguoiDung);
            psInsertTaiKhoan.executeUpdate();
        }

        JOptionPane.showMessageDialog(null, "Đăng ký thành công!");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }


    }//GEN-LAST:event_jButton1ActionPerformed

    private String generateNewMaNguoiDung() {
    return "ND" + System.currentTimeMillis();
}

// Hàm tạo mã tài khoản mới
private String generateNewMaTaiKhoan() {
    return "TK" + System.currentTimeMillis();
}
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
