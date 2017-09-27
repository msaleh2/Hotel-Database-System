package actions.get;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBUtil;
import database.JavaDatabaseConnection;
import models.Customer;
import sql.CustomerDBA;

/**
 * Class to handle I/O for retrieving a customer
 * @author Alex
 *
 */
public class GetCustomerServedBy {
	
	/**
	 * Method to get the room numbers that a staff has served from the database
	 * @param id ID of staff member to query for
	 * @return list of rooms which this staff has served
	 * @throws SQLException
	 */
	public static List<Integer> getRoomNumbersOfCustomerServedBy(CustomerDBA dba, int id) {
		try {
			return dba.getRoomNumbersOfCustomerServedBy(id);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetCustomerServedBy.java");
			return null;
		}
	}
	
	/**
	 * Method to get the customer ID that a of a specific room from the databse
	 * @param roomNumber room number to query for
	 * @return the ID associated with the room
	 * @throws SQLException
	 */
	public static int getCustomerIDFromRoomNumber(CustomerDBA dba, int roomNumber) {
		try {
			return dba.getCustomerIDFromRoomNumber(roomNumber);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetCustomerServedBy.java");
			return -1;
		}
	}
	
	/**
	 * Method to get the customer name that is associated with the ID from the databse
	 * @param id customer ID to query for
	 * @return the name associated with the customer ID
	 * @throws SQLException
	 */
	public static String getCustomerNameFromID(CustomerDBA dba, int id) {
		try {
			return dba.getCustomerNameFromID(id);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetCustomerServedBy.java");
			return null;
		}
	}
}