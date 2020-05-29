package connexionBDD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Connexion {
	private static Connection conn;
	private static final Logger logger = Logger.getLogger(Connexion.class.getName());

	public Connexion()  {
		LecteurFichier  lec = new LecteurFichier();
		String user=null,mdp=null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			logger.severe(ex.getMessage());
		}
		try {
			
			try {
				conn = DriverManager.getConnection(lec.recupererInfoConnexionBdd("././cheminBdd"),lec.recupererInfoConnexionBdd("././identifiantBdd"),lec.recupererInfoConnexionBdd("././mdpBdd"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
			logger.severe(e.getMessage());
			}
			
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		
	}

	// M�thode qui va nous retourner notre instance et la cr�er si elle n'existe pas
	public static Connection getInstance()  {
		if (conn == null) {		
			new Connexion();			
		}
		return conn;
	}
}
