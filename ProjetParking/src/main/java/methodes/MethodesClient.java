package methodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mysql.ClientMysql;
import mysql.RecherchePlaceDispoMysql;
import pojo.Client;
import pojo.Reservation;
import verificationsentreeclavier.MethodesFormatClavierInterface;
import verificationsentreeclavier.MethodesVerificationsAjoutClient;

public class MethodesClient {

	public static void ajouterUnClient() {

		MethodesVerificationsAjoutClient methodesverificationsajoutclient = new MethodesVerificationsAjoutClient();
		String nom, prenom, adresse, mail, IBAN, mobile;
		Scanner sc = new Scanner(System.in);
		boolean estValide = false;

		System.out.println("Bienvenue dans l'interface de creation de compte d'un client ! ");
		nom = methodesverificationsajoutclient.verifNomClient();
		prenom = methodesverificationsajoutclient.verifPrenomClient();
		adresse = methodesverificationsajoutclient.verifAdresse();
		mail = methodesverificationsajoutclient.verifMail();
		IBAN = methodesverificationsajoutclient.verifIban();
		mobile = methodesverificationsajoutclient.verifMobile();

		Client client = new Client(mobile, nom, prenom, adresse, mail, IBAN);
		ClientMysql.getInstance().create(client);
		System.out.println("Inscription terminée.");
	}

	public static void modifierClient(Client client) {
		MethodesVerificationsAjoutClient methodesverificationsajoutclient = new MethodesVerificationsAjoutClient();
		String nom, prenom, adresse, mail, IBAN, mobile;
		int id;

		id = client.getId();
		nom = client.getNom();
		prenom = client.getPrenom();
		adresse = client.getAdresse();
		mail = client.getMail();
		IBAN = client.getIBAN();
		mobile = client.getNumeroMobile();

		Scanner sc = new Scanner(System.in);
		String choixMenu = "";

		while (choixMenu.equals("7") == false) {

			System.out.println("Bienvenue dans vos paramètres client. \n Souhaitez-vous modifier une information ? \n"
					+ "1 - Modifier mon adresse \n" + "2 - Modifier mon numéro de mobile \n"
					+ "3 - Modifier mon adresse mail \n" + "4 - Modifier mon nom \n" + "5 - Modifier mon prénom \n"
					+ "6 - Modifier mon IBAN \n " + "7 - Revenir au menu précédent");

			choixMenu = sc.nextLine();

			switch (choixMenu) {
			case "1":
				System.out.println("valeur actuelle : " + adresse);
				adresse = methodesverificationsajoutclient.verifAdresse();
				client.setAdresse(adresse);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouvelle adresse : "+adresse);
				break;
			case "2":
				System.out.println("valeur actuelle : " + mobile);
				mobile = methodesverificationsajoutclient.verifMobile();
				client.setNumeroMobile(mobile);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau numéro mobile : "+mobile);
				break;
			case "3":
				System.out.println("valeur actuelle : " + mail);
				mail = methodesverificationsajoutclient.verifMail();
				client.setMail(mail);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau mail : "+mail);
				break;
			case "4":
				System.out.println("valeur actuelle : " + nom);
				nom = methodesverificationsajoutclient.verifNomClient();
				client.setNom(nom);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau nom : "+nom);
				break;
			case "5":
				System.out.println("valeur actuelle : " + prenom);
				prenom = methodesverificationsajoutclient.verifPrenomClient();
				client.setPrenom(prenom);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau prénom : "+prenom);
				break;
			case "6":
				System.out.println("valeur actuelle : " + IBAN);
				IBAN = methodesverificationsajoutclient.verifIban();
				client.setPrenom(IBAN);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau IBAN : "+IBAN);
				break;
			case "7":
				System.out.println("Retour au menu ...");
				break;
			default:
				System.out.println("Veuillez choisir un nombre compris entre 1 et 7");
				break;
			}
		}

	}
	
	public boolean	consulterPlacesParkingDispo(String dateReserv, String heureReserv,String dureeReserv){
		
		MethodesCalculs methodescalculs = new MethodesCalculs();
		MethodesFormatClavierInterface methodesformatclavierinterface = new MethodesFormatClavierInterface();
// Conversion des date debut et fin utilisateur en formatBDD		
String dateDebutReservation	 =	methodescalculs.conversionDateDebutReservationEnFormatBdd(dateReserv,heureReserv);
String dateFinReservation	= methodescalculs.conversionDateFinReservationEnFormatBdd(dateDebutReservation, dureeReserv);
int placeClient = methodescalculs.numeroPlaceReservationClient(dateDebutReservation, dateFinReservation, dureeReserv);

System.out.println("Recherche des places disponibles du  "+dateDebutReservation+ " au  "+dateFinReservation);		
		if(placeClient > 0) {
			
			System.out.println("Il reste des places disponibles ! ");
			
			return true;			
		}else {
		   System.out.println("Il n'y a plus de place pour ce créneau !");
		   return false;
		}
	
			}
	
