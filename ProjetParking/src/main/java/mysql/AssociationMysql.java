package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import connexionBDD.Connexion;
import pojo.Reservation;

public class AssociationMysql {
	Connection conn;

	private static final Logger logger = Logger.getLogger(AssociationMysql.class.getName());

	private static AssociationMysql associationmysql;

	public AssociationMysql(Connection conn) {
		super();
		this.conn = conn;
	}

	public static AssociationMysql getInstance() {
		if (associationmysql == null)
			associationmysql = new AssociationMysql(Connexion.getInstance());
		return associationmysql;
	}

	public boolean verificationPlaqueExiste(String plaque) {
		try (PreparedStatement preparedStmt = conn
				.prepareStatement("Select * from Association where num_plaque_immatricule = ?");) {
			preparedStmt.setString(1, plaque);
			try (ResultSet res = preparedStmt.executeQuery()) {
				return res.next();
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return false;
	}

	public List<Integer> clientsUtilisantCeVehicule(String plaque) {
		ArrayList<Integer> liste = new ArrayList<>();
		try (PreparedStatement preparedStmt = conn
				.prepareStatement("Select * from Association where num_plaque_immatricule = ?")) {
			preparedStmt.setString(1, plaque);
			try (ResultSet res = preparedStmt.executeQuery()) {
				while (res.next()) {
					liste.add(res.getInt("id_client"));
				}
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return liste;
	}

	public Reservation verifierReservationCorrespondantePlaqueMemeJour(String plaque) {
		try (PreparedStatement preparedStmt = conn.prepareStatement(
				"SELECT * FROM reservation r, association a where r.id_client = a.id_client and a.num_plaque_immatricule = ? and r.date_debut < now() and r.date_fin > now();")) {
			preparedStmt.setString(1, plaque);
			try (ResultSet res = preparedStmt.executeQuery()) {
				if (res.next()) {
					return new Reservation(res.getInt("id"), res.getInt("id_client"), res.getTimestamp("date_debut"),
							res.getTimestamp("date_fin"), res.getInt("duree"), res.getTimestamp("date_arrive_reel"),
							res.getTimestamp("date_depart_reel"), res.getInt("id_place"), res.getInt("delai_attente"));
				}
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		return null;
	}
}
