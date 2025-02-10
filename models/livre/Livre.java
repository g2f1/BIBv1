package models.livre;
public class Livre {
	//Attributes
	private String Titre;
	private String Auteur;
	private int ISBN;
	private String Categorie;
	public int NbExemplaire;

	// Getter pour Titre
	public String getTitre() {
		return Titre;
	}

	// Setter pour Titre
	public void setTitre(String Titre) {
		this.Titre = Titre;
	}

	// Getter pour Auteur
	public String getAuteur() {
		return Auteur;
	}

	// Setter pour Auteur
	public void setAuteur(String Auteur) {
		this.Auteur = Auteur;
	}

	// Getter pour ISBN
	public int getISBN() {
		return ISBN;
	}

	// Setter pour ISBN
	public void setISBN(int ISBN) {
		this.ISBN = ISBN;
	}

	// Getter pour Categorie
	public String getCategorie() {
		return Categorie;
	}

	// Setter pour Categorie
	public void setCategorie(String Categorie) {
		this.Categorie = Categorie;
	}

	// Getter pour NbExemplair
	public int getNbExemplaire() {
		return NbExemplaire;
	}

	// Setter pour NbExemplaire
	public void setNbExemplaire(int NbExemplaire) {
		this.NbExemplaire = NbExemplaire;
	}



	//Constructor 
	public Livre() {
		setTitre("undefined");
		setAuteur("undefined");
		setISBN(0);
		setCategorie("undefined");
		setNbExemplaire(0);
	}

	public Livre(String titre,String auteur,int isbn,String cat,int nb) {
		setTitre(titre);
		setAuteur(auteur);
		setISBN(isbn);
		setCategorie(cat);
		setNbExemplaire(nb);
	}

	//Other Methods
	//printLivreInfo method
	public void printLivreInfo() {
		System.out.println("-----------------------------------------");
		System.out.println("Titre: " + Titre);
		System.out.println("Auteur: " + Auteur);
		System.out.println("ISBN: " + ISBN);
		System.out.println("Catégorie: " + Categorie);
		System.out.println("Nombre d'exemplaires disponibles: " + NbExemplaire);
		System.out.println("-----------------------------------------");
	}

	public String fileLivre() {
		return this.Titre+","+this.Auteur+","+this.ISBN+","+this.Categorie+","+this.NbExemplaire;
	}
}