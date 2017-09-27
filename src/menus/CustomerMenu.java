package menus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import actions.get.GetCharges;
import actions.get.GetHotel;
import actions.get.GetPresidentialSuite;
import actions.get.GetReservations;
import actions.get.GetRoom;
import models.Bill;
import models.Charge;
import models.Customer;
import models.Hotel;
import models.PresidentialSuite;
import models.Reservation;
import models.Room;
import sql.ChargesDBA;
import sql.CustomerDBA;
import sql.HotelDBA;
import sql.ReservationDBA;
import sql.RoomDBA;
import actions.validate.ValidateCustomer;
import actions.get.GetCustomer;
import menus.OriginalMenu;

/**
 * A class containing the interface for the customer menus/view
 * @author Alex
 *
 */
public class CustomerMenu {
	
	private Customer c; //The customer currently being edited
	private CustomerDBA customerDBA; //A customer database access object
	private ChargesDBA chargesDBA; //A charges database access object
	private ReservationDBA reservationDBA; //A reservation database access object
	private HotelDBA hotelDBA; //A hotel database access object
	
	/**
	 * Null constructor for customer menu with initiations of various global variables.
	 */
	public CustomerMenu() {
		customerDBA = new CustomerDBA();
		chargesDBA = new ChargesDBA();
		reservationDBA = new ReservationDBA();
		hotelDBA = new HotelDBA();
	}
	
	/**
	 * The main class for the customer menu, provides the user with the initial options and initializes the input scanner
	 * @param console the input scanner
	 */
	public void mainCustomerMenu(Scanner console){
		//Get the customer
		System.out.print("What is your customer ID: ");
		int customerID = console.nextInt();
		if(!validCustomerID(customerID)) {
			OriginalMenu.inputError();
		}
		c = GetCustomer.getCustomer(customerDBA, customerID);
		
		//Main customer options and handling
		System.out.println("Please select the action you would like to perform");
		System.out.println("(1) View Personal Information");
		System.out.println("(2) View Reservation Information");
		System.out.println("(3) View Bill");
		
		int menuSelection = console.nextInt();
		
		if(menuSelection==1){
			viewPersonalInfo(console, customerID);
		}else if(menuSelection==2){
			viewReservationInfo(console, customerID);
		}else if(menuSelection==3){
			viewBill(console, customerID);
		}else{
			OriginalMenu.inputError();
		}
	}
	
	/**
	 * Check that the customer exists in the database
	 * @param customerID ID of the customer to check
	 * @return whether or not the customer is in the database
	 */
	private boolean validCustomerID(int customerID) {
		return ValidateCustomer.validateCustomer(customerDBA, customerID);
	}

	/**
	 * Method to show a customer their current bill.
	 * @param console input scanner
	 * @param customerID the ID of our current customer
	 */
	private void viewBill(Scanner console, int customerID) {
		System.out.println("Please enter hotel ID: ");
		int hotelID = console.nextInt();
		
		System.out.println("Please enter room number: \n");
		int roomNumber = console.nextInt();
		
		
		Bill b = GetCharges.getCharges(chargesDBA, roomNumber, customerID, hotelID);
		System.out.println("Room Number: " + b.getRoom());
		System.out.println("Hotel ID: " + b.getHotelID());
		System.out.println("Customer ID: " + b.getCustomerID());
		ArrayList<Charge> chargeList = b.getChargesList();
		for (int i = chargeList.size() - 1, j = 0; i >= 0; i--, j++) {
			Charge c = chargeList.get(i); // latest charge first 
			System.out.println("Charge - " + j);
			String serviceType = "";
			if (c.getSvcType() == 0){ 
				serviceType = "Laundry";
			}else if (c.getSvcType() == 1){ 
				serviceType = "Restaurant";
			}else if (c.getSvcType() == 2){ 
				serviceType = "Taxi";
			}else if (c.getSvcType() == 3){ 
				serviceType= "Phone";
			};
			System.out.println("\tService Name: " + serviceType + " " + c.getSvcCount());
			System.out.println("\tAmount: $" + c.getAmount());
			System.out.println("\tStaff ID: " + c.getStaffID());
		}
		
		System.out.println("Type 1 to go back");
		int customerAction = console.nextInt();	
		
		if(customerAction == 1){
			OriginalMenu.main(null);
		} else{
			OriginalMenu.inputError();
		}
	}

	/**
	 * Method to allow a customer to view reservation information.
	 * @param console input scanner
	 * @param customerID ID of the current customer
	 */
	private void viewReservationInfo(Scanner console, int customerID) {
		List<Reservation> reservations = GetReservations.getReservations(reservationDBA, customerID);
		for(int i = 0; i < reservations.size(); i++){
			Hotel hotel = GetHotel.getHotel(hotelDBA, reservations.get(i).getHotelID());
			System.out.println("(" + (i+1) + ") Hotel " + hotel.getName() + ": " + reservations.get(i).getCheckInDate().substring(0, 10) + " - "+  (reservations.get(i).getCheckOutDate() != null? reservations.get(i).getCheckOutDate().substring(0,10) : "TBD"));
		}	
		System.out.println("Enter the number of the reservation you want to view");
		int reservationNumber = console.nextInt();
		
		Reservation r = reservations.get(reservationNumber - 1);

		
		System.out.println("Room Number: " + r.getRoom());
		Hotel hotel = GetHotel.getHotel(hotelDBA, reservations.get(reservationNumber - 1).getHotelID());
		System.out.println("Hotel ID: " + hotel.getName());
		System.out.println("Customer: " + c.getName());
		System.out.println("Check In Date: " + r.getCheckInDate());
		System.out.println("Check Out Date: " + (r.getCheckOutDate() != null ? r.getCheckOutDate() : "TBD"));
		System.out.println("Balance: $" + r.getBalance());
		
		Room room = GetRoom.getRoom(new RoomDBA(), hotel.getID(), r.getRoom());
		if(room.getType() == 3){
			PresidentialSuite ps = GetPresidentialSuite.getPresidentialSuite(new RoomDBA(), r.getRoom(), hotel.getID());
			System.out.println("Catering Staff Assigned: " + ps.getCateringStaffID());
			System.out.println("Room Staff Assigned: " + ps.getRoomStaffID());
		}
		
		System.out.println("Type 1 to go back");
		int customerAction = console.nextInt();	
		
		if(customerAction == 1){
			OriginalMenu.main(null);
		}else{
			OriginalMenu.inputError();
		}
	}

	/**
	 * Method to allow customers to view their personal information excluding SSN
	 * @param console input scanner
	 * @param customerID ID of current customer
	 */
	private void viewPersonalInfo(Scanner console, int customerID) {
		System.out.println("Name: " + c.getName());
		System.out.println("Gender: " + (c.getGender()==0 ? "Male" : "Female"));
		System.out.println("Address: " + c.getAddress());
		System.out.println("Email: " + c.getEmail());
		System.out.println("Phone: " + c.getPhone());
		System.out.println("Billing Address: " + c.getBillingAddress());
		
		System.out.println("Type 1 to go back");
		int customerAction = console.nextInt();	
		
		if(customerAction == 1){
			OriginalMenu.main(null);
		}else{
			OriginalMenu.inputError();
		}
	}

}
