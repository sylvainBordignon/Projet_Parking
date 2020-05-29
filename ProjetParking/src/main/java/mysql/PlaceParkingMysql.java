package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import connexionBDD.Connexion;

public class PlaceParkingMysql {

	Connection conn;

	private static PlaceParkingMysql placemysql;
	
	private static final Logger logger = Logger.getLogger(PlaceParkingMysql.class.getName());

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
		try (PreparedStatement preparedStmt = conn
				.prepareStatement("SELECT * FROM placeParking where etat = 'disponible'")) {
			try (ResultSet res = preparedStmt.executeQuery()) {
				res.next();
				return res.getInt(1);
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return -1;
	}

	public int verifierNombrePlaceDispo() {
		try (PreparedStatement preparedStmt = conn
				.prepareStatement("SELECT count(*) FROM placeParking where etat = 'disponible'")) {
			try (ResultSet res = preparedStmt.executeQuery()) {
				res.next();
				return res.getInt(1);
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return -1;
	}

	public boolean verifierPlaceNonPrise(int id) {
		try (PreparedStatement preparedStmt = conn
				.prepareStatement("SELECT * FROM placeParking where etat <> 'occupée' and id = ?")) {
			preparedStmt.setInt(1, id);
			try (ResultSet res = preparedStmt.executeQuery()) {
				return res.next();
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return false;
	}

	public String visualiserEtatPlace(int id) {
		try (PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM placeParking where id = ?")) {
			preparedStmt.setInt(1, id);
			try (ResultSet res = preparedStmt.executeQuery()) {
				res.next();
				return res.getString("etat");
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return "";
	}

	public int changerEtatPlaceLibre(int id) {
		try (PreparedStatement preparedStmt = conn
				.prepareStatement("UPDATE placeParking set etat = 'disponible' where id = ?")) {
			preparedStmt.setInt(1, id);
			return preparedStmt.executeUpdate();
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return 0;
	}

	public int changerEtatPlaceOccupee(int id) {
		try (PreparedStatement preparedStmt = conn
				.prepareStatement("UPDATE placeParking set etat = 'occupée' where id = ?")) {
			preparedStmt.setInt(1, id);
			return preparedStmt.executeUpdate();
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return 0;
	}

	public int changerEtatPlaceReservee(int id) {
		try (PreparedStatement preparedStmt = conn
				.prepareStatement("UPDATE placeParking set etat = 'réservée' where id = ?")) {
			preparedStmt.setInt(1, id);
			return preparedStmt.executeUpdate();
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return 0;
	}

	public void supprimerPlaceParking(int id) {
		try (PreparedStatement preparedStmt2 = conn.prepareStatement("DELETE FROM reservation where id=? ")) {
			preparedStmt2.setInt(1, id);
			preparedStmt2.executeUpdate();
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}

}