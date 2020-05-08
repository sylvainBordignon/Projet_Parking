package connexionBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

	private String url = "jdbc:mysql://localhost:3306/Parking";
	private String user = "root";
	private String mdp = "";

	public static Connection conn;

	public Connexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, mdp);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// M�thode qui va nous retourner notre instance et la cr�er si elle n'existe pas
	public static Connection getInstance() {
		if (conn == null) {
			System.out.println("ouverture connexion ");
			new Connexion();
			System.out.println("Connexion Ok ");
		}
		return conn;
	}
}
