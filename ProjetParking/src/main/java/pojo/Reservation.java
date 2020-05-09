package pojo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import methodes.MethodesCalculs;

public class Reservation {
	private int id, id_client, id_place, delai_attente, duree;
	
	private Timestamp date_debut, date_fin, date_arrive_reel, date_depart_reel;
	
	public Reservation(int id, int idCli, Timestamp dateDebut, Timestamp dateFin, int duree,
			Timestamp dateArriveReel, Timestamp dateDepartReel, int idPlace, int delaiAttente) {
		setId(id);
		setId_client(idCli);
		setDate_debut(dateDebut);
		setDate_fin(dateFin);
		setDuree(duree);
		setDate_arrive_reel(dateArriveReel);
		setDate_depart_reel(dateDepartReel);
		setId_place(idPlace);
		setDelai_attente(delaiAttente);
	}

	public Reservation(int idCli, String dateDebut, int heures, int minutes) {
		try {
			setId_client(idCli);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		    Date parsedDate = dateFormat.parse(dateDebut);
			setDate_debut(new Timestamp(parsedDate.getTime()));
			setDuree(heures*60+minutes);
			long millis=parsedDate.getTime()+(getDuree()*60000);
			setDate_fin(new Timestamp(millis));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public Reservation(int idCli, String dateDebut,String dateFin,int duree,int place) {
		try {
			setId_client(idCli);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date parsedDate = dateFormat.parse(dateDebut);
			setDate_debut(new Timestamp(parsedDate.getTime()));
		    parsedDate = dateFormat.parse(dateFin);
			setDate_fin(new Timestamp(parsedDate.getTime()));
			setDuree(duree);
			setId_place(place);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public Reservation(int idCli, Timestamp dateDebut,int duree,int place, Timestamp dateArriveReel) {
			setId_client(idCli);
			setDate_debut(dateDebut);
			setDate_fin(new Timestamp(dateDebut.getTime()+TimeUnit.MINUTES.toMillis(duree)));
			setDuree(duree);
			setId_place(place);
			setDate_arrive_reel(dateArriveReel);
	}	
	
	public void modifierDuree(String duree) {
		MethodesCalculs methodescalculs = new MethodesCalculs();
	    int dureeMinute =	methodescalculs.conversionHeureMinuteEnMinute(duree);
		Date dateDebut = this.getDate_debut();
		long millis = dateDebut.getTime()+((dureeMinute)*60000);
		Timestamp nouvelledateFin=new Timestamp(millis);
		int nouvelleDuree = dureeMinute;
		this.setDate_fin(nouvelledateFin);
		this.setDuree(nouvelleDuree);
	}
	
	public void modifierDateDebut(String dateDebut) {
		try {
			long dureeCourante = this.getDuree()*60000;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		    Date parsedDate = dateFormat.parse(dateDebut);
			Timestamp nouvelleDateDebut = new Timestamp(parsedDate.getTime());
			Timestamp nouvelleDateFin=new Timestamp(parsedDate.getTime()+dureeCourante);
			this.setDate_debut(nouvelleDateDebut);
			this.setDate_fin(nouvelleDateFin);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public int getId_place() {
		return id_place;
	}

	public void setId_place(int id_place) {
		this.id_place = id_place;
	}

	public int getDelai_attente() {
		return delai_attente;
	}

	public void setDelai_attente(int delai_attente) {
		this.delai_attente = delai_attente;
	}

	public Timestamp getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Timestamp date_debut) {
		this.date_debut = date_debut;
	}

	public Timestamp getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Timestamp date_fin) {
		this.date_fin = date_fin;
	}
	
	public Timestamp getDate_arrive_reel() {
		return date_arrive_reel;
	}

	public void setDate_arrive_reel(Timestamp date_arrive_reel) {
		this.date_arrive_reel = date_arrive_reel;
	}

	public Timestamp getDate_depart_reel() {
		return date_depart_reel;
	}

	public void setDate_depart_reel(Timestamp date_depart_reel) {
		this.date_depart_reel = date_depart_reel;
	}

	@Override
	public String toString() {
		Date dateDebut=date_debut;
		Date dateFin=date_fin;
		long dureeMillis=dateFin.getTime()-dateDebut.getTime();
		long minutes = dureeMillis / (60 * 1000) % 60; 
		long heures = dureeMillis / (60 * 60 * 1000);
		return "ID: "+ id + ", Date début: "+ date_debut +", Duree: "+ heures +" heures et "+ minutes +" minutes";
	}
}
