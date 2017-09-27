package actions.get;

import java.sql.SQLException;
import java.util.List;

import models.Staff;
import sql.FrontDeskRepresentativeDBA;

/**
 * Class to handle I/O for retrieving fdrs
 * @author Alex
 *
 */
public class GetAllFrontDeskRepresentatives {
	
	/**
	 * Method which returns all staff members based on the queried information
	 * @param dba database access object
	 * @return the queried staff members or null if an error occurs
	 */
	public static List<Staff> getAllFrontDeskRepresentatives(FrontDeskRepresentativeDBA dba) {
		try {
			return dba.getAllFrontDeskRepresentatives();
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetAllFrontDeskRepresentatives.java");
			return null;
		}
	}
}
