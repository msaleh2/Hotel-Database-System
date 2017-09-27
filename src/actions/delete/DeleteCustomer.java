package actions.delete;

import sql.CustomerDBA;

import java.sql.SQLException;

/**
 * Class to handle errors and output for removing a customer
 * @author Alex
 *
 */
public class DeleteCustomer {
	
	/**
	 * Method to delete a front desk represenatative
	 * @param dba database action class
	 * @param id customer id
	 * @return Success or failure of the operation
	 */
	public static String deleteCustomer(CustomerDBA dba, int customerID) {
		try {
			dba.removeCustomer(customerID);
			return "Success: Customer deleted.";
		} catch(SQLException e) {
			return "Failed to remove customer.";
		}
	}
	
}