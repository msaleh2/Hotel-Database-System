package actions.get;

import models.Customer;
import sql.CustomerDBA;

import java.sql.SQLException;

/**
 * Class to handle I/O for retrieving a customer
 * @author Alex
 *
 */
public class GetCustomer {
	
	/**
	 * Method which returns a customer based on the queried information
	 * @param dba database access object
	 * @param id primary key for a customer
	 * @return the queried customer or null if an error occurs
	 */
	public static Customer getCustomer(CustomerDBA dba, int customerID) {
		try {
			return dba.getCustomer(customerID);
		} catch(SQLException e) {
			System.out.println("SQL Exception in GetCustomer.java");
			return null;
		}
	}
	
}
