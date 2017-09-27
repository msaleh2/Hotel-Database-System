/**
 * 
 */
package models;

/**
 * POJO for the WolfVillas DBMS from; CSC440 Fall 2016 Course Project
 * Represents an employee at WolfVillas including manager, front desk rep, and 
 * laundry/catering/room service staff, etc
 * 
 * @author Anusha Balaji (abalaji)
 *
 */
public class Staff {
	
	private int staffID; //Staff member identifier
	private int ssn; //Staff member social security number
	private int hotelID; //Hotel ID of where the staff member is employed
	private int salary; //Staff member salary
	private int age; //Staff member age
	private int jobTitle; //Staff member job title
	private long phone; //Staff member phone number
	
	private String name; //Staff member name, first and last
	private int gender; //Staff member integer representation of gender
	private String address; //Staff member address
	private String dept; //Staff member department
	
	public Staff() {}
	
	/**
	 * Constructor class for a staff member object
	 * @param staffID the ID of this staff member
	 * @param hotelID the ID of the hotel where this staff member is employed
	 */
	public Staff(int staffID, int hotelID) {
		this.staffID = staffID;
		this.hotelID = hotelID;
	}

	/**
	 * Gets the ID of this staff member
	 * @return staff members ID
	 */
	public int getStaffID() {
		return staffID;
	}

	/**
	 * Sets the ID of this staff member
	 * @param dept staff members ID
	 */
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	
	/**
	 * Gets the SSN of this staff member
	 * @return staff members SSN
	 */
	public int getSsn() {
		return ssn;
	}

	/**
	 * Sets the SSN of this staff member
	 * @param dept staff members SSN
	 */
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	
	/**
	 * Gets the Hotel ID of this staff member
	 * @return staff members Hotel ID
	 */
	public int getHotelID() {
		return hotelID;
	}

	/**
	 * Sets the Hotel ID of this staff member
	 * @param dept staff members Hotel ID
	 */
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	/**
	 * Gets the salary of this staff member
	 * @return staff members salary
	 */
	public int getSalary() {
		return salary;
	}

	/**
	 * Sets the salary of this staff member
	 * @param dept staff members salary
	 */
	public void setSalary(int salary) {
		this.salary = salary;
	}

	/**
	 * Gets the age of this staff member
	 * @return staff members age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the age of this staff member
	 * @param dept staff members age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Gets the job title of this staff member
	 * @return staff members job title
	 */
	public int getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title of this staff member
	 * @param dept staff members job title
	 */
	public void setJobTitle(int jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the phone number of this staff member
	 * @return staff members phone number
	 */
	public long getPhone() {
		return phone;
	}

	/**
	 * Sets the phone number of this staff member
	 * @param dept staff members phone number
	 */
	public void setPhone(long phone) {
		this.phone = phone;
	}

	/**
	 * Gets the name of this staff member
	 * @return staff members name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this staff member
	 * @param dept staff members name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the gender of this staff member
	 * @return staff members gender
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * Sets the gender of this staff member
	 * @param dept staff members gender
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}

	/**
	 * Gets the address of this staff member
	 * @return staff members address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address of this staff member
	 * @param dept staff members address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the department of this staff member
	 * @return staff members department
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * Sets the department of this staff member
	 * @param dept staff members department
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
}
