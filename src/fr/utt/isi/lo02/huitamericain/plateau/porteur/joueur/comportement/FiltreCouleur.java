package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Filtre les cartes jouables avec une certaines couleurs
* 
* @author Martin Prillard
*/
public class FiltreCouleur implements Filtrable, Serializable{
	private static final long serialVersionUID = 1L;
	private int couleurFavoris = -1;
	
	public FiltreCouleur(int couleur){couleurFavoris = couleur;}

	
	@Override
	public ArrayList<CarteModel> filtrerListCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		ArrayList<CarteModel> listCarteFiltre = new ArrayList<CarteModel>();
		
		//On prend une carte de la couleur favorie
		for (CarteModel carte : listCarteJouable)
			if (carte.retourneCouleur() == couleurFavoris)
				listCarteFiltre.add(carte);

		return listCarteFiltre;
	}
	
	@Override
	public String toString()
	{
		String chaine = null;
		switch(couleurFavoris)
		{
		case 0:
			chaine = PanelEditionIA.PREFTREFLE;
			break;
		case 1:
			chaine = PanelEditionIA.PREFCARREAU;
			break;
		case 2:
			chaine = PanelEditionIA.PREFCOEUR;
			break;
		case 3:
			chaine = PanelEditionIA.PREFPIC;
			break;
		}
		return chaine;
	};
	
}
