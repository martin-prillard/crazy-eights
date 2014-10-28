package fr.utt.isi.lo02.huitamericain.plateau.carte.drag;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurControlleur;

/**
 * Ensembles de classes permettant le drag and drop de la carté sélectionné à la souris
 * Sources de bases : http://gfx.developpez.com/tutoriel/java/swing/drag/
 */
public class GhostDropAdapter extends MouseAdapter {
    protected GhostGlassPane glassPane;
	protected CarteVue carte;
	protected JoueurControlleur jc;
	protected AbstrJoueurVue joueur;
	
	private List<GhostDropListener> listeners;

    public GhostDropAdapter(GhostGlassPane glassPane, CarteVue _carte, JoueurControlleur _jc) {
        this.glassPane = glassPane;
        this.carte = _carte;
        this.jc = _jc;
        this.listeners = new ArrayList<GhostDropListener>();
    }

    public void addGhostDropListener(GhostDropListener listener) {
        if (listener != null)
            listeners.add(listener);
    }

    public void removeGhostDropListener(GhostDropListener listener) {
        if (listener != null)
            listeners.remove(listener);
    }

    protected void fireGhostDropEvent(GhostDropEvent evt) {
        Iterator<GhostDropListener> it = listeners.iterator();
        while (it.hasNext()) {
        	((GhostDropListener) it.next()).ghostDropped(evt);
        }
    }
}