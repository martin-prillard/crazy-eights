package fr.utt.isi.lo02.huitamericain.ihm.popup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JPanel;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BorderArrondis;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BoutonImg;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;

/**
* Menu graphique permettant à l'utilateur de choisir une couleur parmit les quatres
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public class DialogVueChoixCouleur extends AbstrDialogVue{

    /* variables */
    private PlateauModel platMod;
    private BorderArrondis borderSelection  = new BorderArrondis(Color.GRAY, 10, 10, 3);
    
    /**
     * Constructeur
     * @param _vJeu
     * @param _platMod
     * @throws IOException
     */
    public DialogVueChoixCouleur(JeuVue _vJeu, PlateauModel _platMod) throws IOException {
        super(_vJeu, 525, 225, true);
        platMod = _platMod;
        JPanel centre = new JPanel(new GridLayout(0,4));
		
		for (int i = 0; i < 4; i++)
		{
			final BoutonImg img = new BoutonImg(GestionImagesAppli.getInstance().retourneImgCouleur(i));
			img.addActionListener(new ActionBouton(i));
			img.setPreferredSize(new Dimension(100,92));
			img.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent arg0) {}
				@Override
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {img.setBorder(null);}
				@Override
				public void mouseEntered(MouseEvent arg0) {img.setBorder(borderSelection);}
				@Override
				public void mouseClicked(MouseEvent arg0) {}
			});
			centre.add(img);
		}

		dialDetail.add(centre);
    } 
    

    /**
     * Attribution des boutons
     * @author MP
     */
    public class ActionBouton implements ActionListener {

        private int indiceActionCase;
        
        public ActionBouton(int pnumCase) {indiceActionCase = pnumCase;}

        public void actionPerformed(ActionEvent e) {
            switch (indiceActionCase) {
                /*Couleur trefle*/
                case 0:
                	platMod.editerCouleurChoisie(0);
                    break;
                /*Couleur Carreau*/
                case 1:
                	platMod.editerCouleurChoisie(1);
                    break;
                /*Couleur coeur*/
                case 2:
                	platMod.editerCouleurChoisie(2);
                    break;
                /*Couleur pique*/
                case 3:
                	platMod.editerCouleurChoisie(3);
                    break;
            }
            //Destruction de l'objet
			fadeOutSansTimer();
            platMod.roulementJoueurCourant();
        }
    }
}
