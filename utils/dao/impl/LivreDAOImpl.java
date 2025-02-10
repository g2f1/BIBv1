package utils.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import models.livre.Livre;
import models.utilisateur.Utilisateur;
import utils.dao.interfaces.LivreDAO;

public class LivreDAOImpl implements LivreDAO {
	@Override
	public void enregistrerLivre(Livre livre,Connection c) {
		try {
			PreparedStatement PrStatement = c.prepareStatement("INSERT INTO Livre (Titre, Auteur, ISBN, Categorie, nbExemplaire) VALUES (?, ?, ?, ?, ?)");
			PrStatement.setString(1, livre.getTitre());
			PrStatement.setString(2, livre.getAuteur());
			PrStatement.setInt(3, livre.getISBN());
			PrStatement.setString(4, livre.getCategorie());
			PrStatement.setInt(5, livre.getNbExemplaire());
			PrStatement.executeUpdate();
			System.out.println("Livre enregistré avec succés !");
			PrStatement.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la creation du 'Statement' "+e.getMessage());
		}
	}

	@Override
	public List<Livre> getTousLesLivres(Connection c) {
		List<Livre> L1 = new ArrayList<>();
		String query = "SELECT * FROM Livre";
		try {
			Statement Statement = c.createStatement();
			ResultSet rs = Statement.executeQuery(query);
			while (rs.next()) {
				Livre livre = new Livre(rs.getString("Titre"),rs.getString("Auteur"),rs.getInt("ISBN"), rs.getString("Categorie") ,rs.getInt("nbExemplaire"));
				L1.add(livre);
			}
			Statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return L1;
	}

	@Override
	public void supprimerUnLivre(Connection c, int isbn) {
		String query="DELETE FROM Livre WHERE ISBN="+isbn;
		try{
			Statement Statement = c.createStatement();
			Statement.executeUpdate(query);
			System.out.println("Enregistrement supprimé avec succés");
			Statement.close();
		}
		catch (SQLException e) {
			System.out.println("Erreur lors de la suppression ,"+e.getMessage());
		}
	}String query="UPDATE Livre SET Titre=?, nbExemplaire=?, Auteur=?, ISBN=?, Categorie=? WHERE ISBN=?";
	
	@Override
	public void modifierLivre(Connection c,Livre l) {
		String query="UPDATE Livre SET Titre=?, nbExemplaire=?, Auteur=?, ISBN=?, Categorie=? WHERE ISBN=?";
		try{
			PreparedStatement PrStatement = c.prepareStatement(query);
			PrStatement.setInt(2,l.getNbExemplaire());
			PrStatement.setString(1,l.getTitre());
			PrStatement.setString(3,l.getAuteur());
			PrStatement.setInt(4,l.getISBN());
			PrStatement.setString(5,l.getCategorie());
			PrStatement.setInt(6,l.getISBN());
			PrStatement.executeUpdate();
			System.out.println("Enregistrement modifié avec succés");
			PrStatement.close();
		}
		catch (SQLException e) {
			System.out.println("Erreur lors de la modificaion ,"+e.getMessage());
		}
	}
}
