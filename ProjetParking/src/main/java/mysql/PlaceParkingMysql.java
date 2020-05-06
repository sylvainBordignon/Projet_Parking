package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnexionBDD.Connexion;

public class PlaceParkingMysql {
	
	Connection conn;

	private static PlaceParkingMysql placemysql;

	public PlaceParkingMysql(Connection conn) {
		super();
		this.conn = conn;
	}

	public static PlaceParkingMysql getInstance() {
		if (placemysql == null)
			placemysql = new PlaceParkingMysql(Connexion.getInstance());
		return placemysql;
	}
	
	
	public int verifierNombrePlaceDispo() {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT count(*) FROM placeParking where etat = 'disponible'");
			ResultSet res = preparedStmt.executeQuery();
			res.next();
			return res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}