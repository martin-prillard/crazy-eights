package fr.utt.isi.lo02.huitamericain.ihm.composant;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.border.Border;
 
/**
* Classe personnalisée implémentant l'interface Border
* 
* @author Martin Prillard
*/
public class BorderArrondis implements Border {  
 
	private Color couleur;
	private int largeurArc;
	private int hauteurArc;
	private int adjustXY = 0; //pour ajuster le dessin en x et y
	private int adjustWH = 1; //idem pour width et height
	private float epaisseur = 1.0f;
 
	/**
	 * Constructeur
	 * @param _couleur
	 * @param _largeurArc
	 * @param _hauteurArc
	 * @param _epaisseur
	 */
	public BorderArrondis(Color _couleur, int _largeurArc, int _hauteurArc, float _epaisseur)  {
		this.couleur = _couleur;
		this.largeurArc = _largeurArc;
		this.hauteurArc = _hauteurArc;
		this.epaisseur = _epaisseur;
	}  
	
	/**
	 * Constructeur
	 * @param _couleur
	 * @param _largeurArc
	 * @param _hauteurArc
	 * @param _adjustXY
	 * @param _adjustWH
	 * @param _epaisseur
	 */
	public BorderArrondis(Color _couleur, int _largeurArc, int _hauteurArc, int _adjustXY, int _adjustWH, float _epaisseur)  {
		this.couleur = _couleur;
		this.largeurArc = _largeurArc;
		this.hauteurArc = _hauteurArc;
		this.adjustXY = _adjustXY;
		this.adjustWH = _adjustWH;
		this.epaisseur = _epaisseur;
	}  
 
 
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)  {
        g.setColor(this.couleur);
        Graphics2D g2 = (Graphics2D)g;
        Stroke stroke = new BasicStroke(epaisseur);
        g2.setStroke(stroke);
        //Antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(x+adjustXY, y+adjustXY, width-adjustWH, height-adjustWH, this.largeurArc, this.hauteurArc);
	}  
 
 
	@Override
	public Insets getBorderInsets(Component c)  {  
		return new Insets(0,0,0,0); 
	}  
 
	@Override
	public boolean isBorderOpaque()  {
		return true; 
	} 
 
}
