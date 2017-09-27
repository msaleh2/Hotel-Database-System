package menus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import actions.add.AddCharge;
import actions.add.AddCustomer;
import actions.add.AddReservation;
import actions.add.AddRoom;
import actions.add.AddStaff;
import actions.delete.DeleteCustomer;
import actions.delete.DeleteReservation;
import actions.delete.DeleteRoom;
import actions.edit.EditCustomer;
import actions.edit.EditReservation;
import actions.edit.EditRoom;
import actions.get.GetAvRooms;
import actions.get.GetCharges;
import actions.get.GetCustomer;
import actions.get.GetCustomerServedBy;
import actions.get.GetHotel;
import actions.get.GetOccupancy;
import actions.get.GetOccupancyPercentage;
import actions.get.GetPresidentialSuite;
import actions.get.GetReservations;
import actions.get.GetRoom;
import actions.get.GetStaff;
import actions.validate.ValidateHotel;
import actions.validate.ValidateRoom;
import actions.validate.ValidateStaff;
import models.Bill;
import models.Charge;
import models.Customer;
import models.Hotel;
import models.PresidentialSuite;
import models.Reservation;
import models.Room;
import models.Staff;
import sql.ChargesDBA;
import sql.CustomerDBA;
import sql.FrontDeskRepresentativeDBA;
import sql.HotelDBA;
import sql.ManagerDBA;
import sql.OtherDBA;
import sql.ReservationDBA;
import sql.RoomDBA;
import sql.RoomsDBA;
import sql.StaffDBA;

/**
 * Class for generic staff menus/view
 * @author Alex
 *
 */
public class StaffMenu {
	
	private HotelDBA hotelDBA; //Hotel database access object
	private CustomerDBA customerDBA; //Customer database access object
	private StaffDBA staffDBA; //Staff database access object
	private ManagerDBA managerDBA; //Manager database access object
	private FrontDeskRepresentativeDBA frontDeskRepresentativeDBA; //Front Desk Representative database access object
	private RoomDBA roomDBA; //Room database access object
	private ReservationDBA reservationDBA; //Reservation database access object
	private OtherDBA otherDBA; //Miscellaneous database access object
	private ChargesDBA chargesDBA; //Charges database access object
	
	/**
	 * Constructor for the staff menu class, initializes the basic access objects for a staff member
	 */
	public StaffMenu() {
		hotelDBA = new HotelDBA();
		customerDBA = new CustomerDBA();
		staffDBA = new StaffDBA();
		managerDBA = new ManagerDBA();
		frontDeskRepresentativeDBA = new FrontDeskRepresentativeDBA();
		roomDBA = new RoomDBA();
		reservationDBA = new ReservationDBA();
		otherDBA = new OtherDBA();
		chargesDBA = new ChargesDBA();
	}
	
	/**
	 * Main staff menu interface, allows a staff member to select what they want to do.
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void mainStaffMenu(Scanner console, int userType){
		System.out.println("What is the hotel id of the hotel you work at?");
		int hotelID = console.nextInt();
		if(!validHotel(hotelID)) {
			OriginalMenu.inputError();
		}
		System.out.print("What is your Staff ID:");
		int staffID = console.nextInt();
		if(!validStaffID(staffID)) {
			OriginalMenu.inputError();
		}
		System.out.println("Please select the action you would like to perform");
		System.out.println("(1) View Personal Information");
		System.out.println("(2) Add Charge to Customer Bill");
		
		int menuSelection = console.nextInt();
		
		if(menuSelection==1) {
			getPersonalInfo(staffID, userType, console);
		}else if(menuSelection==2) {
			chargeCustomerAccount(console, userType, hotelID, staffID);
		}else{
			OriginalMenu.inputError();
		}
	}
	
	/**
	 * Validates that a hotel exists in the database
	 * @param hotelID ID of the hotel to validate
	 * @return whether or not the hotel exists
	 */
	private boolean validHotel(int hotelID) {
		return ValidateHotel.validateHotel(getOtherDBA(), hotelID);
	}
	
	/**
	 * Validates that a staff member exists in the database
	 * @param staffID ID of the staff member
	 * @return whether or not the staff member exists
	 */
	private boolean validStaffID(int staffID) {
		return ValidateStaff.validateStaff(staffDBA, staffID);
	}
	
