package models.emprunt;
import java.time.*;
import java.time.format.DateTimeFormatter;
public class Emprunt {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	//Attributes
	private static int idIncrement=0;
	private int id;
	private String CIN;
	private int ISBN;
	private LocalDateTime DateEmprunt;
	private LocalDateTime DateRetour;
	private String etat;//R pour retourné et E pour encore en emprunt

	//getter & setters
	public String getDateEmprunt() {
		return DateEmprunt.format(formatter);
	}
	public void setDateEmprunt(LocalDateTime dateEmprunt) {
		DateEmprunt = dateEmprunt;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	public String getDateRetour() {
		return DateRetour.format(formatter);
	}
	public void setDateRetour(LocalDateTime dateRetour) {
		DateRetour = dateRetour;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
    
	public static void setInitialId(int index) {
		idIncrement=index;
	}
	//Constructeurs
	public Emprunt() {
		setId(++idIncrement);
		this.ISBN=0;
		this.CIN="";
		this.DateEmprunt=LocalDateTime.of(9999,12,31,12,59);
		this.DateRetour=LocalDateTime.of(9999,12,31,12,59);
		this.etat="E";
	}

	public Emprunt(int isbn,String cin) {
		setId(++idIncrement);
		this.ISBN=isbn;
		this.CIN=cin;
		this.DateEmprunt=LocalDateTime.now();
		this.DateRetour=LocalDateTime.of(9999,12,31,12,59);
		this.etat="E";
	}
	
	public Emprunt(int isbn,String cin,String E) {
		setId(++idIncrement);
		this.ISBN=isbn;
		this.CIN=cin;
		this.DateEmprunt=LocalDateTime.now();
		this.DateRetour=LocalDateTime.of(9999,12,31,12,59);
		this.etat=E;
	}
	public Emprunt(int id,String cin, int isbn, LocalDateTime de, LocalDateTime dr, String e) {
		setId(id);
		this.ISBN=isbn;
		this.CIN=cin;
		this.DateEmprunt=de;
		this.DateRetour=dr;
		this.etat=e;
		}
	

	//Afficher info emprunt
	public void printEmprunt() {
		System.out.println("id : "+this.id);
		System.out.println("CIN de l'utilisateur : " + this.CIN);
		System.out.println("ISBN du livre : " + this.ISBN);
		System.out.println("Date d'emprunt du livre : " + this.DateEmprunt.format(formatter));
		System.out.println("Date de retour du livre : " + this.DateRetour.format(formatter));
	}

	public String fileEmprunt() {
		return this.id+","+this.CIN+","+this.ISBN+","+this.DateEmprunt.format(formatter)+","+this.DateRetour.format(formatter)+","+this.etat;
	}

}