package utils.dao.interfaces;
import java.util.List;
import models.emprunt.Emprunt;
import java.sql.*;
import java.time.LocalDateTime;

public interface EmpruntDAO {
	public abstract void enregistrerEmprunt(Emprunt emprunt,Connection c);
	public abstract List<Emprunt> getTousLesEmprunts(Connection c);
	public abstract void supprimerUnEmprunt(Connection c,int id);
	public abstract void modifierEmprunt(Connection c,Emprunt e);
}
