package pojo;

import java.sql.Time;

public class ReservationPermanente {
	
	private int id, idClient, duree;
	
	private Integer jourSemaine, jourMois;
	
	private String type;
	
	private Time heureDebut;
	
	public ReservationPermanente(int id, int idCli, String type, Time heureDebut, int duree, Integer jourSemaine, Integer jourMois) {
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

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
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
		this.jourSemaine = jourSemaine;
	}

	public Integer getJourMois() {
		return jourMois;
	}

	public void setJourMois(Integer jourMois) {
		this.jourMois = jourMois;
	}
}
