package fr.utt.isi.lo02.huitamericain.ihm.composant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import fr.utt.isi.lo02.huitamericain.partie.Partie;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurIA;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.Filtrable;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.FiltreCouleur;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.FiltreCouleurEquilibre;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.FiltreNeutre;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.Jouable;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.StrategyComposite;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.StrategyJeuAudacieux;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.StrategyJeuCarteFroussard;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.StrategyJeuCarteSournois;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.StrategyJeuChanceux;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.StrategyJeuSage;

/**
* Classe permettant la gestion des ia des joueurs
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class PanelEditionIA extends JPanel{

	private JPanel pZoneJH;
	private JPanel pZoneGlobalJIA;
	private JPanel pZoneJIA;
    private ArrayList<JSpinner> listeStrategie = new ArrayList<JSpinner>();
    private ArrayList<JSpinner> listeCouleurs = new ArrayList<JSpinner>();
	private Border bJoueurH = BorderFactory.createTitledBorder("Joueur Humain");
	private Border bJoueurIA = BorderFactory.createTitledBorder("Joueur IA");
    public final static String CHANCEUX = "Chanceux";
    public final static String FROUSSARD = "Froussard";
    public final static String AUDACIEUX = "Audacieux";
    public final static String SOURNOIS = "Sournois";
    public final static String SAGE = "Sage";
    public final static String NEUTRE = "Neutre";
    public final static String CHOIXEQUI = "Choix equilibr�";
    public final static String PREFTREFLE = "Pr�f�rence : tr�fle";
    public final static String PREFCARREAU = "Pr�f�rence : carreau";
    public final static String PREFCOEUR = "Pr�f�rence : coeur";
    public final static String PREFPIC = "Pr�f�rence : pic";
    private String[] tabStrategy = new String[]{CHANCEUX, FROUSSARD, AUDACIEUX, SOURNOIS, SAGE};
    private String[] tabCouleur = new String[]{NEUTRE, CHOIXEQUI, PREFTREFLE, PREFCARREAU, PREFCOEUR, PREFPIC};
    private JLabel info = new JLabel("<html>" +
    		"<br><font color=\"gray\"><b>-</b></font><font color=\"orange\"> Chanceux : </font><font color=\"white\">Joue une carte totalement au  hasard</font>" +
    		"<br><font color=\"gray\"><b>-</b></font><font color=\"orange\"> Froussard : </font><font color=\"white\">Se d�fausse des cartes de grande valeur</font>" +
    		"<br><font color=\"gray\"><b>-</b></font><font color=\"orange\"> Audacieux : </font><font color=\"white\">Se d�fausse des cartes sans fonction</font>" +
    		"<br><font color=\"gray\"><b>-</b></font><font color=\"orange\"> Sournois : </font><font color=\"white\">Cherche � p�naliser les autres joueurs</font>" +
    		"<br><font color=\"gray\"><b>-</b></font><font color=\"orange\"> Sage : </font><font color=\"white\">Garde des cartes de d�fense en r�serve</font>" +
    		"</html>");
	
    /**
     * Constructeur appel� lors de la cr�ation d'une partie
     */
	public PanelEditionIA(int nbJoueurIA, int nbJoueurHumain)
	{
		setLayout(new BorderLayout());
		actualiser(nbJoueurIA, nbJoueurHumain);
	}
	
    /**
     * Constructeur appel� lors de l'�dition des ia au cours du jeu
     */
	public PanelEditionIA(Partie part)
	{
		setLayout(new BorderLayout());
		int cpt = 0;
		SpinnerListModel spinModel;
		pZoneJIA = new JPanel(new SpringLayout());

	    pZoneJIA.add(new JLabel());
	    
	    JLabel texte = new JLabel("Strat�gie de jeu :");
	    texte.setForeground(Color.WHITE);
	    pZoneJIA.add(texte);
	    
	    texte = new JLabel(" Filtre par couleur favorie : ");
	    texte.setForeground(Color.WHITE);
	    pZoneJIA.add(texte);
	    
	    for (AbstrJoueurModel jIA : part.retourneListJoueur())
	    {
	    	if (jIA instanceof JoueurIA)
	    	{
	    		StrategyComposite strateg = (StrategyComposite) ((JoueurIA)jIA).retourneStrategy();
		    	JLabel infoJoueur = new JLabel(jIA.retourneNom()+" :");
		    	infoJoueur.setForeground(Color.white);
		    	pZoneJIA.add(infoJoueur);
		    	
		    	spinModel = new SpinnerListModel(tabStrategy);
		    	spinModel.setValue(strateg.retourneStrategyJeu().toString());
			    listeStrategie.add(new JSpinner(spinModel));
			    pZoneJIA.add(listeStrategie.get(cpt));
			    
		    	spinModel = new SpinnerListModel(tabCouleur);
		    	spinModel.setValue(strateg.retourneFiltre().toString());
			    listeCouleurs.add(new JSpinner(spinModel));
			    pZoneJIA.add(listeCouleurs.get(cpt));
			    cpt++;
	    	}
	    }
    	
		SpringUtilities.makeCompactGrid(pZoneJIA,
				part.retourneNbJoueurIA()+1, 3, //rows, cols
                0, 0,        //initX, initY
                5, 1);       //xPad, yPad
		
		
	    pZoneJIA.setOpaque(false);
		add(pZoneJIA, BorderLayout.CENTER);
		add(info, BorderLayout.SOUTH);
	}
	
	/**
	 * Retourne la strat�gie de l'index du joueur pass� en param�tre
	 * @param joueurIA
	 * @return strat�gie
	 */
	public Jouable retourneStrategyChoisie(int joueurIA)
	{
		Jouable strategieJeu = null;
		Filtrable strategieCouleur = null;

		//On r�cupere les infos du JSpinner listeStrategie
		String sJeu = (String) listeStrategie.get(joueurIA).getValue();
		if (sJeu.equalsIgnoreCase(tabStrategy[0])) //Chanceux
			strategieJeu = new StrategyJeuChanceux();
		else if (sJeu.equalsIgnoreCase(tabStrategy[1])) //Froussard
			strategieJeu = new StrategyJeuCarteFroussard();
		else if (sJeu.equalsIgnoreCase(tabStrategy[2])) //Audacieux
			strategieJeu = new StrategyJeuAudacieux();
		else if (sJeu.equalsIgnoreCase(tabStrategy[3])) //Sournois
			strategieJeu = new StrategyJeuCarteSournois();
		else if (sJeu.equalsIgnoreCase(tabStrategy[4])) //Sage
			strategieJeu = new StrategyJeuSage();
		
		//On r�cupere les infos du JSpinner listeCouleurs
		String sCouleur = (String) listeCouleurs.get(joueurIA).getValue();
		if(sCouleur.equalsIgnoreCase(tabCouleur[0])) //Choix neutre
			strategieCouleur = new FiltreNeutre();
		else if(sCouleur.equalsIgnoreCase(tabCouleur[1])) //Choix equilibr�
			strategieCouleur = new FiltreCouleurEquilibre();
		else if(sCouleur.equalsIgnoreCase(tabCouleur[2])) //Pr�f�rence : tr�fle
			strategieCouleur = new FiltreCouleur(CarteModel.TREFLE);
		else if(sCouleur.equalsIgnoreCase(tabCouleur[3])) //Pr�f�rence : carreau
			strategieCouleur = new FiltreCouleur(CarteModel.CARREAU);
		else if(sCouleur.equalsIgnoreCase(tabCouleur[4])) //Pr�f�rence : coeur
			strategieCouleur = new FiltreCouleur(CarteModel.COEUR);
		else if(sCouleur.equalsIgnoreCase(tabCouleur[5])) //Pr�f�rence : pic
			strategieCouleur = new FiltreCouleur(CarteModel.PIQUE);
		
		return new StrategyComposite(strategieCouleur, strategieJeu);
	}
	
	/**
	 * Retourne une liste de nom entr� par l'utilisateur pour nommer les joueurs humains
	 * @return listNomJH
	 */
	public ArrayList<String> retourneListNomJH()
	{
		ArrayList<String> listNomJH = new ArrayList<String>();
		
		for (int i = 0; i < pZoneJH.getComponentCount(); i++)
			if (pZoneJH.getComponent(i) instanceof JTextField)
				listNomJH.add(((JTextField)pZoneJH.getComponent(i)).getText());
		
		return listNomJH;
	}
	
	/**
	 * Fonction appel� lorsque l'utilisateur choisit le nombre de joueur humain/ia
	 * Actualise le composant graphique en fonction
	 * @param nbJoueurIA
	 * @param nbJoueurHumain
	 */
	public void actualiser(int nbJoueurIA, int nbJoueurHumain)
	{
		removeAll();
		pZoneJH = new JPanel(new SpringLayout());
		pZoneJH.setBorder(bJoueurH);
		JLabel texte;
		JLabel infoJoueur;
		
	    //Partie joueur Humain
		pZoneJH.add(new JLabel());
	    texte = new JLabel("Nom des joueurs humain                                  ");
	    texte.setForeground(Color.WHITE);
	    pZoneJH.add(texte);
	    
	    for (int i = 0; i < nbJoueurHumain; i++)
	    {
	    	infoJoueur = new JLabel("J-H n�"+(i+1)+" :");
	    	infoJoueur.setForeground(Color.white);
	    	pZoneJH.add(infoJoueur);
	    	
	    	JTextField nomJoueur = new JTextField(System.getProperty("user.name")+"_"+(i+1));
	    	pZoneJH.add(nomJoueur);
	    }
	    SpringUtilities.makeCompactGrid(pZoneJH,
				nbJoueurHumain+1, 2, //rows, cols
                0, 0,        //initX, initY
                5, 1);       //xPad, yPad
	    
	    
	    //Partie joueur IA
		if (nbJoueurIA > 0)
		{   
			pZoneGlobalJIA = new JPanel(new BorderLayout());
			pZoneGlobalJIA.setBorder(bJoueurIA);
			pZoneGlobalJIA.setOpaque(false);
			pZoneJIA = new JPanel(new SpringLayout());
		    pZoneJIA.add(new JLabel());
		    texte = new JLabel("Strat�gie de jeu :");
		    texte.setForeground(Color.WHITE);
		    pZoneJIA.add(texte);
		    
		    texte = new JLabel(" Filtre par couleur favorie : ");
		    texte.setForeground(Color.WHITE);
		    pZoneJIA.add(texte);
		    
		    for (int i = 0; i < nbJoueurIA; i++)
		    {
		    	infoJoueur = new JLabel("J-IA n�"+(i+1)+" :");
		    	infoJoueur.setForeground(Color.white);
		    	pZoneJIA.add(infoJoueur);
	
			    listeStrategie.add(new JSpinner(new SpinnerListModel(tabStrategy)));
			    pZoneJIA.add(listeStrategie.get(i));
			    
			    listeCouleurs.add(new JSpinner(new SpinnerListModel(tabCouleur)));
			    pZoneJIA.add(listeCouleurs.get(i));
		    }
		    
		    SpringUtilities.makeCompactGrid(pZoneJIA,
					nbJoueurIA+1, 3, //rows, cols
	                0, 0,        //initX, initY
	                5, 1);       //xPad, yPad
		    pZoneGlobalJIA.add(pZoneJIA, BorderLayout.CENTER);
		    pZoneGlobalJIA.add(info, BorderLayout.SOUTH);
		    add(pZoneGlobalJIA, BorderLayout.CENTER);
		}
		
		pZoneJH.setOpaque(false);
	    pZoneJIA.setOpaque(false);
	    add(pZoneJH, BorderLayout.NORTH);
	}
}