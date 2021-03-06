package com.aviad_eshel_johnBryce_JDBC.DAOs;

import com.aviad_eshel_johnBryce_JDBC.Models.Coupon;
import com.aviad_eshel_johnBryce_JDBC.exceptions.DBExceptions;
import com.aviad_eshel_johnBryce_JDBC.exceptions.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {
    public void addCoupon (Coupon coupon) throws SQLException, DBExceptions,DaoException;
    public void updateCoupon (Coupon coupon) throws SQLException ,DBExceptions,DaoException;
    public void deleteCuopon (Coupon coupon) throws SQLException;
    public ArrayList<Coupon> getAllCoupons() throws SQLException;
    public Coupon getOneCoupon (int couponID) throws SQLException;
    public void addCuoponPurchase (int customerID, int couponID) throws SQLException;
    public void deleteCouponPurchase (int customerID, int couponID) throws SQLException;

}
