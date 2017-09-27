/**
 * 
 */
package models;

import java.util.ArrayList;

/**
 * POJO for the WolfVillas DBMS from; CSC440 Fall 2016 Course Project
 * 
 * 
 * @author Anusha Balaji (abalaji)
 *
 */
public class Bill {

	private ArrayList<Charge> chargesList; //List of charges linked to this bill
	private int customerID; //Customer ID linked to this bill
	private int hotelID; //hotel ID linked to this bill
	private int room; //room number for this bill
	
	/**
	 * Constructor method for the bill class
	 * @param customerID Customer who is being billed
	 * @param hotelID Hotel the customer is at
	 * @param room Room the customer is in
	 */
	public Bill(int customerID, int hotelID, int room) {
		this.customerID = customerID;
		this.hotelID = hotelID;
		this.room = room;
		chargesList = new ArrayList<Charge>();
	}
	
	/**
	 * Empty constructor for purposes of avoiding null pointers
	 * Initializes chargesList.
	 */
	public Bill() {
		chargesList = new ArrayList<Charge>();
	}

	/**
	 * Method to add a charge to a Bill object
	 * @param temp charge to add
	 */
	public void addCharge(Charge temp){
		chargesList.add(temp);
	}

	/**
	 * Getter method for the Bill class
	 * @return a list of charges associated with this bill
	 */
	public ArrayList<Charge> getChargesList() {
		return chargesList;
	}

	/**
	 * Setter method for the Bill class
	 * @param chargesList set the list of charges on this bill
	 */
	public void setChargesList(ArrayList<Charge> chargesList) {
		this.chargesList = chargesList;
	}

	/**
	 * Getter method for the Bill class
	 * @return the customer ID linked to this bill
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * Setter method for the Bill class
	 * @param customerID the customer's ID
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * Getter method for the Bill class
	 * @return Hotel ID
	 */
	public int getHotelID() {
		return hotelID;
	}

	/**
	 * Setter method for the Bill class
	 * @param hotelID the hotel's ID
	 */
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	/**
	 * Getter method for the Bill class
	 * @return room number linked to this bill
	 */
	public int getRoom() {
		return room;
	}

	/**
	 * Setter method for the Bill class
	 * @param room (room number)
	 */
	public void setRoom(int room) {
		this.room = room;
	}

	/**
	 * Method to add a charge to this bill
	 * @param temp the charge to add.
	 */
	public void add(Charge temp) {
		chargesList.add(temp);
		
	}
}
