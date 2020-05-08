package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import connexionBDD.Connexion;
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
						res.getTime("heure_debut"), res.getTime("duree"), res.getInt("jour_semaine"),
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
						res.getTime("heure_debut"), res.getTime("duree"), res.getInt("jour_semaine"),
						res.getInt("jour_mois"));
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean ajoutReservationPermanenteAuto(ReservationPermanente reserv) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"INSERT into reservationpermanente (id_client, type, heure_debut, duree, jour_semaine, jour_mois) values (?,?,?,?,?,?)");
			preparedStmt.setInt(1, reserv.getIdClient());
			preparedStmt.setString(2, reserv.getType());
			preparedStmt.setTime(3, reserv.getHeureDebut());
			preparedStmt.setTime(4, reserv.getDuree());
			if (reserv.getJourSemaine() == null) {
				preparedStmt.setNull(5, 0);
			} else {
				preparedStmt.setInt(5, reserv.getJourSemaine());
			}
			if (reserv.getJourMois() == null) {
				preparedStmt.setNull(6, 0);
			} else {
				preparedStmt.setInt(6, reserv.getJourMois());
			}
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean ajoutReservationPermanente(ReservationPermanente reserv) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"INSERT into reservationpermanente (id, id_client, type, heure_debut, duree, jour_semaine, jour_mois) values (?,?,?,?,?,?,?)");
			preparedStmt.setInt(1, reserv.getId());
			preparedStmt.setInt(2, reserv.getIdClient());
			preparedStmt.setString(3, reserv.getType());
			preparedStmt.setTime(4, reserv.getHeureDebut());
			preparedStmt.setTime(5, reserv.getDuree());
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

	public ReservationPermanente reservationContigue(ReservationPermanente r1) {
		List<ReservationPermanente> liste = this.selectionnerReservationsPermanentesClient(r1.getIdClient());
		ReservationPermanente r2;
		for (int i = 0; i < liste.size(); i++) {
			r2 = liste.get(i);
			if (r2.getType().equals(r1.getType()) && r2.getJourSemaine() == r1.getJourSemaine()
					&& r2.getJourMois() == r1.getJourMois()) {
				LocalTime time1 = r1.getHeureDebut().toLocalTime();
				LocalTime time2 = r2.getHeureDebut().toLocalTime();
				LocalTime time1fin = time1.plusHours(r1.getDuree().getHours()).plusMinutes(r1.getDuree().getMinutes());
				LocalTime time2fin = time2.plusHours(r2.getDuree().getHours()).plusMinutes(r2.getDuree().getMinutes());
				long delai1 = Duration.between(time1fin, time2).getSeconds();
				long delai2 = Duration.between(time2fin, time1).getSeconds();
				if ((delai1 <= Duration.ofMinutes(60).getSeconds() && delai1 > 0)
						|| (delai2 <= Duration.ofMinutes(60).getSeconds() && delai2 > 0)) {
					return r2;
				}
			}
		}
		return null;
	}

	public boolean fusionDeuxReservationsContigue(ReservationPermanente r1, ReservationPermanente r2) {
		try {
			int intervalle;
			LocalTime time1 = r1.getHeureDebut().toLocalTime();
			LocalTime time2 = r2.getHeureDebut().toLocalTime();
			PreparedStatement preparedStmt = conn
					.prepareStatement("UPDATE reservationpermanente set heure_debut = ?, duree = ? where id = ?");
			if (r1.getHeureDebut().before(r2.getHeureDebut())) {
				preparedStmt.setTime(1, r1.getHeureDebut());
				time2 = time2.plusHours(r2.getDuree().getHours()).plusMinutes(r2.getDuree().getMinutes());
				intervalle = (int) (Duration.between(time1, time2).getSeconds()/60);
			} else {
				preparedStmt.setTime(1, r2.getHeureDebut());
				time1 = time1.plusHours(r1.getDuree().getHours()).plusMinutes(r1.getDuree().getMinutes());
				intervalle = (int) (Duration.between(time2, time1).getSeconds()/60);
			}
			preparedStmt.setTime(2, new Time(0,intervalle,0));
			preparedStmt.setInt(3, r1.getId());
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
