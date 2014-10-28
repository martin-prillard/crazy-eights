package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur;

import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.Porteur;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche.PiocheModel;

/**
* Classe abstraite d'un joueur dont hérite JoueurIA et JoueurHumain
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public abstract class AbstrJoueurModel extends Porteur{

	protected String nom = "DEFAULT";
	protected boolean joueurCourant = false;
	protected int profil;
	protected boolean rajouteCarte;
	protected int nbPartieGagnee = 0;
    protected int scoreTotal = 0;
    protected int classementJoueur;
    protected boolean carteMasque = true;
    protected int idProfil = 7;
    
	/**
	 * Méthode jouer
	 * @param pm
	 * @param listCarteJouable
	 */
    public abstract void jouer(PlateauModel pm, ArrayList<CarteModel> listCarteJouable);
	
	/**
	 * Edite le nom du joueur
	 * @param _nouveauNom
	 */
	public void editeNom(String _nouveauNom){nom = _nouveauNom;}
	
	/**
	 * Le joueur devient joueur courant ou non
	 * @param _joueurCourant
	 */
    public void editeJoueurCourant(boolean _joueurCourant) {joueurCourant = _joueurCourant;}
    
    /**
     * Retourne si le joueur courant ou non
     * @return true si joueurCourant
     */
    public boolean estJoueurCourant() {return joueurCourant;}
    
    /**
     * Incrémente le nombre de partie gagnée
     */
    public void editeGagnant(){nbPartieGagnee++;}
    
    /**
     * Change le classement du joueur
     * @param classement
     */
    public void editeClassement(int classement){classementJoueur = classement;}

    /**
     * Retourne le nom du joueur
     * @return nom
     */
	public String retourneNom(){return nom;}
	
	/**
	 * Retourne le classement du joueur
	 * @return classementJoueur
	 */
    public int retourneClassement() {return classementJoueur;}
    
    /**
     * Retourne le score total
     * @return scoreTotal
     */
    public int retourneScoreTotal(){return scoreTotal;}
    
    /**
     * Retourne le nombre de partie gagnée
     * @return nbPartieGagnee
     */
    public int retournePartieGagne(){return nbPartieGagnee;}
    
    /**
     * Retourne si le joueur ajoute ou retire une carte de son jeu
     * @return rajouteCarte
     */
    public boolean retourneEtatAjoutRetireCarte(){ return rajouteCarte;}
    
    /**
     * Retourne si les cartes du joueur sont masqué ou non
     * @return carteMasque
     */
	public boolean retourneCarteMasque(){return carteMasque;}

	/**
	 * Masque les cartes du joueur
	 * @param b
	 */
	public void masqueCarte(boolean b)
	{
		carteMasque = b;
		setChanged();
		notifyObservers();
	}

	/**
	 * Retourne une carte au hasard dans la main du joueur
	 * @return carte au hasard
	 */
   public CarteModel retourneCarteAuHasard()
    {
    	CarteModel cm = null;
    	
    	int lower = 0;
    	int higher = retourneNbCarte();
    	int random = (int)(Math.random() * (higher-lower)) + lower;
    	cm = retourneVectCarte().get(random);
    	
    	return cm;
    }
   	
	/* --------------- Fonctions statistiques --------------- */
    /**
     * Retourne le poids de la donne du joueur
     * @return poids de la donne
     */
    public int retournePoidsDonne()
    {
    	int poidsDonne = 0;
    	for (CarteModel carteEnJeu : vectCartes)
    		poidsDonne += carteEnJeu.retourneValeur();
    	return poidsDonne;
    }
    
    /**
     * Incrémente le score total en fin de partie
     */
    public void traitementFinPartie()
    {
        scoreTotal += retournePoidsDonne();
    }
    
    /**
     * Réinitialise les cartes du joueur entre chaques manches
     */
    public void reinitialise() 
    {
    	masqueCarte(true);
        vectCartes.removeAllElements();
        joueurCourant = false;
    }
	
	/**
	 * Insertion d'une carte dans la main du joueur
	 */
	public void insererCarte(CarteModel _idCarte){
		vectCartes.add(_idCarte);
		rajouteCarte = true;
		setChanged();
		notifyObservers(_idCarte);
	}
		
	/**
	 * Suppression d'une carte de la main du joueur
	 */
	public void supprimerCarte(CarteModel _idCarte){
		vectCartes.remove(_idCarte);
		rajouteCarte = false;
		setChanged();
		notifyObservers(_idCarte);
	}
	
    /**
     * Pioche une carte dans la pioche
     * @param _pioche
     */
    public void piocherCarte(PiocheModel _pioche)
    {
    	//On ne peut piocher que si la pioche contient des cartes
    	if(_pioche.estNonVide())
    	{
	    	CarteModel carteModelTemp = _pioche.retournePremiereCarte();
	    	carteModelTemp.editeClignote(false);
	    	insererCarte(carteModelTemp);
	    	_pioche.supprimerPremiereCarte();
    	}
    }
    
    /**
     * Pioche plusieurs cartes dans la pioche
     * @param _pioche
     * @param n
     */
    public void piocherCarteMultiple(PiocheModel _pioche, int n)
    {
    	for (int i = 0; i<n; i++)
    		piocherCarte(_pioche);
    }
    
    /**
     * Pioche une carte chez un joueur
     * @param _joueurRolePioche
     * @param _cartePiochee
     */
    public void piocherCarteChezJoueur(AbstrJoueurModel _joueurRolePioche, CarteModel _cartePiochee)
    {
    	CarteModel carteModelTemp = _cartePiochee;
    	carteModelTemp.editeClignote(false);
    	insererCarte(carteModelTemp);
    	_joueurRolePioche.supprimerCarte(carteModelTemp);
    }
    
    /**
     * Affiche la liste des cartes du joueur
     * @return liste
     */
    public String afficherListeCarte()
    {
    	String liste = "";
    	
    	for(CarteModel carte : vectCartes)
    		liste += "\nIndex : "+ vectCartes.indexOf(carte)+ " -> "+carte.toString();
    			
    	return liste;
    }
    
    /**
     * Retourne l'id du joueur
     * @return idProfil
     */
    public int retourneIdProfil(){return idProfil;}
}
