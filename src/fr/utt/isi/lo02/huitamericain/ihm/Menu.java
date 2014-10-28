package fr.utt.isi.lo02.huitamericain.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.AccesFileChooser;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BoutonImg;
import fr.utt.isi.lo02.huitamericain.ihm.composant.CourbeAnim;
import fr.utt.isi.lo02.huitamericain.ihm.composant.FiltreSimple;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueChoixPlateau;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueCreationPartie;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueEditProfil;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueEditValCarte;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueImprimerScore;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueMenuAide;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueStatVue;
import fr.utt.isi.lo02.huitamericain.partie.Partie;

/**
* Classe menu de la vue graphique
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class Menu extends JMenuBar {

    /* -------- MENU 1 -------- */
    private JMenuBar menu1 = new JMenuBar();
    //Onglet menu1
    private JMenu fichierM1 = new JMenu("Fichier");
    private JMenu editerM1 = new JMenu("Editer");
    private JMenu affichageM1 = new JMenu("Affichage");
    private JMenu aideM1 = new JMenu("Aide");
    //fichier
    private JMenuItem nouvelleDonneM1 = new JMenuItem("Nouvelle donne");
    private JMenuItem partiePersoM1 = new JMenuItem("Nouvelle partie");
    private JMenuItem chargerM1 = new JMenuItem("Charger une partie");
    private JMenuItem sauvPartieM1 = new JMenuItem("Sauvegarder la partie");
    private JMenu sauvScoreM1 = new JMenu("Sauvegarder le score");
    private JMenuItem sauvScoreImgM1 = new JMenuItem("Image .JPEG");
    private JMenuItem sauvScoreTxtM1 = new JMenuItem("Fichier .TXT");
    private JMenuItem imprimM1 = new JMenuItem("Imprimer le score");
    private JMenuItem quitM1 = new JMenuItem("Quitter");
    //Editer
    private JMenu sonM1 = new JMenu("Audio");
    private JMenuItem defSonM1 = new JMenuItem("Par défaut");
    private JMenuItem valCarteM1 = new JMenuItem("Valeur des cartes");
    private JMenuItem choixSonM1 = new JMenuItem("Personnalisé");
    private JCheckBox actSonM1 = new JCheckBox("Son", true);
    private JMenuItem profilM1 = new JMenuItem("Profil des joueurs");
    //Affichage
    private JRadioButtonMenuItem animCrazyM1 = new JRadioButtonMenuItem("Crazy");
    private JRadioButtonMenuItem animNormalM1 = new JRadioButtonMenuItem("Normale", true);
    private JRadioButtonMenuItem animAucuneM1 = new JRadioButtonMenuItem("Aucune");
    private JRadioButtonMenuItem carteTheme1M1 = new JRadioButtonMenuItem("Old-school");
    private JRadioButtonMenuItem carteTheme2M2 = new JRadioButtonMenuItem("Crazy Eight", true);
    private JRadioButtonMenuItem carteGrdM1 = new JRadioButtonMenuItem("Grande");
    private JRadioButtonMenuItem carteMoyM1 = new JRadioButtonMenuItem("Normale", true);
    private JRadioButtonMenuItem cartePetM1 = new JRadioButtonMenuItem("Petite");
    private JMenuItem choixPlateauM1 = new JMenuItem("Vue du plateau");
    private JMenuItem statM1 = new JMenuItem("Vue des scores");
    private JMenu animM1 = new JMenu("Animations");
    private JMenu tailleM1 = new JMenu("Taille des cartes");
    private JMenu carteTM1 = new JMenu("Thème des cartes");
    private JMenu themeM1 = new JMenu("Thème du jeu");
    private JMenu avanM1 = new JMenu("Options avancées");
    private JCheckBox aliaM1 = new JCheckBox("Antialiasing", true);
    private JMenu infoSlide = new JMenu("Niveau de flou");
    private JSlider slideFlou;
    private JCheckBox dbsM1 = new JCheckBox("D-Buffering Software", false);
    private JCheckBox affScoreM1 = new JCheckBox("Score", true);
    private JCheckBox aideCarteM1 = new JCheckBox("Cartes à jouer", true);
    private JRadioButtonMenuItem t1M1 = new JRadioButtonMenuItem("Crazy Eight", true);
    private JRadioButtonMenuItem t2M1 = new JRadioButtonMenuItem("Classique");
    private JRadioButtonMenuItem t3M1 = new JRadioButtonMenuItem("Roses");
    private JRadioButtonMenuItem t4M1 = new JRadioButtonMenuItem("Neige");
    private JRadioButtonMenuItem t5M1 = new JRadioButtonMenuItem("Océan");
    private JRadioButtonMenuItem t6PersoM1 = new JRadioButtonMenuItem("Personnalisé");
    private UIManager.LookAndFeelInfo[] listeLAF;
    private ButtonGroup bg = new ButtonGroup();
    private ButtonGroup bgTaille = new ButtonGroup();
    private ButtonGroup bgThemeCarte = new ButtonGroup();
    private ButtonGroup bgAnim = new ButtonGroup();
    private JRadioButtonMenuItem[] tJrbi;
    //Aide
    private JMenuItem regleM1 = new JMenuItem("Règles du jeu");
    private JMenuItem aProposDeM1 = new JMenuItem("A propos de");
    
    /* -------- MENU 2 -------- */
    private BoutonImg partiePersoM2;
    private BoutonImg chargerM2;
    private BoutonImg choixPlateauM2;
    private BoutonImg statM2;
    private BoutonImg sauvegarderM2;
    private BoutonImg actSonM2;
    private BoutonImg imprimM2;
    
    /* -------- VARIABLES -------- */
    private JPanel menuBG1 = new JPanel(new GridLayout(1, 10, 8, 0));
    private Border menuBord = BorderFactory.createLineBorder(Color.BLACK);
    private CourbeAnim curv;
    private FileFilter filtreImage;
	private JFileChooser choixFileChooser;
	private int indiceTheme = 0;
    private boolean sonActiver = true;
	private JeuVue vJeu;

	/**
	 * Constructeur du menu
	 * @param _vJeu
	 * @throws IOException
	 */
    public Menu(JeuVue _vJeu) throws IOException {
    	setLayout(new BorderLayout());
    	vJeu = _vJeu;	
        attribMenu1();
        attribMenu2();
        attribRaccourcieClavier();
        attribAction(); 
    }

    /**
     * Initialisation des composants du menu1 (menu haut)
     * @throws IOException
     */
    public void attribMenu1() throws IOException {
    	/* Slide de flou */
    	slideFlou = new JSlider(0,50,GestionParametresAppli.getInstance().retourneFlou());
    	slideFlou.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				try {
					GestionParametresAppli.getInstance().editeFlou(slideFlou.getValue());
				} catch (IOException e) {e.printStackTrace();}
			}
		});
    	slideFlou.setPaintTicks(true);
    	slideFlou.setMinorTickSpacing(5);
    	slideFlou.setMajorTickSpacing(25);
    	slideFlou.setPaintLabels(true);
    	slideFlou.setPaintTicks(true);
    	slideFlou.setPaintTrack(true);
    	slideFlou.setPreferredSize(new Dimension(110,40));
        /* Add */
        bg.add(t1M1);
        bg.add(t2M1);
        bg.add(t3M1);
        bg.add(t4M1);
        bg.add(t5M1);
        bg.add(t6PersoM1);
        themeM1.add(getJMenu());
        themeM1.addSeparator();
        themeM1.add(t1M1);
        themeM1.add(t2M1);
        themeM1.add(t3M1);
        themeM1.add(t4M1);
        themeM1.add(t5M1);
        themeM1.addSeparator();
        themeM1.add(t6PersoM1);
        bgAnim.add(animCrazyM1);
        bgAnim.add(animNormalM1);
        bgAnim.add(animAucuneM1);
        bgTaille.add(carteGrdM1);
        bgTaille.add(carteMoyM1);
        bgTaille.add(cartePetM1);
        tailleM1.add(carteGrdM1);
        tailleM1.add(carteMoyM1);
        tailleM1.add(cartePetM1);
        fichierM1.add(partiePersoM1);
        fichierM1.add(nouvelleDonneM1);
        fichierM1.addSeparator();
        fichierM1.add(chargerM1);
        fichierM1.addSeparator();
        fichierM1.add(sauvPartieM1);
        fichierM1.add(sauvScoreM1);
        sauvScoreM1.add(sauvScoreTxtM1);
        sauvScoreM1.add(sauvScoreImgM1);
        fichierM1.addSeparator();
        fichierM1.add(imprimM1);
        fichierM1.addSeparator();
        fichierM1.add(quitM1);
        editerM1.add(valCarteM1);
        editerM1.add(profilM1);
        editerM1.addSeparator();
        editerM1.add(sonM1);
        sonM1.add(defSonM1);
        sonM1.addSeparator();
        sonM1.add(choixSonM1);
        sonM1.addSeparator();
        sonM1.add(actSonM1);
        affichageM1.add(themeM1);
        animM1.add(animCrazyM1);
        animM1.add(animNormalM1);
        animM1.add(animAucuneM1);
        affichageM1.add(animM1);
        affichageM1.addSeparator();
        affichageM1.add(tailleM1);
        affichageM1.add(carteTM1);
        affichageM1.add(choixPlateauM1);
        affichageM1.add(statM1);
        bgThemeCarte.add(carteTheme1M1);
        bgThemeCarte.add(carteTheme2M2);
        carteTM1.add(carteTheme2M2);
        carteTM1.add(carteTheme1M1);
        affichageM1.addSeparator();
        affichageM1.add(affScoreM1);
        affichageM1.add(aideCarteM1);
        affichageM1.addSeparator();
        affichageM1.add(avanM1);
        infoSlide.add(slideFlou);
        avanM1.add(infoSlide);
        avanM1.addSeparator();
        avanM1.add(aliaM1);
        avanM1.addSeparator();
        avanM1.add(dbsM1);
        aideM1.add(regleM1);
        aideM1.addSeparator();
        aideM1.add(aProposDeM1);
        menu1.add(fichierM1);
        menu1.add(editerM1);
        menu1.add(affichageM1);
        menu1.add(aideM1);
        add(menu1, BorderLayout.NORTH);
        /* Detail */
        menu1.setBorder(menuBord);
        menu1.setPreferredSize(new Dimension(this.getWidth(), 20));
        /* Enabled */
        carteTM1.setEnabled(false);
        tailleM1.setEnabled(false);
        nouvelleDonneM1.setEnabled(false);
        choixPlateauM1.setEnabled(false);
        profilM1.setEnabled(false);
        statM1.setEnabled(false);
        sauvPartieM1.setEnabled(false);
        sauvScoreM1.setEnabled(false);
        imprimM1.setEnabled(false);
    }
    
    /**
     * Initialisation des composants du menu2 (menu bas avec icones)
     * @throws IOException
     */
    public void attribMenu2() throws IOException {
    	/* Initialisation */
    	curv = new CourbeAnim(35);
        curv.setLayout(new BorderLayout());
        partiePersoM2 = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(3));
        imprimM2 = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(4));
        chargerM2 = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(6));
        choixPlateauM2 = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(10));
        statM2 = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(7));
        sauvegarderM2 = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(5));
        actSonM2 = new BoutonImg(GestionImagesAppli.getInstance().retourneImgMenu(8));
        menuBG1.setOpaque(false);
        /* Size */
        actSonM2.setPreferredSize(new Dimension(35,35));
        partiePersoM2.setPreferredSize(new Dimension(35,35));
        imprimM2.setPreferredSize(new Dimension(35,35));
        chargerM2.setPreferredSize(new Dimension(35,35));
        choixPlateauM2.setPreferredSize(new Dimension(35,35));
        statM2.setPreferredSize(new Dimension(35,35));
        sauvegarderM2.setPreferredSize(new Dimension(35,35));
        curv.setPreferredSize(new Dimension(this.getWidth(), 35));
        /* Add */
        menuBG1.add(partiePersoM2);
        menuBG1.add(chargerM2);
        menuBG1.add(sauvegarderM2);
        menuBG1.add(imprimM2);
        menuBG1.add(choixPlateauM2);
        menuBG1.add(statM2);
        curv.add(menuBG1, BorderLayout.WEST);
        curv.add(actSonM2, BorderLayout.EAST);
        add(curv, BorderLayout.SOUTH);
        /* Visible */
        sauvegarderM2.setVisible(false);
        imprimM2.setVisible(false);
        choixPlateauM2.setVisible(false);
        statM2.setVisible(false);
    }
    
    /**
     * Attribut des raccourcies clavier pour certain menu
     */
    public void attribRaccourcieClavier()
    {
    	t1M1.setAccelerator(KeyStroke.getKeyStroke('1', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	t2M1.setAccelerator(KeyStroke.getKeyStroke('2', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	t3M1.setAccelerator(KeyStroke.getKeyStroke('3', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	t4M1.setAccelerator(KeyStroke.getKeyStroke('4', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    	t5M1.setAccelerator(KeyStroke.getKeyStroke('5', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }
    
    /**
     * Donne une action à chaques boutons
     */
    public void attribAction() {
    	//Fichier
        quitM1.addActionListener(new ActionBouton(1));
        partiePersoM1.addActionListener(new ActionBouton(2));
        partiePersoM2.addActionListener(new ActionBouton(2));
        nouvelleDonneM1.addActionListener(new ActionBouton(3));
        chargerM2.addActionListener(new ActionBouton(4));
        chargerM1.addActionListener(new ActionBouton(4));
        sauvPartieM1.addActionListener(new ActionBouton(5));
        sauvegarderM2.addActionListener(new ActionBouton(5));
        sauvScoreTxtM1.addActionListener(new ActionBouton(6));
        sauvScoreImgM1.addActionListener(new ActionBouton(7));
        imprimM1.addActionListener(new ActionBouton(8));
        imprimM2.addActionListener(new ActionBouton(8));
        //Editer
        valCarteM1.addActionListener(new ActionBouton(9));
        profilM1.addActionListener(new ActionBouton(10));
        defSonM1.addActionListener(new ActionBouton(11));
        choixSonM1.addActionListener(new ActionBouton(12));
        actSonM1.addActionListener(new ActionBouton(13));
        actSonM2.addActionListener(new ActionBouton(13));
        //Affichage
        t1M1.addActionListener(new ActionBouton(14));
        t2M1.addActionListener(new ActionBouton(15));
        t3M1.addActionListener(new ActionBouton(16));
        t4M1.addActionListener(new ActionBouton(17));
        t5M1.addActionListener(new ActionBouton(18));
        t6PersoM1.addActionListener(new ActionBouton(19));
        animCrazyM1.addActionListener(new ActionBouton(20));
        animNormalM1.addActionListener(new ActionBouton(21));
        animAucuneM1.addActionListener(new ActionBouton(22));
        carteGrdM1.addActionListener(new ActionBouton(23));
        carteMoyM1.addActionListener(new ActionBouton(24));
        cartePetM1.addActionListener(new ActionBouton(25));
        carteTheme1M1.addActionListener(new ActionBouton(26));
        carteTheme2M2.addActionListener(new ActionBouton(27));
        choixPlateauM1.addActionListener(new ActionBouton(28));
        choixPlateauM2.addActionListener(new ActionBouton(28));
        statM1.addActionListener(new ActionBouton(29));
        statM2.addActionListener(new ActionBouton(29));
        affScoreM1.addActionListener(new ActionBouton(30));
        aideCarteM1.addActionListener(new ActionBouton(31));
        aliaM1.addActionListener(new ActionBouton(32));
        dbsM1.addActionListener(new ActionBouton(33));
        //Aide
        regleM1.addActionListener(new ActionBouton(34));
        aProposDeM1.addActionListener(new ActionBouton(35));
    }
    

    /**
     * Active ou désactive des élements du menu en fonction de la partie créer par l'utilisateur
     */
    public void activerElementMenu()
    {
    	carteTM1.setEnabled(true);
    	tailleM1.setEnabled(true);
		nouvelleDonneM1.setEnabled(true);
        statM1.setEnabled(true);
        sauvPartieM1.setEnabled(true);
        sauvScoreM1.setEnabled(true);
        imprimM1.setEnabled(true);
        choixPlateauM1.setEnabled(true);
        sauvegarderM2.setVisible(true);
        imprimM2.setVisible(true);
        choixPlateauM2.setVisible(true);
        statM2.setVisible(true);
        
        //On active le menu d'édition des joueurs IA que si il existe des joueurs IA dans cette partie
		if(vJeu.retourneJeuCon().retournePartie().retourneNbJoueurIA() == 0)
			profilM1.setEnabled(false);
		else
			profilM1.setEnabled(true);
    }
    
    /**
     * Retourne la liste de look and feel disponible sur l'ordinateur
     * @return jm
     */
    public JMenu getJMenu() {
        JMenu jm = new JMenu("Look and Feel");
        ButtonGroup bg1 = new ButtonGroup();
        listeLAF = UIManager.getInstalledLookAndFeels();
        tJrbi = new JRadioButtonMenuItem[listeLAF.length];
        for (int i = 0; i < listeLAF.length; i++) {
            tJrbi[i] = new JRadioButtonMenuItem();
            tJrbi[i].setText(listeLAF[i].getName());
            tJrbi[i].setSelected(i == 0);
            tJrbi[i].setSelected(false);
            bg1.add(tJrbi[i]);
            tJrbi[i].addItemListener(new java.awt.event.ItemListener() {

                // Changer le Look And Feel
                public void itemStateChanged(java.awt.event.ItemEvent e) {
                    int i = 0;
                    for (i = 0; i < tJrbi.length; ++i) {
                        if (tJrbi[i] == e.getSource()) {
                            break;
                        }
                    }
                    try {
                        UIManager.setLookAndFeel(listeLAF[i].getClassName());
                        SwingUtilities.updateComponentTreeUI(vJeu);
                    } catch (Exception ex1) {
                    	ex1.printStackTrace();
                    }
                }
            });
            jm.add(tJrbi[i]);
        }
        return jm;
    }

    /**
     * Classe interne : gestion des actions du menu 
     * 
     * @author Martin Prillard
     */
    public class ActionBouton implements ActionListener {

        private int indiceActionCase;

        public ActionBouton(int pnumCase) {
            super();
            indiceActionCase = pnumCase;
        }

        public void actionPerformed(ActionEvent e) {

        	try {
	            switch (indiceActionCase) {
	                /* Quitter le logiciel */
	                case 1:
	                    System.exit(0);
	                    break;
	                /* Nouvelle partie */
	                case 2:
						new DialogVueCreationPartie(vJeu, false, false);
	                	break;
	                /* Nouvelle donne */
	                case 3:
	                		vJeu.retourneJeuCon().nouveauPlateau();
	                    	break;
	                /* Charger une partie */
	                case 4:
	                	FiltreSimple dat = new FiltreSimple("Sauvegarde Huit Américain", ".sha");
	                    JFileChooser filechooser = new JFileChooser("./src/Res/Sauvegardes");
	                    filechooser.addChoosableFileFilter(dat);
	                    if (JFileChooser.APPROVE_OPTION == filechooser.showOpenDialog(null)) {
	                    	FileInputStream fichier = new FileInputStream(filechooser.getSelectedFile());
	                    	ObjectInputStream ois = new ObjectInputStream(fichier);
	                    	vJeu.retourneJeuCon().chargerPartie((Partie) ois.readObject());
	                    }
	                	break;
	                /* Sauvegarde la partie */
	                case 5:
	                	//Gère les filtres
	            		choixFileChooser = new JFileChooser("./src/Res/Sauvegardes");
	            		choixFileChooser.addChoosableFileFilter(new FiltreSimple("Sauvegarde Huit Américain", ".sha"));
	                    //Choix du fichier destination
	                    if (JFileChooser.APPROVE_OPTION == choixFileChooser.showSaveDialog(null)) {
	                    	String destination = choixFileChooser.getSelectedFile().toString()+".sha";
		                    File fichierSave = new File(destination);
		    	            vJeu.retourneJeuCon().retournePartie().sauvegarder(fichierSave);
	                    }
	                	break;
	                /* Sauvegarde du score : txt */
	                case 6:
	                	//Gère les filtres
	            		choixFileChooser = new JFileChooser();
	            		choixFileChooser.addChoosableFileFilter(new FiltreSimple("Fichier TXT", ".txt"));
	                    //Choix du fichier destination
	                    if (JFileChooser.APPROVE_OPTION == choixFileChooser.showSaveDialog(null)) {
	                    	File nouveauFichier = new File(choixFileChooser.getSelectedFile().toString()+".txt");
	                    	vJeu.retourneJeuCon().retournePartie().enregistrerSous(nouveauFichier);
	                    }
	                	break;
	                /* Sauvegarde le score : jpeg */
	                case 7:
		                	//Gère les filtres
	                		choixFileChooser = new JFileChooser();
	                		choixFileChooser.addChoosableFileFilter(new FiltreSimple("Image JPEG", ".jpeg"));
							DialogVueStatVue dialStat = null;
							dialStat = new DialogVueStatVue(vJeu, vJeu.retourneJeuCon().retournePartie().retourneStatModel());
		                    //Choix du fichier destination
		                    if (JFileChooser.APPROVE_OPTION == choixFileChooser.showSaveDialog(null)) {
			                    	dialStat.writeJPEGImage(choixFileChooser.getSelectedFile().toString()+".jpeg");
			                    	dialStat.fadeOut();
		                    }else{
		                    	dialStat.fadeOut();
		                    }
	                    	break;
	                /* Impression */
	                case 8:
		                	new DialogVueImprimerScore(vJeu, false);
	                    	break;
	                /* Editer valeur des cartes */
	                case 9:
						new DialogVueEditValCarte(vJeu);
	                	break;
	                /* Editer profil des joueurs */
	                case 10:
						new DialogVueEditProfil(vJeu);
	                	break;
	                /* Musique par défaut */
	                case 11:
						GestionParametresAppli.getInstance().editeSonDefautMp3(sonActiver);
						actSonM1.setSelected(true);
	                	break;
	                /* Choix personnalisé de la musique */
	                case 12:
		            	File sourceImgMusique;
		            	FileFilter sonMp3 = new FiltreSimple("Musique mp3",".mp3");
		            	choixFileChooser = new JFileChooser();
		        		choixFileChooser.addChoosableFileFilter(sonMp3);
		        		
		            	if (JFileChooser.APPROVE_OPTION == choixFileChooser.showSaveDialog(null)) {
		            		try {
		            			sourceImgMusique = choixFileChooser.getSelectedFile();
								GestionParametresAppli.getInstance().editeSonMp3(sourceImgMusique, sonActiver);
							} catch (IOException e1) {
					            JOptionPane.showMessageDialog(null,
					                    "Son invalide",
					                    "Choisissez une musique mp3 valide",
					                    JOptionPane.WARNING_MESSAGE);
							}
		            	}
	            	break;
	                /* Active desactive le son */
	                case 13 :
	                	  sonActiver = !sonActiver;
		                  if (sonActiver)
		                  {
							GestionParametresAppli.getInstance().editeEtatSonMp3(true);
							actSonM2.EditImage(GestionImagesAppli.getInstance().retourneImgMenu(8));
							actSonM1.setSelected(true);
		                  }else{
							GestionParametresAppli.getInstance().editeEtatSonMp3(false);
							actSonM2.EditImage(GestionImagesAppli.getInstance().retourneImgMenu(9));
							actSonM1.setSelected(false);
		                  }
	                break;
	                /* Theme 1 */
	                case 14:
						GestionParametresAppli.getInstance().editeEtatFondEcran(0);
						vJeu.retournePlateau().EditImage(GestionImagesAppli.getInstance().retourneImgFondEcran(0));
						indiceTheme = 0;
	                	break;
	                /* Theme 2 */
	                case 15:
						GestionParametresAppli.getInstance().editeEtatFondEcran(1);
						vJeu.retournePlateau().EditImage(GestionImagesAppli.getInstance().retourneImgFondEcran(1));
						indiceTheme = 1;
	                	break;
	                /* Theme 3 */
	                case 16:
						GestionParametresAppli.getInstance().editeEtatFondEcran(2);
						vJeu.retournePlateau().EditImage(GestionImagesAppli.getInstance().retourneImgFondEcran(2));
						indiceTheme = 2;
	                	break;
	                /* Theme 4 */
	                case 17:
						GestionParametresAppli.getInstance().editeEtatFondEcran(3);
						vJeu.retournePlateau().EditImage(GestionImagesAppli.getInstance().retourneImgFondEcran(3));
						indiceTheme = 3;
	                	break;
	                /* Theme 5 */
	                case 18:
						GestionParametresAppli.getInstance().editeEtatFondEcran(4);
						vJeu.retournePlateau().EditImage(GestionImagesAppli.getInstance().retourneImgFondEcran(4));
						indiceTheme = 4;
	                	break;
	                /* Theme 2 personnalisé */    	
	                case 19:
	                	String[] formatsLecture = ImageIO.getReaderFormatNames();  
	                	filtreImage = new FileNameExtensionFilter("Images", formatsLecture);
	                	String sourceImgFond;
	                	choixFileChooser = new JFileChooser();
	                	choixFileChooser.addChoosableFileFilter(filtreImage);
	                	AccesFileChooser preview = new AccesFileChooser();
	                	choixFileChooser.setAccessory(preview);
	                	choixFileChooser.addPropertyChangeListener(preview);
	
	                	//On copie l'image perso de l'utilisateur pour ensuite l'exploiter
	                	if (JFileChooser.APPROVE_OPTION == choixFileChooser.showSaveDialog(null)) {
	                		try {
								sourceImgFond = choixFileChooser.getSelectedFile().getAbsolutePath();
								vJeu.retournePlateau().EditImageExterieur(sourceImgFond);
								GestionParametresAppli.getInstance().editeEtatFondEcran(5);
								GestionParametresAppli.getInstance().editeFondEcranPerso(sourceImgFond);
							} catch (IOException e1) {
					            JOptionPane.showMessageDialog(null,
					                    "Image invalide",
					                    "Choisissez une image valide",
					                    JOptionPane.WARNING_MESSAGE);
							}
	                	}else{
	                		switch (indiceTheme)
	                		{
	                		case 0:
	                			t1M1.setSelected(true);
	                			break;
	                		case 1:
	                			t2M1.setSelected(true);
	                			break;
	                		case 2:
	                			t3M1.setSelected(true);
	                			break;
	                		case 3:
	                			t4M1.setSelected(true);
	                			break;
	                		case 4:
	                			t5M1.setSelected(true);
	                			break;
	                		}
	                	}
		                break;
		            /* Animation crazy */
	            	case 20:
						GestionParametresAppli.getInstance().editeEtatAnimation(1);
						GestionParametresAppli.getInstance().editeCourbePlateau(true);
		            	curv.reactiver();
	            		break;
		        	/* Animation normale */
		            case 21:
						GestionParametresAppli.getInstance().editeEtatAnimation(2);
						GestionParametresAppli.getInstance().editeCourbePlateau(false);
		            	curv.reactiver();
		            	break;
			          /* Animation aucune */
			          case 22:
						GestionParametresAppli.getInstance().editeEtatAnimation(3);
						GestionParametresAppli.getInstance().editeCourbePlateau(false);
		        		curv.desactiver();
		                break;
		             /* Taille carte grande */
	                 case 23:
						GestionParametresAppli.getInstance().editeEtatTailleCarte(1);
						vJeu.changePlateau();
	                	break;
	                 /* Taille carte moyen */
	                 case 24:
						GestionParametresAppli.getInstance().editeEtatTailleCarte(2);
						vJeu.changePlateau();
	                	break;
	                /* Taille carte petit */
	                case 25:
						GestionParametresAppli.getInstance().editeEtatTailleCarte(3);
						vJeu.changePlateau();
	                	break;
	                /* Theme des cartes : theme 1 */
	                case 26:
						GestionParametresAppli.getInstance().editeEtatThemeCarte(1);
						vJeu.changePlateau();
	                	break;
	                /* Theme des cartes : theme 2 */
	                case 27:
						GestionParametresAppli.getInstance().editeEtatThemeCarte(2);
						vJeu.changePlateau();
	                	break;
		            /* Changement de vue du plateau */ 
		            case 28:
						new DialogVueChoixPlateau(vJeu);
		            	break;
		            /* Affichage des scores (stat) */
	                case 29:
	                	vJeu.retourneJeuCon().retournePartie().retourneStatModel().consultationScore();
						new DialogVueStatVue(vJeu, vJeu.retourneJeuCon().retournePartie().retourneStatModel());
	                	break;
	                /* Affichage des scores sur le plateau */
	                case 30:
		                  if (affScoreM1.isSelected())
							GestionParametresAppli.getInstance().editeInfoJoueur(true);
		                  else
							GestionParametresAppli.getInstance().editeInfoJoueur(false);
		                  break;
		            /* Active ou desactive la vue des cartes valides à jouer */
	                case 31:
		                  if (aideCarteM1.isSelected())
							GestionParametresAppli.getInstance().editeAnimationCarteAJouer(true);
		                  else
							GestionParametresAppli.getInstance().editeAnimationCarteAJouer(false);
	                	break;
	                /* Antialiasing */
	                case 32:
	                    if (aliaM1.isSelected())
							GestionParametresAppli.getInstance().editeAntialiasing(true);
	                    else
							GestionParametresAppli.getInstance().editeAntialiasing(false);
	                    break;
	                /* Double buffering software */
	                case 33:
	                    if (dbsM1.isSelected())
							GestionParametresAppli.getInstance().editeDoubleBufferingSoftware(true);
	                    else
							GestionParametresAppli.getInstance().editeDoubleBufferingSoftware(false);
	                    break;
	                /* Regles du jeu */
	                case 34:
						new DialogVueMenuAide(vJeu,1);
	                    break;
	                /*A propos de nous et de notre projet*/
	                case 35:
	    				new DialogVueMenuAide(vJeu,1,1);
	    				break;
	            }
        	} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
    }
}
