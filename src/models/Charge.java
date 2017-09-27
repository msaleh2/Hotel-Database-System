/**
 * 
 */
package models;

/**
 * POJO for the WolfVillas DBMS from; CSC440 Fall 2016 Course Project
 * Represents a charge an employee can add to a reservation for services availed
 * including room service, taxi, restaurant, in-store/in-hotel purchases, phone, and laundry
 * Required fields: serviceType, amount, and description
 * 
 * @author Anusha Balaji (abalaji)
 */
public class Charge {

	/** identification fields */
	private int room, hotelID, customerID;
	/** charge atrributes */
	private int staffID, svcCount, svcType;
	private int amount;
	
	/**
	 * Charge class constructor method
	 */
	public Charge(int room, int hotelID, int customerID, int staffID, int svcCount, int svcType, int amountCharged) {
		this.setRoom(room);
		this.setHotelID(hotelID);
		this.setCustomerID(customerID);
		this.staffID = staffID;
		this.setSvcCount(svcCount);
		this.svcType = svcType;
		this.amount = amountCharged;
	}
	
	/**
	 * Charge class constructor method
	 */
	public Charge(int room, int hotelID, int customerID, int staffID) {
		this.setRoom(room);
		this.setHotelID(hotelID);
		this.setCustomerID(customerID);
		this.staffID = staffID;
		svcType = 0;
		setSvcCount(0);
		amount = 0;
	}

	/**
	 * Null constructor for the charge class
	 */
	public Charge(){
		room = 0;
		hotelID = 0;
		customerID = 0;
		staffID = 0;
	}
	
	/**
	 * Tests whether or not a customer needs a new bill or not
	 * @return whether or not a customer needs a new bill
	 */
	public boolean isNew(){
		if (room == 0 || hotelID == 0 || customerID == 0 || staffID == 0)
			return true;
		return false;
	}
	
	/**
	 * Used for testing
	 * @return string representation for present charge 
	 */
	public String toString() {
		String s = "";
		if (svcType == 0)
			s = "Laundry Bill ";
		else if (svcType == 1)
			s = "Restaurant bill ";
		else if (svcType == 2)
			s = "Taxi bill ";
		else if (svcType == 3)
			s = "Phone bill ";
		else 
			return null;
		return s += svcCount + " for customer " + customerID + " for $" + amount;
	}
	
	/**
	 * Getter method for the Charge class
	 * @return svcType the service type of the charge
	 */
	public int getSvcType() {
		return svcType;
	}
	
	/**
	 * Setter method for the Charge class
	 * @param set the service type of the charge
	 */
	public void setServiceType(int svcType) {
		this.svcType = svcType;
	}
	
	/**
	 * Getter method for the Charge class
	 * @return the amount of the charge
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * Setter method for the Charge class
	 * @param amount the amount of the charge
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * Setter method for the Charge class
	 * @param staffID the Staff member who created the charge's ID
	 */
	public void setStaffID(int staffID) {
		this.staffID = staffID;	
	}
	
	/**
	 * Getter method for the Charge class
	 * @return Staff ID of staff member who created the charge
	 */
	public int getStaffID(){
		return staffID;
	}

	/**
	 * @return the room
	 */
	public int getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(int room) {
		this.room = room;
	}

	/**
	 * @return the hotelID
	 */
	public int getHotelID() {
		return hotelID;
	}

	/**
	 * @param hotelID the hotelID to set
	 */
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return the svcCount
	 */
	public int getSvcCount() {
		return svcCount;
	}

	/**
	 * @param svcCount the svcCount to set
	 */
	public void setSvcCount(int svcCount) {
		this.svcCount = svcCount;
	}
	
}
