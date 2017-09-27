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
 * Database access object for the manager functions
 * @author Alex
 *
 */
public class ManagerDBA {
	
	/**
	 * Load a manager object from a queried result set
	 * @param rs queried result set
	 * @return the Staff object for the queried manager
	 * @throws SQLException
	 */
	public Staff loadManager(ResultSet rs) throws SQLException {
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
	 * Method to add a manager to the database
	 * @param s the staff member java object to add to the database
	 * @throws SQLException
	 */
	public void addManager(Staff s) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement(" INSERT INTO Staff(staffID, name, address, age, dept, "
					+ "phoneNumber, gender, jobTitle, SSN, hotelID, salary)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, s.getStaffID());
			ps.setString(2, s.getName());
			ps.setString(3, s.getAddress());
			ps.setInt(4, s.getAge());
			ps.setString(5, s.getDept());
			ps.setLong(6, s.getPhone());
			ps.setInt(7, s.getGender());
			ps.setInt(8, s.getJobTitle());
			ps.setInt(9, s.getSsn());
			ps.setInt(10, s.getHotelID());
			ps.setInt(11, s.getSalary());			
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement(" INSERT INTO manager(staffID) VALUES(?)");
			ps2.setInt(1, s.getStaffID());			
			ps2.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeStatement(ps2);
			
		}
	}
	
	/**
	 * Method to edit a manager in the database
	 * @param s an edited version of the manager to update in the database
	 * @throws SQLException
	 */
	public void editManager(Staff s) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement(" UPDATE Staff SET staffID=?, name=?, address=?, age=?, dept=?, "
					+ "phoneNumber=?, gender=?, jobTitle=?, SSN=?, hotelID=?, salary=?");
			ps.setInt(1, s.getStaffID());
			ps.setString(2, s.getName());
			ps.setString(3, s.getAddress());
			ps.setInt(4, s.getAge());
			ps.setString(5, s.getDept());
			ps.setLong(6, s.getPhone());
			ps.setInt(7, s.getGender());
			ps.setInt(8, s.getJobTitle());
			ps.setInt(9, s.getSsn());
			ps.setInt(10, s.getHotelID());
			ps.setInt(11, s.getSalary());			
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement(" UPDATE manager SET staffID=?");
			ps2.setInt(1, s.getStaffID());			
			ps2.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeStatement(ps2);
		}
	}
	
	/**
	 * Method to remove a manager from the database
	 * @param id Manager's ID to remove from the database
	 * @throws SQLException
	 */
	public void removeManager(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("DELETE FROM Staff WHERE StaffID=?");
			ps.setInt(1, id);			
			ps.executeUpdate();
			
			ps2 = conn.prepareStatement("DELETE FROM manager WHERE StaffID=?");
			ps2.setInt(1, id);
			ps2.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
			DBUtil.closeStatement(ps2);
		}
	}
	
	/**
	 * Method to retrieve a manager from the database
	 * @param id Manager's id to retrieve
	 * @return staff object for the retrieved manager
	 * @throws SQLException
	 */
	public Staff getManager(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM Staff WHERE StaffID=?");
			ps.setInt(1, id);
			
			final ResultSet results = ps.executeQuery();
			return loadManager(results);
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to get a list of all manager objects from the database
	 * @return list of staff objects for all managers in the database
	 * @throws SQLException
	 */
	public List<Staff> getAllManagers() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Staff> list = new ArrayList<Staff>();
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			//jobTitle id: manager=0; fdr=1; room staff=2; catering=3
			ps = conn.prepareStatement("SELECT * FROM Staff where jobTitle=0");
			final ResultSet results = ps.executeQuery();
			
			while(results.next()) {
				list.add(loadManager(results));
			}
			return list;
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Method to validate that a manager object exists in the database
	 * @param id ID of the manager to check for
	 * @return whether or not the manager is present in the database
	 * @throws SQLException
	 */
	public boolean validateManager(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM manager WHERE staffID=?");
			ps.setInt(1, id);
			
			final ResultSet results = ps.executeQuery();
			return results.next();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
