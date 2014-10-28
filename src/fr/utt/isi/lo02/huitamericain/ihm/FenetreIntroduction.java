package fr.utt.isi.lo02.huitamericain.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelReflection;

/**
* JFrame de présentation au demmarage de l'application
* Execute et affiche l'état du chargement des images
* 
* @author Martin Prillard
* @see GestionImagesAppli
*/
@SuppressWarnings("serial")
public class FenetreIntroduction extends JFrame implements Runnable{
    private final static int LARGEUR = 550;
    private final static int HAUTEUR = 375;
    private JLabel infoTelechargement = new JLabel();
    private JProgressBar pChargement = new JProgressBar(0, 100);

    /**
     * Contructeur
     * @throws IOException
     */
    public FenetreIntroduction() throws IOException {
    	Thread tChargement = new Thread(this);
    	tChargement.start();
        setTitle("Chargement...");
        setSize(LARGEUR, HAUTEUR);
        setResizable(false);   
        setUndecorated(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        
        //Initialisation des composants
        attribComp();
        
        //Chargement du logo de l'application
        GestionImagesAppli.getInstance().initChargementLogo();
        setIconImage(GestionImagesAppli.getInstance().retourneImgLogo());
        
        setVisible(true);
    }

    /**
     * Initialisation des composants graphiques de la JFrame
     */
    public void attribComp() {
    	try {
			GestionImagesAppli.getInstance().initChargementImageIntro();
			PanelReflection panelReflec = new PanelReflection(GestionImagesAppli.getInstance().retourneImgIntro());
	    	panelReflec.setLayout(new BorderLayout());
	    	pChargement.setStringPainted(true);
	    	pChargement.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JProgressBar progress = (JProgressBar)e.getSource();
					progress.setString((int)(progress.getPercentComplete()*100) + " %");
				}
			});
	    	infoTelechargement.setOpaque(false);
	    	infoTelechargement.setForeground(Color.darkGray);
	    	panelReflec.add(infoTelechargement, BorderLayout.SOUTH);
	    	add(pChargement, BorderLayout.SOUTH);
	        add(panelReflec, BorderLayout.CENTER);
		} catch (IOException e1) {e1.printStackTrace();}
    }

    /**
     * Lance le chargement des images en parallèle dans un thread
     */
	@Override
	public void run() {
        try {
        	GestionParametresAppli.getInstance().initChargementParametres();
        	GestionImagesAppli.getInstance().chargementImage(pChargement, infoTelechargement);
        	
    		//Création de la vue principale du jeu
        	new JeuVue(new JeuControlleur(new JeuModel()));
    		
    		//Destruction de l'objet
    		FenetreIntroduction.this.dispose();
            
		} catch (IOException e) {e.printStackTrace();}
	}
}
