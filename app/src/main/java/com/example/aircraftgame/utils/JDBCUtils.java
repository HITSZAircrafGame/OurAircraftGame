package com.example.aircraftgame.utils;

import java.sql.Connection;

import java.sql.DriverManager;

public class JDBCUtils {
    private static final String TAG = "mysql-player-JDBCUtils";

    private static String driver = "com.mysql.jdbc.Driver";

    private static String dbName = "db_for_myself";

    private static String user = "ted";

    private static String password = "";

    public static Connection getConn(){
        Connection connection = null;
        try{
            Class.forName(driver);
            String ip = "10.250.176.199";

            connection = DriverManager.getConnection("jdbc:mysql://" + ip +
                    ":3306/" + dbName + "?useUnicode=true&characterEncoding=utf-8&useSSL=false",user,password);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
