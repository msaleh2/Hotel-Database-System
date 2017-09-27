/**
 * 
 */
package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Bill;
import models.Charge;
import models.Reservation;
import database.DBUtil;
import database.JavaDatabaseConnection;

/**
 * Database access class for Bills and Charges
 * @author Anusha
 *
 */
public class ChargesDBA {

	/**
	 * Loads an SQL result set into a java Bill object
	 * @param rs results of a query
	 * @return Bill object
	 * @throws SQLException
	 */
	public Bill loadCharges(ResultSet rs) throws SQLException {
		Bill bill = new Bill();
		if(rs.next()) {
			bill = new Bill(rs.getInt("customerID"), rs.getInt("hotelID"), rs.getInt("roomNumber"));
			do {
				Charge charge = new Charge();
				charge.setHotelID(rs.getInt("hotelID"));
				charge.setRoom(rs.getInt("roomNumber"));
				charge.setCustomerID(rs.getInt("customerID"));
				charge.setStaffID(rs.getInt("staffID"));
				charge.setServiceType(rs.getInt("serviceType"));
				charge.setSvcCount(rs.getInt("serviceCounter"));
				charge.setAmount(rs.getInt("Amount"));
				bill.add(charge);
			} while (rs.next());
		}
		return bill; 
	}

	/**
	 * Method to access the database and get a Bill (which contains a set of charges and Identifying information)
	 * @param roomNumber key linked to the bill
	 * @param customerID key linked to the bill
	 * @param hotelID key linked to the bill
	 * @return the bill containing the charges
	 * @throws SQLException
	 */
	public Bill getCharges(int roomNumber, int customerID, int hotelID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("SELECT * FROM charges WHERE roomNumber=? AND customerID=? AND hotelID=?");
			ps.setInt(1, roomNumber);
			ps.setLong(2, customerID);
			ps.setInt(3, hotelID);

			final ResultSet results = ps.executeQuery();
			return loadCharges(results);

		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Adds a charge to a reservation and a bill in the database
	 * @param c the charge to add
	 * @param res the reservation to add the charge to
	 * @throws SQLException
	 */
	public void addCharge(Charge c, Reservation res) throws SQLException {
		try {
			Bill b = getCharges(res.getRoom(), res.getCustomerID(), res.getHotelID());
			b.add(c);
			res.setBill(b);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("INSERT INTO charges(roomNumber, hotelID, customerID, staffID, serviceType, serviceCounter, amount) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, res.getRoom());
			ps.setInt(2, res.getHotelID());
			ps.setLong(3, res.getCustomerID());
			ps.setLong(4, c.getStaffID());
			ps.setInt(5, c.getSvcType());
			ps.setInt(6, c.getSvcCount());
			ps.setInt(7, c.getAmount());

			ps.executeUpdate();
			
			ps2 = conn.prepareStatement("UPDATE reservation SET balance=? WHERE customerID=? AND roomNumber=? "
					+ "AND checkInDate=to_date(?, 'dd-mon-yyyy hh:mi:ss')");
			int total = res.getBalance() + c.getAmount();
			ps2.setInt(1, total);
			ps2.setInt(2, res.getCustomerID());
			ps2.setInt(3, res.getRoom());
			ps2.setString(4, res.formatDate(res.getCheckInDate()));
			
			ps2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Error adding charge: " + c.toString());
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeConnection(conn, ps2);
		}
		
	}

	/**
	 * Method to remove a charge from the database
	 * @param res the reservation the charge is linked to
	 * @param c the charge to remove from the reservation
	 * @throws SQLException
	 */
	public void removeCharge(Reservation res, Charge c) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("DELETE FROM charges WHERE customerID=? AND roomNumber=? AND hotelID=?" +
			" AND staffID=? AND serviceType=? AND serviceCounter=? AND AMOUNT=?");
			ps.setInt(1, res.getCustomerID());
			ps.setInt(2, res.getRoom());
			ps.setInt(3, res.getHotelID());
			ps.setInt(4, c.getStaffID());
			ps.setInt(5,  c.getSvcType());
			ps.setInt(6, c.getSvcCount());
			ps.setInt(7, c.getAmount());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error deleting charge: " + c.toString());
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
