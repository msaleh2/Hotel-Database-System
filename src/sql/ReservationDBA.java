/**
 * 
 */
package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Bill;
import models.Reservation;
import database.DBUtil;
import database.JavaDatabaseConnection;

/**
 * Interacts with the database to add/edit/delete reservations 
 * 
 * @author Anusha Balaji (abalaji) 
 *set
 */
public class ReservationDBA {

	// copied-and-pasted from Other DBA.java
	/**
	 * Load a single reservation from the given ResultSet 
	 * @param rs ResultSet to be used for loading 
	 * @return Reservation based on the ResultSet 
	 * @throws SQLException error loading reservation 
	 */
	public Reservation loadReservation(ResultSet rs) throws SQLException {

		Reservation r = new Reservation(rs.getInt("customerID"), rs.getInt("room"),
				rs.getString("checkInDate"));
		r.setCheckOutDate(rs.getString("checkOutDate"));
		r.setHotelID(rs.getInt("hotelID"));
		r.setOccupancy(rs.getInt("occupancy") == 1 ? true : false);
		
		// update balance
		ChargesDBA getBill = new ChargesDBA();
		Bill cumulativeBill = getBill.getCharges(rs.getInt("room"), rs.getInt("customerID"), rs.getInt("hotelID"));
		r.setBill(cumulativeBill);
		r.setBalance(r.getBalance());
		return r;
	}	

	/**
	 * Retrieve the reservation with the given properties from the database 
	 * @param roomNumber room number associated with this reservation 
	 * @param customerID id of the customer associated with this reservation 
	 * @param checkIn check in date and time associated with this reservation 
	 * @return Reservation with the given attribute values 
	 * @throws SQLException error accessing/retrieving the reservation 
	 */
	public Reservation getReservation(int roomNumber, int customerID, String checkIn) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JavaDatabaseConnection.getConnection();
			ps = conn.prepareStatement("SELECT * FROM reservation "
					+ "WHERE roomNumber=? AND customerID=? AND checkInDate=?");
			ps.setInt(1, roomNumber);
			ps.setInt(2, customerID);	
			ps.setString(3, checkIn);
			final ResultSet results = ps.executeQuery();
			return loadReservation(results);

		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	// more functions based on reservation
	/** 
	 * Add the given reservation to the database 
	 * @param r new reservation to be added to the database 
	 * @throws SQLException error adding to the database (invalid values for fields, runtime errors. etc.) 
	 */
	public void addReservation(Reservation r) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			conn.commit();
			
