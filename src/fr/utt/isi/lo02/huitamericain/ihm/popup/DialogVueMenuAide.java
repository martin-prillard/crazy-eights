package fr.utt.isi.lo02.huitamericain.ihm.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.ihm.composant.BoutonImg;

/**
* Fenetre de presentation du projet en cliquant sur "à propos de" et affichage des règles du jeu
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public class DialogVueMenuAide extends AbstrDialogVue{

    /* variables */
    private JPanel centre;
    private JLabel texteBas;
    private JLabel texteHaut;
    private BoutonImg img;
    
    /**
     * Constructeur, fenetre de regles du jeu
     * @param _vJeu
     * @param _temp1
     * @throws IOException
     */
    public DialogVueMenuAide(JeuVue _vJeu, int _temp1) throws IOException {
    	super(_vJeu, 910, 650, false);
    	centre = new JPanel(new BorderLayout());
    	centre.setOpaque(false);
    	texteHaut = new JLabel("Règles du jeu");
    	texteHaut.setFont(texteHaut.getFont().deriveFont((float)25.0));
    	texteHaut.setForeground(Color.WHITE);
    	texteBas = new JLabel("<html><body> <h1><strong>Règles du Huit américain</strong></h1> <p>Comme son nom l'indique, le Huit américain est  jeu est d'origine américaine. Beaucoup plus riche qu'il n'y paraît, le Huit américain est un jeu amusant et animé parce que fertile en rebondissements. Le Huit américain se joue avec deux jeux de 52 cartes, plus 2 Jokers.</p><h2><strong>But du jeu</strong></h2> <p>Se débarrasser de toutes ses cartes. Le gagnant sera donc le joueur ayant totalisé le moins de points en fin de partie.</p> <h2 ><strong>Nombre de joueurs</strong></h2> <p>Deux à huit joueurs</p> <h2 ><strong>Valeur des cartes et distribution</strong></h2> <p>La hauteur des cartes n'intervient pas dans le jeu, mais seulement lors des comptes. Les Jokers valent : 50 points, les 8 : 32 points, les As : 20 points, les figures (R, D, V) : 10 points et les autres cartes leur valeur numérique.</p> <p >Le donneur distribue une à une un minimum de huit cartes à chacun, ou plus s'il le désire (à condition, bien sûr, de distribuer le même nombre de cartes à chaque joueur). Une fois la donne terminée, il retourne la carte suivante, la « bergère », qu'il pose à côté du talon.</p> <h2 ><strong>Déroulement d'une partie</strong></h2> <p>Le voisin de gauche du donneur entame en jouant la couleur désignée par la bergère, et pose sa carte face visible sur cette dernière, pour former une pile qui va augmenter ainsi au fur et à mesure du jeu.</p> <p >Les joueurs disposent de quatre possibilités :</p> <p >1. Jouer la couleur demandée, en se débarrassant prioritairement des cartes les plus hautes.</p> <p >2. Jouer une carte de même hauteur que celle qui est posée au-dessus de la pile de défausses, ce qui a pour effet de changer la couleur demandée. Ainsi, par exemple, si on pose une Dame de cœur sur une Dame de pique, le joueur suivant devra fournir du Cœur.</p> <p >3. Jouer une carte spéciale :</p> <p >• Un 8 de n'importe quelle couleur peut être joué à tout moment, sauf derrière un As. Le joueur qui l'a posé peut alors choisir la Couleur de son choix.</p> <p >• Un Joker peut être joué à tout moment, sauf derrière un As. Le joueur suivant pioche cinq cartes au talon et passe son tour. Le joueur qui a posé le Joker peut en outre annoncer une couleur de son choix.</p>  <p >• Un 10 ne peut être posé que sur une carte de même couleur (un 10 de Cœur sur une carte Cœur) ou sur un autre 10. Le jeu repart en sens inverse.</p> <p >• Un 7 doit être posé sur une carte de sa couleur ou sur un autre 7. Le joueur suivant doit piocher une carte au hasard dans le jeu de celui qui vient de poser le 7, et passer son tour, sans défausser.</p> <p >• Un 2 doit être posé dans les mêmes conditions. Le joueur suivant pioche deux cartes au talon et passe son tour.</p> <p >• Si un joueur pose un As, on est obligé de poser un As derrière lui. A défaut, on pioche deux cartes et on passe son tour. Le jeu repart avec la couleur de l'As posé en dernier. Si on peut poser un second As, c'est le troisième joueur qui, s'il n'en possède pas, devra piocher deux cartes par As posé, c'est-à-dire quatre cartes.</p> <p >Dernière possibilité : </p> <p >Si on ne peut jouer des trois manières précédemment décrites : passer. Le joueur pioche une carte dans le talon et passe son tour sans défausser.</p> <p ><strong>Fin du coup</strong></p> <p >Au moment de jouer son avant-dernière carte, on doit annoncer « Carte » avant que celle-ci ait touché la table. Si un adversaire attentif fait remarquer à temps l'oubli de cette règle, le joueur fautif doit piocher deux cartes et passer son tour.<br /> Dès qu'un joueur a posé sa dernière carte, le jeu s'arrête et chacun compte les points qui lui restent en main.<br /> On ne peut terminer le coup avec un 8 ou un Joker.</p><h2 ><strong>Décompte des points</strong></h2><p>Les points conservés par chaque joueur pénalisent ceux-ci selon le barème suivant :<br />Joker : 50 points<br />8 : 32 points<br />As : 20 points<br />Figures (R, D, V) : 10 points<br />Les autres cartes ont leur valeur nominale.</p><h2 ><strong>Fin de la partie</strong></h2><p>Une partie de Huit américain peut se jouer au nombre de tours ou en un nombre de points défini à l'avance par les joueurs.</p> </body></html>");
		img = new BoutonImg(GestionImagesAppli.getInstance().retourneImgDialog(1));
		img.setPreferredSize(new Dimension(0, 188));
		
		//Lien vers le site internet utilisé pour récuperer les règles du jeu
		img.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
                Properties sys = System.getProperties();
                String os = sys.getProperty("os.name");
                Runtime r = Runtime.getRuntime();
                try {
                    if (os.endsWith("NT") || os.endsWith("2000") || os.endsWith("XP") || os.endsWith("7")) {
                        r.exec("cmd /c start http://www.adpoker.fr/regles-huit-americain.html");
                    } else {
                        r.exec("start http://www.adpoker.fr/regles-huit-americain.html");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
			}
		});
    	JScrollPane scroll = new JScrollPane(texteBas);
    	scroll.setPreferredSize(new Dimension(800, 450));
    	
    	JPanel zoneHaut = new JPanel(new BorderLayout());
    	zoneHaut.setOpaque(false);
    	img.setPreferredSize(new Dimension(139, 100));
    	
    	zoneHaut.add(texteHaut, BorderLayout.CENTER);
    	zoneHaut.add(img, BorderLayout.EAST);
    	
    	centre.add(zoneHaut, BorderLayout.NORTH);
    	centre.add(scroll, BorderLayout.CENTER);

		dialDetail.add(centre);
    }
    
    /**
     * Constructeur, fenetre de presentation du projet en cliquant sur "à propos de"
     * @param _vJeu
     * @param _temp1
     * @param _temp2
     * @throws IOException
     */
    public DialogVueMenuAide(JeuVue _vJeu, int _temp1, int _temp2) throws IOException {
    	super(_vJeu, 360, 500, false);
    	centre = new JPanel(new BorderLayout());
    	centre.setOpaque(false);
    	texteHaut = new JLabel("A propos de");
    	texteHaut.setFont(texteHaut.getFont().deriveFont((float)25.0));
		texteHaut.setForeground(Color.WHITE);
		texteBas = new JLabel("<html><body><hr/><p><b><font color=\"orange\"> Version du produit :</font><br/></b> PROJET LO02 : 8 Américain <b>v-1.71</b> <br/> </p><hr/><p> <b> <font color=\"gray\">Descritpion :</b></font><br/> <div align=\"justify\"> <b>8A</b> est un logiciel simulant une partie du jeu du Huit Américain.</br> Ce logiciel est programmé entièrement en langage java et fait office de projet dans le cadre de mon enseignement à l'Université Technologique de Troyes.<br/></div></p><hr/><b><font color=\"gray\"> Réalisation :</font></b> PRILLARD Martin </body></html>");
		texteBas.setForeground(Color.WHITE);
		img = new BoutonImg(GestionImagesAppli.getInstance().retourneImgDialog(0));
		img.setPreferredSize(new Dimension(250, 188));
		img.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		texteBas.setPreferredSize(new Dimension(250, 200));
    	centre.add(texteBas, BorderLayout.SOUTH);
    	texteHaut.setPreferredSize(new Dimension(0, 35));
    	centre.add(texteHaut, BorderLayout.NORTH);
    	centre.add(img, BorderLayout.CENTER);
		dialDetail.add(centre);
    } 
}
