package util;


import com.mysql.jdbc.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import static java.util.jar.Pack200.Packer.PASS;

/**
 * Created by kalexjune on 17/3/24.
 */
public class DBUtil {

    // src is the root folder.
    private static final String PROP_PATH = "db.properties";

    // 连接数据库的路径
    public static String url;
    // 连接数据库的用户名
    public static String user;
    // 连接数据库的密码
    public static String pwd;

    public static String driver;

    static {
        try {
            Properties properties = new Properties();
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream(PROP_PATH);
            properties.load(in);
            in.close();

            driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            user = properties.getProperty("jdbc.user");
            pwd = properties.getProperty("jdbc.password");

            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance() throws SQLException {

        try {
            Connection conn = DriverManager.getConnection(url,user,pwd);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 关闭给定的连接
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭ResultSet
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 关闭Statement
     * @param stmt
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
