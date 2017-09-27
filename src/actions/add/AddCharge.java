/**
 * 
 */
package actions.add;

import java.sql.SQLException;

import models.Charge;
import models.Reservation;
import sql.ChargesDBA;

/**
 * Adds the given charge to a particular reservation 
 * 
 * @author Anusha Balaji (abalaji)
 *
 */
public class AddCharge {

	/**
	 * Uses room number, checkIn date, customer id to retrieve the bill and update a specific charge in it
	 * Charges are identified by their dataAndTime field. So, that field is unmodifiable 
	 * @param dba 
	 * @param serviceType
	 */
	public static String addCharges(ChargesDBA dba, Reservation res, Charge c) {
		try {
			dba.addCharge(c, res);
			return "Success: Charge added!";
		} catch(SQLException e) {
			return "Failed to add charge";
		}
	}
	

}
