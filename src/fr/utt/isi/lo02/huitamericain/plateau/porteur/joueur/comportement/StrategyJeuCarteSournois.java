package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Stratégie Sournois
* Cherche à pénaliser les autres joueurs
* 
* @author Martin Prillard
*/
public class StrategyJeuCarteSournois implements Jouable, Serializable {

	private static final long serialVersionUID = 1L;
	private CarteModel carteJoue;
	private int couleur = -1;
	
	@Override
	public void jouerCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		CarteModel cartePenalite = listCarteJouable.get((int)(Math.random() * listCarteJouable.size())); //Par defaut, on prend une carte au hasard
		
		for (CarteModel carte : listCarteJouable)
			if ((pm.retourneNbCarteAPiocher() + 2) > 5 && carte.retourneTaille() == CarteModel.AS) //Carte a piocher > 5
				cartePenalite = carte;
			else if (carte.retourneTaille() == CarteModel.JOKER) //Carte a piocher = 5
				cartePenalite = carte;
			else if (carte.retourneTaille() == CarteModel.DEUX) //Carte a piocher = 2
				cartePenalite = carte;
			else if (carte.retourneTaille() == CarteModel.AS) //Carte a piocher = 2
				cartePenalite = carte;

		
		if (cartePenalite.retourneTaille() == CarteModel.HUIT || cartePenalite.retourneTaille() == CarteModel.JOKER)
			couleur = (int)(Math.random() * 3);
		else
			couleur = cartePenalite.retourneCouleur();
		
		carteJoue = cartePenalite;
	}

	@Override
	public CarteModel retourneCarteJoue() {return carteJoue;}
	@Override
	public int retourneCouleur() {return couleur;}
	@Override
	public String toString(){return PanelEditionIA.SOURNOIS;};
}
