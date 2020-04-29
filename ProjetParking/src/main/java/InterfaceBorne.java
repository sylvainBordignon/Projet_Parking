import java.util.Scanner;

public class InterfaceBorne {
	public static final String messageErreur="Veuillez entrer une information correcte.";
	public static final String messageAcceptation="Vous occupez la place ... pour une durée de ... Merci de votre visite.";
	public static final String messageDedommagement="Il n'y a pas de places disponibles. Vous recevrez un dédommagement. Veuillez nous excuser.";
	public static final String messageSansDedommagement="Il n'y a pas de places disponibles. Veuillez nous excuser.";
	public static int placesLibres = 0;
	
	public static void lancerInterfaceBorne() {
		System.out.println("Avez vous une réservation?" + "\n" + "1 - Oui" + "\n" + "2 - Non");
		Scanner in = new Scanner(System.in);
		String reponse = in.nextLine();
		while(!reponse.equals("1") && !reponse.equals("2")) { 
			System.out.println(messageErreur);
			in = new Scanner(System.in);
			reponse = in.nextLine();
		}
		if (reponse.equals("1")) {
			if(placesLibres==0)
				System.out.println(messageSansDedommagement);
			else
				InterfaceBorne.menuReservation();
		}
		else if (reponse.equals("2")) {
			InterfaceBorne.menuSansReservation();
		}
	}//v�rifier qu'il y a des places

	private static void menuReservation() {
		System.out.println("Veuillez entrer votre numéro de réservation.");
		Scanner in = new Scanner(System.in);
		String numReservation = in.nextLine();
		while(numReservation.equalsIgnoreCase("incorrect")) {//on v�rifie que le format est correct
			System.out.println(messageErreur);
			in = new Scanner(System.in);
			numReservation = in.nextLine();
		}
		if (numReservation.equalsIgnoreCase("existe")) {//v�rification que le num�ro reservation existe dans la base
			if(placesLibres==0) 
				System.out.println(messageDedommagement);
			else
				System.out.println(messageAcceptation);//on enregistre la pr�sence du client dans la base
		}
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
		System.out.println("Veuillez entrer votre plaque d'immatriculation.");
		Scanner in = new Scanner(System.in);
		String immatriculation = in.nextLine();
		while(immatriculation.equalsIgnoreCase("incorrect")) {//on v�rifie que le format est correct
			System.out.println(messageErreur);
			in = new Scanner(System.in);
			immatriculation = in.nextLine();
		}
		if (!immatriculation.equalsIgnoreCase("incorrect")) {//v�rification que l'immatriculation existe dans la base
			if(immatriculation.equalsIgnoreCase("existe")) {//v�rification qu'une reservation existe � cette date
				if(placesLibres==0)
					System.out.println(messageDedommagement);
				else
					System.out.println(messageAcceptation);//on enregistre la pr�sence du client dans la base
			}
			else {
				if(immatriculation.equalsIgnoreCase("existePas")) {
					System.out.println("Vous n'avez pas de réservation à cette date.");
					if(placesLibres==0)
						System.out.println(messageSansDedommagement);
					else
						InterfaceBorne.dureeSejour();
				}
				else {
				System.out.println("Numéro d'immatriculation inconnu. Voulez vous reessayer?" + "\n" + "1 - Oui" + "\n" + "2 - Non");
				String reponse = InterfaceBorne.reessayer();
				if (reponse.equals("1"))
					InterfaceBorne.menuSansReservation();
				else if (reponse.equals("2"))
					InterfaceBorne.verifNumClient();
				}
			}
		}
	}
	
	private static void verifNumClient() {
		System.out.println("Veuillez entrer votre numéro de client.");
		Scanner in = new Scanner(System.in);
		String numClient = in.nextLine();
		while(!numClient.equals("1") && !numClient.equals("2")) {//on v�rifie que le format est correct
			System.out.println(messageErreur);
			in = new Scanner(System.in);
			numClient = in.nextLine();
		}
		if (numClient.equals("1")) {//v�rification que le num�ro client existe dans la base
			InterfaceBorne.dureeSejour();
		}
		else if (numClient.equals("2")){//le num�ro n'existe pas dans la base
			System.out.println("Numéro de client inconnu. Voulez vous reessayer?" + "\n" + "1 - Oui" + "\n" + "2 - Non");
			String reponse = InterfaceBorne.reessayer();
			if (reponse.equals("1"))
				InterfaceBorne.verifNumClient();
			else if (reponse.equals("2"))
				System.out.println("Veuillez vous diriger vers la sortie. Merci pour votre visite.");
		}		
	}
	
	private static void dureeSejour() {
		System.out.println("Combien de temps souhaitez vous rester? (en minutes)");
		Scanner in = new Scanner(System.in);
		int duree = in.nextInt();
		while(duree<0 || duree>100) {//on v�rifie que le format est correct
			System.out.println(messageErreur);
			in = new Scanner(System.in);
			duree = in.nextInt();
		}
		//si la duree est correcte
		System.out.println(messageAcceptation);//on enregistre la pr�sence du client dans la base
	}
	
	public static void main(String[] args) {
		  InterfaceBorne.lancerInterfaceBorne();
	}
	
	public static String reessayer() {
		Scanner in = new Scanner(System.in);
		String reponse = in.nextLine();
		while(!reponse.equals("1") && !reponse.equals("2")) { 
			System.out.println(messageErreur);
			in = new Scanner(System.in);
			reponse = in.nextLine();
		}
		return reponse;
	}

}
