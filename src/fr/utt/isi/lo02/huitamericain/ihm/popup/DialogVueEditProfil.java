package fr.utt.isi.lo02.huitamericain.ihm.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurIA;

/**
* Menu graphique permettant à l'utilateur de modifier le profil des joueurs IA
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public class DialogVueEditProfil extends AbstrDialogVue{

    private JPanel zoneBouton = new JPanel(new GridLayout(1,2,10,0));
    private JButton bValide = new JButton("Valider");
    private JButton bQuitter = new JButton("Retour");
    
    /**
     * Constructeur
     * @param _vJeu
     * @throws IOException
     */
	public DialogVueEditProfil(JeuVue _vJeu) throws IOException {
		super(_vJeu, 400, ((_vJeu.retourneJeuCon().retournePartie().retourneNbJoueurIA()+1)*30)+280, true);
		final PanelEditionIA panelIA = new PanelEditionIA(instanceJeu.retourneJeuCon().retournePartie());
		JPanel centre = new JPanel(new BorderLayout());
		JLabel texteHaut = new JLabel("Profil des joueurs");
    	texteHaut.setFont(texteHaut.getFont().deriveFont((float)30.0));
    	texteHaut.setForeground(Color.WHITE);
    	bValide.setBackground(Color.BLACK);
    	bValide.setForeground(Color.white);
        bQuitter.setBackground(Color.BLACK);
        bQuitter.setForeground(Color.white);
        
        //Quitter
        bQuitter.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fadeOut();
			}
		});
        
        //Valider le choix de l'utilisateur vis à vis de l'ia
        bValide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int cptJoueurIA = 0;
				for(AbstrJoueurModel joueurIA : instanceJeu.retourneJeuCon().retournePartie().retourneListJoueur())
					if(joueurIA instanceof JoueurIA)
					{
						((JoueurIA)joueurIA).editeStrategie(panelIA.retourneStrategyChoisie(cptJoueurIA));
						cptJoueurIA++;
					}
				fadeOut();
			}
		});
        
		panelIA.setOpaque(false);
		zoneBouton.setOpaque(false);
    	centre.setOpaque(false);
    	texteHaut.setPreferredSize(new Dimension(0,70));
    	zoneBouton.add(bValide);
    	zoneBouton.add(bQuitter);
    	centre.add(texteHaut, BorderLayout.NORTH);
    	centre.add(panelIA, BorderLayout.CENTER);
    	centre.add(zoneBouton, BorderLayout.SOUTH);
		dialDetail.add(centre);
	}
}
