package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConnexionBDD.Connexion;
import pojo.ReservationPermanente;

public class ReservationPermanenteMysql {

	Connection conn;

	private static ReservationPermanenteMysql reservPermamysql;

	public ReservationPermanenteMysql(Connection conn) {
		super();
		this.conn = conn;
	}

	public static ReservationPermanenteMysql getInstance() {
		if (reservPermamysql == null)
			reservPermamysql = new ReservationPermanenteMysql(Connexion.getInstance());
		return reservPermamysql;
	}

	public List<ReservationPermanente> selectionnerReservationsPermanentesClient(int id) {
		List<ReservationPermanente> liste = new ArrayList<>();
		try {

			PreparedStatement preparedStmt = conn
					.prepareStatement("SELECT * FROM reservationpermanente where id_client = ?");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			while (res.next()) {
				liste.add(new ReservationPermanente(res.getInt("id"), res.getInt("id_client"), res.getString("type"),
						res.getTime("heure_debut"), res.getInt("duree"), res.getInt("jour_semaine"),
						res.getInt("jour_mois")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}

	public ReservationPermanente selectionnerReservationPermanente(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM reservationpermanente where id = ?");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			if (res.next()) {
				return new ReservationPermanente(res.getInt("id"), res.getInt("id_client"), res.getString("type"),
						res.getTime("heure_debut"), res.getInt("duree"), res.getInt("jour_semaine"),
						res.getInt("jour_mois"));
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean ajoutReservationPermanente(ReservationPermanente reserv) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"INSERT into reservationpermanente (id, id_client, type, heure_debut, duree, jour_semaine, jour_mois) values (?,?,?,?,?,?,?)");
			preparedStmt.setInt(1, reserv.getId());
			preparedStmt.setInt(2, reserv.getIdClient());
			preparedStmt.setString(3, reserv.getType());
			preparedStmt.setTime(4, reserv.getHeureDebut());
			preparedStmt.setInt(5, reserv.getDuree());
			if (reserv.getJourSemaine() == null) {
				preparedStmt.setNull(6, 0);
			} else {
				preparedStmt.setInt(6, reserv.getJourSemaine());
			}
			if (reserv.getJourMois() == null) {
				preparedStmt.setNull(7, 0);
			} else {
				preparedStmt.setInt(7, reserv.getJourMois());
			}
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int suppressionReservationPermanente(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("DELETE from reservationpermanente where id = ?");
			preparedStmt.setInt(1, id);
			int res = preparedStmt.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
