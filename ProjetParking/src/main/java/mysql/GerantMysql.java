package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import connexionBDD.Connexion;

public class GerantMysql {
	Connection conn;

	private static GerantMysql gerantmysql;

	public GerantMysql(Connection conn) {
		super();
		this.conn = conn;
	}

	public static GerantMysql getInstance() {
		if (gerantmysql == null)
			gerantmysql = new GerantMysql(Connexion.getInstance());
		return gerantmysql;
	}

	public float selectionnerTarifNormal() {
		return selectionnerUnTarif("tarif_normal");
	}

	public float selectionnerTarifDepassement() {
		return selectionnerUnTarif("tarif_dépassement");
	}

	public float selectionnerTarifProlongationAttente() {
		return selectionnerUnTarif("tarif_prolongation_attente");
	}

	public float selectionnerUnTarif(String tarif) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("SELECT valeur FROM parametresParking where nom = ?");
			preparedStmt.setString(1, tarif);
			ResultSet res = preparedStmt.executeQuery();
			res.next();
			return res.getFloat(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void modifierTarifNormal(float montant) {
		modifierUnTarif(montant, "tarif_normal");
	}

	public void modifierTarifDepassement(float montant) {
		modifierUnTarif(montant, "tarif_dépassement");
	}

	public void modifierTarifProlongationAttente(float montant) {
		modifierUnTarif(montant, "tarif_prolongation_attente");
	}

	public void modifierUnTarif(float montant, String tarif) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("UPDATE parametresParking set valeur = ? where nom = ?");
			preparedStmt.setFloat(1, montant);
			preparedStmt.setString(2, tarif);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int selectionnerNbPlaceSurreservation() {
		return (int)selectionnerUnTarif("nb_places_surréservation");
	}
	
	public int selectionnerNbPlaceSurreservationEnCours() {
		return (int)selectionnerUnTarif("nb_places_surréservation_en_cours");
	}
	
	public void modifierNbPlaceSurreservation(int nbPlace) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("UPDATE parametresParking set valeur = ? where nom = ?");
			preparedStmt.setInt(1, nbPlace);
			preparedStmt.setString(2, "nb_places_surréservation");
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void modifierNbPlaceSurreservationEnCours(int nbPlace) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("UPDATE parametresParking set valeur = ? where nom = ?");
			preparedStmt.setInt(1, nbPlace);
			preparedStmt.setString(2, "nb_places_surréservation_en_cours");
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void visualiserLesReservationsEnCours() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 String sDate = dateFormat.format(new Date());
	 sDate =  sDate.substring(0, 19);
		
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"SELECT nom,prenom,id_place,date_fin,date_debut,duree,date_arrive_reel,delai_attente FROM reservation,client where ( date_debut< ? AND date_fin > ?  )"
					+ " AND reservation.id_client=client.id");
			preparedStmt.setString(1,sDate);
			preparedStmt.setString(2,sDate);
			ResultSet res = preparedStmt.executeQuery();
			while (res.next()) {
			System.out.println(res.getString("nom")+"  "+res.getString("prenom")+"  "+res.getInt("id_place")+"  "+res.getString("date_debut")+"  "+res.getString("date_fin")
			+"  "+res.getInt("duree")+"  "+res.getString("date_arrive_reel")+"  "+res.getInt("delai_attente"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}