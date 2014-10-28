package fr.utt.isi.lo02.huitamericain.ihm.composant;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;

/**
* Classe hérité de JButton, utilisé pour des boutons avec images
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class BoutonImg extends JButton {

	private JLabel info = new JLabel();
	private JLabel info2 = new JLabel();
	private JPanel zoneInfo2 = new JPanel(new BorderLayout());
    private Image iBouton;

    public BoutonImg(Image img) throws IOException {
        super();
        iBouton = img;
    }
    
    /**
     * Constructeur de BoutonImg avec la possibilité d'écrire un texte
     */
    public BoutonImg(Image img, String txt) throws IOException {
        super();
        setLayout(new BorderLayout());
        iBouton = img;
        info.setText("  "+txt);
        info.setForeground(GestionParametresAppli.getInstance().retourneCouleurTexteCarte());
        info.setFont(info.getFont().deriveFont((float)20.0));
        info2.setForeground(GestionParametresAppli.getInstance().retourneCouleurTexteCarte());
        info2.setFont(info2.getFont().deriveFont((float)25.0));
        zoneInfo2.add(info2, BorderLayout.EAST);
        add(info, BorderLayout.NORTH);
        add(zoneInfo2, BorderLayout.SOUTH);
        info.setOpaque(false);
        info2.setOpaque(false);
        zoneInfo2.setOpaque(false);
		this.setBorder(null);
    }

    /**
     * Affichage de l'objet
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //On récupère la taille du composant
        int i = getWidth();
        int j = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints hints = createRenderingHints();
        g2.setRenderingHints(hints);
        //Dessine l'image sur le bouton
        g.drawImage(iBouton, 0, 0, i, j, this); 
    }

    /**
     * Permet de changer l'image du bouton avec une image interne au programme
     * @throws IOException
     */
    public void EditImage(Image _img) throws IOException {
        iBouton = _img;
        this.repaint();
    }
    
    /**
     * Permet de changer l'image du bouton avec une image exterieur choisie par l'utilisateur
     * @param url
     * @throws IOException
     */
    public void EditImageExterieur(String url) throws IOException {
    	iBouton = getToolkit().getImage(url);
        this.repaint();
    }
    
    /**
     * Permet d'editer du texte pour l'afficher en haut de l'image
     * @param txt
     */
    public void editeTexteInfoHaut(String txt)
    {
    	info.setText("  "+txt);
    }
    
    /**
     * Permet d'editer du texte pour l'afficher en bas de l'image
     * @param txt
     */
    public void editeTexteInfoBas(String txt)
    {
    	info2.setText(txt);
    }
    

    /**
     * Antialiasing
     * @return
     */
    protected RenderingHints createRenderingHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        return hints;
    }

}
