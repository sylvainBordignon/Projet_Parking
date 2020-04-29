import java.util.Scanner;

import verificationsentreeclavier.MethodesVerificationsDate;

public class InterfaceClient {

	/**
	 * Changer les sc.nextLine en SC.nextLine pour gérer simplement et pas avoir
	 * erreur si caract�re. g�r�r les v�hicules
	 */
	private static String messageErreur = "Erreur dans la saisie, entrez un num�ro correspondant à un choix du menu.",
			messageRetourAccueil = "Retour à l'accueil...",
			messageFermetureAppli = "Fermeture de l'application mobile...",
			messageQueFaire = "Que souhaitez-vous faire ?",
			messageChoixDate = "Veuillez choisir une date (Format : JJ/MM/AAAA)",
			messageChoixHeure = "Veuillez choisir une heure de début (Format : HH:MM)",
			messageChoixDuree = "Veuillez choisir une dur�e de réservation (en minutes de stationnement)";

	public static void interfaceClient() {
		boolean fin = false;
		System.out.println("Bonjour, bienvenue sur l'application mobile.");
		while (!fin) {
			Scanner sc = new Scanner(System.in);
			System.out.println(messageQueFaire);
			System.out.println("1 - Consulter mon profil");
			System.out.println("2 - Gérer mes réservations");
			System.out.println("3 - Gérer mes réservations permanentes");
			System.out.println("3 - Gérer mes véhicules");
			System.out.println("4 - Consulter la disponibilité des places du parking");
			System.out.println("5 - Consulter mes factures");
			System.out.println("6 - Quitter l'application");
			System.out.print("Choix : ");
			String choixMenu = sc.nextLine();
			switch (choixMenu) {
			case "1":
				boolean finProfil = false;
				while (!finProfil) {
					System.out.println("Affichage des informations du profil... A RAJOUTER");
					System.out.println(messageQueFaire
							+ "\n1 - Modifier mon profil\n2 - Retourner à l'accueil de l'application\n3 - Quitter l'application");
					System.out.print("Choix : ");
					String choixProfil = sc.nextLine();
					switch (choixProfil) {
					case "1":
						System.out.println("Modification des informations du profil... A RAJOUTER");
						// ...
						System.out.println("Validation des modifications effectuées.");
						break;
					case "2":
						finProfil = true;
						System.out.println(messageRetourAccueil);
						break;
					case "3":
						finProfil = true;
						fin = true;
						System.out.println(messageFermetureAppli);
						break;
					default:
						System.out.println(messageErreur);
					}
				}
				break;
			case "2":
				boolean finReservation = false;
				boolean reservationEnCours = false;
				boolean reservationAVenir = false;
				while (!finReservation) {
					System.out.println("Liste des réservations : ");
					// reservation en cours et � venir... repris et changement des booleans
					if (reservationEnCours) {
						// affiche reservation en cours + choix dans menu
					}
					if (reservationAVenir) {
						// affiche reservations + choix modif menu
					}
					if (!reservationEnCours && !reservationAVenir) {
						System.out.println("Aucune réservation. Retour à l'accueil de l'application...");
						finReservation = true;
					}
					System.out.println(messageQueFaire);
					if (reservationEnCours) {
						System.out.println("1 - Gérer la réservation en cours");
					}
					if (reservationAVenir) {
						System.out.println("2 - Modifier une réservation à venir");// +ajout
					}
					System.out.println("3 - Ajouter une nouvelle réservation");
					System.out.println("4 - Retourner à l'accueil de l'application");
					System.out.println("5 - Quitter l'application");
					String choixReservation = sc.nextLine();
					switch (choixReservation) {
					case "1":
						if (reservationEnCours) {
							System.out.println(messageQueFaire
									+ "\n1 - Prolonger la fin de la réservation de 30 minutes\n2 - Prolonger le délai d'attente");
							String choixReservEnCours = sc.nextLine();
							switch (choixReservEnCours) {
							case "1":
								// si possible et dans 30 derni�res minutes
								System.out.println("Prolongation de la fin de la réservation");
								// sinon impossible
								break;
							case "2":
								// prolonger d�lai attente
								break;
							default:
								System.out.println(messageErreur);
							}
						} else {
							System.out.println(messageErreur);
						}
						break;
					case "2":
						if (reservationAVenir) {
							System.out.println("Quelle réservation souhaitez-vous modifier ?");
							String choixModifReservation = sc.nextLine();
							// verif
							// puis interface modif
						} else {
							System.out.println(messageErreur);
						}
						break;
					case "3":
						entrerDateReservation();
						//+save dans la BDD
						break;
					case "4":
						finReservation = true;
						System.out.println(messageRetourAccueil);
						break;
					case "5":
						finReservation = true;
						fin = true;
						System.out.println(messageFermetureAppli);
						break;
					default:
						System.out.println(messageErreur);
					}

				}
				break;
			case "3":
				boolean finPermanent = false;
				while (!finPermanent) {
					// si aucunes r�servations
					System.out.println("Vous n'avez pas de réservations permanentes.");
					// si reservations
					System.out.println("Liste de vos réservations permanentes : ");

					System.out.println(messageQueFaire + "\n1 - Ajouter une réservation permanente");
					// si reservation existante :
					System.out.println("2 - Supprimer une réservation permanente");

					System.out.println("3 - Retour à l'accuei?\n4 - Quitter l'application");
					String choixPermanent = sc.nextLine();
					switch (choixPermanent) {
					case "1":
						// si r�servation < 3
						// faire fonction car x2
						entrerDateReservation();
						// Ajout dans la BDD

						// si deja 3 r�servations, proposer une fusion ou suppression
						System.out.println("Vous possédez déjà 3 réservations.\n" + messageQueFaire
								+ "\n1 - Fusionner deux réservations\n2 - Supprimer une réservation\n3 - Revenir au menu précédant\n4 - Retour à l'accueil\n5 - Quitter l'application");
						String choixReservPlein = sc.nextLine();
						switch (choixReservPlein) {
						case "1":
							System.out.println("Entrer les num�ros des deux réservations : ");
							// ...prendre date d�but 1 jusqua date fin 2
							// modif BDD
							break;
						case "2":
							System.out.println("Veuillez entrez le numéro correspondant à la réservation permanente :");
							String supprPlaque = sc.nextLine();
							// Suppression dans la BDD
							break;
						case "3":
							break;
						case "4":
							finPermanent = true;
							System.out.println(messageRetourAccueil);
							break;
						case "5":
							finPermanent = true;
							fin = true;
							System.out.println(messageFermetureAppli);
							break;
						default:
							System.out.println(messageErreur);
						}
						break;
					case "2":
						System.out.println("Veuillez entrez le numéro correspondant à la réservation permanente :");
						String supprPlaque = sc.nextLine();
						// Suppression dans la BDD
						break;
					case "3":
						finPermanent = true;
						System.out.println(messageRetourAccueil);
						break;
					case "4":
						finPermanent = true;
						fin = true;
						System.out.println(messageFermetureAppli);
						break;
					default:
						System.out.println(messageErreur);
					}
				}
				break;
			case "4":
				boolean finVehicule = false;
				while (!finVehicule) {
					System.out.println("Liste de vos véhicules : ");
					// lister plaques des v�hicules
					System.out.println(messageQueFaire
							+ "\n1 - Ajouter un véhicule\n2 - Supprimer un véhicule\n3 - Retour à l'accuei?\n4 - Quitter l'application");
					String choixVehicule = sc.nextLine();
					switch (choixVehicule) {
					case "1":
						String plaqueVehicule = sc.nextLine();
						// Ajout dans la BDD
						break;
					case "2":
						System.out.println("Veuillez entrez le numéro correspondant à la plaque d'immatriculation :");
						String supprPlaque = sc.nextLine();
						// Suppression dans la BDD
						break;
					case "3":
						finVehicule = true;
						System.out.println(messageRetourAccueil);
						break;
					case "4":
						finVehicule = true;
						fin = true;
						System.out.println(messageFermetureAppli);
						break;
					default:
						System.out.println(messageErreur);
					}
				}
				break;
			case "5":
				System.out.println("Consultation des disponiblités des places de parking");
				entrerDateReservation();
				//Puis check dans la BDD

				// si tout est bon
				System.out.println("Affichage du nombre de place dispo");
				// si > 0
				System.out.println(
						"Souhaitez-vous réserver une place à cette date et pendant cette durée ?\n1 - Oui\n2 - Non");
				// si oui on fait la r�servation + retour accueil
				// si non on demande si nouvelle recherche ou retour accueil
				break;
			case "6":
				boolean finFacture = false;
				while (!finFacture) {
					System.out.println(
							"Quelles factures voulez-vous consulter ?\n1 - Les factures du mois-ci\n2 - Toutes les factures\n3 - Retour à l'accueil\n4 - Quitter l'application");
					String choixFacture = sc.nextLine();
					switch (choixFacture) {
					case "1":
						System.out.println("Affichage des factures du mois");
						break;
					case "2":
						System.out.println("Affichage de toutes les factures du client");
						break;
					case "3":
						finFacture = true;
						System.out.println(messageRetourAccueil);
						break;
					case "4":
						finFacture = true;
						fin = true;
						System.out.println(messageFermetureAppli);
						break;
					default:
						System.out.println(messageErreur);
					}
				}
				break;
			case "7":
				fin = true;
				break;
			default:
				System.out.println(messageErreur);

			}
		}
	}
	
	
	public static void entrerDateReservation() {
		MethodesVerificationsDate verifDate = new MethodesVerificationsDate();
		Scanner sc = new Scanner(System.in);
		boolean valide = false;
		String dateReserv, heureReserv, dureeReserv;
		while (!valide) {
			System.out.println(messageChoixDate);
			dateReserv = sc.nextLine();
			valide = verifDate.estValideDate(dateReserv);
			if (!valide) {
				System.out.println("Erreur dans le format, veuillez reéssayer.");
			}
		}
		valide = false;
		while (!valide) {
			System.out.println(messageChoixHeure);
			heureReserv = sc.nextLine();
			valide = verifDate.estValideDate(heureReserv);
			if (!valide) {
				System.out.println("Erreur dans le format, veuillez reéssayer.");
			}
		}
		System.out.println(messageChoixDuree);
		valide = false;
		while (!valide) {
			dureeReserv = sc.nextLine();
			valide = verifDate.estValideDate(dureeReserv);
			if (!valide) {
				System.out.println("Erreur dans le format, veuillez reéssayer.");
			}
		}
	}

	public static void main(String[] args) {
		InterfaceClient.interfaceClient();
	}
}
