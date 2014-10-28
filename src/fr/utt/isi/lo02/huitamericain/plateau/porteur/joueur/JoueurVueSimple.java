package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JPanel;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.Avatar;
import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelReflection;

/**
* Vue simple d'un joueur
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class JoueurVueSimple extends AbstrJoueurVue {

	private final static int LARGEUR = 145;
	private final static int HAUTEUR = 390;
	private PanelReflection carteAffiche;
	private JPanel zoneCarte = new JPanel(new BorderLayout());
	private JPanel paneVide = new JPanel();
	
	/**
	 * Constructeur de la vue simple
	 * @param _jc
	 */
	public JoueurVueSimple(JoueurControlleur _jc)
	{
		super(_jc);
		setLayout(new BorderLayout());
		avat = new Avatar(jc.retourneAbstrJoueurModel());
		avat.setPreferredSize(new Dimension(125,125));
		avat.retourneAvatarImage().setPreferredSize(new Dimension(100,100));
		
		try {
			carteAffiche = new PanelReflection(GestionImagesAppli.getInstance().retourneImgJVSimple(), " "+jc.retourneAbstrJoueurModel().retourneNbCarte());
			carteAffiche.editeTexteInfo(" "+jc.retourneAbstrJoueurModel().retourneNbCarte());
		} catch (IOException e) {e.printStackTrace();}

		paneVide.setOpaque(false);
		zoneCarte.setOpaque(false);
		setOpaque(false);
		
		paneVide.setPreferredSize(new Dimension(7,0));
		setPreferredSize(new Dimension(LARGEUR,HAUTEUR));
		setMaximumSize(new Dimension(LARGEUR,HAUTEUR));
		  
		zoneCarte.add(paneVide, BorderLayout.WEST);
		zoneCarte.add(carteAffiche, BorderLayout.CENTER);
		
		add(avat, BorderLayout.NORTH);
		add(zoneCarte, BorderLayout.CENTER);

	}

	/**
	 * Actualise l'affichage du nombre de carte
	 * @param _nbCarte
	 */
	public void actualiseNbCarte(int _nbCarte)
	{
		carteAffiche.editeTexteInfo(" "+_nbCarte);
		if(_nbCarte == 0)
			carteAffiche.setVisible(false);
	}
	
	/**
	 * Mis à jour
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		if (arg instanceof Boolean)
			avat.editeVisibleDerCarte((Boolean) arg);
		else
			actualiseNbCarte(jc.retourneAbstrJoueurModel().retourneNbCarte());
	}
	
}
