package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnexionBDD.Connexion;

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
	
	public float selectionnerNbPlaceSurreservation() {
		return selectionnerUnTarif("nb_places_surréservation");
	}
	
	public void modifierNbPlaceSurreservation(float montant) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("UPDATE parametresParking set valeur = ? where nom = ?");
			preparedStmt.setFloat(1, montant);
			preparedStmt.setString(2, "nb_places_surréservation");
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GerantMysql gerantmysql = GerantMysql.getInstance();
		System.out.println("tarif normal : "+gerantmysql.selectionnerTarifNormal());
		System.out.println("tarif depassement : "+gerantmysql.selectionnerTarifDepassement());
		System.out.println("tarif prolongation : "+gerantmysql.selectionnerTarifProlongationAttente());
	}
}