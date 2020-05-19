package interfaces;
import mysql.ClientMysql;
import mysql.GerantMysql;
import pojo.Reservation;
import verificationsentreeclavier.MethodesFormatClavierInterface;

public class InterfaceBorneSortie {

	public static void lancerInterfaceBorne() {
		System.out.println("Bienvenue sur l'interface borne sortie du parking.");
		boolean fin = false;
			int reponse = MethodesFormatClavierInterface
					.entreeEntier("Veuillez rentrer votre numéro de client pour pouvoir sortir du parking.");
			Reservation reservation  =ClientMysql.getInstance().verifierReservationCorrespondanteClientMemeJour(reponse);
			if (reservation != null) {
				String sDate; 
				// Si oui , on UPDATE la date de fin de stationnement , on libère la place du parking, nb_surreservation en cours + 1 si < à nb_surreserrvation	
				// enregistrement fin d'occupation de place 
				ClientMysql.getInstance().editerDateDepartReel(reponse);
				// reservation.setDate_depart_reel(date_depart_reel);
				int nbPlaceSurreservation, nbPlaceSurreservationEnCours;
				
				nbPlaceSurreservation =GerantMysql.getInstance().selectionnerNbPlaceSurreservation();
				nbPlaceSurreservationEnCours = GerantMysql.getInstance().selectionnerNbPlaceSurreservationEnCours();
				
				if (nbPlaceSurreservationEnCours<nbPlaceSurreservation) {
					
				GerantMysql.getInstance().modifierNbPlaceSurreservationEnCours(nbPlaceSurreservationEnCours+1);
					
				}
				System.out.println("Vous pouvez sortir, Bonne journée ! ");
				
			} else {
				System.out.println("Vous vous êtes trompé sur votre numéro de client !");
			}
			
			
	}	
	
}
