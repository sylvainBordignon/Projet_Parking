package verificationsentreeclavier;

import java.util.Scanner;

public class MethodesFormatClavierInterface {

	public static int entreeEntier(String message) {
		Scanner sc = new Scanner(System.in);
		int res = 0;
		String entree;
		boolean fin = false;
		while(!fin) {
			System.out.println(message);
			entree = sc.nextLine();
			if(entree.matches("\\d+")) {
				res = Integer.parseInt(entree);
				fin = true;	
			}else {
				System.out.println("Erreur dans le format, veuillez entrer un entier.");
			}
		}		
		return res;
	}
	
	public static String entreePlaqueImmatriculation(String message) {
		Scanner sc = new Scanner(System.in);
		String entree = "";
		boolean fin = false;
		while(!fin) {
			System.out.println(message);
			entree = sc.nextLine();
			if(entree.matches("^([A-Z]){2}-([0-9]){3}-([A-Z]){2}")) {
				fin = true;	
			}else {
				System.out.println("Erreur dans le format, veuillez entrer un numéro d'immatriculation valide (Ex: AA-000-AA).");
			}
		}		
		return entree;
	}
	
	public static String entreeDate(String message) {
		Scanner sc = new Scanner(System.in);
		String entree = "";
		boolean fin = false;
		while(!fin) {
			System.out.println(message);
			entree = sc.nextLine();
			if(MethodesVerificationsDate.estValideDate(entree)) {
				fin = true;	
			}else {
				System.out.println("Erreur dans le format, veuillez entrer une date valide (Ex: 01/01/2020).");
			}
		}		
		return entree;
	}
	
	public static String entreeHeure(String message) {
		Scanner sc = new Scanner(System.in);
		String entree = "";
		boolean fin = false;
		while(!fin) {
			System.out.println(message);
			entree = sc.nextLine();
			if(MethodesVerificationsDate.estValideHeureMinute(entree)) {
				fin = true;	
			}else {
				System.out.println("Erreur dans le format, veuillez entrer une heure valide (Ex: 05:15).");
			}
		}		
		return entree;
	}
	
	public static String entreeHeureMemeJour(String message) {
		Scanner sc = new Scanner(System.in);
		String entree = "";
		boolean fin = false;
		while(!fin) {
			System.out.println(message);
			entree = sc.nextLine();
			if(MethodesVerificationsDate.estValideHeureMinuteMemeJour(entree)) {
				fin = true;	
			}else {
				System.out.println("Erreur dans le format, veuillez entrer une heure valide et une heure supérieur à l'heure actuelle.(Ex: 05:15).");
			}
		}		
		return entree;
	}
	
	public static Boolean validerUneReservation(String message) {
		Scanner sc = new Scanner(System.in);
		String entree = "";
		Boolean reponse = false;
		System.out.println("Voulez-vous créer une réservation ? \n ");
		boolean fin = false;
		while(!fin) {
			System.out.println(message);
			entree = sc.nextLine();
			if( entree.compareTo("o") == 0) {
				reponse=true;
				fin = true;	
			}else if(entree.compareTo("n") == 0) {
				reponse=false;
				fin = true;
			}else {
				System.out.println("Erreur, veuillez choisir une réponse correcte.");
			}
		}
		return reponse;
	}
	
	
	
}
