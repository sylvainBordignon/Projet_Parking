package interfaces;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import methodes.MethodesCalculs;
import methodes.MethodesClient;
import mysql.ClientMysql;
import mysql.ReservationPermanenteMysql;
import pojo.Client;
import pojo.ReservationPermanente;
import verificationsentreeclavier.MethodesVerificationsDate;

public class InterfaceClient {

	private static final String MESSAGE_ERREUR = "Erreur dans la saisie, entrez un numéro correspondant à un choix du menu.",
			MESSAGE_RETOUR_ACCUEIL = "Retour à l'accueil...",
			MESSAGE_FERMETURE_APPLI = "Fermeture de l'application mobile...",
			MESSAGE_QUE_FAIRE = "Que souhaitez-vous faire ?",
			MESSAGE_CHOIX_DATE = "Veuillez choisir une date (Format : JJ/MM/AAAA)",
			MESSAGE_CHOIX_HEURE = "Veuillez choisir une heure de début (Format : HH:MM)",
			MESSAGE_CHOIX_DUREE = "Veuillez choisir une durée de réservation (Format : HH:MM)";

	private static Client client;

	private static Scanner sc = new Scanner(System.in);

	public static void accesInterfaceClient() {
		System.out.println("Bonjour, bienvenue sur l'application mobile.");
		boolean fermetureAppli = false;
		while (!fermetureAppli) {
			System.out.println(MESSAGE_QUE_FAIRE);
			System.out.println("1 - S'inscrire\n2 - Se connecter en tant que client\n3 - Quitter l'application mobile");
			String choixAppli = sc.nextLine();
			switch (choixAppli) {
			case "1":
				// module pour l'inscription
				break;
			case "2":
				// pour se connecter
				boolean finConnexion = false;
				while (!finConnexion) {
					System.out.println("Veuillez entrer votre numéro client : ");
					String numCli = sc.nextLine();
					System.out.println("Veuillez saisir votre adresse mail :");
					String mail = sc.nextLine();
					client = ClientMysql.getInstance().visualierInfoClient(Integer.parseInt(numCli));
					if (client != null) {
						if (client.getMail().equals(mail)) {
							finConnexion = true;
							System.out.println("Connexion réussie.");
							interfaceClient();
						} else {
							System.out.println("Erreur, cette adresse mail n'est pas correcte.");
						}
					} else {
						System.out.println("Ce numéro client n'est pas correct.");
					}
				}
				break;
			case "3":
				fermetureAppli = true;
				System.out.println(MESSAGE_FERMETURE_APPLI);
				break;
			default:
				System.out.println(MESSAGE_ERREUR);
			}

		}
	}

