package fr.utt.isi.lo02.huitamericain.ihm.composant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;

/**
* Classe comprenant l'avatar et l'afichage du score des joeueurs
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class Avatar extends JPanel implements Observer, ActionListener{

	private BoutonImg avat;
	private AbstrJoueurModel instanceJoueur;
	private JPanel zoneBas = new JPanel(new BorderLayout());
	private JLabel score;
	private JButton derCarte = new JButton("Carte !");
	private BorderArrondis border  = new BorderArrondis(Color.black, 19, 19, 1);
	private BorderArrondis borderSelection  = new BorderArrondis(Color.black, 19, 19, 2);
	
	
	/**
	 * Constructeur de la classe Avatar
	 * @param _instanceJoueur
	 */
	public Avatar(AbstrJoueurModel _instanceJoueur)
	{
		instanceJoueur = _instanceJoueur;
		try {
			GestionParametresAppli.getInstance().addObserver(this);
			score = new JLabel("Score : " + instanceJoueur.retourneScoreTotal());
			score.setForeground(Color.white);
			score.setVisible(GestionParametresAppli.getInstance().retourneInfoJoueur());
			
			avat = new BoutonImg(GestionImagesAppli.getInstance().retourneImgJoueur(instanceJoueur.retourneIdProfil()));
			avat.addActionListener(this);
			avat.setBackground(new Color(0,0,0,0));
			avat.setOpaque(false);
			avat.setBorder(border);
			
			avat.setPreferredSize(new Dimension(GestionParametresAppli.getInstance().retourneEtatTailleAvatImage(),GestionParametresAppli.getInstance().retourneEtatTailleAvatImage()));
			setPreferredSize(new Dimension(GestionParametresAppli.getInstance().retourneEtatTailleAvat(),GestionParametresAppli.getInstance().retourneEtatTailleAvat()));
			avat.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent arg0) {}
				@Override
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {avat.setBorder(border);}
				@Override
				public void mouseEntered(MouseEvent arg0) {avat.setBorder(borderSelection);}
				@Override
				public void mouseClicked(MouseEvent arg0) {}
			});
			
			//S'il s'agit d'un joueur humain, on actualise son etat "Carte !"
			
			if (instanceJoueur instanceof JoueurHumain)
			{
				if (!((JoueurHumain)instanceJoueur).retourneDerCarte())
					derCarte.setVisible(false);
				
				derCarte.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						((JoueurHumain)instanceJoueur).editeDerCarte(false);
					}
				});
				zoneBas.add(derCarte, BorderLayout.EAST);
				derCarte.setPreferredSize(new Dimension(65, 18));
			}
			zoneBas.setOpaque(false);
			zoneBas.add(score, BorderLayout.WEST);
			add(avat);
			add(zoneBas);
			setOpaque(false);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public JLabel retourneLabelScore(){return score;}
	public BoutonImg retourneAvatarImage(){return avat;}

	
	/**
	 * Change l'image de l'avatar du joueur.
	 * @param url
	 * @throws IOException
	 */
    public void EditImage(String url) throws IOException {
    	avat.EditImageExterieur(url);
    	GestionImagesAppli.getInstance().editeImgJoueur(instanceJoueur.retourneIdProfil(), url);
    }
    
    /**
     * Affiche ou non le bouton "Carte !"
     * @param b
     */
    public void editeVisibleDerCarte(boolean b)
    {
    	derCarte.setVisible(b);
    }
    
    /**
     * Action lors du click sur l'image de l'avatar.
     * Permet à l'utilisateur de personnaliser l'image de son avatar.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
    	String[] formatsLecture = ImageIO.getReaderFormatNames();  
    	FileNameExtensionFilter filtreImage = new FileNameExtensionFilter("Images", formatsLecture);
    	String sourceImgFond;
    	JFileChooser fcImage = new JFileChooser();
    	fcImage.addChoosableFileFilter(filtreImage);
    	AccesFileChooser preview = new AccesFileChooser();
    	fcImage.setAccessory(preview);
    	fcImage.addPropertyChangeListener(preview);

    	if (JFileChooser.APPROVE_OPTION == fcImage.showSaveDialog(null)) {
    		try {
				sourceImgFond = fcImage.getSelectedFile().getAbsolutePath();
				EditImage(sourceImgFond);
			} catch (IOException e1) {
	            JOptionPane.showMessageDialog(null,
	                    "Image invalide",
	                    "Choisissez une image valide",
	                    JOptionPane.WARNING_MESSAGE);
			}
    	}
	}

	/**
	 * Affiche ou non le score des joueurs sous l'avatar
	 * @see GestionParametresAppli
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof GestionParametresAppli)
		{
			try {
				score.setVisible(GestionParametresAppli.getInstance().retourneInfoJoueur());
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}