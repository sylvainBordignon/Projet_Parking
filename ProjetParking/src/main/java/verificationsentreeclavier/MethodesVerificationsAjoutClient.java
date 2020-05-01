package verificationsentreeclavier;

import java.util.Scanner;
import java.util.regex.Pattern;

public class MethodesVerificationsAjoutClient {

	// doit comporter uniquement des lettres et une taille < à 30
	public String verifNomClient() {
		String nom=null;
		boolean estValide=false;
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez rentrer votre nom\n nom :  ");
		

		while(estValide == false) {
			nom = sc.nextLine();
			
			if (Pattern.matches("[A-zÀ-ú- ]+", nom) && (nom.length() > 1 && nom.length() <= 30)) {
				estValide = true;
			} else {
				System.out.println(
						"Veuillez rentrer un nom correct ( uniquement des caractères et ayant une taille inférieure à 30) \n nom :  ");
			}
		}
		
		return nom;
	}

	// doit comporter uniquement des lettres et une taille < à 30
	public String verifPrenomClient() {
		String prenom= null;
		boolean estValide = false;
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez rentrer votre prenom \n prenom :  ");

		while (estValide == false) {
			prenom = sc.nextLine();

			if (Pattern.matches("[A-zÀ-ú-]+", prenom) && (prenom.length() > 1 && prenom.length() <= 30)) {
				estValide = true;
			} else {
				System.out.println(
						"Veuillez rentrer un prenom correct ( uniquement des caractères et ayant une taille inférieure à 30) \n prenom :  ");
			}
		}
		return prenom;
	}
	

	// une adresse peut contenir n'importe quel type de caractères sauf + /
	public String verifAdresse() {
		String adresse= null;
		boolean estValide = false;
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez rentrer votre adresse \n adresse :  ");
		
		
		while (estValide == false) {
			adresse = sc.nextLine();
			
			if (adresse.length() < 50 && Pattern.matches("[A-zÀ-ú0-9'_ ]*$", adresse)) {
				estValide = true;
			}else {
				System.out.println("Veuillez rentrer une adresse correcte ( max 50 caractères, les caractères spéciaux sont interdits) ");	
			}
		}
		return adresse;
	}
	
	
	// xxxx@xx.xxx
	public String verifMail() {
		String mail=null;
		Scanner sc = new Scanner(System.in);
		boolean estValide = false;
		System.out.println("Veuillez rentrer votre mail \n mail : ");
	
		while(estValide=false) {
			
		mail = sc.nextLine();		
		if (Pattern.matches("^[a-zA-Z0-9_^.-]+@[a-zA-Z0-9.-]+$", mail)) {
			estValide = true;
		}else {	
		System.out.println("Veuillez rentrer une adresse mail valide ");	
		}
	
	}
		
		return mail;	

}
	
}
