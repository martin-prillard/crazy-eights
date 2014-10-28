package fr.utt.isi.lo02.huitamericain.ihm.chargement;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Gestion du chargement des images du jeu,
* chargé au lancement du programme
* (Singleton)
* 
* @author Martin Prillard
*/
public class GestionImagesAppli {

    private ClassLoader classLoader = getClass().getClassLoader();
    private static GestionImagesAppli stockInstance = null;
    //Liste d'image
    private List<Image> vectImageCarteTheme1;
    private List<Image> vectImageCarteTheme2;
    private List<Image> vectImageFondEcran;
    private List<Image> vectImageDialog;
    private List<Image> vectImageMenu;
    private List<Image> vectImageSens;
    private List<Image> vectImageJoueur;
    private List<Image> vectImageCouleur;
    private Image intro;
    private Image logo;

    /**
     * Constructeur privé (Singleton)
     * Initialise les liste
     * @throws IOException
     */
    private GestionImagesAppli() throws IOException {
    	vectImageCarteTheme1 = new ArrayList<Image>();
    	vectImageCarteTheme2 = new ArrayList<Image>();
    	vectImageFondEcran = new ArrayList<Image>();
    	vectImageDialog = new ArrayList<Image>();
    	vectImageMenu = new ArrayList<Image>();  
    	vectImageSens = new ArrayList<Image>();  
    	vectImageJoueur = new ArrayList<Image>();  
    	vectImageCouleur = new ArrayList<Image>(); 
    }

    /**
     * Charge les images en mémoire dans les listes attribuées
     * @param pChargement
     * @param infoTelechargement
     * @throws IOException
     */
    public void chargementImage(JProgressBar pChargement, JLabel infoTelechargement) throws IOException
    {
    	int cptBar = 2;
    	
    	//Ajout des cartes : Thèmes un et deux
        for (int j = 0; j < 56; j++) {
            vectImageCarteTheme1.add(ImageIO.read(classLoader.getResource("Img/Cartes/Theme1/idCarte (" + j + ").png")));
            infoTelechargement.setText(classLoader.getResource("Img/Cartes/Theme1/idCarte (" + j + ").png").getFile());
            vectImageCarteTheme2.add(ImageIO.read(classLoader.getResource("Img/Cartes/Theme2/idCarte (" + j + ").png")));
            infoTelechargement.setText(classLoader.getResource("Img/Cartes/Theme2/idCarte (" + j + ").png").getFile());
            pChargement.setValue(cptBar++);
        }
        
    	//Ajout des joueurs
        for (int j = 0; j < 7; j++) {
            vectImageJoueur.add(ImageIO.read(classLoader.getResource("Img/Joueurs/joueur (" + j + ").png")));
            pChargement.setValue(cptBar++);
            infoTelechargement.setText(classLoader.getResource("Img/Joueurs/joueur (" + j + ").png").getFile());
        }
        for (int j = 7; j < 15; j++) {
            vectImageJoueur.add(ImageIO.read(classLoader.getResource("Img/Joueurs/joueur (7).png")));
            pChargement.setValue(cptBar++);
            infoTelechargement.setText(classLoader.getResource("Img/Joueurs/joueur (7).png").getFile());
        }
        
    	//Ajout des images sens
        for (int j = 0; j < 2; j++) {
            vectImageSens.add(ImageIO.read(classLoader.getResource("Img/Sens/sens (" + j + ").png")));
            pChargement.setValue(cptBar++);
            infoTelechargement.setText(classLoader.getResource("Img/Sens/sens (" + j + ").png").getFile());
        }
    	//Ajout des fonds d'écran
        for (int j = 0; j < 5; j++) {
        	vectImageFondEcran.add(ImageIO.read(classLoader.getResource("Img/Fonds/sol (" + j + ").jpg")));
        	pChargement.setValue(cptBar++);
        	infoTelechargement.setText(classLoader.getResource("Img/Fonds/sol (" + j + ").jpg").getFile());
        }
    	//Ajout des dialogs
        for (int j = 0; j < 2; j++) {
            vectImageDialog.add(ImageIO.read(classLoader.getResource("Img/Dialog/dia (" + j + ").png")));
            pChargement.setValue(cptBar++);
            infoTelechargement.setText(classLoader.getResource("Img/Dialog/dia (" + j + ").png").getFile());
        }
    	//Ajout des menus
        for (int j = 0; j < 11; j++) {
            vectImageMenu.add(ImageIO.read(classLoader.getResource("Img/Menu/menu (" + j + ").png")));
            pChargement.setValue(cptBar++);
            infoTelechargement.setText(classLoader.getResource("Img/Menu/menu (" + j + ").png").getFile());
        }
    	//Ajout des couleurs
        for (int j = 0; j < 8; j++) {
            vectImageCouleur.add(ImageIO.read(classLoader.getResource("Img/Couleurs/couleur (" + j + ").png")));
            pChargement.setValue(cptBar++);
            infoTelechargement.setText(classLoader.getResource("Img/Couleurs/couleur (" + j + ").png").getFile());
        }
    }
    
    /**
     * Chargement du logo
     * @throws IOException
     */
    public void initChargementLogo() throws IOException
    {
        //Ajout du logo
        logo = ImageIO.read(classLoader.getResource("Img/Logo/logo.png"));
    }
    
    /**
     * Chargement de l'image d'intro
     * @throws IOException
     */
    public void initChargementImageIntro() throws IOException
    {
        //Ajout du logo
        intro = ImageIO.read(classLoader.getResource("Img/Intro/intro.png"));
    }
    
