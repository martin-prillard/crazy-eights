package fr.utt.isi.lo02.huitamericain.ihm.composant;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JLabel;

/**
* Classe personnalisée héritant du composant JLabel
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class LabelImg extends JLabel{

	    private Image iBouton;

	    /**
	     * Constructeur avec l'image désirée
	     * @param img
	     * @throws IOException
	     */
	    public LabelImg(Image img) throws IOException {
	        iBouton = img;
	        setOpaque(false);
	    }

	    /**
	     * Affichage
	     */
	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (iBouton == null) {
	            return;
	        }
	        int i = getWidth();
	        int j = getHeight();

	        g.drawImage(iBouton, 0, 0, i, j, this); //Dessine l'image sur le bouton
	    }

	    /**
	     * Pour changer directement l'image avec une image interne au programme
	     * @param _img
	     * @throws IOException
	     */
	    public void EditImage(Image _img) throws IOException {
	        iBouton = _img;
	        this.repaint();
	    }
	    
	    /**
	     * Pour changer directement l'image avec une image externe choisit par l'utilisateur
	     * @param url
	     * @throws IOException
	     */
	    public void EditImageExterieur(String url) throws IOException {
	    	iBouton = getToolkit().getImage(url);
	        this.repaint();
	    }
	    
	    /**
	     * Retourne l'image du composant
	     * @return iBouton
	     */
	    public Image retourneImage(){return iBouton;}
	}