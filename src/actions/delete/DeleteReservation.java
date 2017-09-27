package actions.delete;

import java.sql.SQLException;

import models.Reservation;
import sql.ReservationDBA;

/**
 * Deleting a reservation functionality. 
 *  
 * @author Anusha Balaji (abalaji)   
 *  
 */
public class DeleteReservation {

	/**
	 * Deletes the given reservation from the database if it exists
	 * @return message on the execution of the function 
	 */
	public static String deleteReservation(ReservationDBA dba, Reservation r) {
		try {
			dba.removeReservation(r.getCustomerID(), r.getRoom(), r.getCheckInDate(), r.getHotelID());
			return "Success: Reservation deleted"; 
		} catch(SQLException e) {
			return "Failed to delete reservation"; 
		}
	}

	public static String deletePresidentialReservation(ReservationDBA dba,
			Reservation r) {
		try {
			dba.removeReservation(r.getCustomerID(), r.getRoom(), r.getCheckInDate(), r.getHotelID());
			dba.removePresidentialReservation(r);
			return "Success: Reservation deleted"; 
		} catch(SQLException e) {
			return "Failed to delete reservation"; 
		}
		
	}

}
