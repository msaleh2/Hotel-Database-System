package actions.delete;

import java.sql.SQLException;

import models.Room;
import sql.RoomDBA;

/**
 * Delete a room functionality 
 * 
 * @author Anusha Balaji (abalaji) 
 *
 */
public class DeleteRoom {

	/**
	 * Deletes the given room from the database. 
	 * Error message if there is no such room in hotel 
	 * @param r room to be deleted from the database
	 * @return message upon successful/unsuccessful completion 
	 */
	public static String deleteRoom(RoomDBA dba, Room r){
		try {
			dba.removeRoom(r);
			return "Success: Room " + r.getNumber() + " deleted from hotel " + r.getHotelID();
		} catch(SQLException e) {
			return "Failed to remove room";
		}
	}

}
