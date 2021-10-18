package com.aviad_eshel_johnBryce_JDBC.DAOs;

import com.aviad_eshel_johnBryce_JDBC.Models.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {
    public boolean isCustomerExists (String email, String password) throws SQLException ;
    public void addCustomer (Customer customer) throws SQLException ;
    public void updateCustomer(Customer customer) throws SQLException ;
    public void deleteCustomer (int customerID) throws SQLException ;
    public ArrayList<Customer> getAllCustomer () throws SQLException ;
    public Customer getOneCustomers (int customerID) throws SQLException ;
}
