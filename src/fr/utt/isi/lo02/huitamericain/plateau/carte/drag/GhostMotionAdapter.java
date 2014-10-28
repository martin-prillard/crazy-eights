package fr.utt.isi.lo02.huitamericain.plateau.carte.drag;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.SwingUtilities;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BorderArrondis;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteVue;

/**
 * Ensembles de classes permettant le drag and drop de la carté sélectionné à la souris
 * Sources de bases : http://gfx.developpez.com/tutoriel/java/swing/drag/
 */
public class GhostMotionAdapter extends MouseMotionAdapter implements MouseListener
{
    private GhostGlassPane glassPane;
    private CarteVue cv;
    private BorderArrondis borderSelection  = new BorderArrondis(Color.BLACK, 10, 10, 2);
    	
	public GhostMotionAdapter(GhostGlassPane glassPane, CarteVue _cv) {
		cv = _cv;
		this.glassPane = glassPane;
	}

	public void mouseDragged(MouseEvent e)
    {
      Component c = e.getComponent();
      Point p = (Point) e.getPoint().clone();
      SwingUtilities.convertPointToScreen(p, c);
      SwingUtilities.convertPointFromScreen(p, glassPane);
      glassPane.setPoint(p);
      glassPane.repaint();
    }

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		cv.setBorder(borderSelection);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		cv.setBorder(null);
		try {
			if (GestionParametresAppli.getInstance().retourneEtatAnimationCarteJouer()
					&& (GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.NORMAL
					|| GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.AUCUNE))
				cv.editeClignote();
		} catch (IOException e1) {e1.printStackTrace();}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		glassPane.lancerTimer();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		glassPane.arreterTimer();
	}
}
