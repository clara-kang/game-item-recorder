package logic;

import java.sql.*;
import java.util.*;

public class H2DataUtil implements DataUtil{

    private static String pwd = Utils.readCurrentDirectory();
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL_PREFIX = "jdbc:h2:" + pwd + "/data/";

    private static final String USER = "sa";
    private static final String PASS = "";

    public List<String> readDates(String month){
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("Connecting to database...");
        Connection conn = null;
        Statement stmt = null;
        List<String> result = new LinkedList<String>();
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='TABLE'";
        try {
            conn = DriverManager.getConnection(DB_URL_PREFIX + month, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                result.add(rs.getString("TABLE_NAME"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cleanUp(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Set<Item> readItems(String month, String date){
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("Connecting to database...");
        Connection conn = null;
        Statement stmt = null;
        Set<Item> result = new HashSet<Item>();
        String sql = "SELECT * FROM " + date;
        try {
            conn = DriverManager.getConnection(DB_URL_PREFIX + month, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Item item = new Item();
                item.setName(rs.getString("ITEM"));
                item.setPrice(rs.getInt("PRICE"));
                item.setQuantity(rs.getInt("QUANTITY"));
                result.add(item);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cleanUp(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void cleanUp(Connection conn, Statement stmt) throws SQLException {
        if(stmt != null) {
            stmt.close();
        }
        if(conn != null) {
            conn.close();
            System.out.println("Disconnected from database");
        }
    }
}
