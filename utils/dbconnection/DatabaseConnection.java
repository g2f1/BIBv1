package utils.dbconnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static DatabaseConnection instance;
	private Connection connection;
	private static final String URL = "jdbc:mysql://localhost:3306/DB";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	private DatabaseConnection() {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static DatabaseConnection getInstance(){
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			if(connection.isClosed()||connection==null) {
				connection=DriverManager.getConnection(URL, USER, PASSWORD);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}

