package actions.get;

import java.sql.SQLException;

import models.Room;
import sql.RoomDBA;

/**
 * Retrieve room functionality
 * 
 * @author Anusha Balaji (abalaji) 
 */
public class GetRoom {

	/**
	 * Returns further details about a room given it's hotelID and number 
	 * @return Room with the given hotelID and number or null if no room with such 
	 * specifications is found 
	 */
	public static Room getRoom(RoomDBA dba, int hotelID, int num) {
		try {
			return dba.getRoom(hotelID, num);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetRoom.java");
			return null; 
		} 
	}

}
