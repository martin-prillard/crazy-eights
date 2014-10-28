package fr.utt.isi.lo02.huitamericain.plateau.carte.drag;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;
import org.jdesktop.swingx.JXPanel;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;

/**
 * Ensembles de classes permettant le drag and drop de la carté sélectionné à la souris
 * Sources de bases : http://gfx.developpez.com/tutoriel/java/swing/drag/
 */
@SuppressWarnings("serial")
public class GhostGlassPane extends JXPanel implements Observer 
{
	private AlphaComposite composite;
    private BufferedImage dragged = null;
    private Point location = new Point(0, 0);
    private int tailleLargeur = 0;
    private int tailleHauteur = 0;
    private Timer timer;
    private int tailleLargeurBuffer;
    private int tailleHauteurBuffer;
    private boolean augmente = true;
    private int angle = 0;
    private boolean utiliseTimer = false;
    private double zoom = 1.2;
    private boolean tourneSens = false;
    private boolean crazy = false;
    private float alpha;

    public GhostGlassPane(){
    	editeEtatAnimation();
    	try {
			GestionParametresAppli.getInstance().addObserver(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void setImage(BufferedImage dragged)
    {
        this.dragged = dragged;
        if (dragged != null)
        {
            tailleLargeurBuffer = dragged.getWidth();
            tailleHauteurBuffer = dragged.getHeight();
            tailleLargeur = tailleLargeurBuffer;
            tailleHauteur = tailleHauteurBuffer;
        }
    }

    public void setPoint(Point location)
    {
        this.location = location;
    }
    
    public void editeEtatAnimation()
    {
    	try {
	        if (GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.CRAZY)
	        {
	        	zoom = 1.7;
	        	alpha = 0.7f;
	        	composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	        	crazy = true;
	        	utiliseTimer = true;
	        }else if (GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.NORMAL){
	        	zoom = 1.2;
	        	alpha = 0.7f;
	            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	            crazy = false;
	            utiliseTimer = true;
	        }else if (GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.AUCUNE)
	        {
	        	alpha = 1.0f;
	            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	            crazy = false;
	            utiliseTimer = false;
	            tailleLargeur = tailleLargeurBuffer;
	            tailleLargeur = tailleHauteurBuffer;
	        }
	        setOpaque(false);
    	} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    public void paintComponent(Graphics g)
    {
        if (dragged == null)
            return;
        
        Graphics2D g2 = (Graphics2D) g;
        
		if (tourneSens && crazy)
		{
			angle ++;
			if (angle == 360)
				angle = 0;
			dragged = rotate(dragged);
		}
		g2.setComposite(composite);
        
        g2.drawImage(dragged,
                     (int) (location.getX() - (tailleLargeur  / 2)),
                     (int) (location.getY() - (tailleHauteur / 2)),
                     tailleLargeur,
                     tailleHauteur,
                     null);
    }
    
    public BufferedImage rotate(BufferedImage img) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(w, h, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.rotate(Math.toRadians(angle), w/2, h/2);  
        g.drawImage(img, null, 0, 0);  
        return dimg;  
    }  
    
    /* timer*/
    public javax.swing.Timer creationTimer(int temps) {
        // Création d'une instance de listener
        ActionListener action = new ActionListener() {

            // Méthode appelée à chaque tic du timer
            public void actionPerformed(ActionEvent event) {
            	if ((tailleLargeur <= tailleLargeurBuffer) && (tailleHauteur <= tailleHauteurBuffer))
            		augmente = true;
                else if ((tailleLargeur >= tailleLargeurBuffer * zoom) && (tailleHauteur >= tailleHauteurBuffer * zoom))
                	augmente = false;
            	
            	if (augmente)
            	{
	            	tailleLargeur = tailleLargeur + 2;
	                tailleHauteur = tailleHauteur + 2;
            	}else{
                	tailleLargeur = tailleLargeur - 2;
                    tailleHauteur = tailleHauteur - 2;
            	}
            	repaint();
            }
        };
        return new javax.swing.Timer(temps, action);
	}
    
    public void lancerTimer()
    {
    	if (!tourneSens && utiliseTimer)
    	{
    		timer = creationTimer(50);
    		timer.start();
    		tourneSens = true;
    	}
    }
    
    public void arreterTimer()
    {
    	if(tourneSens)
    	{
    		timer.stop();
    		tourneSens = false;
    	}
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		editeEtatAnimation();
	}
}
