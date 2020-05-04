package pojo;

public class Client {
	private int id;
	private String nom, prenom, adresse, mail, IBAN, numeroMobile;

	public Client(String numeroMobile, String nom, String prenom, String adresse, String mail, String IBAN) {

		this.numeroMobile = numeroMobile;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.mail = mail;
		this.IBAN = IBAN;
	}

	public Client(int id, String numeroMobile, String nom, String prenom, String adresse, String mail, String IBAN) {
		this.id = id;
		this.numeroMobile = numeroMobile;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.mail = mail;
		this.IBAN = IBAN;
	}

	@Override
	public String toString() {
		return "Client [numeroMobile=" + numeroMobile + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse
				+ ", mail=" + mail + ", IBAN=" + IBAN + "]";
	}

	public String getNumeroMobile() {
		return numeroMobile;
	}

	public void setNumeroMobile(String numeroMobile) {
		this.numeroMobile = numeroMobile;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
