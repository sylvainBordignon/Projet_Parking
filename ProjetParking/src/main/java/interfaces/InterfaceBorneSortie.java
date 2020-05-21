package interfaces;

import methodes.MethodesClient;
import mysql.ClientMysql;
import mysql.FacturationMysql;
import pojo.Facturation;
import pojo.Reservation;
import verificationsentreeclavier.MethodesFormatClavierInterface;

public class InterfaceBorneSortie {

	public static void lancerInterfaceBorne() {
		System.out.println("Bienvenue sur l'interface borne sortie du parking.");
		boolean fin = false;
		while (!fin) {
			int reponse = MethodesFormatClavierInterface
					.entreeEntier("Veuillez rentrer votre numéro de client pour pouvoir sortir du parking.");
			// creer une autre methode, si date_fin < date_fin_reel il faut récupérer
			// l'objet réservation.
			Reservation reservation = ClientMysql.getInstance()
					.verifierReservationCorrespondanteClientMemeJour(reponse);
			if (reservation != null) {
				MethodesClient.sortieParking(reservation);
				FacturationMysql.getInstance().genererFacturation(new Facturation(reservation));
				fin = true;
			} else {
				Reservation reservationDelaiDepasse = ClientMysql.getInstance()
						.recupererInfosReservationDelaiDepasse(reponse);
				if (reservationDelaiDepasse != null) {
					MethodesClient.sortieParking(reservationDelaiDepasse);
					FacturationMysql.getInstance().genererFacturation(new Facturation(reservationDelaiDepasse));
					fin = true;
				} else {
					System.out.println(
							"Vous vous êtes trompé sur votre numéro de client, ou vous n'êtes pas censé être ici :) !");
				}
			}
		}
	}
}