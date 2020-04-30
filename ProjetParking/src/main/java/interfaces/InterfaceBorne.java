package interfaces;

import java.util.Scanner;

public class InterfaceBorne {
	public static final String MESSAGE_ERREUR="Veuillez entrer une information correcte.";
	public static final String MESSAGE_ACCEPTATION="Vous occupez la place ... pour une durée de ... Merci de votre visite.";
	public static final String MESSAGE_DEDOMMAGEMENT="Il n'y a pas de places disponibles. Vous recevrez un dédommagement. Veuillez nous excuser.";
	public static final String MESSAGE_SANS_DEDOMMAGEMENT="Il n'y a pas de places disponibles. Veuillez nous excuser.";
	public static final int PLACES_LIBRES = 3;
	public static final String RETOUR_MENU_PRINCIPAL = "\nTapez '-1' pour retourner au menu principal";
	
	public static void lancerInterfaceBorne() {
		System.out.println("Bienvenue sur l'interface du parking. \nAvez vous une réservation?" + "\n" + "1 - Oui" + "\n" + "2 - Non" + RETOUR_MENU_PRINCIPAL);
		String reponse = InterfaceBorne.reessayer();
		if (reponse.equals("1")) {
			if(PLACES_LIBRES==0)
				System.out.println(MESSAGE_DEDOMMAGEMENT);
			else
				InterfaceBorne.menuReservation();
		}
		else if (reponse.equals("2")) {
			InterfaceBorne.menuSansReservation();
		}
		else if (reponse.equals("-1")) {//retour menu principal
			InterfaceGenerale.interfaceGenerale();
		}
	}//v�rifier qu'il y a des places

	private static void menuReservation() {
		System.out.println("Veuillez entrer votre numéro de réservation." + RETOUR_MENU_PRINCIPAL);
		Scanner in = new Scanner(System.in);
		String numReservation = in.nextLine();
		while(numReservation.equalsIgnoreCase("incorrect")) {//on v�rifie que le format est correct
			System.out.println(MESSAGE_ERREUR);
			in = new Scanner(System.in);
			numReservation = in.nextLine();
		}
		if (numReservation.equalsIgnoreCase("existe")) {//v�rification que le num�ro reservation existe dans la base
			if(PLACES_LIBRES==0) 
				System.out.println(MESSAGE_DEDOMMAGEMENT);
			else
				System.out.println(MESSAGE_ACCEPTATION);//on enregistre la pr�sence du client dans la base
		}
		else if (numReservation.equals("-1"))//retour menu principal
			InterfaceGenerale.interfaceGenerale();
		else {//le num�ro n'existe pas dans la base
			System.out.println("Numéro de reservation inconnu. Voulez vous reessayer?" + "\n" + "1 - Oui" + "\n" + "2 - Non");
			String reponse = InterfaceBorne.reessayer();
			if (reponse.equals("1"))
				InterfaceBorne.menuReservation();
			else if (reponse.equals("2"))
				InterfaceBorne.menuSansReservation();
		}		
		
	}
	
	private static void menuSansReservation() {
		System.out.println("Veuillez entrer votre plaque d'immatriculation." + RETOUR_MENU_PRINCIPAL);
		Scanner in = new Scanner(System.in);
		String immatriculation = in.nextLine();
		while(immatriculation.equalsIgnoreCase("incorrect")) {//on v�rifie que le format est correct
			System.out.println(MESSAGE_ERREUR);
			in = new Scanner(System.in);
			immatriculation = in.nextLine();
		}
		if (immatriculation.equalsIgnoreCase("plaqueInconnue")) {//la plaque n'existe pas dans la base
			System.out.println("Numéro d'immatriculation inconnu. Voulez vous reessayer?" + "\n" + "1 - Oui" + "\n" + "2 - Non");
			String reponse = InterfaceBorne.reessayer();
			if (reponse.equals("1"))
				InterfaceBorne.menuSansReservation();
			else if (reponse.equals("2"))
				InterfaceBorne.verifNumClient();
		}
		else {//la plaque existe, on v�rifie qu'une reservation existe
			InterfaceBorne.verifReservationExiste(immatriculation);
		}
	}
	
	private static void verifNumClient() {
		System.out.println("Veuillez entrer votre numéro de client." + RETOUR_MENU_PRINCIPAL);
		Scanner in = new Scanner(System.in);
		String numClient = in.nextLine();
		while(numClient.equalsIgnoreCase("incorrect")) {//on v�rifie que le format est correct
			System.out.println(MESSAGE_ERREUR);
			in = new Scanner(System.in);
			numClient = in.nextLine();
		}
		if (numClient.equalsIgnoreCase("numeroIconnu")){//le num�ro n'existe pas dans la base
			System.out.println("Numéro de client inconnu. Voulez vous reessayer?" + "\n" + "1 - Oui" + "\n" + "2 - Non");
			String reponse = InterfaceBorne.reessayer();
			if (reponse.equals("1"))
				InterfaceBorne.verifNumClient();
			else if (reponse.equals("2"))//numéro de client non reconnu
				System.out.println("Veuillez vous diriger vers la sortie. Merci pour votre visite.");
		}
		else {//le numero existe dans la base, on vérifie qu'une reservation existe
			InterfaceBorne.verifReservationExiste(numClient);
		}
	}
	
	private static void verifReservationExiste(String immatriculationOuClient) {
		if(immatriculationOuClient.equalsIgnoreCase("existe")) {//v�rification qu'une reservation existe � cette date
			if(PLACES_LIBRES==0)//s'il n'y a plus de place
				System.out.println(MESSAGE_DEDOMMAGEMENT);
			else
				System.out.println(MESSAGE_ACCEPTATION);//on enregistre la pr�sence du client dans la base
		}
		else if(immatriculationOuClient.equalsIgnoreCase("existePas")) {
				System.out.println("Vous n'avez pas de réservation à cette date.");
				if(PLACES_LIBRES==0)
					System.out.println(MESSAGE_SANS_DEDOMMAGEMENT);
				else
					InterfaceBorne.dureeSejour();
			}
		else if (immatriculationOuClient.equals("-1"))//retour au menu principal
			InterfaceGenerale.interfaceGenerale();
	}
	
	private static void dureeSejour() {
		System.out.println("Combien de temps souhaitez vous rester? (en minutes)" + RETOUR_MENU_PRINCIPAL);
		Scanner in = new Scanner(System.in);
		String duree = in.nextLine();
		while(duree.equals("incorrect")) {//on v�rifie que le format est correct
			System.out.println(MESSAGE_ERREUR);
			in = new Scanner(System.in);
			duree = in.nextLine();
		}
		if(duree.equals("-1"))//retour au menu principal
			InterfaceGenerale.interfaceGenerale();
		else//si la duree est correcte
			System.out.println(MESSAGE_ACCEPTATION);//on enregistre la pr�sence du client dans la base
	}
	
	public static String reessayer() {
		Scanner in = new Scanner(System.in);
		String reponse = in.nextLine();
		while(!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("-1")) { 
			System.out.println(MESSAGE_ERREUR);
			in = new Scanner(System.in);
			reponse = in.nextLine();
		}
		return reponse;
	}
	
	public static void main(String[] args) {
		  InterfaceBorne.lancerInterfaceBorne();
	}

}
