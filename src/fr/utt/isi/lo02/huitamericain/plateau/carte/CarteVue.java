package fr.utt.isi.lo02.huitamericain.plateau.carte;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BorderArrondis;

/**
* Vue de la carte
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class CarteVue extends JButton implements Observer,ActionListener{

	private int LARGEUR;
	private int HAUTEUR;
	private Image iCarte;
	private BufferedImage buffICarte;
	private CarteModel carteMod;
	private BorderArrondis borderSelection  = new BorderArrondis(Color.DARK_GRAY, 9, 9, 1, 3, 4);
	private float alpha = 1.0f;
	private Timer timer;
	private int animationDuration = 2000;
	private long animationStartTime;
	   
    /**
     * Constructeur de la vue de la carte, prend en paramètre une CarteModel
     * @param _carteMod
     */
	public CarteVue(CarteModel _carteMod)
	{
		_carteMod.addObserver(this);
		carteMod = _carteMod;
		try {
			iCarte = GestionImagesAppli.getInstance().retourneImgCarte(carteMod.retourneTaille(), carteMod.retourneCouleur());
			GestionParametresAppli.getInstance().addObserver(this);
			buffICarte = toBufferedImage(iCarte);
            animationStartTime = System.nanoTime() / 1000000;
            timer = new Timer(30, this);
            
            LARGEUR = GestionParametresAppli.getInstance().retourneEtatTailleCarteX();
            HAUTEUR = GestionParametresAppli.getInstance().retourneEtatTailleCarteY();
            this.setMinimumSize(new Dimension(LARGEUR,HAUTEUR));
    		this.setPreferredSize(new Dimension(LARGEUR,HAUTEUR));
    		this.setMaximumSize(new Dimension(LARGEUR,HAUTEUR));
    		editeClignote();
    		setOpaque(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * Masque ou non la carte
	 * @param _cacher
	 */
    public void masquerCarte(boolean _cacher)
    {
    	if (_cacher)
    	{
			try {
				buffICarte = toBufferedImage(GestionImagesAppli.getInstance().retourneImgCarteCache());
			} catch (IOException e) {e.printStackTrace();}
    	}else{
        	buffICarte = toBufferedImage(iCarte);
    	}
    }
	
    /**
     * Pivote la carte horyzontalement
     */
    public void pivoterCarteHoryzontal()
    {
        this.setMinimumSize(new Dimension(HAUTEUR,LARGEUR));
		this.setPreferredSize(new Dimension(HAUTEUR,LARGEUR));
		this.setMaximumSize(new Dimension(HAUTEUR,LARGEUR));
		buffICarte = rotate90DX(buffICarte);	
    }
    
    
    /**
     * Rotatin à 90degrés d'une image
     * @param bi
     * @return bufferedImage modifié après rotation
     */
	 public BufferedImage rotate90DX(BufferedImage bi){
		  int width = bi.getWidth();
		  int height = bi.getHeight();
		  //Exception image type = 0
		  BufferedImage biFlip = new BufferedImage(height, width, bi.getType());
		  for(int i=0; i<width; i++)
		      for(int j=0; j<height; j++)
		          biFlip.setRGB(height-1-j, width-1-i, bi.getRGB(i, j));
		  
		  return biFlip;
	  }
    
	 /**
	  * Transforme une image en un BufferedImage
	  * @param image
	  * @return BufferedImage de l'image
	  */
	BufferedImage toBufferedImage(Image image) {
        /** On test si l'image n'est pas déja une instance de BufferedImage */
        if( image instanceof BufferedImage ) {
                return( (BufferedImage)image );
        } else {
                /** On s'assure que l'image est complètement chargée */
                image = new ImageIcon(image).getImage();
                
                /** On crée la nouvelle image */
                BufferedImage bufferedImage = new BufferedImage(
                            image.getWidth(null),
                            image.getHeight(null),
                            BufferedImage.TYPE_INT_RGB );
                
                Graphics g = bufferedImage.createGraphics();
                g.drawImage(image,0,0,null);
                g.dispose();
                
                return( bufferedImage );
        } 
	}
   
	/**
	 * Affichage
	 */
    @Override
    public void paintComponent(Graphics g) {
    	
        Graphics2D g2d = (Graphics2D) g;

        AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(newComposite);
        
        int width = getWidth();
        int height = getHeight();
        g2d.drawImage(buffICarte, 0, 0,width, height, this);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

    }
    
    
    /**
     * Effet fading de la carte (transparence)
     */
    public void actionPerformed(ActionEvent ae) {
        long currentTime = System.nanoTime() / 1000000;
        long totalTime = currentTime - animationStartTime;
        if (totalTime > animationDuration) {
            animationStartTime = currentTime;
        }
        float fraction = (float) totalTime / animationDuration;
        fraction = Math.min(1.0f, fraction);
        alpha = Math.abs(1 - (2 * fraction));
        repaint();
    }
    
    
    /**
     * Actionne ou non le fait qu'une carte clignote
     */
    public void editeClignote()
    {
    	boolean b = carteMod.retourneClignote();
    	try {
    		desactiveTouteLesAnimations(); //On desactive au préalable l'animation précédente
			if (GestionParametresAppli.getInstance().retourneEtatAnimationCarteJouer() && b)
			{
					if (GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.CRAZY)
						timer.start();
					if (GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.NORMAL
							|| GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.AUCUNE)
						CarteVue.this.setBorder(borderSelection);
			}
		} catch (IOException e) {e.printStackTrace();}
    }
    
    /**
     * Desctive toutes les animations de la carte
     */
    public void desactiveTouteLesAnimations()
    {
		if (timer.isRunning()) //Desactive l'animation crazy
		{
			timer.stop();
			alpha = 1.0f;
		}
        CarteVue.this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); //Desactive l'animation normal et aucune
    }
    
    /**
     * Retourne le model de la carte
     * @return carteMod
     */
    public CarteModel retourneCarteModel(){return carteMod;}

    /**
     * Mis a jour de la vue de la carte
     */
	@Override
	public void update(Observable arg0, Object arg1) {
		editeClignote();
		if (arg0 instanceof GestionParametresAppli)
		{
			try {
				carteMod.editeValeur(GestionParametresAppli.getInstance().retourneValCarte(carteMod.retourneTaille()));
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}