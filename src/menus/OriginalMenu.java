package menus;

import java.util.Scanner;

/**
 * The Main menu for the entire management system, used on startup to determine user type and
 * type specific interface.
 * @author Alex
 *
 */
public class OriginalMenu {
	
	private CustomerMenu customerMenu = new CustomerMenu(); //Customer interface object
	private FDRMenu FDRMenu = new FDRMenu(); //Front Desk Representative interface object
	private ManagerMenu managerMenu = new ManagerMenu(); //Manager interface object
	private StaffMenu staffMenu = new StaffMenu(); //Staff interface object

	/**
	 * Main method for the whole management system
	 * @param args command line arguments, not used for this program
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		
		System.out.println("Welcome to WolfVillas! Which of the following type of user are you?");
		System.out.println("(For navigating through this system, please type the number accociated with your selection.)");
		
		OriginalMenu obj = new OriginalMenu();
		obj.mainMenu(console);
	}
	
	/**
	 * Main menu options/interface
	 * @param console input scanner
	 */
	public void mainMenu(Scanner console){
		System.out.println("(1) Customer");
		System.out.println("(2) Staff");
		System.out.println("(3) Manager");
		System.out.println("(4) Front Desk Representatives");
		System.out.println("(5) Wolf Villas Management");
		
		int userType = console.nextInt();
		
		menuSelection(console, userType);
	}
	
	/**
	 * Menu selection interface which sends users to their specialized menu.
	 * @param console input scanner
	 * @param userType integer representation of the type of staff user used for menu navigation and access control
	 */
	public void menuSelection(Scanner console, int userType){
		if(userType==1){
			System.out.println("Welcome Customer");
			customerMenu.mainCustomerMenu(console);
		}else if(userType==2){
			System.out.println("Welcome Staff");
			staffMenu.mainStaffMenu(console, userType);
		}else if(userType==3){
			System.out.println("Welcome Manager");
			managerMenu.mainManagerMenu(console, userType);
		}else if(userType==4){
			System.out.println("Welcome FDR");
			FDRMenu.mainFDRMenu(console, userType);
		}else if(userType==5){
			System.out.println("Welcome Wolf Villas Mgmt");
		}else{
			inputError();
		}
	}
	
	/**
	 * Outputs an error message if input is invalid and reroutes the user to the main menu
	 */
	public static void inputError() {
		System.out.println("Your input is invalid. Exiting to main menu");
		main(null);
	}

}
