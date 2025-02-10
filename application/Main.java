package application;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.emprunt.Emprunt;
import views.book.bookUI;
import views.home.homeUI;
import views.user.userUI;
import views.emprunt.empruntUI;
import controllers.bookUI.BookCon;
import controllers.homeUI.HomeCon;

public class Main extends Application {
	public static Scene sceneUser;
	public static Scene sceneBook;
	public static Scene sceneEmprunt;
	public static Scene sceneHome;
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("BibV1");
		sceneUser=userUI.createScene(primaryStage);
		sceneBook=bookUI.createScene(primaryStage);
		sceneEmprunt=empruntUI.createScene(primaryStage);
		sceneHome=homeUI.createScene(primaryStage);
		primaryStage.setScene(sceneHome);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

