/**
 * 
 */
package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBUtil;
import database.JavaDatabaseConnection;
import models.PresidentialSuite;
import models.Room;

/**
 * DB interaction to get, add, edit, and delete rooms. 
 * 
 * @author Anusha Balaji (abalaji) 
 *
 */
public class RoomDBA {

	public Room loadRoom(ResultSet rs) throws SQLException {
		Room r = null;
		while(rs.next()){
			r = new Room(rs.getInt("num"), rs.getInt("hotelID"));
			r.setNumber(rs.getInt("num"));
			r.setHotelID(rs.getInt("hotelID"));
			r.setRate(rs.getInt("rate"));
			r.setAvailability(rs.getInt("availability"));
			r.setMaxAllowedOccupancy(rs.getInt("occupancy"));
			r.setType(rs.getInt("roomtype"));
		}
		return r;
	}	

	public void addRoom(Room r) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement(" INSERT INTO room(num, hotelID, rate, availability, occupancy, roomType) "
					+ "VALUES(?,?,?,?,?,?)");
			ps.setInt(1, r.getNumber());
			ps.setInt(2, r.getHotelID());
			ps.setInt(3, r.getRate());
			ps.setInt(4, r.getAvailability());
			ps.setInt(5, r.getMaxAllowedOccupancy());
			ps.setInt(6, r.getType());

			ps.executeUpdate();
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Number and hotelID are unchangeable
	 * @param r updated room
	 * @throws SQLException when error accessing the database 
	 */
	public void editRoom(Room r) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("UPDATE room SET rate=?,"
					+ "availability=?, occupancy=?, roomType=? "
					+ "WHERE num=? and hotelID=?");
			ps.setDouble(1, r.getRate());
			ps.setInt(2, r.getAvailability());
			ps.setInt(3, r.getMaxAllowedOccupancy());
			ps.setInt(4, r.getType());
			ps.setInt(5, r.getNumber());
			ps.setInt(6, r.getHotelID());

			ps.executeUpdate();

		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public void removeRoom(Room r) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("DELETE FROM Room WHERE num=? and hotelID=?");
			ps.setInt(1, r.getNumber());
			ps.setInt(2, r.getHotelID());

			ps.executeUpdate();

		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public Room getRoom(int hotelID, int num) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("SELECT * FROM Room WHERE hotelID=? and num=?");
			ps.setLong(1, hotelID);
			ps.setInt(2, num);
			final ResultSet results = ps.executeQuery();
			return loadRoom(results);

		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public void addPresidentialRoom(Room r) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement(" INSERT INTO room(num, hotelID, rate, availability, occupancy, roomType) "
					+ "VALUES(?,?,?,?,?,?)");
			ps.setInt(1, r.getNumber());
			ps.setInt(2, r.getHotelID());
			ps.setInt(3, r.getRate());
			ps.setInt(4, r.getAvailability());
			ps.setInt(5, r.getMaxAllowedOccupancy());
			ps.setInt(6, r.getType());

			ps.executeUpdate();
			
			ps2 = conn.prepareStatement("INSERT INTO presidentialSuite(num, hotelID)" 
					+ " VALUES (?,?)");
			ps2.setInt(1, r.getNumber());
			ps2.setInt(2, r.getHotelID());
			ps2.executeUpdate();
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeConnection(conn, ps2);
		}
	}

	public PresidentialSuite getPresidentialSuite(int hotelID, int roomNumber) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("SELECT * FROM presidentialSuite WHERE hotelID=? and num=?");
			ps.setLong(1, hotelID);
			ps.setInt(2, roomNumber);
			final ResultSet results = ps.executeQuery();
			return loadPresidentialSuite(results);

		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	private PresidentialSuite loadPresidentialSuite(ResultSet rs) throws SQLException {
		PresidentialSuite r = null;
		while(rs.next()){
			r = new PresidentialSuite(rs.getInt("num"), rs.getInt("hotelID"));
			r.setCateringStaffID(rs.getInt("cateringStaffID"));
			r.setRoomStaffID(rs.getInt("roomStaffID"));
		}
		return r;
	}

	public boolean validateRoom(int roomNumber) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM room WHERE num=?");
			ps.setInt(1, roomNumber);
			
			final ResultSet results = ps.executeQuery();
			return results.next();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}

