package actions.edit;

import java.sql.SQLException;

import models.Reservation;
import sql.ReservationDBA;

/**
 * Modifying a reservation functionality. 
 * 
 * @author Anusha Balaji (abalaji) 
 *
 */
public class EditReservation {

	/**
	 * Assuming customerID, roomNumber, and checkIn date haven't changed as they'll be used to 
	 * identify and retrieve the reservation. 
	 * If any of these must be changed, please delete the original reservation and create a new one. 
	 * @param r updated reservation 
 	 * @return message upon success/failing completion of the function 
 	 * customerID, roomNumber, and checkIn date or error accessing the database. 
	 */
	public static String editReservation(ReservationDBA dba, Reservation r, Reservation oldR) {
		try {
			dba.editReservation(r, oldR);
			return "Success: Reservation updated!";
		} catch(SQLException e) {
			return "Failed to update reservation.";
		}
	}

	public static String editPresidentialReservation( ReservationDBA dba, Reservation r, int cateringStaff,
			int roomStaff, Reservation oldR) {
		try {
			dba.editReservation(r, oldR);
			dba.editPresidentialReservation(cateringStaff, roomStaff, r);
			return "Success: Reservation updated!";
		} catch(SQLException e) {
			return "Failed to update reservation.";
		}
	}

	public static String checkout(ReservationDBA reservationDBA, Reservation r, int newBalance, int numberOfNights, String checkOutDate) {
		try {
			reservationDBA.checkout(r, newBalance, numberOfNights, checkOutDate);
			return "Success: Checkout complete!";
		} catch(SQLException e) {
			return "Failed to checkout.";
		}
		
	}

}
