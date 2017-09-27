package models;

import java.sql.SQLException;

import actions.get.GetRoom;
import sql.ReservationDBA;
import sql.RoomDBA;

/**
 * POJO for the WolfVillas DBMS from; CSC440 Fall 2016 Course Project
 * Represents a Reservation in the system
 * Required fields: customerID, hotelID, room number, and checkIn date 
 * 
 * @author Anusha Balaji (abalaji)
 *
 */
public class Reservation {

	private int customerID;
	private int hotelID;
	private int room;
	private String checkInDate;
	private String checkOutDate;
	private boolean occupancy;
	private int balance;
	private Bill bill;

	/**
	 * Creates a reservation with the given parameters 
	 * Also, responsible for marking the given room unavailable. 
	 * Note: Bill is not generated yet 
	 * @param customerID customer's id associated with this reservation 
	 * @param hotelID hotel id associated with this reservation 
	 * @param room room number associate with this reservation 
	 * @param checkInDate check in date associated with this reservation. Required format: dd/mm/yyyy
	 */
	public Reservation(int customerID, int hotelID, int room, String checkInDate) {
		this.customerID = customerID;
		this.hotelID = hotelID;
		this.room = room;
		this.checkInDate = checkInDate;
		bill = new Bill(customerID, hotelID, room);
	}

	/**
	 * Used only for loading from database purposes when retrieving a particular reservation 
	 * @param customerID customer id of the customer associated with this reservation 
	 * @param room roomNumber associated with this reservation 
	 * @param checkIn check in date associated with this reservation 
	 */
	public Reservation(int customerID, int room, String checkIn) {
		this.customerID = customerID;
		this.room = room;
		this.checkInDate = checkIn;
	}

	/**
	 * Getter method for reservation class
	 * @return customer ID number
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * Setter method for reservation class
	 * @param customerID the customers ID
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * Getter method for reservation class
	 * @return Hotel ID number
	 */
	public int getHotelID() {
		return hotelID;
	}

	/**
	 * Setter method for reservation class
	 * @param hotelID the hotel's ID
	 */
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	/**
	 * Getter method for reservation class
	 * @return room attached to this reservation
	 */
	public int getRoom() {
		return room;
	}


	/**
	 * Setter method for reservation class
	 * @param room the reserved room
	 */
	public void setRoom(int room) {
		this.room = room;
	}
	
	/**
	 * Getter method for reservation class
	 * @return check in date
	 */
	public String getCheckInDate() {
		return checkInDate;
	}


	/**
	 * Setter method for reservation class
	 * @param checkInDate date of check in
	 */
	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	/**
	 * Getter method for reservation class
	 * @return check out date
	 */
	public String getCheckOutDate() {
		return checkOutDate;
	}


	/**
	 * Setter method for reservation class
	 * @param checkOutDate date of check out
	 */
	public void setCheckOutDate(String date) {
		this.checkOutDate = date;
	}
	
	/**
	 * when retreiving datat from the server it returns it in the format
	 * yyyy-mm-dd hh:mm:ss.s which did not work for us. 
	 * @param date
	 * @return
	 */
	public static String formatDate(String date) {
		String year = date.substring(0, 4);
		String month = numericMonthToLetter(Integer.parseInt(date.substring(5, 7)));
		String day = date.substring(8, 10);
		String time = date.substring(11, 19);
		
		String result = day + "-" + month + "-" + year + " " + time;
		return result;
	}
	
	/**
	 * Method to convert our numeric date types to the String format used in the databse
	 * @param month the integer representation of month
	 * @return the string representation of month
	 */
	private static String numericMonthToLetter(int month) {
		String result = "";
		switch(month) {
			case 1: 
				result = "JAN";
				break;
			case 2:
				result = "FEB";
				break;
			case 3:
				result = "MAR";
				break;
			case 4:
				result = "APR";
				break;
			case 5:
				result = "MAY";
				break;
			case 6:
				result = "JUN";
				break;
			case 7:
				result = "JUL";
				break;
			case 8:
				result = "AUG";
				break;
			case 9:
				result = "SEP";
				break;
			case 10:
				result = "OCT";
				break;
			case 11:
				result = "NOV";
				break;
			case 12:
				result = "DEC";
				break;
		}
		return result;	
	}

	/**
	 * Gets the total bill + nightlyRate * # of days for the Reservation 
	 * Make sure the bill has the latest/most accurate charges first!! 
	 * @return total balance for the reservation 
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * Setter method for reservation class
	 * @param balance the charge balance of this reservation
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}

	/**
	 * Getter method for reservation class
	 * @return Bill attached to this reservation
	 */
	public Bill getBill() {
		return bill;
	}


	/**
	 * Setter method for reservation class
	 * @param bill the bill for this reservation
	 */
	public void setBill(Bill bill) {
		this.bill = bill;
	}


	/**
	 * Setter method for reservation class
	 * @param b whether or not this room is occupied
	 */
	public void setOccupancy(boolean b) {
		this.occupancy = b;
	}

	/**
	 * Getter method for reservation class
	 * @return occupation status
	 */
	public boolean isOccupied() {
		return occupancy;
	}
}
