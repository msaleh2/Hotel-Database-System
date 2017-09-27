package actions.add;

import models.Customer;
import sql.CustomerDBA;
import java.sql.SQLException;

/**
 * Class to handle exceptions and output for adding a customer
 * @author Alex
 *
 */
public class AddCustomer {
	
	/**
	 * Adds a customer to the database
	 * @param dba database action class
	 * @param c the customer to be added
	 * @return information about the success or failure of the operation
	 */
	public static String addCustomer(CustomerDBA dba, Customer c) {
		try {
			dba.addCustomer(c);
			return "Success: Customer - " + c.getName() + " created. ";
		} catch (SQLException e) {
			return "Failed to add customer";
		}
	}
		
}
