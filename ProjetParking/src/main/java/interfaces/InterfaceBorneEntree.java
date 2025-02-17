package interfaces;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import mysql.AssociationMysql;
import mysql.ClientMysql;
import mysql.PlaceParkingMysql;
import pojo.Client;
import pojo.Reservation;
import verificationsentreeclavier.MethodesFormatClavierInterface;

public class InterfaceBorneEntree {
	public static final String MESSAGE_ERREUR = "Veuillez entrer une information correcte.";
	public static final String MESSAGE_ACCEPTATION = "Vous occupez la place ... pour une durée de ... Merci de votre visite.";
	public static final String MESSAGE_DEDOMMAGEMENT = "Il n'y a pas de places disponibles. Vous recevrez un dédommagement. Veuillez nous excuser.";
	public static final String MESSAGE_SANS_DEDOMMAGEMENT = "Il n'y a pas de places disponibles. Veuillez nous excuser.";
	private static int placesLibres;
	public static final String RETOUR_MENU_PRINCIPAL = "\nTapez '0' pour retourner au menu principal";
	public static final Scanner in = new Scanner(System.in);

	public static void lancerInterfaceBorne() {
		System.out.println("Bienvenue sur l'interface du parking.");
		boolean fin = false;
		while (!fin) {
			int reponse = MethodesFormatClavierInterface
					.entreeEntier("Avez vous une réservation?\n1 - Oui\n2 - Non" + RETOUR_MENU_PRINCIPAL);
			switch (reponse) {
			case 1:
				fin = true;
				InterfaceBorneEntree.menuReservation();
				break;
			case 2:
				fin = true;
				InterfaceBorneEntree.menuSansReservation();
				break;
			case 0:
				fin = true;
				InterfaceGenerale.interfaceGenerale();
				break;
			default:
				System.out.println(MESSAGE_ERREUR);
				break;
			}
		}
	}

	private static void menuReservation() {
		int numReservation = MethodesFormatClavierInterface
				.entreeEntier("Veuillez entrer votre numéro de réservation." + RETOUR_MENU_PRINCIPAL);
		if (numReservation == 0) {
			InterfaceGenerale.interfaceGenerale();
		} else {
			Reservation reservation = ClientMysql.getInstance().visualiserReservationMemeJour(numReservation);
			if (reservation != null) {// v�rification que le num�ro reservation existe dans la base
				placesLibres = PlaceParkingMysql.getInstance().verifierNombrePlaceDispo();
				if (placesLibres == 0)
					System.out.println(MESSAGE_DEDOMMAGEMENT);// rajouter la facture ???
				else {
					if (PlaceParkingMysql.getInstance().verifierPlaceNonPrise(reservation.getIdPlace())) {
						// si la place n'est pas prise
						PlaceParkingMysql.getInstance().changerEtatPlaceOccupee(reservation.getIdPlace());
					} else {
						System.out.println("Votre place réservée est encore occupée, veuillez aller à la place :");
						int idPlace = PlaceParkingMysql.getInstance().trouverPlaceDisponible();
						System.out.println("place " + idPlace);
						PlaceParkingMysql.getInstance().changerEtatPlaceOccupee(idPlace);
						reservation.setIdPlace(idPlace);
					}
					reservation.setDateArriveReel(new Timestamp(System.currentTimeMillis()));
					ClientMysql.getInstance().modifierReservation(reservation);
					System.out.println("Vous occupez la place " + reservation.getIdPlace() + " pour une durée de "
							+ reservation.getDuree() + " minutes. Merci de votre visite.");
				}
			} else {// le num�ro n'existe pas dans la base
				System.out.println("Numéro de reservation inconnu.");
				int reponse = InterfaceBorneEntree.reessayer();
				if (reponse == 1)
					InterfaceBorneEntree.menuReservation();
				else if (reponse == 2)
					InterfaceBorneEntree.menuSansReservation();
			}
		}
	}

	private static void menuSansReservation() {
		String immatriculation = MethodesFormatClavierInterface
				.entreePlaqueImmatriculation("Veuillez entrer votre plaque d'immatriculation.");
		if (!AssociationMysql.getInstance().verificationPlaqueExiste(immatriculation)) {// la plaque n'existe pas
			System.out.println("Numéro d'immatriculation inconnu.");
			int reponse = InterfaceBorneEntree.reessayer();
			if (reponse == 1)
				InterfaceBorneEntree.menuSansReservation();
			else if (reponse == 2)
				InterfaceBorneEntree.verifNumClient();
		} else {// la plaque existe, on v�rifie qu'une reservation existe
			InterfaceBorneEntree.verifReservationExistePlaque(immatriculation);
		}
	}

