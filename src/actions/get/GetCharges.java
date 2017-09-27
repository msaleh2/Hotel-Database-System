package actions.get;

import models.Bill;
import sql.ChargesDBA;

import java.sql.SQLException;

/**
 * Class to handle I/O for retrieving charges
 * @author Alex
 *
 */
public class GetCharges {

	/**
	 * Method which returns a bill based on the queried information
	 * @param dba database access object
	 * @param roomNumber, customerID, hotelID primary key for a bill
	 * @return the queried bill or null if an error occurs
	 */
	public static Bill getCharges(ChargesDBA dba, int roomNumber, int customerID, int hotelID) {
		try {	
			return dba.getCharges(roomNumber, customerID, hotelID);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetCharges.java");
			return null;
		}
	}
	
}
