package fr.utt.isi.lo02.huitamericain.plateau;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.Border;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.CourbeAnim;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueStatVue;
import fr.utt.isi.lo02.huitamericain.plateau.carte.drag.GhostDropListener;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche.PiocheControlleur;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche.PiocheModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche.PiocheVue;

/**
* Vue abstraite du plateau
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class AbstrPlateauVue extends JPanel implements Observer{

	protected PlateauModel platMod;
	protected GhostDropListener listener;
    protected int nbJoueur;
	protected JPanel zoneJGauche = new JPanel();
	protected JPanel zoneJBas = new JPanel();
	protected JPanel zoneJDroite = new JPanel();
	protected JPanel zoneJHaut = new JPanel();
	protected JPanel zoneVideCentral = new JPanel();
	protected JPanel zoneCentral = new JPanel();
	protected JPanel zoneVideVertical = new JPanel();
	protected JPanel zoneVideVertical2 = new JPanel();
	protected JPanel zoneCarteSommet = new JPanel();
	protected JPanel zonePioche = new JPanel();
	protected PiocheModel piocheM;
	protected PiocheVue piocheV;
	protected Border bType;
	protected ArrayList<AbstrJoueurVue> listJoueurs;   	
	protected boolean antialiasing = false;
	protected boolean doubleBufferingSoftware = false;
	protected CourbeAnim curv;
	protected boolean courbePlateau = false;
	protected JeuVue jv;
	protected PiocheControlleur piocheC;
	//Double Buffering Software
    private RenderingThreadSoftware renderingThreadSoft = new RenderingThreadSoftware();// boucle d'affichage
    protected Image iFond; // image mémoire correspondante au buffer
	
    
    /**
     * Constructeur correspondant à un simple plateau vide
     */
	public AbstrPlateauVue()
	{
		setLayout(new BorderLayout());
		curv = new CourbeAnim(30);
		curv.setLayout(new BorderLayout());

		try {
			GestionParametresAppli.getInstance().addObserver(this);
			int fond = GestionParametresAppli.getInstance().retourneNumFondEcran();
			if (fond < 5)
				iFond = GestionImagesAppli.getInstance().retourneImgFondEcran(fond);
			else
				EditImageExterieur(GestionParametresAppli.getInstance().retourneSourceFondEcranPerso());
		} catch (IOException e) {e.printStackTrace();}
		
		iFond.getGraphics();
		add(curv);
		actualisePlateauParametres();
	}
	
	/**
	 * Constructeur de la vue d'un PlateauModel
	 * @param _platMod
	 * @param _jv
	 */
	public AbstrPlateauVue(PlateauModel _platMod, JeuVue _jv)
	{
		setLayout(new BorderLayout());
		jv = _jv;
		curv = new CourbeAnim(30);
		curv.setLayout(new BorderLayout());
		platMod = _platMod;
		platMod.addObserver(this);
		
		piocheC = new PiocheControlleur(platMod.retourneModelPioche(), platMod, jv);
		
		try {
			GestionParametresAppli.getInstance().addObserver(this);
			int fond = GestionParametresAppli.getInstance().retourneNumFondEcran();
			if (fond < 5)
			{
				iFond = GestionImagesAppli.getInstance().retourneImgFondEcran(fond);
				iFond.getGraphics();
			}
			else
				EditImageExterieur(GestionParametresAppli.getInstance().retourneSourceFondEcranPerso());
		} catch (IOException e) {e.printStackTrace();}
		
		actualisePlateauParametres();
	}
	

	/**
	 * Affichage
	 */
    @Override
    public void paintComponent(Graphics g) {  
	      int i = getWidth();
	      int j = getHeight();
	      
	       Graphics2D g2 = (Graphics2D) g;
	       
	       
	      if (antialiasing)
	      {
		    	RenderingHints hints = createRenderingHints();
		        g2.setRenderingHints(hints);
	      }else{
	    	  /** Désactivation de l'anti-aliasing */
	    	  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	    	  g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	    	  /** Demande de rendu rapide */
	    	  g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	    	  g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
	    	  g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
	    	  g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
	      }
	      
	      g.drawImage(iFond, 0, 0, i, j, this); //Dessine l'image sur le panel
    }
    
    /**
     * fonction pour changer directement l'image de fond avec une image en mémoire
     */
    public void EditImage(Image _img) throws IOException {
        iFond = _img;
        this.repaint();
    }
    
    /**
     * fonction pour changer directement l'image de fond avec une image choisie par l'utilisateur
     */
    public void EditImageExterieur(String url) throws IOException {
    	iFond = getToolkit().getImage(url);
        this.repaint();
    }
    
    /**
     * Active le double buffering software
     */
    @SuppressWarnings("deprecation")
	public void activerDoubleBufferingSoftware()
    {
    	if (renderingThreadSoft.isAlive())
    		renderingThreadSoft.resume();
    	else
    		renderingThreadSoft.start();
    }
    
    /**
     * Désactive le double buffering software
     */
    @SuppressWarnings("deprecation")
	public void desactiverDoubleBufferingSoftware()
    {
    	if (renderingThreadSoft.isAlive())
    		renderingThreadSoft.suspend();
    }
    
    
    /**
     * Antialiasing
     * @return RenderingHints
     */
    protected RenderingHints createRenderingHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        return hints;
    }


    /**
     * Double Buffering Software
     * @author Martin Prillard
     *
     */
    class RenderingThreadSoftware extends Thread {
        /**
         *  Ce thread appelle le rafraichissement du plateau
         *  toutes les 10 milli-secondes
         */
         public void run(){
            while(true){
               try {
                  repaint(); 
                  sleep( 10 );
               } catch ( Exception e ) {} 
            }
         }
      }   
    
	
    /**
     * Actialise le plateau en fonction des choix de l'utilisateur
     * (Antialiasing, double buff.soft.)
     */
    public void actualisePlateauParametres()
    {
		try {
			antialiasing = GestionParametresAppli.getInstance().retourneAntialiasing();
			doubleBufferingSoftware = GestionParametresAppli.getInstance().retourneDoubleBufferingSoftware();
			courbePlateau = GestionParametresAppli.getInstance().retourneEtatCourbePlateau();
		} catch (IOException e) {e.printStackTrace();}	
		
		if (doubleBufferingSoftware)
			activerDoubleBufferingSoftware();
		else
			desactiverDoubleBufferingSoftware();
		
		if (courbePlateau)
			curv.reactiver();
		else
			curv.desactiver();
		
		repaint();
    }
    
    /**
     * Met à jour la vue
     */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof GestionParametresAppli)
			actualisePlateauParametres();
		else if (o instanceof PlateauModel && !(arg instanceof JoueurHumain))
		{
			PlateauModel platMod = (PlateauModel) o;
			
			//Si c'est la fin de la partie ou la fin de la manche, on affiche le menu
			if(platMod.retourneFinDePartie())
			{
				try {
					new DialogVueStatVue(jv, platMod.retourneStatModel(), 0, 0);
				} catch (IOException e) {e.printStackTrace();}
			}else if (platMod.retourneFinDeManche()){
				try {
					new DialogVueStatVue(jv, platMod.retourneStatModel(), 0);
				} catch (IOException e) {e.printStackTrace();}
			}
		}
	}
}
