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
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BorderArrondis;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BoutonImg;

/**
* Menu graphique permettant à l'utilateur de choisir une vue de plateau parmit les trois
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public class DialogVueChoixPlateau extends AbstrDialogVue{

    private BorderArrondis borderSelection  = new BorderArrondis(Color.GRAY, 8, 8, 2);
    
    /**
     * Constructeur, choix de la vue du plateau
     * @param _vJeu
     * @throws IOException
     */
    public DialogVueChoixPlateau(JeuVue _vJeu) throws IOException {
    	super(_vJeu, 920, 300, true);
    	JPanel centre = new JPanel(new GridLayout(0,3));
		
		for (int i = 0; i < 3; i++)
		{
			final BoutonImg img = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(i));
			img.addActionListener(new ActionBouton(i));
			img.setPreferredSize(new Dimension(265,175));
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
	 *  Attribution des boutons 
	 */
    public class ActionBouton implements ActionListener {

        private int indiceActionCase;
        
        public ActionBouton(int pnumCase) {indiceActionCase = pnumCase;}

        public void actionPerformed(ActionEvent e) {
            switch (indiceActionCase) {
                /*Vue 1*/
                case 0:
					try {
						GestionParametresAppli.getInstance().editeTheme(1);
					} catch (IOException e1) {e1.printStackTrace();}
                    break;
                /*Vue 2*/
                case 1:
					try {
						GestionParametresAppli.getInstance().editeTheme(2);
					} catch (IOException e1) {e1.printStackTrace();}
                    break;
                /*Vue 3*/
                case 2:
					try {
						GestionParametresAppli.getInstance().editeTheme(3);
					} catch (IOException e1) {e1.printStackTrace();}
                    break;
            }
            //Destruction de l'objet
			fadeOut();
			//Changement de vue du plateau
			try {
				instanceJeu.changePlateau();
			} catch (IOException e1) {e1.printStackTrace();}
        }
    }
}
