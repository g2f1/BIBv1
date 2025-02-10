package controllers.userUI;

import java.sql.Connection;
import java.time.LocalDate;

import controllers.homeUI.HomeCon;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import models.livre.Livre;
import models.utilisateur.Utilisateur;
import views.user.userUI;
import views.book.bookUI;
import views.home.homeUI;
import utils.dbconnection.DatabaseConnection;
import utils.dao.impl.*;
import utils.dao.interfaces.*;
public class UserCon {
	public static void setButtons(Stage s) {
		userUI.searchField.textProperty().addListener((obs, oldText, newText) -> {
			userUI.filteredusers.setPredicate(utilisateur -> {
				if (newText == null || newText.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newText.toLowerCase();
				return utilisateur.getNom().toLowerCase().contains(lowerCaseFilter)||utilisateur.getCIN().toLowerCase().contains(lowerCaseFilter)||utilisateur.getPrenom().toLowerCase().contains(lowerCaseFilter);
			});
		});

		userUI.tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			userUI.btnAdd.setDisable(true);
			userUI.btnModify.setDisable(false);
			userUI.btnDelete.setDisable(false);
			if (newSelection != null) {
				userUI.tf1.setText(newSelection.getNom());
				userUI.tf2.setText(newSelection.getPrenom());
				userUI.tf3.setText(newSelection.getCIN());
				userUI.tf4.setValue(newSelection.getDateDeNaissance());
			}

		});

		userUI.homeMenu.setOnAction(e-> {s.setScene(homeUI.createScene(s));userUI.tf1.clear();
		userUI.tf2.clear();
		userUI.tf3.clear();
		userUI.tf4.getEditor().clear();
		userUI.tableView.getSelectionModel().clearSelection();});

		userUI.btnAdd.setOnAction(e -> {
			String nom = userUI.tf1.getText();
			String prenom = userUI.tf2.getText();
			String cin = userUI.tf3.getText();
			LocalDate date = userUI.tf4.getValue();
			if (!nom.isEmpty() && !prenom.isEmpty() && !cin.isEmpty() && date!=null) {
				Utilisateur U=new Utilisateur(nom, prenom, cin, date);
				if(userUI.users.indexOf(U)==-1) {
				userUI.users.add(U);Connection Conn = DatabaseConnection.getInstance().getConnection();UtilisateurDAO U_DAO=new UtilisateurDAOImpl();U_DAO.enregistrerUtilisateur(U, Conn);}
				else {HomeCon.showWarningDialog("Utilisateur avec ce CIN deja existant");}
				 
				userUI.tf1.clear();
				userUI.tf2.clear();
				userUI.tf3.clear();
				userUI.tf4.getEditor().clear();
			}
		});

		userUI.btnModify.setOnAction(e -> {
			Utilisateur selecteduser = userUI.tableView.getSelectionModel().getSelectedItem();
			if (selecteduser != null) {
				if (!userUI.tf1.getText().isEmpty() && !userUI.tf1.getText().isEmpty() && !userUI.tf1.getText().isEmpty() && userUI.tf4.getEditor().getText().isEmpty()) {
				selecteduser.setNom(userUI.tf1.getText());
				selecteduser.setPrenom(userUI.tf2.getText());
				selecteduser.setCIN(userUI.tf3.getText());
				selecteduser.setDateDeNaissance(userUI.tf4.getValue());
				Connection Conn = DatabaseConnection.getInstance().getConnection();UtilisateurDAO U_DAO=new UtilisateurDAOImpl();U_DAO.modifierUtilisateur(Conn, selecteduser);
				//database updates 
				userUI.tableView.refresh(); // Refresh table view to reflect changes
			}
			userUI.btnAdd.setDisable(false);
			userUI.btnModify.setDisable(true);
			userUI.btnDelete.setDisable(true);
			userUI.tf1.clear();
			userUI.tf2.clear();
			userUI.tf3.clear();
			userUI.tf4.getEditor().clear();
			userUI.tableView.getSelectionModel().clearSelection();
			}});

		userUI.btnDelete.setOnAction(e -> {
			Utilisateur selecteduser = userUI.tableView.getSelectionModel().getSelectedItem();
			if (selecteduser != null) {
				userUI.users.remove(selecteduser);
				Connection Conn = DatabaseConnection.getInstance().getConnection();UtilisateurDAO U_DAO=new UtilisateurDAOImpl();U_DAO.supprimerUnUtilisateur(Conn, selecteduser.getCIN());
				//database updates
			}
			userUI.btnAdd.setDisable(false);
			userUI.btnModify.setDisable(true);
			userUI.btnDelete.setDisable(true);
			userUI.tf1.clear();
			userUI.tf2.clear();
			userUI.tf3.clear();
			userUI.tf4.getEditor().clear();
			userUI.tableView.getSelectionModel().clearSelection();
		});
		
		userUI.btnClear.setOnAction(e -> {
			userUI.btnAdd.setDisable(false);
			userUI.btnModify.setDisable(true);
			userUI.btnDelete.setDisable(true);
			userUI.tf1.clear();
			userUI.tf2.clear();
			userUI.tf3.clear();
			userUI.tf4.getEditor().clear();
			userUI.tableView.getSelectionModel().clearSelection();

		});
	}
}
