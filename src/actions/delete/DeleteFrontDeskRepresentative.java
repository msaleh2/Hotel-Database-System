package actions.delete;

import sql.FrontDeskRepresentativeDBA;

import java.sql.SQLException;

/**
 * Class to handle errors and output for removing a front desk representative.
 * @author Alex
 *
 */
public class DeleteFrontDeskRepresentative {
	
	/**
	 * Method to delete a front desk represenatative
	 * @param dba database action class
	 * @param id fdr id
	 * @return Success or failure of the operation
	 */
	public static String deleteFrontDeskRepresentative(FrontDeskRepresentativeDBA dba, int id) {
		try {
			dba.removeFrontDeskRepresentative(id);
			return "Success: FrontDeskRepresentative deleted.";
		} catch(SQLException e) {
			return "Failed to remove front desk represenatative";
		}
	}
	
}