	/**
	 * Returns the personal information of the current staff member
	 * @param staffID ID of the current staff member
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 * @param console input scanner
	 */
	public void getPersonalInfo(int staffID, int userType, Scanner console){
		Staff staff = GetStaff.getStaff(staffDBA, staffID);
		System.out.println("Name: " + staff.getName());
		System.out.println("Job Title" + staff.getJobTitle());
		//TODO - get string instead of int
		System.out.println("Hotel Serving: " + staff.getHotelID());
		System.out.println("Age: "+ staff.getAge());
		System.out.println("Gender: " + (staff.getGender()==0 ? "Male" : "Female"));
		System.out.println("Phone: " + staff.getPhone());
		System.out.println("Address: " + staff.getAddress());
		System.out.println("Department: " + staff.getDept());
		
		staffGoBack(console, userType);
	}
	
	/**
	 * Method used to redirect a staff member to the appropriate sub menu or the main menu
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void staffGoBack(Scanner console, int userType) {
		System.out.println("Type 1 to go back to sub-menu or 2 to go back to main menu.");
		int customerAction = console.nextInt();	
		
		if(customerAction == 1) {
			if(userType == 2) {
				mainStaffMenu(console, userType);
			}
			else if(userType == 3) {
				ManagerMenu menu = new ManagerMenu();
				((ManagerMenu) menu).mainManagerMenu(console, userType);
			}
			else if(userType == 4) {
				FDRMenu menu = new FDRMenu();
				((FDRMenu) menu).mainFDRMenu(console, userType);
			} else {
				OriginalMenu.inputError();
			}
		} else if (customerAction == 2) {
			OriginalMenu.main(null);
		} else {
			OriginalMenu.inputError();
		}
	}
	
	/**
	 * Interface for charging a customer account
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 * @param hotelID ID of the hotel the charge is occuring at
	 * @param staffID ID of the staff member inserting the charge
	 */
	public void chargeCustomerAccount(Scanner console, int userType, int hotelID, int staffID){
		System.out.println("Please enter customer ID: ");
		int customerID = console.nextInt();
		System.out.println("Choose from the following reservations: ");
		List<Reservation> reservations = GetReservations.getReservations(reservationDBA, customerID);
		for(int i = 0; i < reservations.size(); i++){
			Hotel hotel = GetHotel.getHotel(hotelDBA, reservations.get(i).getHotelID());
			System.out.println("(" + (i+1) + ") Hotel " + hotel.getName() + " on " + reservations.get(i).getCheckInDate());
		}	
		System.out.println("Enter the number of the reservation you want to add a charge to.");
		int reservationNumber = console.nextInt();
		Reservation r = reservations.get(reservationNumber - 1);
		System.out.println("Please choose a type of service");
		System.out.println("(1) Laundry");
		System.out.println("(2) Restaurant");
		System.out.println("(3) Taxi");
		System.out.println("(4) Phone");
		int serviceType = console.nextInt();
		if(serviceType>4){
			OriginalMenu.inputError();
		}
		System.out.println("Please enter amount charged: ");
		int amountCharged = console.nextInt();
		
		Charge c = new Charge(r.getRoom(), hotelID, customerID, staffID, 
				getCount(r.getRoom(), customerID, hotelID, serviceType-1), serviceType -1, amountCharged);
		String message = AddCharge.addCharges(chargesDBA, r, c);
		System.out.println(message);
		staffGoBack(console, userType);
	}
	
	/**
	 * Get the number of times each service has been used
	 * @param roomNumber ID for the room in which the services were used
	 * @param customerID ID for the customer who ordered the services
	 * @param hotelID ID for the hotel in which the services were ordered
	 * @param svcType integer representation of the type of service to query for
	 * @return the number of services of this type for the specified user/visit
	 */
	private int getCount(int roomNumber, int customerID, int hotelID, int svcType) {
		Bill b = GetCharges.getCharges(chargesDBA, roomNumber, customerID, hotelID);
		ArrayList<Charge> chargeList = b.getChargesList();
		if(chargeList.size()==0) {
			return 1; //empty list when reservation is just created and first charge is added
		}
		int countNumber = 0;
		for (int i = chargeList.size() - 1; i >= 0; i--) {
			Charge c = chargeList.get(i); // latest charge first 
			if(c.getSvcCount() > countNumber){
				if(c.getSvcType() == svcType){
					countNumber = c.getSvcCount();
				}
			}
		}
		return countNumber + 1;
	}

