package verificationsentreeclavier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MethodesVerificationsDate {

	public boolean estValideDate(String dateUtilisateur) {

		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(dateUtilisateur);
		} catch (ParseException e) {
			return false;
		}
		System.out.println("OK");
		return true;
	}

	public boolean estValideHeureMinute(String dateUtilisateur) {

		DateFormat sdf = new SimpleDateFormat("HH:mm");
		sdf.setLenient(false);
		try {
			sdf.parse(dateUtilisateur);
		} catch (ParseException e) {
			return false;
		}
		System.out.println("OK");
		return true;
	}

}
