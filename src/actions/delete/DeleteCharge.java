/**
 * 
 */
package actions.delete;

import java.sql.SQLException;

import sql.ChargesDBA;
import models.Charge;
import models.Reservation;

/**
 * Class for removing a charge
 * @author Anusha
 *
 */
public class DeleteCharge {

	/**
	 * Method to delete a charge
	 * @param dba database action object
	 * @param res Reservation object
	 * @param c charge to be deleted from the reservation
	 */
	public static String deleteCharge(ChargesDBA dba, Reservation res, Charge c) {
		try {
			dba.removeCharge(res, c);
			return "Success: Charge deleted.";
		} catch(SQLException e) {
			return "Failed to remove the charge ";
		}
	}

}
