package com.company;


import java.sql.*;

import static java.sql.DriverManager.*;

public class Main {

    public static void main(String[] args) {
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



        Airport air = new Airport("Sofia", "Sofia", 5);
        Airport air2 = new Airport("Varna", "Varna", 3);

        String query = "select id, name from Airports";

        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String age = rs.getString("name");

                System.out.print("ID: " + id);
                System.out.print(", Name: " + age);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