	/**
	 * Interface for staff to view and edit rooms
	 * @param console input scanner
	 * @param hotelID ID of the hotel the rooms are in
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void viewEditRooms(Scanner console, int hotelID, int userType){
		System.out.println("Please enter the room number: ");
		int roomNumber = console.nextInt();
		if(!validRoom(roomNumber)) {
			OriginalMenu.inputError();
		}
		Room room = GetRoom.getRoom(new RoomDBA(), hotelID, roomNumber);
		System.out.println("Number: " + room.getNumber());
		Hotel hotel = GetHotel.getHotel(hotelDBA, room.getHotelID());
		System.out.println("Hotel: " + hotel.getName());
		
		String roomString = "";
		if(room.getType() == 0){
			roomString = "Economy";
		}if(room.getType() == 1){
			roomString = "Deluxe";
		}if(room.getType() == 2){
			roomString = "Executive Suite";
		}if(room.getType() == 3){
			roomString = "Presidential Suite";
		}
		System.out.println("Type: " + roomString);
		
		System.out.println("Rate: " + room.getRate());
		System.out.println("Availability: " + ((room.getAvailability() != 0) ? "Available" : "Not Available"));
		
		System.out.println("Please select from the following actions: ");
		System.out.println("(1) Edit");
		System.out.println("(2) Delete");
		int roomAction = console.nextInt();
		console.nextLine(); //consume the last newline character 
		if(roomAction == 1){
			System.out.println("Please enter data for the attributes you would like to edit. "
					+ "If you don't want a value to change, enter \"-\".");
			System.out.println("Choose room type");
			System.out.println("(1) Economy");
	        System.out.println("(2) Deluxe");
	        System.out.println("(3) Executive Suite");
	        System.out.println("(4) Presidential Suite");
	        String typeString = console.next();
			int type = (!typeString.equals("-")) ? Integer.parseInt(typeString): room.getType();
			
			
			System.out.println("Enter room rate (integer)");
			String roomRateString = console.next();
			int roomRate = (!typeString.equals("-")) ? Integer.parseInt(roomRateString): room.getRate();
			
			System.out.println("Set availability of the room. [0 - not available. 1 - available.]");
			String availability = console.next();
			int availabilityNum = (!typeString.equals("-")) ? Integer.parseInt(availability): room.getAvailability();
			
			Room editedRoom = new Room(roomNumber, hotelID);
			editedRoom.setAvailability(availabilityNum);
			editedRoom.setRate(roomRate);
			editedRoom.setType(type-1);
			
			System.out.println(EditRoom.editRoom(roomDBA, editedRoom));
			
		}else if(roomAction==2){
			System.out.println("Are you sure you want to delete this room? (Type 1 for Yes and 2 for No)");
			int delete = console.nextInt();
			if(delete == 1){
				 DeleteRoom.deleteRoom(roomDBA, room);
				System.out.println("Room Deleted");
			}else if(delete == 2){
				viewEditRooms(console, hotelID, userType);
			}
		}else{
			OriginalMenu.inputError();
		}
		
		staffGoBack(console, userType);
	}
	
	/**
	 * Validates that a room exists in the database
	 * @param hotelID ID of the hotel to validate
	 * @return whether or not the hotel exists
	 */
	private boolean validRoom(int roomNumber) {
		return ValidateRoom.validateRoom(getRoomDBA(), roomNumber);
	}

