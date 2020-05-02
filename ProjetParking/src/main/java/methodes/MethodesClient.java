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

public void modifierClient(Client client) {
MethodesVerificationsAjoutClient methodesverificationsajoutclient = new MethodesVerificationsAjoutClient();		
String nom,prenom,adresse,mail,IBAN,mobile;
int id;

id = client.getId();
nom = client.getNom();
prenom = client.getPrenom();
adresse = client.getAdresse();
mail = client.getMail();
IBAN = client.getIBAN();
mobile = client.getNumeroMobile();

Scanner sc = new Scanner(System.in);
String choixMenu="";

while(choixMenu.equals("7") ==false) {


System.out.println("Bienvenue dans vos paramètres client. \n Souhaitez-vous modifier une information ? \n"
		+ "1 - Modifier mon adresse \n"
		+ "2 - Modifier mon numéro de mobile \n"
		+ "3 - Modifier mon adresse mail \n"
		+ "4 - Modifier mon nom \n"
		+ "5 - Modifier mon prénom \n"
		+ "6 - Modifier mon IBAN \n "
		+ "7 - Revenir au menu précédent");

choixMenu = sc.nextLine();	
	
	
switch (choixMenu) {
case "1":
	System.out.println("valeur actuelle : "+adresse);
	adresse = methodesverificationsajoutclient.verifAdresse();
	client.setAdresse(adresse);
	ClientMysql.getInstance().update(client);
	break;
case "2":
	System.out.println("valeur actuelle : "+mobile);
	mobile = methodesverificationsajoutclient.verifMobile();
	client.setNumeroMobile(mobile);
	ClientMysql.getInstance().update(client);
	break;
case "3":
	System.out.println("valeur actuelle : "+mail);
	mail = methodesverificationsajoutclient.verifMail();
	client.setMail(mail);
	ClientMysql.getInstance().update(client);
	break;	
case "4":
	System.out.println("valeur actuelle : "+nom);
	nom = methodesverificationsajoutclient.verifNomClient();
	client.setNom(nom);
	ClientMysql.getInstance().update(client);
	break;
case "5":
	System.out.println("valeur actuelle : "+prenom);
	prenom = methodesverificationsajoutclient.verifPrenomClient();
	client.setPrenom(prenom);
	ClientMysql.getInstance().update(client);
	break;
case "6":
	System.out.println("valeur actuelle : "+IBAN);
	IBAN = methodesverificationsajoutclient.verifIban();
	client.setPrenom(IBAN);
	ClientMysql.getInstance().update(client);
	break;
case "7":
	System.out.println("Retour au menu ...");
	break;
	
	
default :
	System.out.println("Veuillez choisir un nombre compris entre 1 et 7");
	break;
}

}

}


}


