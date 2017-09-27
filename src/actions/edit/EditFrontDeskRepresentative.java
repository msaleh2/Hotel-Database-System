package actions.edit;

import models.Staff;
import sql.FrontDeskRepresentativeDBA;

import java.sql.SQLException;

/**
 * Action class to handle I/O for editing a front desk representative
 * @author Alex
 *
 */
public class EditFrontDeskRepresentative {
	
	/**
	 * Method which returns a string of success or failure
	 * @param dba database object
	 * @param c fdr to edit
	 * @return success or failure of the operation 
	 */
	public static String editFrontDeskRepresentative(FrontDeskRepresentativeDBA dba, Staff c) {
		try {
			dba.editFrontDeskRepresentative(c);
			return "Success: Front Desk Representative - " + c.getName() + " modified.";
		} catch(SQLException e) {
			return "Failed to modify front desk representative";
		}
	}
	
}