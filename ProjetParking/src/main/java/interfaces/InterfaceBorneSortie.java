package interfaces;
import methodes.MethodesClient;
import mysql.ClientMysql;
import pojo.Reservation;
import verificationsentreeclavier.MethodesFormatClavierInterface;

public class InterfaceBorneSortie {
	
	public static void lancerInterfaceBorne() {
		System.out.println("Bienvenue sur l'interface borne sortie du parking.");
		boolean fin = false;
		int reponse = MethodesFormatClavierInterface
				.entreeEntier("Veuillez rentrer votre numéro de client pour pouvoir sortir du parking.");
		// creer une autre methode, si date_fin < date_fin_reel il faut récupérer
		// l'objet réservation.
		Reservation reservation = ClientMysql.getInstance().verifierReservationCorrespondanteClientMemeJour(reponse);
		if (reservation != null) {
			MethodesClient.sortieParking(reservation);
		} else {
			Reservation reservationDelaiDepasse = ClientMysql.getInstance().recupererInfosReservationDelaiDepasse(reponse);
			if (reservationDelaiDepasse != null) {
			MethodesClient.sortieParking(reservationDelaiDepasse);	
			} else {
				System.out.println("Vous vous êtes trompé sur votre numéro de client, ou vous n'êtes pas censé être ici :) !");
			}
		}
	}
}