package actions.delete;

import sql.StaffDBA;

import java.sql.SQLException;

/**
 * I/O and error handling for deleting a staff member
 * @author Alex
 *
 */
public class DeleteStaff {
	
	/**
	 * Deletes the given staff member from the database. 
	 * @param id id of staff member to be deleted
	 * @return message upon successful/unsuccessful completion 
	 */
	public static String deleteStaff(StaffDBA dba, int id) {
		try {
			dba.removeStaff(id);
			return "Success: Staff deleted.";
		} catch(SQLException e) {
			return "Failed to remove staff member";
		}
	}
	
}
