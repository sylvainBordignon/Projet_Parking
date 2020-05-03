package methodes;

import java.util.Scanner;
import org.omg.CORBA.SystemException;

import mysql.ClientMysql;
import pojo.Client;
import verificationsentreeclavier.MethodesVerificationsAjoutClient;

public class MethodesClient {
	
public void ajouterUnClient() {
	
MethodesVerificationsAjoutClient methodesverificationsajoutclient = new MethodesVerificationsAjoutClient();	
String nom,prenom,adresse,mail,IBAN,mobile;
Scanner sc = new Scanner(System.in);
boolean estValide=false;

System.out.println("Bienvenue dans l'interface de creation de compte d'un client ! ");
nom = methodesverificationsajoutclient.verifNomClient();
prenom = methodesverificationsajoutclient.verifPrenomClient();
adresse = methodesverificationsajoutclient.verifAdresse();
mail = methodesverificationsajoutclient.verifMail();
IBAN= methodesverificationsajoutclient.verifIban();
mobile=methodesverificationsajoutclient.verifMobile();

Client client = new Client(mobile,nom,prenom,adresse,mail,IBAN);
ClientMysql.getInstance().create(client);


}


}


