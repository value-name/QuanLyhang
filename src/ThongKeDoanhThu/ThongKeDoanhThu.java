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

    public ThongKeDoanhThu() {
        setTitle("Thống kê doanh thu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel chứa các input cho khoảng thời gian
        JPanel panelInput = new JPanel(new FlowLayout());
        // Nút quay về với icon
JButton btnQuayVe = new JButton("Quay về");
btnQuayVe.setIcon(new ImageIcon(getClass().getResource("/icon/rsz_back.png"))); // Thêm icon từ đường dẫn

// Thay đổi kích thước nút và font nếu muốn phóng to
btnQuayVe.setPreferredSize(new Dimension(120, 40)); // Đặt kích thước nút lớn hơn

// Thêm nút vào panel
panelInput.add(btnQuayVe);
ImageIcon icon = new ImageIcon(getClass().getResource("/icon/rsz_back.png"));
Image image = icon.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH); // Thay đổi kích thước icon
btnQuayVe.setIcon(new ImageIcon(image));

        
        panelInput.add(new JLabel("Từ ngày (yyyy-MM-dd):"));
        txtFromDate = new JTextField(10);
        panelInput.add(txtFromDate);

        panelInput.add(new JLabel("Đến ngày (yyyy-MM-dd):"));
        txtToDate = new JTextField(10);
        panelInput.add(txtToDate);

        JButton btnXemThongKe = new JButton("Xem thống kê");
        panelInput.add(btnXemThongKe);

        // Nút quay về


        // Bảng hiển thị đơn hàng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã đơn hàng", "Mã sản phẩm", "Ngày giao dịch", "Tổng tiền", "Trạng thái", "Phương thức thanh toán"});
        tableDonHang = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableDonHang);

        // Label hiển thị tổng doanh thu
        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu: ");
        txtTongDoanhThu = new JTextField(10);
        txtTongDoanhThu.setEditable(false); // Không cho phép chỉnh sửa
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

        // Xử lý sự kiện khi nhấn nút "Quay về"
        btnQuayVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrangChu().setVisible(true); // Mở trang chủ
                dispose(); // Đóng cửa sổ hiện tại
            }
        });
    }

    private void connectDatabase() {
        try {
            String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QUANLY;user=sa;password=123456;encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Không kết nối được với cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void xemThongKe() {
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        txtTongDoanhThu.setText(""); // Đặt lại tổng doanh thu

        try {
            String fromDate = txtFromDate.getText();
            String toDate = txtToDate.getText();
            String query = "SELECT MaDonHang, MaSanPham, NgayGiaoDich, TongTien, TrangThaiDonHang, PhuongThucThanhToan " +
                           "FROM DonHang WHERE NgayGiaoDich BETWEEN ? AND ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);

            ResultSet rs = ps.executeQuery();
            double tongDoanhThu = 0.0;

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("MaDonHang"));
                row.add(rs.getString("MaSanPham"));
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
            ThongKeDoanhThu frame = new ThongKeDoanhThu();
            frame.setVisible(true);
        });
    }
}
