package ThongKeDoanhThu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BieuDoDoanhThu extends JFrame {

    private Connection conn;

    public BieuDoDoanhThu(Connection conn, String fromDate, String toDate) {
        this.conn = conn;

        setTitle("Biểu đồ doanh thu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo và thêm ChartPanel vào JFrame
        JPanel chartPanel = createChartPanel(fromDate, toDate);
        add(chartPanel, BorderLayout.CENTER);
    }

    private JPanel createChartPanel(String fromDate, String toDate) {
        CategoryDataset dataset = createDataset(fromDate, toDate);  // Tạo dataset từ dữ liệu

        // Tạo biểu đồ từ dataset
        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh thu theo ngày", // Tiêu đề biểu đồ
                "Ngày", // Trục X
                "Doanh thu (VND)", // Trục Y
                dataset // Dữ liệu
        );

        // Tạo và trả về ChartPanel chứa biểu đồ
        return new ChartPanel(chart);
    }

    private CategoryDataset createDataset(String fromDate, String toDate) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            String query = "SELECT NgayGiaoDich, SUM(TongTien) AS TongDoanhThu " +
                           "FROM DonHang WHERE NgayGiaoDich BETWEEN ? AND ? " +
                           "GROUP BY NgayGiaoDich ORDER BY NgayGiaoDich";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getDouble("TongDoanhThu"), "Doanh thu", rs.getDate("NgayGiaoDich").toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu cho biểu đồ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return dataset;
    }
}
