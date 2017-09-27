package actions.get;

import java.sql.SQLException;

import models.PresidentialSuite;
import models.Room;
import sql.RoomDBA;

/**
 * Retrieve room functionality
 * 
 * @author Joanna Pollard (jbpolla2) 
 */
public class GetPresidentialSuite {

	/**
	 * Returns further details about a room given it's hotelID and number 
	 * @return Room with the given hotelID and number or null if no room with such 
	 * specifications is found 
	 */
	public static PresidentialSuite getPresidentialSuite(RoomDBA dba, int roomNumber, int hotelID) {
		try {
			return dba.getPresidentialSuite(hotelID, roomNumber);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetRoom.java");
			return null; 
		} 
	}

}