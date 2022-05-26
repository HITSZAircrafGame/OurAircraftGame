package com.example.aircraftgame.utils;

import java.sql.Connection;

import java.sql.DriverManager;

public class JDBCUtils {
    private static final String TAG = "mysql-player-JDBCUtils";

    private static String driver = "com.mysql.jdbc.Driver";

    private static String dbName = "db_for_myself";

    private static String user = "root";

    private static String password = "zxq1314mm";

    public static Connection getConn(){
        Connection connection = null;
        try{
            Class.forName(driver);
            String ip = "10.0.2.2";

            connection = DriverManager.getConnection("jdbc:mysql://" + ip +
                    ":3306/" + dbName,user,password);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
