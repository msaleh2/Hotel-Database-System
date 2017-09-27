package actions.edit;

import models.Staff;
import sql.StaffDBA;

import java.sql.SQLException;

/**
 * Action class to handle I/O for editing a staff member
 * @author Alex
 *
 */
public class EditStaff {
	
	/**
	 * Method which returns a string of success or failure
	 * @param dba database object
	 * @param c staff to edit
	 * @return success or failure of the operation 
	 */
	public static String editStaff(StaffDBA dba, Staff c) {
		try {
			dba.editStaff(c);
			return "Success: Staff - " + c.getName() + " modified.";
		} catch(SQLException e) {
			return "Failed to edit staff member";
		}
	}
	
}