package views.book;

import java.sql.Connection;

import controllers.bookUI.BookCon;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.livre.Livre;
import views.home.homeUI;
import java.sql.Connection;
import utils.dao.impl.LivreDAOImpl;
import utils.dao.interfaces.LivreDAO;
import utils.dbconnection.DatabaseConnection;
public class bookUI {
	public static TableView<Livre> tableView;
	public static Button btnAdd = new Button("Ajouter");
	public static Button btnModify = new Button("Modifier");
	public static Button btnDelete = new Button("Supprimer");
	public static Button btnClear = new Button("Réinitialiser");
	public static MenuItem homeMenu = new MenuItem("Acceuil");
	public static TextField searchField = new TextField("");
	public static TextField tf1 = new TextField();
	public static TextField tf2 = new TextField();
	public static TextField tf3 = new TextField();
	public static TextField tf4 = new TextField();
	public static TextField tf= new TextField();
	public static Slider slider = new Slider();
	public static ObservableList<Livre> livres=FXCollections.observableArrayList();
	public static FilteredList<Livre> filteredBooks;
	public static Scene scene;


	public static TableView<Livre> createTableView() {//refreshtableview
		if (tableView == null) {
			tableView= new TableView<>();
			LivreDAO L_DAO = new LivreDAOImpl();
			Connection conn = DatabaseConnection.getInstance().getConnection();
			TableColumn<Livre, String> col1 = new TableColumn<>("Titre");
			col1.setCellValueFactory(new PropertyValueFactory<>("Titre"));
			TableColumn<Livre, String> col2 = new TableColumn<>("Auteur");
			col2.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
			TableColumn<Livre, Integer> col3 = new TableColumn<>("ISBN");
			col3.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
			TableColumn<Livre, String> col4 = new TableColumn<>("Categorie");
			col4.setCellValueFactory(new PropertyValueFactory<>("Categorie"));
			TableColumn<Livre, Integer> col5 = new TableColumn<>("NbExemplaire");
			col5.setCellValueFactory(new PropertyValueFactory<>("NbExemplaire"));

			tableView.getColumns().addAll(col1, col2, col3, col4, col5);
			if(livres.isEmpty()) {
				livres = FXCollections.observableArrayList(L_DAO.getTousLesLivres(conn));}
			filteredBooks = new FilteredList<>(livres, b -> true);
			tableView.setItems(filteredBooks);
		}
		return tableView;
	}


	public static GridPane createForm() {

		Label lbl1 = new Label("Titre:");
		tf1.setPromptText("Enter Le titre");
		Label lbl2 = new Label("Auteur:");
		tf2.setPromptText("Enter le nom de l'auteur");
		Label lbl3 = new Label("ISBN:");
		tf3.setPromptText("Enter l'ISBN");
		Label lbl4 = new Label("Categorie:");
		tf4.setPromptText("Enter la categorie");
		Label lbl5 = new Label("Exemplaire:");
		slider = new Slider(0, 20, 0);
		slider.setMajorTickUnit(5);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		tf.textProperty().bind(Bindings.format("%.0f",slider.valueProperty()));
		tf.setPrefWidth(35);
		tf.setMaxWidth(35);
		//GridPane.setHalignment(tf, HPos.CENTER); // Align horizontally
		//GridPane.setValignment(tf, VPos.CENTER); // Align vertically

		ColumnConstraints col1 = new ColumnConstraints(100); 
		ColumnConstraints col2 = new ColumnConstraints(180); 
		ColumnConstraints col3 = new ColumnConstraints(45); 
		
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
		formLayout.add(lbl5, 0, 4);
		formLayout.add(slider, 1, 4);
		formLayout.add(tf, 2, 4);
		formLayout.getColumnConstraints().addAll(col1, col2, col3);
		//formLayout.add(btnAdd, 0, 5);
		//formLayout.add(btnModify, 1, 5);
		//formLayout.add(btnDelete, 2, 5);
		//formLayout.add(btnClear, 3, 5);
		return formLayout;
	}

	public static Scene createScene(Stage stage) {
		if(scene==null) {
			searchField.setPromptText("rechercher...");
			searchField.setStyle("-fx-pref-width: 350px; -fx-pref-height: 20px;");
			HBox search = new HBox(searchField); 
			HBox h = new HBox(20,btnAdd,btnModify,btnDelete,btnClear);
			h.setPadding(new Insets(12));
			search.setAlignment(Pos.CENTER);
			BorderPane root = new BorderPane();
			root.setPadding(new Insets(0));
			Separator s = new Separator();
			Menu m=new Menu("Acceuil");
			m.getItems().addAll(homeMenu);
			MenuBar menuBar = new MenuBar(m);
			VBox vBoxLayout = new VBox(15, createForm(),h,search,s,createTableView());
			vBoxLayout.setAlignment(Pos.CENTER);
			root.setCenter(vBoxLayout);
			root.setTop(menuBar);
			scene = new Scene(root,500,500);
			BookCon.setButtons(stage);}
		return scene;
	}
}
