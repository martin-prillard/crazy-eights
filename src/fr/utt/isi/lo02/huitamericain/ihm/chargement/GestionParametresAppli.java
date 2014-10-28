package fr.utt.isi.lo02.huitamericain.ihm.chargement;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

import fr.utt.isi.lo02.huitamericain.ihm.audio.GestionSonMP3Appli;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Gestion des paramètres du programme,
* fait office de properties
* (Singleton)
* 
* @author Martin Prillard
*/
public class GestionParametresAppli extends Observable{

	private static GestionParametresAppli paramInstance = null;
	
	/* menu affichage  */
	public enum Animations { CRAZY, NORMAL, AUCUNE};
	private boolean antialiasing;
	private boolean pleinEcran;
	private boolean doubleBufferingSoftware;
	private int numFondEcran;
	private String sourceImgPerso;
    private ClassLoader classLoader = getClass().getClassLoader();
	private GestionSonMP3Appli sonMp3;
	private boolean courbePlateau;
	private boolean infoJoueur;
	private int themeCarte;
	private URL urlSon;
	private URL urlSonDefaut;
	private boolean animationCarteAJouer;
	private int tailleCarteX;
	private int tailleCarteY;
	private int tailleAvat;
	private int tailleAvatImage;
	private Animations etatAnimations;
	private int theme;
	private int flou;
	private Color couleurTexteCarte;
	private int valAs = 20;
	private int val2 = 2;
	private int val3 = 3;
	private int val4 = 4;
	private int val5 = 5;
	private int val6 = 6;
	private int val7 = 7;
	private int val8 = 32;
	private int val9 = 9;
	private int val10 = 10;
	private int valV = 10;
	private int valD = 10;
	private int valR = 10;
	private int valJ = 50;
	
	private GestionParametresAppli(){}

    public void initChargementParametres() throws IOException
    {
		numFondEcran = 1;
		theme = 1;
		flou = 20;
		themeCarte = 2;
		tailleAvat = 150;
		tailleAvatImage = 125;
		tailleCarteX = 93;
		tailleCarteY = 135;
		etatAnimations = Animations.NORMAL;
		antialiasing = true;
		pleinEcran = false;
		couleurTexteCarte = Color.gray;
		courbePlateau = false;
		doubleBufferingSoftware = false;
		urlSonDefaut = classLoader.getResource("Son/La_lettre_a_Elise.mp3");
		urlSon = urlSonDefaut;
		sonMp3 = new GestionSonMP3Appli(urlSon);
		infoJoueur = true;
		animationCarteAJouer = true;
    }
    
	/* --------------- Modifie état --------------- */

    public void editeFlou(int val){flou = val;}
    
    public void editeAnimationCarteAJouer(Boolean _active)
    {
    	animationCarteAJouer = _active;
		setChanged();
		notifyObservers();
    }
    
	public void editeEtatFondEcran(int _choix)
	{
		numFondEcran = _choix;
	}
	
	public void editeEtatThemeCarte(int _choix)
	{
		if(_choix == 1)
			couleurTexteCarte = Color.BLACK;
		else
			couleurTexteCarte = Color.gray;
		themeCarte = _choix;
	}
	
	public void editeCourbePlateau(boolean _visible)
	{
		courbePlateau = _visible;
		setChanged();
		notifyObservers();
	}
	
	public void editeInfoJoueur(boolean _infoJoueur)
	{
		infoJoueur = _infoJoueur;
		setChanged();
		notifyObservers();
	}
	
	public void editeFondEcranPerso(String _source)
	{
		sourceImgPerso = _source;
	}
	
	public void editeEtatAnimation(int _choix)
	{
		switch (_choix)
		{
		case 1:
			etatAnimations = Animations.CRAZY;
			break;
		case 2:
			etatAnimations = Animations.NORMAL;
			break;
		case 3:
			etatAnimations = Animations.AUCUNE;
			break;
		}
		setChanged();
		notifyObservers();
	}
	
	public void editeEtatTailleCarte(int _choix)
	{
		switch (_choix)
		{
		case 1:
			tailleAvat = 185;
			tailleAvatImage = 160; 
			tailleCarteX = 117;
			tailleCarteY = 170;
			break;
		case 2:
			tailleAvat = 150;
			tailleAvatImage = 125; 
			tailleCarteX = 93;
			tailleCarteY = 135;
			break;
		case 3:
			tailleAvat = 115;
			tailleAvatImage = 90; 
			tailleCarteX = 68;
			tailleCarteY = 100;
			break;
		}
	}
	
	/**
	 * Son exterieur
	 * @param source
	 * @throws FileNotFoundException 
	 */
    @SuppressWarnings("deprecation")
	public void editeSonMp3(File source, boolean sonActive) throws FileNotFoundException
    {
    	editeEtatSonMp3(false);
    	try {
			urlSon = source.toURL();
		} catch (MalformedURLException e) {e.printStackTrace();}
    	sonMp3 = new GestionSonMP3Appli(urlSon);
    	 if (sonActive)
    	    editeEtatSonMp3(true);
    }
    
