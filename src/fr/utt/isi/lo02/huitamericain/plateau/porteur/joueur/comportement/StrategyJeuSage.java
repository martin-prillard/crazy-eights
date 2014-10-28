package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Stratégie Sage
* Garde des cartes de défense en réserve
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class StrategyJeuSage implements Jouable, Serializable {
	private CarteModel carteJoue;
	private int couleur = -1;
	
	@Override
	public void jouerCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		CarteModel carteSage = listCarteJouable.get((int)(Math.random() * listCarteJouable.size())); //Par defaut, on prend une carte au hasard
		
		//Si une carte à une valeur supérieur, on la prend, mais on garde les as, ils pourront servir plus tard
		for (CarteModel carte : listCarteJouable)
			if (carte.retourneValeur() > carteSage.retourneValeur() && carte.retourneValeur() != CarteModel.AS)
				carteSage = carte;
    	
    	if (carteSage.retourneTaille() == CarteModel.HUIT || carteSage.retourneTaille() == CarteModel.JOKER)
    		couleur = (int)(Math.random() * 3);
		else
			couleur = carteSage.retourneCouleur();
		
		carteJoue = carteSage;
	}

	
	@Override
	public CarteModel retourneCarteJoue() {return carteJoue;}
	@Override
	public int retourneCouleur() {return couleur;}
	@Override
	public String toString(){return PanelEditionIA.SAGE;};
}
