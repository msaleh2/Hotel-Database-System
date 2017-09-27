package models;


/**
 * POJO for the WolfVillas DBMS from; CSC440 Fall 2016 Course Project
 * Represents a Presidential Suite in the system
 * Required fields: hotelID, roomNumber, roomStaffID, cateringStaffID 
 * 
 * @author Joanna Pollard (jbpolla2)
 *
 */
public class PresidentialSuite {
	private int hotelID;
	private int roomNumber;
	private int roomStaffID;
	private int cateringStaffID;

	
	public PresidentialSuite(int hotelID, int roomNumber){
		this.hotelID = hotelID;
		this.roomNumber = roomNumber;
	}
	
	public PresidentialSuite(int hotelID, int roomNumber, int cateringStaffID, int roomStaffID){
		this.hotelID = hotelID;
		this.roomNumber = roomNumber;
		this.cateringStaffID = cateringStaffID;
		this.roomStaffID = roomStaffID;
	}
	/**
	 * @return the roomStaffID
	 */
	public int getRoomStaffID() {
		return roomStaffID;
	}
	/**
	 * @param roomStaffID the roomStaffID to set
	 */
	public void setRoomStaffID(int roomStaffID) {
		this.roomStaffID = roomStaffID;
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
	 * @return the roomNumber
	 */
	public int getRoomNumber() {
		return roomNumber;
	}
	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	/**
	 * @return the cateringStaffID
	 */
	public int getCateringStaffID() {
		return cateringStaffID;
	}
	/**
	 * @param cateringStaffID the cateringStaffID to set
	 */
	public void setCateringStaffID(int cateringStaffID) {
		this.cateringStaffID = cateringStaffID;
	}
}
