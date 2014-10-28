package fr.utt.isi.lo02.huitamericain.plateau;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.plateau.carte.drag.GhostDropManagerDemo;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurControlleur;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurVueComplexe;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche.PiocheVue;

/**
* Vue C du plateau
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class PlateauVueC extends AbstrPlateauVue implements Observer{

	/**
	 * Constructeur de la vue C
	 * @param _platMod
	 * @param _jv
	 */
	public PlateauVueC(PlateauModel _platMod, JeuVue _jv)
	{
		super(_platMod, _jv);	
		nbJoueur = platMod.retourneNbJoueur();
		initLayoutPlateau();
		initPlacementPioche();
		initJoueurSurPlateau();
		initPlacementJoueur();
	}
	
	/**
	 * Initialisation des joueurs
	 */
	public void initJoueurSurPlateau()
	{
		JoueurVueComplexe v;
		listJoueurs = new ArrayList<AbstrJoueurVue>();
		
		for(AbstrJoueurModel iJoueur : platMod.retourneListJoueur())
		{
			if(iJoueur instanceof JoueurHumain)
			{
				v = new JoueurVueComplexe(new JoueurControlleur(iJoueur, platMod, jv), true, listener);
			}else{
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
		zoneJGauche.setLayout(new GridLayout(nbJoueur, 0));
		zonePioche.setLayout(new GridBagLayout());
		zoneJDroite.setLayout(new BorderLayout());
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
		piocheV.pivoterVertical();
		zonePioche.add(piocheV, gbc);
		listener = new GhostDropManagerDemo(piocheV.retourneZoneCarteVue());
	}
	
	/**
	 * Initialisation des joueurs
	 */
	public void initPlacementJoueur()
	{
		
		
		for(int i = 0; i < nbJoueur; i++)
			zoneJGauche.add(listJoueurs.get(i));
		
		JScrollPane scrollZoneJoueur = new JScrollPane(zoneJGauche);
		scrollZoneJoueur.setOpaque(false);
		scrollZoneJoueur.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		scrollZoneJoueur.getViewport().setOpaque(false);
		
		zoneVideVertical.setOpaque(false);
		zoneVideVertical2.setOpaque(false);
		zoneJGauche.setOpaque(false);
		zonePioche.setOpaque(false);
		zoneJDroite.setOpaque(false);
		
		zoneVideVertical.setPreferredSize(new Dimension(150,0));
		zoneVideVertical2.setPreferredSize(new Dimension(150,0));
		
		zoneJDroite.add(zoneVideVertical, BorderLayout.WEST);
		zoneJDroite.add(zonePioche, BorderLayout.CENTER);
		zoneJDroite.add(zoneVideVertical2, BorderLayout.EAST);

		
		curv.add(zoneJDroite, BorderLayout.WEST);
		curv.add(scrollZoneJoueur, BorderLayout.CENTER);
		add(curv);
	}
}
