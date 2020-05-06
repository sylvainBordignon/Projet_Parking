package mysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ConnexionBDD.Connexion;
import pojo.Client;


public class ClientMysql {
	Connection conn ;
	
	private static ClientMysql clientmysql;
	
	public ClientMysql(Connection conn) {
		super();
		this.conn = conn;
	}
	public static ClientMysql getInstance(){
		if(clientmysql==null)
			clientmysql=new ClientMysql(Connexion.getInstance());
		return clientmysql;
	}
	public int create(Client obj) {
		int id=-1;	
		try {
			PreparedStatement ins = this.conn.prepareStatement("INSERT INTO Client (nom,prenom,adresse,numero_mobile,mail,RIB) VALUES (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			 ins.setString(1, obj.getNom());
			 ins.setString(2, obj.getPrenom());
			 ins.setString(3, obj.getAdresse());
			 ins.setString(4, obj.getNumeroMobile());
			 ins.setString(5, obj.getMail());
			 ins.setString(6, obj.getIBAN());
			 ins.executeUpdate();
			 ResultSet res=ins.getGeneratedKeys();
			 if(res.next()){
			id = res.getInt(1);
			res.close();	 
			 } 
			 if(ins!=null){	 
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
			 up.setString(6, obj.getIBAN());
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
			while(res.next())
			{
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
			PreparedStatement preparedStmt = conn
					.prepareStatement("SELECT num_plaque_immatricule FROM association where id_client=? and num_plaque_immatricule=?");
			preparedStmt.setInt(1, numClient);
			preparedStmt.setString(2, immatriculation);
			ResultSet res = preparedStmt.executeQuery();
			if(res.next()) {
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
			PreparedStatement preparedStmt = conn
					.prepareStatement("SELECT * FROM client where id = ?");
			preparedStmt.setInt(1, id);
			ResultSet res = preparedStmt.executeQuery();
			if(res.next()) {
				return new Client(res.getInt("id"), res.getString("numero_mobile"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("mail"), res.getString("RIB"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> selectionnerListeReservations(int numClient) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("SELECT id, date_debut, date_fin FROM reservation where id_client=?");
			preparedStmt.setInt(1, numClient);
			ResultSet res = preparedStmt.executeQuery();
			ArrayList<String> listePlaques = new ArrayList<>();
			while(res.next())
			{
				Date dateDebut=res.getTimestamp("date_debut");
				Date dateFin=res.getTimestamp("date_fin");
				long dureeMillis=dateFin.getTime()-dateDebut.getTime();
				long minutes = dureeMillis / (60 * 1000) % 60; 
				long heures = dureeMillis / (60 * 60 * 1000);
				String reservation="ID: "+res.getInt("id")+", Date début: " 
						+ res.getTimestamp("date_debut")+", Durée: "+heures+"h"+minutes;
				listePlaques.add(reservation);
			}
			return listePlaques;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public void ajouterUneReservation(int numClient, String dateDebut, int heures, int minutes) {
		try {
			PreparedStatement preparedStmt = conn
					.prepareStatement("INSERT INTO reservation (id_client, date_debut, date_fin, id_place) VALUES (?,?,?,?)");
			LocalDateTime date_debut = LocalDateTime.parse(dateDebut,
				    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			long millis = date_debut.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+((heures*60 + minutes)*60*1000);
			Timestamp dateFin=new Timestamp(millis);
			preparedStmt.setInt(1, numClient);
			preparedStmt.setString(2, dateDebut);
			preparedStmt.setTimestamp(3, dateFin);
			preparedStmt.setInt(4, 1);//ajouter une attribution de place automatique
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean modifierDateReservation(int id, String date) {
		try {
			PreparedStatement preparedStmt1 = conn
					.prepareStatement("SELECT id, date_debut, date_fin FROM reservation where id = ?");
			preparedStmt1.setInt(1, id);
			ResultSet res = preparedStmt1.executeQuery();
			if(res.next()) {
				LocalDateTime ancienne_date_debut = LocalDateTime.parse(res.getString("date_debut"),
					    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s"));
				LocalDateTime date_fin = LocalDateTime.parse(res.getString("date_fin"),
					    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s"));
				LocalDateTime date_debut = LocalDateTime.parse(date,
					    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				long millis = date_debut.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+(
						date_fin.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
						-ancienne_date_debut.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
				Timestamp dateFin=new Timestamp(millis);
				PreparedStatement preparedStmt2= conn
						.prepareStatement("UPDATE reservation set date_debut = ?, date_fin = ? where id = ?");
				preparedStmt2.setString(1, date);
				preparedStmt2.setTimestamp(2, dateFin);
				preparedStmt2.setInt(3, id);
				preparedStmt2.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean modifierDureeReservation(int id, int heures, int minutes) {
		try {
			PreparedStatement preparedStmt1 = conn
					.prepareStatement("SELECT id, date_debut FROM reservation where id = ?");
			preparedStmt1.setInt(1, id);
			ResultSet res = preparedStmt1.executeQuery();
			if(res.next()) {
				LocalDateTime date_debut = LocalDateTime.parse(res.getString("date_debut"),
					    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s"));
				System.out.println(res.getString("date_debut"));
				long millis = date_debut.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+((heures*60 + minutes)*60*1000);
				Timestamp dateFin=new Timestamp(millis);
				PreparedStatement preparedStmt2 = conn
						.prepareStatement("UPDATE reservation set date_fin = ? where id= ?");
				preparedStmt2.setTimestamp(1, dateFin);
				preparedStmt2.setInt(2, id);
				preparedStmt2.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public static void main(String[] args) {
		ClientMysql clientmysql = ClientMysql.getInstance();
//		ArrayList<String> reservations=(ArrayList<String>) clientmysql.selectionnerListeReservations(5);
//		for(int i=0; i<reservations.size(); i++) {
//			System.out.println(reservations.get(i));
//		}
		clientmysql.modifierDateReservation(4,"2020-05-06 10:10:00");
//		boolean oui=clientmysql.modifierDureeReservation(4, 1, 5);
//		System.out.println(oui);
//		clientmysql.ajouterUneReservation(5, "2020-05-06 09:32:00", 3, 10);
	}

}
