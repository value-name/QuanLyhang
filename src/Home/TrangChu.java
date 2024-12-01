package Home;

import QLDanhMuc.CategoryManagerGUI;
import QLNguoiDung.QLTaiKhoanFrame;
import QLDonHang.QLDonHangFrame;
import ThongKeDoanhThu.ThongKeDoanhThu;
import QLSanPham.QuanLySanPham;
import QLTonKho.QuanLyTonKho;
import com.sun.jdi.connect.spi.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TrangChu extends javax.swing.JFrame {

    public TrangChu() {
        initComponents();
    }

    
    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TRANG CHỦ");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/rsz_1home-button.png")));
        jButton1.setText("TRANG CHỦ");

        jButton2.setText("Quản lý danh mục sản phẩm");
        jButton2.addActionListener(evt -> {
            this.dispose(); // Đóng trang chủ trước khi mở trang quản lý danh mục sản phẩm
            new CategoryManagerGUI().setVisible(true);
        });

        jButton3.setText("Quản lý tồn kho");
        jButton3.addActionListener(evt -> {
            this.dispose(); // Đóng trang chủ trước khi mở trang quản lý tồn kho
            new QuanLyTonKho().setVisible(true);
        });

        jButton4.setText("Quản lý đơn hàng");
        jButton4.addActionListener(evt -> {
            this.dispose(); // Đóng trang chủ trước khi mở trang quản lý đơn hàng
            new QLDonHangFrame().setVisible(true);
        });

        jButton5.setText("Quản lý người dùng");
        jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                TrangChu.this.dispose(); // Đóng trang chủ trước khi mở trang quản lý người dùng
                new QLTaiKhoanFrame().setVisible(true);
            }
        });

        jButton7.setText("Thống kê doanh thu");
        jButton7.addActionListener(evt -> {
            this.dispose(); // Đóng trang chủ trước khi mở trang thống kê doanh thu
            new ThongKeDoanhThu().setVisible(true);
        });

        jButton8.setText("Quản lý sản phẩm");
        jButton8.addActionListener(evt -> {
            this.dispose(); // Đóng trang chủ trước khi mở trang quản lý sản phẩm
            new QuanLySanPham().setVisible(true);
        });

        // Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jButton1)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }



    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TrangChu().setVisible(true));
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
}
