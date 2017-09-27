package actions.get;

import java.sql.SQLException;
import sql.OtherDBA;

/**
 * Class to handle I/O for retrieving percent rooms occupied
 * @author Alex
 *
 */
public class GetOccupancyPercentage {
	
	/**
	 * Method which returns percent rooms occupied based on the queried information
	 * @param dba database access object
	 * @return the queried percentage or null if an error occurs
	 */
	public static double getOccupancyPercentage(OtherDBA dba) {
		try {
			return dba.getOccupancyPercentage();
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetOccupancyPercentage.java");
			return -1;
		}
	}
}