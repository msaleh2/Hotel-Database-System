package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBUtil;
import database.JavaDatabaseConnection;
import models.Hotel;
/**
 * Database access class for hotel objects
 * @author Joanna Pollard (jbpolla2) 
 *
 */
public class HotelDBA {
	
	/**
	 * Load a hotel result set into a hotel java object
	 * @param rs the query result set
	 * @return the hotel object queried for
	 * @throws SQLException
	 */
	public Hotel loadHotel(ResultSet rs) throws SQLException {
		Hotel h = null;
		while(rs.next()) {
			h = new Hotel(rs.getInt("hotelID"));
			h.setName(rs.getString("name"));
			h.setAddress(rs.getString("address"));
			h.setPhone(rs.getString("phoneNumber"));
		}
		return h;
	}

	/**
	 * Gets the hotel object given a hotel id
	 * @param id ID of the hotel to get
	 * @return the hotel queried for
	 * @throws SQLException
	 */
	public Hotel getHotel(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		Hotel h = null;
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM hotel WHERE hotelID=?");
			ps.setInt(1, id);
			final ResultSet results = ps.executeQuery();
			h = loadHotel(results);
			
		} catch (SQLException e) {
			System.out.println("Could not get hotel.");
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return h;
	}
}
