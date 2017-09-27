package actions.get;

import java.sql.SQLException;
import java.util.List;

import models.Staff;
import sql.ManagerDBA;

/**
 * Class to handle I/O for retrieving managers
 * @author Alex
 *
 */
public class GetAllManagers {
	
	/**
	 * Method which returns all staff members based on the queried information
	 * @param dba database access object
	 * @return the queried staff members or null if an error occurs
	 */
	public static List<Staff> getAllManagers(ManagerDBA dba) {
		try {
			return dba.getAllManagers();
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetAllManagers.java");
			return null;
		}
	}
}
