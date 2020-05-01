package methodes;

import java.util.Scanner;

import org.omg.CORBA.SystemException;

import verificationsentreeclavier.MethodesVerificationsAjoutClient;

public class MethodesClient {
	
public void ajouterUnClient() {
	
MethodesVerificationsAjoutClient methodesverificationsajoutclient = new MethodesVerificationsAjoutClient();	
String nom = null,prenom=null,adresse=null,mail=null,RIB=null;
int numeroMobile;
Scanner sc = new Scanner(System.in);
boolean estValide=false;

System.out.println("Bienvenue dans l'interface de creation de compte d'un client ! ");
nom = methodesverificationsajoutclient.verifNomClient();
prenom = methodesverificationsajoutclient.verifPrenomClient();
adresse = methodesverificationsajoutclient.verifAdresse();
mail = methodesverificationsajoutclient.verifMail();


}




}


