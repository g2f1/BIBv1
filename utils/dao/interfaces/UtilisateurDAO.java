package utils.dao.interfaces;
import java.sql.*;
import java.util.List;
import models.utilisateur.Utilisateur;

public interface UtilisateurDAO {
	public abstract void enregistrerUtilisateur(Utilisateur Utilisateur, Connection c);
	public abstract List<Utilisateur> getTousLesUtilisateurs(Connection c);
	public abstract void supprimerUnUtilisateur(Connection c, String cin);
	public abstract void modifierUtilisateur(Connection c, Utilisateur u);
}
