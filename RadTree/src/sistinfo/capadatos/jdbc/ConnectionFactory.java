package sistinfo.capadatos.jdbc;

import java.sql.*;
import java.util.Properties;

public class ConnectionFactory {
	
	/* Nombre del driver JDBC driver y URL de la base de datos */
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	static Connection conn = null;

   	/* Conectar a la base de datos */
	public static Connection getConnection() throws SQLException {
		Properties properties = new Properties();
		Connection conn = null;
		try {
			properties.load(ConnectionFactory.class.getResourceAsStream("login.properties"));
		    Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(
					properties.getProperty("db_url"),
					properties.getProperty("user"),
					properties.getProperty("password")
				);
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException();
		}
		return conn;
	}
   
}