    /**
     * Son par défaut
     */
    public void editeSonDefautMp3(boolean sonActive)
    {
    	editeEtatSonMp3(false);
    	urlSon = urlSonDefaut;
    	sonMp3 = new GestionSonMP3Appli(urlSon);
	   	 if (sonActive)
		    editeEtatSonMp3(true);
    }
    
    public void editeEtatSonMp3(boolean active)
    {
        if (active && !sonMp3.etat())
        {
            sonMp3 = new GestionSonMP3Appli(urlSon);
        	sonMp3.start();
        } else {
        	sonMp3.arret();
        	sonMp3.interrupt();
        }
    }
	public void editeAntialiasing(boolean _modif)
	{
		antialiasing = _modif;
		setChanged();
		notifyObservers();
	}
	public void editeDoubleBufferingSoftware(boolean _modif)
	{
		doubleBufferingSoftware = _modif;
		setChanged();
		notifyObservers();
	}
	public void editePleinEcran(boolean _modif){pleinEcran = _modif;}
	public void editeTheme(int val){theme = val;}
	 public void editeValCarte(int taille, int valeur)
    {
    	switch(taille)
    	{
	    	case CarteModel.AS:
	    		valAs = valeur;
	    		break;
	    	case CarteModel.DEUX:
	    		val2 = valeur;
	    		break;
	    	case CarteModel.TROIS:
	    		val3 = valeur;
	    		break;
	    	case CarteModel.QUATRE:
	    		val4 = valeur;
	    		break;
	    	case CarteModel.CINQ:
	    		val5 = valeur;
	    		break;
	    	case CarteModel.SIX:
	    		val6 = valeur;
	    		break;
	    	case CarteModel.SEPT:
	    		val7 = valeur;
	    		break;
	    	case CarteModel.HUIT:
	    		val8 = valeur;
	    		break;
	    	case CarteModel.NEUF:
	    		val9 = valeur;
	    		break;
	    	case CarteModel.DIX:
	    		val10 = valeur;
	    		break;
	    	case CarteModel.VALET:
	    		valV = valeur;
	    		break;
	    	case CarteModel.DAME:
	    		valD = valeur;
	    		break;
	    	case CarteModel.ROI:
	    		valR = valeur;
	    		break;
	    	case CarteModel.JOKER:
	    		valJ = valeur;
	    		break;
    	}
    	setChanged();
    	notifyObservers();
    }
	 
	/* --------------- Retourne --------------- */
	public Color retourneCouleurTexteCarte(){ return couleurTexteCarte;}
	public int retourneThemeCarte(){return themeCarte;}
	public int retourneNumFondEcran(){return numFondEcran;}
	public Animations retourneEtatAnimation(){return etatAnimations;}
	public int retourneEtatTailleAvat(){return tailleAvat;}
	public int retourneEtatTailleAvatImage(){return tailleAvatImage;}
	public int retourneEtatTailleCarteX(){return tailleCarteX;}
	public int retourneEtatTailleCarteY(){return tailleCarteY;}
	public boolean retourneAntialiasing(){return antialiasing;}
	public boolean retourneInfoJoueur(){return infoJoueur;}
	public boolean retournePleinEcran(){return pleinEcran;}
	public String retourneSourceFondEcranPerso(){ return sourceImgPerso;}
	public boolean retourneDoubleBufferingSoftware(){return doubleBufferingSoftware;}
    public boolean retourneEtatSon(){return sonMp3.etat();}
    public boolean retourneEtatCourbePlateau() {return courbePlateau; }
    public boolean retourneEtatAnimationCarteJouer() {return animationCarteAJouer; }
    public int retourneTheme(){ return theme;}
    public int retourneFlou(){return flou;}
    public int retourneValCarte(int taille)
    {
    	int valCarte = 0;
    	switch(taille)
    	{
	    	case CarteModel.AS:
	    		valCarte = valAs;
	    		break;
	    	case CarteModel.DEUX:
	    		valCarte = val2;
	    		break;
	    	case CarteModel.TROIS:
	    		valCarte = val3;
	    		break;
	    	case CarteModel.QUATRE:
	    		valCarte = val4;
	    		break;
	    	case CarteModel.CINQ:
	    		valCarte = val5;
	    		break;
	    	case CarteModel.SIX:
	    		valCarte = val6;
	    		break;
	    	case CarteModel.SEPT:
	    		valCarte = val7;
	    		break;
	    	case CarteModel.HUIT:
	    		valCarte = val8;
	    		break;
	    	case CarteModel.NEUF:
	    		valCarte = val9;
	    		break;
	    	case CarteModel.DIX:
	    		valCarte = val10;
	    		break;
	    	case CarteModel.VALET:
	    		valCarte = valV;
	    		break;
	    	case CarteModel.DAME:
	    		valCarte = valD;
	    		break;
	    	case CarteModel.ROI:
	    		valCarte = valR;
	    		break;
	    	case CarteModel.JOKER:
	    		valCarte = valJ;
	    		break;
    	}
    	return valCarte;
    }
	
    /**
     * Singleton, retourne l'instance de l'objet
     * @return paramInstance
     * @throws IOException
     */
    public static GestionParametresAppli getInstance() throws IOException {
        if (paramInstance == null) {
        	paramInstance = new GestionParametresAppli();
        }
        return paramInstance;
    }
    

}