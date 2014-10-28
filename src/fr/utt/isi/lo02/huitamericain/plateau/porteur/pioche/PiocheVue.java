package fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BorderArrondis;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BoutonImg;
import fr.utt.isi.lo02.huitamericain.ihm.composant.LabelImg;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;

/**
* Vue de la pioche
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class PiocheVue extends JPanel implements Observer{

	private BoutonImg cartePioche;
	private LabelImg carteSommetVue;
	private LabelImg sens;
	private PiocheControlleur pc;
	private LabelImg couleurC;
	private JPanel zoneLabelImg = new JPanel(new GridLayout(2,1));
	private BorderArrondis borderSelection  = new BorderArrondis(Color.DARK_GRAY, 6, 6, 5, 6, 3);
	
	/**
	 * Constructeur de la vue de la pioche
	 * @param _pc
	 */
	public PiocheVue(PiocheControlleur _pc)
	{
		setLayout(new GridLayout(1,3,10,0));
		pc = _pc;
		pc.retournePiocheModel().addObserver(this);
		pc.retournePlateauModel().addObserver(this);
		initComponent();
		this.setOpaque(false);
	}
	
	/**
	 * Initialisation des composants
	 */
	public void initComponent()
	{
		try {
			sens = new LabelImg(GestionImagesAppli.getInstance().retourneImgSens(0));
			if(pc.retournePlateauModel().retourneSens())
				sens.EditImage(GestionImagesAppli.getInstance().retourneImgSens(0));
			else
				sens.EditImage(GestionImagesAppli.getInstance().retourneImgSens(1));
			couleurC = new LabelImg(GestionImagesAppli.getInstance().retourneImgCouleur(pc.retournePlateauModel().retourneCouleurChoisie()+4));
			cartePioche = new BoutonImg(GestionImagesAppli.getInstance().retourneImgPioche(), ""+(pc.retournePiocheModel().retourneNbCarte()-1));
			cartePioche.setMinimumSize(new Dimension(new Dimension(GestionParametresAppli.getInstance().retourneEtatTailleCarteX(), GestionParametresAppli.getInstance().retourneEtatTailleCarteY())));
			cartePioche.setPreferredSize(new Dimension(new Dimension(GestionParametresAppli.getInstance().retourneEtatTailleCarteX(), GestionParametresAppli.getInstance().retourneEtatTailleCarteY())));
			cartePioche.setMaximumSize(new Dimension(new Dimension(GestionParametresAppli.getInstance().retourneEtatTailleCarteX(), GestionParametresAppli.getInstance().retourneEtatTailleCarteY())));
			carteSommetVue = new LabelImg(GestionImagesAppli.getInstance().retourneImgCarte(pc.retournePiocheModel().retourneDerniereCarte().retourneTaille(), pc.retournePiocheModel().retourneDerniereCarte().retourneCouleur()));
			carteSommetVue.setPreferredSize(new Dimension(GestionParametresAppli.getInstance().retourneEtatTailleCarteX(), GestionParametresAppli.getInstance().retourneEtatTailleCarteY()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (pc.retournePlateauModel().retourneNbCarteAPiocher() > 1)
			cartePioche.editeTexteInfoBas(" + "+pc.retournePlateauModel().retourneNbCarteAPiocher());
		cartePioche.setVisible(pc.retournePiocheModel().estNonVide());

		
		cartePioche.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pc.clickSurPioche();
				} catch (IOException e1) {e1.printStackTrace();}
			}
		});
		cartePioche.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {cartePioche.setBorder(null);}
			@Override
			public void mouseEntered(MouseEvent arg0) {cartePioche.setBorder(borderSelection);}
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		cartePioche.setContentAreaFilled(false);
		zoneLabelImg.setOpaque(false);
		zoneLabelImg.add(sens);
		zoneLabelImg.add(couleurC);
		add(carteSommetVue);
		add(zoneLabelImg);
		add(cartePioche);

	}
	
	/**
	 * Pivote la vue verticalement
	 */
	public void pivoterVertical()
	{
		setLayout(new GridLayout(3,1,0,10));
		repaint();
	}
	
	/**
	 * Retourne la carte de la pioche
	 * @return cartePioche
	 */
	public BoutonImg retourneCartePioche(){return cartePioche;}
	
	/**
	 * Retourne la dernière carte insérée dans la pioche
	 * @return carteSommetVue
	 */
	public LabelImg retourneZoneCarteVue(){return carteSommetVue;}

	/**
	 * Update pour l'actualisation de l'image indiquant le sens de jeu.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if( o instanceof PiocheModel)
		{
			try {
				carteSommetVue.EditImage(GestionImagesAppli.getInstance().retourneImgCarte(pc.retournePiocheModel().retourneDerniereCarte().retourneTaille(), pc.retournePiocheModel().retourneDerniereCarte().retourneCouleur()));
			} catch (IOException e) {e.printStackTrace();}
			cartePioche.editeTexteInfoHaut(""+(pc.retournePiocheModel().retourneNbCarte()-1));
			cartePioche.setVisible((Boolean) arg);
		} 
		else if (o instanceof PlateauModel)
		{
			PlateauModel platMod = (PlateauModel) o;
			try {
				couleurC.EditImage(GestionImagesAppli.getInstance().retourneImgCouleur(pc.retournePlateauModel().retourneCouleurChoisie()+4));
				if(platMod.retourneSens())
					sens.EditImage(GestionImagesAppli.getInstance().retourneImgSens(0));
				else
					sens.EditImage(GestionImagesAppli.getInstance().retourneImgSens(1));
			} catch (IOException e) {e.printStackTrace();}
			if (platMod.retourneNbCarteAPiocher() > 1)
				cartePioche.editeTexteInfoBas(" + "+platMod.retourneNbCarteAPiocher());
			else
				cartePioche.editeTexteInfoBas("");
		}
		repaint();
	}
}
