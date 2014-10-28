package fr.utt.isi.lo02.huitamericain.plateau;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueStatVue;
import fr.utt.isi.lo02.huitamericain.plateau.carte.drag.GhostDropManagerDemo;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurControlleur;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurVueComplexe;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurVueSimple;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche.PiocheVue;

/**
* Vue A du plateau
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class PlateauVueA extends AbstrPlateauVue implements Observer{

	/**
	 * Constructeur de la vue A
	 * @param _platMod
	 * @param _jv
	 */
	public PlateauVueA(PlateauModel _platMod, JeuVue _jv)
	{
		super(_platMod, _jv);	
		nbJoueur = platMod.retourneNbJoueur();
		initPlacementPioche();
		initJoueurSurPlateau((JoueurHumain) platMod.retourneJoueurCourant());
		initPlacementJoueur();
		initLayout();
	}
	
	/**
	 * Initialisation de la pioche
	 */
	public void initPlacementPioche()
	{
		zoneCentral.setLayout(new BorderLayout());
		zonePioche.setLayout(new GridBagLayout());
		zoneJHaut.setLayout(new BoxLayout(zoneJHaut, BoxLayout.LINE_AXIS));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		//Pioche
		piocheM = platMod.retourneModelPioche();
		piocheV = new PiocheVue(piocheC);
		zonePioche.add(piocheV, gbc);
		listener = new GhostDropManagerDemo(piocheV.retourneZoneCarteVue());
	}
	
	/**
	 * Initialisation des joueur sur le plateau en fonction du joueur humain courant
	 */
	public void initJoueurSurPlateau(JoueurHumain jh)
	{
		AbstrJoueurVue v;
		int indexJoueurCourant = 0;
		listJoueurs = null;
		listJoueurs = new ArrayList<AbstrJoueurVue>();

		//On place le joueur humain courrant en tête de liste
		indexJoueurCourant = platMod.retourneListJoueur().indexOf(jh);
		v = new JoueurVueComplexe(new JoueurControlleur(jh, platMod, jv), true, listener);
		bType = BorderFactory.createTitledBorder(jh.retourneNom());
		v.setBorder(bType);
		listJoueurs.add(v);

		//Ajout des joueurs se trouvant après le joueur courant
		for(int i = indexJoueurCourant+1; i < platMod.retourneListJoueur().size(); i++)	
		{
			v = new JoueurVueSimple(new JoueurControlleur(platMod.retourneListJoueur().get(i), platMod, jv));
			bType = BorderFactory.createTitledBorder(platMod.retourneListJoueur().get(i).retourneNom());
			v.setBorder(bType);
			listJoueurs.add(v);
		}

		//Ajout des joueurs se trouvant avant le joueur courant, si il y en a
		if (indexJoueurCourant > 0)
			for(int i = 0; i < indexJoueurCourant; i++)	
			{
				v = new JoueurVueSimple(new JoueurControlleur(platMod.retourneListJoueur().get(i), platMod, jv));
				bType = BorderFactory.createTitledBorder(platMod.retourneListJoueur().get(i).retourneNom());
				v.setBorder(bType);
				listJoueurs.add(v);
			}
	}
	
	/**
	 * Initialisation des joueurs
	 */
	public void initPlacementJoueur()
	{
		zoneJHaut.add(Box.createHorizontalGlue());
		for(int i = 1; i < nbJoueur; i++)
		{
			zoneJHaut.add(listJoueurs.get(i));
			zoneJHaut.add(Box.createHorizontalGlue());
		}
	}
	
	/**
	 * Initialisation des layout
	 */
	public void initLayout()
	{	
		zoneVideVertical.setPreferredSize(new Dimension(200,0));
		zoneVideVertical2.setPreferredSize(new Dimension(200,0));
		
		zoneVideVertical.setOpaque(false);
		zoneVideVertical2.setOpaque(false);
		zoneCentral.setOpaque(false);
		zonePioche.setOpaque(false);
		zoneJHaut.setOpaque(false);
		
		zoneCentral.add(zoneVideVertical, BorderLayout.WEST);
		zoneCentral.add(zonePioche, BorderLayout.CENTER);
		zoneCentral.add(zoneVideVertical2, BorderLayout.EAST);
		curv.add(zoneJHaut, BorderLayout.NORTH);
		curv.add(zoneCentral, BorderLayout.CENTER);
		curv.add(listJoueurs.get(0), BorderLayout.SOUTH);
		add(curv);
	}
	
	/**
	 * Affiche le joueur courant comme vue complexe, et les autres joueurs en vue simple
	 * @param jh
	 */
	public void roulementPlateau(JoueurHumain jh)
	{
		zoneJHaut.removeAll();
		curv.remove(listJoueurs.get(0));
		
		initJoueurSurPlateau(jh);
		initPlacementJoueur();
		
		curv.add(listJoueurs.get(0), BorderLayout.SOUTH);
		
		validate();
	}
	
	/**
	 * Mis a jour de la vue, roulement des joueurs si nécessaire
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof GestionParametresAppli)
		{
			actualisePlateauParametres();
		}else if (o instanceof PlateauModel)
		{
			if (arg instanceof JoueurHumain)
			{
				roulementPlateau((JoueurHumain) arg);
			}else{
				PlateauModel platMod = (PlateauModel) o;
				//Si c'est la fin de la partie ou la fin de la manche, on affiche le menu
				try {
					if(platMod.retourneFinDePartie())
						new DialogVueStatVue(jv, platMod.retourneStatModel(), 0, 0);
					else if (platMod.retourneFinDeManche())
						new DialogVueStatVue(jv, platMod.retourneStatModel(), 0);
				} catch (IOException e) {e.printStackTrace();}
				
			}
		}
	}
}