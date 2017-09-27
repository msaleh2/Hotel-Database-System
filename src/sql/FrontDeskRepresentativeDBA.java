package sql;

import models.Staff;
import database.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.JavaDatabaseConnection;

/**
 * Database access class for front desk representative functions
 * @author Alex
 *
 */
public class FrontDeskRepresentativeDBA {
	
	public Staff loadFrontDeskRepresentative(ResultSet rs) throws SQLException {
		Staff s = new Staff(rs.getInt("staffID"), rs.getInt("hotelID"));
		s.setStaffID(rs.getInt("staffID"));
		s.setName(rs.getString("name"));
		s.setAddress(rs.getString("address"));
		s.setAge(rs.getInt("age"));
		s.setDept(rs.getString("dept"));
		s.setPhone(rs.getLong("phoneNumber"));
		s.setGender(rs.getInt("gender"));
		s.setJobTitle(rs.getInt("jobTitle"));
		s.setSsn(rs.getInt("SSN"));
		s.setHotelID(rs.getInt("hotelID"));
		s.setSalary(rs.getInt("salary"));
		return s;
	}	
	
	/**
	 * Method to add a front desk representative to the database
	 * @param s the staff object for the fdr
	 * @throws SQLException
	 */
	public void addFrontDeskRepresentative(Staff s) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement(" INSERT INTO Staff(staffID, name, address, age, dept, "
					+ "phoneNumber, gender, jobTitle, SSN, hotelID, salary)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, s.getStaffID());
			ps.setString(2, s.getName());
			ps.setString(3,  s.getAddress());
			ps.setInt(4,  s.getAge());
			ps.setString(5, s.getDept());
			ps.setLong(6,  s.getPhone());
			ps.setInt(7,  s.getGender());
			ps.setInt(8,  s.getJobTitle());
			ps.setInt(9, s.getSsn());
			ps.setInt(10,  s.getHotelID());
			ps.setInt(11, s.getSalary());
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement(" INSERT INTO frontDeskRepresentative(staffID) VALUES(?)");
			ps2.setInt(1, s.getStaffID());			
			ps2.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeStatement(ps2);
			
		}
	}
	
	/**
	 * Method to edit a front desk representative entry in the database
	 * @param s The updated staff object to replace the existing one with
	 * @throws SQLException
	 */
	public void editFrontDeskRepresentative(Staff s) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement(" UPDATE Staff SET staffID=?, name=?, address=?, age=?, dept=?, "
					+ "phoneNumber=?, gender=?, jobTitle=?, SSN=?, hotelID=?, salary=?");
			ps.setInt(1, s.getStaffID());
			ps.setString(2, s.getName());
			ps.setString(3,  s.getAddress());
			ps.setInt(4,  s.getAge());
			ps.setString(5, s.getDept());
			ps.setLong(6,  s.getPhone());
			ps.setInt(7,  s.getGender());
			ps.setInt(8,  s.getJobTitle());
			ps.setInt(9, s.getSsn());
			ps.setInt(10,  s.getHotelID());
			ps.setInt(11, s.getSalary());			
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement(" UPDATE frontDeskRepresentative SET staffID=?");
			ps2.setInt(1, s.getStaffID());			
			ps2.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeStatement(ps2);
		}
	}
	
	/**
	 * Method to remove a front desk representative from the database
	 * @param id ID of the fdr to remove
	 * @throws SQLException
	 */
	public void removeFrontDeskRepresentative(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("DELETE FROM Staff WHERE StaffID=?");
			ps.setInt(1, id);			
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement("DELETE FROM frontDeskRepresentative WHERE StaffID=?");
			ps2.setInt(1, id);
			ps2.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeStatement(ps2);
		}
	}
	
	/**
	 * Method to get a Front desk representative from the database
	 * @param id ID of the fdr to get
	 * @return The staff object for the front desk representative
	 * @throws SQLException
	 */
	public Staff getFrontDeskRepresentative(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM Staff WHERE StaffID=?");
			ps.setInt(1, id);
			
			final ResultSet results = ps.executeQuery();
			return loadFrontDeskRepresentative(results);
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to get a list of all front desk representatives
	 * @return a list of all fdr objects
	 * @throws SQLException
	 */
	public List<Staff> getAllFrontDeskRepresentatives() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Staff> list = new ArrayList<Staff>();
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			//jobTitle id: manager=0; fdr=1; room staff=2; catering=3
			ps = conn.prepareStatement("SELECT * FROM Staff where jobTitle=1");
			final ResultSet results = ps.executeQuery();
			
			while(results.next()) {
				list.add(loadFrontDeskRepresentative(results));
			}
			return list;
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to validate the existence of a front desk representative object
	 * @param id ID of the fdr to validate
	 * @return whether or not the object exists
	 * @throws SQLException
	 */
	public boolean validateFrontDeskRepresentative(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM frontDeskRepresentative WHERE staffID=?");
			ps.setInt(1, id);
			
			final ResultSet results = ps.executeQuery();
			return results.next();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
