package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        List countries = a.GetCountries();

        System.out.println(countries);

        // Disconnect from database
        a.disconnect();

    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public List GetCountries()
    {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT country.Name"
                    + "FROM country"
                    + "ORDER BY country.Population ASC";

            ResultSet rset = stmt.executeQuery(strSelect);

           List countries = new ArrayList();
           while (rset.next())
           {
               countries.add(rset.getString("country.Name"));
           }
           return countries;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed To Print Countries");
            return null;
        }
    }

}