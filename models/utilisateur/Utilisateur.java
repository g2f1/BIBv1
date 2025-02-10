package models.utilisateur;
import java.time.*;
public class Utilisateur {
	//Attributes
	private String Nom;
	private String Prenom;
	private String CIN;
	private LocalDate DateDeNaissance;

	//Constructeur avec paramètre
	public Utilisateur(String Nom, String Prenom, String CIN, LocalDate DateDeNaissance) {
		this.Nom = Nom;
		this.Prenom = Prenom;
		this.CIN = CIN;
		this.DateDeNaissance = DateDeNaissance;
	}

	//Constructeur sans paramètre
	public Utilisateur() {
		this.Nom = "undefined";
		this.Prenom = "undefined";
		this.CIN = "undefined";
		this.DateDeNaissance = LocalDate.of(9999, 1, 1);
	}

	// getters
	public String getNom() {
		return Nom;
	}

	public String getPrenom() {
		return Prenom;
	}

	public String getCIN() {
		return CIN;
	}

	public LocalDate getDateDeNaissance() {
		return DateDeNaissance;
	}

	// setters
	public void setNom(String Nom) {
		this.Nom = Nom;
	}

	public void setPrenom(String Prenom) {
		this.Prenom = Prenom;
	}

	public void setCIN(String CIN) {
		this.CIN = CIN;
	}

	public void setDateDeNaissance(LocalDate DateDeNaissance) {
		this.DateDeNaissance = DateDeNaissance;
	}

	//Afficher les informations de l'utilisateur
	public void printUtilisateur() {
		System.out.println("Nom : " + getNom());
		System.out.println("Prénom : " + getPrenom());
		System.out.println("CIN : " + getCIN());
		System.out.println("Date de naissance : " + getDateDeNaissance());
	}

	public String fileUtilisateur() {
		return this.Nom+","+this.Prenom+","+this.CIN+","+this.DateDeNaissance;
	}
}

