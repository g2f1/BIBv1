package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import controllers.homeUI.HomeCon;
import utils.dao.impl.EmpruntDAOImpl;
import utils.dao.impl.LivreDAOImpl;
import utils.dao.impl.UtilisateurDAOImpl;
import utils.dao.interfaces.EmpruntDAO;
import utils.dao.interfaces.LivreDAO;
import utils.dao.interfaces.UtilisateurDAO;
import utils.dbconnection.DatabaseConnection;
import models.emprunt.Emprunt;
import models.livre.Livre;
import models.utilisateur.Utilisateur;
public class EnregistrerFichier{
public static void enregistrerDansFichier(File file) {
	Connection conn = DatabaseConnection.getInstance().getConnection();
	EmpruntDAO E_DAO = new EmpruntDAOImpl();
	LivreDAO L_DAO = new LivreDAOImpl();
	UtilisateurDAO U_DAO = new UtilisateurDAOImpl();
	try (FileWriter writer = new FileWriter(file)) {
		writer.write("--- Utilisateurs ---\n");
		for (Utilisateur utilisateur : U_DAO.getTousLesUtilisateurs(conn)) {
			writer.write(utilisateur.fileUtilisateur() + "\n");
		}
		writer.write("\n--- Livres ---\n");
		for (Livre livre : L_DAO.getTousLesLivres(conn)) {
			writer.write(livre.fileLivre() + "\n");
		}
		writer.write("\n--- Emprunts ---\n");
		for (Emprunt emprunt : E_DAO.getTousLesEmprunts(conn)) {
			writer.write(emprunt.fileEmprunt() + "\n");
		}
		HomeCon.showWarningDialog("Données enregistrées avec succés");
	} catch (IOException e) {
		HomeCon.showWarningDialog("Erreur lors de l'enregistrement dans le fichier");
	}
}
}
