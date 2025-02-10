package utils.dao.interfaces;
import java.util.List;
import java.sql.*;

import models.livre.Livre;
public interface LivreDAO {
	public abstract void enregistrerLivre(Livre livre,Connection c);
	public abstract List<Livre> getTousLesLivres(Connection c);
	public abstract void supprimerUnLivre(Connection c, int isbn);
	public abstract void modifierLivre(Connection c,Livre L);

}
