package verificationsentreeclavier;

import java.util.Scanner;
import java.util.regex.Pattern;

import mysql.ClientMysql;

public class MethodesVerificationsAjoutClient {

	private static Scanner sc = new Scanner(System.in);

	// doit comporter uniquement des lettres et une taille < à 30
	public static String verifNomClient() {
		String nom = null;
		boolean estValide = false;
		System.out.println("Veuillez rentrer votre nom\n nom :  ");
		while (!estValide) {
			nom = sc.nextLine();
			if (formatNom(nom)) {
				estValide = true;
			} else {
				System.out.println(
						"Veuillez rentrer un nom correct ( uniquement des caractères et ayant une taille inférieure à 30) \n nom :  ");
			}
		}
		return nom;
	}

	public static boolean formatNom(String nom) {
		return Pattern.matches("[A-zÀ-ú- ]+", nom) && (nom.length() > 1 && nom.length() <= 30);
	}

	// doit comporter uniquement des lettres et une taille < à 30
	public static String verifPrenomClient() {
		String prenom = null;
		boolean estValide = false;
		System.out.println("Veuillez rentrer votre prenom \n prenom :  ");
		while (!estValide) {
			prenom = sc.nextLine();
			if (formatPrenom(prenom)) {
				estValide = true;
			} else {
				System.out.println(
						"Veuillez rentrer un prenom correct ( uniquement des caractères et ayant une taille inférieure à 30) \n prenom :  ");
			}
		}
		return prenom;
	}

	public static boolean formatPrenom(String prenom) {
		return Pattern.matches("[A-zÀ-ú-]+", prenom) && (prenom.length() > 1 && prenom.length() <= 30);
	}

	// une adresse peut contenir n'importe quel type de caractères sauf + /
	public static String verifAdresse() {
		String adresse = null;
		boolean estValide = false;
		System.out.println("Veuillez rentrer votre adresse \n adresse :  ");
		while (!estValide) {
			adresse = sc.nextLine();
			if (formatAdresse(adresse)) {
				estValide = true;
			} else {
				System.out.println(
						"Veuillez rentrer une adresse correcte ( max 50 caractères, les caractères spéciaux sont interdits) ");
			}
		}
		return adresse;
	}

	public static boolean formatAdresse(String adresse) {
		return adresse.length() < 50 && Pattern.matches("[A-zÀ-ú0-9'_ ]*$", adresse);
	}

	// xxxx@xx.xxx
	public static String verifMail() {
		String mail = null;
		boolean estValide = false;
		System.out.println("Veuillez rentrer votre mail \n mail : ");
		while (!estValide) {
			mail = sc.nextLine();
			if (formatMail(mail)) {
				estValide = true;
			} else {
				System.out.println("Veuillez rentrer une adresse mail valide ");
			}
		}

		return mail;
	}

	public static boolean formatMail(String mail) {
		return Pattern.matches("^(.+)@(.+)$", mail);
			
		
		
	
	}
	
	public static boolean checkFormatEtExisteMail(String mail) {
		
	
		if(Pattern.matches("^(.+)@(.+)$", mail) == true) {
			
		Boolean b =ClientMysql.getInstance().adresseMailExisteDeja(mail);
		 
		if( b == true) {
		System.out.println("Désolé cette adresse mail est déjà associé à un compte client, veuillez en choisir une autre  !  ");
		return false;
		}else {
			
			return true;
		}
		
		}else {
			return false;
		}
		
	}

	public static String verifIban() {
		String iban = null;
		boolean estValide = false;
		System.out.println("Veuillez rentrer votre IBAN \n IBAN : ");
		while (!estValide) {
			iban = sc.nextLine();
			if (formatIban(iban)) {
				estValide = true;
			} else {
				System.out.println("Veuillez rentrer un IBAN valide  ");
			}
		}
		return iban;
	}

	public static boolean formatIban(String iban) {
		return Pattern.matches("[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}", iban);
	}

	public static String verifMobile() {
		String mobile = null;
		boolean estValide = false;
		System.out.println("Veuillez rentrer votre numéro de mobile \n numéro de mobile : ");
		while (!estValide) {
			mobile = sc.nextLine();
			if (formatMobile(mobile)) {
				estValide = true;
			} else {
				System.out.println("Veuillez rentrer un numéro mobile valide  ");
			}
		}
		return mobile;
	}

	public static boolean formatMobile(String mobile) {
		return Pattern.matches("^(06|07)\\d{8} ?", mobile);
	}
}
