package fr.utt.isi.lo02.huitamericain.plateau.carte;

import java.io.Serializable;
import java.util.Observable;

/**
* Model d'une carte
* 
* @author Martin Prillard
*/
public class CarteModel extends Observable implements Serializable{

	private static final long serialVersionUID = 1L;
	private int valeur;
	protected int couleur;
	private int taille;
	private boolean clignote;
	//Couleur
	public final static int TREFLE = 0;
	public final static int CARREAU = 1;
	public final static int COEUR = 2;
	public final static int PIQUE = 3;
	public final static int INCOLORE = 4;
	//Valeur
	public final static int AS = 1;
	public final static int DEUX = 2;
	public final static int TROIS = 3;
	public final static int QUATRE = 4;
	public final static int CINQ = 5;
	public final static int SIX = 6;
	public final static int SEPT = 7;
	public final static int HUIT = 8;
	public final static int NEUF = 9;
	public final static int DIX = 10;
	public final static int VALET = 11;
	public final static int DAME = 12;
	public final static int ROI = 13;
	public final static int JOKER = 15;
	
	/**
	 * Constructeur d'une carte
	 * @param _taille
	 * @param _valeur
	 * @param _couleur
	 */
	public CarteModel(int _taille, int _valeur, int _couleur)
	{
		taille = _taille;
		valeur = _valeur;
		couleur = _couleur;
		clignote = false;
	}
	
	/**
	 * Edite si cette carte doit étre mis en valeur ou non si elle est jouable
	 * La vue se chargera ensuite de la façon dont elle sera visualisé
	 * @param b
	 */
    public void editeClignote(boolean b)
    {
    	clignote = b;
    	setChanged();
    	notifyObservers();
    }
    
    /**
     * Modifie la valeur de la carte
     * @param val
     */
    public void editeValeur(int val){valeur = val;}
    
	public int retourneTaille(){return taille;}
	public int retourneCouleur(){return couleur;}
	public int retourneValeur(){return valeur;}
	public String toString(){return "Taille = "+taille+" | Valeur = "+valeur+" | Couleur = "+couleur;}
	public boolean retourneClignote(){return clignote;}
}