	/**
	 * Method for staff members to view and edit customers
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 * @param hotelID 
	 */
	public void viewEditCustomer(Scanner console, int userType, int hotelID){
		System.out.println("Please enter the customer ID: ");
		int customerID = console.nextInt();
		Customer c = GetCustomer.getCustomer(customerDBA, customerID);
		System.out.println("Name: " + c.getName());
		System.out.println("Gender: " + (c.getGender()==0 ? "Male" : "Female"));
		System.out.println("Address: " + c.getAddress());
		System.out.println("Email: " + c.getEmail());
		System.out.println("Phone: " + c.getPhone());
		System.out.println("Please select from the following actions: ");
		System.out.println("(1) Edit Customer");
		System.out.println("(2) Delete Customer");
		System.out.println("(3) View/Edit/Delete Reservations");
		System.out.println("(4) Add New Reservation");
		int customerAction = console.nextInt();
		if(customerAction == 1){
			console.nextLine(); //consume the last newline character 
			System.out.println("Please enter data for the attributes you would like to edit. "
					+ "\nIf you don't want a value to change, Enter \"-\" to keep the old information.");
			
			System.out.print("Enter customer name: ");
			String name = console.nextLine();
			name = (!name.equals("-")) ? name: c.getName();
			
			System.out.print("Enter customer SSN: ");
			String ssnString = console.nextLine();
			int ssn = (!ssnString.equals("-")) ? Integer.parseInt(ssnString): c.getSSN();
			
			System.out.print("Enter customer email: ");
			String email = console.nextLine();
			email = (!email.equals("-")) ? email: c.getEmail();
			
			System.out.print("Enter customer address: ");
			String address = console.nextLine();
			address = (!address.equals("-")) ? address: c.getAddress();
			
			System.out.print("Enter customer phone: ");
			String phoneString = console.nextLine();
			long phone = (!phoneString.equals("-")) ? Long.parseLong(phoneString): c.getPhone();
			
			System.out.print("Enter customer gender [1 for male and 2 for female]: ");
			String genderString = console.nextLine();
			int gender = (!genderString.equals("-")) ? Integer.parseInt(genderString): c.getGender();
			
			System.out.print("Enter customer card number: ");
			String cardNumberString = console.nextLine();
			long cardNumber = (!cardNumberString.equals("-")) ? Long.parseLong(cardNumberString): c.getCardNumber();
			
			System.out.print("Enter customer billing address: ");
			String billingAddress = console.nextLine();
			billingAddress = (!billingAddress.equals("-")) ? billingAddress: c.getBillingAddress();
			
			Customer editedCustomer = new Customer(c.getId());
			editedCustomer.setName(name);
			editedCustomer.setSSN(ssn);
			editedCustomer.setEmail(email);
			editedCustomer.setAddress(address);
			editedCustomer.setPhone(phone);
			editedCustomer.setGender(gender-1);
			editedCustomer.setCardNumber(cardNumber);
			editedCustomer.setBillingAddress(billingAddress);
			System.out.println(EditCustomer.editCustomer(customerDBA, editedCustomer));
		}else if(customerAction==2){
			System.out.println("Are you sure you want to delete this customer? (Type 1 for Yes and 2 for No)");
			int delete = console.nextInt();
			if(delete == 1){
				System.out.println(DeleteCustomer.deleteCustomer(customerDBA, customerID));
			}else if(delete ==2){
				viewEditCustomer(console, userType, hotelID);
			}else{OriginalMenu.inputError();}
		}else if(customerAction ==3){
			customerReservations(console, userType, customerID, reservationDBA, hotelID);
		}else if(customerAction ==4){
			addNewReservation(console, userType, customerID, hotelID);
		}else{
			OriginalMenu.inputError();
		}
		
		staffGoBack(console, userType);
	}
	
