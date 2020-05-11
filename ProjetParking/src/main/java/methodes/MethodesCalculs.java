package methodes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import mysql.GerantMysql;
import mysql.RecherchePlaceDispoMysql;

public class MethodesCalculs {
	public String conversionDateDebutReservationEnFormatBdd(String dateDebut, String heureDebut) {
		String annee = dateDebut.substring(6, 10);
		String mois = dateDebut.substring(3, 5);
		String jour = dateDebut.substring(0, 2);
		return annee + "-" + mois + "-" + jour + " " + heureDebut + ":" + "00";
	}

	public String conversionDateFinReservationEnFormatBdd(String dateHeureDebut, String heureFin) {
		int nbHeureFin = conversionHeureMinuteEnMinute(heureFin);
		Duration duree = Duration.ofMinutes(nbHeureFin);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldt = LocalDateTime.parse(dateHeureDebut, dtf);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Europe/Amsterdam"));
		ZonedDateTime dateFinale = zdt.plus(duree);
		return dtf.format(dateFinale);
	}

	public int conversionHeureMinuteEnMinute(String heureMinute) {
// format heureMinute = XX:XX
		String heure = heureMinute.substring(0, 2);
		String minute = heureMinute.substring(3, 5);
		int nbHeure = Integer.parseInt(heure);
		int nbMinute = Integer.parseInt(minute);

		return nbHeure * 60 + nbMinute;
	}

	public String conversionDureeMinuteEnFormatHeure(String heureMinute, int dureeMinute) {
		int nbHeureMinute = conversionHeureMinuteEnMinute(heureMinute);
		int total = nbHeureMinute + dureeMinute;
		int nbHeure = total / 60;
		int nbMinute = total % 60;
		return Integer.toString(nbHeure) + ":" + Integer.toString(nbMinute);
	}

	public String recupererHeureDansUneDate(String dateDebut) {
		return dateDebut.substring(11, 16);
	}

	public String recupererHeureFinDansUneDate(String dateDebut, int duree) {
		String heureDebut = recupererHeureDansUneDate(dateDebut);
		return conversionDureeMinuteEnFormatHeure(heureDebut, duree);
	}

	public boolean reservationEstSuperieureA24H(String duree) {
		return conversionHeureMinuteEnMinute(duree) >= 1440;
	}

	public String conversionDureeMinute2EnFormatHeure(int dureeMinute) {
		int nbHeure = dureeMinute / 60;
		int nbMinute = dureeMinute % 60;
		return Integer.toString(nbHeure) + ":" + Integer.toString(nbMinute);
	}

	public int nombreDeReservationPermanenteTotalDurantReservationClient(String dateDebutReservation,
			String dateFinReservation, String duree) {
		int nbPlaceJournaliere = RecherchePlaceDispoMysql.getInstance()
				.listeDesPlacesReservePermanentJournaliereDurantPeriodeReservationClient(dateDebutReservation,
						dateFinReservation, duree);
		int nbPlaceHebdomadaire = RecherchePlaceDispoMysql.getInstance()
				.listeDesPlacesReservePermanentHebdomadaireDurantPeriodeReservationClient(dateDebutReservation,
						dateFinReservation, duree);
		int nbPlaceMensuelle = RecherchePlaceDispoMysql.getInstance()
				.listeDesPlacesReservePermanentMensuelleDurantPeriodeReservationClient(dateDebutReservation,
						dateFinReservation, duree);

		return nbPlaceJournaliere + nbPlaceHebdomadaire + nbPlaceMensuelle;

	}

	public int numeroPlaceReservationClient(String dateDebutReservation, String dateFinReservation, String duree) {
		int numeroPlace = 0;
		// Récupération de toute les places du parking
		List<Integer> listeDesPlacesDuParking = RecherchePlaceDispoMysql.getInstance().listeDesPlacesDuParking();
		// Récupération des places occupées pour la période du client
		List<Integer> listeDesPlacesReserve = RecherchePlaceDispoMysql.getInstance()
				.listeDesPlacesReserveDurantPeriodeReservationClient(dateDebutReservation, dateFinReservation);

		// place du parking dispo après la soustraction des places dans réservation
		listeDesPlacesDuParking.removeAll(listeDesPlacesReserve);

		int nombreDeReservationPermanente;
		nombreDeReservationPermanente = this.nombreDeReservationPermanenteTotalDurantReservationClient(
				dateDebutReservation, dateFinReservation, duree);

		
		if (listeDesPlacesDuParking.size() > nombreDeReservationPermanente) {
			numeroPlace = listeDesPlacesDuParking.get(0);
		}else {
			// y a plus de place pour cette date de réservation , il faut regarder les sureservations. 
		int nbPlaceSureserve =GerantMysql.getInstance().selectionnerNbPlaceSurreservationEnCours();
		

			if (nbPlaceSureserve > 0) {
			
				// on accepte quand même la réservation et on soustrait de 1 le nb de suréservation en cours
				GerantMysql.getInstance().modifierNbPlaceSurreservationEnCours(nbPlaceSureserve-1);
				return nbPlaceSureserve;
				
			} else {
			// il n'y a plus du tout de place 	
			return 0;	
			}
			
		}
		
		return numeroPlace;

	}

	public String conversionMinuteEnFormatHeure(int dureeMinute) {
		int nbHeure = dureeMinute / 60;
		int nbMinute = dureeMinute % 60;
		String heure, minute;

		if (nbHeure < 10) {
			heure = "0" + Integer.toString(nbHeure);
		} else {
			heure = Integer.toString(nbHeure);
		}

		if (nbMinute < 10) {
			minute = "0" + Integer.toString(nbMinute);
		} else {
			minute = Integer.toString(nbMinute);
		}

		return heure + ":" + minute;
	}

}
