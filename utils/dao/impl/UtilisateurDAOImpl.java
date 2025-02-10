package utils.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.utilisateur.Utilisateur;
import utils.dao.interfaces.UtilisateurDAO;

public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	@Override
	public void enregistrerUtilisateur(Utilisateur U,Connection c) {
		try {
			PreparedStatement PrStatement = c.prepareStatement("INSERT INTO Utilisateur (Nom, Prenom, CIN, DateDeNaissance) VALUES (?, ?, ?, ?)");
			PrStatement.setString(1, U.getNom());
			PrStatement.setString(2, U.getPrenom());
			PrStatement.setString(3, U.getCIN());
			PrStatement.setDate(4, Date.valueOf(U.getDateDeNaissance()));
			PrStatement.executeUpdate();
			System.out.println("Utilisateur enregistré avec succés !");
			PrStatement.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la creation du 'Statement' "+e.getMessage());
		}
	}

	@Override
	public List<Utilisateur> getTousLesUtilisateurs(Connection c) {
		List<Utilisateur> U1 = new ArrayList<>();
		String query = "SELECT * FROM Utilisateur";
		try {
			Statement Statement = c.createStatement();
			ResultSet rs = Statement.executeQuery(query);
			while (rs.next()) {
				Utilisateur u = new Utilisateur(rs.getString("Nom"),rs.getString("Prenom"),rs.getString("CIN"), rs.getDate("DateDeNaissance").toLocalDate());
				U1.add(u);
			}
			Statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return U1;
	}

	@Override
	public void supprimerUnUtilisateur(Connection c, String cin) {
		String query="DELETE FROM Utilisateur where CIN="+"'"+cin+"'";
		try{
			Statement Statement = c.createStatement();
			Statement.executeUpdate(query);
			System.out.println("Enregistrement supprimé avec succés");
			Statement.close();
			}
		catch (SQLException e) {
			System.out.println("Erreur lors de la suppression ,"+e.getMessage());
		}
	}
	
	public void modifierUtilisateur(Connection c,Utilisateur u) {
		String query="UPDATE Utilisateur SET Nom=?, Prenom=?, CIN=?, DateDeNaissance=? WHERE CIN=?";
		try{
			PreparedStatement PrStatement = c.prepareStatement(query);
			PrStatement.setString(2,u.getPrenom());
			PrStatement.setString(1,u.getNom());
			PrStatement.setString(3,u.getCIN());
			PrStatement.setDate(4,Date.valueOf(u.getDateDeNaissance()));
			PrStatement.setString(5,u.getCIN());

			PrStatement.executeUpdate();
			System.out.println("Enregistrement modifié avec succés");
			PrStatement.close();
		}
		catch (SQLException e) {
			System.out.println("Erreur lors de la modificaion ,"+e.getMessage());
		}
	}
}
