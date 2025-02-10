package services;

import java.sql.Connection;
import java.sql.SQLException;

import models.livre.Livre;
import models.utilisateur.Utilisateur;
import utils.dao.impl.LivreDAOImpl;
import utils.dao.interfaces.LivreDAO;
import utils.dbconnection.DatabaseConnection;
import models.emprunt.Emprunt;
import views.book.bookUI;
import views.emprunt.empruntUI;
import views.home.homeUI;
import views.user.userUI;
import controllers.homeUI.HomeCon;
public class EmprunterLivre {
	public static int index=0;
	public static int emprunterLivre() {
	    try {
	        // Validate if the second field is a valid integer
	        Integer.parseInt(empruntUI.tf2.getText());
	    } catch (NumberFormatException nf) {
	        HomeCon.showWarningDialog("L'ISBN du livre doit être un entier");
	        return 2;
	    }

	    // Flags to check if user and book exist
	    boolean userFound = false, bookFound = false;
	    int index = -1;

	    // Check if the user exists
	    for (Utilisateur u : userUI.users) {
	        if (empruntUI.tf1.getText().equalsIgnoreCase(u.getCIN())) {
	            userFound = true;
	            break;
	        }
	    }

	    // Check if the book exists
	    for (Livre l : bookUI.livres) {
	        if (empruntUI.tf2.getText().equalsIgnoreCase(String.valueOf(l.getISBN()))) {
	            bookFound = true;
	            index = bookUI.livres.indexOf(l);
	            break;
	        }
	    }

	    // Handle different cases
	    if (!userFound && !bookFound) {
	        HomeCon.showWarningDialog("Utilisateur et Livre introuvables");
	        return 2;
	    } else if (!userFound) {
	        HomeCon.showWarningDialog("Utilisateur introuvable");
	        return 2;
	    } else if (!bookFound) {
	        HomeCon.showWarningDialog("Livre introuvable");
	        return 2;
	    }

	    // Both user and book are found, proceed with the operation
	    if(bookUI.livres.get(index).NbExemplaire>0) {
	    bookUI.livres.get(index).NbExemplaire--;Connection Conn = DatabaseConnection.getInstance().getConnection();LivreDAO L_DAO=new LivreDAOImpl();L_DAO.modifierLivre(Conn, bookUI.livres.get(index));return 1;}else {HomeCon.showWarningDialog("Pas d'exemplaire pour ce livre");return 2;}
	}}
