/**
 * 
 */
package models;

/**
 * POJO for the WolfVillas DBMS from; CSC440 Fall 2016 Course Project
 * Represents a Customer for WolfVlllas
 * Required field: customerID
 * Preferred fields: name, paymentMethod, and cardNumber
 * 
 * @author Anusha Balaji (abalaji)
 */
public class Customer {

	private int id; //ID of the customer
	private String name; //name of the customer
	private int gender; //gender of the customer
	private String address; //address of the customer
	private String paymentMethod; //payment method of the customer
	private String email; //email address of the customer
	private String billingAddress; //billing address of the customer
	private int ssn; //SSN of the customer
	private long cardNumber; //credit/debit card number of the customer
	private Long phone; //Phone number of the customer
	
	/**
	 * Null constructor for customer
	 */
	public Customer() {}
	
	/**
	 * Customer class constructor
	 * @param id the customers ID
	 */
	public Customer(int id) {
		this.id = id;
	}
	
	/**
	 * Getter method for the customer class
	 * @return Customer ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter method for the customer class
	 * @param id Customer ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter method for the customer class
	 * @param name customer name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter method for the customer class
	 * @param email customer email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer gender
	 */
	public int getGender() {
		return gender;
	}
	
	/**
	 * Setter method for the customer class
	 * @param gender customer gender
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer SSN
	 */
	public int getSSN() {
		return ssn;
	}
	
	/**
	 * Setter method for the customer class
	 * @param ssn Customer SSN
	 */
	public void setSSN(int ssn) {
		this.ssn = ssn;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer phone number
	 */
	public Long getPhone() {
		return phone;
	}
	
	/**
	 * Setter method for the customer class
	 * @param phone customer phone number
	 */
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer billing address
	 */
	public String getBillingAddress() {
		return billingAddress;
	}
	
	/**
	 * Setter method for the customer class
	 * @param billingAddress customer billing address
	 */
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Setter method for the customer class
	 * @param address customer address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer payment method
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
	/**
	 * Setter method for the customer class
	 * @param paymentMethod customer payment method
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	/**
	 * Getter method for the customer class
	 * @return customer card number
	 */
	public long getCardNumber() {
		return cardNumber;
	}
	
	/**
	 * Setter method for the customer class
	 * @param cardNumber customer card number
	 */
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}
}
