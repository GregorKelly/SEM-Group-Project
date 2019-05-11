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
                country.population = rset.getInt("Population");
                country.Continent = rset.getString("Continent");
                country.region = rset.getString("Region");
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
                country.region = rset.getString("country.Region");
                country.population = rset.getInt("country.Population");
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
                            country.CountryCode, country.CountryName, country.Continent, country.region, country.population);
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
                country.population = rset.getInt("Population");
                country.Continent = rset.getString("Continent");
                country.region = rset.getString("Region");
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
                country.region = rset.getString("country.Region");
                country.population = rset.getInt("country.Population");
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
                            country.CountryCode, country.CountryName, country.Continent, country.region, country.population);
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
                            + "WHERE country.name IS Scotland"
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

    ///12 - See cities in District
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



    //27 - See population of the continent ***DOESNT WORK***
    @RequestMapping("/PopulationContinent")
    public ArrayList<Continent> getPoulationContinent(/*@RequestParam(value = "continent") String continent*/) {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT country.Continent, SUM(country.Population)/*/SUM(city.Population))*/,(SUM(city.Population)/SUM(country.Population))*100," +
                            " SUM(country.Population)-SUM(city.Population), ((SUM(country.Population)-SUM(city.Population))/SUM(country.Population))*100 "
                            + "FROM country JOIN city ON country.Code = city.CountryCode "
                            + "WHERE country.Continent LIKE '" + "Europe" + "' "
                            + "GROUP BY country.Continent";


            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Continent> continentArray = new ArrayList<>();

            while (rset.next()) {
                Continent continentPopulation = new Continent();
                continentPopulation.Name = rset.getString("country.Continent");
                continentPopulation.population = rset.getInt("SUM(country.Population)");
                continentPopulation.CityPopulation = rset.getInt("SUM(city.Population)");
                continentPopulation.CityPopulationPercent = rset.getFloat("(SUM(city.Population)/SUM(country.Population))*100");
                continentPopulation.NotCityPopulation = rset.getInt("SUM(country.Population)-SUM(city.Population)");
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

    @RequestMapping("/PopulationPercentContinent")
    public ArrayList<Continent> getPoulationPercentContinent() {

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT country.Continent, SUM(city.Population)"
                            + "FROM country JOIN city ON country.Code = city.CountryCode "
                            + "GROUP BY country.Continent";


            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Continent> continentArray = new ArrayList<>();

            List<String> continents = new ArrayList<>();
            continents.add("Asia");
            continents.add("Europe");
            continents.add("North America");
            continents.add("Africa");
            continents.add("Oceania");
            continents.add("South America");

            int i = 0;

            while (rset.next()) {

                Continent continentPopulation = new Continent();
                continentPopulation.Name = rset.getString("country.Continent");
                continentPopulation.CityPopulation = rset.getLong("SUM(city.Population)");
                continentPopulation.CityPopulationPercent = rset.getFloat("(SUM(city.Population)/SUM(country.Population))*100");
                continentPopulation.NotCityPopulation = rset.getLong("SUM(country.Population)-SUM(city.Population)");
                continentPopulation.NotCityPopulationPercent = rset.getFloat("((SUM(country.Population)-SUM(city.Population))/SUM(country.Population))*100");
                continentArray.add(continentPopulation);
                i++;

            }
            return continentArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed To get population ");
            return null;
        }
    }

    public ArrayList<Continent> getContinentPercentPopulation(){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Continent, SUM(city.Population)"
                            + "FROM country JOIN city ON country.Code = city.CountryCode "
                            + "GROUP BY country.Continent";


            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Continent> continentArray = new ArrayList<>();

            List<String> continents = new ArrayList<>();
            continents.add("Asia");
            continents.add("Europe");
            continents.add("North America");
            continents.add("Africa");
            continents.add("Oceania");
            continents.add("South America");

            int i = 0;

            while (rset.next()) {

                Continent continentPopulation = new Continent();
                continentPopulation.Name = rset.getString("country.Continent");
                continentPopulation.population = rset.getInt("SUM(country.Population)");
                continentPopulation.CityPopulation = rset.getInt("SUM(city.Population)");
                continentPopulation.CityPopulationPercent = rset.getFloat("(SUM(city.Population)/SUM(country.Population))*100");
                continentPopulation.NotCityPopulation = rset.getInt("SUM(country.Population)-SUM(city.Population)");
                continentPopulation.NotCityPopulationPercent = rset.getFloat("((SUM(country.Population)-SUM(city.Population))/SUM(country.Population))*100");
                continentArray.add(continentPopulation);
                i++;
            }
                return continentArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    public void displayPopulationPercentContinent (ArrayList<Continent> continentArray) {
        if (continentArray == null) {
            System.out.println("No countries found");
            return;
        }

        System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s %-20s", "Name", "Population", "City Population", "City Population %", "Rural Population", "Rural Population %"));
        // Loop over all countries in the list
        for (Continent continent : continentArray) {
            String country_string =
                    String.format("%-20s %-20s %-20s %-20s %-20s %-20s",
                            continent.Name, continent.population, continent.CityPopulation, continent.CityPopulationPercent, continent.NotCityPopulation, continent.NotCityPopulationPercent);
            System.out.println(country_string);
        }
        System.out.println("\n");
    }



    public Continent getThePopulationContinent(/*String name*/){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Continent, SUM(Population) "
                            + "FROM country "
                            +"WHERE country.Continent  LIKE '" + "Europe" + "' "
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

   /* public List GetCountriesCont(String continentIn)
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
    /*
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

    public List GetCountriesWorldReg(String continentIn)
    {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Name "
                            + "FROM country " + "'" + continentIn + "' "
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
*/
    /*public List GetCityCont(String cityCont)
    {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT Name "
                            + "FROM city "
                            + "WHERE continent = " + "'" + cityCont + "'"
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
            System.out.println("Failed To Print Cities from Continent");
            return null;
        }
    }*/

    }

