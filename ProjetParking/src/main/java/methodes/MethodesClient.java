package methodes;

import java.time.ZonedDateTime;
import java.util.Scanner;

import mysql.ClientMysql;
import pojo.Client;
import pojo.Reservation;
import verificationsentreeclavier.MethodesFormatClavierInterface;
import verificationsentreeclavier.MethodesVerificationsAjoutClient;

public class MethodesClient {

	public static void ajouterUnClient() {
		String nom, prenom, adresse, mail, iban, mobile;

		System.out.println("Bienvenue dans l'interface de creation de compte d'un client ! ");
		nom = MethodesVerificationsAjoutClient.verifNomClient();
		prenom = MethodesVerificationsAjoutClient.verifPrenomClient();
		adresse = MethodesVerificationsAjoutClient.verifAdresse();
		mail = MethodesVerificationsAjoutClient.verifMail();
		iban = MethodesVerificationsAjoutClient.verifIban();
		mobile = MethodesVerificationsAjoutClient.verifMobile();

		Client client = new Client(mobile, nom, prenom, adresse, mail, iban);
		ClientMysql.getInstance().create(client);
		System.out.println("Inscription terminée.");
	}

	public static void modifierClient(Client client) {
		String nom, prenom, adresse, mail, IBAN, mobile;
		nom = client.getNom();
		prenom = client.getPrenom();
		adresse = client.getAdresse();
		mail = client.getMail();
		IBAN = client.getIban();
		mobile = client.getNumeroMobile();

		Scanner sc = new Scanner(System.in);
		String choixMenu = "";

		while (!choixMenu.equals("7")) {

			System.out.println("Bienvenue dans vos paramètres client. \n Souhaitez-vous modifier une information ? \n"
					+ "1 - Modifier mon adresse \n" + "2 - Modifier mon numéro de mobile \n"
					+ "3 - Modifier mon adresse mail \n" + "4 - Modifier mon nom \n" + "5 - Modifier mon prénom \n"
					+ "6 - Modifier mon IBAN \n " + "7 - Revenir au menu précédent");

			choixMenu = sc.nextLine();

			switch (choixMenu) {
			case "1":
				System.out.println("valeur actuelle : " + adresse);
				adresse = MethodesVerificationsAjoutClient.verifAdresse();
				client.setAdresse(adresse);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouvelle adresse : " + adresse);
				break;
			case "2":
				System.out.println("valeur actuelle : " + mobile);
				mobile = MethodesVerificationsAjoutClient.verifMobile();
				client.setNumeroMobile(mobile);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau numéro mobile : " + mobile);
				break;
			case "3":
				System.out.println("valeur actuelle : " + mail);
				mail = MethodesVerificationsAjoutClient.verifMail();
				client.setMail(mail);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau mail : " + mail);
				break;
			case "4":
				System.out.println("valeur actuelle : " + nom);
				nom = MethodesVerificationsAjoutClient.verifNomClient();
				client.setNom(nom);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau nom : " + nom);
				break;
			case "5":
				System.out.println("valeur actuelle : " + prenom);
				prenom = MethodesVerificationsAjoutClient.verifPrenomClient();
				client.setPrenom(prenom);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau prénom : " + prenom);
				break;
			case "6":
				System.out.println("valeur actuelle : " + IBAN);
				IBAN = MethodesVerificationsAjoutClient.verifIban();
				client.setPrenom(IBAN);
				ClientMysql.getInstance().update(client);
				System.out.println("Nouveau IBAN : " + IBAN);
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

	public static boolean consulterPlacesParkingDispo(String dateReserv, String heureReserv, String dureeReserv) {
		MethodesCalculs methodescalculs = new MethodesCalculs();
// Conversion des date debut et fin utilisateur en formatBDD		
		String dateDebutReservation = methodescalculs.conversionDateDebutReservationEnFormatBdd(dateReserv,
				heureReserv);
		String dateFinReservation = methodescalculs.conversionDateFinReservationEnFormatBdd(dateDebutReservation,
				dureeReserv);
		int placeClient = methodescalculs.numeroPlaceReservationClient(dateDebutReservation, dateFinReservation,
				dureeReserv);

		System.out
				.println("Recherche des places disponibles du  " + dateDebutReservation + " au  " + dateFinReservation);
		if (placeClient > 0) {

			System.out.println("Il reste des places disponibles ! ");

			return true;
		} else {
			System.out.println("Il n'y a plus de place pour ce créneau !");
			return false;
		}

	}

	public static void creeUneReservation(String dateReserv, String heureReserv, String dureeReserv, int numeroClient) {
		MethodesCalculs methodescalculs = new MethodesCalculs();
// Conversion des date debut et fin utilisateur en formatBDD		
		String dateDebutReservation = methodescalculs.conversionDateDebutReservationEnFormatBdd(dateReserv,
				heureReserv);
		String dateFinReservation = methodescalculs.conversionDateFinReservationEnFormatBdd(dateDebutReservation,
				dureeReserv);
		String ouiOuNon = "Veuillez rentrez 'o' pour oui ou 'n' pour non ";
		int placeClient = methodescalculs.numeroPlaceReservationClient(dateDebutReservation, dateFinReservation,
				dureeReserv);
	
		int duree = 0;
		boolean reponse = MethodesFormatClavierInterface.validerUneReservation(ouiOuNon);

		if (reponse) {

			System.out.println("Votre réservation a bien été prise en compte. \n" + "Récapitulatif :  \n"
					+ "- début de la réservation : " + dateDebutReservation + " \n" + "- fin de la réservation : "
					+ dateFinReservation + "\n" + "- votre numéro de place de parking réservé : " + placeClient
					+ " \n Retour au menu ... ");

			// Création de l'objet réservation
			duree = methodescalculs.conversionHeureMinuteEnMinute(dureeReserv);
			Reservation reservation = new Reservation(numeroClient, dateDebutReservation, dateFinReservation, duree,
					placeClient);
			// Insertion dans la BDD
			ClientMysql.getInstance().ajouterUneReservation(reservation);

		} else {
			System.out.println("Retour au menu ...");
		}
	}

	public static void modifierUneReservation(String dateReserv, String heureReserv, String dureeReserv,
			int numeroClient, Reservation reservation) {
		MethodesCalculs methodescalculs = new MethodesCalculs();
		// Conversion des date debut et fin utilisateur en formatBDD
		String dateDebutReservation = methodescalculs.conversionDateDebutReservationEnFormatBdd(dateReserv,
				heureReserv);
		String dateFinReservation = methodescalculs.conversionDateFinReservationEnFormatBdd(dateDebutReservation,
				dureeReserv);
		int placeClient = methodescalculs.numeroPlaceReservationClient(dateDebutReservation, dateFinReservation,
				dureeReserv);
			
		System.out.println(
				"Votre réservation a bien été modifié. \n" + "Récapitulatif :  \n" + "- début de la réservation : "
						+ dateDebutReservation + " \n" + "- fin de la réservation : " + dateFinReservation + "\n"
						+ "- votre numéro de place de parking réservé : " + placeClient + " \n Retour au menu ... ");
		reservation.modifierDateDebut(dateDebutReservation);
		ClientMysql.getInstance().modifierReservation(reservation);
	}

	public static void modifierDureeReservation(String dateReserv, String dureeReserv, int numeroClient,
			Reservation reservation) {

		MethodesCalculs methodescalculs = new MethodesCalculs();
		String dateFinReservation = methodescalculs.conversionDateFinReservationEnFormatBdd(dateReserv, dureeReserv);
		int placeClient = methodescalculs.numeroPlaceReservationClient(dateReserv, dateFinReservation, dureeReserv);

	
		
		System.out.println(
				"Votre réservation a bien été modifié. \n" + "Récapitulatif :  \n" + "- début de la réservation : "
						+ dateReserv + " \n" + "- fin de la réservation : " + dateFinReservation + "\n"
						+ "- votre numéro de place de parking réservé : " + placeClient + " \n Retour au menu ... ");
		reservation.modifierDuree(dureeReserv);
		ClientMysql.getInstance().modifierReservation(reservation);
	}

	public static boolean verifierProlongationPossible30Minutes(Reservation reservation) {
		return (reservation.getDate_fin().getTime() - 1800000) < ZonedDateTime.now().toInstant().toEpochMilli();
	}
		
	
}
