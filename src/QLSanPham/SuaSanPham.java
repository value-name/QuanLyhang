package QLSanPham;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
public class SuaSanPham extends javax.swing.JFrame {

    /**
     * Creates new form ThemSanPham1
     */
    public SuaSanPham() {
        initComponents();
    }
    public SuaSanPham(String tenSanPham, double giaBan, int soLuongTon, String moTa, String maDanhMuc) {
    initComponents();

    // Hiển thị thông tin sản phẩm cũ lên giao diện
    jTextField6.setText(tenSanPham);
    jTextField1.setText(String.valueOf(giaBan));
    jTextField4.setText(String.valueOf(soLuongTon));
    jTextField7.setText(moTa);
    jTextField3.setText(maDanhMuc);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("THÊM SẢN PHẨM");

        jLabel2.setText("Tên sản phẩm : ");

        jLabel3.setText("Giá bán : ");

        jLabel4.setText("Số lượng tồn : ");

        jLabel5.setText("Mô tả : ");

        jLabel6.setText("Mã danh mục : ");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel8.setText("SỬA SẢN PHẨM");
        jLabel8.setToolTipText("");

        jButton1.setText("Sửa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(309, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                        .addComponent(jTextField1)
                        .addComponent(jTextField4)
                        .addComponent(jTextField7)
                        .addComponent(jTextField3)))
                .addGap(153, 153, 153))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addComponent(jButton1)
                .addContainerGap(202, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
       
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                                     
    // Lấy dữ liệu từ các trường nhập
String tenSanPham = jTextField6.getText();
String giaBanStr = jTextField1.getText();
String soLuongTonStr = jTextField4.getText();
String moTa = jTextField7.getText();
String maDanhMuc = jTextField3.getText();

// Kiểm tra nếu giá bán và số lượng tồn không phải là số
try {
    // Kiểm tra giá bán
    double giaBan = Double.parseDouble(giaBanStr); // Chuyển đổi giá bán từ chuỗi thành số
    if (giaBan < 0) {
        JOptionPane.showMessageDialog(this, "Lỗi: Giá bán không thể là số âm.");
        return;
    }
    
    // Kiểm tra số lượng tồn
    int soLuongTon = Integer.parseInt(soLuongTonStr); // Chuyển đổi số lượng tồn từ chuỗi thành số
    if (soLuongTon < 0) {
        JOptionPane.showMessageDialog(this, "Lỗi: Số lượng tồn không thể là số âm.");
        return;
    }

    // Kiểm tra tên sản phẩm
    if (tenSanPham == null || tenSanPham.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Lỗi: Tên sản phẩm không thể trống.");
        return;
    }
    
    // Kết nối với cơ sở dữ liệu và thực hiện cập nhật
    String url = "jdbc:sqlserver://localhost:1433;databaseName=QUANLY;user=sa;password=123456;encrypt=true;trustServerCertificate=true;";
    Connection con = DriverManager.getConnection(url);

    // Câu lệnh SQL để cập nhật sản phẩm
    String sql = "UPDATE SanPham SET TenSanPham = ?, GiaBan = ?, SoLuongTon = ?, MoTaSanPham = ?, MaDanhMuc = ? WHERE TenSanPham = ?";

    // Prepare the statement
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setString(1, tenSanPham);  // New product name
    stmt.setDouble(2, giaBan);
    stmt.setInt(3, soLuongTon);
    stmt.setString(4, moTa);
    stmt.setString(5, maDanhMuc);
    stmt.setString(6, tenSanPham);  // Old product name (to identify the product to update)

    // Execute the update
    int rowsAffected = stmt.executeUpdate();

    // Check the result
    if (rowsAffected > 0) {
        JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
    } else {
        JOptionPane.showMessageDialog(this, "Lỗi: Không thể thay đổi tên sản phẩm .");
    }

    // Close the connection
    con.close();
} catch (NumberFormatException nfe) {
    // Handle invalid number format (non-numeric input)
    JOptionPane.showMessageDialog(this, "Lỗi: Giá bán hoặc số lượng tồn phải là số hợp lệ.");
    nfe.printStackTrace();
} catch (NullPointerException npe) {
    // NullPointerException - Handle null pointer errors
    JOptionPane.showMessageDialog(this, "Lỗi: Dữ liệu không hợp lệ (null value).");
    npe.printStackTrace();
} catch (Exception e) {
    // General Exception - Catch any other errors
    JOptionPane.showMessageDialog(this, "Lỗi không xác định: " + e.getMessage());
    e.printStackTrace();
}


    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ThemSanPhamFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThemSanPhamFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThemSanPhamFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThemSanPhamFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
