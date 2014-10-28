package fr.utt.isi.lo02.huitamericain.partie;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurIA;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.TirageNomAleatoire;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.Jouable;

/**
* Model d'une partie de jeu
* 
* @author Martin Prillard
*/
public class Partie implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<AbstrJoueurModel> listJoueurs;
	private ArrayList<Jouable> listNiveauIA;
	private ArrayList<String> listNomJH;
	private int nbJoueur;
	private int nbJoueurHumain;
	private int nbJoueurIA;
	private int nbCarteParJoueur;
	private int nbPioche = 1;
	private StatModel statPartie;
	private File fichierScore;
	private PlateauModel platMod;
	private int nbPourVictoire;
	private boolean victoireParPoints;
	
	/**
	 * Constructeur d'une partie
	 * @param _nbJoueurHumain
	 * @param _nbJoueurIA
	 * @param _nbCarteParJoueur
	 * @param _nbPourVictoire
	 * @param _victoireParPoints
	 * @param _listNiveauIA
	 * @param _listNomJH
	 * @throws IOException
	 */
	public Partie(int _nbJoueurHumain, int _nbJoueurIA, int _nbCarteParJoueur, int _nbPourVictoire, boolean _victoireParPoints, ArrayList<Jouable> _listNiveauIA, ArrayList<String> _listNomJH) throws IOException
	{
		//Distribution aleatoire des joueurs sur le plateau
		try {
			TirageNomAleatoire.getInstance().reset();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	nbCarteParJoueur = _nbCarteParJoueur;
		listNiveauIA = _listNiveauIA;
		nbJoueurHumain = _nbJoueurHumain;
		nbJoueurIA = _nbJoueurIA;
		listNomJH = _listNomJH;
		nbJoueur = nbJoueurHumain+nbJoueurIA;
		nbPourVictoire = _nbPourVictoire;
		victoireParPoints = _victoireParPoints;
		listJoueurs = new ArrayList<AbstrJoueurModel>();
		nbPioche = determineNbPaquetCarte();
		
		//Initialisations
		initJoueurs();
		initialiseFichierScore();
	}
	
	/**
	 * Initialisation des joueurs,
	 * en fonction des choix de l'utilisateur
	 */
	public void initJoueurs()
	{
		for(int i = 0; i < nbJoueurHumain; i++)
			listJoueurs.add(new JoueurHumain(listNomJH.get(i), 7+i));

		for (int i = 0; i <nbJoueurIA; i++) //A mettre le nombre de joueur humain
		{
			JoueurIA jia = new JoueurIA();
	        listJoueurs.add(jia);
	        jia.editeStrategie(listNiveauIA.get(i));
		}
		statPartie = new StatModel(listJoueurs);
	}
	
	/**
	 * Reinitialisation entre chaque manche
	 * remise à zero des carte des joueurs et de la pioche
	 */
    public void reinitialise()
    {
    	try {
    		for (AbstrJoueurModel iJoueur : listJoueurs)
    			iJoueur.reinitialise();
            	platMod.initialisationPioche();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Permet de changer le plateau model en cours
     * @param _nouveauPlateau
     */
	public void editePlateau(PlateauModel _nouveauPlateau){platMod = _nouveauPlateau;}
	
	/**
	 * Retourne le plateau en cours
	 * @return platMod
	 */
	public PlateauModel retournePlateauMod(){return platMod;}
	
	/**
	 * Créer un nouveau fichier temporaire contenant des informations sur la partie
	 */
	public void initialiseFichierScore(){
        try {
    		fichierScore = File.createTempFile("_temp",".dat");
    		fichierScore.deleteOnExit();
        	BufferedWriter bw = new BufferedWriter(new FileWriter(fichierScore));
            GregorianCalendar b = new GregorianCalendar();
            int heure = b.get(Calendar.HOUR);
            int min = b.get(Calendar.MINUTE);
            int sec = b.get(Calendar.SECOND);
            bw.write("********************");
            bw.write(System.getProperty("line.separator"));
            bw.write("*   CRAZY EIGHTS   *");
            bw.write(System.getProperty("line.separator"));
            bw.write("********************");
            bw.write(System.getProperty("line.separator"));
            bw.write("*");
            bw.write(System.getProperty("line.separator"));
            bw.write("*	"+heure + "h" + min + "m" + sec + "s :");
            bw.write(System.getProperty("line.separator"));
            bw.write("*	NOUVELLE PARTIE");
            bw.write(System.getProperty("line.separator"));
            if (victoireParPoints)
            	bw.write("*	En : "+nbPourVictoire+" points");
            else
            	bw.write("*	En : "+nbPourVictoire+" manches");
            bw.write(System.getProperty("line.separator"));
            bw.write("*");
            bw.write(System.getProperty("line.separator"));
            bw.write("********************");
            bw.write(System.getProperty("line.separator"));
            bw.write("Liste des joueurs : ");
            bw.write(System.getProperty("line.separator"));
            for(AbstrJoueurModel iJoueur : listJoueurs)
            {
            	bw.write("	*"+iJoueur.retourneNom());
                bw.write(System.getProperty("line.separator"));
            }
            bw.write("********************");
            bw.write(System.getProperty("line.separator"));
            bw.write(System.getProperty("line.separator"));
            bw.write(System.getProperty("line.separator"));
            bw.flush();
            bw.close();
        } catch (IOException ex) {ex.printStackTrace();}
	}
	
	/**
	 * Après chaque manche, le fichier de score est réactualisé
	 */
	public void ecrireFichierScore() {
        try {
        	BufferedWriter bw = new BufferedWriter(new FileWriter(fichierScore, true));
            GregorianCalendar b = new GregorianCalendar();
            int heure = b.get(Calendar.HOUR);
            int min = b.get(Calendar.MINUTE);
            int sec = b.get(Calendar.SECOND);
            bw.write(heure + "h" + min + "m" + sec + "s :");
            bw.write(System.getProperty("line.separator"));
            bw.write("Manche n° " + statPartie.retourneNbPartiesJouees());
            bw.write(System.getProperty("line.separator"));
            for(AbstrJoueurModel iJoueur : listJoueurs)
            {
            	bw.write("	"+iJoueur.retourneNom()+" : ");
            	bw.write(System.getProperty("line.separator"));
            	bw.write("		Classement           :  "+iJoueur.retourneClassement());
            	bw.write(System.getProperty("line.separator"));
            	bw.write("		Partie(s) gagnée(s)  :  "+iJoueur.retournePartieGagne());
            	bw.write(System.getProperty("line.separator"));
            	bw.write("		Score total          :  "+iJoueur.retourneScoreTotal());
            	bw.write(System.getProperty("line.separator"));
            }
            bw.write(System.getProperty("line.separator"));
            bw.write(System.getProperty("line.separator"));
            bw.flush();
            bw.close();
        } catch (IOException ex) {ex.printStackTrace();}
	}
	
	/**
	 * Enregistre le fichier de score
	 * @param fichierDestination
	 */
	 public void enregistrerSous(File fichierDestination)
	    {
	        String line = "";
	        try {
	        	BufferedReader br = new BufferedReader(new FileReader(fichierScore));
	        	BufferedWriter bw = new BufferedWriter(new FileWriter(fichierDestination));
	            while ((line = br.readLine()) != null) {
	                bw.write(line + System.getProperty("line.separator"));
	                bw.flush();
	            }
	            br.close();
	            bw.close();
	        } catch (IOException ex) {ex.printStackTrace();}
	    }
    
	
	public boolean retourneVictoireParPoints(){return victoireParPoints;}
	public int retourneNbPourVictoire(){return nbPourVictoire;}
	public File retourneFichierScore(){ return fichierScore;}
    public int determineNbPaquetCarte(){return ((int)(nbJoueur*nbCarteParJoueur)/52)+1;}
    public ArrayList<AbstrJoueurModel> retourneListJoueur(){return listJoueurs;}
    public int retourneNbJoueur(){return nbJoueur;} //
    public int retourneNbPioche(){return nbPioche;}
    public int retourneNbJoueurIA(){return nbJoueurIA;}
    public int retourneNbCarteParJoueur(){return nbCarteParJoueur;}
    public StatModel retourneStatModel() { return statPartie;}
    
   /**
    * Impression du fichier de score
    * @throws FileNotFoundException
    */
    public void printTxt() throws FileNotFoundException {
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        // lister les imprimantes qui supportent ce flavor
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        // choix de l'imprimante
        PrintService service = ServiceUI.printDialog(null, 200, 200, printService, defaultService, flavor, pras);
        if (service != null) {
            DocPrintJob job = service.createPrintJob();
            FileInputStream fis = new FileInputStream(fichierScore);
            DocAttributeSet das = new HashDocAttributeSet();
            Doc doc = new SimpleDoc(fis, flavor, das);
            try {
                //lancement de l'impression
                job.print(doc, pras);
            } catch (PrintException ex) {ex.printStackTrace();}
        }
    }
    
    
    /**
     * Sauvegarde de la partie en cours dans un fichier
     */
	public void sauvegarder(File fichierSave)
	{
		try {
			ObjectOutputStream oos=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichierSave)));
			oos.writeObject(this);
			oos.close();
		} catch (Exception e){e.printStackTrace();}
	}
}
