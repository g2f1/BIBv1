package views.home;

import java.util.ArrayList;
import java.util.List;

import controllers.bookUI.BookCon;
import controllers.homeUI.HomeCon;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import views.book.bookUI;
import views.user.userUI;

public class homeUI {
	public static Button btnGestionEmprunts = new Button("Gestion des emprunts");
	public static Button btnGestionUtilisateurs = new Button("Gestion des utilisateurs");
	public static Button btnGestionLivres = new Button("Gestion des livres");
	public static MenuItem newItem = new MenuItem("Nouveau");
	public static MenuItem openItem = new MenuItem("Ouvrir");
	public static MenuItem enrItem = new MenuItem("Enregistrer");
	public static MenuItem aideMenuItem = new MenuItem("About");
	public static Scene scene;
	public static Scene createScene(Stage stage) {
		if(scene==null) {
		Label titleLabel = new Label("Welcome in BIBv1");
		titleLabel.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-padding: 20px;");
		btnGestionEmprunts.setStyle("-fx-font-size: 16px; -fx-pref-width: 500px; -fx-pref-height: 50px;");
		btnGestionUtilisateurs.setStyle("-fx-font-size: 16px; -fx-pref-width: 245px;-fx-pref-height: 50px;");
		btnGestionLivres.setStyle("-fx-font-size: 16px; -fx-pref-width: 245px;-fx-pref-height: 50px;");
		HBox hBoxButtons = new HBox(10, btnGestionUtilisateurs, btnGestionLivres);
        hBoxButtons.setAlignment(Pos.CENTER);
        VBox vBoxLayout = new VBox(20, titleLabel, btnGestionEmprunts, hBoxButtons);
		vBoxLayout.setAlignment(Pos.CENTER);
		vBoxLayout.setStyle("-fx-padding: 20px;");
		Menu fileMenu = new Menu("File");
		Menu aideMenu = new Menu("About");
		fileMenu.getItems().addAll(newItem, openItem, enrItem);
		aideMenu.getItems().addAll(aideMenuItem);
		MenuBar menuBar = new MenuBar(fileMenu, aideMenu);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		borderPane.setCenter(vBoxLayout);
		scene = new Scene(borderPane, 500, 500);
		HomeCon.setButtons(stage);
		}
		return scene;
	}
}
