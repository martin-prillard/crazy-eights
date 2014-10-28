package fr.utt.isi.lo02.huitamericain.plateau.porteur;

import java.io.Serializable;
import java.util.Observable;
import java.util.Vector;

import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Classe porteur, dont hérite les classes PiocheModel et AbstrJoueurModel
* 
* @author Martin Prillard
*/
public abstract class Porteur extends Observable implements Serializable{

	private static final long serialVersionUID = 1L;
	protected Vector<CarteModel> vectCartes = new Vector<CarteModel>();
	
	/**
	 * Retourne la première carte en main
	 * @return premiere carte
	 */
	public CarteModel retournePremiereCarte(){return vectCartes.firstElement();}
	
	/**
	 * Retourne la derniere carte en main
	 * @return derniere carte
	 */
	public CarteModel retourneDerniereCarte(){return vectCartes.lastElement();}
	
	/**
	 * Retourne le nombre de carte
	 * @return nb carte dans main
	 */
	public int retourneNbCarte() {return vectCartes.size();}
	
	/**
	 * Retourne l'ensemble des cartes
	 * @return main de carte
	 */
	public Vector<CarteModel> retourneVectCarte() {return vectCartes;}
	
	/**
	 * Insère une carte
	 * @param _idCarte
	 */
	public abstract void insererCarte(CarteModel _idCarte);
	
	/**
	 * Supprime une carte
	 * @param _idCarte
	 */
	public abstract void supprimerCarte(CarteModel _idCarte);
}
