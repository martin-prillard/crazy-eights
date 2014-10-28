package fr.utt.isi.lo02.huitamericain.ihm;

import java.awt.Frame;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.swingx.JXFrame;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueCreationPartie;
import fr.utt.isi.lo02.huitamericain.plateau.AbstrPlateauVue;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauVueA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauVueB;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauVueC;
import fr.utt.isi.lo02.huitamericain.plateau.carte.drag.GhostGlassPane;

/**
* Vue du jeu, 
* Gère l'affichage de la structure du programme (menu, etc..)
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class JeuVue extends JXFrame implements Observer{

	private final static int LARGEUR = 1024;
	private final static int HAUTEUR = 700;
	private static GhostGlassPane glassPaneGhost = new GhostGlassPane();
    private AbstrPlateauVue plateau;
    private JeuControlleur jc;
    private Menu menuPerso;
	
    /**
     * Constructeur de la vue du jeu
     * @param _jc
     * @throws IOException
     */
	public JeuVue(JeuControlleur _jc) throws IOException {
		super("Crazy Eight");
 
		plateau = new AbstrPlateauVue();
		jc = _jc;
		jc.addObserver(this);
	
		menuPerso = new Menu(this);
		setJMenuBar(menuPerso);
		setGlassPane(glassPaneGhost);
		setSize(LARGEUR, HAUTEUR);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(plateau);

		try {
			GestionParametresAppli.getInstance().editeEtatSonMp3(true);
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(JeuVue.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(JeuVue.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(JeuVue.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(JeuVue.class.getName()).log(Level.SEVERE, null, ex);
		}
		
        setIconImage(GestionImagesAppli.getInstance().retourneImgLogo());
        setVisible(true);
        
        new DialogVueCreationPartie(this, false, false);
        
	}
     
	/**
	 * Change la vue du plateau en fonction des préférences de l'utilisateur
	 * @throws IOException
	 */
    public void changePlateau() throws IOException
    {
    	//On détruit explicitement l'ancien plateau
    	plateau.removeAll();
    	this.remove(plateau);
    	plateau = null;
    	//Appelle explicite du Garbadge collector
    	System.gc();
    	
    	switch(GestionParametresAppli.getInstance().retourneTheme())
		{
    		case 1 : 
    			plateau = new PlateauVueA(jc.retournePartie().retournePlateauMod(), this);
    			break;
    		case 2:
    			plateau = new PlateauVueB(jc.retournePartie().retournePlateauMod(), this);
    			break;
    		case 3:
    			plateau = new PlateauVueC(jc.retournePartie().retournePlateauMod(), this);
    			break;
		}
    	
    	setContentPane(plateau);
    	this.validate();
    }
    
    /**
     * Appelé par le controlleur de la vue,
     * change la vue du plateau
     */
	@Override
	public void update(Observable arg0, Object arg1) {
		//Déverouille les éléments du menu concernant la partie
		menuPerso.activerElementMenu();
		//Change la vue du plateau
		try {
			changePlateau();
		} catch (IOException e) {e.printStackTrace();}
	}  
    
    
    public AbstrPlateauVue retournePlateau(){return plateau;}
	public static GhostGlassPane retourneGlassPaneGhost(){return glassPaneGhost;} 
    public JeuControlleur retourneJeuCon(){return jc;}
    
}