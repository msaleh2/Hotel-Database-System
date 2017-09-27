/**
 * 
 */
package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Room;
import database.DBUtil;
import database.JavaDatabaseConnection;

/**
 * Gets Available rooms as of now
 * 
 * @author Anusha BalajI (abalaji) and Joanna Pollard (jbpolla2)
 * 
 */
public class RoomsDBA {

	public ArrayList<Room> getAvRooms(int hotelID, int roomType) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<Room> list = new ArrayList<Room>();
		try {
			conn = JavaDatabaseConnection.getConnection();

			ps = conn.prepareStatement("SELECT * FROM room WHERE availability=? AND hotelID=? AND roomType=?");
			ps.setInt(1, 1);
			ps.setInt(2, hotelID);
			ps.setInt(3, roomType);
			
			final ResultSet results = ps.executeQuery();
			list = loadAvRooms(results, list);			
			return list;
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public ArrayList<Room> loadAvRooms(ResultSet rs, ArrayList<Room> list) throws SQLException {
		Room r;
		while(rs.next()) {
			r = new Room(rs.getInt("num"), rs.getInt("hotelID"));
			r.setAvailability(rs.getInt("availability"));
			r.setType(rs.getInt("roomType"));
			r.setMaxAllowedOccupancy(rs.getInt("occupancy"));
			r.setRate(rs.getInt("rate"));
			list.add(r);
		}
		return list; 
	}


	
}
