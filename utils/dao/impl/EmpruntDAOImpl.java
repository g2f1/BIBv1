package utils.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.emprunt.Emprunt;
import utils.dao.interfaces.EmpruntDAO;

public class EmpruntDAOImpl implements EmpruntDAO {
	public void enregistrerEmprunt(Emprunt emprunt,Connection c) {
		try {
			PreparedStatement PrStatement = c.prepareStatement("INSERT INTO Emprunt (id, CIN, ISBN, DateEmprunt, DateRetour, Etat) VALUES (?, ?, ?, ?, ?, ?)");
			PrStatement.setInt(1, emprunt.getId());
			PrStatement.setString(2, emprunt.getCIN());
			PrStatement.setInt(3, emprunt.getISBN());
			PrStatement.setTimestamp(4, Timestamp.valueOf(emprunt.getDateEmprunt()));
			PrStatement.setTimestamp(5, Timestamp.valueOf(emprunt.getDateRetour()));
			PrStatement.setString(6, emprunt.getEtat());
			PrStatement.executeUpdate();
			System.out.println("Emprunt enregistré avec succés !");
			PrStatement.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la creation du 'Statement' "+e.getMessage());
		}
	}

	@Override
	public List<Emprunt> getTousLesEmprunts(Connection c) {
		List<Emprunt> E1 = new ArrayList<>();
		String query = "SELECT * FROM Emprunt";
		try {
			Statement Statement = c.createStatement();
			ResultSet rs = Statement.executeQuery(query);
			while (rs.next()) {
				Emprunt emprunt = new Emprunt(rs.getInt("id"),rs.getString("CIN"),rs.getInt("ISBN"),rs.getTimestamp("DateEmprunt").toLocalDateTime(),rs.getTimestamp("DateRetour").toLocalDateTime(),rs.getString("etat"));
				E1.add(emprunt);
			}
			Statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return E1;
	}

	@Override
	public void supprimerUnEmprunt(Connection c, int id) {
		String query="DELETE FROM Emprunt where id="+id;
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

	@Override
	public void modifierEmprunt(Connection c,Emprunt e) {
		String query="UPDATE Emprunt SET DateRetour=?,Etat=? WHERE id=?";
		try{
			PreparedStatement PrStatement = c.prepareStatement(query);
			PrStatement.setTimestamp(1,Timestamp.valueOf(e.getDateRetour()));
			PrStatement.setString(2,e.getEtat());
			PrStatement.setInt(3,e.getId());
			PrStatement.executeUpdate();
			System.out.println("Enregistrement modifié avec succés");
			PrStatement.close();
		}
		catch (SQLException ee) {
			System.out.println("Erreur lors de la modificaion ,"+ee.getMessage());
		}
	}
}
