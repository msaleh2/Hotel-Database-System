/**
 * 
 */
package models;

import java.util.ArrayList;

/**
 * Maintains a list of hotels. 
 * used to retrieve and remove hotels. 
 * 
 * Can be used to add/update/delete to specific hotels 
 * Also, can be used to generate reports on hotels like displaying occupancy by hotel. 
 * 
 * @author Anusha Balaji (abalaji) 
 */
public class WolfVillasChain {

	private static ArrayList<Hotel> hotels;
	
	/**
	 * Initializes the hotels and customers lists
	 */
	public WolfVillasChain() {
		hotels = new ArrayList<Hotel>();
	}

	/**
	 * Return the hotel with the id given
	 * @param hotelID id of the hotel to be searched for existence in the chain 
	 * @return Hotel if a hotel with the given id exists in Wolf Villas or null otherwise 
	 */
	public static Hotel getHotelByID(int hotelID) {
		for ( Hotel h : hotels) {
			if ( h.getID() == hotelID )
				return h;
		}
		return null;
	}
}