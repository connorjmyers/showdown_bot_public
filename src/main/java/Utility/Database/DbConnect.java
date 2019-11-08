package Utility.Database;

import java.sql.*;

/**
 * Connects program to database.
 * Uses material from https://www.youtube.com/watch?v=BCqW5XwtJxY. Thank you for your help, Naveed <3
 *
 * @author Naveed Ziarab
 */
public class DbConnect {

    public Connection con;
    public Statement st;
    public ResultSet rs;

    public DbConnect() {

        try {
            String host = "jdbc:mysql://server_ip:3306/database_name";
            String conditions = "?autoReconnect=true&useSSL=false";

            String user = "username";
            String password = "password";

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(host + conditions, user, password);
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("There has been an error: " + ex);
        }
    }
}

