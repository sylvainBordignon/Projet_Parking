package mysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		if(clientmysql==null)clientmysql=new ClientMysql(Connexion.getInstance());
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
}
