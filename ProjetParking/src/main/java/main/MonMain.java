
package main;
import java.sql.Connection;
import ConnexionBDD.Connexion;
import methodes.MethodesClient;
import verificationsentreeclavier.MethodesVerificationsDate;

public class MonMain {

	public static void main(String[] args) {
		 
	//	Connection maConnexion = Connexion.getInstance();
	//	MethodesVerificationsDate m = new MethodesVerificationsDate();
	//	InterfaceGerant it = new InterfaceGerant();
		
	//	it.lancerInterfaceGerant();
		
	//	m.estValideDate("28/02/1990");
		
	//	m.estValideHeureMinute("23:45");
		
		MethodesClient m = new MethodesClient();
		m.ajouterUnClient();
		
	  
		  
		  
	}

}
