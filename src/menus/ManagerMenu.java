package menus;

import java.util.List;
import java.util.Scanner;

import actions.delete.DeleteStaff;
import actions.edit.EditStaff;
import actions.get.GetAllStaff;
import actions.get.GetOccupancyPercentage;
import actions.get.GetStaff;
import actions.validate.ValidateHotel;
import actions.validate.ValidateManager;
import models.Staff;
import sql.OtherDBA;

/**
 * Subclass of the Staff menu superclass used for manager specific menu functions.
 * @author Joanna Pollard
 *
 */
public class ManagerMenu extends StaffMenu {
	
	private OtherDBA otherDBA; //Database Access object for a few miscellaneous access functions

	/**
	 * Generic staff constructor plus an additional initialization of a database access object
	 */
	public ManagerMenu() {
		super();
		otherDBA = new OtherDBA();
	}
	
	/**
	 * The starting menu for a manager
	 * @param console input scanner
	 * @param userType userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void mainManagerMenu(Scanner console, int userType){
		System.out.println("What is the hotel id of the hotel you work at?");
		int hotelID = console.nextInt();
		if(!validHotel(hotelID)) {
			OriginalMenu.inputError();
		}
		
		System.out.print("What is your Staff ID:");
		int staffID = console.nextInt();
		if(!validManagerID(staffID)) {
			OriginalMenu.inputError();
		}
		
		System.out.println("Please select the action you would like to perform");
		System.out.println("(1) View Personal Information");
		System.out.println("(2) Add Charge to Customer Bill");
		System.out.println("(3) Add New Customers");
		System.out.println("(4) Add New Room");
		System.out.println("(5) Add New Staff");
		System.out.println("(6) View/Edit Customers AND View/Add/Edit/Delete Reservations");
		System.out.println("(7) View/Edit/Delete Rooms");
		System.out.println("(8) View/Edit Staff");
		System.out.println("(9) View Hotel Occupancy");
		System.out.println("(10) Get Percentage Occupied");
		System.out.println("(11) View Staff By Role");
		System.out.println("(12) Get Customers Served by Staff Member");
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
        case 5:  addNewStaff(console, hotelID, userType);
        		 break;
        case 6:  viewEditCustomer(console, userType, hotelID);
                 break;
        case 7:  viewEditRooms(console, hotelID, userType);
                 break;
        case 8:  viewEditStaff(console, hotelID, userType);
                 break;
        case 9:  viewHotelOccupancy(console, userType);
                 break;
        case 10: getPercentageOccupied(console, userType);
                 break;
        case 11: getStaffByRole(console, userType);
                 break;
        case 12: getCustomerServedBy(console, userType);
                 break;
        default: OriginalMenu.inputError();
                 break;
		}
	}
	
	/**
	 * Validates that the hotel exists
	 * @param hotelID the id of the hotel to be checked
	 * @return if the hotel exists
	 */
	private boolean validHotel(int hotelID) {
		return ValidateHotel.validateHotel(getOtherDBA(), hotelID);
	}
	
	/**
	 * Validates that the manager exists
	 * @param hotelID the id of the manager to be checked
	 * @return if the manager exists
	 */
	private boolean validManagerID(int managerID) {
		return ValidateManager.validateManager(getManagerDBA(), managerID);
	}

