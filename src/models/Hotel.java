/**
 * 
 */
package models;

import java.util.ArrayList;

/**
 * POJO for the WolfVillas DBMS from; CSC440 Fall 2016 Course Project
 * Represents a hotel in the WolfVillas hotel chain
 * 
 * @author Anusha Balaji (abalaji) and Joanna Pollard (jbpolla2)
 *
 */
public class Hotel {
	
	private int ID; //Hotel ID for this hotel
	private String name; //Name of this hotel
	private String address; //Hotel address
	private String phone; //Hotel phone
	private int managerID; //Hotel manager
	private ArrayList<Room> rooms; //List of rooms in hotel
	
	/**
	 * Hotel class constructor based on a hotel's ID
	 * @param ID a hotel's ID
	 */
	public Hotel(int ID) {
		this.ID = ID;
		rooms = new ArrayList<Room>();
	}
	
	/**
	 * Getter method for Hotel Class
	 * @return hotel ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Setter method for Hotel Class
	 * @param ID Hotel ID
	 */
	public void setID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Getter method for Hotel Class
	 * @return name of hotel
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for Hotel Class
	 * @param name hotel name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for Hotel Class
	 * @return hotel address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter method for Hotel Class
	 * @param address hotel address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Getter method for Hotel Class
	 * @return hotel phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter method for Hotel Class
	 * @param phone hotel phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * Getter method for Hotel Class
	 * @return hotel manager ID
	 */
	public int getManagerID() {
		return managerID;
	}

	/**
	 * Setter method for Hotel Class
	 * @param managerID hotel manager ID
	 */
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	
	/**
	 * Getter method for Hotel Class
	 * @return List of rooms in the hotel
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}

	/**
	 * Setter method for Hotel Class
	 * @param rooms list of rooms in the hotel
	 */
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
	/**
	 * Add a room under some condition
	 * @param temp room to check and possibly add
	 * @return if the room should be added
	 */
	public boolean addRoomIfNew(Room temp){
		if (temp.isNew(rooms)) {
			rooms.add(temp);
			return true;
		}
		return false;
	}
}
