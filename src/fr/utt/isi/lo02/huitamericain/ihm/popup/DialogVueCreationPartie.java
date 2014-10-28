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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BorderArrondis;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BoutonImg;
import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.partie.Partie;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.Jouable;

/**
* Menu graphique permettant à l'utilateur de créer une nouvelle partie
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public class DialogVueCreationPartie extends AbstrDialogVue{

	/* Constantes */
	private Border bType = BorderFactory.createTitledBorder("Type de partie");
	private Border bJoueur = BorderFactory.createTitledBorder("Nombre de joueur [2-8] : Humain & IA");
	private Border bNiveau = BorderFactory.createTitledBorder("Paramètres des joueurs");
	private Border bTheme = BorderFactory.createTitledBorder("Thème");
	private JPanel panelCentral = new JPanel(new BorderLayout());
	private JPanel panelEpicentre = new JPanel(new BorderLayout());
	private JPanel pType = new JPanel(new GridLayout(2,0,0,7));
	private JPanel pTypePartie = new JPanel(new BorderLayout());
	private JPanel pVictoire = new JPanel(new BorderLayout());
	private JSpinner jtNb = new JSpinner();
	private JPanel pJoueur;
	private JPanel pNiveau = new JPanel();
	private JPanel pTheme = new JPanel(new GridLayout(0,3,0,0));
	private JButton btChoix = new JButton("Nouvelle partie");
	private JSpinner listeNbCarteJoueur = new JSpinner();
	private JSpinner listeVictoire;
	//Joueur Humain
	private ButtonGroup  cbNbJoueurHumain = new ButtonGroup ();
	private JRadioButton rbh1 = new JRadioButton("1",true);
    private JRadioButton rbh2 = new JRadioButton("2",false);
    private JRadioButton rbh3 = new JRadioButton("3",false);
    private JRadioButton rbh4 = new JRadioButton("4",false);
    private JRadioButton rbh5 = new JRadioButton("5",false);
    private JRadioButton rbh6 = new JRadioButton("6",false);
    private JRadioButton rbh7 = new JRadioButton("7",false);
    private JRadioButton rbh8 = new JRadioButton("8",false);
	//Joueur IA
	private ButtonGroup  cbNbJoueurIA = new ButtonGroup ();
	private JRadioButton rb0 = new JRadioButton("0",false);
	private JRadioButton rb1 = new JRadioButton("1",false);
    private JRadioButton rb2 = new JRadioButton("2",false);
    private JRadioButton rb3 = new JRadioButton("3",false);
    private JRadioButton rb4 = new JRadioButton("4",false);
    private JRadioButton rb5 = new JRadioButton("5",false);
    private JRadioButton rb6 = new JRadioButton("6",false);
    private JRadioButton rb7 = new JRadioButton("7",true);
    private JButton bQuitter = new JButton("Retour");
    private int themeChoix;
    private JLabel infoVictoire = new JLabel("Condition de victoire : ");
    private JLabel infoNbCarteJoueur = new JLabel(" Nombre de carte par joueur en début de partie : ");
    private Color transparent = new Color(0,0,0,0);
    private JPanel zoneLargeBouton = new JPanel(new BorderLayout());
    private JPanel zoneBouton = new JPanel(new GridLayout(1,2,10,0));
    private PanelEditionIA niveauIA;
    private int nbJoueurHumain = 1;
    private int nbJoueurIa = 7;
    private String[] tabType = new String[]{"manches", "points"};
    private boolean retourSurStatVue = false;
    private BorderArrondis borderSelection  = new BorderArrondis(Color.GRAY, 8, 8, 2);
    private JLabel titre = new JLabel("Nouvelle partie");
    private JPanel zoneTitreEtJoueur = new JPanel(new BorderLayout());
	
    /**
     * Constructeur création partie
     * @param _vJeu
     * @param _retourMasquer
     * @param _retourSurStatVue
     * @throws IOException
     */
	public DialogVueCreationPartie(JeuVue _vJeu, boolean _retourMasquer, boolean _retourSurStatVue) throws IOException
	{
		super(_vJeu, 800, 600, true);	
		retourSurStatVue = _retourSurStatVue;
		attribChoix(_retourMasquer);
		attribGraphisme();
		attribAction();
			
		setVisible(true);
	}
	
	/**
	 * Initialisation des composants
	 * @param retourMasquer
	 * @throws IOException
	 */
	public void attribChoix(boolean retourMasquer) throws IOException
	{  
		themeChoix = GestionParametresAppli.getInstance().retourneTheme();
    	titre.setFont(titre.getFont().deriveFont((float)45.0));
//		titre.setFont(new Font("Sherif",Font.CENTER_BASELINE,45));
    	titre.setForeground(Color.WHITE);
		pJoueur = new JPanel(new GridLayout(2,9,0,0));
		niveauIA = new PanelEditionIA(nbJoueurIa, nbJoueurHumain);
		zoneTitreEtJoueur.add(titre, BorderLayout.NORTH);
		zoneTitreEtJoueur.add(pJoueur, BorderLayout.CENTER);
		panelEpicentre.add(zoneTitreEtJoueur, BorderLayout.NORTH);
		panelEpicentre.add(pType, BorderLayout.CENTER);
		panelEpicentre.add(pTheme, BorderLayout.SOUTH);
		panelCentral.add(panelEpicentre, BorderLayout.CENTER);
		panelCentral.add(pNiveau, BorderLayout.EAST);
		zoneBouton.add(btChoix);
		if(!retourMasquer)
			zoneBouton.add(bQuitter);
		zoneLargeBouton.add(zoneBouton, BorderLayout.EAST);
		panelCentral.add(zoneLargeBouton, BorderLayout.SOUTH);
		zoneTitreEtJoueur.setOpaque(false);
		zoneLargeBouton.setOpaque(false);
		dialDetail.add(panelCentral);
		
		 listeVictoire = new JSpinner(new SpinnerListModel(tabType));
		 listeNbCarteJoueur.setModel(new SpinnerNumberModel(8, 8, 50, 1));
		 pVictoire.add(infoVictoire, BorderLayout.WEST);
		 pVictoire.add(jtNb, BorderLayout.CENTER);
		 pVictoire.add(listeVictoire, BorderLayout.EAST);
		 pTypePartie.add(infoNbCarteJoueur, BorderLayout.CENTER);
		 pTypePartie.add(listeNbCarteJoueur, BorderLayout.EAST);
		 pType.add(pTypePartie);
		 pType.add(pVictoire);
		 jtNb.setModel(new SpinnerNumberModel(5, 1, 1000, 1));
		 cbNbJoueurIA.add(rb0);
		 cbNbJoueurIA.add(rb1);
		 cbNbJoueurIA.add(rb2);
		 cbNbJoueurIA.add(rb3);
		 cbNbJoueurIA.add(rb4);
		 cbNbJoueurIA.add(rb5);
		 cbNbJoueurIA.add(rb6);
		 cbNbJoueurIA.add(rb7);
		 cbNbJoueurHumain.add(rbh1);
		 cbNbJoueurHumain.add(rbh2);
		 cbNbJoueurHumain.add(rbh3);
		 cbNbJoueurHumain.add(rbh4);
		 cbNbJoueurHumain.add(rbh5);
		 cbNbJoueurHumain.add(rbh6);
		 cbNbJoueurHumain.add(rbh7);
		 cbNbJoueurHumain.add(rbh8);
		 JLabel info = new JLabel("J-H");
		 info.setForeground(Color.orange);
		 pJoueur.add(info);
		 pJoueur.add(rbh1);
		 pJoueur.add(rbh2);
		 pJoueur.add(rbh3);
		 pJoueur.add(rbh4);
		 pJoueur.add(rbh5);
		 pJoueur.add(rbh6);
		 pJoueur.add(rbh7);
		 pJoueur.add(rbh8);
		 info = new JLabel("J-IA");
		 info.setForeground(Color.orange);
		 pJoueur.add(info);
		 pJoueur.add(rb0);
		 pJoueur.add(rb1);
		 pJoueur.add(rb2);
		 pJoueur.add(rb3);
		 pJoueur.add(rb4);
		 pJoueur.add(rb5);
		 pJoueur.add(rb6);
		 pJoueur.add(rb7);
		 rbh1.addActionListener(new ListenerNbJoueur());
		 rbh2.addActionListener(new ListenerNbJoueur());
		 rbh3.addActionListener(new ListenerNbJoueur());
		 rbh4.addActionListener(new ListenerNbJoueur());
		 rbh5.addActionListener(new ListenerNbJoueur());
		 rbh6.addActionListener(new ListenerNbJoueur());
		 rbh7.addActionListener(new ListenerNbJoueur());
		 rbh8.addActionListener(new ListenerNbJoueur());
		 rb0.addActionListener(new ListenerNbIA());
		 rb1.addActionListener(new ListenerNbIA());
		 rb2.addActionListener(new ListenerNbIA());
		 rb3.addActionListener(new ListenerNbIA());
		 rb4.addActionListener(new ListenerNbIA());
		 rb5.addActionListener(new ListenerNbIA());
		 rb6.addActionListener(new ListenerNbIA());
		 rb7.addActionListener(new ListenerNbIA());
		 pNiveau.add(niveauIA);
		 //Choix du thème
		 for (int i = 0; i<3; i++)
		 {
			 final BoutonImg btTheme1 = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(i));
			 btTheme1.addActionListener(new ActionBouton(i));
			 btTheme1.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent arg0) {}
					@Override
					public void mousePressed(MouseEvent arg0) {}
					@Override
					public void mouseExited(MouseEvent arg0) {btTheme1.setBorder(null);}
					@Override
					public void mouseEntered(MouseEvent arg0) {btTheme1.setBorder(borderSelection);}
					@Override
					public void mouseClicked(MouseEvent arg0) {}
			 });
			 pTheme.add(btTheme1);
		 }
	}
	
	 class ListenerNbIA implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			 JRadioButton rad = (JRadioButton) e.getSource();
			 //Si l'utilisateur a changer la valeur du nombre de joueur IA
			 if (nbJoueurIa != Integer.parseInt(rad.getText()))
			 {
				 nbJoueurIa = Integer.parseInt(rad.getText());
				 
				//Gere le fait qu'il faut au moins deux joueurs pour faire une partie
				 if(nbJoueurHumain == 1 && nbJoueurIa == 0)
				 {
					 cbNbJoueurIA.setSelected(rb1.getModel(), true);
					 nbJoueurIa = Integer.parseInt(rb1.getText());
				 }else{
					 //Gere le fait qu'il faut au maximum 8 joueurs
					 if(nbJoueurHumain+nbJoueurIa > 8)
					 {
						 int valMax = 8 - (nbJoueurIa);
						 cbNbJoueurHumain.setSelected(retourneRadioButton(valMax, true).getModel(), true);
						 //On actualise le nombre de joueur Humain
						 nbJoueurHumain = Integer.parseInt(retourneRadioButton(valMax, true).getText());
					 }
				 }
				 //Dans le cas general, on actualise le tableau des stratégies
				 niveauIA.actualiser(nbJoueurIa, nbJoueurHumain);
				 validate();
			 }
		}
	 }
	 
	 class ListenerNbJoueur implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 JRadioButton rad = (JRadioButton) e.getSource();
				 //Si l'utilisateur a changer la valeur du nombre de joueur humain
				 if (nbJoueurHumain != Integer.parseInt(rad.getText()))
				 {
					 nbJoueurHumain = Integer.parseInt(rad.getText());
				
					 //Gere le fait qu'il faut au moins deux joueurs pour faire une partie
					 if(nbJoueurHumain == 1 && nbJoueurIa == 0)
					 {
						 cbNbJoueurIA.setSelected(rb1.getModel(), true);
						 nbJoueurIa = Integer.parseInt(rb1.getText());
					 }else{
						 //Gere le fait qu'il faut au maximum 8 joueurs
						 if(nbJoueurHumain+nbJoueurIa > 8)
						 {
							 int valMax = 8 - (nbJoueurHumain);
							 cbNbJoueurIA.setSelected(retourneRadioButton(valMax, false).getModel(), true);
							 //On actualise le nombre de joueur IA
							 nbJoueurIa = Integer.parseInt(retourneRadioButton(valMax, false).getText());
						 }
					 }
					 //Dans le cas general, on actualise le tableau des stratégies
					 niveauIA.actualiser(nbJoueurIa,  nbJoueurHumain);
					 validate();
				 }
			}
		 }
	 
	 /**
	  * Retourne le JRadioButton correspondant au bon nombre de joueur
	  * @param val
	  * @param joueurHumain
	  * @return JRadioButton concerné
	  */
	 public JRadioButton retourneRadioButton(int val, boolean joueurHumain)
	 {
		 JRadioButton rad = null;
		 
		 if(joueurHumain)
		 {
			 switch(val)
			 {
				 case 1:
					 rad = rbh1;
					 break;
				 case 2:
					 rad = rbh2;
					 break;
				 case 3:
					 rad = rbh3;
					 break;
				 case 4:
					 rad = rbh4;
					 break;
				 case 5:
					 rad = rbh5;
					 break;
				 case 6:
					 rad = rbh6;
					 break;
				 case 7:
					 rad = rbh7;
					 break;
				 case 8:
					 rad = rbh8;
					 break;
			 }
		 }else{
			 switch(val)
			 {
			 	 case 0:
			 		 rad = rb0;
			 		 break;
				 case 1:
					 rad = rb1;
					 break;
				 case 2:
					 rad = rb2;
					 break;
				 case 3:
					 rad = rb3;
					 break;
				 case 4:
					 rad = rb4;
					 break;
				 case 5:
					 rad = rb5;
					 break;
				 case 6:
					 rad = rb6;
					 break;
				 case 7:
					 rad = rb7;
					 break;
			 }
		 }
		 return rad;
	 }
	 
	 /**
	  * Initialisation des composants
	  */
	public void attribGraphisme()
	{
        bQuitter.setBackground(Color.BLACK);
        bQuitter.setForeground(Color.white);
    	titre.setPreferredSize(new Dimension(500, 120));
		pType.setPreferredSize(new Dimension(350,105));
		pJoueur.setPreferredSize(new Dimension(350,135));
		pTheme.setPreferredSize(new Dimension(350,130));
		pNiveau.setPreferredSize(new Dimension(350,0));
		panelCentral.setPreferredSize(new Dimension(700,515));
		pType.setBorder(bType);
		pJoueur.setBorder(bJoueur);
		pNiveau.setBorder(bNiveau);
		pTheme.setBorder(bTheme);
		zoneBouton.setOpaque(false);
		panelEpicentre.setOpaque(false);
		pJoueur.setOpaque(false);
		pNiveau.setOpaque(false);
		pType.setOpaque(false);
		pTheme.setOpaque(false);
		btChoix.setOpaque(false);
		dialDetail.setOpaque(false);
		panelCentral.setOpaque(false);
		pTypePartie.setOpaque(false);
		infoNbCarteJoueur.setForeground(Color.white);
		infoVictoire.setForeground(Color.white);
		infoVictoire.setOpaque(false);
		infoNbCarteJoueur.setOpaque(false);
		listeNbCarteJoueur.setOpaque(false);
		jtNb.setOpaque(false);
		pVictoire.setOpaque(false);
		rbh1.setForeground(Color.white);
		rbh2.setForeground(Color.white);
		rbh3.setForeground(Color.white);
		rbh4.setForeground(Color.white);
		rbh5.setForeground(Color.white);
		rbh6.setForeground(Color.white);
		rbh7.setForeground(Color.white);
		rbh8.setForeground(Color.white);
		rb0.setForeground(Color.white);
		rb1.setForeground(Color.white);
		rb2.setForeground(Color.white);
		rb3.setForeground(Color.white);
		rb4.setForeground(Color.white);
		rb5.setForeground(Color.white);
		rb6.setForeground(Color.white);
		rb7.setForeground(Color.white);
		btChoix.setOpaque(false);
		niveauIA.setBackground(transparent);
		niveauIA.setForeground(Color.white);
		jtNb.setBackground(transparent);
		jtNb.setForeground(Color.white);
		listeNbCarteJoueur.setBackground(transparent);
		listeNbCarteJoueur.setForeground(Color.white);
        btChoix.setBackground(Color.BLACK);
        btChoix.setForeground(Color.white);
	}
	
	/**
	 * Attribution des actions aux différents boutons
	 */
	public void attribAction()
	{
		btChoix.addActionListener(new ActionBouton(4));
        bQuitter.addActionListener(new ActionBouton(5));
	}
	
	/**
	 * Actions des boutons
	 * @author MP
	 */
    public class ActionBouton implements ActionListener {

        private int indiceActionCase;

        public ActionBouton(int pnumCase) {
            super();
            indiceActionCase = pnumCase;
        }

        public void actionPerformed(ActionEvent e) {

            switch (indiceActionCase) {
                /*Active un thème*/
                case 0:
                	themeChoix = 1;
                    break;
                case 1:
                	themeChoix = 2;
                    break;
                case 2:
                	themeChoix = 3;
                    break;
                //Lance une nouvelle partie en récuperant l'etat des composants de la vue
                case 4:
                	ArrayList<Jouable> listNiveauIA = new ArrayList<Jouable>();
                    int nbCarteJoueur = (Integer)listeNbCarteJoueur.getModel().getValue(); 
                    int nbPourVictoire = (Integer)jtNb.getModel().getValue();
                    boolean victoireParPoints = false;
                    if ((String)listeVictoire.getModel().getValue() == "points") //Manche = 0; Points = 1
                    	victoireParPoints = true;
                    for (int i = 0; i < nbJoueurIa; i++)
                    	listNiveauIA.add(niveauIA.retourneStrategyChoisie(i));
                    
                    //Création du nouveau plateau
					try {
						GestionParametresAppli.getInstance().editeTheme(themeChoix);
						instanceJeu.retourneJeuCon().nouvellePartie(new Partie(nbJoueurHumain,nbJoueurIa, nbCarteJoueur, nbPourVictoire, victoireParPoints, listNiveauIA, niveauIA.retourneListNomJH()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
                    fadeOut();
					break;
                case 5:
                	if (retourSurStatVue)
                	{
                		fadeOutSansTimer();
                		try {
							new DialogVueStatVue(instanceJeu, instanceJeu.retourneJeuCon().retournePartie().retourneStatModel(), 0);
						} catch (IOException e1) {e1.printStackTrace();}
                	}
                	else
                	{
                		fadeOut();
                	}
                	break;
            }
        }
    }
}
