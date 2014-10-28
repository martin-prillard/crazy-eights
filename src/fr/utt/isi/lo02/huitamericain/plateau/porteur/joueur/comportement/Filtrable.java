package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Interface filtrable
* 
* @author Martin Prillard
*/
public interface Filtrable {
	
	public abstract ArrayList<CarteModel> filtrerListCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable);
}
