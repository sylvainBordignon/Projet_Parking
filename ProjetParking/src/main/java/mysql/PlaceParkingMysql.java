package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connexionBDD.Connexion;

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
	
	public int trouverPlaceDisponible() {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM placeParking where etat = 'disponible'");
			ResultSet res = preparedStmt.executeQuery();
			res.next();
			return res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
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
	
	public boolean verifierPlaceNonPrise(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM placeParking where etat <> 'occupée' and id = ?");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			return res.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String visualiserEtatPlace(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM placeParking where id = ?");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			res.next();
			return res.getString("etat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public int changerEtatPlaceLibre(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("UPDATE placeParking set etat = 'disponible' where id = ?");
			preparedStmt.setInt(1, id);
			int res = preparedStmt.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int changerEtatPlaceOccupee(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("UPDATE placeParking set etat = 'occupée' where id = ?");
			preparedStmt.setInt(1, id);
			int res = preparedStmt.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int changerEtatPlaceReservee(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("UPDATE placeParking set etat = 'réservée' where id = ?");
			preparedStmt.setInt(1, id);
			int res = preparedStmt.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}