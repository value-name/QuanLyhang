package LoginAndSignUp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Admin
 */
public class DBConnect {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QUANLY;encrypt=false;trustServerCertificate=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}