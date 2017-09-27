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

public class StaffDBA {
	
	public Staff loadStaff(ResultSet rs) throws SQLException {
		Staff s = null;
		while(rs.next()) {
			s = new Staff();
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
		}
		return s;
	}
	
	public void addStaff(Staff s) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JavaDatabaseConnection.getConnection();
			ps = conn.prepareStatement(" INSERT INTO Staff(staffID, name, address, age, dept, "
					+ "phoneNumber, gender, jobTitle, SSN, hotelID, salary)" + " VALUES(staff_seq.nextval,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, s.getName());
			ps.setString(2, s.getAddress());
			ps.setInt(3, s.getAge());
			ps.setString(4, s.getDept());
			ps.setLong(5, s.getPhone());
			ps.setInt(6, s.getGender());
			ps.setInt(7, s.getJobTitle());
			ps.setInt(8, s.getSsn());
			ps.setInt(9, s.getHotelID());
			ps.setInt(10, s.getSalary());
			ps.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public void editStaff(Staff s) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			conn.commit();
			ps = conn.prepareStatement("UPDATE staff SET staffID=?, name=?, address=?, age=?, dept=?, "
					+ "phoneNumber=?, gender=?, jobTitle=?, SSN=?, hotelID=?, salary=? where staffID=?");
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
			ps.setInt(12, s.getStaffID());	
			ps.executeUpdate();
			
			int ssnLength = String.valueOf(s.getSsn()).length();
			if ( ssnLength != 9 ) {
				conn.rollback();
				throw new SQLException("Invalid SSN");
			}			
			int phoneLength = String.valueOf(s.getPhone()).length();
			if (phoneLength != 10) {
				conn.rollback();
				throw new SQLException("Invalid Phone Number");
			}
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public void removeStaff(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("DELETE FROM Staff WHERE StaffID=?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public Staff getStaff(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM Staff WHERE StaffID=?");
			ps.setInt(1, id);
			
			final ResultSet results = ps.executeQuery();
			return loadStaff(results);
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<Staff> getAllStaff() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Staff> list = new ArrayList<Staff>();
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			//jobTitle id: manager=0; fdr=1; room staff=2; catering=3
			ps = conn.prepareStatement("SELECT * FROM Staff where jobTitle=2 or jobTitle=3");
			final ResultSet results = ps.executeQuery();
			
			while(results.next()) {
				list.add(loadStaff(results));
			}
			return list;
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public List<Staff> getStaffByRole() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Staff> list = new ArrayList<Staff>();
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT staffID, name, jobTitle FROM staff GROUP BY (staffID, name, jobTitle) "
					+ "ORDER BY jobTitle ASC");
			final ResultSet results = ps.executeQuery();
			
			while(results.next()) {
				Staff s = new Staff();
				s.setStaffID(results.getInt("staffID"));
				s.setName(results.getString("name"));
				s.setJobTitle(results.getInt("jobTitle"));
				list.add(s);
			}
			return list;
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public boolean validateStaff(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = JavaDatabaseConnection.getConnection();
			
			ps = conn.prepareStatement("SELECT * FROM staff WHERE staffID=?");
			ps.setInt(1, id);
			
			final ResultSet results = ps.executeQuery();
			return results.next();
			
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}