	/**
	 * Gets staff members by their role and lists them for the manager to see
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	private void getStaffByRole(Scanner console, int userType) {
		List<Staff> staffByRole = GetAllStaff.getStaffByRole(getStaffDBA());
		
		for(int i = 0 ; i < staffByRole.size(); i++){
			System.out.printf("%-30s%-30s%-2s\n", jobTitleIntToString(staffByRole.get(i).getJobTitle()), 
					staffByRole.get(i).getName(), "ID: " + staffByRole.get(i).getStaffID());
		}
		staffGoBack(console, userType);
		
	}

	/**
	 * Outputs the percentage of a hotel occupied
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	private void getPercentageOccupied(Scanner console, int userType) {
		double percentage = GetOccupancyPercentage.getOccupancyPercentage(otherDBA);
		System.out.println("Percentage Occupied: " + percentage);
		
		staffGoBack(console, userType);
		
	}

	/**
	 * Interface for the manager to view and edit staff members.
	 * @param console input scanner
	 * @param hotelID hotel identifier
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void viewEditStaff(Scanner console, int hotelID, int userType){
		System.out.println("Please enter the Staff ID: ");
		int staffID = console.nextInt();
		Staff staff = GetStaff.getStaff(getStaffDBA(), staffID);
		
		System.out.println("Name: " + staff.getName());
		System.out.println("Job Title: " + jobTitleIntToString(staff.getJobTitle()));
		System.out.println("Hotel Serving: " + staff.getHotelID());
		System.out.println("Age: "+ staff.getAge());
		System.out.println("Gender: " + (staff.getGender()==0 ? "Male" : "Female"));
		System.out.println("Phone: " + staff.getPhone());
		System.out.println("Address: " + staff.getAddress());
		System.out.println("Department: " + staff.getDept());
		System.out.println("SSN: " + staff.getSsn());
		System.out.println("Salary: " + staff.getSalary());
		System.out.println("Please select from the following actions: ");
		System.out.println("(1) Edit");
		System.out.println("(2) Delete");
		
		int customerAction = console.nextInt();
		
		if(customerAction == 1){
			Staff editedStaff = new Staff(staff.getStaffID(), staff.getHotelID());
			System.out.println("Please enter data for the attributes you would like to edit. "
					+ "\nIf you don't want a value to change, Enter \"-\" to keep the old information.");
			System.out.println("Name: ");
			console.nextLine(); //consume the last newline character 
			String name = console.nextLine();
			name = (!name.equals("-")) ? name: staff.getName();
			editedStaff.setName(name);
			
			System.out.println("Job Title: ");
			System.out.println("Choose one of the following Departments");
			System.out.println("(1) Manager");
			System.out.println("(2) Front Desk Representative");
			System.out.println("(3) Catering Staff");
			System.out.println("(4) Room Staff");
			String jobTitleString = console.nextLine();
			int jobTitle = (!jobTitleString.equals("-")) ? Integer.parseInt(jobTitleString): staff.getJobTitle();
			editedStaff.setJobTitle(jobTitle - 1);
			
			System.out.println("Hotel ID Serving: ");
			String hotelIDString = console.nextLine();
			int newHotelID = (!hotelIDString.equals("-")) ? Integer.parseInt(hotelIDString): staff.getHotelID();
			editedStaff.setHotelID(newHotelID);
			
			System.out.println("Age: ");
			String ageString = console.nextLine();
			int age = (!ageString.equals("-")) ? Integer.parseInt(ageString): staff.getAge();
			editedStaff.setAge(age);
			
			System.out.println("Gender [1 for male and 2 for female]: ");
			String genderString = console.nextLine();
			int gender = (!genderString.equals("-")) ? Integer.parseInt(genderString)-1: staff.getGender();
			editedStaff.setGender(gender);

			System.out.println("Phone: ");
			String phoneString = console.nextLine();
			long phone = (!phoneString.equals("-")) ? Long.parseLong(phoneString): staff.getPhone();
			editedStaff.setPhone(phone);
			
			System.out.println("Address: ");
			String addressString = console.nextLine();
			String address = (!addressString.equals("-")) ? addressString: staff.getAddress();
			editedStaff.setAddress(address);
			
			System.out.println("Department: ");
			String deptString = console.nextLine();
			String dept = (!deptString.equals("-")) ? deptString : staff.getDept();
			editedStaff.setDept(dept);
			
			System.out.println("SSN: ");
			String ssnString = console.nextLine();
			int ssn = (!ssnString.equals("-")) ? Integer.parseInt(ssnString) : staff.getSsn();
			editedStaff.setSsn(ssn);
			
			System.out.println("Salary: ");
			String salaryString = console.nextLine();
			int salary = (!salaryString.equals("-")) ? Integer.parseInt(salaryString) : staff.getSalary();
			editedStaff.setSalary(salary);
			
			String message = EditStaff.editStaff(getStaffDBA(), editedStaff);
			System.out.println(message);
		}else if(customerAction==2){			
			System.out.println("Are you sure you want to delete this Staff Member? (Type 1 for Yes and 2 for No)");
			int delete = console.nextInt();
			
			if(delete == 1) {				
				String message = DeleteStaff.deleteStaff(getStaffDBA(), staffID);
				System.out.println(message);
				mainManagerMenu(console, 3);				
			} else if(delete ==2) {				
				viewEditRooms(console, hotelID, userType);
				
			} else {
				OriginalMenu.inputError();
			}
			
		} else {
			OriginalMenu.inputError();
		}
		
		staffGoBack(console, userType);
	}

	private String jobTitleIntToString(int jobTitle) {
		if(jobTitle == 0) { return "Manager"; }
		if(jobTitle == 1) { return "Front Desk Representative"; }
		if(jobTitle == 2) { return "Catering Staff"; }
		if(jobTitle == 3) { return "Room Staff"; }		
		//Should never get to this.
		return null;
	}
}
