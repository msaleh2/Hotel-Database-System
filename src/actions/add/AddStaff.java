package actions.add;

import models.Staff;
import sql.StaffDBA;

import java.sql.SQLException;

/**
 * Class for error handling and output to add a staff member.
 * @author Alex
 *
 */
public class AddStaff {
	
	/**
	 * Adds a staff member to the database
	 * @param dba database action class
	 * @param s the staff member to be added
	 * @return information about the success or failure of the operation
	 */
	public static String addStaff(StaffDBA dba, Staff s) {
		try {
			dba.addStaff(s);
			return "Success: Staff - " + s.getName() + " created. ";
		} catch(SQLException e) {
			return "Failed to add staff member";
		}
	}
	
}