package interfaces;

import java.util.Scanner;

import mysql.GerantMysql;
import pojo.Client;
import verificationsentreeclavier.MethodesVerificationsDate;

public class InterfaceGerant {

	public static void lancerInterfaceGerant() {
		MethodesVerificationsDate methodesverificationdate = new MethodesVerificationsDate();
		Scanner sc = new Scanner(System.in);
		int choix = 0;
		do {

			System.out.println("Bienvenue sur l'interface du gérant. \n\n");

			System.out.println("Veuillez sélectionner une option parmis les choix suivants : \n"
					+ "1 - Consulter le profil d'un client \n" + "2 - Modifier les tarifs des services"
					+ " (frais de stationnement pendant période réservé, après dépassement et non-présentation …) \n"
					+ "3 - Modifier le nombre de place en surréservation. \n"
					+ "4 - Visualiser les réservations en cours. \n" + "5 - Visualiser les transactions des clients  \n"
					+ "6 - Visualiser les statistiques du parking \n" + "7 - Revenir à l'accueil des api  \n");
			while (!sc.hasNextInt()) {
				System.out.println("Veuillez rentrer un nombre ! ");
				sc.next();
			}
			choix = sc.nextInt();
			switch (choix) {
			case 1:
				int numeroClient;
				do {
					System.out.println(
							"Veuillez saisir le numéro du client ... (Entrez -1 pour revenir au menu principal) ");
					while (!sc.hasNextInt()) {
						System.out.println("Veuillez rentrer un nombre ! ");
						sc.next();
					}
					numeroClient = sc.nextInt();
					System.out.println("Information du client :");
					Client c = GerantMysql.getInstance().visualierInfoClient(numeroClient);
					if (c == null) {
						System.out.println("Ce numéro de client n'est associé à aucun client.");
					} else {
						System.out.println(c);
					}
					numeroClient = -1;
				} while (numeroClient != -1);
				break;
			case 2:
				int choixModifTarif;
				do {
					System.out.println("Quel tarif voulez-vous modifier ? (Entrez -1 pour revenir au menu principal) \n"

							+ "1 - Frais de stationnement pendant période réservée \n"
							+ "2 - Frais de stationnement de dépassement de période " + "\n"
							+ "3 - Frais de prolongation de la période d'attente  \n");
					while (!sc.hasNextInt()) {
						System.out.println("Veuillez rentrer un nombre ! ");
						sc.next();
					}
					choixModifTarif = sc.nextInt();
					switch (choixModifTarif) {
					case 1:
						float valeurFraisStationementPendantReservation;
						do {
							float tarifNormal = GerantMysql.getInstance().selectionnerTarifNormal();
							System.out.println("Saisissez -1 pour revenir en arrière \n"
									+ "Valeur actuelle des frais de stationnement :" + tarifNormal + "\n"
									+ "Veuillez saisir la nouvelle valeur ...");

							while (!sc.hasNextFloat()) {
								System.out.println("Veuillez rentrer un nombre ! ");
								sc.next();
							}
							valeurFraisStationementPendantReservation = sc.nextFloat();
							GerantMysql.getInstance().modifierTarifNormal(valeurFraisStationementPendantReservation);
							System.out.println("Nouveau tarif : " + valeurFraisStationementPendantReservation);
							valeurFraisStationementPendantReservation = -1;
						} while (valeurFraisStationementPendantReservation != -1);
						break;
					case 2:
						float valeurFraisStationementDepassementPeriode;
						do {
							float tarifDepassement = GerantMysql.getInstance().selectionnerTarifDepassement();
							System.out.println("Saisissez -1 pour revenir en arrière \n"
									+ "Valeur actuelle des frais de dépassement de stationnement : " + tarifDepassement
									+ " \n" + "Veuillez saisir la nouvelle valeur ...");
							while (!sc.hasNextFloat()) {
								System.out.println("Veuillez rentrer un nombre ! ");
								sc.next();
							}
							valeurFraisStationementDepassementPeriode = sc.nextFloat();
							GerantMysql.getInstance()
									.modifierTarifDepassement(valeurFraisStationementDepassementPeriode);
							System.out.println("Nouveau tarif : " + valeurFraisStationementDepassementPeriode);
							valeurFraisStationementDepassementPeriode = -1;
						} while (valeurFraisStationementDepassementPeriode != -1);
						break;
					case 3:
						float valeurProlongationAttente;
						do {
							float tarifProlongation = GerantMysql.getInstance().selectionnerTarifProlongationAttente();
							System.out.println("Saisissez -1 pour revenir en arrière \n"
									+ "Valeur actuelle de la prolongation d'attente : " + tarifProlongation + " \n"
									+ "Veuillez saisir la nouvelle valeur ...");
							while (!sc.hasNextFloat()) {
								System.out.println("Veuillez rentrer un nombre ! ");
								sc.next();
							}
							valeurProlongationAttente = sc.nextFloat();
							GerantMysql.getInstance().modifierTarifProlongationAttente(valeurProlongationAttente);
							System.out.println("Nouveau tarif : " + valeurProlongationAttente);
							valeurProlongationAttente = -1;
						} while (valeurProlongationAttente != -1);
						break;
					default:
						System.out.println("Veuillez rentrer un nombre entre 1 et 3 ");
						break;
					}

				} while (choixModifTarif != -1);
				break;
			case 3:
				int nbPlaceSurreservation = (int) GerantMysql.getInstance().selectionnerNbPlaceSurreservation();
				do {

					System.out.println(
							"Veuillez saisir le nouveau nombre de place en surréservation (Saisissez -1 pour revenir en arrière) "
									+ "Nombre actuel : " + nbPlaceSurreservation);
					while (!sc.hasNextInt()) {
						System.out.println("Veuillez rentrer un nombre ! ");
						sc.next();
					}
					nbPlaceSurreservation = sc.nextInt();
					GerantMysql.getInstance().modifierNbPlaceSurreservation(nbPlaceSurreservation);
					System.out.println("Nouvelle valeur : " + nbPlaceSurreservation);
					nbPlaceSurreservation = -1;
				} while (nbPlaceSurreservation != -1);
				break;
			case 4:
				int choixReservationEnCours;
				do {
					System.out.println("Saisissez -1 pour revenir en arrière \n"
							+ "Actuellement, il y a XX places de prises sur XX places. \n"

							+ "1 - Pour chaque place réservé, afficher le client et le créneau  \n"
							+ "2 - Rafrichir la page " + "\n");

					while (!sc.hasNextInt()) {
						System.out.println("Veuillez rentrer un nombre ! ");
						sc.next();
					}
					choixModifTarif = sc.nextInt();

					switch (choixModifTarif) {

					case 1:
						int retourMenuListeReservations;
						do {
							System.out.println("Saisissez -1 pour revenir en arrière \n"
									+ "Voici la liste des places occupées ainsi que leurs informations");

							while (!sc.hasNextInt()) {
								System.out.println("Veuillez rentrer un nombre ! ");
								sc.next();
							}
							retourMenuListeReservations = sc.nextInt();
						} while (retourMenuListeReservations != -1);
						break;

					case 2:
						int retourMenuReservationRafraichissement;
						do {
							System.out.println("Rafraichissement en cours ... \n");
							// while ici
							retourMenuReservationRafraichissement = -1;
						} while (retourMenuReservationRafraichissement != -1);
						break;
					default:
						System.out.println("Veuillez rentrer un nombre entre 1 et 2 ");
						break;
					}
				} while (choixModifTarif != -1);
				break;
			case 5:
				int choixTransaction;
				do {
					System.out.println("Saisissez -1 pour revenir en arrière \n"
							+ "1 - Visualiser les transactions pour une période donnée. \n"
							+ "2 - Visualiser les transactions pour un client spécifique.  \n");
					while (!sc.hasNextInt()) {
						System.out.println("Veuillez rentrer un nombre ! ");
						sc.next();
					}
					choixTransaction = sc.nextInt();
					switch (choixTransaction) {
					case 1:
						int retourMenuTransaction;
						do {
							System.out.println(
									"Saisissez -1 pour revenir en arrière \n" + "Veuillez rentrer la période ...\n"
											+ "Commencez par rentrer la date de début au format suivant : JJ/MM/AAAA\n"
											+ "date de début : "

							);
							boolean saisieDateDebutEstValide, saisieDateFinEstValide = false;
							String dateDebut, dateFin;

							dateDebut = sc.nextLine();

							if (dateDebut.equals("-1")) {

								retourMenuTransaction = -1;
							} else {

								saisieDateDebutEstValide = methodesverificationdate.estValideDate(dateDebut);

								if (saisieDateDebutEstValide == true) {
									System.out.println(
											"Veuillez rentrer la date de fin au format suivant : JJ/MM/AAAA \n");
									dateFin = sc.nextLine();
									saisieDateFinEstValide = methodesverificationdate.estValideDate(dateFin);

									if (saisieDateFinEstValide == true) {

										System.out.println("Affichage des résultats .......");

									} else {

										// la date de fin n'est pas valide
									}

								} else {

									// la date de début n'est pas valide

								}

							}
							// a enlever après
							retourMenuTransaction = 1;

						} while (retourMenuTransaction != -1);
						break;
					case 2:
						int numeroClientTransaction;
						do {
							System.out.println("Saisissez -1 pour revenir en arrière \n"
									+ "Veuillez rentrer le numéro du client afin de visualiser ses transactions.");

							while (!sc.hasNextInt()) {
								System.out.println("Veuillez rentrer un nombre ! ");
								sc.next();
							}
							numeroClientTransaction = sc.nextInt();

							// Traitement Si client existe alors sinon ...

						} while (numeroClientTransaction != -1);
						break;
					default:
						System.out.println("Veuillez rentrer un nombre entre 1 et 2 ");
						break;
					}
				} while (choixTransaction != -1);
				break;
			case 6:
				int choixStatistique;
				do {
					System.out.println("Saisissez -1 pour revenir en arrière \n"
							+ "Bienvenue dans le menu des statistiques.Veuillez effectuer un choix parmis les suivants : \n"
							+ "");

					while (!sc.hasNextInt()) {
						System.out.println("Veuillez rentrer un nombre ! ");
						sc.next();
					}
					choixStatistique = sc.nextInt();
				} while (choixStatistique != -1);

				break;
			default:
				System.out.println("Veuillez rentrer un nombre entre 1 et 8 ");
				break;
			}
		} while (choix != 7);
	}
}