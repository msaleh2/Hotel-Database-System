package actions.get;

import java.sql.SQLException;
import java.util.List;

import models.Staff;
import sql.StaffDBA;

/**
 * Class to handle I/O for retrieving staff
 * @author Alex
 *
 */
public class GetAllStaff {
	
	/**
	 * Method which returns all staff members based on the queried information
	 * @param dba database access object
	 * @return the queried staff members or null if an error occurs
	 */
	public static List<Staff> getAllStaff(StaffDBA dba, int id) {
		try {
			return dba.getAllStaff();
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetAllStaff.java");
			return null;
		}
	}
	
	/**
	 * Method which returns all staff members based on the queried information
	 * @param dba database access object
	 * @return the queried staff members or null if an error occurs
	 */
	public static List<Staff> getStaffByRole(StaffDBA dba) {
		try {
			return dba.getStaffByRole();
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetAllStaff.java");
			return null;
		}
	}
}
