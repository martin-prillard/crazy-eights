package fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche;

import java.io.IOException;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVuePiocheChezJoueur;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.JoueurHumain;

/**
* Controlleur de la pioche
* 
* @author Martin Prillard
*/
public class PiocheControlleur {

	private PiocheModel pm;
	private PlateauModel platMod;
	private JeuVue jv;
	 
	/**
	 * Constructeur du controlleur
	 * @param _pm
	 * @param _platMod
	 * @param _jv
	 */
	public PiocheControlleur(PiocheModel _pm, PlateauModel _platMod, JeuVue _jv)
	{
		pm = _pm;
		platMod = _platMod;
		jv = _jv;
	}
	
	/**
	 * Méthode appelé lors du click sur la pioche
	 * @throws IOException
	 */
	public void clickSurPioche() throws IOException
	{
		//Seul le joueur humain peut clicker sur la pioche
		if (platMod.retourneJoueurCourant() instanceof JoueurHumain && !platMod.retourneFinDeManche())
		{
			//Si le joueur doit piocher une carte chez le joueur précédent
			if (platMod.retournePiocheChezJoueur())
	    	{
				System.out.println("Vous piochez chez un joueur");
				new DialogVuePiocheChezJoueur(jv, platMod);
				jv.validate();
			//sinon, c'est qu'il peut piocher dans la pioche
	    	}else if (platMod.tourJoueurHumain() && !platMod.retourneFinDeManche())
			{
	    		System.out.println("Vous piochez "+platMod.retourneNbCarteAPiocher()+" cartes dans la pioche");
	    		platMod.retourneJoueurCourant().piocherCarteMultiple(pm, platMod.retourneNbCarteAPiocher());
	    		platMod.reinitialiserNbCarteAPiocher();
				platMod.roulementJoueurCourant();
			}
		}
	}
	
	/**
	 * Retourne le plateau model
	 * @return platMod
	 */
	public PlateauModel retournePlateauModel(){return platMod;}
	
	/**
	 * Retourne la pioche model
	 * @return pm
	 */
	public PiocheModel retournePiocheModel(){ return pm;}
}
