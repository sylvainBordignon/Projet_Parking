package interfaces;

import java.sql.Timestamp;
import java.util.Scanner;

import mysql.ClientMysql;
import mysql.PlaceParkingMysql;
import pojo.Client;
import pojo.Reservation;
import verificationsentreeclavier.MethodesFormatClavierInterface;

public class InterfaceBorne {
	public static final String MESSAGE_ERREUR = "Veuillez entrer une information correcte.";
	public static final String MESSAGE_ACCEPTATION = "Vous occupez la place ... pour une durée de ... Merci de votre visite.";
	public static final String MESSAGE_DEDOMMAGEMENT = "Il n'y a pas de places disponibles. Vous recevrez un dédommagement. Veuillez nous excuser.";
	public static final String MESSAGE_SANS_DEDOMMAGEMENT = "Il n'y a pas de places disponibles. Veuillez nous excuser.";
	public static int PLACES_LIBRES;
	public static final String RETOUR_MENU_PRINCIPAL = "\nTapez '0' pour retourner au menu principal";
	public static Scanner in = new Scanner(System.in);

	public static void lancerInterfaceBorne() {
		System.out.println("Bienvenue sur l'interface du parking.");
		int reponse = MethodesFormatClavierInterface
				.entreeEntier("Avez vous une réservation?\n1 - Oui\n2 - Non" + RETOUR_MENU_PRINCIPAL);
		switch (reponse) {
		case 1:
			InterfaceBorne.menuReservation();
			break;
		case 2:
			InterfaceBorne.menuSansReservation();
			break;
		case 0:
			InterfaceGenerale.interfaceGenerale();
			break;
		default:
			// cas defaut ???
			break;
		}
	}

	private static void menuReservation() {
		int numReservation = MethodesFormatClavierInterface
				.entreeEntier("Veuillez entrer votre numéro de réservation." + RETOUR_MENU_PRINCIPAL);
		if (numReservation == 0) {
			InterfaceGenerale.interfaceGenerale();
		} else {
			Reservation reservation = ClientMysql.getInstance().visualiserReservation(numReservation);
			if (reservation != null) {// v�rification que le num�ro reservation existe dans la base
				PLACES_LIBRES = PlaceParkingMysql.getInstance().verifierNombrePlaceDispo();
				if (PLACES_LIBRES == 0)
					System.out.println(MESSAGE_DEDOMMAGEMENT);//facture ???
				else {
					if (PlaceParkingMysql.getInstance().verifierPlaceNonPrise(reservation.getId_place())) {
						// si la place n'est pas prise
						PlaceParkingMysql.getInstance().changerEtatPlaceOccupee(reservation.getId_place());
					} else {
						System.out.println("Votre place réservée est encore occupée, veuillez aller à la place :");
						int idPlace = PlaceParkingMysql.getInstance().trouverPlaceDisponible();
						System.out.println("place "+idPlace);
						PlaceParkingMysql.getInstance().changerEtatPlaceOccupee(idPlace);
						reservation.setId_place(idPlace);
					}
					reservation.setDate_arrive_reel(new Timestamp(System.currentTimeMillis()));
					ClientMysql.getInstance().modifierReservation(reservation);
					System.out.println("Vous occupez la place "+reservation.getId_place()+" pour une durée de "+reservation.getDuree()+" minutes.  Merci de votre visite.");
				}
			} else {// le num�ro n'existe pas dans la base
				System.out.println("Numéro de reservation inconnu.");
				int reponse = InterfaceBorne.reessayer();
				if (reponse == 1)
					InterfaceBorne.menuReservation();
				else if (reponse == 2)
					InterfaceBorne.menuSansReservation();
			}
		}
	}

	private static void menuSansReservation() {
		String immatriculation = MethodesFormatClavierInterface
				.entreePlaqueImmatriculation("Veuillez entrer votre plaque d'immatriculation.");
		if (immatriculation.equalsIgnoreCase("plaqueInconnue")) {// la plaque n'existe pas dans la base
			System.out.println("Numéro d'immatriculation inconnu.");
			int reponse = InterfaceBorne.reessayer();
			if (reponse == 1)
				InterfaceBorne.menuSansReservation();
			else if (reponse == 2)
				InterfaceBorne.verifNumClient();
		} else {// la plaque existe, on v�rifie qu'une reservation existe
			InterfaceBorne.verifReservationExiste(immatriculation);
		}
	}

	private static void verifNumClient() {
		int numClient = MethodesFormatClavierInterface.entreeEntier("Veuillez entrer votre numéro de client.");
		Client client = ClientMysql.getInstance().visualierInfoClient(numClient);
		if (client == null) {// le num�ro n'existe pas dans la base
			System.out.println("Numéro de client inconnu.");
			int reponse = InterfaceBorne.reessayer();
			if (reponse == 1)
				InterfaceBorne.verifNumClient();
			else if (reponse == 2)// numéro de client non reconnu
				System.out.println("Veuillez vous diriger vers la sortie. Merci pour votre visite.");
		} else {// le numero existe dans la base, on vérifie qu'une reservation existe
			// InterfaceBorne.verifReservationExiste(numClient);
		}
	}

	private static void verifReservationExiste(String immatriculationOuClient) {
		if (immatriculationOuClient.equalsIgnoreCase("existe")) {// v�rification qu'une reservation existe � cette date
			if (PLACES_LIBRES == 0)// s'il n'y a plus de place
				System.out.println(MESSAGE_DEDOMMAGEMENT);
			else
				System.out.println(MESSAGE_ACCEPTATION);// on enregistre la pr�sence du client dans la base
		} else if (immatriculationOuClient.equalsIgnoreCase("existePas")) {
			System.out.println("Vous n'avez pas de réservation à cette date.");
			if (PLACES_LIBRES == 0)
				System.out.println(MESSAGE_SANS_DEDOMMAGEMENT);
			else
				InterfaceBorne.dureeSejour();
		} else if (immatriculationOuClient.equals("-1"))// retour au menu principal
			InterfaceGenerale.interfaceGenerale();
	}

	private static void dureeSejour() {
		int duree = MethodesFormatClavierInterface
				.entreeEntier("Combien de temps souhaitez vous rester? (en minutes)" + RETOUR_MENU_PRINCIPAL);
		if (duree == 0)// retour au menu principal
			InterfaceGenerale.interfaceGenerale();
		else// si la duree est correcte
			System.out.println(MESSAGE_ACCEPTATION);// on enregistre la pr�sence du client dans la base
	}

	public static int reessayer() {
		return MethodesFormatClavierInterface.entreeEntier("Voulez vous reessayer?\n1 - Oui\n2 - Non");
	}// 133
}