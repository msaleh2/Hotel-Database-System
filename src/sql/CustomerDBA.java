package sql;

import models.Customer;
import database.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.JavaDatabaseConnection;

/**
 * Customer database access class
 * @author Alex
 *
 */
public class CustomerDBA {
	
	/**
	 * Loads customer data from a result set (database query return) into a customer object
	 * @param rs sql result set
	 * @return queried customer object
	 * @throws SQLException
	 */
	public Customer loadCustomer(ResultSet rs) throws SQLException {
		Customer c = null;
		while(rs.next()) {
			c = new Customer(rs.getInt("customerID"));
			c.setName(rs.getString("name"));
			c.setSSN(rs.getInt("ssn"));
			c.setEmail(rs.getString("email"));
			c.setAddress(rs.getString("address"));
			c.setPhone(rs.getLong("phoneNumber"));
			c.setGender(rs.getInt("gender"));
			c.setCardNumber(rs.getLong("creditCard"));
			c.setBillingAddress(rs.getString("billingAddress"));
		}
		return c;
	}	
	
	/**
	 * Method to add a customer to the database
	 * @param c customer to add to the database
	 * @throws SQLException
	 */
	public void addCustomer(Customer c) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement(" INSERT INTO customer(customerID, name, SSN, email, address, "
					+ "phoneNumber, gender, creditCard, billingAddress)"
					+ " VALUES(customer_seq.nextval,?,?,?,?,?,?,?,?)");
			ps.setString(1, c.getName());
			ps.setInt(2, c.getSSN());
			ps.setString(3, c.getEmail());
			ps.setString(4, c.getAddress());
			ps.setLong(5, c.getPhone());
			ps.setInt(6, c.getGender());
			ps.setLong(7, c.getCardNumber());
			ps.setString(8, c.getBillingAddress());
			
			ps.executeUpdate();
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to edit customer information in the database
	 * @param c A full customer object to overwrite the current one, creation is handled in the menus
	 * @throws SQLException
	 */
	public void editCustomer(Customer c) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			conn.commit();
			
			ps = conn.prepareStatement(" UPDATE customer SET customerID=?, name=?, SSN=?,"
					+ "email=?, address=?, phoneNumber=?, gender=?, creditCard=?,"
					+ "billingAddress=? WHERE customerID=?");
			ps.setInt(1, c.getId());
			ps.setString(2, c.getName());
			ps.setInt(3, c.getSSN());
			ps.setString(4, c.getEmail());
			ps.setString(5, c.getAddress());
			ps.setLong(6, c.getPhone());
			ps.setInt(7, c.getGender());
			ps.setLong(8, c.getCardNumber());
			ps.setString(9, c.getBillingAddress());
			ps.setLong(10, c.getId());
			ps.executeUpdate();
			
			int ssnLength = String.valueOf(c.getSSN()).length();
			if ( ssnLength != 9 ) {
				conn.rollback();
				throw new SQLException("Invalid SSN");
			}
			int cardLength = String.valueOf(c.getCardNumber()).length();
			if (cardLength != 16) {
				conn.rollback();
				throw new SQLException("Invalid Credit Card");
			}
			int phoneLength = String.valueOf(c.getPhone()).length();
			if (phoneLength != 10) {
				conn.rollback();
				throw new SQLException("Invalid Phone Number");
			}
		}  finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to remove a customer from the database
	 * @param id ID of the customer to remove
	 * @throws SQLException
	 */
	public void removeCustomer(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("DELETE FROM customer WHERE customerID=?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to retrieve a customer from the database
	 * @param id ID of the customer to retrieve
	 * @return the Customer retrieved
	 * @throws SQLException
	 */
	public Customer getCustomer(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		Customer c = null;
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM customer WHERE customerID=?");
			ps.setInt(1, id);
			final ResultSet results = ps.executeQuery();
			c = loadCustomer(results);
			
		} catch (SQLException e) {
			System.out.println("Could not get customer.");
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return c;
	}
	
	/**
	 * Method to get the room numbers that a staff has served from the database
	 * @param id ID of staff member to query for
	 * @return list of rooms which this staff has served
	 * @throws SQLException
	 */
	public List<Integer> getRoomNumbersOfCustomerServedBy(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		List<Integer> roomNumberList = new ArrayList<Integer>();
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps1 = conn.prepareStatement("SELECT * FROM charges WHERE staffID=?");
			ps1.setInt(1, id);		
			final ResultSet results1 = ps1.executeQuery();
			while(results1.next()) {
				int nextRow = results1.getInt("roomNumber");
				if(!roomNumberList.contains(nextRow))
					roomNumberList.add(nextRow);
			}
			ps2 = conn.prepareStatement("SELECT * FROM presidentialSuite WHERE roomStaffID=? OR cateringStaffID=?");
			ps2.setInt(1, id);
			ps2.setInt(2, id);
			final ResultSet results2 = ps2.executeQuery();			
			while(results2.next()) {
				int nextTuple = results2.getInt("num");
				if(!roomNumberList.contains(nextTuple))
					roomNumberList.add(nextTuple);
			}			
			return roomNumberList;
		} finally {
			DBUtil.closeConnection(conn, ps1);
			DBUtil.closeConnection(conn, ps2);
		}		
	}
	
	/**
	 * Method to get the customer ID that a of a specific room from the databse
	 * @param roomNumber room number to query for
	 * @return the ID associated with the room
	 * @throws SQLException
	 */
	public int getCustomerIDFromRoomNumber(int roomNumber) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		int customerID = -1;
		try {
			conn = JavaDatabaseConnection.getConnection();
		
			ps = conn.prepareStatement("SELECT * FROM reservation WHERE roomNumber=?");
			ps.setInt(1, roomNumber);
			final ResultSet results = ps.executeQuery();			
			while(results.next()) {
				customerID = results.getInt("customerID");
			}
			return customerID;			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to get the customer name that is associated with the ID from the databse
	 * @param id customer ID to query for
	 * @return the name associated with the customer ID
	 * @throws SQLException
	 */
	public String getCustomerNameFromID(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String customerName = null;
		try {
			conn = JavaDatabaseConnection.getConnection();
		
			ps = conn.prepareStatement("SELECT * FROM customer WHERE customerID=?");
			ps.setInt(1, id);
			final ResultSet results = ps.executeQuery();			
			while(results.next()) {
				customerName = results.getString("name");
			}
			return customerName;			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Validate that the customer object exists
	 * @param id ID of the customer object to validate
	 * @return whether or not the customer object is present
	 * @throws SQLException
	 */
	public boolean validateCustomer(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM customer WHERE customerID=?");
			ps.setInt(1, id);
			
			final ResultSet results = ps.executeQuery();
			return results.next();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
