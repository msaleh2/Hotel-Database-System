
/**
 * 
 */
package models;

import java.util.ArrayList;

/**
 * POJO for the WolfVillas DBMS from; CSC440 Fall 2016 Course Project
 * Represents a room in a hotel
 * Required fields: hotelID and number
 * 
 * @author Anusha Balaji (abalaji)
 *
 */
public class Room {
	
	private int number; //Room number
	private int type; //Room type/classification
	private int maxAllowedOccupancy; //Maximal occupancy of this room
	private int hotelID; //Hotel ID for the hotel this room is in
	private int rate; //Nightly rate for this room
	private int availability; //Occupation status of this room

	/**
	 * Constructor for Room
	 * @param number room number
	 * @param hotelID Hotel ID
	 */
	public Room(int number, int hotelID) {
		this.number = number;
		this.hotelID = hotelID;
	}
	
	/**
	 * Gets the room number for this room
	 * @return room number
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Sets the room number for this room
	 * @param number room number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Gets the room type for this room
	 * @return type of room
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Sets the room type for this room
	 * @param type room type
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * Gets the maximal occupancy for this room
	 * @return maximal occupancy
	 */
	public int getMaxAllowedOccupancy() {
		return maxAllowedOccupancy;
	}
	
	/**
	 * Sets the maximal occupancy for this room
	 * @param maxAllowedOccupancy
	 */
	public void setMaxAllowedOccupancy(int maxAllowedOccupancy) {
		this.maxAllowedOccupancy = maxAllowedOccupancy;
	}
	
	/**
	 * Gets the Hotel ID for this room
	 * @return Hotel ID
	 */
	public int getHotelID() {
		return hotelID;
	}
	
	/**
	 * Sets the Hotel ID for this room
	 * @param hotelID the hotel ID to set for this room
	 */
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}
	
	/**
	 * Gets the room rate for this room
	 * @return room rate
	 */
	public int getRate() {
		return rate;
	}
	
	/**
	 * Sets the room rate for this room
	 * @param rate room rate
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	/**
	 * Sets the availability for this room
	 * @param availability availability status of this room
	 */
	public void setAvailability(int availability) {
		this.availability = availability;
	}

	/**
	 * Makes sure the new room in hotel has a unique room number
	 * @param rooms list of room of a specific hotel
	 * @return true if room is new (room number is not taken yet)
	 * and false otherwise
	 */
	public boolean isNew(ArrayList<Room> rooms) {
		for(Room temp : rooms) {
			if ( temp.getHotelID() == hotelID && temp.getNumber() == number )
				return false;
		}
		return true;
	}

	/**
	 * Gets the availability for this room
	 * @return room availability status
	 */
	public int getAvailability() {
		return availability;
	}
}
