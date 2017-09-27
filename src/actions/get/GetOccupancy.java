package actions.get;

import java.sql.SQLException;
import sql.OtherDBA;

/**
 * Class to handle I/O for retrieving occupancy
 * @author Alex
 *
 */
public class GetOccupancy {
	
	/**
	 * Method which returns occupancy based on the queried information
	 * @param dba database access object
	 * @return the queried occupancy or -1 if an error occurs
	 */
	public static int getOccupancy(OtherDBA dba) {
		try {
			return dba.getOccupancy();
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetOccupancy.java");
			return -1;
		}
	}
}