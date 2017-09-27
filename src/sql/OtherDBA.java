package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBUtil;
import database.JavaDatabaseConnection;
import models.Bill;
import models.Charge;

/**
 * Database access object for miscellaneous functions
 * @author Alex
 *
 */
public class OtherDBA {

	/**
	 * Loads a bill from the result set
	 * @param rs result set from the queried bill
	 * @return the java bill object
	 * @throws SQLException
	 */
	public Bill loadCharges(ResultSet rs) throws SQLException {
		Bill bill = new Bill(rs.getInt("customerID"), rs.getInt("hotelID"), 
				rs.getInt("roomNumber"));
		while (rs.next()) {
			Charge temp = new Charge(rs.getInt("roomNumber"), rs.getInt("hotelID"), 
					rs.getInt("customerID"), rs.getInt("staffID"));
			temp.setServiceType(rs.getInt("serviceType"));
			temp.setSvcCount(rs.getInt("serviceCounter"));
			temp.setAmount(rs.getInt("Amount"));
			bill.add(temp);
		}
		return bill; 
	}

	/**
	 * Method to get charges from from the database
	 * @param roomNumber Room number the charges are linked to
	 * @param customerID customer the charges are linked to
	 * @param checkInDate check in date the charges are linked to
	 * @return a bill object which contains a charge set and key information
	 * @throws SQLException
	 */
	public Bill getCharges(int roomNumber, long customerID, String checkInDate) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("SELECT * FROM charges WHERE roomNumber=? AND customerID=? AND checkInDate=?");
			ps.setInt(1, roomNumber);
			ps.setLong(2, customerID);
			ps.setString(3, checkInDate);

			final ResultSet results = ps.executeQuery();
			return loadCharges(results);

		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Get the current occupancy of the hotel
	 * @return the current occupancy
	 * @throws SQLException
	 */
	public int getOccupancy() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		int currentOccupancy = 0;
		try {
			conn = JavaDatabaseConnection.getConnection();
			ps = conn.prepareStatement("SELECT occupancy FROM room where availability=0");
			final ResultSet results = ps.executeQuery();
			
			while(results.next()) {
				currentOccupancy += results.getInt("occupancy");
			}
			return currentOccupancy;			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to calculate the percent of rooms occupied
	 * @return the percentage
	 * @throws SQLException
	 */
	public double getOccupancyPercentage() throws SQLException {
		double result = (double) getOccupancy() / getMaxOccupancy() * 100;
		result = Math.round (result * 100.0) / 100.0;  
		return result;
	}
	
	/**
	 * Get the maximum occupancy of a room
	 * @return the maximum occupancy
	 * @throws SQLException
	 */
	private int getMaxOccupancy() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		int maxOccupancy = 0;
		try {
			conn = JavaDatabaseConnection.getConnection();
			ps = conn.prepareStatement("SELECT occupancy FROM room");
			final ResultSet results = ps.executeQuery();
			
			while(results.next()) {
				maxOccupancy += results.getInt("occupancy");
			}
			return maxOccupancy;
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Validates that a hotel exists in the database
	 * @param id ID of the hotel to check
	 * @return whether or not the hotel exists
	 * @throws SQLException
	 */
	public boolean validateHotel(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM hotel WHERE hotelID=?");
			ps.setInt(1, id);
			
			final ResultSet results = ps.executeQuery();
			return results.next();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
