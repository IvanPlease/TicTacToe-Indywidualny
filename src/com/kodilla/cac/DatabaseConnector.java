package com.kodilla.cac;

import java.sql.*;

public class DatabaseConnector {

    private String mysqlURL = "jdbc:mysql://localhost:3306/ttt_game";
    private String user = "root";
    private String password = "";

    public void insertToDatabase(String sqlQuery) throws Exception{
        Connection con = DriverManager.getConnection(mysqlURL,user,password);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sqlQuery);
        con.close();
    }

    public void updateRowInDatabase(String sqlQuery) throws Exception{
        Connection con = DriverManager.getConnection(mysqlURL,user,password);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sqlQuery);
        con.close();
    }

    public int selectFromDatabase(String sqlQuery) throws Exception{
        Connection con = DriverManager.getConnection(mysqlURL,user,password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        int count = 0;
        if(rs.next()){
            count = rs.getInt("count(*)");
        }
        con.close();
        return count;
    }

    public boolean selectFromDatabaseForResult(String sqlQuery) throws Exception{
        Connection con = DriverManager.getConnection(mysqlURL,user,password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        boolean res = false;
        if(rs.next()){
            if(rs.getInt(1) == 1){
                res = true;
            }
        }
        con.close();
        return res;
    }

    public void selectFromDatabase(String sqlQuery, int[] users) throws Exception{
        Connection con = DriverManager.getConnection(mysqlURL,user,password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        int iter = 0;
        while (rs.next()){
            users[iter] = rs.getInt(1);
            iter++;
        }
        con.close();
    }

    public int selectIDFromDatabase(String sqlQuery) throws Exception{
        Connection con = DriverManager.getConnection(mysqlURL,user,password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        int users = 0;
        if(rs.next()){
            users = rs.getInt(1);
        }
        con.close();
        return users;
    }
}

