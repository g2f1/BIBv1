package controllers.empruntUI;
import services.EmprunterLivre;

import services.LivreParIsbn;
import java.lang.classfile.instruction.SwitchCase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.*;
import controllers.homeUI.HomeCon;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.livre.Livre;
import models.utilisateur.Utilisateur;
import models.emprunt.Emprunt;
import views.user.userUI;
import views.home.homeUI;
import views.book.bookUI;
import views.emprunt.empruntUI;
import utils.dbconnection.DatabaseConnection;
import utils.dao.impl.*;
import utils.dao.interfaces.*;
public class EmpruntCon {
	public static int cmp=0;
	public static void setButtons(Stage s) {

		empruntUI.searchField.textProperty().addListener((obs, oldText, newText) -> {
			empruntUI.filteredemprunts.setPredicate(emprunt -> {
				if (newText == null || newText.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newText.toLowerCase();
				return emprunt.getCIN().toLowerCase().contains(lowerCaseFilter)||String.valueOf(emprunt.getISBN()).contains(lowerCaseFilter);
			});
		});

		empruntUI.choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

			empruntUI.filteredemprunts.setPredicate(emprunt -> {
				if ("Tous".equals(newVal)) {
					return true;
				}
				else if("Retourné".equals(newVal)) {return emprunt.getEtat().equals("R");}
				else {return emprunt.getEtat().equals("E");}
			});
		});

		empruntUI.tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				empruntUI.btnAdd.setDisable(true);
				empruntUI.btnModify.setDisable(false);
				empruntUI.btnDelete.setDisable(false);
				empruntUI.tf1.setText(newSelection.getCIN());
				empruntUI.tf2.setText(String.valueOf(newSelection.getISBN()));
				empruntUI.tf1.setDisable(true);
				empruntUI.tf2.setDisable(true);
				empruntUI.choiceBox1.setDisable(false);
				switch (newSelection.getEtat()) {
				case "R":
					empruntUI.choiceBox1.setValue(new Pair<>("Retourné",newSelection.getEtat()));
					break;

				default:
					empruntUI.choiceBox1.setValue(new Pair<>("En cours",newSelection.getEtat()));
					break;
				}}

		});

		empruntUI.homeMenu.setOnAction(e-> {s.setScene(homeUI.createScene(s));empruntUI.tf1.clear();
		empruntUI.tf2.clear();
		empruntUI.tableView.getSelectionModel().clearSelection();});

		empruntUI.btnAdd.setOnAction(e -> {
			cmp++;
			int index=0;
			if(cmp==1) {
				if(!empruntUI.emprunts.isEmpty()) {index=empruntUI.emprunts.getLast().getId();Emprunt.setInitialId(index);}}
			String cin = empruntUI.tf1.getText();
			String isbn = empruntUI.tf2.getText();
			String etat = "E";
			if (!cin.isEmpty() && !isbn.isEmpty() && !etat.isEmpty()) {
				int i = EmprunterLivre.emprunterLivre();
				if(i==1) {
					try
					{
						empruntUI.emprunts.add(new Emprunt(Integer.parseInt(isbn), cin, etat));Connection conn = DatabaseConnection.getInstance().getConnection();
						EmpruntDAO E_DAO = new EmpruntDAOImpl();
						E_DAO.enregistrerEmprunt(empruntUI.emprunts.getLast(), conn);
						}
					catch(Exception eee) {HomeCon.showWarningDialog("Une erreur est survenu lors de l'emprunt du livre");}
				}
				//database updates 
				empruntUI.tf1.clear();
				empruntUI.tf2.clear();
			}
			bookUI.tableView.refresh();
		});

		empruntUI.btnModify.setOnAction(e -> 
		{Emprunt selectedemprunt = empruntUI.tableView.getSelectionModel().getSelectedItem();
		if (selectedemprunt != null) {
			empruntUI.btnModify.setDisable(true);
			empruntUI.btnDelete.setDisable(true);
			empruntUI.btnAdd.setDisable(false);
			empruntUI.tf1.setDisable(false);
			empruntUI.tf2.setDisable(false);
			if(selectedemprunt.getEtat().equals(empruntUI.choiceBox1.getValue().getValue())) {HomeCon.showWarningDialog("Aucun changement n'est effectué");}
			else {
				selectedemprunt.setEtat(empruntUI.choiceBox1.getValue().getValue());
				Livre L = LivreParIsbn.livreParIsbn(bookUI.livres,Integer.parseInt(empruntUI.tf2.getText())) ;
				if(empruntUI.choiceBox1.getValue().getValue().equals("E")) {L.NbExemplaire--;selectedemprunt.setDateRetour(LocalDateTime.of(9999,12,31,12,59));}
				else {L.NbExemplaire++;selectedemprunt.setDateRetour(LocalDateTime.now());}
				Connection conn = DatabaseConnection.getInstance().getConnection();
				EmpruntDAO E_DAO = new EmpruntDAOImpl();
				LivreDAO L_DAO=new LivreDAOImpl();
				L_DAO.modifierLivre(conn, L);
				E_DAO.modifierEmprunt(conn, selectedemprunt);
			}
			empruntUI.tf1.clear();
			empruntUI.tf2.clear();
			empruntUI.tableView.getSelectionModel().clearSelection();
  
			//database updates 
			empruntUI.tableView.refresh(); 
			bookUI.tableView.refresh();
		}
		});

		empruntUI.btnDelete.setOnAction(e -> {
			empruntUI.btnModify.setDisable(true);
			empruntUI.btnDelete.setDisable(true);
			empruntUI.btnAdd.setDisable(false);
			empruntUI.tf1.setDisable(false);
			empruntUI.tf2.setDisable(false);
			Emprunt selectedemprunt = empruntUI.tableView.getSelectionModel().getSelectedItem();
			if (selectedemprunt != null) {
				Livre L = LivreParIsbn.livreParIsbn(bookUI.livres,selectedemprunt.getISBN());
				if(selectedemprunt.getEtat().equals("E")) {L.NbExemplaire++;}
				else {L.NbExemplaire--;}
				Connection conn = DatabaseConnection.getInstance().getConnection();
				EmpruntDAO E_DAO = new EmpruntDAOImpl();
				E_DAO.supprimerUnEmprunt(conn, selectedemprunt.getId());
				empruntUI.emprunts.remove(selectedemprunt);
				empruntUI.tf1.clear();
				empruntUI.tf2.clear();
				empruntUI.tableView.getSelectionModel().clearSelection();
				bookUI.tableView.refresh();
				//database updates
			}
		});

		empruntUI.btnClear.setOnAction(e -> {
			empruntUI.btnAdd.setDisable(false);
			empruntUI.btnModify.setDisable(true);
			empruntUI.btnDelete.setDisable(true);
			empruntUI.tf1.clear();
			empruntUI.tf2.clear();
			empruntUI.tableView.getSelectionModel().clearSelection();
			empruntUI.tf1.setDisable(false);
			empruntUI.tf2.setDisable(false);

		});
	}
}
