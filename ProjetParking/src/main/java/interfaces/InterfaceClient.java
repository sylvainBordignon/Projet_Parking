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
import mysql.FacturationMysql;
import mysql.ReservationPermanenteMysql;
import pojo.Client;
import pojo.Facturation;
import pojo.Reservation;
import pojo.ReservationPermanente;
import verificationsentreeclavier.MethodesFormatClavierInterface;
import verificationsentreeclavier.MethodesVerificationsAjoutClient;

public class InterfaceClient {

	private static final String MESSAGE_ERREUR = "Erreur dans la saisie, entrez un numéro correspondant à un choix du menu.",
			MESSAGE_RETOUR_ACCUEIL = "Retour à l'accueil...",
			MESSAGE_FERMETURE_APPLI = "Fermeture de l'application mobile...",
			MESSAGE_QUE_FAIRE = "Que souhaitez-vous faire ?",
			MESSAGE_CHOIX_DATE = "Veuillez choisir une date (Format : JJ/MM/AAAA)",
			MESSAGE_CHOIX_HEURE = "Veuillez choisir une heure de début (Format : HH:MM)",
			MESSAGE_ENTREZ_NUMERO_CLIENT = "Veuillez  rentrer votre numéro de client",
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
				MethodesClient.ajouterUnClient();
				System.out.println("Vous pouvez vous connecter.");
				break;
			case "2":
				// pour la connexion
				boolean finConnexion = false;
				while (!finConnexion) {
					int numCli = MethodesFormatClavierInterface.entreeEntier(MESSAGE_ENTREZ_NUMERO_CLIENT);
					String mail = MethodesVerificationsAjoutClient.verifMail();
					client = ClientMysql.getInstance().visualierInfoClient(numCli);
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
					System.out.println("Affichage des informations du profil :" + client);
					System.out.println(MESSAGE_QUE_FAIRE
							+ "\n1 - Modifier mon profil\n2 - Retourner à l'accueil de l'application\n3 - Se déconnecter");
					System.out.print("Choix : ");
					String choixProfil = sc.nextLine();
					switch (choixProfil) {
					case "1":
						MethodesClient.modifierClient(client);
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
				boolean finReservation = false, reservationEnCours = false, reservationAVenir = false;
				while (!finReservation) {
					System.out.println("Liste des réservations : ");
					List<Reservation> listeReservations = ClientMysql.getInstance()
							.selectionnerListeReservations(client.getId());
					Reservation reservationCourante = ClientMysql.getInstance()
							.verifierReservationCorrespondanteClientMemeJour(client.getId());
					if (!listeReservations.isEmpty()) {
						reservationAVenir = true;
					}
					if (reservationCourante != null) {
						reservationEnCours = true;
					}
					if (reservationAVenir) {
						for (int i = 0; i < listeReservations.size(); i++) {
							System.out.println(listeReservations.get(i));
						}
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
						System.out.println("2 - Modifier une réservation à venir");
					}
					System.out.println("3 - Retourner à l'accueil de l'application");
					System.out.println("4 - Se déconnecter");
					String choixReservation = sc.nextLine();
					switch (choixReservation) {
					case "1":
						if (reservationEnCours) {
							System.out.println(MESSAGE_QUE_FAIRE
									+ "\n1 - Prolonger la fin de la réservation de 30 minutes\n2 - Prolonger le délai d'attente");
							String choixReservEnCours = sc.nextLine();
							switch (choixReservEnCours) {
							case "1":
								if (MethodesClient.verifierProlongationPossible30Minutes(reservationCourante)) {
									String dateDebut = reservationCourante.getDateDebut().toString();
									dateDebut = dateDebut.substring(0, 19);
									MethodesCalculs methodesCalculs = new MethodesCalculs();
									String sduree = methodesCalculs
											.conversionMinuteEnFormatHeure(reservationCourante.getDuree() + 30);
									MethodesClient.modifierDureeReservation(dateDebut, sduree, client.getId(),
											reservationCourante);
								} else {
									System.out.println(
											"Vous ne pouvez prolonger la réservation que 30 minutes avant la fin.");
								}
								break;
							case "2":
								if (MethodesClient.verifierProlongationDelaiDattentePossible(reservationCourante)) {
									int prolongation = MethodesFormatClavierInterface.entreeEntier("De combien de minutes voulez-vous prolonger le délai d'attentes?");
									while(!MethodesClient.verifierProlongationCorrecte(reservationCourante, prolongation)){
										System.out.println("Le delai d'attente ne peut pas excéder l'heure de fin de la réservation.");
										prolongation = MethodesFormatClavierInterface.entreeEntier("Veuillez entrer une nouvelle valeur : ");
									}
									reservationCourante.setDelaiAttente(reservationCourante.getDelaiAttente()+prolongation);
									ClientMysql.getInstance().modifierReservation(reservationCourante);
									System.out.println("Le délai d'attente a bien été prolongé.");
								}
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
							System.out.println("Veuillez entrer l'ID de la réservation que vous voulez modifier.");
							for (int i = 0; i < listeReservations.size(); i++) {
								System.out.println(listeReservations.get(i));
							}
							int choixModifReservation = MethodesFormatClavierInterface.entreeEntier("Choix :");
							boolean trouve = false;
							int emplacement = 0;
							while (!trouve) {
								for (int i = 0; i < listeReservations.size(); i++) {
									if (listeReservations.get(i).getId() == choixModifReservation) {
										trouve = true;
										emplacement = i;
									}
								}
								if (!trouve) {
									System.out.println("Réservation inexistante.");
									choixModifReservation = MethodesFormatClavierInterface
											.entreeEntier("Veuillez reessayer :");
								}
							}
							Reservation reservation = listeReservations.get(emplacement);
							boolean finChoixModif = false;
							while (!finChoixModif) {
								System.out.println("Que voulez vous modifier? \n1 - Date de début \n2 - Duree ");
								String quoiModifier = sc.nextLine();
								switch (quoiModifier) {
								case "1":
									String dateReserv = MethodesFormatClavierInterface.entreeDate(MESSAGE_CHOIX_DATE);
									String heureReserv;
									DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									String date = sdf.format(new Date());
									if (date.equals(dateReserv)) {
										heureReserv = MethodesFormatClavierInterface
												.entreeHeureMemeJour(MESSAGE_CHOIX_HEURE);
									} else {
										heureReserv = MethodesFormatClavierInterface.entreeHeure(MESSAGE_CHOIX_HEURE);
									}
									int dureeMinute = reservation.getDuree();
									MethodesCalculs methodescalculs = new MethodesCalculs();
									String sduree = methodescalculs.conversionMinuteEnFormatHeure(dureeMinute);
									System.out.println(
											"Date de réservation : " + dateReserv + " \n Heure de réservation : "
													+ heureReserv + " \n durée réservation : " + sduree);
									boolean restePlace = MethodesClient.consulterPlacesParkingDispo(dateReserv,
											heureReserv, sduree);
									if (restePlace) {
										MethodesClient.modifierUneReservation(dateReserv, heureReserv, sduree,
												client.getId(), reservation);
									} else {
										System.out.println(
												"Vous ne pouvez pas modifier cette réservation à cette date, le parking est complet.");
									}
									finChoixModif = true;
									break;
								case "2":
									String dureeReserv = MethodesFormatClavierInterface
											.entreeHeure(MESSAGE_CHOIX_DUREE);
									String debutReserv = reservation.getDateDebut().toString();
									debutReserv = debutReserv.substring(0, 19);
									System.out.println(debutReserv);
									MethodesClient.modifierDureeReservation(debutReserv, dureeReserv, client.getId(),
											reservation);
									finChoixModif = true;
									break;
								default:
									System.out.println(MESSAGE_ERREUR);
								}
							}
						} else {
							System.out.println(MESSAGE_ERREUR);
						}
						break;
					case "3":
						finReservation = true;
						System.out.println(MESSAGE_RETOUR_ACCUEIL);
						break;
					case "4":
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
						if (listeReservPerma.size() < 3) {
							ajouterReservationPermanente();
						} else {
							System.out.println(
									"Vous possédez déjà 3 réservations. Vous devez en supprimer une avant de pouvoir ajouter une nouvelle réservation.");
						}
						break;
					case "2":
						int supprReserv = MethodesFormatClavierInterface.entreeEntier(
								"Veuillez entrez le numéro correspondant à la réservation permanente à supprimer :");
						if (supprReserv >= 0 && supprReserv < listeReservPerma.size()) {
							int res = ReservationPermanenteMysql.getInstance()
									.suppressionReservationPermanente(listeReservPerma.get(supprReserv).getId());
							if (res == 1) {
								System.out.println("Votre réservation permanente à bien été supprimé.");
							} else {
								System.out.println("Erreur lors de la suppression dans la base de données.");
							}
						} else {
							System.out.println(
									"Erreur lors de la suppression, entrez un numéro correspondant à une réservation permanente.");
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
							.selectionnerListeVehicules(client.getId());
					for (int i = 0; i < listeVehicules.size(); i++) {
						System.out.println(listeVehicules.get(i));
					}
					System.out.println(MESSAGE_QUE_FAIRE
							+ "\n1 - Ajouter un véhicule\n2 - Supprimer un véhicule\n3 - Retour à l'accueil\n4 - Se déconnecter");
					String choixVehicule = sc.nextLine();
					switch (choixVehicule) {
					case "1":
						String plaqueVehicule = MethodesFormatClavierInterface.entreePlaqueImmatriculation(
								"Veuillez entrez le numéro de plaque d'immatriculation du nouveau véhicule :");
						ClientMysql.getInstance().ajouterVehicule(plaqueVehicule, client.getId());// Ajout dans la base
						break;
					case "2":
						System.out.println("Veuillez entrez le numéro correspondant à la plaque d'immatriculation :");
						String supprPlaque = sc.nextLine();
						if (ClientMysql.getInstance().supprimerVehicule(supprPlaque, client.getId())) {
							// Suppression dans la BDD
							System.out.println("Véhicule supprimé.");
						} else {
							System.out.println("Véhicule inconnu.");
						}
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
				String[] parametresClient = entrerDateReservation();
				MethodesClient.consulterPlacesParkingDispo(parametresClient[0], parametresClient[1],
						parametresClient[2]);
				MethodesClient.creeUneReservation(parametresClient[0], parametresClient[1], parametresClient[2],
						client.getId());
				// si oui on fait la r�servation + retour accueil
				// si non on demande si nouvelle recherche ou retour accueil
				break;
			case "6":
				boolean finFacture = false;
				while (!finFacture) {
					System.out.println(
							"Quelles factures voulez-vous consulter ?\n1 - Les factures du mois-ci\n2 - Toutes les factures\n3 - Retour à l'accueil\n4 - Se déconnecter");
					String choixFacture = sc.nextLine();
					List<Facturation> facturations;
					switch (choixFacture) {
					case "1":
						System.out.println("Affichage des factures du mois");
						facturations = FacturationMysql.getInstance().selectionnerFacturationsClientMois(client.getId());
						for(int i = 0; i < facturations.size(); i++) {
							System.out.println(facturations.get(i));
						}
						break;
					case "2":
						System.out.println("Affichage de toutes les factures du client");
						facturations = FacturationMysql.getInstance().selectionnerFacturationsClient(client.getId());
						for(int i = 0; i < facturations.size(); i++) {
							System.out.println(facturations.get(i));
						}
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

	public static String[] entrerDateReservation() {
		String[] tab = new String[3];
		String dateReserv = MethodesFormatClavierInterface.entreeDate(MESSAGE_CHOIX_DATE);
		String heureReserv;
		tab[0] = dateReserv;
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date());
		if (date.equals(dateReserv)) {
			heureReserv = MethodesFormatClavierInterface.entreeHeureMemeJour(MESSAGE_CHOIX_HEURE);
		} else {
			heureReserv = MethodesFormatClavierInterface.entreeHeure(MESSAGE_CHOIX_HEURE);

		}
		String dureeReserv = MethodesFormatClavierInterface.entreeHeure(MESSAGE_CHOIX_DUREE);
		tab[2] = dureeReserv;
		tab[1] = heureReserv;
		return tab;
	}

	public static void ajouterReservationPermanente() {
		ReservationPermanente reservation = null;
		System.out.println(
				"Veuillez choisir un type de réservation permanente : \n1 - Journalier\n2 - Hebdomadaire\n3 - Mensuel");
		String typeReserv = sc.nextLine();
		if (typeReserv.equals("1") || typeReserv.equals("2") || typeReserv.equals("3")) {
			boolean finAjout = false, donnees = false;
			while (!finAjout) {
				String heureDebut = MethodesFormatClavierInterface.entreeHeure(MESSAGE_CHOIX_HEURE);
				String duree = MethodesFormatClavierInterface.entreeHeure(MESSAGE_CHOIX_DUREE);
				try {
					String[] tabDebut = heureDebut.split(":");
					String[] tabDuree = duree.split(":");
					reservation = new ReservationPermanente(client.getId(), "journalière",
							new Time(Integer.parseInt(tabDebut[0]), Integer.parseInt(tabDebut[1]), 0),
							new Time(Integer.parseInt(tabDuree[0]), Integer.parseInt(tabDuree[1]), 0));
					donnees = true;
				} catch (Exception e) {
					System.out.println("Erreur dans le format des champs entrés, veuillez réessayer.");

				}
				boolean format = false;
				if (typeReserv.equals("1") && donnees) {
					format = true;
				} else if (typeReserv.equals("2") && donnees) {
					int choixJour = MethodesFormatClavierInterface.entreeEntier(
							"Jour de la semaine : (1 = Lundi, 2 = Mardi, 3 = Mercredi, 4 = Jeudi, 5 = Vendredi, 6 = Samedi, 7 = Dimanche)");
					if (choixJour >= 0 && choixJour <= 6) {
						reservation.setJourSemaine(choixJour);
						reservation.setType("hebdomadaire");
						format = true;
					}
				} else if (typeReserv.equals("3") && donnees) {
					int choixMois = MethodesFormatClavierInterface.entreeEntier("Jour du mois : (1-28)");
					if (choixMois >= 0 && choixMois <= 28) {
						reservation.setJourMois(choixMois);
						reservation.setType("mensuelle");
						format = true;
					}
				}
				if (donnees && format) {
					ReservationPermanente reservContigue = ReservationPermanenteMysql.getInstance()
							.reservationContigue(reservation);
					if (reservContigue != null) {
						boolean finFusion = false;
						while (!finFusion) {
							System.out.println(
									"La réservation permanente que vous souhaitez ajouter est contigue avec une autre, vous devez les fusionnez ou en supprimer une.");
							System.out.println(MESSAGE_QUE_FAIRE
									+ "\n1 - Fusionner les deux réservations\n 2 - Annuler l'insertion");
							String choixFusion = sc.nextLine();
							switch (choixFusion) {
							case "1":
								finFusion = true;
								finAjout = true;
								ReservationPermanenteMysql.getInstance().fusionDeuxReservationsContigue(reservContigue,
										reservation);
								System.out.println("Fusion des deux réservations.");
								break;
							case "2":
								finFusion = true;
								finAjout = true;
								System.out.println("Annulation de l'ajout.");
								break;
							default:
								System.out.println(MESSAGE_ERREUR);
							}
						}
					} else {
						ReservationPermanenteMysql.getInstance().ajoutReservationPermanenteAuto(reservation);
						finAjout = true;
						System.out.println("Votre réservation permanente à bien été ajoutée.");
					}
				}
			}

		} else {
			System.out.println(MESSAGE_ERREUR);
		}
	}
}