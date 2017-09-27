package actions.get;

import models.Reservation;
import sql.ReservationDBA;

import java.sql.SQLException;

/**
 * Class to handle I/O for retrieving a reservation
 * @author Alex
 *
 */
public class GetReservation {
	
	/**
	 * Method which returns a reservation member based on the queried information
	 * @param dba database access object
	 * @param roomNumber, customerID   primary key for a reservation member
	 * @return the queried reservation or null if an error occurs
	 */
	public static Reservation getReservation(ReservationDBA dba, int roomNumber, int customerID, String checkIn) {
		try {
			return dba.getReservation(roomNumber, customerID, checkIn);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetReservation.java");
			return null;
		}
	}
	
}
