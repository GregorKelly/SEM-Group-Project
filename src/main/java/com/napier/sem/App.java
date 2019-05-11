package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class App {

    public static void main(String[] args) {

        // Create new Application
        // App a = new App();

        // Connect to database
        if (args.length < 1) {
            connect("localhost:33060");
        } else {
            connect(args[0]);
        }

        SpringApplication.run(App.class, args);

        // List countriesWorld = GetCountriesWorld();

        // System.out.println("Countries in the world sorted by population");
        // System.out.println(countriesWorld);
/*
        List countriesCont = a.GetCountriesCont("Europe");

        System.out.println("Countries in Europe sorted by population");
        System.out.println(countriesCont);

        List countriesReg = a.GetCountriesReg("Middle East");

        System.out.println("Countries in Middle East sorted by population");
        System.out.println(countriesReg);

        List cityWorld = a.GetCityWorld();

        System.out.println("Cities in World sorted by population");
        System.out.println(cityWorld);

        //List CountriesWorldReg = a.GetCountriesWorldReg();
        //System.out.println("Cities in World sorted by population");
        //System.out.println(cityWorld);

        //List CityCont = a.GetCityCont("Asia");

        //System.out.println("Cities in World sorted by population");
        //System.out.println(CityCont);
        // Disconnect from database
        a.disconnect();
*/
        //disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public static void connect(String location) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
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
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
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
    public static void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    //1 - See all counties in World from largest population to smallest ***WORKS***
    @RequestMapping("/CountriesWorld")
    public ArrayList<Country> getCountriesWorld() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Code, Name, Continent, Region, Population "
                            + "FROM country "
                            + "ORDER BY Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Country> countryArray = new ArrayList<>();
            while (rset.next()) {
                Country country = new Country();
                country.CountryCode = rset.getString("Code");
                country.CountryName = rset.getString("Name");
                country.Population = rset.getInt("Population");
                country.Continent = rset.getString("Continent");
                country.Region = rset.getString("Region");
                countryArray.add(country);
            }
            return countryArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To Print Countries from world");
            return null;
        }
    }

    public ArrayList<Country> getTheCountriesWorld(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population "
                            + "FROM country "
                            + "ORDER BY country.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countryArray = new ArrayList<>();
            while (rset.next()) {
                Country country = new Country();
                country.CountryCode = rset.getString("country.Code");
                country.CountryName = rset.getString("country.Name");
                country.Continent = rset.getString("country.Continent");
                country.Region = rset.getString("country.Region");
                country.Population = rset.getInt("country.Population");
                countryArray.add(country);
            }
            return countryArray;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public void displayCountriesWorld(ArrayList<Country> countryArray) {
        // Check the country arraylist is not null
        if (countryArray == null) {
            System.out.println("No countries");
            return;
        }
        // Print header
        System.out.println(String.format("%-15s %-20s %-15s %-20s %-15s", "Country Code", "Name", "Continent", "Region", "Population"));
        // Loop over all countries in the list
        for (Country country : countryArray) {
            String country_string =
                    String.format("%-15s %-20s %-15s %-20s %-15s",
                            country.CountryCode, country.CountryName, country.Continent, country.Region, country.Population);
            System.out.println(country_string);
        }
        System.out.println("\n");
    }


    //2 - See all counties in Continent from largest population to smallest ****DOESN'T WORK****
    @RequestMapping("/CountriesContinent")
    public ArrayList<Country> getCountriesContinent() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Code, Name, Continent, Region, Population"
                    + "FROM country "
                    + "WHERE country.Continent='Europe'"
                    + "ORDER BY country.Population DESC";
                   /*"SELECT country.Code, country.Name, country.Continent, country.Region, country.Population"
                            + "FROM country "
                            + "WHERE country.Continent='Europe'"
                            + "ORDER BY country.Population DESC"; */

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Country> countryArray = new ArrayList<>();
            while (rset.next()) {
                Country country = new Country();
                //country.CountryCode = rset.getString("Code");
                country.CountryName = rset.getString("Name");
                country.Continent = rset.getString("Continent");
                country.Population = rset.getInt("Population");
                country.Continent = rset.getString("Continent");
                country.Region = rset.getString("Region");
                countryArray.add(country);
            }
            return countryArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To Print Countries from world");
            return null;
        }
    }

    public ArrayList<Country> getTheCountriesContinent(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population"
                    + "FROM country "
                    + "WHERE country.Continent='Europe'"
                    + "ORDER BY country.Population DESC";
                   /*"SELECT Code, Name, Continent, Region, Population"
                            + "FROM country "
                            + "WHERE country.Continent='Europe'"
                            + "ORDER BY country.Population DESC"; */
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countryArray = new ArrayList<>();
            while (rset.next()) {
                Country country = new Country();
                country.CountryCode = rset.getString("country.Code");
                country.CountryName = rset.getString("country.Name");
                country.Continent = rset.getString("country.Continent");
                country.Region = rset.getString("country.Region");
                country.Population = rset.getInt("country.Population");
                countryArray.add(country);
            }
            return countryArray;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public void displayCountriesContinent(ArrayList<Country> countryArray) {
        // Check the country arraylist is not null
        if (countryArray == null) {
            System.out.println("No countries");
            return;
        }
        // Print header
        System.out.println(String.format("%-15s %-20s %-15s %-20s %-15s", "Country Code", "Name", "Continent", "Region", "Population"));
        // Loop over all countries in the list
        for (Country country : countryArray) {
            String country_string =
                    String.format("%-15s %-20s %-15s %-20s %-15s",
                            country.CountryCode, country.CountryName, country.Continent, country.Region, country.Population);
            System.out.println(country_string);
        }
        System.out.println("\n");
    }

//7 - See all cites in world ***WORKS***

    @RequestMapping("/CitiesWorld")
    public ArrayList<City> getCitiesWorld() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT city.ID, city.Name, city.District, city.Population "
                            + "FROM city "
                            + "ORDER BY city.Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("ID");
                city.CityName = rset.getString("Name");
                city.District = rset.getString("District");
                city.population = rset.getInt("Population");
                city.CountryName = rset.getString("Name");

                cityArray.add(city);
            }
            return cityArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To Print city from world");
            return null;
        }
    }

    public ArrayList<City> getTheCitiesWorld(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city "
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("ID");
                city.CityName = rset.getString("Name");
                city.District = rset.getString("District");
                city.population = rset.getInt("Population");
                city.CountryCode = rset.getString("Code");
                city.CountryName = rset.getString("Name");

                cityArray.add(city);
            }
            return cityArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public void displayCitiesWorld(ArrayList<City> cityArray) {
        // Check the country arraylist is not null
        if (cityArray == null) {
            System.out.println("No cities");
            return;
        }
        // Print header
        System.out.println(String.format("%-15s %-20s %-15s %-20s %-15s", "Name", "Country", "District", "Population"));
        // Loop over all countries in the list
        for (City city : cityArray) {
            String country_string =
                    String.format("%-20s %-20s %-15s %-15s",
                            city.CityName, city.CountryName, city.District, city.population);
            System.out.println(country_string);
        }
        System.out.println("\n");
    }


    //8- See all cites in Continent ***WORKS***
    @RequestMapping("/CitesContinent")
    public ArrayList<City> getCitiesContinent() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT city.ID, city.Name, city.District, city.Population "
                            + "FROM city JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.continent='Europe'"
                            + "ORDER BY city.Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("ID");
                city.CityName = rset.getString("Name");
                city.District = rset.getString("District");
                city.population = rset.getInt("Population");
                city.CountryName = rset.getString("Name");


                cityArray.add(city);
            }
            return cityArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To Print city from world");
            return null;
        }
    }

    public ArrayList<City> getTheCitiesContinent(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.continent='Europe'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("ID");
                city.CityName = rset.getString("Name");
                city.District = rset.getString("District");
                city.population = rset.getInt("Population");
                city.CountryCode = rset.getString("Code");
                city.CountryName = rset.getString("Name");

                cityArray.add(city);
            }
            return cityArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public void displayCitiesContinent(ArrayList<City> cityArray) {
        // Check the country arraylist is not null
        if (cityArray == null) {
            System.out.println("No cities");
            return;
        }
        // Print header
        System.out.println(String.format("%-15s %-20s %-15s %-20s %-15s", "Name", "Country", "District", "Population"));
        // Loop over all countries in the list
        for (City city : cityArray) {
            String country_string =
                    String.format("%-20s %-20s %-15s %-15s",
                            city.CityName, city.CountryName, city.District, city.population);
            System.out.println(country_string);
        }
        System.out.println("\n");
    }


    //9 - See all cites in Region ***WORKS***

    @RequestMapping("/CitesRegion")
    public ArrayList<City> getCitiesRegion() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT city.ID, city.Name, city.District, city.Population "
                            + "FROM city JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.region='Eastern Europe'"
                            + "ORDER BY city.Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("ID");
                city.CityName = rset.getString("Name");
                city.District = rset.getString("District");
                city.population = rset.getInt("Population");
                city.CountryName = rset.getString("Name");

                cityArray.add(city);
            }
            return cityArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To Print city from world");
            return null;
        }
    }

    public ArrayList<City> getTheCitiesRegion(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.region='Eastern Europe'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("ID");
                city.CityName = rset.getString("Name");
                city.District = rset.getString("District");
                city.population = rset.getInt("Population");
                city.CountryCode = rset.getString("Code");
                city.CountryName = rset.getString("Name");

                cityArray.add(city);
            }
            return cityArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public void displayCitiesRegion(ArrayList<City> cityArray) {
        // Check the country arraylist is not null
        if (cityArray == null) {
            System.out.println("No cities");
            return;
        }
        // Print header
        System.out.println(String.format("%-15s %-20s %-15s %-20s %-15s", "Name", "Country", "District", "Population"));
        // Loop over all countries in the list
        for (City city : cityArray) {
            String country_string =
                    String.format("%-20s %-20s %-15s %-15s",
                            city.CityName, city.CountryName, city.District, city.population);
            System.out.println(country_string);
        }
        System.out.println("\n");
    }



    ///10 - See cities in Country ***WORKS***
    @RequestMapping("/countryCities")
    public ArrayList<City> getCountryCities() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.name='United Kingdom'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("city.ID");
                city.CityName = rset.getString("city.Name");
                city.CountryCode = rset.getString("city.CountryCode");
                city.District = rset.getString("city.District");
                city.population = rset.getInt("city.Population");
                cityArray.add(city);
            }
            return cityArray;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<City> getTheCitiesCountry() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city JOIN country ON city.CountryCode = country.Code "
                            + "WHERE country.name='United Kingdom'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("city.ID");
                city.CityName = rset.getString("city.Name");
                city.CountryCode = rset.getString("city.CountryCode");
                city.District = rset.getString("city.District");
                city.population = rset.getInt("city.Population");
                cityArray.add(city);
            }
            return cityArray;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    // Displays cities in a country
    public void displayCountryCities(ArrayList<City> cityArray) {
        // Check the city arraylist is not null
        if (cityArray == null) {
            System.out.println("No cities");
            return;
        }
        // Print header
        System.out.println(String.format("%-20s %-20s %-15s %-15s", "Name", "Country", "District", "Population"));
        // Loop over all countries in the list
        for (City city : cityArray) {
            String country_string =
                    String.format("%-20s %-20s %-15s %-15s",
                            city.CityName, city.CountryName, city.District, city.population);
            System.out.println(country_string);
        }
        System.out.println("\n");
    }

    ///12 - See cities in District ***WORKS***
    @RequestMapping("/CitiesDistrict")
    public ArrayList<City> getCitiesDistrict() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city JOIN country ON city.CountryCode = country.Code "
                            + "WHERE city.District='Wales'"
                            + "ORDER BY city.Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("city.ID");
                city.CityName = rset.getString("city.Name");
                city.CountryCode = rset.getString("city.CountryCode");
                city.District = rset.getString("city.District");
                city.population = rset.getInt("city.Population");
                cityArray.add(city);
            }
            return cityArray;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<City> getTheCitiesDistrict() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                            + "FROM city JOIN country ON city.CountryCode = country.Code "
                            + "WHERE city.District='Wales'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<City> cityArray = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.CityID = rset.getInt("city.ID");
                city.CityName = rset.getString("city.Name");
                city.CountryCode = rset.getString("city.CountryCode");
                city.District = rset.getString("city.District");
                city.population = rset.getInt("city.Population");
                cityArray.add(city);
            }
            return cityArray;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    // Displays cities in a country
    public void displayDistrictCities(ArrayList<City> cityArray) {
        // Check the city arraylist is not null
        if (cityArray == null) {
            System.out.println("No cities");
            return;
        }
        // Print header
        System.out.println(String.format("%-20s %-20s %-15s %-15s", "Name", "Country", "District", "Population"));
        // Loop over all countries in the list
        for (City city : cityArray) {
            String country_string =
                    String.format("%-20s %-20s %-15s %-15s",
                            city.CityName, city.CountryName, city.District, city.population);
            System.out.println(country_string);
        }
        System.out.println("\n");
    }

    //26 - See population of the world ***WORKS***
    @RequestMapping("/PopulationWorld")
    public ArrayList<World> getPoulationWorld(/*@RequestParam(value = "name") String Name*/) {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT SUM(country.Population) "
                            + "FROM country ";


            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<World> worldArray = new ArrayList<>();
            while (rset.next()) {
                World worldPopulation = new World();
                worldPopulation.population = rset.getLong("SUM(country.Population)");
                worldArray.add(worldPopulation);
            }
            return worldArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To get population ");
            return null;
        }
    }

    public World getThePopulationWorld(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT SUM(country.Population) "
                            + "FROM country ";


            ResultSet rset = stmt.executeQuery(strSelect);


            while (rset.next()) {

                World world = new World();
                world.population = rset.getLong("SUM(country.Population)");
                return world;
            }
            return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public void displayCountriesWorld(World world) {
        if (world != null) {
            System.out.println("World " + world.population);
        }
        else {

            System.out.println("No population in world");
        }
    }



    //27 - See population of the continent ***WORKS***
    @RequestMapping("/PopulationContinent")
    public ArrayList<Continent> getPopulationOfContinent(/*@RequestParam(value = "continent") String continent*/) {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT country.Continent, SUM(country.Population)/*/SUM(city.Population))*/,(SUM(city.Population)/SUM(country.Population))*100," +
                            " SUM(country.Population)-SUM(city.Population), ((SUM(country.Population)-SUM(city.Population))/SUM(country.Population))*100 "
                            + "FROM country JOIN city ON country.Code = city.CountryCode "
                            + "WHERE country.Continent='Europe'"
                            + "GROUP BY country.Continent";


            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Continent> continentArray = new ArrayList<>();

            while (rset.next()) {
                Continent continentPopulation = new Continent();
                continentPopulation.Name = rset.getString("country.Continent");
                continentPopulation.population = rset.getLong("SUM(country.Population)");
               // continentPopulation.CityPopulation = rset.getLong("SUM(city.Population)");
                continentPopulation.CityPopulationPercent = rset.getFloat("(SUM(city.Population)/SUM(country.Population))*100");
                continentPopulation.NotCityPopulation = rset.getLong("SUM(country.Population)-SUM(city.Population)");
                continentPopulation.NotCityPopulationPercent = rset.getFloat("((SUM(country.Population)-SUM(city.Population))/SUM(country.Population))*100");
                continentArray.add(continentPopulation);
            }
            return continentArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To get population ");
            return null;
        }
    }

    public Continent getThePopulationContinent(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Continent, SUM(Population) "
                            + "FROM country "
                            + "WHERE country.Continent='Europe'"
                            + "GROUP BY country.Continent";


            ResultSet rset = stmt.executeQuery(strSelect);


            if (rset.next()) {

                Continent continent = new Continent();
                continent.Name = rset.getString("country.Continent");
                continent.population = rset.getLong("SUM(Population)");
                return continent;
            } else
                return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public void displayPopulationContinent (Continent continent) {
        if (continent != null) {
            System.out.println(continent.Name + " " + continent.population);
        }
        else {

            System.out.println("No population in continent");
        }
    }

    //28 - See population of the Region ***WORKS***
    @RequestMapping("/PopulationRegion")
    public ArrayList<Region> getPoulationRegion() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT country.Region, SUM(country.Population), SUM(city.Population), (SUM(city.Population)/SUM(country.Population))*100, SUM(country.Population)-SUM(city.Population), ((SUM(country.Population)-SUM(city.Population))/SUM(country.Population))*100 "
                            + "FROM country JOIN city ON country.Code = city.CountryCode "
                            + "WHERE country.Region='Eastern Europe'"
                            + "GROUP BY country.Region";


            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Region> regionArray = new ArrayList<>();

            while (rset.next()) {
                Region populationRegion = new Region();
                populationRegion.Name = rset.getString("country.Region");
                populationRegion.Population = rset.getLong("SUM(country.Population)");
                populationRegion.CityPopulation = rset.getLong("SUM(city.Population)");
                populationRegion.CityPopulationPercent = rset.getFloat("(SUM(city.Population)/SUM(country.Population))*100");
                populationRegion.NotCityPopulation = rset.getLong("SUM(country.Population)-SUM(city.Population)");
                populationRegion.NotCityPopulationPercent = rset.getFloat("((SUM(country.Population)-SUM(city.Population))/SUM(country.Population))*100");
                regionArray.add(populationRegion);
            }
            return regionArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To get population ");
            return null;
        }
    }

    public Region getThePopulationRegion(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Continent, SUM(Population) "
                            + "FROM country "
                            + "WHERE country.Continent='Eastern Europe'"
                            + "GROUP BY country.Continent";

            ResultSet rset = stmt.executeQuery(strSelect);


            if (rset.next()) {

                Region region = new Region();
                region.Name = rset.getString("country.Continent");
                region.Population = rset.getLong("SUM(Population)");
                return region;
            } else
                return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public void displayPopulationRegion (Region region) {
        if (region != null) {
            System.out.println(region.Name + " " + region.Population);
        }
        else {

            System.out.println("No population in continent");
        }
    }



    //29 - See population of the country ***WORKS***
    @RequestMapping("/PopulationCountry")
    public ArrayList<Country> getPoulationCountry() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "WHERE country.name='United Kingdom'";

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Country> countryArray = new ArrayList<>();

            while (rset.next()) {
                Country populationCountry = new Country();
                populationCountry.CountryName = rset.getString("country.Region");
                populationCountry.Population = rset.getInt("country.Population");
                countryArray.add(populationCountry);
            }
            return countryArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To get population ");
            return null;
        }
    }

    public void displayPopulationCountry (Country country) {
        if (country != null) {
            System.out.println(country.CountryName + " " + country.Population);
        }
        else {

            System.out.println("No population in continent");
        }
    }

    //30 - See population of a district ***WORKS***
    @RequestMapping("/PopulationDistrict")
    public ArrayList<District> getPoulationDistrict() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT District, SUM(Population) "
                            + "FROM city "
                            + "WHERE city.District='Wales'";

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<District> districtArray = new ArrayList<>();

            while (rset.next()) {
                District populationDistrict = new District();
                populationDistrict.District = rset.getString("District");
                populationDistrict.population = rset.getInt("SUM(population)");
                districtArray.add(populationDistrict);
            }
            return districtArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To get population ");
            return null;
        }
    }

    public void displayPopulationDistrict (District district) {
        if (district != null) {
            System.out.println(district.District + " " + district.population);
        }
        else {

            System.out.println("No population in District");
        }
    }


    //31 - See population of a city ***WORKS***
    @RequestMapping("/PopulationCity")
    public ArrayList<City> getPoulationCity() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT city.Name, Population "
                            + "FROM city "
                            + "WHERE city.Name='Edinburgh'";

            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<City> cityArray = new ArrayList<>();

            while (rset.next()) {
                City populationCity = new City();
                populationCity.CityName = rset.getString("city.Name");
                populationCity.population = rset.getInt("population");
                cityArray.add(populationCity);
            }
            return cityArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To get population ");
            return null;
        }
    }

    public void displayPopulationCity(City city) {
        if (city != null) {
            System.out.println(city.CityName + " " + city.population);
        }
        else {

            System.out.println("No population in City");
        }
    }



    }

