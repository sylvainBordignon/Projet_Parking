package pojo;

import java.sql.Time;

public class ReservationPermanente {
	
	private int id, idClient;
	
	private Integer jourSemaine = null, jourMois = null;
	
	private String type;
	
	private Time heureDebut, duree;
	
	public ReservationPermanente(int idCli, String type, Time heureDebut, Time duree) {
		setIdClient(idCli);
		setType(type);
		setHeureDebut(heureDebut);
		setDuree(duree);
	}
	
	public ReservationPermanente(int idCli, String type, Time heureDebut, Time duree, int jourSemaine) {
		setIdClient(idCli);
		setType(type);
		setHeureDebut(heureDebut);
		setDuree(duree);
		setJourSemaine(jourSemaine);
	}
	
	public ReservationPermanente(int idCli, String type, Time heureDebut, Time duree, Integer jourSemaine, Integer jourMois) {
		setIdClient(idCli);
		setType(type);
		setHeureDebut(heureDebut);
		setDuree(duree);
		setJourSemaine(jourSemaine);
		setJourMois(jourMois);
	}
	
	public ReservationPermanente(int id, int idCli, String type, Time heureDebut, Time duree, Integer jourSemaine) {
		setId(id);
		setIdClient(idCli);
		setType(type);
		setHeureDebut(heureDebut);
		setDuree(duree);
		setJourSemaine(jourSemaine);
	}
	
	public ReservationPermanente(int id, int idCli, String type, Time heureDebut, Time duree, Integer jourSemaine, Integer jourMois) {
		setId(id);
		setIdClient(idCli);
		setType(type);
		setHeureDebut(heureDebut);
		setDuree(duree);
		setJourSemaine(jourSemaine);
		setJourMois(jourMois);
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

	public Time getDuree() {
		return duree;
	}

	public void setDuree(Time duree) {
		this.duree = duree;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Time getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(Time heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Integer getJourSemaine() {
		return jourSemaine;
	}

	public void setJourSemaine(Integer jourSemaine) {
		if(jourSemaine != 0)
			this.jourSemaine = jourSemaine;
	}

	public Integer getJourMois() {
		return jourMois;
	}

	public void setJourMois(Integer jourMois) {
		if(jourMois != 0)
			this.jourMois = jourMois;
	}

	@Override
	public String toString() {
		return "ReservationPermanente [id=" + id + ", idClient=" + idClient + ", duree=" + duree + ", jourSemaine="
				+ jourSemaine + ", jourMois=" + jourMois + ", type=" + type + ", heureDebut=" + heureDebut + "]";
	}
}