    /**
     * Retourne l'image de la carte concerné en fonction de sa taille et sa couleur
     * @param _taille
     * @param _couleur
     * @return Image de la carte
     * @throws IOException
     */
    public Image retourneImgCarte(int _taille, int _couleur) throws IOException {
    	int index = 0;
    	
    	switch(_taille)
    	{
	    	case CarteModel.AS:
	    		index = 0;
	    		break;
	    	case CarteModel.DEUX:
	    		index = 4;
	    		break;
	    	case CarteModel.TROIS:
	    		index = 8;
	    		break;
	    	case CarteModel.QUATRE:
	    		index = 12;
	    		break;
	    	case CarteModel.CINQ:
	    		index = 16;
	    		break;
	    	case CarteModel.SIX:
	    		index = 20;
	    		break;
	    	case CarteModel.SEPT:
	    		index = 24;
	    		break;
	    	case CarteModel.HUIT:
	    		index = 28;
	    		break;
	    	case CarteModel.NEUF:
	    		index = 32;
	    		break;
	    	case CarteModel.DIX:
	    		index = 36;
	    		break;
	    	case CarteModel.VALET:
	    		index = 40;
	    		break;
	    	case CarteModel.DAME:
	    		index = 44;
	    		break;
	    	case CarteModel.ROI:
	    		index = 48;
	    		break;
	    	case CarteModel.JOKER:
	    		index = 52;
	    		break;
    	}
    	
    	switch(_couleur)
    	{
	    	case CarteModel.CARREAU:
	    		index++;
	    		break;
	    	case CarteModel.COEUR:
	    		index = index + 2;
	    		break;
	    	case CarteModel.PIQUE:
	    		index = index + 3;
	    		break;
    	}
    	
    	Image imgCarte = null;
    	
    	//En fonction du thème du paquet de carte
    	if (GestionParametresAppli.getInstance().retourneThemeCarte() == 1)
    		imgCarte = vectImageCarteTheme1.get(index);
    	else if (GestionParametresAppli.getInstance().retourneThemeCarte() == 2)
    		imgCarte = vectImageCarteTheme2.get(index);
    	
    	return imgCarte;
    }
    
    public Image retourneImgCarteCache() throws IOException
    {
    	Image imgCarte = null;
    	
    	//En fonction du thème du paquet de carte
    	if (GestionParametresAppli.getInstance().retourneThemeCarte() == 1)
    		imgCarte = vectImageCarteTheme1.get(53);
    	else if (GestionParametresAppli.getInstance().retourneThemeCarte() == 2)
    		imgCarte = vectImageCarteTheme2.get(53);
    	
    	return imgCarte;
    }
    
    public Image retourneImgPioche() throws IOException
    {
    	Image imgCarte = null;
    	
    	//En fonction du thème du paquet de carte
    	if (GestionParametresAppli.getInstance().retourneThemeCarte() == 1)
    		imgCarte = vectImageCarteTheme1.get(54);
    	else if (GestionParametresAppli.getInstance().retourneThemeCarte() == 2)
    		imgCarte = vectImageCarteTheme2.get(54);
    	
    	return imgCarte;
    }
    
    public Image retourneImgJVSimple() throws IOException
    {
    	Image imgCarte = null;
    	
    	//En fonction du thème du paquet de carte
    	if (GestionParametresAppli.getInstance().retourneThemeCarte() == 1)
    		imgCarte = vectImageCarteTheme1.get(55);
    	else if (GestionParametresAppli.getInstance().retourneThemeCarte() == 2)
    		imgCarte = vectImageCarteTheme2.get(55);
    	
    	return imgCarte;
    }
    
    public Image retourneImgFondEcran(int _index) throws IOException {
        return vectImageFondEcran.get(_index);
    }
    
    public Image retourneImgCouleur(int _index) throws IOException {
        return vectImageCouleur.get(_index);
    }
    
    public Image retourneImgSens(int _index) throws IOException {
        return vectImageSens.get(_index);
    }
    
    public Image retourneImgDialog(int _index) throws IOException {return vectImageDialog.get(_index);}
    
    public Image retourneImgMenu(int _index) throws IOException {return vectImageMenu.get(_index);}
    
    public Image retourneImgJoueur(int _index) throws IOException {return vectImageJoueur.get(_index);}
    
    public Image retourneImgLogo() throws IOException {return logo;}
    
    public Image retourneImgIntro() throws IOException {return intro;}

    
    /**
     * Change l'image avatar du joueur
     * @param _index
     * @param _source
     * @throws IOException
     */
    public void editeImgJoueur(int _index, String _source) throws IOException
    {
    	vectImageJoueur.set(_index, ImageIO.read(new File(_source)));
    }
    
    /**
     * Reinitialise les images avatar des joueurs en mémoire
     * @throws IOException
     */
    public void reinitialiseImageAvatar() throws IOException
    {
        for (int j = 0; j < 7; j++) {
            vectImageJoueur.add(j, ImageIO.read(classLoader.getResource("Img/Joueurs/joueur (" + j + ").png")));
        }
        for (int j = 7; j < 15; j++) {
            vectImageJoueur.set(j, ImageIO.read(classLoader.getResource("Img/Joueurs/joueur (7).png")));
        }
    }
    
    /**
     * Retourne l'instance de l'objet (Singleton)
     * @return instance GestionImagesAppli
     * @throws IOException
     */
    public static GestionImagesAppli getInstance() throws IOException {
        if (stockInstance == null) {
            stockInstance = new GestionImagesAppli();
        }
        return stockInstance;
    }
    
}