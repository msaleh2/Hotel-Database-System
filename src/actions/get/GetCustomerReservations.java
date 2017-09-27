/**
 * 
 */
package actions.get;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Reservation;
import sql.ReservationDBA;

/**
 * Get the reservations associated with this customer from the database. 
 * 
 * @author Anusha Balaji (abalaji) 
 */
public class GetCustomerReservations {

	/**
	 * Get the reservations associated with this customer from the database 
	 */
	public static ArrayList<Reservation> getCustomerReservations(ReservationDBA dba, int customerID) {
		try {
			return dba.getReservations(customerID);
		} catch(SQLException e) { 
			System.out.println("SQL Exception in GetCustomerReservations.java");
			return null; 
		}
	}

}
