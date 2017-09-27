/**
 * 
 */
package actions.add;

import java.sql.SQLException;

import models.Room;
import sql.RoomDBA;

/**
 * Functionality to add a room to a hotel 
 * 
 * @author Anusha Balaji (abalaji) 
 *
 */
public class AddRoom {

	/**
	 * Adds the given room to the database; hotel must already exists or error is thrown 
	 * @param r Room to be added to the database
	 * @return message about the result of the action or null if hotel doesn't exist 
	 * @throws SQL exception when error adding a room to the database 
	 */
	public static String addRoom(RoomDBA dba, Room r) {
		try {
			dba.addRoom(r);
			return "Success: Room - " + r.getNumber() + " added to hotel " + r.getHotelID();
		} catch(SQLException e) {
			return "Failed to add room";
		}
	}

	public static String addPresidentialRoom(RoomDBA dba, Room r) {
		try {
			dba.addPresidentialRoom(r);
			return "Success: Room - " + r.getNumber() + " added to hotel " + r.getHotelID();
		} catch(SQLException e) {
			return "Failed to add presidential suite";
		}
		
	}
}
