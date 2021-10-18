package com.aviad_eshel_johnBryce_JDBC.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.aviad_eshel_johnBryce_JDBC.Infastucture.ConnectionPool;

import com.aviad_eshel_johnBryce_JDBC.Models.Customer;

public class CustomerDBDAO implements CustomersDAO {
	ConnectionPool connectionPool;

	private static final String dbName = "sql11437103";
	private static final String userName = "sql11437103";
	private static final String password = "NLxRdvgRp9";
	private static final String connectionString = "jdbc:mysql://sql11.freemysqlhosting.net/" + dbName + "?user="
			+ userName + "&password=" + password;

	@Override
	public boolean isCustomerExists(String email, String password) throws SQLException {

		Customer customer = new Customer(email, password);
		if (getAllCustomer().contains(customer)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void addCustomer(Customer customer) throws SQLException {

		if (isCustomerExists(customer.getEmail(), customer.getPassword())) {
			
			System.out.println("company already exists");
			
		} else {
			// to send the info to the DB
			System.out.println("Connecting to db " + dbName);
			//we d'ont have customer id yet
			//System.out.println("Inserting data id=" + customer.getId());
			System.out.println("Inserting data to customer=" + customer.getFirstName()+" "+customer.getLastName());
			String sql = "insert into customers (  firsName,lastName,email,password ) values ("
					+ customer.getFirstName() + "," + customer.getLastName() + "," + " " + customer.getEmail() + ""
					+ ", " + customer.getPassword() + ")";
			PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
			preparedStatement.execute();
			System.out.println("new customer added to DB");
			connectionPool.killConnection();
		}

	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException {

		System.out.println("Connecting to db " + dbName);

		// todo-correct sql quarey
		String sql = "update customer set (  firsName,lastName,email,password ) values (" + customer.getFirstName()
				+ "," + customer.getLastName() + "," + " " + customer.getEmail() + "" + ", " + customer.getPassword()
				+ ")";
		PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
		preparedStatement.executeUpdate();
		System.out.println("Record with id " + customer.getId() + " was updated");
		connectionPool.killConnection();

	}

	@Override
	public void deleteCustomer(int customerID) throws SQLException {

		System.out.println("Connected to DB " + dbName);
		String sql = "delete from customers where id = " + customerID;
		PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
		preparedStatement.executeUpdate();
		System.out.println("Record with id " + customerID + " was deleted");

	}

	@Override
	public ArrayList<Customer> getAllCustomer() throws SQLException {

		ArrayList<Customer> customers = new ArrayList<>();

		System.out.println("Connected to DB " + dbName);

		String sql = "select id, name, email, password from customers";

		Statement statement = connectionPool.getConnection().createStatement();
		ResultSet results = statement.executeQuery(sql);
		while (results.next()) // Iteration over each row / record
		{
			int id = results.getInt("id");
			String firstName = results.getString("firstName");
			String lastName = results.getString("lastName");
			String email = results.getString("email");
			String password = results.getString("password");
			System.out.println("id : " + id + "firstName : " + firstName + "lastName : " + lastName + ", email: "
					+ email + ", password:" + password);
		}

		return customers;
	}

	@Override
	public Customer getOneCustomers(int customerID) throws SQLException {
		
		String firstName = null;
		String lastName = null;
		String email = null;
		String password = null;
		
		Customer customer = new Customer(firstName, lastName, email, password);
		System.out.println("Connecting to db " + dbName);
		Statement statement = connectionPool.getConnection().createStatement();
		String sql = "select id, firstName,lastName, email, password from customers where id=" + customerID;

		ResultSet results = statement.executeQuery(sql);
		// while(results.next()) //Iteration over each row / record
		// {
		int id = results.getInt("id");
		firstName = results.getString("firstName");
		lastName = results.getString("lastName");
		email = results.getString("email");
		password = results.getString("password");
		System.out.println("id : " + id + "firstName : " + firstName + "lastName : " + lastName + ", email: " + email
				+ ", password:" + password);
		customer = new Customer(firstName, lastName, email, password);

		// }
		if (isCustomerExists(email, password)) {
			System.out.println("Query executed successfully");
			return customer;
		} else {
			System.out.println("no such customer in DB");
			return null;
		}

	}

}
