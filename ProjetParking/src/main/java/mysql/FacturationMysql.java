package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connexionBDD.Connexion;
import pojo.Facturation;

public class FacturationMysql {

	Connection conn;

	private static FacturationMysql facturationmysql;

	public FacturationMysql(Connection conn) {
		super();
		this.conn = conn;
	}

	public static FacturationMysql getInstance() {
		if (facturationmysql == null)
			facturationmysql = new FacturationMysql(Connexion.getInstance());
		return facturationmysql;
	}

	public int genererFacturation(Facturation facturation) {
		try {
			PreparedStatement ins = this.conn.prepareStatement(
					"INSERT INTO facturation (id_client, id_historique_reservation, cout_normal, cout_depassement, cout_remboursement, cout_prolongation_attente) VALUES (?,?,?,?,?,?)");
			ins.setInt(1, facturation.getIdClient());
			ins.setInt(2, facturation.getIdHistoriqueReservation());
			ins.setFloat(3, facturation.getCoutNormal());
			ins.setFloat(4, facturation.getCoutDepassement());
			ins.setFloat(5, facturation.getCoutRemboursement());
			ins.setFloat(6, facturation.getCoutProlongationAttente());
			int res = ins.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			
			}
		return 0;
	}

	public ArrayList<Facturation> selectionnerFacturationsClient(int idClient) {
		ArrayList<Facturation> facturations = new ArrayList<>();
		try {
			PreparedStatement ins = this.conn.prepareStatement("Select * from facturation where id_client = ?");
			ins.setInt(1, idClient);
			ResultSet res = ins.executeQuery();
			while (res.next()) {
				facturations.add(new Facturation(res.getInt("id"), res.getInt("id_client"),
						res.getInt("id_historique_reservation"), res.getFloat("cout_normal"),
						res.getFloat("cout_depassement"), res.getFloat("cout_remboursement"),
						res.getFloat("cout_prolongation_attente")));
			}
			return facturations;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			
			}
		return facturations;
	}
	
	public ArrayList<Facturation> selectionnerFacturationsClientMois(int idClient) {
		ArrayList<Facturation> facturations = new ArrayList<>();
		try {
			PreparedStatement ins = this.conn.prepareStatement("Select * from facturation f, historiquereservation r where f.id_historique_reservation = r.id and f.id_client = ? and MONTH(r.date_debut) = MONTH(now()) and YEAR(r.date_debut) = YEAR(now())");
			ins.setInt(1, idClient);
			ResultSet res = ins.executeQuery();
			while (res.next()) {
				facturations.add(new Facturation(res.getInt("id"), res.getInt("id_client"),
						res.getInt("id_historique_reservation"), res.getFloat("cout_normal"),
						res.getFloat("cout_depassement"), res.getFloat("cout_remboursement"),
						res.getFloat("cout_prolongation_attente")));
			}
			return facturations;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			
			}
		return facturations;
	}
	
	public ArrayList<Facturation> selectionnerToutesLesFacturations() {
		ArrayList<Facturation> facturations = new ArrayList<>();
		try {
			PreparedStatement ins = this.conn.prepareStatement("Select * from  facturation");
			ResultSet res = ins.executeQuery();
			while (res.next()) {
				facturations.add(new Facturation(res.getInt("id"), res.getInt("id_client"),
						res.getInt("id_historique_reservation"), res.getFloat("cout_normal"),
						res.getFloat("cout_depassement"), res.getFloat("cout_remboursement"),
						res.getFloat("cout_prolongation_attente")));
			}
			return facturations;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			
			}
		return facturations;
	}
}
