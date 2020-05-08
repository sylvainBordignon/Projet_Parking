package methodes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
		String heure = dateDebut.substring(11, 16);
		return heure;
	}

	public String recupererHeureFinDansUneDate(String dateDebut, int duree) {
		String heureDebut = recupererHeureDansUneDate(dateDebut);
		return conversionDureeMinuteEnFormatHeure(heureDebut, duree);
	}

	public boolean ReservationEstSuperieureA24H(String duree) {
		int minutes = conversionHeureMinuteEnMinute(duree);
		if (minutes < 1440) {
			return false;
		} else {
			return true;
		}
	}

	public String conversionDureeMinute2EnFormatHeure(int dureeMinute) {
		int nbHeure = dureeMinute / 60;
		int nbMinute = dureeMinute % 60;
		return Integer.toString(nbHeure) + ":" + Integer.toString(nbMinute);
	}

	public int nombreDeReservationPermanenteTotalDurantReservationClient(String dateDebutReservation,
			String dateFinReservation, String duree) {
		RecherchePlaceDispoMysql rechercheplacedispomysql = new RecherchePlaceDispoMysql(null);
		int nbPlaceJournaliere = rechercheplacedispomysql
				.getInstance().listeDesPlacesReservePermanentJournaliereDurantPeriodeReservationClient(dateDebutReservation,
						dateFinReservation, duree);
		int nbPlaceHebdomadaire = rechercheplacedispomysql
				.getInstance().listeDesPlacesReservePermanentHebdomadaireDurantPeriodeReservationClient(dateDebutReservation,
						dateFinReservation, duree);
		int nbPlaceMensuelle = rechercheplacedispomysql
				.getInstance().listeDesPlacesReservePermanentMensuelleDurantPeriodeReservationClient(dateDebutReservation,
						dateFinReservation, duree);

		return nbPlaceJournaliere + nbPlaceHebdomadaire + nbPlaceMensuelle;

	}

	public int numeroPlaceReservationClient(String dateDebutReservation, String dateFinReservation, String duree) {
        int numeroPlace=0;
		// Récupération de toute les places du parking
		RecherchePlaceDispoMysql rechercheplacedispomysql = new RecherchePlaceDispoMysql(null);
		List<Integer> listeDesPlacesDuParking = new ArrayList<Integer>();
		listeDesPlacesDuParking = rechercheplacedispomysql.getInstance().listeDesPlacesDuParking();
		System.out.println("Liste place parking : " + listeDesPlacesDuParking);
		// Récupération des places occupées pour la période du client
		List<Integer> listeDesPlacesReserve = new ArrayList<Integer>();
		listeDesPlacesReserve = rechercheplacedispomysql.getInstance()
				.listeDesPlacesReserveDurantPeriodeReservationClient(dateDebutReservation, dateFinReservation);
		System.out.println("Liste place reserve : " + listeDesPlacesReserve);

		// place du parking dispo après la soustraction des places dans réservation
		listeDesPlacesDuParking.removeAll(listeDesPlacesReserve);

		int nombreDeReservationPermanente;
		nombreDeReservationPermanente =	this.nombreDeReservationPermanenteTotalDurantReservationClient(dateDebutReservation, dateFinReservation, duree);
		System.out.println("nb place permanente :"+nombreDeReservationPermanente);
		
		if(listeDesPlacesDuParking.size() > nombreDeReservationPermanente) {
			numeroPlace =listeDesPlacesDuParking.get(0);
		}
		
		listeDesPlacesDuParking.size();
		
		System.out.println(numeroPlace);
		
		return   numeroPlace ; 

	}

}
