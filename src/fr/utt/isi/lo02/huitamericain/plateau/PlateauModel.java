package fr.utt.isi.lo02.huitamericain.plateau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import fr.utt.isi.lo02.huitamericain.partie.Partie;
import fr.utt.isi.lo02.huitamericain.partie.StatModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurIA;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche.PiocheModel;

/**
* Model du plateau
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class PlateauModel extends Observable implements Serializable{
	
	private AbstrJoueurModel joueurCourant = null;
	private AbstrJoueurModel joueurCourantAncien = null;
	private PiocheModel laPioche;
	private boolean sensHorraire = true;
	private int nbPourVictoire;
	private boolean victoireParPoints;
	private int positionJoueurCourant;
	private int cptAs = 0;
	private int nbCarteAPiocher = 1;
	private boolean bloqueParUnAsActif = false;
	private boolean piocheChezJoueur = false;
	private int couleurChoisie = -1;
	private Partie partMod;
	private int nbJoueur;
	private ArrayList<AbstrJoueurModel> listJoueurs;
	private boolean finDeManche = false;
	private boolean finDePartie = false;
	
  
	/**
	 * Constructeur d'un plateau de jeu
	 * @param _partMod
	 */
	public PlateauModel(Partie _partMod)
	{
		partMod = _partMod;
		victoireParPoints = partMod.retourneVictoireParPoints();
		nbPourVictoire = partMod.retourneNbPourVictoire();
		nbJoueur = partMod.retourneNbJoueur();
		listJoueurs = partMod.retourneListJoueur();
		int nbCarteParJoueur = partMod.retourneNbCarteParJoueur();
		
		initialisationPioche();
		
		//Distribution des cartes
		for (int i = 0; i < nbCarteParJoueur; i++)
			for (AbstrJoueurModel iJoueur : listJoueurs)
				iJoueur.piocherCarte(laPioche);
	    //Donne la main au joueur humain pour debuter la partie
		positionJoueurCourant = 0;
		attribueJoueurCourant(listJoueurs.get(positionJoueurCourant));
	}
	
	/**
	 * Création de la pioche
	 */
	public void initialisationPioche()
	{
		laPioche = new PiocheModel(partMod.retourneNbPioche());
		couleurChoisie = laPioche.retourneDerniereCarte().retourneCouleur();
	}
    
	/**
	 * Attribution d'un joueur courant
	 * @param m
	 */
    public void attribueJoueurCourant(AbstrJoueurModel m)
    {
    	//On traite les données concernant le joueur qui vient de jouer
        if (joueurCourant != null)
        {
        	//On lui retire la main
        	joueurCourantAncien = joueurCourant;
        	joueurCourant.editeJoueurCourant(false);

        	//Si c'est un joueur humain qui vient de jouer son tour
            if (joueurCourantAncien instanceof JoueurHumain)
            {
            	//On affiche le bouton "Carte !" si il ne lui reste que deux cartes
            	if (joueurCourantAncien.retourneNbCarte() == 2)
            		((JoueurHumain) joueurCourantAncien).editeDerCarte(true);
            	else
            		((JoueurHumain) joueurCourantAncien).editeDerCarte(false);
            	//On masque ses cartes
            	joueurCourant.masqueCarte(true);
                //On désactive les animations
                clignoteCarteJoueur(joueurCourantAncien, false);
            }
        }
        
        //On donne la main au nouveau joueur courant
        joueurCourant = m;
        m.editeJoueurCourant(true);
        
        //Si ce nouveau joueur courant est humain
        if (joueurCourant instanceof JoueurHumain)
        {
    		//Roulement visuel du plateau si nécessaire
        	setChanged();
        	notifyObservers(joueurCourant);
        	
        	//On affiche le bouton "Carte !" si il ne lui reste que deux cartes
        	if (joueurCourant.retourneNbCarte() == 2)
        		((JoueurHumain) joueurCourant).editeDerCarte(true);
        	else
        		((JoueurHumain) joueurCourant).editeDerCarte(false);
        	
        	//On affiche ses cartes
    		joueurCourant.masqueCarte(false);
    		//On active les animations
        	clignoteCarteJoueur(joueurCourant, true);
        }
    }
    
    /**
     * Effectue le roulement du joueur courant
     * Appelé une fois que le joueur a terminer son tour
     * @return le joueur courant
     */
    public AbstrJoueurModel roulementJoueurCourant()
    {	
    	//On vérifie que ce n'est pas la fin du jeu
    	finDeManche();
    	setChanged();
    	notifyObservers();
    	
    	//On effectue le roulement
    	if (sensHorraire)
    	{
    		positionJoueurCourant = (positionJoueurCourant+1)%nbJoueur;
    	}else{
    		positionJoueurCourant--;
    		if (positionJoueurCourant == -1)
    			positionJoueurCourant = nbJoueur-1;
    	}
    	attribueJoueurCourant(listJoueurs.get(positionJoueurCourant));
    	
    	//Si le joueur suivant est un joueur IA, il joue automatiquement
    	if (joueurCourant instanceof JoueurIA && !finDeManche)
    		joueurCourant.jouer(this, retourneListCarteJouable(joueurCourant));
    	//si c'est à un joueur humain de jouer, qu'il n'a pas de cartes jouables et qu'il ne peut pas piocher car la pioche est vide, il passe son tour
    	else if (joueurCourant instanceof JoueurHumain && retourneListCarteJouable(joueurCourant).size() == 0 && !laPioche.estNonVide() && !finDeManche)
    		roulementJoueurCourant();
    		
    	return joueurCourant;
    }
    
    /**
     * Fonction permettant d'inverser le sens de déroulement du jeu
     */
    public void inverserSensRoulement(){sensHorraire = !sensHorraire;}
        
    /**
     * Réinitialise la pioche lorsqu'un joueur pioche
     * @see #actionCarteSurPlateau(AbstrJoueurModel, CarteModel, int)
     */
    public void reinitialiserNbCarteAPiocher()
    {
		cptAs = 0;
		bloqueParUnAsActif = false;
    	nbCarteAPiocher = 1;
    }
    
    /**
     * Modifie le model des cartes selon que celle ci doit clignoter ou non
     * (Aide pour le joueur humain, il sait ainsi quelles cartes jouer) 
     */
    public void clignoteCarteJoueur(AbstrJoueurModel joueurQuiACliquer, boolean activer)
    {
    	if (activer)
    	{
	    	for (CarteModel iCarte : joueurQuiACliquer.retourneVectCarte())
	    		if (peutJouerCarte(iCarte))
	    			iCarte.editeClignote(true);
    	}else{
    		for (CarteModel iCarte : joueurQuiACliquer.retourneVectCarte())
    			iCarte.editeClignote(false);
    	}

    }
    
    /**
     * Permet de savoir à partir d'une carte donnée, si cette dernière est jouable ou non.
     * Retourne "true" si jouable, "false" si injouable.
     * @param carteJouer
     * @return true si la carte peut étre joué
     */
    public boolean peutJouerCarte(CarteModel carteJouer)
    {
    	boolean jouerCarte = false;
    	int tailleCarteJouer = carteJouer.retourneTaille();

    	    /* Si le joueur doit piocher une carte chez le joueur précédent */
        	if (!piocheChezJoueur)
        	{
        		/* Si le joueur doit piocher des cartes (pénalité d'un as, d'un deux, ou d'un joker) */
            	if (nbCarteAPiocher > 1)
            	{
            		/* Si le joueur doit en théorie piocher a cause d'un as actif, il peut eviter de piocher si il joue lui aussi un as */
            		if ((bloqueParUnAsActif) && (tailleCarteJouer == CarteModel.AS) )
            		{
            			jouerCarte = true;
            		}
            	}
            	/* Si pas de carte a piocher, ni chez le joueur, ni dans la pioche */
            	else
            	{
            		if (!estBloqueParUnAsPassif() && joueurCourant.retourneNbCarte() > 1) //Un huit ou un joker ne peut pas être jouer comme dernière carte
            		{
    	        		switch (tailleCarteJouer)
    	        		{
    		    			case CarteModel.HUIT:
    		    				jouerCarte = true;
    		    				break;
    		    			case CarteModel.JOKER:
    		    				jouerCarte = true;
    		    				break;
    	        		}
            		}
    	        	if ( !jouerCarte && ((estDeMemeCouleur(carteJouer) || estDeMemeTaille(carteJouer))))
    	        	{
	    	        	jouerCarte = true;
    	        	}
            	}
        	}
    	
    	return jouerCarte; 
    }
    
    /**
     * Méthode s'occupant de gerer l'action de la carte jouée par un joueur sur le plateau.
     * @param m
     * @param carte
     * @param _couleurChoisie
     */
    public void actionCarteSurPlateau(AbstrJoueurModel m, CarteModel carte, int _couleurChoisie)
    {	
		switch(carte.retourneTaille())
		{
			case 1 :
    			cptAs++;
    			nbCarteAPiocher = cptAs*2;
				bloqueParUnAsActif = true;
				break;
			case 2 :
				nbCarteAPiocher = 2;
				break;
			case 7 :
    			piocheChezJoueur = true;
				break;
			case 10 :
    			inverserSensRoulement();
				break;
			case 15 :
				nbCarteAPiocher = 5;
				break;
		}
    	laPioche.insererCarte(carte);
    	m.supprimerCarte(carte);
		couleurChoisie = _couleurChoisie;
    }
  
    /**
     * Fonction de vérification permettant de savoir 
     * si la carte joué est de la couleur du jeu.
     * @param carteJouer
     * @return true si même couleur, false sinon
     * @see #peutJouerCarte(CarteModel)
     */
    public boolean estDeMemeCouleur(CarteModel carteJouer)
    {
    	boolean memeCouleur = false;
    	if (carteJouer.retourneCouleur() == couleurChoisie)
    		memeCouleur = true;
    	return memeCouleur;
    }
    
    /**
     * Fonction de vérification permettant de savoir 
     * si la carte joué est de même taille que la bérgère.
     * @param carteJouer
     * @return true si même taille, false sinon
     * @see #peutJouerCarte(CarteModel)
     */
    public boolean estDeMemeTaille(CarteModel carteJouer)
    {
    	boolean memeTaille = false;
    	if (carteJouer.retourneTaille() == laPioche.retourneDerniereCarte().retourneTaille())
    		memeTaille = true;
    	return memeTaille;
    }

    
    /**
     * Fonction de vérification permettant de savoir 
     * si la carte joué est de même taille que la bérgère.
     * @return true si même taille, false sinon
     * @see #peutJouerCarte(CarteModel)
     */
    public boolean estBloqueParUnAsPassif()
    {
    	boolean asDevant = false;
    	if (laPioche.retourneDerniereCarte().retourneTaille() == 1)
    		asDevant = true;
    	return asDevant;
    }
    
	/** 
	 * Test de fin de manche -> Affichage des scores
	 */
    public void finDeManche()
    {
    	for (AbstrJoueurModel iJoueur : listJoueurs)
	    	if(iJoueur.retourneNbCarte() == 0)
	    	{
	    		System.out.println("\n********* Fin de la manche *********\n");
	    		finDeManche = true;
	    		iJoueur.editeGagnant();
	    		partMod.retourneStatModel().traitementPartieTermine();
	    		partMod.ecrireFichierScore();
	    		finDePartie();
	    	}
    }
    
	/** 
	 * Test de fin du jeu -> Affichage des scores
	 */
    public void finDePartie()
    {
    	if(victoireParPoints)
    	{
    		for (AbstrJoueurModel iJoueur : listJoueurs)
    		{
    			if(iJoueur.retourneScoreTotal() >= nbPourVictoire)
    			{
    				finDePartie = true;
    				System.out.println("\n********* Fin de la partie *********\n");
    			}
    		}
    	} else if (partMod.retourneStatModel().retourneNbPartiesJouees() >= nbPourVictoire)
    	{
    			finDePartie = true;
    			System.out.println("\n********* Fin de la partie *********\n");
    	}
    }
    
    /**
     * Modifie etat pioche chez joueur
     * @param val
     */
    public void editePiocheChezJoueur(boolean val){piocheChezJoueur = val;}
    
    public boolean retourneFinDePartie(){ return finDePartie;}
    public boolean retourneFinDeManche(){ return finDeManche;}
    public StatModel retourneStatModel(){ return partMod.retourneStatModel();}
    public AbstrJoueurModel retourneJoueurCourant(){return joueurCourant;}
    public AbstrJoueurModel retourneJoueurCourantAncien(){return joueurCourantAncien;}
    public PiocheModel retourneModelPioche(){return laPioche;}
    public int retourneNbCarteAPiocher(){ return nbCarteAPiocher;}
    public void editerCouleurChoisie(int val){ couleurChoisie = val; }
    public boolean retournePiocheChezJoueur(){ return piocheChezJoueur;}
    public AbstrJoueurModel retourneDernierJoueurCourant(){return joueurCourantAncien;}
    public int retourneNbJoueur(){ return nbJoueur;}
    public int retourneCouleurChoisie(){ return couleurChoisie;}
    public ArrayList<AbstrJoueurModel> retourneListJoueur(){ return listJoueurs;}
    public boolean retourneSens(){return sensHorraire;}
    
    /**
     * Test si c'est à un joueur humain de jouer
     * @return true si c'est à un joueur humain de jouer
     */
    public boolean tourJoueurHumain()
    {
    	boolean peutJouer = false;
    	
    	for (AbstrJoueurModel iJoueur : listJoueurs)
    		//Si l'un des joueurs humain est le joueur courant
    		if (iJoueur instanceof JoueurHumain && iJoueur.estJoueurCourant())
    			peutJouer = true;
    	return peutJouer;
    }

    /**
     * Test chaque carte d'un joueur et retourne celles qui sont jouable
     * @param jm
     * @return la liste de carte jouable pour un joueur donné
     */
    public ArrayList<CarteModel> retourneListCarteJouable(AbstrJoueurModel jm)
    {
    	ArrayList<CarteModel> listCarteJouable = new ArrayList<CarteModel>();
    	for(CarteModel cm : jm.retourneVectCarte())
    		if (peutJouerCarte(cm))
    			listCarteJouable.add(cm);
    	return listCarteJouable;
    }
}