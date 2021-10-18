package com.aviad_eshel_johnBryce_JDBC.DAOs;
import com.aviad_eshel_johnBryce_JDBC.Infastucture.ConnectionPool;
import com.aviad_eshel_johnBryce_JDBC.Models.Company;

import java.sql.*;
import java.util.ArrayList;


import java.sql.*;
import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {
    private ConnectionPool connectionPool;
    private static final String dbName = "sql11437103";
    private static final String userName = "sql11437103";
    private static final String password = "NLxRdvgRp9";
    private static final String connectionString = "jdbc:mysql://sql11.freemysqlhosting.net/"
            + dbName +
            "?user=" + userName +
            "&password=" + password;

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException {
        Company company = new Company(email, password);
//        for (Company c : getAllCompanies()){
//            if (c.equals(company)){
        if (getAllCompanies().contains(company)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addCompany(Company company) throws SQLException {
        if (isCompanyExists(company.getEmail(), company.getPassword())) {
            System.out.println("company already exists");
        } else {
            // to send the info to the DB
                System.out.println("Connecting to db "+dbName);
            //   Connection connection = DriverManager.getConnection(connectionString);
                System.out.println("Connected to DB "+dbName);
                System.out.println("Inserting data id="+company.getId());
                String sql = "insert into companies (  name,email,password ) values ("+company.getName()+"," +
                        " "+company.getEmail()+"" + ", "+company.getPassword()+")";
                PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
                preparedStatement.execute();
                System.out.println("new company added to DB");
                connectionPool.killConnection();
        }

    }

    @Override
    public void updateCompany(Company company) throws SQLException {
        System.out.println("Connecting to db "+dbName);
      //  Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);
        String sql = "update companies SET `name` = 'pizza_paza' WHERE (`id` = '1');" +
                " = "+company.getName()+", name='"+company.getName()+"' where id="+company.getId();
        PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
        preparedStatement.executeUpdate();
        System.out.println("Record with id "+company.getId()+" was updated");
        connectionPool.killConnection();
    }

    @Override
    public void deleteCompany(int companyID) throws SQLException{
        System.out.println("Connecting to db "+dbName);
       // Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);
        String sql = "delete from company where id = "+companyID;
        PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
        preparedStatement.executeUpdate();
        System.out.println("Record with id "+companyID +" was deleted");

    }

    @Override
    public ArrayList<Company> getAllCompanies() throws SQLException{
        ArrayList<Company> companies = new ArrayList<>();
        System.out.println("Connecting to db "+dbName);

        System.out.println("Connected to DB "+dbName);


        String sql = "select id, name, email, password from companies";
        //PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
        Statement statement = connectionPool.getConnection().createStatement();
        ResultSet results = statement.executeQuery(sql);
        while(results.next()) //Iteration over each row / record
        {
            int id = results.getInt("id");
            String name = results.getString("name");
            String email = results.getString("email");
            String password = results.getString( "password");
            System.out.println("id : "+id+"Name : "+name+", email: "+email+", password:"+password);
        }

        return companies;
    }

    @Override
    public Company getOneCompany(int companyID) throws SQLException {
        String name=null;
        String email=null;
        String password=null;
        Company comp = new Company(name,email,password) ;
        System.out.println("Connecting to db "+dbName);
       // Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);
        Statement statement = connectionPool.getConnection().createStatement();
        String sql = "select id, name, email, password from companies where id="+companyID;

        ResultSet results = statement.executeQuery(sql);
       // while(results.next()) //Iteration over each row / record
       // {
            int id = results.getInt("id");
             name = results.getString("name");
             email = results.getString("email");
             password = results.getString( "password");
            System.out.println("id : "+id+"Name : "+name+", email: "+email+", password:"+password);
            comp = new Company(name,email,password);

       // }
        if (isCompanyExists(email,password)){
            System.out.println("Query executed successfully");
            return comp;

        }
        else{
            System.out.println("no such company in DB");
            return null;}





    }

    public static void insert(int id, String name, String email, String password) throws SQLException
    {
        System.out.println("Connecting to db "+dbName);
        Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);
        System.out.println("Inserting data id="+id);
        String sql = "insert into companies ( id, name,email,password ) values ('"+id+"', "+name+", "+email+", "+password+")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.execute();

        System.out.println("Data inserted ok");

    }

    public static void create1(int id, String name, String email, String password) throws SQLException
    {
        System.out.println("Connecting to db "+dbName);
        Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);
        System.out.println("Inserting data id="+id);
        String sql = "insert into companies ( id, name,email,password ) values ('"+id+"', "+name+", "+email+", "+password+")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        int ret = preparedStatement.executeUpdate();

        //gets the generated ids
        ResultSet results = preparedStatement.getGeneratedKeys();
        while(results.next())
        {
            int newId = results.getInt(1);
            System.out.println("Record added with id "+newId);
        }

        System.out.println("Data inserted ok, ret = "+ret );
    }

    public static void readAll() throws SQLException
    {
        System.out.println("Connecting to db "+dbName);
        Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);

        Statement statement = connection.createStatement();
        String sql = "select id, name, email, password from companies";

        ResultSet results = statement.executeQuery(sql);
        while(results.next()) //Iteration over each row / record
        {
            int id = results.getInt("id");
            String name = results.getString("name");
            int salary = results.getInt("salary");
            System.out.println("Name : "+name+", Salary: "+salary+", ID:"+id);
        }
    }

    public static void readById(int id) throws SQLException
    {
        System.out.println("Connecting to db "+dbName);
        Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);

        Statement statement = connection.createStatement();
        String sql = "select id, salary, name from workers where id="+id;

        ResultSet results = statement.executeQuery(sql);
        while(results.next()) //Iteration over each row / record
        {
            //int id = results.getInt("id");
            String name = results.getString("name");
            int salary = results.getInt("salary");
            System.out.println("Name : "+name+", Salary: "+salary+", ID:"+id);
        }

        System.out.println("Query executed successfully");
    }

    public static void update(int id, String name, int salary) throws SQLException
    {
        System.out.println("Connecting to db "+dbName);
        Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);
        String sql = "update workers set salary = "+salary+", name='"+name+"' where id="+id;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        System.out.println("Record with id "+id+" was updated");
    }

    public static void delete(int id) throws SQLException
    {
        System.out.println("Connecting to db "+dbName);
        Connection connection = DriverManager.getConnection(connectionString);
        System.out.println("Connected to DB "+dbName);
        String sql = "delete from workers where id = "+id;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        System.out.println("Record with id "+id +" was deleted");
    }


}
 String fff= "SELECT * FROM example.workers";