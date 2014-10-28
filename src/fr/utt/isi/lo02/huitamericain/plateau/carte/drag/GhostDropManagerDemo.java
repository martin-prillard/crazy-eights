package fr.utt.isi.lo02.huitamericain.plateau.carte.drag;

import java.awt.Point;
import java.io.IOException;

import javax.swing.JComponent;

import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurControlleur;

/**
 * Ensembles de classes permettant le drag and drop de la carté sélectionné à la souris
 * Sources de bases : http://gfx.developpez.com/tutoriel/java/swing/drag/
 */
public class GhostDropManagerDemo extends AbstractGhostDropManager {

    public GhostDropManagerDemo(JComponent target) {
        super(target);
    }

	public void ghostDropped(GhostDropEvent e) {
	   CarteVue carte = e.retourneCarteVue();
	   JoueurControlleur jc = e.retourneJoueurControlleur();
	   Point p = getTranslatedPoint(e.getDropLocation());
	   if (isInTarget(p)) {
		   try {
			jc.clickSurCarte(carte.retourneCarteModel());
		   } catch (IOException e1) {e1.printStackTrace();}
	   }
	}
}