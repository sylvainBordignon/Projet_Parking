package interfaces;
import java.util.Scanner;

public class InterfaceGenerale {

	public static void interfaceGenerale() {
		boolean fermetureInterface = false;
		Scanner sc = new Scanner(System.in);
		while(!fermetureInterface) {
			System.out.println("Menu d'accueil de l'API générale");
			System.out.println("Que souhaitez-vous faire ?\n1 - Interface de la borne du parking\n2 - Interface client\n3 - Interface gérant\n4 - Fermer l'API.");
			String choix = sc.nextLine();
			switch(choix) {
			case "1":
				InterfaceBorne.lancerInterfaceBorne();
				break;
			case "2":
				InterfaceClient.interfaceClient();
				break;
			case "3":
				InterfaceGerant.lancerInterfaceGerant();
				break;
			case "4":
				fermetureInterface = true;
				System.out.println("Fermeture de l'API.");
				break;
			default:
				System.out.println("Erreur, veuillez choisir un numéro correpondant à un des choix");
			}
		}
	}
	
	public static void main(String[] args) {
		interfaceGenerale();
	}
}