	private void addNewReservation(Scanner console, int userType, int customerID, int hotelID) {
		/*System.out.println("Would you like to view available rooms in your hotel? (y/n): ");
		String resp = console.next();

		if (resp.equals("y")) {
			getAvailableRooms(console, 0, userType);
		} else if (!resp.equals("y")) {
			OriginalMenu.inputError();
		}*/

		System.out.print("Please enter the reservation's room number: ");
		int roomNumber = console.nextInt();
		Room room = GetRoom.getRoom(new RoomDBA(), hotelID, roomNumber);
		console.nextLine(); //consume the last newline character
		System.out.print("Check-in date (yyyy-mm-dd): ");
		String checkInDate = console.nextLine();
		checkInDate = checkInDate.concat(" 12:00:00");
		
		System.out.println(checkInDate);
		
		Reservation r = new Reservation(customerID, hotelID, roomNumber, checkInDate);
		r.setBalance(0);
		r.setOccupancy(true);
		String message;
		if(room.getType() == 3){
			System.out.println("Enter the id of the catering staff you want to assign to this presidential suite:");
			int cateringStaff = console.nextInt();
			System.out.println("Enter the id of the room staff you want to assign to this presidential suite:");
			int roomStaff = console.nextInt();
			message = AddReservation.addPresidentialReservation(reservationDBA, r, cateringStaff, roomStaff);
		}else{
			message = AddReservation.addReservation(reservationDBA, r);
		}
		System.out.println(message + '\n');

		staffGoBack(console, userType);
	}

	private void getAvailableRooms(Scanner console, int hotelID,int userType) {
		System.out.println("Available rooms in hotel " + hotelID + ": \n" + 
				"-----------------------------------------------------");
		ArrayList<Room> response = GetAvRooms.getAvRooms(new RoomsDBA(), hotelID, userType);
		for(int i = 0; i <= response.size(); i++){
			System.out.println("Room " +response.get(i).getNumber());
			System.out.println("--------------");
			System.out.println("Type: "+ response.get(i).getType());
			System.out.println("Nightly Rate: " + response.get(i).getRate());
			System.out.println("");
		}
		System.out.println();
		staffGoBack(console, userType);
	}

	/**
	 * Method to retrieve and view a customer reservation
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 * @param customerID ID of the customer holding the reservation
	 * @param dba reservation database access object
	 */
	private void customerReservations(Scanner console, int userType, int customerID, ReservationDBA dba, int hotelID) {
		List<Reservation> reservations = GetReservations.getReservations(reservationDBA, customerID);
		for(int i = 0; i < reservations.size(); i++){
			Hotel hotel = GetHotel.getHotel(hotelDBA, reservations.get(i).getHotelID());
			System.out.println("(" + (i+1) + ") Hotel " + hotel.getName() + ": " + reservations.get(i).getCheckInDate().substring(0, 10) + " - "+  (reservations.get(i).getCheckOutDate() != null? reservations.get(i).getCheckOutDate().substring(0,10) : "TBD"));
		}
		if(reservations.size() == 0) {
			System.out.println("No reservations to view. Exiting.");
			OriginalMenu.inputError();
		}
		System.out.println("Enter the number of the reservation you want to view");
		int reservationNumber = console.nextInt();
		
		Reservation r = reservations.get(reservationNumber - 1);
		
		System.out.println("Room Number: " + r.getRoom());
		Hotel hotel = GetHotel.getHotel(hotelDBA, reservations.get(reservationNumber - 1).getHotelID());
		System.out.println("Hotel ID: " + hotel.getName());
		Customer c = GetCustomer.getCustomer(customerDBA, customerID);
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
		
		System.out.println("Action Options: ");
		System.out.println("(1) Delete Reservation");
		System.out.println("(2) Edit Reservation");
		int userAction = console.nextInt();
		if(userAction == 1){
			if(room.getType() == 3){
				DeleteReservation.deletePresidentialReservation(dba, r);
			}
			String message = DeleteReservation.deleteReservation(dba, r);
			System.out.println(message);
		}else if(userAction == 2){
			System.out.println("If you do not want to edit a field, insert a '-' as your input");
			console.nextLine();
			System.out.print("Please enter the reservation's room number: ");
			String roomNumberString = console.nextLine();
			int roomNumber = (!roomNumberString.equals("-")) ? Integer.parseInt(roomNumberString): r.getRoom();
			System.out.print("Check-in date (yyyy-mm-dd): ");
			String checkInDate = console.nextLine();
			checkInDate = (!checkInDate.equals("-")) ? checkInDate.concat(" 12:00:00") : r.getCheckInDate();
			System.out.print("Balance: ");
			String balanceString = console.nextLine();
			int balance = (!balanceString.equals("-")) ? Integer.parseInt(balanceString) : r.getBalance();
			Reservation editedReservation = new Reservation(customerID, hotelID, roomNumber, checkInDate);
			editedReservation.setCheckInDate(checkInDate);
			editedReservation.setBalance(balance);
			editedReservation.setRoom(roomNumber);
			
			String message = "";
			if(room.getType() == 3){
				PresidentialSuite ps = GetPresidentialSuite.getPresidentialSuite(new RoomDBA(), roomNumber, hotelID);
				System.out.println("Enter the id of the catering staff you want to assign to this presidential suite:");
				String cateringString = console.nextLine();
				int cateringStaff = (!cateringString.equals("-")? Integer.parseInt(cateringString) : ps.getCateringStaffID());
				System.out.println("Enter the id of the room staff you want to assign to this presidential suite:");
				String roomString = console.nextLine();
				int roomStaff = (!roomString.equals("-")? Integer.parseInt(roomString) : ps.getRoomStaffID());
				message = EditReservation.editPresidentialReservation(reservationDBA, editedReservation, cateringStaff, roomStaff, r);
			}else{
				message = EditReservation.editReservation(dba, editedReservation, r);
			}
			System.out.println(message + '\n');
		}
		
		staffGoBack(console, userType);
		
	}

