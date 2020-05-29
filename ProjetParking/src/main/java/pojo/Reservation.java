package pojo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import methodes.MethodesCalculs;

public class Reservation {
	private int id, idClient, idPlace, delaiAttente, duree;

	private Timestamp dateDebut, dateFin, dateArriveReel, dateDepartReel;
	
	private static final Logger logger = Logger.getLogger(Reservation.class.getName());

	public Reservation(int id, int idCli, Timestamp dateDebut, Timestamp dateFin, int duree, Timestamp dateArriveReel,
			Timestamp dateDepartReel, int idPlace, int delaiAttente) {
		setId(id);
		setIdClient(idCli);
		setDateDebut(dateDebut);
		setDateFin(dateFin);
		setDuree(duree);
		setDateArriveReel(dateArriveReel);
		setDateDepartReel(dateDepartReel);
		setIdPlace(idPlace);
		setDelaiAttente(delaiAttente);
	}

	public Reservation(int idCli, String dateDebut, int heures, int minutes) {
		try {
			setIdClient(idCli);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			Date parsedDate = dateFormat.parse(dateDebut);
			setDateDebut(new Timestamp(parsedDate.getTime()));
			setDuree(heures * 60 + minutes);
			long millis = parsedDate.getTime() + (getDuree() * 60000);
			setDateFin(new Timestamp(millis));
		} catch (ParseException e) {
			logger.severe(e.getMessage());
		}

	}

	public Reservation(int idCli, String dateDebut, String dateFin, int duree, int place) {
		try {
			setIdClient(idCli);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parsedDate = dateFormat.parse(dateDebut);
			setDateDebut(new Timestamp(parsedDate.getTime()));
			parsedDate = dateFormat.parse(dateFin);
			setDateFin(new Timestamp(parsedDate.getTime()));
			setDuree(duree);
			setIdPlace(place);
		} catch (ParseException e) {
			logger.severe(e.getMessage());
		}

	}

	public Reservation(int idCli, Timestamp dateDebut, int duree, int place, Timestamp dateArriveReel) {
		setIdClient(idCli);
		setDateDebut(dateDebut);
		setDateFin(new Timestamp(dateDebut.getTime() + TimeUnit.MINUTES.toMillis(duree)));
		setDuree(duree);
		setIdPlace(place);
		setDateArriveReel(dateArriveReel);
	}

	public void modifierDuree(String duree) {
		MethodesCalculs methodescalculs = new MethodesCalculs();
		int dureeMinute = methodescalculs.conversionHeureMinuteEnMinute(duree);
		long millis = dateDebut.getTime() + ((dureeMinute) * 60000);
		Timestamp nouvelledateFin = new Timestamp(millis);
		int nouvelleDuree = dureeMinute;
		this.setDateFin(nouvelledateFin);
		this.setDuree(nouvelleDuree);
	}

	public void modifierDateDebut(String dateDebut) {
		try {
			long dureeCourante = this.getDuree() * 60000;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parsedDate = dateFormat.parse(dateDebut);
			Timestamp nouvelleDateDebut = new Timestamp(parsedDate.getTime());
			Timestamp nouvelleDateFin = new Timestamp(parsedDate.getTime() + dureeCourante);
			this.setDateDebut(nouvelleDateDebut);
			this.setDateFin(nouvelleDateFin);
		} catch (ParseException e) {
			logger.severe(e.getMessage());
		}
	}

	public int calculerTempsDepassement() {
		int res = (int) ((dateDepartReel.getTime() - dateFin.getTime()) / 1000 / 60);
		if (res > 0) {
			return res;
		}
		return 0;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public int getIdPlace() {
		return idPlace;
	}

	public void setIdPlace(int idPlace) {
		this.idPlace = idPlace;
	}

	public int getDelaiAttente() {
		return delaiAttente;
	}

	public void setDelaiAttente(int delaiAttente) {
		this.delaiAttente = delaiAttente;
	}

	public Timestamp getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Timestamp dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Timestamp getDateFin() {
		return dateFin;
	}

	public void setDateFin(Timestamp dateFin) {
		this.dateFin = dateFin;
	}

	public Timestamp getDateArriveReel() {
		return dateArriveReel;
	}

	public void setDateArriveReel(Timestamp dateArriveReel) {
		this.dateArriveReel = dateArriveReel;
	}

	public Timestamp getDateDepartReel() {
		return dateDepartReel;
	}

	public void setDateDepartReel(Timestamp dateDepartReel) {
		this.dateDepartReel = dateDepartReel;
	}

	public String afficherInfo() {
		long dureeMillis = dateFin.getTime() - dateDebut.getTime();
		long minutes = dureeMillis / (60 * 1000) % 60;
		long heures = dureeMillis / (60 * 60 * 1000);
		return "ID: " + id + ", ID client : "+idClient+", ID place : "+idPlace+", Date début: " + dateDebut + ", Duree: " + heures + " heures et " + minutes
				+ " minutes, date arrivé réél : " + dateArriveReel + ", date départ réel : " + dateDepartReel
				+ ", délai attente : " + delaiAttente;
	}

	@Override
	public String toString() {
		long dureeMillis = dateFin.getTime() - dateDebut.getTime();
		long minutes = dureeMillis / (60 * 1000) % 60;
		long heures = dureeMillis / (60 * 60 * 1000);
		return "ID: " + id + ", Date début: " + dateDebut + ", Duree: " + heures + " heures et " + minutes
				+ " minutes";
	}
}
