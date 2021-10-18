package com.aviad_eshel_johnBryce_JDBC.DAOs;

import com.aviad_eshel_johnBryce_JDBC.Infastucture.ConnectionPool;
import com.aviad_eshel_johnBryce_JDBC.Models.Category;
import com.aviad_eshel_johnBryce_JDBC.Models.Coupon;
import com.aviad_eshel_johnBryce_JDBC.Models.Customer;
import com.aviad_eshel_johnBryce_JDBC.exceptions.DBExceptions;
import com.aviad_eshel_johnBryce_JDBC.exceptions.DaoException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO{
	
    ConnectionPool connectionPool;
    private static final String dbName = "sql11437103";
	private static final String userName = "sql11437103";
	private static final String password = "NLxRdvgRp9";
	private static final String connectionString = "jdbc:mysql://sql11.freemysqlhosting.net/" + dbName + "?user="
			+ userName + "&password=" + password;

    @Override
    public void addCoupon(Coupon coupon) throws SQLException, DBExceptions,DaoException {

    	Connection con = ConnectionPool.getInstance().getConnection();
    		
			// to send the info to the DB
			System.out.println("Connecting to db " + dbName);

			System.out.println("Inserting data to coupon=" + coupon.getTitle());
			//TODO check category_id
			try {
			String sql = "INSERT INTO sql11437103.coupons +"
					+ "(company_id, category_id, title, discription, start_date, end_date, amount, price, image) +"
					+ "VALUES ('"+coupon.getCompanyId()+"','"+coupon.getCategory()+"', '"+coupon.getTitle()+"',"
					+ " '"+coupon.getDescription()+"', '"+coupon.getStartDate()+"', '"+coupon.getEndDate()+"',"
					+ " '"+coupon.getAmount()+"', '"+coupon.getPrice()+"', '"+coupon.getImage()+"')";
			
			
			PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.execute();
			//get back the id
			ResultSet rsKey = preparedStatement.getGeneratedKeys();
			rsKey.next();
			coupon.setId(rsKey.getInt("id"));
	
			} catch (SQLException e) {
				throw new DaoException("adding cupon failed ", e);
			}finally {	// TODO: handle exception
				ConnectionPool.getInstance().restoreConnection(con);
			System.out.println("new coupon added to DB");
			
		}
	}
	
    

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException, DBExceptions {

    	Connection con = ConnectionPool.getInstance().getConnection();
		System.out.println("Connecting to db " + dbName);

		// TODO-correct sql quarey
		String sql = "update customer set sql11437103.coupons"
				+ " (company_id, category_id, title, discription, start_date, end_date, amount, price, image)"
				+ "  VALUES ('"+coupon.getCompanyId()+"','"+coupon.getCategory()+"', '"+coupon.getTitle()+"', "
						+ "'"+coupon.getDescription()+"', '"+coupon.getStartDate()+"', '"+coupon.getEndDate()+"',"
								+ "  '"+coupon.getAmount()+"', '"+coupon.getPrice()+"', '"+coupon.getImage()+"')";
		PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
		preparedStatement.executeUpdate();
		System.out.println("Record with description " + coupon.getDescription() + " was updated");
		connectionPool.restoreConnection(connectionPool.getConnection());

	
    }

    @Override
    public void deleteCuopon(Coupon coupon) throws SQLException {


		System.out.println("Connected to DB " + dbName);
		String sql = "delete from customers where id = " + coupon.getId();
		PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
		preparedStatement.executeUpdate();
		System.out.println("Record with id " + coupon.getId() + " was deleted");

	
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException {


		ArrayList<Coupon> coupons = new ArrayList<>();

		System.out.println("Connected to DB " + dbName);

		String sql = "select company_id, category_id, title, discription, start_date, end_date, amount, price, image)"
				+ " from coupons";

		Statement statement = connectionPool.getConnection().createStatement();
		ResultSet results = statement.executeQuery(sql);
		while (results.next()) // Iteration over each row / record
		{
			
		   //check category
			Coupon coupon = new Coupon();
			coupon.setCategory(Category.valueOf(results.getString("category")));
			
			
			coupon.setId(results.getInt("id"));
			
			coupon.setTitle(results.getString("title"));
			coupon.setDescription(results.getString("Description"));
			coupon.setStartDate(results.getDate("Start_Date"));
			coupon.setEndDate(results.getDate("end_Date"));
			coupon.setAmount(results.getInt("amount"));
			coupon.setPrice(results.getDouble("price"));
			coupon.setImage(results.getString("image"));
			coupon.setCompanyId(results.getInt("company_id"));
			coupons.add(coupon);
			
			System.out.println(coupon);
			System.out.println(coupon);
			
			
		}

		return coupons;
	
    }

    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException {
		
		     int id = (Integer) null;
		     int companyId= (Integer) null;
		     Category category= null;
		     String title= null;
		     String description= null;
		     Date startDate= null;
		     Date endDate= null;
		     int amount= (Integer) null;
		     Double price= null;
		     String image= null;
		
		Coupon coupon = new Coupon(id, companyId, category, title, description, amount, price, image);
		System.out.println("Connecting to db " + dbName);
		Statement statement = connectionPool.getConnection().createStatement();
    	String sql = "select company_id, category_id, title, discription, start_date, end_date, amount, price, image)"
				+ " from couponswhere id= "+couponID;

		ResultSet results = statement.executeQuery(sql);
//		 while(results.next()) //Iteration over each row / record
//		 {
		  //check category
		 id = results.getInt("id");
		 companyId = results.getInt("companyId");
		// category = results.getCategory("category");
		 title = results.getString("title");
		 description = results.getString("description");
		  startDate = results.getDate("startDate");
	      endDate = results.getDate("endDate");
		 amount = results.getInt("amount");
		 price = results.getDouble("price");
		 image = results.getString("image");
		
		
		System.out.println("id : " + id + "companyId : " + companyId + "category : " + category + ", title: "
				+ title + ", description:" + description+ ", startDate:" + startDate+ ", endDate:" + endDate
				+ ", amount:" + amount+ ", price:" + price+ ", image:" + image);
		coupon = new Coupon(id, companyId, category, title, description, amount, price, image);

		// }
		if (getAllCoupons().contains(id)) {
			System.out.println("Query executed successfully");
			return coupon;
		} 
		else 
			{
			System.out.println("no such customer in DB");
			
			return null;
			}

    }
     
    // i got Here
    @Override
    public void addCuoponPurchase(int customerID, int couponID) {

    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {

    }
}
