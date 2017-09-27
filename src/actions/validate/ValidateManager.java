package actions.validate;

import java.sql.SQLException;

import sql.ManagerDBA;

/**
 * Handles I/O for validating that an object was successfully added to the database
 * @author Alex
 *
 */
public class ValidateManager {
	
	/**
	 * Method which calls a validator and returns whether or not an object is in the database
	 * @param dba database object
	 * @param id the id of the object to check
	 * @return whether or not the object is in the database
	 */
	public static boolean validateManager(ManagerDBA dba, int id) {
		try {
			return dba.validateManager(id);
		} catch(SQLException e) {
			return false;
		}
	}
}