			ps = conn.prepareStatement("INSERT INTO reservation(roomNumber, hotelID, customerID, " + 
					"checkInDate, balance) " + " VALUES(?,?,?,to_date(?, 'dd-mon-yyyy hh:mi:ss'),?)"); 
			ps.setInt(1, r.getRoom());
			ps.setInt(2, r.getHotelID());
			ps.setInt(3, r.getCustomerID());
			ps.setString(4, Reservation.formatDate(r.getCheckInDate()));
			ps.setInt(5, r.getBalance());
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement("UPDATE room SET AVAILABILITY= 0 WHERE num= ? AND hotelID = ?");
			ps2.setInt(1, r.getRoom());
			ps2.setInt(2, r.getHotelID());
			ps2.executeUpdate();
			
		} catch (SQLException e) {
			conn.rollback();
			throw new SQLException("Invalid Reservation input");
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeConnection(conn, ps2);
		}

	}

	/**
	 * Assuming customerID, roomNumber, and checkIn date haven't changed as they'll be used to 
	 * identify and retrieve the reservation. 
	 * If any of these must be changed, please delete the original reservation and create a new one. 
	 * @param r updated reservation 
	 * @throws SQLException if no reservation is found with the given reservation's 
	 * customerID, roomNumber, and checkIn date or error accessing the database. 
	 */
	public void editReservation(Reservation r, Reservation oldR) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

		try {
			conn = JavaDatabaseConnection.getConnection();
			ps = conn.prepareStatement("UPDATE reservation SET balance=?, checkInDate=to_date(?, 'dd-mon-yyyy hh:mi:ss'), " +
					" roomNumber = ? WHERE customerID = ? AND roomNumber = ? AND hotelID = ? and checkInDate = to_date(?, 'dd-mon-yyyy hh:mi:ss')");
			
			ps.setInt(1, r.getBalance());
			ps.setString(2, Reservation.formatDate(r.getCheckInDate()));
			ps.setInt(3, r.getRoom());
			
			ps.setInt(4, r.getCustomerID());
			ps.setInt(5, oldR.getRoom());
			ps.setInt(6, oldR.getHotelID());
			ps.setString(7, Reservation.formatDate(oldR.getCheckInDate()));

			ps.executeUpdate();
			
			ps2 = conn.prepareStatement("UPDATE room SET availability = 0 WHERE num = ?");
			ps3 = conn.prepareStatement("UPDATE room SET availability = 1 WHERE num = ?");
			
			ps2.setInt(1, r.getRoom());
			ps3.setInt(1, oldR.getRoom());
			
			ps3.executeUpdate();
			ps2.executeUpdate();

		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeConnection(conn, ps2);
			DBUtil.closeConnection(conn, ps3);
		}
	}

	/**
	 * Deletes the reservation with the given parameters from the database
	 * @param customerID customer ID of the customer associated with the reservation 
	 * @param roomNum room number associated with the reservation 
	 * @param checkIn check in date associated with the reservation 
	 * @throws SQLException when no reservation with the given parameters is found and error accessing the database 
	 */
	public void removeReservation(int customerID, int roomNum, String checkIn, int hotelID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("DELETE FROM reservation WHERE customerID=? AND roomNumber=? "
					+ "AND checkInDate=to_date(?, 'dd-mon-yyyy hh:mi:ss')");
			ps.setInt(1, customerID);
			ps.setInt(2, roomNum);
			ps.setString(3, Reservation.formatDate(checkIn));
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement("UPDATE room SET availability=1 WHERE num=? AND hotelID=?");
			ps2.setInt(1, roomNum);
			ps2.setInt(2, hotelID);
			ps2.executeUpdate();
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeConnection(conn, ps2);
		}
	}

	/**
	 * Get reservations for customer with the given id 
	 * @param customerID id of the customer whose reservations in WolfVillas hotels must be returned 
	 * @return arraylist of reservations for this customer 
	 * @throws SQLException error retrieving the reservations associated with this customer 
	 */
	public ArrayList<Reservation> getReservations(int customerID) throws SQLException {
		ArrayList<Reservation> list = new ArrayList<Reservation>();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("SELECT * FROM reservation "
					+ "WHERE customerID=?");
			ps.setInt(1, customerID);

			final ResultSet results = ps.executeQuery();
			list = loadReservation(results, list);

		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return list;
	}

	/**
	 * Loads reservations one-by-one from the database 
	 * @param results ResultSet with the reservations associated with this customer 
	 * @param list empty initialized ArrayList to hold the reservations 
	 * @return updated list with the reservations 
	 * @throws SQLException error when loading the results from the ResultSet 
	 */
	private ArrayList<Reservation> loadReservation(ResultSet results,
			ArrayList<Reservation> list) throws SQLException {
		Reservation r;
		while(results.next()) {
			r = new Reservation(results.getInt("customerID"), results.getInt("roomNumber"),
					results.getString("checkInDate"));
			r.setCheckOutDate(results.getString("checkOutDate"));
			r.setHotelID(results.getInt("hotelID"));
			r.setBalance(results.getInt("balance"));
			r.setBalance(results.getInt("Balance"));
			list.add(r);
		}
		return list;
	}
	
	public void addPresidentialReservation(Reservation r, int cateringStaff, int roomStaff) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		try {
			conn = JavaDatabaseConnection.getConnection();
			ps = conn.prepareStatement("INSERT INTO reservation(roomNumber, hotelID, customerID, " + 
					"checkInDate, balance) " + " VALUES(?,?,?,to_date(?, 'dd-mon-yyyy hh:mi:ss'),?)"); 
			ps.setInt(1, r.getRoom());
			ps.setInt(2, r.getHotelID());
			ps.setInt(3, r.getCustomerID());
			ps.setString(4, Reservation.formatDate(r.getCheckInDate()));
			ps.setInt(5, r.getBalance());
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement("UPDATE room SET AVAILABILITY= 0 WHERE num= ? AND hotelID = ?");
			ps2.setInt(1, r.getRoom());
			ps2.setInt(2, r.getHotelID());
			ps2.executeUpdate();
			
			ps3 = conn.prepareStatement("UPDATE presidentialSuite SET cateringStaffID = ?, roomStaffID = ? WHERE"
					+ " num = ? AND hotelID = ?");
			ps3.setInt(1, cateringStaff);
			ps3.setInt(2, roomStaff);
			ps3.setInt(3, r.getRoom());
			ps3.setInt(4, r.getHotelID());
			ps3.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeConnection(conn, ps2);
			DBUtil.closeConnection(conn, ps3);
		}
		
	}

	public void editPresidentialReservation(int cateringStaff, int roomStaff, Reservation r) throws SQLException {
		Connection conn = null;
		PreparedStatement ps3 = null;
		try {
			conn = JavaDatabaseConnection.getConnection();
			ps3 = conn.prepareStatement("UPDATE presidentialSuite SET cateringStaffID = ?, roomStaffID = ? WHERE"
					+ " num = ? AND hotelID = ?");
			ps3.setInt(1, cateringStaff);
			ps3.setInt(2, roomStaff);
			ps3.setInt(3, r.getRoom());
			ps3.setInt(4, r.getHotelID());
			ps3.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps3);
		}
		
	}

	public void removePresidentialReservation(Reservation r) throws SQLException {
		Connection conn = null;
		PreparedStatement ps3 = null;
		try {
			conn = JavaDatabaseConnection.getConnection();
			ps3 = conn.prepareStatement("UPDATE presidentialSuite SET cateringStaffID = null, roomStaffID = null WHERE"
					+ " num = ? AND hotelID = ?");
			ps3.setInt(1, r.getRoom());
			ps3.setInt(2, r.getHotelID());
			
			ps3.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps3);
		}
		
	}

	public void checkout(Reservation r, int newBalance, int numberOfNights, String checkOutDate) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		try {
			conn = JavaDatabaseConnection.getConnection();
			ps = conn.prepareStatement("UPDATE reservation SET balance=?, checkOutDate=to_date(?, 'dd-mon-yyyy hh:mi:ss') " +
					" WHERE customerID = ? AND roomNumber = ? AND hotelID = ? and checkInDate = to_date(?, 'dd-mon-yyyy hh:mi:ss')");
			
			ps.setInt(1, r.getBalance());
			ps.setString(2, Reservation.formatDate(checkOutDate));
			
			ps.setInt(3, r.getCustomerID());
			ps.setInt(4, r.getRoom());
			ps.setInt(5, r.getHotelID());
			ps.setString(6, Reservation.formatDate(r.getCheckInDate()));
			
			ps.executeUpdate();
			
			
			ps2 = conn.prepareStatement("UPDATE room SET availability = 1 WHERE num = ?");
			ps2.setInt(1, r.getRoom());
			ps2.executeUpdate();
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeConnection(conn, ps2);
		}
		
	}

}
