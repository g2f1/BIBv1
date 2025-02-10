package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import controllers.homeUI.HomeCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.emprunt.Emprunt;
import models.livre.Livre;
import models.utilisateur.Utilisateur;
import views.emprunt.empruntUI;
import views.book.bookUI;
import views.user.userUI;
import utils.dao.impl.EmpruntDAOImpl;
import utils.dao.impl.LivreDAOImpl;
import utils.dao.impl.UtilisateurDAOImpl;
import utils.dao.interfaces.EmpruntDAO;
import utils.dao.interfaces.LivreDAO;
import utils.dao.interfaces.UtilisateurDAO;
import utils.dbconnection.DatabaseConnection;
public class LireFichier {
	
	public static void lireUnFichier(File file) {
		Connection conn = DatabaseConnection.getInstance().getConnection();
		ArrayList<Utilisateur> utilisateurs=new ArrayList();
		ArrayList<Emprunt> emprunts=new ArrayList();
		ArrayList<Livre> livres=new ArrayList();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String ligne;
			String section = "";  // Pour identifier la section (Utilisateurs, Livres, Emprunts)

			while ((ligne = reader.readLine()) != null) {
				ligne = ligne.trim();  // Supprimer les espaces avant et après
				if (ligne.startsWith("---")) {
					// Identifier la section
					if (ligne.equals("--- Utilisateurs ---")) {
						section = "utilisateurs";
					} else if (ligne.equals("--- Livres ---")) {
						section = "livres";
					} else if (ligne.equals("--- Emprunts ---")) {
						section = "emprunts";
					}
				} 
				else {
					String[] parts = ligne.split(",");
					// En fonction de la section, créer les objets appropriés
					switch (section) {
					case "utilisateurs":
						if (!ligne.isEmpty()) {
							Utilisateur utilisateur = new Utilisateur();
							utilisateur.setNom(parts[0]);
							utilisateur.setPrenom(parts[1]);
							utilisateur.setCIN(parts[2]);
							utilisateur.setDateDeNaissance(LocalDate.parse(parts[3], formatter)); // Conversion de la chaîne en LocalDate
							utilisateurs.add(utilisateur);
						}
						break;
					case "livres":
						if (!ligne.isEmpty()) {
							Livre livre = new Livre();
							livre.setTitre(parts[0]);
							livre.setAuteur(parts[1]);
							livre.setISBN(Integer.parseInt(parts[2]));
							livre.setCategorie(parts[3]);
							livre.setNbExemplaire(Integer.parseInt(parts[4]));
							livres.add(livre);}
						break;
					case "emprunts":
						if (!ligne.isEmpty()) {
							Emprunt emprunt = new Emprunt();
							emprunt.setId(Integer.parseInt(parts[0]));
							emprunt.setISBN(Integer.parseInt(parts[2]));
							emprunt.setCIN(parts[1]);
							emprunt.setDateEmprunt(LocalDateTime.parse(parts[3], formatter1));
							emprunt.setDateRetour(LocalDateTime.parse(parts[4], formatter1));
							emprunt.setEtat(parts[5]);
							emprunts.add(emprunt);
						}

						break;
					}
				}
			}
			HomeCon.showWarningDialog("Données chargées avec succés");
			EmpruntDAO E_DAO = new EmpruntDAOImpl();
			LivreDAO L_DAO = new LivreDAOImpl();
			UtilisateurDAO U_DAO = new UtilisateurDAOImpl();
			for(Emprunt emprunt : emprunts) {
				E_DAO.enregistrerEmprunt(emprunt,conn);
			}
			for(Utilisateur user : utilisateurs) {
				U_DAO.enregistrerUtilisateur(user,conn);
			}
			for(Livre livre : livres) {
				L_DAO.enregistrerLivre(livre, conn);
			}
			
			empruntUI.emprunts=FXCollections.observableArrayList(emprunts);
			userUI.users=FXCollections.observableArrayList(utilisateurs);
			bookUI.livres=FXCollections.observableArrayList(livres);
		}
		catch (IOException e) {
			HomeCon.showWarningDialog("Erreur lors de la lecture du fichier");
		}
	}
}