	private static void verifNumClient() {
		int numClient = MethodesFormatClavierInterface.entreeEntier("Veuillez entrer votre numéro de client.");
		Client client = ClientMysql.getInstance().visualierInfoClient(numClient);
		if (client == null) {// le num�ro n'existe pas dans la base
			System.out.println("Numéro de client inconnu.");
			int reponse = InterfaceBorneEntree.reessayer();
			if (reponse == 1)
				InterfaceBorneEntree.verifNumClient();
			else if (reponse == 2)// numéro de client non reconnu
				System.out.println("Veuillez vous diriger vers la sortie. Merci pour votre visite.");
		} else {// le numero existe dans la base, on vérifie qu'une reservation existe
			InterfaceBorneEntree.verifReservationExisteClient(numClient);
		}
	}

	private static void verifReservationExistePlaque(String immatriculation) {
		Reservation reservation = AssociationMysql.getInstance()
				.verifierReservationCorrespondantePlaqueMemeJour(immatriculation);
		if (reservation != null) {// v�rification qu'une reservation existe � cette date
			clientAvecReservation(reservation);
		} else {
			placesLibres = PlaceParkingMysql.getInstance().verifierNombrePlaceDispo();
			if (placesLibres == 0)
				System.out.println(MESSAGE_SANS_DEDOMMAGEMENT);
			else {
				List<Integer> liste = AssociationMysql.getInstance().clientsUtilisantCeVehicule(immatriculation);
				if (liste.size() == 1) {
					InterfaceBorneEntree.dureeSejour(liste.get(0));
				} else {
					System.out
							.println("Il y a plusieurs utilisateurs associés à ce véhicule, veuillez vous identifier.");
					InterfaceBorneEntree.verifNumClient();
				}
			}
		}
	}

	private static void verifReservationExisteClient(int client) {
		Reservation reservation = ClientMysql.getInstance().verifierReservationCorrespondanteClientMemeJour(client);
		if (reservation != null) {// v�rification qu'une reservation existe � cette date
			clientAvecReservation(reservation);
		} else {
			placesLibres = PlaceParkingMysql.getInstance().verifierNombrePlaceDispo();
			if (placesLibres == 0)
				System.out.println(MESSAGE_SANS_DEDOMMAGEMENT);
			else {
				System.out.println(
						"Vous n'avez pas de réservation à cette date. Une nouvelle réservation va être créée.");
				InterfaceBorneEntree.dureeSejour(client);
			}
		}
	}

	private static void dureeSejour(int idClient) {
		int duree = MethodesFormatClavierInterface
				.entreeEntier("Combien de temps souhaitez vous rester ? (en minutes)" + RETOUR_MENU_PRINCIPAL);
		if (duree == 0)// retour au menu principal
			InterfaceGenerale.interfaceGenerale();
		else {// si la duree est correcte
			int place = PlaceParkingMysql.getInstance().trouverPlaceDisponible();
			Reservation reservation = new Reservation(idClient, new Timestamp(System.currentTimeMillis()), duree, place,
					new Timestamp(System.currentTimeMillis()));
			ClientMysql.getInstance().ajouterUneReservationBorne(reservation);
			System.out.println("Vous occupez la place " + reservation.getIdPlace() + " pour une durée de "
					+ reservation.getDuree() + " minutes.  Merci de votre visite.");
		}
	}

	public static int reessayer() {
		return MethodesFormatClavierInterface.entreeEntier("Voulez vous reessayer?\n1 - Oui\n2 - Non");
	}

	public static void clientAvecReservation(Reservation reservation) {
		placesLibres = PlaceParkingMysql.getInstance().verifierNombrePlaceDispo(); // Module *2
		if (placesLibres == 0)
			System.out.println(MESSAGE_DEDOMMAGEMENT);// rajouter la facture ???
		else {
			if (PlaceParkingMysql.getInstance().verifierPlaceNonPrise(reservation.getIdPlace())) {
				// si la place n'est pas prise
				PlaceParkingMysql.getInstance().changerEtatPlaceOccupee(reservation.getIdPlace());
			} else {
				System.out.println("Votre place réservée est encore occupée, veuillez aller à la place :");
				int idPlace = PlaceParkingMysql.getInstance().trouverPlaceDisponible();
				System.out.println("place " + idPlace);
				PlaceParkingMysql.getInstance().changerEtatPlaceOccupee(idPlace);
				reservation.setIdPlace(idPlace);
			}
			reservation.setDateArriveReel(new Timestamp(System.currentTimeMillis()));
			ClientMysql.getInstance().modifierReservation(reservation);
			System.out.println("Vous occupez la place " + reservation.getIdPlace() + " pour une durée de "
					+ reservation.getDuree() + " minutes.  Merci de votre visite.");
		}
	}
}