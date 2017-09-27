package actions.get;

import models.Staff;
import sql.StaffDBA;

import java.sql.SQLException;

/**
 * Class to handle I/O for retrieving staff
 * @author Alex
 *
 */
public class GetStaff {
	
	/**
	 * Method which returns a staff member based on the queried information
	 * @param dba database access object
	 * @param id primary key for a staff member
	 * @return the queried staff member or null if an error occurs
	 */
	public static Staff getStaff(StaffDBA dba, int id) {
		try {
			return dba.getStaff(id);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetStaff.java");
			return null;
		}
	}
	
}
