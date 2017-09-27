package actions.edit;

import java.sql.SQLException;

import models.Room;
import sql.RoomDBA;

/**
 * Functionality to edit room
 * 
 * @author Anusha Balaji (abalaji) 
 */
public class EditRoom {

	/**
	 * Editing room functionality. Only the rate, occupancy, availability, type are editable
	 * Unsuccessful only if the given room doesn't exist in the hotel given. 
	 */
	public static String editRoom(RoomDBA dba, Room r) {
		try {
			dba.editRoom(r);
			return "Success: Room - " + r.getNumber() + " edit. ";
		} catch(SQLException e) {
			return "Failed to edit room.";
		} 

	}

}
