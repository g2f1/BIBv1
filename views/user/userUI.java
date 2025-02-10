package views.user;

import java.sql.Connection;

import controllers.bookUI.BookCon;
import controllers.userUI.UserCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.utilisateur.Utilisateur;
import utils.dao.impl.UtilisateurDAOImpl;
import utils.dao.interfaces.UtilisateurDAO;
import utils.dbconnection.DatabaseConnection;
import views.home.homeUI;

public class userUI {
	public static Button btnAdd = new Button("Ajouter");
	public static Button btnModify = new Button("Modifier");
	public static Button btnDelete = new Button("Supprimer");
	public static MenuItem homeMenu = new MenuItem("Acceuil");
	public static Button btnClear = new Button("Rénitialiser");
	public static TextField searchField = new TextField("");
	public static TextField tf1 = new TextField();
	public static TextField tf2 = new TextField();
	public static TextField tf3 = new TextField();
	public static DatePicker tf4 = new DatePicker();
	public static ObservableList<Utilisateur> users=FXCollections.observableArrayList();
	public static FilteredList<Utilisateur> filteredusers  ;
	public static TableView<Utilisateur> tableView;
	public static Scene scene;
	public static TableView<Utilisateur> createTableView() {
		if(tableView==null) {
			tableView= new TableView<>();
			UtilisateurDAO U_DAO = new UtilisateurDAOImpl();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			TableColumn<Utilisateur, String> col1 = new TableColumn<>("Nom");
			col1.setCellValueFactory(new PropertyValueFactory<>("Nom"));
			TableColumn<Utilisateur, String> col2 = new TableColumn<>("Prenom");
			col2.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
			TableColumn<Utilisateur, String> col3 = new TableColumn<>("CIN");
			col3.setCellValueFactory(new PropertyValueFactory<>("CIN"));
			TableColumn<Utilisateur, String> col4 = new TableColumn<>("Date De Naissance");
			col4.setCellValueFactory(new PropertyValueFactory<>("DateDeNaissance"));
			tableView.getColumns().addAll(col1, col2, col3, col4);
			if(users.isEmpty()) {
			users =  FXCollections.observableArrayList(U_DAO.getTousLesUtilisateurs(conn));}
			filteredusers = new FilteredList<>(users, b->true);
			tableView.setItems(filteredusers);
		}
		return tableView;
	}

	public static GridPane createForm() {
		Label lbl1 = new Label("Nom:");
		tf1.setPromptText("Enter Le Nom");
		Label lbl2 = new Label("Prenom:");
		tf2.setPromptText("Enter le prenom");
		Label lbl3 = new Label("CIN:");
		tf3.setPromptText("Enter CIN");
		Label lbl4 = new Label("Date de Naissance:");
		tf4.setPromptText("Enter la date de nasissance");
		ColumnConstraints col1 = new ColumnConstraints(130); 
		ColumnConstraints col2 = new ColumnConstraints(180); 

		GridPane formLayout = new GridPane();
		formLayout.setHgap(10);
		formLayout.setVgap(15);
		formLayout.setPadding(new Insets(10));
		formLayout.add(lbl1, 0, 0);
		formLayout.add(tf1, 1, 0);
		formLayout.add(lbl2, 0, 1);
		formLayout.add(tf2, 1, 1);
		formLayout.add(lbl3, 0, 2);
		formLayout.add(tf3, 1, 2);
		formLayout.add(lbl4, 0, 3);
		formLayout.add(tf4, 1, 3);
		//formLayout.add(btnAdd, 0, 5);
		//formLayout.add(btnModify, 1, 5);
		//formLayout.add(btnDelete, 2, 5);
		formLayout.getColumnConstraints().addAll(col1, col2);
		return formLayout;
	}

	public static Scene createScene(Stage stage ) {
		if(scene==null) {
			searchField.setPromptText("rechercher...");
			searchField.setStyle(" -fx-pref-width: 350px; -fx-pref-height: 20px;");
			HBox search = new HBox(searchField); 
			HBox h = new HBox(20,btnAdd,btnModify,btnDelete,btnClear);
			h.setPadding(new Insets(12));
			search.setAlignment(Pos.CENTER);
			BorderPane root = new BorderPane();
			root.setPadding(new Insets(0));
			Separator s = new Separator();
			Menu m = new Menu("Acceuil");
			m.getItems().add(homeMenu);
			MenuBar menuBar = new MenuBar(m);
			VBox vBoxLayout = new VBox(20, createForm(),h,search,s,createTableView());
			vBoxLayout.setAlignment(Pos.CENTER);
			root.setCenter(vBoxLayout);
			root.setTop(menuBar);
			scene = new Scene(root,500,500);
			UserCon.setButtons(stage);
		}
		return scene;
	}

}
