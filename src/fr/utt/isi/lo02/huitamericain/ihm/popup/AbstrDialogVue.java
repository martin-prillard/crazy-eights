package fr.utt.isi.lo02.huitamericain.ihm.popup;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.image.GaussianBlurFilter;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.DialogDetail;
import fr.utt.isi.lo02.huitamericain.ihm.composant.GraphicsUtilities;

/**
* Classe mère dont les menus hérites, permet un affichage progressif graçe au fadin et fadout
* 
* @author Martin Prillard
* @see DialogDetail
*/
@SuppressWarnings("serial")
public class AbstrDialogVue extends JPanel{

    private BufferedImage blurBuffer;
    private BufferedImage backBuffer;
    private float alpha = 0.0f;
    protected JeuVue instanceJeu;
    protected DialogDetail dialDetail;
    private Timer timer = new Timer(150, null);
    
    public AbstrDialogVue(JeuVue _vJeu, int dialDetailX, int dialDetailY, boolean fermeClickBouton) throws IOException
    {
    	_vJeu.setGlassPane(this);
    	setVisible(true);
        // Bloque toute actions de l'utilisateur
    	addMouseListener(new MouseAdapter() {});
    	addMouseMotionListener(new MouseMotionAdapter() { });
    	addKeyListener(new KeyAdapter() { });
    	setFocusTraversalKeysEnabled(false);
    	addComponentListener(new ComponentAdapter() {
    		public void componentShown(ComponentEvent evt) {
    			requestFocusInWindow();
    		}
    	});
    	if (!fermeClickBouton)
    	{
        	this.addMouseListener(new MouseListener() {
    			@Override
    			public void mouseReleased(MouseEvent e) {}
    			@Override
    			public void mousePressed(MouseEvent e) {}
    			@Override
    			public void mouseExited(MouseEvent e) {}
    			@Override
    			public void mouseEntered(MouseEvent e) {}
    			@Override
    			public void mouseClicked(MouseEvent e) {
    				fadeOut();
    			}
    		});
    	}
    	setLayout(new GridBagLayout());
    	instanceJeu = _vJeu;
    	dialDetail = new DialogDetail(dialDetailX, dialDetailY);
    	dialDetail.setAlpha(0.0f);
    	dialDetail.setLayout(new GridBagLayout());
        add(dialDetail);
        
    	fadeIn();
    }

    /**
     * Crée un effet de flou avec un fou gaussien
     * @throws IOException
     */
    private void createBlur() throws IOException {
        JRootPane root = SwingUtilities.getRootPane(this);
        blurBuffer = GraphicsUtilities.createCompatibleImage(instanceJeu.getWidth()-15, instanceJeu.getHeight()-38);
        Graphics2D g2 = blurBuffer.createGraphics();
        root.paint(g2);
        g2.dispose();
        backBuffer = blurBuffer;
        blurBuffer = GraphicsUtilities.createThumbnailFast(blurBuffer, instanceJeu.getWidth() / 2);
        blurBuffer = new GaussianBlurFilter(GestionParametresAppli.getInstance().retourneFlou()).filter(blurBuffer, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (isVisible() && blurBuffer != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            RenderingHints hints = createRenderingHints();
	        g2.setRenderingHints(hints);
            g2.drawImage(backBuffer, 0, 0, null);

            g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
            g2.drawImage(blurBuffer, 0, 0, getWidth(), getHeight(), null);
            g2.dispose();
        }
    }
    
    /**
     * Retourne le niveau de transparence
     * @return alpha
     */
    public float getAlpha() {return alpha;}

    /**
     * Edite le niveau de transparence
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    /**
     * Affichage progressif avec un thread dédié (bibliothèque externe, Animator)
     * @throws IOException
     */
    public void fadeIn() throws IOException {
		createBlur();
		if (GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.AUCUNE)
		{
			dialDetail.setAlpha(1.0f);
			AbstrDialogVue.this.setAlpha(1.0f);
		}else{
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                Animator animator = PropertySetter.createAnimator(400, dialDetail, "alpha", 1.0f);
	                animator.setAcceleration(0.2f);
	                animator.setDeceleration(0.3f);
	                animator.addTarget(new PropertySetter(AbstrDialogVue.this, "alpha", 1.0f));
	                animator.start();
	            }
	        });
    	}
    }
    
    /**
     * Destruction de l'objet sans animation
     */
    public void fadeOutSansTimer() {
			AbstrDialogVue.this.setVisible(false);
			instanceJeu.setGlassPane(JeuVue.retourneGlassPaneGhost());
			AbstrDialogVue.this.removeAll();
			instanceJeu.remove(this);
    }
    
    /**
     * Destruction de l'objet avec animation
     */
    public void fadeOut() {
		
    	try {
			if (GestionParametresAppli.getInstance().retourneEtatAnimation() == GestionParametresAppli.Animations.AUCUNE)
			{
				AbstrDialogVue.this.setVisible(false);
				instanceJeu.setGlassPane(JeuVue.retourneGlassPaneGhost());
				AbstrDialogVue.this.removeAll();
				instanceJeu.remove(this);
			}else{
			    SwingUtilities.invokeLater(new Runnable() {
			        public void run() {
			            Animator animator = PropertySetter.createAnimator(400, dialDetail, "alpha", 0.0f);
			            animator.setAcceleration(0.2f);
			            animator.setDeceleration(0.3f);
			            animator.addTarget(new PropertySetter(AbstrDialogVue.this, "alpha", 0.0f));
			            animator.start();
			        }
			    });
			    
			    
			    //Utilisation d'un timer pour détruire l'objet quant celui ci est totalement invisible pour l'utilisateur
			    timer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(AbstrDialogVue.this.getAlpha() == 0.0f)
						{
							AbstrDialogVue.this.setVisible(false);
							instanceJeu.setGlassPane(JeuVue.retourneGlassPaneGhost());
							AbstrDialogVue.this.removeAll();
							instanceJeu.remove(AbstrDialogVue.this);
							timer.stop();
						}
					}
				});
			    timer.start();
			}
		} catch (IOException e) {e.printStackTrace();}
    }

    /**
     * Antialiasing
     * @return RenderingHints
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
    
    /**
     * Retourne le timer
     * @return timer
     */
    public Timer retourneTimer(){return timer;}
}
