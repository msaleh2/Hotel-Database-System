package actions.get;

import models.Hotel;
import sql.HotelDBA;

import java.sql.SQLException;

/**
 * Class to handle I/O for retrieving a hotel
 * @author Alex
 *
 */
public class GetHotel {
	
	/**
	 * Method which returns a hotel based on the queried information
	 * @param dba database access object
	 * @param id primary key for the hotel
	 * @return the queried hotel or null if an error occurs
	 */
	public static Hotel getHotel(HotelDBA dba, int id) {
		try {
			return dba.getHotel(id);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetHotel.java");
			return null;
		}
	}
	
}
