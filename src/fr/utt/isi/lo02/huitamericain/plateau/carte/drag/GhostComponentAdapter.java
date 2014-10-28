package fr.utt.isi.lo02.huitamericain.plateau.carte.drag;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurControlleur;

/**
 * Ensembles de classes permettant le drag and drop de la carté sélectionné à la souris
 * Sources de bases : http://gfx.developpez.com/tutoriel/java/swing/drag/
 */
public class GhostComponentAdapter extends GhostDropAdapter
{
    public GhostComponentAdapter(GhostGlassPane glassPane, CarteVue _carte, JoueurControlleur _jc) {
        super(glassPane, _carte, _jc);
    }

    public void mousePressed(MouseEvent e)
    {
        Component c = e.getComponent();

        BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        c.paint(g);

        glassPane.setVisible(true);

        Point p = (Point) e.getPoint().clone();
        SwingUtilities.convertPointToScreen(p, c);
        SwingUtilities.convertPointFromScreen(p, glassPane);

        glassPane.setPoint(p);
        glassPane.setImage(image);
        glassPane.repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
        Component c = e.getComponent();

        Point p = (Point) e.getPoint().clone();
        SwingUtilities.convertPointToScreen(p, c);

        Point eventPoint = (Point) p.clone();
        SwingUtilities.convertPointFromScreen(p, glassPane);

        glassPane.setPoint(p);
        glassPane.setVisible(false);
        glassPane.setImage(null);

        fireGhostDropEvent(new GhostDropEvent(carte, jc, eventPoint));
    }
}
