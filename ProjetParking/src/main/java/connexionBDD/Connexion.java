package connexionBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Connexion {

	private String url = "jdbc:mysql://localhost:3306/Parking";
	private String user = "root";
	private String mdp = "";

	private static Connection conn;
	
	private static final Logger logger = Logger.getLogger(Connexion.class.getName());

	public Connexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			logger.severe(ex.getMessage());
		}
		try {
			conn = DriverManager.getConnection(url, user, mdp);

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}

	// M�thode qui va nous retourner notre instance et la cr�er si elle n'existe pas
	public static Connection getInstance() {
		if (conn == null) {		
			new Connexion();			
		}
		return conn;
	}
}