	/**
	 * 
	 * @param console
	 * @param userType
	 */
	public void viewHotelOccupancy(Scanner console, int userType){
		int total = GetOccupancy.getOccupancy(otherDBA);
		System.out.println("Occupancy: " + total);
		
		staffGoBack(console, userType);
	}
	
	/**
	 * Method for adding a new staff member to the database
	 * @param console input scanner
	 * @param hotelID ID of the hotel to add the staff member at
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void addNewStaff(Scanner console, int hotelID, int userType) {
		console.nextLine();
		Staff staff = new Staff();
		
		System.out.println("Name: ");
		String name = console.nextLine();
		staff.setName(name);
		
		System.out.println("Job Title: ");
		System.out.println("Choose one of the following Departments");
		System.out.println("(1) Manager");
		System.out.println("(2) Front Desk Representative");
		System.out.println("(3) Catering Staff");
		System.out.println("(4) Room Staff");
		int jobTitle = console.nextInt();
		staff.setJobTitle(jobTitle - 1);
		
		staff.setHotelID(hotelID);
		
		System.out.println("Age: ");
		int age = console.nextInt();
		staff.setAge(age);
		
		System.out.println("Gender: ");
		System.out.println("Type 1 for Male and 2 for Female");
		int gender = console.nextInt();
		staff.setGender(gender - 1);

		System.out.println("Phone: ");
		long phone = console.nextLong();
		staff.setPhone(phone);
		console.nextLine();
		System.out.println("Address: ");
		String address = console.nextLine();
		staff.setAddress(address);
		
		System.out.println("Choose one of the following Departments");
		System.out.println("(1) Administration");
		System.out.println("(2) Catering");
		System.out.println("(3) ");
		System.out.println("(4) ");
		int dept = console.nextInt();
		//TODO set DeptString to actual String
		String deptString = "";
		if(dept == 1){
			deptString = "Administration";
		}else if(dept == 2){
			deptString = "Catering";
		}else if(dept == 3){
			deptString = "Administration";
		}else if(dept == 4){
			deptString = "Administration";
		}
		staff.setDept(deptString);
		
		System.out.println("Enter 9-digit Social Security Number");
		int ssn = console.nextInt();
		staff.setSsn(ssn);
		
		System.out.println("Please enter salary for employee");
		int salary = console.nextInt();
		staff.setSalary(salary);
		
		System.out.println(AddStaff.addStaff(staffDBA, staff));
		
		staffGoBack(console, userType);
		
	}

	/**
	 * Method for a staff member to add a new room to a hotel
	 * @param console input scanner
	 * @param hotelID ID of hotel to add the room at
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void addNewRoom(Scanner console, int hotelID, int userType) {
		System.out.println("Enter the new room number");
		int roomNumber = console.nextInt();
		console.nextLine(); //consume the last newline character 
		System.out.println("Choose room type");
		System.out.println("(1) Economy");
        System.out.println("(2) Deluxe");
        System.out.println("(3) Executive Suite");
        System.out.println("(4) Presidential Suite");
		int type = console.nextInt();
		console.nextLine(); //consume the last newline character 
		System.out.println("Enter room rate (integer): ");
		int rate = console.nextInt();
		console.nextLine(); //consume the last newline character 
		System.out.println("");
		
		Room r = new Room(roomNumber, hotelID);
		r.setRate(rate);
		r.setAvailability(1);
		r.setType(type-1);
		String message = "";
		System.out.println(type);
		if(type == 4){
			message = AddRoom.addPresidentialRoom(roomDBA, r);
		}else{
			message = AddRoom.addRoom(roomDBA, r);
		}
		System.out.println(message);
		
		staffGoBack(console, userType);
	}

	/**
	 * Method to add a new customer to the database
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void addNewCustomer(Scanner console, int userType) {
		console.nextLine(); //consume the last newline character 
		System.out.println("Enter customer name");
		String name = console.nextLine();
		System.out.println("Enter customer SSN");
		int ssn = console.nextInt();
		console.nextLine(); //consume the last newline character 
		System.out.println("Enter customer email");
		String email = console.nextLine();
		System.out.println("Enter customer address");
		String address = console.nextLine();
		System.out.println("Enter customer phone");
		Long phone = console.nextLong();
		console.nextLine(); //consume the last newline character 
		System.out.println("Enter customer gender [1 for male and 2 for female]");
		int gender = console.nextInt();
		console.nextLine(); //consume the last newline character 
		System.out.println("Enter customer card number");
		Long cardNumber = console.nextLong();
		console.nextLine(); //consume the last newline character 
		System.out.println("Enter customer billing address");
		String billingAddress = console.nextLine();
		
		Customer c = new Customer();
		c.setName(name);
		c.setSSN(ssn);
		c.setEmail(email);
		c.setAddress(address);
		c.setPhone(phone);
		c.setGender(gender-1);
		c.setCardNumber(cardNumber);
		c.setBillingAddress(billingAddress);
		
		String message = AddCustomer.addCustomer(customerDBA, c);
		System.out.println(message);
		
		staffGoBack(console, userType);
	}

	/**
	 * Gets a list of customers served by a particular staff member and outputs them to the user
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void getCustomerServedBy(Scanner console, int userType) {
		System.out.println("Please enter the staff ID: ");
		int staffID = console.nextInt();
		
		List<Integer> roomNumbers = GetCustomerServedBy.getRoomNumbersOfCustomerServedBy(customerDBA, staffID);
		List<Integer> customerIDs = new ArrayList<Integer>();
		
		for(int i = 0 ; i < roomNumbers.size(); i++){
			customerIDs.add(GetCustomerServedBy.getCustomerIDFromRoomNumber(customerDBA, 
					roomNumbers.get(i)));
		}
		
		System.out.println("This staff with id " + staffID + " serves these customers: ");
		for(int i = 0 ; i < customerIDs.size(); i++){
			System.out.println(GetCustomerServedBy.getCustomerNameFromID(customerDBA, 
					customerIDs.get(i)));
		}
		staffGoBack(console, userType);
	}

	/**
	 * Gets the customer database access object
	 * @return the database access object
	 */
	public CustomerDBA getCustomerDBA() {
		return customerDBA;
	}

	/**
	 * Gets the staff database access object
	 * @return the database access object
	 */
	public StaffDBA getStaffDBA() {
		return staffDBA;
	}

	/**
	 * Gets the manager database access object
	 * @return the database access object
	 */
	public ManagerDBA getManagerDBA() {
		return managerDBA;
	}

	/**
	 * Gets the room database access object
	 * @return the database access object
	 */
	public RoomDBA getRoomDBA() {
		return roomDBA;
	}

	/**
	 * Gets the fdr database access object
	 * @return the database access object
	 */
	public FrontDeskRepresentativeDBA getFrontDeskRepresentativeDBA() {
		return frontDeskRepresentativeDBA;
	}

	/**
	 * Gets the reservation database access object
	 * @return the database access object
	 */
	public ReservationDBA getReservationDBA() {
		return reservationDBA;
	}

	/**
	 * Gets the miscellaneous database access object
	 * @return the database access object
	 */
	public OtherDBA getOtherDBA() {
		return otherDBA;
	}
}
