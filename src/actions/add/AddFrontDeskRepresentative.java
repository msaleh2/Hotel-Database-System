package actions.add;

import models.Staff;
import sql.FrontDeskRepresentativeDBA;

import java.sql.SQLException;

/**
 * Class for error handling and output to add a front desk representative.
 * @author Alex
 *
 */
public class AddFrontDeskRepresentative {
	
	/**
	 * Adds a front desk representative to the database
	 * @param dba database action class
	 * @param c the representative to be added
	 * @return information about the success or failure of the operation
	 */
	public static String addFrontDeskRepresentative(FrontDeskRepresentativeDBA dba, Staff c) {
		try {
			dba.addFrontDeskRepresentative(c);
			return "Success: Front Desk Representative - " + c.getName() + " created.";
		} catch(SQLException e) {
			return "Failed to add front desk representative";
		}
	}
}