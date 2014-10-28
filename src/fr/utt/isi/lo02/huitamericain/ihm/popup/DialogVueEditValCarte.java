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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Menu graphique permettant de gerer la valeur des cartes du jeu
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public class DialogVueEditValCarte extends AbstrDialogVue{

    private JPanel grille = new JPanel(new GridLayout(15,2,0,5));
    private JSpinner s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s15;
    private JPanel zoneBouton = new JPanel(new GridLayout(1,3,10,0));
    private JButton bValide = new JButton("Valider");
    private JButton bParDefaut = new JButton("Par défaut");
    private JButton bQuitter = new JButton("Retour");

    /**
     * Constructeur du menu
     * @param _vJeu
     * @throws IOException
     */
    public DialogVueEditValCarte(JeuVue _vJeu) throws IOException {
    	super(_vJeu, 400, 660, true);
    	JPanel centre = new JPanel(new BorderLayout());
    	centre.setOpaque(false);
    	JLabel texteHaut = new JLabel("Valeur des cartes");
    	texteHaut.setFont(texteHaut.getFont().deriveFont((float)20.0));
    	texteHaut.setForeground(Color.WHITE);
    	zoneBouton.setOpaque(false);
    	grille.setOpaque(false);
    	bParDefaut.setBackground(Color.BLACK);
    	bParDefaut.setForeground(Color.white);
    	bValide.setBackground(Color.BLACK);
    	bValide.setForeground(Color.white);
        bQuitter.setBackground(Color.BLACK);
        bQuitter.setForeground(Color.white);
        bQuitter.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fadeOut();
			}
		});
        bValide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.AS, (Integer)s1.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.DEUX, (Integer)s2.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.TROIS, (Integer)s3.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.QUATRE, (Integer)s4.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.CINQ, (Integer)s5.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.SIX, (Integer)s6.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.SEPT, (Integer)s7.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.HUIT, (Integer)s8.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.NEUF, (Integer)s9.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.DIX, (Integer)s10.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.VALET, (Integer)s11.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.DAME, (Integer)s12.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.ROI, (Integer)s13.getModel().getValue());
					GestionParametresAppli.getInstance().editeValCarte(CarteModel.JOKER, (Integer)s15.getModel().getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
				fadeOut();
			}
		});
        bParDefaut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				s1.getModel().setValue(20);
				s2.getModel().setValue(2);
				s3.getModel().setValue(3);
				s4.getModel().setValue(4);
				s5.getModel().setValue(5);
				s6.getModel().setValue(6);
				s7.getModel().setValue(7);
				s8.getModel().setValue(32);
				s9.getModel().setValue(9);
				s10.getModel().setValue(10);
				s11.getModel().setValue(10);
				s12.getModel().setValue(10);
				s13.getModel().setValue(10);
				s15.getModel().setValue(50);
			}
		});
        
    	s1 = new JSpinner();
    	s2 = new JSpinner();
    	s3 = new JSpinner();
    	s4 = new JSpinner();
    	s5 = new JSpinner();
    	s6 = new JSpinner();
    	s7 = new JSpinner();
    	s8 = new JSpinner();
    	s9 = new JSpinner();
    	s10 = new JSpinner();
    	s11 = new JSpinner();
    	s12 = new JSpinner();
    	s13 = new JSpinner();
    	s15 = new JSpinner();
    	
    	
    	s1.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.AS), 1, 1000, 1));
    	s2.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.DEUX), 1, 1000, 1));
    	s3.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.TROIS), 1, 1000, 1));
    	s4.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.QUATRE), 1, 1000, 1));
    	s5.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.CINQ), 1, 1000, 1));
    	s6.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.SIX), 1, 1000, 1));
    	s7.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.SEPT), 1, 1000, 1));
    	s8.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.HUIT), 1, 1000, 1));
    	s9.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.NEUF), 1, 1000, 1));
    	s10.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.DIX), 1, 1000, 1));
    	s11.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.VALET), 1, 1000, 1));
    	s12.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.DAME), 1, 1000, 1));
    	s13.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.ROI), 1, 1000, 1));
    	s15.setModel(new SpinnerNumberModel(GestionParametresAppli.getInstance().retourneValCarte(CarteModel.JOKER), 1, 1000, 1));
    	
    	JLabel temp;
    	grille.add(temp = new JLabel("As"));
    	temp.setForeground(Color.white);
    	grille.add(s1);
    	grille.add(temp = new JLabel("Deux"));
    	temp.setForeground(Color.white);
    	grille.add(s2);
    	grille.add(temp = new JLabel("Trois"));
    	temp.setForeground(Color.white);
    	grille.add(s3);
    	grille.add(temp = new JLabel("Quatre"));
    	temp.setForeground(Color.white);
    	grille.add(s4);
    	grille.add(temp = new JLabel("Cinq"));
    	temp.setForeground(Color.white);
    	grille.add(s5);
    	grille.add(temp = new JLabel("Six"));
    	temp.setForeground(Color.white);
    	grille.add(s6);
    	grille.add(temp = new JLabel("Sept"));
    	temp.setForeground(Color.white);
    	grille.add(s7);
    	grille.add(temp = new JLabel("Huit"));
    	temp.setForeground(Color.white);
    	grille.add(s8);
    	grille.add(temp = new JLabel("Neuf"));
    	temp.setForeground(Color.white);
    	grille.add(s9);
    	grille.add(temp = new JLabel("Dix"));
    	temp.setForeground(Color.white);
    	grille.add(s10);
    	grille.add(temp = new JLabel("Valet"));
    	temp.setForeground(Color.white);
    	grille.add(s11);
    	grille.add(temp = new JLabel("Dame"));
    	temp.setForeground(Color.white);
    	grille.add(s12);
    	grille.add(temp = new JLabel("Roi"));
    	temp.setForeground(Color.white);
    	grille.add(s13);
    	grille.add(temp = new JLabel("Joker"));
    	temp.setForeground(Color.white);
    	grille.add(s15);

    	texteHaut.setPreferredSize(new Dimension(0,50));
    	zoneBouton.add(bValide);
    	zoneBouton.add(bParDefaut);
    	zoneBouton.add(bQuitter);
    	centre.add(texteHaut, BorderLayout.NORTH);
    	centre.add(grille, BorderLayout.CENTER);
    	centre.add(zoneBouton, BorderLayout.SOUTH);
		dialDetail.add(centre);
    }
}