	public void	creeUneReservation(String dateReserv, String heureReserv,String dureeReserv,int numeroClient){
		MethodesCalculs methodescalculs = new MethodesCalculs();
		MethodesFormatClavierInterface methodesformatclavierinterface = new MethodesFormatClavierInterface();
// Conversion des date debut et fin utilisateur en formatBDD		
String dateDebutReservation	 =	methodescalculs.conversionDateDebutReservationEnFormatBdd(dateReserv,heureReserv);
String dateFinReservation	= methodescalculs.conversionDateFinReservationEnFormatBdd(dateDebutReservation, dureeReserv);
String OUI_NON = "Veuillez rentrez 'o' pour oui ou 'n' pour non ";
int placeClient = methodescalculs.numeroPlaceReservationClient(dateDebutReservation, dateFinReservation, dureeReserv);
int duree =0;
boolean reponse;
reponse= MethodesFormatClavierInterface.validerUneReservation(OUI_NON);
			
			if(reponse = true) {
			
				System.out.println("Votre réservation a bien été prise en compte. \n"
						+ "Récapitulatif :  \n"
						+ "- début de la réservation : "+dateDebutReservation+" \n"
						+"- fin de la réservation : "+dateFinReservation+"\n"
						+ "- votre numéro de place de parking réservé : "+placeClient+
						" \n Retour au menu ... "
						);	
			
				// Création de l'objet réservation 	
			 duree = methodescalculs.conversionHeureMinuteEnMinute(dureeReserv);
				Reservation reservation = new Reservation(numeroClient,dateDebutReservation,dateFinReservation,duree,placeClient);
				// Insertion dans la BDD
				ClientMysql.getInstance().ajouterUneReservation(reservation);
				
			}else {
			System.out.println("Retour au menu ...");	
			}
			}
	
	
	public void	modifierUneReservation(String dateReserv, String heureReserv,String dureeReserv,int numeroClient,Reservation reservation){
		MethodesCalculs methodescalculs = new MethodesCalculs();
		MethodesFormatClavierInterface methodesformatclavierinterface = new MethodesFormatClavierInterface();
// Conversion des date debut et fin utilisateur en formatBDD		
String dateDebutReservation	 =	methodescalculs.conversionDateDebutReservationEnFormatBdd(dateReserv,heureReserv);
String dateFinReservation	= methodescalculs.conversionDateFinReservationEnFormatBdd(dateDebutReservation, dureeReserv);
String OUI_NON = "Veuillez rentrez 'o' pour valider la modification  ou 'n' pour l'annuler ";
int placeClient = methodescalculs.numeroPlaceReservationClient(dateDebutReservation, dateFinReservation, dureeReserv);
				System.out.println("Votre réservation a bien été modifié. \n"
						+ "Récapitulatif :  \n"
						+ "- début de la réservation : "+dateDebutReservation+" \n"
						+"- fin de la réservation : "+dateFinReservation+"\n"
						+ "- votre numéro de place de parking réservé : "+placeClient+
						" \n Retour au menu ... "
						);	
		int duree = methodescalculs.conversionHeureMinuteEnMinute(dureeReserv);	
	//	Reservation reservation= new Reservation(numeroClient,dateDebutReservation,dateFinReservation,duree,placeClient);
		reservation.modifierDateDebut(dateDebutReservation);
		ClientMysql.getInstance().modifierReservation(reservation);	
			}
	
	public void modifierDureeReservation(String dateReserv,String dureeReserv, int numeroClient, Reservation reservation) {
	
		MethodesCalculs methodescalculs = new MethodesCalculs();
		String dateFinReservation	= methodescalculs.conversionDateFinReservationEnFormatBdd(dateReserv, dureeReserv);
		int placeClient = methodescalculs.numeroPlaceReservationClient(dateReserv, dateFinReservation, dureeReserv);
		
		System.out.println("Votre réservation a bien été modifié. \n"
				+ "Récapitulatif :  \n"
				+ "- début de la réservation : "+dateReserv+" \n"
				+"- fin de la réservation : "+dateFinReservation+"\n"
				+ "- votre numéro de place de parking réservé : "+placeClient+
				" \n Retour au menu ... "
				);	
		reservation.modifierDuree(dureeReserv);
		ClientMysql.getInstance().modifierReservation(reservation);		
	}
}
