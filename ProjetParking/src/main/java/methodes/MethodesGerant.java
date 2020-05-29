package methodes;

import mysql.GerantMysql;
import mysql.RecherchePlaceDispoMysql;

public class MethodesGerant {

	public void changerNombreSurreservation(int saisieGerant) {
		int nbPlaceSurreservation = GerantMysql.getInstance().selectionnerNbPlaceSurreservation();
		int nbPlaceSurreservationEnCours = GerantMysql.getInstance().selectionnerNbPlaceSurreservationEnCours();
		int nbPlaceParking = RecherchePlaceDispoMysql.getInstance().nombrePlacesDuParking();
		// le gérant ne peut pas rentrer un nb > au nb de place du parking.
		// le gérant ne peut pas rentrer un nb > au nb de place qu'il reste en
		// surréservation en cours.
		if (saisieGerant > nbPlaceParking || nbPlaceSurreservationEnCours > saisieGerant) {
			System.out.println("Vous ne pouvez pas saisir cette valeur, veuillez essayer une autre valeur. ");
		} else {
			GerantMysql.getInstance().modifierNbPlaceSurreservation(nbPlaceSurreservation);
			GerantMysql.getInstance()
					.modifierNbPlaceSurreservationEnCours(nbPlaceSurreservation - nbPlaceSurreservationEnCours);
			System.out.println("Nouvelle valeur : " + nbPlaceSurreservation);
		}
	}
}
