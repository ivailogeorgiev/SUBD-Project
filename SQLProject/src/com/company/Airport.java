package com.company;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class Airport {
    public Airport(String Name, String Location, int Runways){
        final String db ="jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC\n";
        final String jdbc_driver ="com.mysql.cj.jdbc.Driver";

        final String username = "root";
        final String password = "root";

        Connection conn =null;
        Statement st = null;


        // write your code here
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = getConnection(db,username,password);
            st = conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    /*
        String query = "insert into Airports"
                + "values('"+Name+"', '"+Location+"', '"+Runways+"')";
    */
        String query = String.format("insert into Airports values(%s, %s, %d)", Name, Location, Runways);
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
