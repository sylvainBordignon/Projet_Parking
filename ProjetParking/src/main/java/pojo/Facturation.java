package pojo;

import mysql.GerantMysql;

public class Facturation {

	public int id, idClient, idHistoriqueReservation;

	public float coutNormal = 0, coutDepassement = 0, coutRemboursement = 0, coutProlongationAttente = 0;

	public Facturation(Reservation reservation) {
		setIdClient(reservation.getId_client());
		setIdHistoriqueReservation(reservation.getId());
		setCoutNormal(calculerCoutNormal(reservation));
		setCoutDepassement(calculerCoutDepassement(reservation));
		setCoutProlongationAttente(calculerCoutProlongationAttente(reservation));
	}
	
	//a definir montant remboursement
	public Facturation(Reservation reservation, boolean remboursement) {
		setIdClient(reservation.getId_client());
		setIdHistoriqueReservation(reservation.getId());
		setCoutRemboursement(555555);
	}
	
	public Facturation(int id, int idClient, int idReserv, float coutNormal, float coutDepassement, float coutRemboursement, float coutProlongationAttente) {
		setId(id);
		setIdClient(idClient);
		setIdHistoriqueReservation(idReserv);
		setCoutNormal(coutNormal);
		setCoutDepassement(coutDepassement);
		setCoutRemboursement(coutRemboursement);
		setCoutProlongationAttente(coutProlongationAttente);
	}
	
	//TArif par heure ou minutes ?
	public static float calculerCoutNormal(Reservation reservation) {
		return reservation.getDuree()*GerantMysql.getInstance().selectionnerTarifNormal()/60;
	}
	
	public static float calculerCoutDepassement(Reservation reservation) {
		int tempsDepassement = reservation.calculerTempsDepassement();
		float  coutDepassement = 0;
		float tarifBase = GerantMysql.getInstance().selectionnerTarifDepassement();
		float tarifCumulatif = GerantMysql.getInstance().selectionnerTarifDepassementAugmentation();
		int i = 0;
		int res = tempsDepassement/60;
		for(i = 0; i < res; i++) {
			coutDepassement += tarifBase+(i*tarifCumulatif);
		}
		int tempsRestant = tempsDepassement-res*60;
		if(tempsRestant > 0) {
			coutDepassement += tempsRestant*(tarifBase+res*tarifCumulatif)/60;
		}
		return coutDepassement;
}
	
	public static float calculerCoutProlongationAttente(Reservation reservation) {
		return (reservation.getDelai_attente()-30)*GerantMysql.getInstance().selectionnerTarifProlongationAttente()/60;
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

	public int getIdHistoriqueReservation() {
		return idHistoriqueReservation;
	}

	public void setIdHistoriqueReservation(int idHistoriqueReservation) {
		this.idHistoriqueReservation = idHistoriqueReservation;
	}

	public float getCoutNormal() {
		return coutNormal;
	}

	public void setCoutNormal(float coutNormal) {
		this.coutNormal = coutNormal;
	}

	public float getCoutDepassement() {
		return coutDepassement;
	}

	public void setCoutDepassement(float coutDepassement) {
		this.coutDepassement = coutDepassement;
	}

	public float getCoutRemboursement() {
		return coutRemboursement;
	}

	public void setCoutRemboursement(float coutRemboursement) {
		this.coutRemboursement = coutRemboursement;
	}

	public float getCoutProlongationAttente() {
		return coutProlongationAttente;
	}

	public void setCoutProlongationAttente(float coutProlongationAttente) {
		this.coutProlongationAttente = coutProlongationAttente;
	}

	@Override
	public String toString() {
		return "Facturation [id=" + id + ", idClient=" + idClient + ", idHistoriqueReservation="
				+ idHistoriqueReservation + ", coutNormal=" + coutNormal + ", coutDepassement=" + coutDepassement
				+ ", coutRemboursement=" + coutRemboursement + ", coutProlongationAttente=" + coutProlongationAttente
				+ "]";
	}
}
