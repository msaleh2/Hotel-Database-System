package actions.get;

import models.Staff;
import sql.FrontDeskRepresentativeDBA;

import java.sql.SQLException;

/**
 * Class to handle I/O for retrieving a front desk representative
 * @author Alex
 *
 */
public class GetFrontDeskRepresentative {
	
	/**
	 * Method which returns a staff member based on the queried information
	 * @param dba database access object
	 * @param id primary key for a staff member
	 * @return the queried staff member or null if an error occurs
	 */
	public static Staff getFrontDeskRepresentative(FrontDeskRepresentativeDBA dba, int id) {
		try {
			return dba.getFrontDeskRepresentative(id);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetFrontDeskRepresentative.java");
			return null;
		}
	}
	
}
