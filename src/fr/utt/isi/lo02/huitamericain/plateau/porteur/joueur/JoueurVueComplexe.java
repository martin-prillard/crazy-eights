package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.Avatar;
import fr.utt.isi.lo02.huitamericain.ihm.composant.SpringUtilities;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteVue;
import fr.utt.isi.lo02.huitamericain.plateau.carte.drag.GhostComponentAdapter;
import fr.utt.isi.lo02.huitamericain.plateau.carte.drag.GhostDropListener;
import fr.utt.isi.lo02.huitamericain.plateau.carte.drag.GhostMotionAdapter;

/**
* Vue complexe d'un joueur, soit avec toutes ces cartes visibles (retournées ou non)
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class JoueurVueComplexe extends AbstrJoueurVue {
	
	private boolean carteVertical = true;
	private boolean carteMasque = true;
	private GhostDropListener listenerCible;
	
	/**
	 * Constructeur de la classe JoueurVueComplexe
	 * @param _jc
	 * @param _carteVertical
	 */
	public JoueurVueComplexe(JoueurControlleur _jc, boolean _carteVertical, GhostDropListener listener)
	{
		super(_jc);
		setLayout(new BorderLayout());
		
		carteVertical = _carteVertical;
		carteMasque = jc.retourneAbstrJoueurModel().retourneCarteMasque();
		listenerCible = listener;
		avat = new Avatar(jc.retourneAbstrJoueurModel());
		donne = new JPanel();
		
		donne.setLayout(new SpringLayout());
		avat.setAlignmentX(CENTER_ALIGNMENT);
		avat.setAlignmentY(CENTER_ALIGNMENT);
		donne.setAlignmentX(CENTER_ALIGNMENT);
		donne.setAlignmentY(CENTER_ALIGNMENT);
		
		donne.setOpaque(false);
		setOpaque(false);
		
		JScrollPane conteneurDonne = new JScrollPane(donne);
		conteneurDonne.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		conteneurDonne.getViewport().setOpaque(false);
		conteneurDonne.setOpaque(false);
		
		if (carteVertical)
			add(avat, BorderLayout.WEST);
		else
			add(avat, BorderLayout.NORTH);
		add(conteneurDonne, BorderLayout.CENTER);
		actualiseVueDonneComplete();
	}
	
    /**
     * Fonction appelé lors de l'ajout d'une CarteModel dans la main du joueur
     * Sa carte vue respectif va ainsi être ajouter à la donne.
     */
	public void actualiseVueAjouteCarte(CarteModel crtM)
	{
		CarteVue carteVueTemp = new CarteVue(crtM);
		int index = 0;

		if (carteMasque)
			carteVueTemp.masquerCarte(true);
		if (!carteVertical)
			carteVueTemp.pivoterCarteHoryzontal();

		carteVueTemp.setAlignmentX(CENTER_ALIGNMENT);
		carteVueTemp.setAlignmentY(CENTER_ALIGNMENT);
		
		if (jc.retourneAbstrJoueurModel() instanceof JoueurHumain)
		{
			//Permet le déplacement à la souris de la carte
			GhostComponentAdapter componentAdapter = new GhostComponentAdapter(JeuVue.retourneGlassPaneGhost(), carteVueTemp, jc);
			GhostMotionAdapter ghostMotion = new GhostMotionAdapter(JeuVue.retourneGlassPaneGhost(), carteVueTemp);
			carteVueTemp.addMouseListener(componentAdapter);
			componentAdapter.addGhostDropListener(listenerCible); //On dicte la cible
			carteVueTemp.addMouseMotionListener(ghostMotion);
			carteVueTemp.addMouseListener(ghostMotion);
			
			carteVueTemp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
		        	CarteVue carteClicke = (CarteVue) arg0.getSource();
		        	try {
						jc.clickSurCarte(carteClicke.retourneCarteModel());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			//Rajoute la carte dans la liste avec un trie : on parcours la donne et on place la nouvelle carte au bon endroit
			for(int i = 0; i < donne.getComponentCount(); i++)
				if (((CarteVue) donne.getComponent(index)).retourneCarteModel().retourneTaille() < carteVueTemp.retourneCarteModel().retourneTaille())
					index++;
		}
		
		donne.add(carteVueTemp, index);
		actualiseSpringLayout();
	}
	
	public void masquerCartes(boolean b)
	{
		for(int i = 0; i < donne.getComponentCount(); i++)
		{
			CarteVue carteVueTemp = ((CarteVue) donne.getComponent(i));
			carteVueTemp.masquerCarte(b);
			if (!carteVertical)
				carteVueTemp.pivoterCarteHoryzontal();
		}
	}
	
	/**
	 * Fonction supprimant la vue de la CarteModel supprimé de la main du joueur
	 * On parcours la liste de carteVue et on supprime celle dont le modèle n'existe plus
	 * @param crtM
	 */
	public void actualiseVueSupprimeCarte(CarteModel crtM)
	{
		CarteVue carteASupprimer = null;

		for (Component compo : donne.getComponents())
		{
			CarteVue carte = ((CarteVue)compo);
			if (carte.retourneCarteModel() == crtM)
				carteASupprimer = carte;
		}
		carteASupprimer.desactiveTouteLesAnimations();
		carteASupprimer.removeAll();
		donne.remove(carteASupprimer);
		actualiseSpringLayout();
	}

	public void actualiseSpringLayout()
	{
		try{
			if (carteVertical)
				SpringUtilities.makeCompactGrid(donne, 1, donne.getComponentCount(), 0, 0, -GestionParametresAppli.getInstance().retourneEtatTailleCarteX()/2, 0);
			else
				SpringUtilities.makeCompactGrid(donne, donne.getComponentCount(), 1, 0, 0, 0, -GestionParametresAppli.getInstance().retourneEtatTailleCarteX()/2);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Actualise l'affichage de toutes les cartes du joueurs
	 */
	public void actualiseVueDonneComplete()
	{
		donne.removeAll();
		for (CarteModel iCarteM : jc.retourneAbstrJoueurModel().retourneVectCarte())
			actualiseVueAjouteCarte(iCarteM);
	}
	
	/**
	 * Mis a jour de la vue
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		//Masque ou démasque les cartes du joueur
		if(carteMasque != jc.retourneAbstrJoueurModel().retourneCarteMasque())
		{
			carteMasque = jc.retourneAbstrJoueurModel().retourneCarteMasque();
			masquerCartes(carteMasque);
		} else if (arg instanceof CarteModel){
			//Actualise l'ajout ou la suppression d'une carte de la main du joueur
			if (jc.retourneAbstrJoueurModel().retourneEtatAjoutRetireCarte())
				actualiseVueAjouteCarte((CarteModel) arg);
			else
				actualiseVueSupprimeCarte((CarteModel) arg);
		} else if (arg instanceof Boolean)
		{
			//On rend visible le boutton "Carte !" si il reste deux cartes
			avat.editeVisibleDerCarte((Boolean) arg);
		}
		
		repaint();
	}
	
}