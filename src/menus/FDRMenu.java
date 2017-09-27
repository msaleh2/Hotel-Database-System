package menus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Hotel;
import models.Reservation;
import models.Room;
import sql.HotelDBA;
import sql.ReservationDBA;
import sql.RoomDBA;
import sql.RoomsDBA;
import actions.add.AddReservation;
import actions.edit.EditReservation;
import actions.get.GetAvRooms;
import actions.get.GetHotel;
import actions.get.GetReservations;
import actions.get.GetRoom;
import actions.validate.ValidateFrontDeskRepresentative;
import actions.validate.ValidateHotel;

/**
 * Class for the Front Desk Representative Menus/View
 * Has a superclass of StaffMenu which provides functions available to all staff members
 * @author Joanna Pollard
 *
 */
public class FDRMenu extends StaffMenu {
	
	/**
	 * Uses the generic constructor for the staff superclass
	 */
	public FDRMenu() {
		super();
	}

	/**
	 * The main menu interface for a front desk representative
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void mainFDRMenu(Scanner console, int userType) {
		System.out.println("What is the hotel id of the hotel you work at?");
		
		int hotelID = console.nextInt();
		if(!validHotel(hotelID)) {
			OriginalMenu.inputError();
		}
		
		System.out.print("What is your Staff ID:");
		int staffID = console.nextInt();
		if(!validFrontDeskRepresentative(staffID)) {
			OriginalMenu.inputError();
		}
		
		System.out.println("Please select the action you would like to perform");
		System.out.println("(1) View Personal Information");
		System.out.println("(2) Add Charge to Customer Bill");
		System.out.println("(3) Add New Customer");
		System.out.println("(4) Add New Room");
		System.out.println("(5) View/Edit Customers");
		System.out.println("(6) View/Edit Rooms");
		System.out.println("(7) Bill Customer");
		System.out.println("(8) View Hotel Occupancy");
		System.out.println("(9) Get Available Rooms");
		System.out.println("(10) Get Customers Served by Staff Member");
		System.out.println("");
		
		int menuSelection = console.nextInt();
		switch (menuSelection) {
        case 1:  getPersonalInfo(staffID, userType, console);
                 break;
        case 2:  chargeCustomerAccount(console, userType, hotelID, staffID);
                 break;
        case 3:  addNewCustomer(console, userType);
        		 break;
        case 4:  addNewRoom(console, hotelID, userType);
        		 break;
        case 5:  viewEditCustomer(console, userType, hotelID);
                 break;
        case 6:  viewEditRooms(console, hotelID, userType);
                 break;
        case 7:  billCustomer(console, hotelID);
        		 break;
        case 8:  viewHotelOccupancy(console, userType);
                 break;
        case 9:  getAvailableRooms(console, hotelID, userType);
                 break;
        case 10: getCustomerServedBy(console, userType);
                 break;
        default: OriginalMenu.inputError();
                 break;
		}
	}
	
	/**
	 * Validate that the hotel queried exists
	 * @param hotelID The ID of the hotel in question
	 * @return whether or not the hotel is in the database
	 */
	private boolean validHotel(int hotelID) {
		return ValidateHotel.validateHotel(getOtherDBA(), hotelID);
	}
	
	/**
	 * Validate that the front desk representative exists
	 * @param fdrID ID of the fdr to check for in the database
	 * @return whether or not the fdr is in the database
	 */
	private boolean validFrontDeskRepresentative(int fdrID) {
		return ValidateFrontDeskRepresentative.
				validateFrontDeskRepresentative(getFrontDeskRepresentativeDBA(), fdrID);
	}

	/**
	 * Get the available rooms for a hotel
	 * @param console input scanner
	 */
	private static void getAvailableRooms(Scanner console, int hotelID, int userType) {
		System.out.println("Choose the room type: ");
		System.out.println("(1) Economy");
		System.out.println("(2) Deluxe");
		System.out.println("(3) Executive Suite");
		System.out.println("(4) Presidential Suite");
		System.out.println("(5) All Types");		
		int roomType = console.nextInt() - 1;
		
		ArrayList<Room> response = GetAvRooms.getAvRooms(new RoomsDBA(), hotelID, roomType );
		System.out.println(response.size() + " rooms retreived.");
		for(int i = 0; i < response.size(); i++){
			System.out.println("Room " +response.get(i).getNumber());
			System.out.println("--------------");
			System.out.println("Type: "+ response.get(i).getType());
			System.out.println("Nightly Rate: " + response.get(i).getRate());
			System.out.println("");
		}
	}

	/**
	 * Bill a customer
	 */
	private static void  billCustomer(Scanner console, int hotelID) {
		//Get Customer Reservations
		//Enter number of nights and update bill
		//Edit checkout date
		System.out.println("Please enter customer ID");
		int customerID = console.nextInt();
		List<Reservation> reservations = GetReservations.getReservations(new ReservationDBA(), customerID);
		for(int i = 0; i < reservations.size(); i++){
			Hotel hotel = GetHotel.getHotel(new HotelDBA(), reservations.get(i).getHotelID());
			System.out.println("(" + (i+1) + ") Hotel " + hotel.getName() + " on " + reservations.get(i).getCheckInDate());
		}	
		System.out.println("Enter the number of the reservation you want to view");
		int reservationNumber = console.nextInt();
		
		Reservation r = reservations.get(reservationNumber - 1);
		
		Room room = GetRoom.getRoom(new RoomDBA(), hotelID, r.getRoom());
		
		System.out.println("How many nights was the stay?");
		int numberOfNights = console.nextInt();
		console.nextLine();
		System.out.println("What is the checkout date (yyyy/mm/dd?");
		String checkOutDate = console.nextLine();
		checkOutDate = checkOutDate.concat(" 12:00:00");
		
		int newBalance = r.getBalance() + (numberOfNights * room.getRate());
		
		System.out.println(EditReservation.checkout(new ReservationDBA(), r, newBalance, numberOfNights, checkOutDate));
		
	}
}
