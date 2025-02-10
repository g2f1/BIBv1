package controllers.bookUI;

import java.sql.Connection;

import controllers.homeUI.HomeCon;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import models.livre.Livre;
import views.book.bookUI;
import views.emprunt.empruntUI;
import views.home.homeUI;
import utils.dbconnection.DatabaseConnection;
import utils.dao.impl.*;
import utils.dao.interfaces.*;
public class BookCon {
	public static void setButtons(Stage s) {

		bookUI.searchField.textProperty().addListener((obs, oldText, newText) -> {
			bookUI.filteredBooks.setPredicate(livre -> {
				if (newText == null || newText.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newText.toLowerCase();
				return livre.getTitre().toLowerCase().contains(lowerCaseFilter)||livre.getAuteur().toLowerCase().contains(lowerCaseFilter);
			});
		});

		bookUI.tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				bookUI.tf1.setText(newSelection.getTitre());
				bookUI.tf2.setText(newSelection.getAuteur());
				bookUI.tf3.setText(String.valueOf(newSelection.getISBN()));
				bookUI.tf4.setText(newSelection.getCategorie());
				bookUI.slider.setValue(newSelection.getNbExemplaire());
				bookUI.btnModify.setDisable(false);bookUI.btnDelete.setDisable(false);
				bookUI.btnAdd.setDisable(true);
			}
		});

		bookUI.homeMenu.setOnAction(e->{s.setScene(homeUI.createScene(s));bookUI.tf1.clear();
		bookUI.tf2.clear();
		bookUI.tf3.clear();
		bookUI.tf4.clear();
		bookUI.slider.setValue(0);
		bookUI.tableView.getSelectionModel().clearSelection();});

		bookUI.btnAdd.setOnAction(e -> {
			String Titre = bookUI.tf1.getText();
			String Auteur = bookUI.tf2.getText();
			String ISBN = bookUI.tf3.getText();
			String Categorie = bookUI.tf4.getText();
			String nb = bookUI.tf.getText();
			if (!Titre.isEmpty() && !Auteur.isEmpty() && !ISBN.isEmpty() && !nb.isEmpty()) {
				Livre L=new Livre(Titre, Auteur,Integer.parseInt(bookUI.tf3.getText()),Categorie,Integer.parseInt(bookUI.tf.getText()));
				if(bookUI.livres.indexOf(L)==-1) {
				bookUI.livres.add(L);Connection Conn = DatabaseConnection.getInstance().getConnection();LivreDAO L_DAO=new LivreDAOImpl();L_DAO.enregistrerLivre(L, Conn);}
				else {HomeCon.showWarningDialog("Livre avec cet ISBN deja existant");}
				
				//database updates 
				bookUI.tf1.clear();
				bookUI.tf2.clear();
				bookUI.tf3.clear();
				bookUI.tf4.clear();
				bookUI.slider.setValue(0);
			}
		});

		bookUI.btnModify.setOnAction(e -> {
			Livre selectedbook = bookUI.createTableView().getSelectionModel().getSelectedItem();
			if (selectedbook != null) {
				if (!bookUI.tf1.getText().isEmpty() && !bookUI.tf2.getText().isEmpty() && !bookUI.tf3.getText().isEmpty() && !bookUI.tf4.getText().isEmpty()) {
				selectedbook.setTitre(bookUI.tf1.getText());
				selectedbook.setAuteur(bookUI.tf2.getText());
				try {
				selectedbook.setISBN(Integer.parseInt(bookUI.tf3.getText()));}
				catch(Exception exception) {HomeCon.showWarningDialog("L'ISBN doit etre un entier");}
				selectedbook.setCategorie(bookUI.tf4.getText());
				selectedbook.setNbExemplaire(Integer.parseInt(bookUI.tf.getText()));Connection Conn = DatabaseConnection.getInstance().getConnection();LivreDAO L_DAO=new LivreDAOImpl();L_DAO.modifierLivre(Conn, selectedbook);}}
				bookUI.tf1.clear();
				bookUI.tf2.clear();
				bookUI.tf3.clear();
				bookUI.tf4.clear();
				bookUI.slider.setValue(0);
				bookUI.tableView.getSelectionModel().clearSelection();
				bookUI.btnModify.setDisable(true);bookUI.btnDelete.setDisable(true);
				bookUI.btnAdd.setDisable(false);
				
				//database updates 
				bookUI.createTableView().refresh(); // Refresh table view to reflect changes
		});

		bookUI.btnDelete.setOnAction(e -> {
			Livre selectedbook = bookUI.createTableView().getSelectionModel().getSelectedItem();
			if (selectedbook != null) {
				bookUI.livres.remove(selectedbook);
				Connection Conn = DatabaseConnection.getInstance().getConnection();LivreDAO L_DAO=new LivreDAOImpl();L_DAO.supprimerUnLivre(Conn, selectedbook.getISBN());}
				//updates data base 
			bookUI.tf1.clear();
			bookUI.tf2.clear();
			bookUI.tf3.clear();
			bookUI.tf4.clear();
			bookUI.slider.setValue(0);
			bookUI.tableView.getSelectionModel().clearSelection();
			bookUI.btnModify.setDisable(true);bookUI.btnDelete.setDisable(true);
			bookUI.btnAdd.setDisable(false);
		});
		
		bookUI.btnClear.setOnAction(e -> {
			bookUI.btnAdd.setDisable(false);
			bookUI.btnModify.setDisable(true);
			bookUI.btnDelete.setDisable(true);
			bookUI.tf1.clear();
			bookUI.tf2.clear();
			bookUI.tf3.clear();
			bookUI.tf4.clear();
			bookUI.slider.setValue(0);
			bookUI.tableView.getSelectionModel().clearSelection();

		});
	}
}