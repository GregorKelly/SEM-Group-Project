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

        List countriesWorld = a.GetCountriesWorld();

        System.out.println("Countries in the world sorted by population");
        System.out.println(countriesWorld);

        List countriesCont = a.GetCountriesCont("Europe");

        System.out.println("Countries in Europe sorted by population");
        System.out.println(countriesCont);

        List countriesReg = a.GetCountriesReg("Middle East");

        System.out.println("Countries in Middle East sorted by population");
        System.out.println(countriesReg);

        List cityWorld = a.GetCityWorld();

        System.out.println("Cities in World sorted by population");
        System.out.println(cityWorld);
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

    public List GetCountriesWorld()
    {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Name "
                    + "FROM country "
                    + "ORDER BY Population DESC";

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
            System.out.println("Failed To Print Countries from world");
            return null;
        }
    }

    public List GetCountriesCont(String continentIn)
    {
        if(continentIn == null)
        {
            System.out.println("No continent entered");
            return null;
        }

        if(continentIn != "Europe" && continentIn != "Asia" && continentIn != "North America" && continentIn != "Africa" && continentIn != "Oceania" && continentIn != "Antartica" && continentIn != "South America")
        {
            System.out.println("Invalid Continent Entered");
            return null;
        }
        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Name "
                            + "FROM country "
                            + "WHERE Continent = " + "'" + continentIn + "' "
                            + "ORDER BY Population DESC";

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
            System.out.println("Failed To Print Countries from continent");
            return null;
        }
    }

    public List GetCountriesReg(String regIn)
    {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Name "
                            + "FROM country "
                            + "WHERE Region = " + "'" + regIn + "' "
                            + "ORDER BY Population DESC";

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
            System.out.println("Failed To Print Countries from region");
            return null;
        }
    }

    public List GetCityWorld()
    {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Name "
                            + "FROM city "
                            + "ORDER BY Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);

            List countries = new ArrayList();
            while (rset.next())
            {
                countries.add(rset.getString("city.Name"));
            }
            return countries;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed To Print Cities from World");
            return null;
        }
    }

   /* public List GetCityCont(String cityCont)
    {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Name "
                            + "FROM city "
                            + "WHERE "
                            + "ORDER BY Population DESC";

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
            System.out.println("Failed To Print Cities from World");
            return null;
        }
    }*/

}