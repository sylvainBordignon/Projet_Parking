package mysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	public static void main(String[] args) {
		ClientMysql clientmysql = ClientMysql.getInstance();
		ArrayList<String> listeVehicules=(ArrayList<String>) clientmysql.selectionnerListeVehicules(5);
		System.out.println(listeVehicules.size());
		for(int i=0; i<listeVehicules.size(); i++) {
			System.out.println("Véhicule "+ (i+1)+ " :" + listeVehicules.get(i));
		}
		clientmysql.supprimerVehicule("YO-542-YO", 5);
		listeVehicules=(ArrayList<String>) clientmysql.selectionnerListeVehicules(5);
		System.out.println(listeVehicules.size());
		for(int i=0; i<listeVehicules.size(); i++) {
			System.out.println("Véhicule "+ (i+1)+ " :" + listeVehicules.get(i));
		}
		
	}

}
