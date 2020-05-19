package interfaces;
import java.util.Scanner;

public class InterfaceGenerale {

	public static void interfaceGenerale() {
		boolean fermetureInterface = false;
		Scanner sc = new Scanner(System.in);
		while(!fermetureInterface) {
			System.out.println("Menu d'accueil de l'API générale");
			System.out.println("Que souhaitez-vous faire ?\n1 - Interface borne entrée du parking\n2 - Interface borne sortie du parking\n3 - Interface client\n4 - Interface gérant"
					+ "\n5 - Fermer l'API."
					);
			String choix = sc.nextLine();
			switch(choix) {
			case "1":
				InterfaceBorneEntree.lancerInterfaceBorne();
				break;
			case "2":
				InterfaceBorneSortie.lancerInterfaceBorne();
				break;
			case "3":
				InterfaceClient.accesInterfaceClient();
				break;
			case "4":
				InterfaceGerant.lancerInterfaceGerant();
				break;
			case "5":
				fermetureInterface = true;
				System.out.println("Fermeture de l'API.");
				break;
			default:
				System.out.println("Erreur, veuillez choisir un numéro correpondant à un des choix.");
			}
		}
	}
}
