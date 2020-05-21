package mysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import connexionBDD.Connexion;
import methodes.MethodesCalculs;

public class RecherchePlaceDispoMysql {
	Connection conn;
	private static RecherchePlaceDispoMysql rechercheplacedispomysql;

	public RecherchePlaceDispoMysql(Connection conn) {
		super();
		this.conn = conn;
	}

	public static RecherchePlaceDispoMysql getInstance() {
		if (rechercheplacedispomysql == null)
			rechercheplacedispomysql = new RecherchePlaceDispoMysql(Connexion.getInstance());
		return rechercheplacedispomysql;
	}

	public List<Integer> listeDesPlacesReserveDurantPeriodeReservationClient(String dateDebutReservation,
			String dateFinReservation) {
		List<Integer> listeDesPlaces = new ArrayList<Integer>() ;
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"SELECT id_place FROM reservation where ( date_debut <= ? AND date_fin >= ? ) OR (date_debut >= ? AND date_debut < ?  )");
			preparedStmt.setString(1, dateDebutReservation);
			preparedStmt.setString(2, dateDebutReservation);
			preparedStmt.setString(3, dateDebutReservation);
			preparedStmt.setString(4, dateFinReservation);
			ResultSet res = preparedStmt.executeQuery();
			while (res.next()) {
				listeDesPlaces.add(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeDesPlaces;
	}

	public List<Integer> listeDesPlacesDuParking() {
		List<Integer> listeDesPlaces = new ArrayList<Integer>();
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT id FROM  placeparking");
			ResultSet res = preparedStmt.executeQuery();
			while (res.next()) {
				listeDesPlaces.add(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeDesPlaces;
	}
	

	public int listeDesPlacesReservePermanentJournaliereDurantPeriodeReservationClient(String dateDebutReservation,
			String dateFinReservation, String duree) {
		MethodesCalculs methodescalculs = new MethodesCalculs();
		String heureDebutReservation = methodescalculs.recupererHeureDansUneDate(dateDebutReservation);
		String heureFinReservation = methodescalculs.recupererHeureDansUneDate(dateFinReservation);
		int nbPlaceJournaliere = 0;
		int dureeReservation = methodescalculs.conversionHeureMinuteEnMinute(duree);
		int dureeTest = methodescalculs.conversionHeureMinuteEnMinute(duree);
		dureeTest = 1440 - dureeTest;
		String strDureeTest = methodescalculs.conversionDureeMinute2EnFormatHeure(dureeTest);

		if (methodescalculs.reservationEstSuperieureA24H(duree) == false) {
// la reservation du client dure moins de 24h 
			DateFormat sdf = new SimpleDateFormat("HH:mm");
			sdf.setLenient(false);
			Date heureParseDebutReservation = null;
			Date heureParseFinReservation = null;
			try {
				heureParseDebutReservation = sdf.parse(heureDebutReservation);
				heureParseFinReservation = sdf.parse(heureFinReservation);
			} catch (ParseException e) {
				System.out.println("erreur format date");
			}
			if (heureParseDebutReservation.after(heureParseFinReservation)) {
				// heure debut : 19:00 ; duree = 22:00H-17h lendemain

				try {
					PreparedStatement preparedStmt = conn.prepareStatement(
							"SELECT count(*)" + "FROM reservationpermanente " + "WHERE  type = 'journalière' ");
					ResultSet res = preparedStmt.executeQuery();

					while (res.next()) {
						nbPlaceJournaliere = res.getInt(1);
					
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					PreparedStatement preparedStmt2 = conn.prepareStatement("SELECT count(*)"
							+ "FROM reservationpermanente " + "WHERE  type = 'journalière' " + "AND ("
							+ "(heure_debut > ? AND heure_debut <  CONVERT(?, TIME) + CONVERT(?, TIME)) "
							+ "  OR (heure_debut < ? AND   CONVERT(heure_debut, TIME) + CONVERT(duree, TIME)  > CONVERT(?, TIME) )"
							+ ")");
					preparedStmt2.setString(1, heureFinReservation);
					preparedStmt2.setString(2, strDureeTest);
					preparedStmt2.setString(3, heureFinReservation);
					preparedStmt2.setString(4, heureFinReservation);
					preparedStmt2.setString(5, heureFinReservation);

					ResultSet res2 = preparedStmt2.executeQuery();

					while (res2.next()) {
						nbPlaceJournaliere = nbPlaceJournaliere - res2.getInt(1);
					
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				

				return nbPlaceJournaliere;

			} else {
				
				nbPlaceJournaliere = 0;
				// heure debut = 13:45 ; heure fin = 18:00
				try {
					PreparedStatement preparedStmt = conn.prepareStatement("SELECT count(*)"
							+ "FROM reservationpermanente " + "WHERE  type = 'journalière' " + "AND ("
							+ "(heure_debut > ? AND heure_debut <  CONVERT(?, TIME) + CONVERT(?, TIME)) "
							+ "  OR (heure_debut < ? AND   CONVERT(heure_debut, TIME) + CONVERT(duree, TIME)  > CONVERT(?, TIME) )"
							+ ")");
					preparedStmt.setString(1, heureDebutReservation);
					preparedStmt.setString(2, duree);
					preparedStmt.setString(3, heureDebutReservation);
					preparedStmt.setString(4, heureDebutReservation);
					preparedStmt.setString(5, heureDebutReservation);

					ResultSet res = preparedStmt.executeQuery();
					nbPlaceJournaliere = 0;
					while (res.next()) {
						nbPlaceJournaliere = res.getInt(1);
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				return nbPlaceJournaliere;
			}
		} else {
			// On enlève toutes les places journalières
			System.out.println("La réservation dure plus de 24H !!! ");
			try {
				PreparedStatement preparedStmt = conn.prepareStatement(
						"SELECT DISTINCT count(*) FROM reservationpermanente " + "WHERE type ='journalière'");
				ResultSet res = preparedStmt.executeQuery();
				while (res.next()) {
					nbPlaceJournaliere = res.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return nbPlaceJournaliere;
		}
	}

	public int listeDesPlacesReservePermanentHebdomadaireDurantPeriodeReservationClient(String dateDebutReservation,
			String dateFinReservation, String duree) {
		MethodesCalculs methodescalculs = new MethodesCalculs();
		String heureDebutReservation = methodescalculs.recupererHeureDansUneDate(dateDebutReservation);
		int nbPlaceHebdo=0;
		// cas si c'est sur 1 jour
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT count(*) FROM reservationpermanente "
					+ " WHERE type ='hebdomadaire'" + " AND jour_semaine =((DAYOFWEEK(?)+5)%7+1)" + "AND ("
					+ "(heure_debut > ? AND heure_debut <  CONVERT(?, TIME) + CONVERT(?, TIME)) "
					+ "  OR (heure_debut < ? AND   CONVERT(heure_debut, TIME) + CONVERT(duree, TIME)  > CONVERT(?, TIME) )"
					+ ")");
			preparedStmt.setString(1, dateDebutReservation);

			preparedStmt.setString(2, heureDebutReservation);
			preparedStmt.setString(3, duree);
			preparedStmt.setString(4, heureDebutReservation);
			preparedStmt.setString(5, heureDebutReservation);
			preparedStmt.setString(6, heureDebutReservation);

			ResultSet res = preparedStmt.executeQuery();
			while (res.next()) {
				nbPlaceHebdo = res.getInt(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbPlaceHebdo;
	}

	public int listeDesPlacesReservePermanentMensuelleDurantPeriodeReservationClient(String dateDebutReservation,
			String dateFinReservation, String duree) {
		MethodesCalculs methodescalculs = new MethodesCalculs();
		String heureDebutReservation = methodescalculs.recupererHeureDansUneDate(dateDebutReservation);
		int nbPlaceMensuelle=0;
		// cas si c'est sur 1 jour
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT count(*) FROM reservationpermanente "
					+ " WHERE type ='mensuelle'" + " AND jour_mois = (DAYOFMONTH(?))" + "AND ("
					+ "(heure_debut > ? AND heure_debut <  CONVERT(?, TIME) + CONVERT(?, TIME)) "
					+ "  OR (heure_debut < ? AND   CONVERT(heure_debut, TIME) + CONVERT(duree, TIME)  > CONVERT(?, TIME) )"
					+ ")");
			preparedStmt.setString(1, dateDebutReservation);
			preparedStmt.setString(2, heureDebutReservation);
			preparedStmt.setString(3, duree);
			preparedStmt.setString(4, heureDebutReservation);
			preparedStmt.setString(5, heureDebutReservation);
			preparedStmt.setString(6, heureDebutReservation);

			ResultSet res = preparedStmt.executeQuery();
			while (res.next()) {
				nbPlaceMensuelle = res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbPlaceMensuelle;
	}
	
	
	
	public int nombrePlacesDuParking() {
		int nbPlaces=0;
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT count(*) FROM  placeparking");
			ResultSet res = preparedStmt.executeQuery();
			while (res.next()) {
			nbPlaces =res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbPlaces;
	}
	

	
}
