package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Stratégie Froussard
* Se défausse des cartes de grande valeur
* 
* @author Martin Prillard
*/
public class StrategyJeuCarteFroussard implements Jouable, Serializable {

	private static final long serialVersionUID = 1L;
	private CarteModel carteJoue;
	private int couleur = -1;
	
	@Override
	public void jouerCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		CarteModel carteMax = listCarteJouable.get((int)(Math.random() * listCarteJouable.size())); //Par defaut, on prend une carte au hasard
		
		//Si une carte à une valeur supérieur, on la prend
		for (CarteModel carte : listCarteJouable)
			if (carte.retourneValeur() > carteMax.retourneValeur())
				carteMax = carte;
		
		
		if (carteMax.retourneTaille() == CarteModel.HUIT || carteMax.retourneTaille() == CarteModel.JOKER)
			couleur = (int)(Math.random() * 3);
		else
			couleur = carteMax.retourneCouleur();
		
		carteJoue = carteMax;
	}

	
	@Override
	public CarteModel retourneCarteJoue() {return carteJoue;}
	@Override
	public int retourneCouleur() {return couleur;}
	@Override
	public String toString(){return PanelEditionIA.FROUSSARD;};
}
