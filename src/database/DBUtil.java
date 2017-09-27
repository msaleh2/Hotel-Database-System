package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
	/**
	 * Close the prepared statement and the connection in a proper way
	 * 
	 * @param conn
	 * @param ps
	 */
	public static void closeConnection(Connection conn, PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.err.println("Error closing connections");
			
		}
	}
	
	/**
	 * Close the prepared statement
	 * 
	 * @param ps
	 */
	public static void closeStatement(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			System.err.println("Error closing connections");
			
		}
	}
}
