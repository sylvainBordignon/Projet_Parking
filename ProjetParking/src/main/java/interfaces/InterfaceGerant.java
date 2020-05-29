package interfaces;

import java.util.ArrayList;
import java.util.Scanner;

import methodes.MethodesGerant;
import mysql.ClientMysql;
import mysql.GerantMysql;
import pojo.Client;
import pojo.Reservation;
import verificationsentreeclavier.MethodesFormatClavierInterface;
import verificationsentreeclavier.MethodesVerificationsDate;

public class InterfaceGerant {

	public static void lancerInterfaceGerant() {
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
			choix = MethodesFormatClavierInterface
					.entreeEntier("Choississez un nombre correspondant à une fonctionnalité");
			switch (choix) {
			case 1:
				int numeroClient;
				do {
					numeroClient = MethodesFormatClavierInterface.entreeEntier(
							"Veuillez saisir le numéro du client ... (Entrez 0 pour revenir au menu principal)");
					System.out.println("Information du client :");
					Client c = ClientMysql.getInstance().visualierInfoClient(numeroClient);
					if (c == null) {
						System.out.println("Ce numéro de client n'est associé à aucun client.");
					} else {
						System.out.println(c);
					}
					numeroClient = 0;
				} while (numeroClient != 0);
				break;
			case 2:
				int choixModifTarif;
				do {
					System.out.println("Quel tarif voulez-vous modifier ? (Entrez -1 pour revenir au menu principal) \n"

							+ "1 - Frais de stationnement pendant période réservée \n"
							+ "2 - Frais de stationnement de dépassement de période de base \n"
							+ "3 - Frais de stationnement de dépassement cumulatif \n"
							+ "4 - Frais de prolongation de la période d'attente  \n");
					choixModifTarif = MethodesFormatClavierInterface
							.entreeEntier("Choississez un nombre correspondant à une fonctionnalité");
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
									+ "Valeur actuelle des frais de dépassement de stationnement de base : "
									+ tarifDepassement + " \n" + "Veuillez saisir la nouvelle valeur ...");
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
						float valeurFraisStationementDepassementCumulatif;
						do {
							float tarifDepassementCumul = GerantMysql.getInstance()
									.selectionnerTarifDepassementAugmentation();
							System.out.println("Saisissez -1 pour revenir en arrière \n"
									+ "Valeur actuelle des frais de dépassement de stationnement cumulatif : "
									+ tarifDepassementCumul + " \n" + "Veuillez saisir la nouvelle valeur ...");
							while (!sc.hasNextFloat()) {
								System.out.println("Veuillez rentrer un nombre ! ");
								sc.next();
							}
							valeurFraisStationementDepassementCumulatif = sc.nextFloat();
							GerantMysql.getInstance()
									.modifierTarifDepassementAugmentation(valeurFraisStationementDepassementCumulatif);
							System.out.println("Nouveau tarif : " + valeurFraisStationementDepassementCumulatif);
							valeurFraisStationementDepassementCumulatif = -1;
						} while (valeurFraisStationementDepassementCumulatif != -1);
						break;
					case 4:
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
				int nbPlaceSurreservation = GerantMysql.getInstance().selectionnerNbPlaceSurreservation();
				do {
					nbPlaceSurreservation = MethodesFormatClavierInterface.entreeEntier(
							"Veuillez saisir le nouveau nombre de place en surréservation (Saisissez -1 pour revenir en arrière) "
									+ "Nombre actuel : " + nbPlaceSurreservation);
					MethodesGerant methodesgerant = new MethodesGerant();
					methodesgerant.changerNombreSurreservation(nbPlaceSurreservation);
					nbPlaceSurreservation = -1;
				} while (nbPlaceSurreservation != -1);
				break;
			case 4:
				int choixReservationEnCours;
				do {
					System.out.println("Saisissez -1 pour revenir en arrière \n"

							+ "1 - Pour chaque place réservé, afficher le client et le créneau  \n"
							+ "2 - Rafrichir la page " + "\n");
					choixModifTarif = MethodesFormatClavierInterface
							.entreeEntier("Choississez un nombre correspondant à une fonctionnalité");
					switch (choixModifTarif) {
					case 1:
						int retourMenuListeReservations;
						do {
							System.out.println("Saisissez -1 pour revenir en arrière \n"
									+ "Voici la liste des places occupées ainsi que leurs informations \n"
									+ "|  Nom  |  Prenom  |  num_place  |  date arrive  |  date fin  |  duree  |  date arrive reel  |  délai  |  \n");
							GerantMysql.getInstance().visualiserLesReservationsEnCours();
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
					choixTransaction = MethodesFormatClavierInterface
							.entreeEntier("Saisissez -1 pour revenir en arrière \n"
									+ "1 - Visualiser les transactions pour une période donnée. \n"
									+ "2 - Visualiser les transactions pour un client spécifique.  \n");
					switch (choixTransaction) {
					case 1:
						int retourMenuTransaction;
						do {
							System.out.println(
									"Saisissez -1 pour revenir en arrière \n" + "Veuillez rentrer la période ...\n"
											+ "Commencez par rentrer la date de début au format suivant : JJ/MM/AAAA\n"
											+ "date de début : ");
							boolean saisieDateDebutEstValide, saisieDateFinEstValide = false;
							String dateDebut, dateFin;
							dateDebut = sc.nextLine();
							if (dateDebut.equals("-1")) {
								retourMenuTransaction = -1;
							} else {
								saisieDateDebutEstValide = MethodesVerificationsDate.estValideDateFormat(dateDebut);
								if (saisieDateDebutEstValide) {
									System.out.println(
											"Veuillez rentrer la date de fin au format suivant : JJ/MM/AAAA \n");
									dateFin = sc.nextLine();
									saisieDateFinEstValide = MethodesVerificationsDate.estValideDateFormat(dateFin);
									if (saisieDateFinEstValide) {
										System.out.println("Affichage des résultats .......");
										ArrayList<Reservation> reservations = ClientMysql.getInstance().selectionnerListeReservationsPasseesPeriode(dateDebut, dateFin);
										for (int i = 0; i < reservations.size(); i++) {
											System.out.println(reservations.get(i).afficherInfo());
										}
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
							System.out.println("Saisissez 0 pour revenir en arrière \n");
							numeroClientTransaction = MethodesFormatClavierInterface.entreeEntier(
									"Veuillez rentrer le numéro du client afin de visualiser ses transactions.");
							Client client = ClientMysql.getInstance().visualierInfoClient(numeroClientTransaction);
							if (client != null) {
								ArrayList<Reservation> reservations = ClientMysql.getInstance()
										.selectionnerListeReservationsPassees(client.getId());
								for (int i = 0; i < reservations.size(); i++) {
									System.out.println(reservations.get(i).afficherInfo());
								}
							} else {
								System.out.println("Ce numéro de client n'est associé à aucun client.");
							}
						} while (numeroClientTransaction != 0);
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