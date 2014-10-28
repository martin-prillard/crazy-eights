package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;
import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Ne filtre par les cartes jouables
* 
* @author Martin Prillard
*/
public class FiltreNeutre implements Filtrable, Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList<CarteModel> filtrerListCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		return listCarteJouable;
	}
	
	@Override
	public String toString(){return PanelEditionIA.NEUTRE;};
}
