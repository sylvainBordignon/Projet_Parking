package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connexionBDD.Connexion;
import pojo.Client;
import pojo.Reservation;

public class ClientMysql {
	Connection conn;

	private static ClientMysql clientmysql;

	public ClientMysql(Connection conn) {
		super();
		this.conn = conn;
	}

	public static ClientMysql getInstance() {
		if (clientmysql == null)
			clientmysql = new ClientMysql(Connexion.getInstance());
		return clientmysql;
	}

	public int create(Client obj) {
		int id = -1;
		try {
			PreparedStatement ins = this.conn.prepareStatement(
					"INSERT INTO Client (nom,prenom,adresse,numero_mobile,mail,RIB) VALUES (?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ins.setString(1, obj.getNom());
			ins.setString(2, obj.getPrenom());
			ins.setString(3, obj.getAdresse());
			ins.setString(4, obj.getNumeroMobile());
			ins.setString(5, obj.getMail());
			ins.setString(6, obj.getIban());
			ins.executeUpdate();
			ResultSet res = ins.getGeneratedKeys();
			if (res.next()) {
				id = res.getInt(1);
				res.close();
			}
			if (ins != null) {
				ins.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return id;
	}

	public boolean update(Client obj) {
		String sql = "UPDATE Client SET nom = ?, prenom = ?,adresse = ?,numero_mobile = ?,mail = ?,RIB = ? WHERE id= ?";
		try {
			PreparedStatement up = this.conn.prepareStatement(sql);
			up.setString(1, obj.getNom());
			up.setString(2, obj.getPrenom());
			up.setString(3, obj.getAdresse());
			up.setString(4, obj.getNumeroMobile());
			up.setString(5, obj.getMail());
			up.setString(6, obj.getIban());
			up.setInt(7, obj.getId());

			up.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<String> selectionnerListeVehicules(int numClient) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("SELECT num_plaque_immatricule FROM association where id_client=?");
			preparedStmt.setInt(1, numClient);
			ResultSet res = preparedStmt.executeQuery();
			ArrayList<String> listePlaques = new ArrayList<>();
			while (res.next()) {
				listePlaques.add(res.getString("num_plaque_immatricule"));
			}
			return listePlaques;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public boolean supprimerVehicule(String immatriculation, int numClient) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"SELECT num_plaque_immatricule FROM association where id_client=? and num_plaque_immatricule=?");
			preparedStmt.setInt(1, numClient);
			preparedStmt.setString(2, immatriculation);
			ResultSet res = preparedStmt.executeQuery();
			if (res.next()) {
				PreparedStatement preparedStmt2 = conn
						.prepareStatement("DELETE FROM association where id_client=? and num_plaque_immatricule=?");
				preparedStmt2.setInt(1, numClient);
				preparedStmt2.setString(2, immatriculation);
				preparedStmt2.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void ajouterVehicule(String immatriculation, int numClient) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("INSERT INTO association (num_plaque_immatricule,id_client) VALUES (?,?)");
			preparedStmt.setString(1, immatriculation);
			preparedStmt.setInt(2, numClient);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Client visualierInfoClient(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM client where id = ?");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			if (res.next()) {
				return new Client(res.getInt("id"), res.getString("numero_mobile"), res.getString("nom"),
						res.getString("prenom"), res.getString("adresse"), res.getString("mail"), res.getString("RIB"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Reservation visualiserReservationMemeJour(int id) {
		
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM reservation where id = ? and date_debut < now() and date_fin > now();");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			if (res.next()) {
				return new Reservation(res.getInt("id"), res.getInt("id_client"), res.getTimestamp("date_debut"),
						res.getTimestamp("date_fin"), res.getInt("duree"), res.getTimestamp("date_arrive_reel"),
						res.getTimestamp("date_depart_reel"), res.getInt("id_place"), res.getInt("delai_attente"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Reservation> selectionnerListeReservations(int numClient) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement("SELECT * FROM reservation where id_client=?");
			preparedStmt.setInt(1, numClient);
			ResultSet res = preparedStmt.executeQuery();
			ArrayList<Reservation> listePlaques = new ArrayList<>();
			while (res.next()) {
				Reservation reservation = new Reservation(res.getInt("id"), res.getInt("id_client"),
						res.getTimestamp("date_debut"), res.getTimestamp("date_fin"), res.getInt("duree"),
						res.getTimestamp("date_arrive_reel"), res.getTimestamp("date_depart_reel"),
						res.getInt("id_place"), res.getInt("delai_attente"));
				listePlaques.add(reservation);
			}
			return listePlaques;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public void ajouterUneReservation(Reservation reservation) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"INSERT INTO reservation (id_client, date_debut, date_fin, id_place,duree) VALUES (?,?,?,?,?)");
			preparedStmt.setInt(1, reservation.getId_client());
			preparedStmt.setTimestamp(2, reservation.getDate_debut());
			preparedStmt.setTimestamp(3, reservation.getDate_fin());
			preparedStmt.setInt(5, reservation.getDuree());
			preparedStmt.setInt(4, reservation.getId_place());
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void ajouterUneReservationDansHistorique(Reservation reservation) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"INSERT INTO historiquereservation (id_client, date_debut,id_place,duree,date_depart_reel,delai_attente,id,date_fin) VALUES (?,?,?,?,?,?,?,?)");
			preparedStmt.setInt(1, reservation.getId_client());
			preparedStmt.setTimestamp(2, reservation.getDate_debut());
			preparedStmt.setInt(3, reservation.getId_place());
			preparedStmt.setInt(4, reservation.getDuree());
			preparedStmt.setTimestamp(5,reservation.getDate_depart_reel());
			preparedStmt.setInt(6, reservation.getDelai_attente());
			preparedStmt.setInt(7, reservation.getId());
			preparedStmt.setTimestamp(8,reservation.getDate_fin());
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void ajouterUneReservationBorne(Reservation reservation) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"INSERT INTO reservation (id_client, date_debut, date_fin, id_place,duree, date_arrive_reel) VALUES (?,?,?,?,?, ?)");
			preparedStmt.setInt(1, reservation.getId_client());
			preparedStmt.setTimestamp(2, reservation.getDate_debut());
			preparedStmt.setTimestamp(3, reservation.getDate_fin());
			preparedStmt.setInt(5, reservation.getDuree());
			preparedStmt.setInt(4, reservation.getId_place());
			preparedStmt.setTimestamp(6, reservation.getDate_arrive_reel());
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean modifierReservation(Reservation reservation) {
		try {
			PreparedStatement preparedStmt2 = conn.prepareStatement(
					"UPDATE reservation set date_debut = ?, date_fin = ?, duree = ?, date_arrive_reel = ?,"
							+ " date_depart_reel = ?, id_place = ?, delai_attente = ? where id = ?");
			preparedStmt2.setTimestamp(1, reservation.getDate_debut());
			preparedStmt2.setTimestamp(2, reservation.getDate_fin());
			preparedStmt2.setInt(3, reservation.getDuree());
			preparedStmt2.setTimestamp(4, reservation.getDate_arrive_reel());
			preparedStmt2.setTimestamp(5, reservation.getDate_depart_reel());
			preparedStmt2.setInt(6, reservation.getId_place());
			preparedStmt2.setInt(7, reservation.getDelai_attente());
			preparedStmt2.setInt(8, reservation.getId());
			preparedStmt2.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Reservation verifierReservationCorrespondanteClientMemeJour(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"SELECT * FROM reservation r where id_client = ? and r.date_debut < now() and r.date_fin > now();");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			if (res.next()) {
				return new Reservation(res.getInt("id"), res.getInt("id_client"), res.getTimestamp("date_debut"),
						res.getTimestamp("date_fin"), res.getInt("duree"), res.getTimestamp("date_arrive_reel"),
						res.getTimestamp("date_depart_reel"), res.getInt("id_place"), res.getInt("delai_attente"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Reservation recupererInfosReservationDelaiDepasse(int id) {
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(
					"SELECT * FROM reservation r where id_client = ? and r.date_debut < now() and r.date_fin < now();");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			if (res.next()) {
				return new Reservation(res.getInt("id"), res.getInt("id_client"), res.getTimestamp("date_debut"),
						res.getTimestamp("date_fin"), res.getInt("duree"), res.getTimestamp("date_arrive_reel"),
						res.getTimestamp("date_depart_reel"), res.getInt("id_place"), res.getInt("delai_attente"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String editerDateDepartReel(int idclient) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String sDate = dateFormat.format(new Date());
		 sDate =  sDate.substring(0, 19);
		
		try {
		PreparedStatement preparedStmt = conn.prepareStatement("UPDATE reservation set date_depart_reel = ? where id = ?");
		preparedStmt.setString(1, sDate);
		preparedStmt.setInt(2, idclient);
	   preparedStmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return sDate;
		
	}

	public static void main(String[] args) {
		ClientMysql clientmysql = ClientMysql.getInstance();
		ArrayList<Reservation> reservations = clientmysql.selectionnerListeReservations(5);
		for (int i = 0; i < reservations.size(); i++) {
			System.out.println(reservations.get(i));
		}
	}
}
