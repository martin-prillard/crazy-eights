package fr.utt.isi.lo02.huitamericain.plateau;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.plateau.carte.drag.GhostDropManagerDemo;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurControlleur;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurVueComplexe;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche.PiocheVue;

/**
* Vue B du plateau
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class PlateauVueB extends AbstrPlateauVue{
	
	/**
	 * Constructeur de la vue B
	 * @param _platMod
	 * @param _jv
	 */
	public PlateauVueB(PlateauModel _platMod, JeuVue _jv)
	{
		super(_platMod, _jv);	
		initLayoutPlateau();
		initPlacementPioche();
		initJoueurSurPlateau();
		initPlacementJoueur();
		
		setOpaque(false);
	}

	/**
	 * Initialisation des joueurs sur le plateau
	 */
	public void initJoueurSurPlateau()
	{
		int cpt = 0;
		JoueurVueComplexe v;
		nbJoueur = platMod.retourneNbJoueur();
		listJoueurs = new ArrayList<AbstrJoueurVue>();
		
		for(AbstrJoueurModel iJoueur : platMod.retourneListJoueur())
		{
			cpt = platMod.retourneListJoueur().indexOf(iJoueur);
			if(iJoueur instanceof JoueurHumain)
			{
				if (cpt == 1 || cpt == 3 || cpt == 5 || cpt == 7)
					v = new JoueurVueComplexe(new JoueurControlleur(iJoueur, platMod, jv), false, listener);
				else
					v = new JoueurVueComplexe(new JoueurControlleur(iJoueur, platMod, jv), true,  listener);
			}else{
				if (cpt == 1 || cpt == 3 || cpt == 5 || cpt == 7)
					v = new JoueurVueComplexe(new JoueurControlleur(iJoueur, platMod, jv), false, listener);
				else
					v = new JoueurVueComplexe(new JoueurControlleur(iJoueur, platMod, jv), true, listener);
			}
			bType = BorderFactory.createTitledBorder(iJoueur.retourneNom());
			v.setBorder(bType);
			listJoueurs.add(v);
		}
	}
	
	/**
	 * Initialisation des layouts
	 */
	public void initLayoutPlateau()
	{
		zoneJGauche.setLayout(new BorderLayout());
		zoneJBas.setLayout(new BorderLayout());
		zoneJDroite.setLayout(new BorderLayout());
		zoneJHaut.setLayout(new BorderLayout());
		zoneCentral.setLayout(new BorderLayout());
		zoneVideCentral.setLayout(new BorderLayout());
		zonePioche.setLayout(new GridBagLayout());
	}
	
	/**
	 * Initialisation de la pioche
	 */
	public void initPlacementPioche()
	{
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
	 * Initialisation des joueur
	 */
	public void initPlacementJoueur()
	{	
		//Switch en cascade
		switch (nbJoueur)
		{
			case 8:
				zoneJDroite.add(listJoueurs.get(7), BorderLayout.EAST);
				listJoueurs.get(7).pivoterVertical();
				listJoueurs.get(7).setOpaque(false);
			case 7:
				zoneJHaut.add(listJoueurs.get(6), BorderLayout.NORTH);
				listJoueurs.get(6).setOpaque(false);
			case 6:
				zoneJGauche.add(listJoueurs.get(5), BorderLayout.WEST);
				listJoueurs.get(5).pivoterVertical();
				listJoueurs.get(5).setOpaque(false);
			case 5:
				zoneJBas.add(listJoueurs.get(4), BorderLayout.SOUTH);
				listJoueurs.get(4).setOpaque(false);
			case 4:
				zoneJDroite.add(listJoueurs.get(3), BorderLayout.WEST);
				listJoueurs.get(3).pivoterVertical();
				listJoueurs.get(3).setOpaque(false);
				zoneJDroite.setOpaque(false);
			case 3:
				zoneJHaut.add(listJoueurs.get(2), BorderLayout.SOUTH);
				listJoueurs.get(2).setOpaque(false);
				zoneJHaut.setOpaque(false);
			case 2:
				zoneJBas.add(listJoueurs.get(0), BorderLayout.NORTH);
				zoneJGauche.add(listJoueurs.get(1), BorderLayout.EAST);
				listJoueurs.get(1).pivoterVertical();
				listJoueurs.get(0).setOpaque(false);
				listJoueurs.get(1).setOpaque(false);
				zoneJGauche.setOpaque(false);
				zoneJBas.setOpaque(false);
			break;
		}
		
		zoneVideVertical.setOpaque(false);
		zoneVideVertical2.setOpaque(false);
		zoneVideCentral.setOpaque(false);
		zonePioche.setOpaque(false);
		zoneCentral.setOpaque(false);
		zoneVideVertical.setPreferredSize(new Dimension(50,0));
		zoneVideVertical2.setPreferredSize(new Dimension(50,0));
		
		zoneCentral.add(zoneJHaut, BorderLayout.NORTH);
		zoneCentral.add(zoneJBas, BorderLayout.SOUTH);
		zoneCentral.add(zonePioche, BorderLayout.CENTER);
		zoneVideCentral.add(zoneVideVertical, BorderLayout.EAST);
		zoneVideCentral.add(zoneCentral, BorderLayout.CENTER);
		zoneVideCentral.add(zoneVideVertical2, BorderLayout.WEST);
		zoneCarteSommet.setOpaque(false);
		curv.add(zoneJGauche, BorderLayout.WEST);
		curv.add(zoneJDroite, BorderLayout.EAST);
		curv.add(zoneVideCentral, BorderLayout.CENTER);
		add(curv);
	}    
}