package fr.utt.isi.lo02.huitamericain.ihm.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BorderArrondis;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteVue;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;

/**
* Menu graphique permettant l'affichage des cartes d'un joueur pour la pioche
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public class DialogVuePiocheChezJoueur extends AbstrDialogVue{

    /* variables */
	private PlateauModel platMod;
    private AbstrJoueurModel joueurCourant;
    private AbstrJoueurModel ancienJoueur;
    private BorderArrondis borderSelection = new BorderArrondis(Color.GRAY, 10, 10, 2);
    
    /**
     * Constructeur du menu
     * @param _vJeu
     * @param _platMod
     * @throws IOException
     */
    public DialogVuePiocheChezJoueur(JeuVue _vJeu, PlateauModel _platMod) throws IOException {
    	super(_vJeu, ((GestionParametresAppli.getInstance().retourneEtatTailleCarteX()+5)* _platMod.retourneJoueurCourantAncien().retourneNbCarte())+100, 310, true);
    	platMod = _platMod;
		ancienJoueur = platMod.retourneJoueurCourantAncien();
		joueurCourant = platMod.retourneJoueurCourant();
		int nbCarte = ancienJoueur.retourneNbCarte();
		JPanel zone = new JPanel(new BorderLayout());
		JPanel centre = new JPanel(new GridLayout(1,nbCarte,5,0));
		
		centre.setOpaque(false);
		zone.setOpaque(false);
		JLabel texteHaut = new JLabel("Piochez chez "+platMod.retourneJoueurCourantAncien().retourneNom());
    	texteHaut.setFont(texteHaut.getFont().deriveFont((float)25.0));
    	texteHaut.setForeground(Color.WHITE);
    	
    	//Affiche toute les cartes de la main du joueur précédent
    	for (CarteModel iCarteMod : ancienJoueur.retourneVectCarte())
    	{
    		final CarteVue carteVueTemp = new CarteVue(iCarteMod);
    		carteVueTemp.masquerCarte(true);
    		carteVueTemp.setBorder(null);
    		carteVueTemp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CarteVue temp = (CarteVue) arg0.getSource();
					fadeOutSansTimer();
                	joueurCourant.piocherCarteChezJoueur(ancienJoueur, temp.retourneCarteModel());
                	platMod.editePiocheChezJoueur(false);
                	
                	//Si le joueur courant ancien est un joueur humain
                	if (ancienJoueur instanceof JoueurHumain)
                	{
                		//On affiche le bouton "Carte !" si il ne lui reste que deux cartes
	                	if (ancienJoueur.retourneNbCarte() == 2)
	                		((JoueurHumain) ancienJoueur).editeDerCarte(true);
	                	else
	                		((JoueurHumain) ancienJoueur).editeDerCarte(false);
	                }
                	platMod.roulementJoueurCourant();
				}
			});
    		
    		//Gestion de la selection avec un border
    		carteVueTemp.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent arg0) {}
				@Override
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {carteVueTemp.setBorder(null);}
				@Override
				public void mouseEntered(MouseEvent arg0) {carteVueTemp.setBorder(borderSelection);}
				@Override
				public void mouseClicked(MouseEvent arg0) {}
			});
    		centre.add(carteVueTemp);
    	}
    	texteHaut.setPreferredSize(new Dimension(0, 40));
    	zone.add(texteHaut, BorderLayout.NORTH);
    	zone.add(centre, BorderLayout.CENTER);
    	dialDetail.add(zone);
    }
}
