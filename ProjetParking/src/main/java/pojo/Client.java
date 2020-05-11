package pojo;

public class Client {
	private int id;
	private String nom, prenom, adresse, mail, iban, numeroMobile;

	public Client(String numeroMobile, String nom, String prenom, String adresse, String mail, String iban) {

		this.numeroMobile = numeroMobile;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.mail = mail;
		this.iban = iban;
	}

	public Client(int id, String numeroMobile, String nom, String prenom, String adresse, String mail, String iban) {
		this.id = id;
		this.numeroMobile = numeroMobile;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.mail = mail;
		this.iban = iban;
	}

	@Override
	public String toString() {
		return "Client [numeroMobile=" + numeroMobile + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse
				+ ", mail=" + mail + ", iban=" + iban + "]";
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
}
