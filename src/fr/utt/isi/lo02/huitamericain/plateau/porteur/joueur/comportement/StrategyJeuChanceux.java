package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Stratégie Chanceux
* Joue une carte totalement au  hasard
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class StrategyJeuChanceux implements Jouable, Serializable {

	private CarteModel carteJoue;
	private int couleur = -1;
	
	@Override
	public void jouerCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		
		//On prend une carte au hasard
    	CarteModel carteHasard = listCarteJouable.get((int)(Math.random() * listCarteJouable.size()));
    	
    	if (carteHasard.retourneTaille() == CarteModel.HUIT || carteHasard.retourneTaille() == CarteModel.JOKER)
    		couleur = (int)(Math.random() * 3);
		else
			couleur = carteHasard.retourneCouleur();
		
		carteJoue = carteHasard;
	}

	
	@Override
	public CarteModel retourneCarteJoue() {return carteJoue;}
	@Override
	public int retourneCouleur() {return couleur;}
	@Override
	public String toString(){return PanelEditionIA.CHANCEUX;};
}
