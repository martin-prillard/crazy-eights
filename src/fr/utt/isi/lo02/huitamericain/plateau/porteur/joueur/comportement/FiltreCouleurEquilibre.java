package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Filtre les cartes jouables de manière a équilibrer les couleurs
* 
* @author Martin Prillard
*/
public class FiltreCouleurEquilibre implements Filtrable, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Override
	public ArrayList<CarteModel> filtrerListCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		int couleurPlusPresente = 0, couleur1 = 0, couleur2 = 0, couleur3 = 0, couleur4 = 0, couleur5 = 0;
		ArrayList<CarteModel> listCarteFiltre = new ArrayList<CarteModel>();
		
		//On compte le nombre de carte pour chaque couleurs
		for (CarteModel carte : listCarteJouable)
		{
			switch(carte.retourneCouleur())
			{
				case 1: couleur1++; break;
				case 2: couleur2++; break;
				case 3: couleur3++; break;
				case 4: couleur4++; break;
				case 5: couleur5++; break;
			}
		}
		
		//On détermine la couleur la plus présente
		if(couleur1 > couleur2 && couleur1 > couleur3 && couleur1 > couleur4 && couleur1 > couleur5)
			couleurPlusPresente = couleur1;
		else if (couleur2 > couleur3 && couleur2 > couleur4 && couleur2 > couleur5)
			couleurPlusPresente = couleur2;
		else if (couleur3 > couleur4 && couleur3 > couleur5)
			couleurPlusPresente = couleur3;
		else if (couleur4 > couleur5)
			couleurPlusPresente = couleur4;
		else
			couleurPlusPresente = couleur5;
		
		
		//On prend une carte de la couleur la plus joué
		for (CarteModel carte : listCarteJouable)
			if (carte.retourneCouleur() == couleurPlusPresente)
				listCarteFiltre.add(carte);
				
		return listCarteFiltre;
	}


	
	@Override
	public String toString(){return PanelEditionIA.CHOIXEQUI;};
}
