/**
 * 
 */
package actions.add;

import java.sql.SQLException;

import models.Reservation;
import sql.ReservationDBA;
import sql.RoomDBA;

/**
 * Adding a reservation functionality. 
 * 
 * @author Anusha Balaji (abalaji) 
 *
 */
public class AddReservation {

	/**
	 * Adds the given room to the database; hotel must already exists or error is thrown 
	 * @param r Room to be added to the database
	 * @return message about the result of the action or null if hotel doesn't exist 
	 * @throws SQL exception when error adding a room to the database 
	 */
	public static String addReservation(ReservationDBA dba, Reservation r) {
		try {
			dba.addReservation(r);
			return "Success: Reservation added! " ;
		} catch(SQLException e) {
			return "Failed to add reservation";
		}
	}

	public static String addPresidentialReservation(ReservationDBA dba, Reservation r, int cateringStaff, int roomStaff) {
		try {
			dba.addPresidentialReservation(r, cateringStaff, roomStaff);
			return "Success: Reservation added! " ;
		} catch(SQLException e) {
			return "Failed to add reservation";
		}
	}

}
