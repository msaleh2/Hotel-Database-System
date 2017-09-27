package actions.get;

import models.Staff;
import sql.ManagerDBA;

import java.sql.SQLException;

/**
 * Class to handle I/O for retrieving a manager
 * @author Alex
 *
 */
public class GetManager {
	
	/**
	 * Method which returns a staff member based on the queried information
	 * @param dba database access object
	 * @param id primary key for a staff member
	 * @return the queried staff member or null if an error occurs
	 */
	public static Staff getManager(ManagerDBA dba, int id) {
		try {
			return dba.getManager(id);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetManager.java");
			return null;
		}
	}
	
}
