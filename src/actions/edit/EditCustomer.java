package actions.edit;

import models.Customer;
import sql.CustomerDBA;

import java.sql.SQLException;

/**
 * Action class to handle I/O for editing a customer
 * @author Alex
 *
 */
public class EditCustomer {
	
	/**
	 * Method which returns a string of success or failure
	 * @param dba database object
	 * @param c customer to edit
	 * @return success or failure of the operation 
	 */
	public static String editCustomer(CustomerDBA dba, Customer c) {
		try {
			dba.editCustomer(c);
			return "Success: Customer - " + c.getName() + " modified.";
		} catch(SQLException e) {
			return "Failure: Customer not modified.";
		}
	}
	
}