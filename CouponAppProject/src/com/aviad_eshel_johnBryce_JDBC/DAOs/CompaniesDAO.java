package com.aviad_eshel_johnBryce_JDBC.DAOs;

import com.aviad_eshel_johnBryce_JDBC.Models.Company;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {
    public boolean isCompanyExists(String email, String password) throws SQLException;
    public void addCompany (Company company) throws SQLException ;
    public void updateCompany (Company company) throws SQLException ;
    public void deleteCompany (int companyID) throws SQLException;
    public ArrayList<Company> getAllCompanies () throws SQLException;
    public Company getOneCompany (int companyID) throws SQLException;


}
