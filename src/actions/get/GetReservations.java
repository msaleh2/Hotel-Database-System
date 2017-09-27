package actions.get;

import java.util.List;
import models.Reservation;

import java.sql.SQLException;

import sql.ReservationDBA;

/**
 * Retrieve Reservations associated with Customer ID
 * 
 * @author Joanna Pollard (jbpolla2) 
 */
public class GetReservations {

	/**
	 * Returns all reservations associated with a Customer ID
	 * @return Reservations
	 */
	public static List<Reservation> getReservations(ReservationDBA dba, int customerID) {
		try {
			return dba.getReservations(customerID);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetReservations.java");
			return null; 
		} 
	}
}
