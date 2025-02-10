package controllers.homeUI;
import views.home.homeUI;
import services.LireFichier;
import java.io.File;
import java.io.IOException;
import views.book.bookUI;
import views.emprunt.empruntUI;
import views.user.userUI;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.emprunt.Emprunt;
import models.livre.Livre;
import models.utilisateur.Utilisateur;
import views.book.bookUI;
import views.emprunt.empruntUI;
import services.EnregistrerFichier;
import views.user.userUI;
import controllers.bookUI.BookCon;
public class HomeCon {
	public static String newFilePath;
	public static void showWarningDialog(String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	public static void setButtons(Stage s) {

		homeUI.btnGestionLivres.setOnAction(event -> {bookUI.btnModify.setDisable(true);bookUI.btnDelete.setDisable(true);bookUI.btnAdd.setDisable(false);s.setScene(bookUI.createScene(s));});
		homeUI.btnGestionUtilisateurs.setOnAction(event ->{userUI.btnModify.setDisable(true);userUI.btnDelete.setDisable(true);userUI.btnAdd.setDisable(false);s.setScene(userUI.createScene(s));});
		homeUI.btnGestionEmprunts.setOnAction(event ->{empruntUI.btnDelete.setDisable(true);empruntUI.tf1.setDisable(false);empruntUI.tf2.setDisable(false);
		empruntUI.btnModify.setDisable(true);empruntUI.btnAdd.setDisable(false);empruntUI.choiceBox1.setDisable(true);s.setScene(empruntUI.createScene(s));});
		homeUI.newItem.setOnAction(event -> {
			TextInputDialog nameDialog = new TextInputDialog("newFile.txt");
			nameDialog.setTitle("Le nom du fichier");
			nameDialog.setHeaderText("Entrer le nom du fichier:");
			nameDialog.setContentText("Nom du fichier:");

			nameDialog.showAndWait().ifPresent(fileName -> {
				if (fileName.trim().isEmpty()) {
					showWarningDialog("Impossible du créer le fichier avec un nom vide");
					return;
				}
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setTitle("Choisir le dosier");
				File selectedDirectory = directoryChooser.showDialog(s);

				if (selectedDirectory != null) {
					File newFile = new File(selectedDirectory, fileName);
					try {
						if (newFile.createNewFile()) {
							showWarningDialog("File created successfully: " + newFile.getAbsolutePath());
						} else {
							showWarningDialog("File already exists: " + newFile.getAbsolutePath());
						}
					} catch (IOException e) {
						showWarningDialog("An error occurred while creating the file.");
					}
				} else {
					showWarningDialog("No directory selected. Please try again.");
				}
			});
		});
		homeUI.openItem.setOnAction(event ->{FileChooser FileChooser = new FileChooser();File selectedFile = FileChooser.showOpenDialog(s);if(selectedFile.length()==0) {showWarningDialog("Le Fichier est vide");}else {LireFichier.lireUnFichier(selectedFile);}});
		homeUI.enrItem.setOnAction(event -> {FileChooser FileChooser1 = new FileChooser();File selectedFile1 = FileChooser1.showOpenDialog(s);showWarningDialog(selectedFile1.getAbsolutePath());EnregistrerFichier.enregistrerDansFichier(selectedFile1);});
		homeUI.aideMenuItem.setOnAction(event -> {Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About me");
		alert.setContentText("Made by g2f1\nEmail: xg2f1x@gmail.com\nGitHub:github.com/g2f1\nLinkedin:www.linkedin.com/in/oualid-daouche");
		alert.showAndWait();});
	}
}
