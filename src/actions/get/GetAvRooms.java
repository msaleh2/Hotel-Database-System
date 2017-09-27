package actions.get;

import java.sql.SQLException;
import java.util.ArrayList;

import sql.RoomsDBA;
import models.Room;

/**
 * Functionality to get the available rooms in a hotel. 
 * 
 * @author Anusha Balaji (abalaji) 
 *
 */
public class GetAvRooms {

	/**
	 * Method which returns all rooms based on the queried information
	 * @param dba database access object
	 * @param hotelID the hotel the rooms are in
	 * @return the queried rooms or null if an error occurs
	 */
	public static ArrayList<Room> getAvRooms(RoomsDBA dba, int hotelID, int roomType) {
		try {
			return dba.getAvRooms(hotelID, roomType);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetAvRooms.java");
			return null;
		}
	}
}