	public static void interfaceClient() {
		MethodesClient methodesclient = new MethodesClient();
		boolean fin = false;
		while (!fin) {
			System.out.println(MESSAGE_QUE_FAIRE);
			System.out.println("1 - Consulter mon profil");
			System.out.println("2 - Gérer mes réservations");
			System.out.println("3 - Gérer mes réservations permanentes");
			System.out.println("4 - Gérer mes véhicules");
			System.out.println("5 - Consulter la disponibilité des places du parking");
			System.out.println("6 - Consulter mes factures");
			System.out.println("7 - Se déconnecter");
			System.out.print("Choix : ");
			String choixMenu = sc.nextLine();
			switch (choixMenu) {
			case "1":
				boolean finProfil = false;
				while (!finProfil) {
					System.out.println("Affichage des informations du profil... A RAJOUTER");
					System.out.println(MESSAGE_QUE_FAIRE
							+ "\n1 - Modifier mon profil\n2 - Retourner à l'accueil de l'application\n3 - Se déconnecter");
					System.out.print("Choix : ");
					String choixProfil = sc.nextLine();
					switch (choixProfil) {
					case "1":
						methodesclient.modifierClient(client);
						// ...
						System.out.println("Validation des modifications effectuées.");
						break;
					case "2":
						finProfil = true;
						System.out.println(MESSAGE_RETOUR_ACCUEIL);
						break;
					case "3":
						finProfil = true;
						fin = true;
						System.out.println(MESSAGE_FERMETURE_APPLI);
						break;
					default:
						System.out.println(MESSAGE_ERREUR);
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
					System.out.println(MESSAGE_QUE_FAIRE);
					if (reservationEnCours) {
						System.out.println("1 - Gérer la réservation en cours");
					}
					if (reservationAVenir) {
						System.out.println("2 - Modifier une réservation à venir");// +ajout
					}
					System.out.println("3 - Ajouter une nouvelle réservation");
					System.out.println("4 - Retourner à l'accueil de l'application");
					System.out.println("5 - Se déconnecter");
					String choixReservation = sc.nextLine();
					switch (choixReservation) {
					case "1":
						if (reservationEnCours) {
							System.out.println(MESSAGE_QUE_FAIRE
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
								System.out.println(MESSAGE_ERREUR);
							}
						} else {
							System.out.println(MESSAGE_ERREUR);
						}
						break;
					case "2":
						if (reservationAVenir) {
							System.out.println("Quelle réservation souhaitez-vous modifier ?");
							String choixModifReservation = sc.nextLine();
							// verif
							// puis interface modif
						} else {
							System.out.println(MESSAGE_ERREUR);
						}
						break;
					case "3":
						entrerDateReservation();
						// +save dans la BDD
						break;
					case "4":
						finReservation = true;
						System.out.println(MESSAGE_RETOUR_ACCUEIL);
						break;
					case "5":
						finReservation = true;
						fin = true;
						System.out.println(MESSAGE_FERMETURE_APPLI);
						break;
					default:
						System.out.println(MESSAGE_ERREUR);
					}

				}
				break;
			case "3":
				boolean finPermanent = false;
				while (!finPermanent) {
					List<ReservationPermanente> listeReservPerma = ReservationPermanenteMysql.getInstance()
							.selectionnerReservationsPermanentesClient(client.getId());
					if (listeReservPerma.isEmpty()) {
						System.out.println("Vous n'avez pas de réservations permanentes.");
					} else {
						System.out.println("Liste de vos réservations permanentes : ");
						for (int i = 0; i < listeReservPerma.size(); i++) {
							System.out.println(i + " : " + listeReservPerma.get(i));
						}
					}
					System.out.println(MESSAGE_QUE_FAIRE + "\n1 - Ajouter une réservation permanente");

					if (!listeReservPerma.isEmpty()) {
						System.out.println("2 - Supprimer une réservation permanente");
					}

					System.out.println("3 - Retour à l'accueil\n4 - Se déconnecter");
					String choixPermanent = sc.nextLine();
					switch (choixPermanent) {
					case "1":
						if (listeReservPerma.size() <= 1) {
							ReservationPermanente reservation = null;
							System.out.println(
									"Veuillez choisir un type de réservation permanente : \n1 - Journalier\n2 - Hebdomadaire\n3 - Mensuel");
							String typeReserv = sc.nextLine();
							boolean finAjout = false;
							switch (typeReserv) {
							case "1":
								while (!finAjout) {
									System.out.println(MESSAGE_CHOIX_HEURE);
									String heureDebut = sc.nextLine();
									MethodesVerificationsDate methodesDate = new MethodesVerificationsDate();
									if (methodesDate.estValideHeureMinute(heureDebut)) {
										System.out.println((MESSAGE_CHOIX_DUREE));
										String duree = sc.nextLine();
										try {
											String[] tab = heureDebut.split(":");
											reservation = new ReservationPermanente(client.getId(), "journalière",
													new Time(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]), 0),
													Integer.parseInt(duree));
											ReservationPermanenteMysql.getInstance()
													.ajoutReservationPermanenteAuto(reservation);
											finAjout = true;
											System.out.println("Votre réservation permanente à bien été ajoutée.");
										} catch (Exception e) {
											System.out.println(
													"Erreur dans le format des champs entrés, veuillez réessayer.");
										}
									} else {
										System.out.println(
												"Mauvais format de l'heure ou doit être entre 0 et 24 heures.");
									}
								}
								break;
							case "2":
								while (!finAjout) {
									System.out.println(
											"Jour de la semaine : (0 = Lundi, 1 = Mardi, 2 = Mercredi, 3 = Jeudi, 4 = Vendredi, 5 = Samedi, 6 = Dimanche)");
									String choixJour = sc.nextLine();
									System.out.println(MESSAGE_CHOIX_HEURE);
									String heureDebut = sc.nextLine();
									MethodesVerificationsDate methodesDate = new MethodesVerificationsDate();
									if (methodesDate.estValideHeureMinute(heureDebut)) {
										System.out.println((MESSAGE_CHOIX_DUREE));
										String duree = sc.nextLine();
										try {
											String[] tab = heureDebut.split(":");
											int jour = Integer.parseInt(choixJour);
											if (jour >= 0 && jour <= 6) {
												reservation = new ReservationPermanente(client.getId(), "hebdomadaire",
														new Time(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]), 0),
														Integer.parseInt(duree), jour);
												ReservationPermanenteMysql.getInstance()
														.ajoutReservationPermanenteAuto(reservation);
												finAjout = true;
												System.out.println("Votre réservation permanente à bien été ajoutée.");
											} else {
												System.out.println("Le jour doit être un chiffre en 0 et 6.");
											}
										} catch (Exception e) {
											System.out.println(
													"Erreur dans le format des champs entrés, veuillez réessayer.");
										}
									} else {
										System.out.println(
												"Mauvais format de l'heure ou doit être entre 0 et 24 heures.");
									}
								}
								break;
							case "3":
								while (!finAjout) {
									System.out.println("Jour du mois : (1-28)");
									String choixMois = sc.nextLine();
									System.out.println(MESSAGE_CHOIX_HEURE);
									String heureDebut = sc.nextLine();
									MethodesVerificationsDate methodesDate = new MethodesVerificationsDate();
									if (methodesDate.estValideHeureMinute(heureDebut)) {
										System.out.println((MESSAGE_CHOIX_DUREE));
										String duree = sc.nextLine();
										try {
											String[] tab = heureDebut.split(":");
											int jourMois = Integer.parseInt(choixMois);
											if (jourMois >= 0 && jourMois <= 28) {
												reservation = new ReservationPermanente(client.getId(), "mensuelle",
														new Time(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]), 0),
														Integer.parseInt(duree), null, jourMois);
												ReservationPermanenteMysql.getInstance()
														.ajoutReservationPermanenteAuto(reservation);
												finAjout = true;
												System.out.println("Votre réservation permanente à bien été ajoutée.");
											} else {
												System.out.println("Le jour doit être un chiffre en 0 et 28.");
											}
										} catch (Exception e) {
											System.out.println(
													"Erreur dans le format des champs entrés, veuillez réessayer.");
										}
									} else {
										System.out.println(
												"Mauvais format de l'heure ou doit être entre 0 et 24 heures.");
									}
								}
								break;
							default:
								System.out.println(MESSAGE_ERREUR);
							}
						} else {
							// si deja 3 r�servations, proposer une fusion ou suppression
							System.out.println("Vous possédez déjà 3 réservations.\n" + MESSAGE_QUE_FAIRE
									+ "\n1 - Fusionner deux réservations\n2 - Supprimer une réservation\n3 - Revenir au menu précédant\n4 - Retour à l'accueil\n5 - Se déconnecter");
							String choixReservPlein = sc.nextLine();
							switch (choixReservPlein) {
							case "1":
								System.out.println("Entrer les num�ros des deux réservations : ");
								// ...prendre date d�but 1 jusqua date fin 2
								// modif BDD
								break;
							case "2":
								System.out.println(
										"Veuillez entrez le numéro correspondant à la réservation permanente :");
								String supprReserv = sc.nextLine();
								try {
									int numReserv = Integer.parseInt(supprReserv);
									if (numReserv >= 0 && numReserv < listeReservPerma.size()) {
										int res = ReservationPermanenteMysql.getInstance()
												.suppressionReservationPermanente(
														listeReservPerma.get(Integer.parseInt(supprReserv)).getId());
										System.out.println("Votre réservation permanente à bien été supprimé.");
									} else {
										System.out.println(
												"Erreur lors de la suppression, entrez un numéro correspondant à une réservation permanente.");
									}
								} catch (NumberFormatException e) {
									System.out.println("Erreur, vous devez entrer un numéro.");
								}
								break;
							case "3":
								break;
							case "4":
								finPermanent = true;
								System.out.println(MESSAGE_RETOUR_ACCUEIL);
								break;
							case "5":
								finPermanent = true;
								fin = true;
								System.out.println(MESSAGE_FERMETURE_APPLI);
								break;
							default:
								System.out.println(MESSAGE_ERREUR);
							}
							break;
						}
						break;
					case "2":
						System.out.println("Veuillez entrez le numéro correspondant à la réservation permanente :");
						String supprReserv = sc.nextLine();
						try {
							int numReserv = Integer.parseInt(supprReserv);
							if (numReserv >= 0 && numReserv < listeReservPerma.size()) {
								int res = ReservationPermanenteMysql.getInstance().suppressionReservationPermanente(
										listeReservPerma.get(Integer.parseInt(supprReserv)).getId());
								System.out.println("Votre réservation permanente à bien été supprimé.");
							} else {
								System.out.println(
										"Erreur lors de la suppression, entrez un numéro correspondant à une réservation permanente.");
							}
						} catch (NumberFormatException e) {
							System.out.println("Erreur, vous devez entrer un numéro.");
						}
						break;
					case "3":
						finPermanent = true;
						System.out.println(MESSAGE_RETOUR_ACCUEIL);
						break;
					case "4":
						finPermanent = true;
						fin = true;
						System.out.println(MESSAGE_FERMETURE_APPLI);
						break;
					default:
						System.out.println(MESSAGE_ERREUR);
					}
				}
				break;
			case "4":
				boolean finVehicule = false;
				while (!finVehicule) {
					System.out.println("Liste de vos véhicules : ");
					ArrayList<String> listeVehicules = (ArrayList<String>) ClientMysql.getInstance()
							.selectionnerListeVehicules(5);// 5 est un num random, il faut avoir le num client
					for (int i = 0; i < listeVehicules.size(); i++) {
						System.out.println(listeVehicules.get(i));
					}
					System.out.println(MESSAGE_QUE_FAIRE
							+ "\n1 - Ajouter un véhicule\n2 - Supprimer un véhicule\n3 - Retour à l'accuei?\n4 - Se déconnecter");
					String choixVehicule = sc.nextLine();
					switch (choixVehicule) {
					case "1":
						System.out
								.println("Veuillez entrez le numéro de plaque d'immatriculation du nouveau véhicule :");
						String plaqueVehicule = sc.nextLine();
						if (plaqueVehicule.matches("^([A-Z]){2}-([0-9]){3}-([A-Z]){2}")) {
							ClientMysql.getInstance().ajouterVehicule(plaqueVehicule, 5);// Ajout dans la BDD; 5 est un
																							// num random, il faut avoir
																							// le num client
							System.out.println("Véhicule ajouté.");
						} else
							System.out.println("Veuillez entrer un numéro d'immatriculation valide (Ex: AA-000-AA)");
						break;
					case "2":
						System.out.println("Veuillez entrez le numéro correspondant à la plaque d'immatriculation :");
						String supprPlaque = sc.nextLine();
						if (ClientMysql.getInstance().supprimerVehicule(supprPlaque, 5)) {
							// Suppression dans la BDD; 5 est un num random, il faut avoir le num client
							System.out.println("Véhicule supprimé.");
						} else
							System.out.println("Véhicule inconnu.");
						break;
					case "3":
						finVehicule = true;
						System.out.println(MESSAGE_RETOUR_ACCUEIL);
						break;
					case "4":
						finVehicule = true;
						fin = true;
						System.out.println(MESSAGE_FERMETURE_APPLI);
						break;
					default:
						System.out.println(MESSAGE_ERREUR);
					}
				}
				break;
			case "5":
				System.out.println("Consultation des disponiblités des places de parking");
				entrerDateReservation();
				// Puis check dans la BDD

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
							"Quelles factures voulez-vous consulter ?\n1 - Les factures du mois-ci\n2 - Toutes les factures\n3 - Retour à l'accueil\n4 - Se déconnecter");
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
						System.out.println(MESSAGE_RETOUR_ACCUEIL);
						break;
					case "4":
						finFacture = true;
						fin = true;
						System.out.println(MESSAGE_FERMETURE_APPLI);
						break;
					default:
						System.out.println(MESSAGE_ERREUR);
					}
				}
				break;
			case "7":
				fin = true;
				break;
			default:
				System.out.println(MESSAGE_ERREUR);

			}
		}
	}
	public static void entrerDateReservation() {
		MethodesVerificationsDate verifDate = new MethodesVerificationsDate();
		MethodesClient methodesclient = new MethodesClient();
		MethodesCalculs methodescalculs = new MethodesCalculs();
		boolean valide = false;
		String dateReserv = null, heureReserv=null, dureeReserv=null;
		
		while (!valide) {
			System.out.println(MESSAGE_CHOIX_DATE);
			dateReserv = sc.nextLine();
			valide = verifDate.estValideDate(dateReserv);
			if (!valide) {
				System.out.println("Erreur dans le format, veuillez reéssayer.");
			}
		}
		valide = false;
		while (!valide) {
			// si dateUtilisateur = dateAujourd'hui alors on vérifie si l'heure d'arrivée
			// n'est pas déjà passé.
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(new Date());

			if (date.equals(dateReserv)) {
				System.out.println(MESSAGE_CHOIX_HEURE);
				heureReserv = sc.nextLine();
				valide = verifDate.estValideHeureMinuteMemeJour(heureReserv);
				if (!valide) {
					System.out.println("Erreur dans le format, veuillez reéssayer.");
				}

			} else {
				System.out.println(MESSAGE_CHOIX_HEURE);
				heureReserv = sc.nextLine();
				valide = verifDate.estValideHeureMinute(heureReserv);
				if (!valide) {
					System.out.println("Erreur dans le format, veuillez reéssayer.");
				}

			}
		}
		System.out.println(MESSAGE_CHOIX_DUREE);
		valide = false;
		while (!valide) {
			dureeReserv = sc.nextLine();
			valide = verifDate.estValideFormatReservation(dureeReserv);
			if (!valide) {
				System.out.println("Erreur dans le format, veuillez reéssayer.");
			}
		}
	methodesclient.consulterPlacesParkingDispo(dateReserv, heureReserv, dureeReserv);
	
	}

	public static void main(String[] args) {
		InterfaceClient.interfaceClient();
	}
}

