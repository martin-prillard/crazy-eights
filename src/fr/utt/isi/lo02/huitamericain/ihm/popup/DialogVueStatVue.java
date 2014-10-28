package fr.utt.isi.lo02.huitamericain.ihm.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.util.Rotation;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BoutonImg;
import fr.utt.isi.lo02.huitamericain.ihm.composant.FiltreSimple;
import fr.utt.isi.lo02.huitamericain.partie.StatModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;

/**
* Menu graphique permettant l'affichage du score sous forme visuel
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public final class DialogVueStatVue extends AbstrDialogVue
{
	private final static int LARGEUR = 1150;
	private final static int HAUTEUR = 600;
    private StatModel statMod;
    private JLabel titre;
    private JLabel infoPartie;
    private JLabel nbPartieJouee;
    private JButton bQuitter;
    private JButton bRejouer;
    private ChartPanel chartPanelParties;
    private ChartPanel chartPanelPoints;
    private JPanel zoneVide = new JPanel();
    private JPanel panelStat = new JPanel(new BorderLayout());
    private JPanel panelBoutton = new JPanel();
    private JPanel panelTitre = new JPanel(new BorderLayout());
    private JPanel panelCentral = new JPanel(new BorderLayout());
    private JPanel panelClassement;
    
    /**
     * Constructeur, affichage du score durant une manche
     * @param _vJeu
     * @param m
     * @throws IOException
     */
    public DialogVueStatVue(JeuVue _vJeu, StatModel m) throws IOException
    {
    	super(_vJeu, LARGEUR, HAUTEUR, true);
        statMod = m;
        initComponent("Statistiques des parties");
        bQuitter.setText("Retour à la partie");
        initLayout();
        panelBoutton.setLayout(new GridLayout(1, 1, 7, 0));
        panelBoutton.add(bQuitter);
        
        bQuitter.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
            	fadeOut();
            }
        });
    }
    
    /**
     * Constructeur, affichage du score en fin de manche
     * @param _vJeu
     * @param m
     * @param z
     * @throws IOException
     */
    public DialogVueStatVue(JeuVue _vJeu, StatModel m, int z) throws IOException
    {
    	super(_vJeu, LARGEUR, HAUTEUR, true);
        statMod = m;
        initComponent("Fin de la manche");
        bQuitter.setText("Recommencer une nouvelle partie");
        initLayout();
        bRejouer.setBackground(Color.BLACK);
        bRejouer.setForeground(Color.white);
        panelBoutton.setLayout(new GridLayout(1, 2, 7, 0));
        panelBoutton.add(bRejouer);
        panelBoutton.add(bQuitter);

        bRejouer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
            	fadeOutSansTimer();
            	instanceJeu.retourneJeuCon().nouveauPlateau();
            }
        });
        
        bQuitter.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
            	fadeOutSansTimer();
            	try {
					new DialogVueCreationPartie(instanceJeu, false, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
    }
    
    /**
     * Constructeur, affichage du score en fin de partie
     * @param _vJeu
     * @param m
     * @param y
     * @param z
     * @throws IOException
     */
    public DialogVueStatVue(JeuVue _vJeu, StatModel m, int y, int z) throws IOException
    {
    	super(_vJeu, LARGEUR, HAUTEUR, true);
        statMod = m;

        initComponent("Fin de la partie");
        bQuitter.setText("Recommencer une nouvelle partie");
        initLayout();
        JButton bSauvImg = new JButton("Sauvegarder le score (.jpeg)");
        JButton bSauvTxt = new JButton("Sauvegarder le score (.txt)");
        bSauvImg.setBackground(Color.BLACK);
        bSauvImg.setForeground(Color.white);
        bSauvTxt.setBackground(Color.BLACK);
        bSauvTxt.setForeground(Color.white);

        panelBoutton.setLayout(new GridLayout(1, 3, 7, 0));
        panelBoutton.add(bSauvTxt);
        panelBoutton.add(bSauvImg);
        panelBoutton.add(bQuitter);
        

        bSauvImg.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
            	JFileChooser choixFileChooser = new JFileChooser();
            	choixFileChooser.addChoosableFileFilter(new FiltreSimple("Image JPEG", ".jpeg"));
                //Choix du fichier destination
                if (JFileChooser.APPROVE_OPTION == choixFileChooser.showSaveDialog(null)) {
                    	writeJPEGImage(choixFileChooser.getSelectedFile().toString()+".jpeg");
                }
            }
        });
        
        bSauvTxt.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
            	fadeOutSansTimer();
            	try {
            		new DialogVueImprimerScore(instanceJeu, true);
            	} catch (IOException e4) {e4.printStackTrace();}
            }
        });
        
        bQuitter.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
            	fadeOutSansTimer();
            	try {
					new DialogVueCreationPartie(instanceJeu, true, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
    }

    /**
     * Initialisation des composants
     * @param txt
     * @throws IOException
     */
    protected void initComponent(String txt) throws IOException 
    {
        titre = new JLabel(txt);
        titre.setForeground(Color.WHITE);
        titre.setFont(titre.getFont().deriveFont((float)45.0));
        nbPartieJouee = new JLabel("Manche(s) terminée(s) : "+statMod.retourneNbPartiesJouees());
        nbPartieJouee.setForeground(Color.LIGHT_GRAY);
        
        if (instanceJeu.retourneJeuCon().retournePartie().retourneVictoireParPoints())
        	infoPartie = new JLabel("Condition de fin de partie : "+instanceJeu.retourneJeuCon().retournePartie().retourneNbPourVictoire()+" points");
        else
        	infoPartie = new JLabel("Condition de fin de partie : "+instanceJeu.retourneJeuCon().retournePartie().retourneNbPourVictoire()+" manches");
        
        infoPartie.setForeground(Color.LIGHT_GRAY);
        bQuitter = new JButton();
        bRejouer = new JButton("Continuer : nouvelle donne");
        
        JFreeChart chartParties = creerChartParties("Parties gagnées");
        chartPanelParties = new ChartPanel(chartParties);
        JFreeChart chartPoints = creerChartPoints("Répartition des points");
        chartPanelPoints = new ChartPanel(chartPoints);
        //Initialisation du classement
        panelClassement = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 0, 2, 0);
        
        panelClassement.setOpaque(false);
        
        //Gère le classement des joueurs
        for (int i = 0; i < statMod.retourneTabClassement().length; i++)
        {
        	for(AbstrJoueurModel m : statMod.retourneJoueursStat()) 
        	{
        		if(statMod.retourneJoueursStat().indexOf(m) == statMod.retourneTabClassement()[i][0])
        		{
        			JPanel zone = new JPanel(new GridLayout(1,2));
        			zone.setOpaque(false);
		           	JLabel infoClassement = new JLabel("     "+m.retourneClassement());
		        	infoClassement.setFont(infoClassement.getFont().deriveFont((float)20));
		        	infoClassement.setOpaque(false);
		        	infoClassement.setForeground(Color.white);
		        	infoClassement.setMinimumSize(new Dimension(50,52));
		        	infoClassement.setPreferredSize(new Dimension(50,52));
		        	infoClassement.setMaximumSize(new Dimension(50,52));
		        	zone.add(infoClassement);
		        	BoutonImg avat = new BoutonImg(GestionImagesAppli.getInstance().retourneImgJoueur(m.retourneIdProfil()));
		        	avat.setBackground(new Color(0,0,0,0));
					avat.setOpaque(false);
					avat.setMinimumSize(new Dimension(50,52));
					avat.setPreferredSize(new Dimension(50,52));
		        	avat.setMaximumSize(new Dimension(50,52));
		        	zone.add(avat);
		        	panelClassement.add(zone, gbc);
        		}
        	}
        }
    }

    /**
     * Initialisation des composants, layout
     */
    protected void initLayout() 
    {
    	panelStat.setOpaque(false);
    	panelCentral.setOpaque(false);
    	titre.setOpaque(false);
    	nbPartieJouee.setOpaque(false);
    	panelTitre.setOpaque(false);
    	zoneVide.setOpaque(false);
    	
    	chartPanelParties.setPreferredSize(new Dimension(480,370));
    	chartPanelPoints.setPreferredSize(new Dimension(480,370));
        
        panelStat.add(chartPanelParties, BorderLayout.WEST);
        panelStat.add(chartPanelPoints,  BorderLayout.EAST);   
        

        zoneVide.setPreferredSize(new Dimension(0,20));
        panelBoutton.setPreferredSize(new Dimension(0,35));

    	panelBoutton.setOpaque(false);
        bQuitter.setBackground(Color.BLACK);
        bQuitter.setForeground(Color.white);
        
        panelTitre.add(titre, BorderLayout.CENTER);
        panelTitre.add(nbPartieJouee, BorderLayout.EAST);
        panelTitre.add(infoPartie, BorderLayout.SOUTH);
        panelCentral.add(panelTitre, BorderLayout.NORTH);
        panelCentral.add(panelStat, BorderLayout.CENTER);
        
        JPanel zoneCentre = new JPanel(new BorderLayout());
        
        zoneCentre.add(panelCentral, BorderLayout.WEST);
        zoneCentre.add(panelClassement,  BorderLayout.EAST);
        
        JPanel zoneGlobal = new JPanel(new BorderLayout());
        zoneGlobal.add(zoneCentre, BorderLayout.CENTER);
        JPanel zoneBas = new JPanel(new BorderLayout());
        zoneBas.add(zoneVide, BorderLayout.NORTH);
        zoneBas.add(panelBoutton, BorderLayout.CENTER);
        zoneBas.setOpaque(false);
        
        zoneGlobal.add(zoneBas, BorderLayout.SOUTH);
        
    	zoneCentre.setOpaque(false);
    	zoneGlobal.setOpaque(false);
    	
        dialDetail.add(zoneGlobal);
    }

    /**
     *  Création des graphiques stats à l'aide de la librairie externe :
     *  création des statistiques du nombre de parties gagnés par joueur
     */
    private JFreeChart creerChartParties(String title) 
    {
        
        JFreeChart chart = ChartFactory.createPieChart3D(
            title,  							// chart title
            statMod.retourneDatasetParties(),   // data
            true,                   			// include legend
            true,
            true
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}"+System.getProperty("line.separator")+"{1} parties"));
        plot.setNoDataMessage("Aucun score pour le moment : première manche en cours");
        return chart;
    }

    /**
     *  Création des graphiques stats à l'aide de la librairie externe :
     *  création des statistiques du nombre de points par joueur
     */
    private JFreeChart creerChartPoints(String title) {
        
        JFreeChart chart = ChartFactory.createPieChart3D(
            title,  							// chart title
            statMod.retourneDatasetPoints(),    // data
            true,                   			// include legend
            true,
            true
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}"+System.getProperty("line.separator")+"{1} points"));
        plot.setNoDataMessage("Aucun score pour le moment : première manche en cours");
        return chart;
    }
    
    /**
     * Permet l'enregistrement du score au format jpeg (imprim ecran du composant)
     * @param destination
     */
    public void writeJPEGImage(String destination) {
    	Dimension size = this.getSize();
        Color bg = Color.white;

        BufferedImage bi = new BufferedImage(size.width, size.height,BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = bi.createGraphics();
        graphics.setClip(dialDetail.getX(), dialDetail.getY(), dialDetail.getWidth(), dialDetail.getHeight());
        graphics.setColor(bg);
        graphics.fillRect(0,0, size.width, size.height);
        this.paint(graphics);
        try{
           ImageIO.write(bi,"jpeg",new File(destination));
        }catch(Exception e){e.printStackTrace();}
    }
    
}
