package ThongKeDoanhThu;

import Home.TrangChu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class ThongKeDoanhThu extends JFrame {
    private JTextField txtFromDate, txtToDate, txtTongDoanhThu;
    private JTable tableDonHang;
    private DefaultTableModel model;
    private Connection conn;
    private String loaiTaiKhoan;

    public ThongKeDoanhThu(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
        setTitle("Thống kê doanh thu");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel chứa các input cho khoảng thời gian
        JPanel panelInput = new JPanel(new FlowLayout());
        JButton btnQuayVe = new JButton("Quay về");
        btnQuayVe.setIcon(new ImageIcon(getClass().getResource("/icon/rsz_back.png")));
        btnQuayVe.setPreferredSize(new Dimension(120, 40));
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/rsz_back.png"));
        Image image = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnQuayVe.setIcon(new ImageIcon(image));
        panelInput.add(btnQuayVe);

        panelInput.add(new JLabel("Từ ngày (yyyy-MM-dd):"));
        txtFromDate = new JTextField(10);
        panelInput.add(txtFromDate);

        panelInput.add(new JLabel("Đến ngày (yyyy-MM-dd):"));
        txtToDate = new JTextField(10);
        panelInput.add(txtToDate);

        JButton btnXemThongKe = new JButton("Xem thống kê");
        panelInput.add(btnXemThongKe);

        JButton btnXemBieuDo = new JButton("Xem biểu đồ");
        panelInput.add(btnXemBieuDo);  // Thêm nút "Xem biểu đồ"

        // Bảng hiển thị đơn hàng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã đơn hàng", "Ngày giao dịch", "Tổng tiền", "Trạng thái", "Phương thức thanh toán"});
        tableDonHang = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableDonHang);

        // Label hiển thị tổng doanh thu
        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu: ");
        txtTongDoanhThu = new JTextField(10);
        txtTongDoanhThu.setEditable(false);
        JPanel panelTongDoanhThu = new JPanel(new FlowLayout());
        panelTongDoanhThu.add(lblTongDoanhThu);
        panelTongDoanhThu.add(txtTongDoanhThu);

        // Layout chính
        setLayout(new BorderLayout());
        add(panelInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelTongDoanhThu, BorderLayout.SOUTH);

        // Kết nối tới cơ sở dữ liệu
        connectDatabase();

        // Xử lý sự kiện khi nhấn nút "Xem thống kê"
        btnXemThongKe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xemThongKe();
            }
        });

        // Xử lý sự kiện khi nhấn nút "Xem biểu đồ"
        btnXemBieuDo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Khi nhấn "Xem biểu đồ", mở cửa sổ mới
                String fromDate = txtFromDate.getText();
                String toDate = txtToDate.getText();
                new BieuDoDoanhThu(conn, fromDate, toDate).setVisible(true);  // Mở cửa sổ biểu đồ
                dispose();  // Đóng cửa sổ hiện tại
            }
        });

        // Xử lý sự kiện khi nhấn nút "Quay về"
        btnQuayVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrangChu(loaiTaiKhoan).setVisible(true);
                dispose();
            }
        });
    }

    private void connectDatabase() {
        try {
            String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QUANLY;user=sa;password=123456;encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(connectionUrl);
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Không kết nối được với cơ sở dữ liệu.\n" + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void xemThongKe() {
        model.setRowCount(0);
        txtTongDoanhThu.setText("");

        try {
            String fromDate = txtFromDate.getText();
            String toDate = txtToDate.getText();
            String query = "SELECT MaDonHang, NgayGiaoDich, TongTien, TrangThaiDonHang, PhuongThucThanhToan " +
                           "FROM DonHang WHERE NgayGiaoDich BETWEEN ? AND ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);

            ResultSet rs = ps.executeQuery();
            double tongDoanhThu = 0.0;

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("MaDonHang"));
                row.add(rs.getDate("NgayGiaoDich"));
                row.add(rs.getDouble("TongTien"));
                row.add(rs.getString("TrangThaiDonHang"));
                row.add(rs.getString("PhuongThucThanhToan"));
                model.addRow(row);
                tongDoanhThu += rs.getDouble("TongTien");
            }

            txtTongDoanhThu.setText(String.valueOf(tongDoanhThu) + " VND");

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian này.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThongKeDoanhThu frame = new ThongKeDoanhThu("a");
            frame.setVisible(true);
        });
    }
}
