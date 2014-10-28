package fr.utt.isi.lo02.huitamericain.plateau.carte.drag;

import java.awt.Point;

import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurControlleur;

/**
 * Ensembles de classes permettant le drag and drop de la carté sélectionné à la souris
 * Sources de bases : http://gfx.developpez.com/tutoriel/java/swing/drag/
 */
public class GhostDropEvent {
	private Point point;
	private CarteVue carte;
	private JoueurControlleur jc;

	public GhostDropEvent(CarteVue _carte, JoueurControlleur _jc, Point _point) {
		this.carte = _carte;
		this.point = _point;
		this.jc = _jc;
	}

	public CarteVue retourneCarteVue() {
		return carte;
	}
	
	public JoueurControlleur retourneJoueurControlleur() {
		return jc;
	}

	public Point getDropLocation() {
		return point;
	}
}
