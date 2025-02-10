package services;

import javafx.collections.ObservableList;
import models.livre.Livre;

public class LivreParIsbn {
	public static Livre livreParIsbn(ObservableList<Livre> livres,int isbn) {
		for(int j=0;j<livres.size();j++) {
			if(livres.get(j).getISBN()==isbn) {
				return livres.get(j);
			}
		}
		return null;
	}
}
