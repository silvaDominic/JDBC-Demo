package com.dan.redding;

import java.sql.*;

/**
 * A simple demo demonstrating the capabilities of JDBC
 *
 */
public class Demo {
    public static void main(String[] args) {
        //Create variables for access to local host
        String dbURL = "jdbc:mysql://localhost:3306/SampleDB";
        String username = "root";
        String password = "ROOTpass";

        //Attempt to connect to local host
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected.");

                //Create variable for prepared statement
                String sqlInsert = "INSERT INTO users (username, password, fullname, email) VALUES (?, ?, ?, ?)";

                //Initialize prepared statement
                PreparedStatement p_statement = conn.prepareStatement(sqlInsert);

                //Set dynamic variables
                p_statement.setString(1, "dan");
                p_statement.setString(2, "redding");
                p_statement.setString(3, "Dan Redding");
                p_statement.setString(4, "dan.redding@email.com");

                //Update table initialize inserted rows variable
                int rowsInserted = p_statement.executeUpdate();

                if(rowsInserted > 0){
                    System.out.println("New user added successfully.");

                    String sqlSelect = "SELECT * FROM users";

                    Statement statement = conn.createStatement();

                    ResultSet result = statement.executeQuery(sqlSelect);

                    int count = 0;
                    while (result.next()){
                        String returnedUsername = result.getString(2);
                        String returnedPassword = result.getString(3);
                        String returnedFullname = result.getString("fullname");
                        String returnedEmail = result.getString("email");

                        String output = "User #%d: %s - %s - %s - %s";

                        System.out.println(String.format(output, ++count, returnedUsername,
                                returnedPassword, returnedFullname, returnedEmail));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
