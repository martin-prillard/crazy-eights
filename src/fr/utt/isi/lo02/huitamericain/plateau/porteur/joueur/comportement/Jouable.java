package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Interface jouable
* 
* @author Martin Prillard
*/
public interface Jouable {

	/**
	 * M�thode jou� une carte
	 * @param pm
	 * @param listCarteJouable
	 */
	public abstract void jouerCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable);
	
	/**
	 * Retourne la carte jou�e
	 * @return carte jou�e
	 */
	public abstract CarteModel retourneCarteJoue();
	
	/**
	 * Retourne la couleur du jeu
	 * @return couleur
	 */
	public abstract int retourneCouleur();
}
