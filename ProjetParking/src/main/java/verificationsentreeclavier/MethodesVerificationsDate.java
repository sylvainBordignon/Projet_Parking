package verificationsentreeclavier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class MethodesVerificationsDate {

	private static final String MESSAGE_ERREUR_PASSEE = "Notre système ne permet seulement de visualiser les places disponibles dans le futur. ";

	public static boolean estValideDate(String dateUtilisateur) {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		String date = sdf.format(new Date());
		try {
			sdf.parse(dateUtilisateur);
		} catch (ParseException e) {
			return false;
		}
		// vérifier si dateUtilisateur > date du jour
		Date dateParseUtilisateur = null;
		Date dateParseDateCourante = null;
		try {
			dateParseUtilisateur = sdf.parse(dateUtilisateur);
			dateParseDateCourante = sdf.parse(date);
			if (dateParseUtilisateur.compareTo(dateParseDateCourante) < 0) {
				System.out.println(MESSAGE_ERREUR_PASSEE);
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	public static boolean estValideDateFormat(String dateUtilisateur) {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(dateUtilisateur);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static boolean estValideHeureMinuteMemeJour(String dateUtilisateur) {
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		sdf.setLenient(false);
		String date = sdf.format(new Date());
		Date dateParseUtilisateur = null;
		Date dateParseDateCourante = null;
		try {
			dateParseUtilisateur = sdf.parse(dateUtilisateur);
			dateParseDateCourante = sdf.parse(date);
			if (dateParseUtilisateur.before(dateParseDateCourante)) {
				System.out.println(MESSAGE_ERREUR_PASSEE);
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static boolean estValideHeureMinute(String dateUtilisateur) {
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		sdf.setLenient(false);
		try {
			sdf.parse(dateUtilisateur);
		} catch (ParseException e) {
			return false;
		}

		return dateUtilisateur.length() == 5;
	}

	public boolean estValideFormatReservation(String dureeReservationUtilisateur) {
		return Pattern.matches("^[0-9]{1,2}[:.,-][0-9]{1,2}?$", dureeReservationUtilisateur);
	}
}
