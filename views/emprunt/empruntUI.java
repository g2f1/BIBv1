package views.emprunt;
import javafx.scene.control.MenuItem;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import controllers.bookUI.BookCon;
import controllers.empruntUI.EmpruntCon;
import utils.dao.impl.EmpruntDAOImpl;
import utils.dao.interfaces.EmpruntDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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
import javafx.util.Pair;
import javafx.util.StringConverter;
import models.emprunt.Emprunt;
import models.livre.Livre;
import utils.dao.impl.LivreDAOImpl;
import utils.dao.interfaces.LivreDAO;
import utils.dbconnection.DatabaseConnection;

public class empruntUI {
	public static Scene scene;
	public static TableView<Emprunt> tableView;
	public static Button btnAdd = new Button("Ajouter");
	public static Button btnModify = new Button("Modifier");
	public static Button btnDelete = new Button("Supprimer");
	public static Button btnClear = new Button("Réinitialiser");
	public static MenuItem homeMenu = new MenuItem("Acceuil");
	public static TextField searchField = new TextField("");
	public static TextField tf1 = new TextField();
	public static TextField tf2 = new TextField();
	public static ChoiceBox<String> choiceBox ;
	public static ChoiceBox<Pair<String,String>> choiceBox1;
	public static ObservableList<Emprunt> emprunts=FXCollections.observableArrayList();
	public static FilteredList<Emprunt> filteredemprunts;
	public static TableView<Emprunt> createTableView() {
		if(tableView==null) {
			tableView= new TableView<Emprunt>();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			EmpruntDAO E_DAO = new EmpruntDAOImpl();
			TableColumn<Emprunt, String> col1 = new TableColumn<>("Id");
			col1.setCellValueFactory(new PropertyValueFactory<>("id"));
			TableColumn<Emprunt, String> col2 = new TableColumn<>("CIN");
			col2.setCellValueFactory(new PropertyValueFactory<>("CIN"));
			TableColumn<Emprunt, String> col3 = new TableColumn<>("ISBN");
			col3.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
			TableColumn<Emprunt, String> col4 = new TableColumn<>("Date de l'emprunt");
			col4.setCellValueFactory(new PropertyValueFactory<>("DateEmprunt"));
			TableColumn<Emprunt, String> col5 = new TableColumn<>("Date de retour");
			col5.setCellValueFactory(new PropertyValueFactory<>("DateRetour"));
			TableColumn<Emprunt, String> col6 = new TableColumn<>("Etat");
			col6.setCellValueFactory(new PropertyValueFactory<>("Etat"));
			tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6);
			if(emprunts.isEmpty()) {
			emprunts = FXCollections.observableList(E_DAO.getTousLesEmprunts(conn));}
			filteredemprunts = new FilteredList<>(emprunts, b->true);
			tableView.setItems(filteredemprunts);
		}
		return tableView;
	}

	public static GridPane createForm() 
	{
		Label lbl1 = new Label("CIN:");
		tf1.setPromptText("Enter CIN");
		Label lbl2 = new Label("ISBN:");
		tf2.setPromptText("Enter l'ISBN");
		Label lbl3 = new Label("Etat:");
		ColumnConstraints col1 = new ColumnConstraints(100); 
		ColumnConstraints col2 = new ColumnConstraints(150); 
		GridPane formLayout = new GridPane();
		formLayout.setHgap(10);
		formLayout.setVgap(15);
		formLayout.setPadding(new Insets(10));
		formLayout.add(lbl1, 0, 0);
		formLayout.add(tf1, 1, 0);
		formLayout.add(lbl2, 0, 1);
		formLayout.add(tf2, 1, 1);
		formLayout.add(lbl3, 0, 2);
		formLayout.add(choiceBox1, 1, 2);
		//formLayout.add(btnAdd, 0, 5);
		//formLayout.add(btnModify, 1, 5);
		//formLayout.add(btnDelete, 2, 5);
		//formLayout.add(btnClear, 3, 5);
		formLayout.getColumnConstraints().addAll(col1, col2);
		return formLayout;
	}

	public static Scene createScene(Stage stage) 
	{
		if(scene==null) {
			choiceBox = new ChoiceBox<>();
			choiceBox1 = new ChoiceBox<Pair<String,String>>();
			choiceBox1.setValue(new Pair("En cours", "E"));
			choiceBox.setItems(FXCollections.observableArrayList("Tous", "Retourné", "En cours"));
			choiceBox1.setItems(FXCollections.observableArrayList(new Pair<String, String>("Retourné", "R"),new Pair<String, String>("En cours", "E")));
			choiceBox.setValue("Tous");
			choiceBox1.setConverter( 
					new StringConverter<Pair<String,String>>() {
						@Override
						public String toString(Pair<String, String> pair) {
							return pair.getKey();
						}

						@Override
						public Pair<String, String> fromString(String string) {
							return null;
						}
					});
			searchField.setPromptText("rechercher...");
			searchField.setStyle(" -fx-pref-width: 350px; -fx-pref-height: 20px;");
			HBox search = new HBox(searchField,choiceBox); 
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
			EmpruntCon.setButtons(stage);
		}
		return scene;
	}
}
