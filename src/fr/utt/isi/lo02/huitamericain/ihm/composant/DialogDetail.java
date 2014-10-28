package fr.utt.isi.lo02.huitamericain.ihm.composant;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.ShadowRenderer;

/**
 * (Classe modifiée par la suite pour répondre aux besoins)
 * En complement des classes héritant de AbstractDialogVue,
 * cette classe crée un panel noir avec ombre en guise de fond pour les menus
 * 
 * @author Romain Guy
 * @see AbstrDialogVue
 */
@SuppressWarnings("serial")
public class DialogDetail extends JXPanel{

    private BufferedImage shadow;
    private int largeur = 0;
    private int hauteur = 0;
    
    /**
     * Constructeur
     * @param _largeur
     * @param _hauteur
     */
    public DialogDetail(int _largeur, int _hauteur) {
    	largeur = _largeur;
    	hauteur = _hauteur;
    	this.setPreferredSize(new Dimension(largeur, hauteur));
    }

	@Override
	protected void paintComponent(Graphics g) {
	    int x = 34;
	    int y = 34;
	    int w = getWidth() - 68;
	    int h = getHeight() - 68;
	    int arc = 30;

	    Graphics2D g2 = (Graphics2D) g.create();
	    //Antialiasing
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

	    //Dessine l'ombre
	    if (shadow != null) {
	        int xOffset = (shadow.getWidth()  - w) / 2;
	        int yOffset = (shadow.getHeight() - h) / 2;
	        g2.drawImage(shadow, x - xOffset, y - yOffset, null);
	    }
	    
	    g2.setColor(new Color(0, 0, 0, 185));
	    g2.fillRoundRect(x, y, w, h, arc, arc);

	    g2.setStroke(new BasicStroke(3f));
	    g2.setColor(Color.WHITE);
	    g2.drawRoundRect(x, y, w, h, arc, arc); 

	    g2.dispose();
	}
	
	/**
	 * L'affichage ne peut se faire qu'en précisant une taille statique
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
	    super.setBounds(x, y, largeur, hauteur);
	    
	    int w = getWidth() - 68;
	    int h = getHeight() - 68;
	    int arc = 30;
	    int shadowSize = 20;
	    
	    shadow = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
	    Graphics2D g2 = shadow.createGraphics();
	    g2.setColor(Color.WHITE);
	    g2.fillRoundRect(0, 0, w, h, arc, arc);
	    g2.dispose();

	    //Gestion des ombres avec SwingX
	    ShadowRenderer renderer = new ShadowRenderer(shadowSize, 0.5f, Color.BLACK);
	    shadow = renderer.createShadow(shadow);
	    
	    g2 = shadow.createGraphics();
		g2.setColor(Color.RED);
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRoundRect(shadowSize, shadowSize, w, h, arc, arc);
	    g2.dispose();
	    
	}
}