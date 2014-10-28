package fr.utt.isi.lo02.huitamericain.ihm;

import java.io.IOException;
import java.util.Observable;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionImagesAppli;
import fr.utt.isi.lo02.huitamericain.partie.Partie;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;

/**
* Controlleur du jeu, 
* g�re principalement le changement de partie et/ou plateau
* 
* @author Martin Prillard
*/
public class JeuControlleur  extends Observable{

	private JeuModel jm;
	public JeuControlleur(JeuModel _jm){jm = _jm;}
	
	/**
	 * Permet le chargement d'une partie sauvegard�e
	 * @param _partie
	 */
	public void chargerPartie(Partie _partie)
	{
		//On d�truit explicitement l'ancienne partie
		jm.editePartie(null);
		
		//On remplace la partie actuelle par la partie charg�e
		jm.editePartie(_partie);
		
		//Appel � la vue pour afficher le plateau
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Appel� lors de la cr�ation d'une nouvelle partie
	 * @param _partie
	 */
	public void nouvellePartie(Partie _partie)
	{
		//On d�truit explicitement l'ancienne partie
		jm.editePartie(null);
		jm.editePartie(_partie);
		jm.retournePartie().editePlateau(new PlateauModel(_partie));
		
		//On r�initialise les images perso des joueurs
		try {
			GestionImagesAppli.getInstance().reinitialiseImageAvatar();
		} catch (IOException e) {e.printStackTrace();}
		
		//Appel � la vue pour afficher le plateau
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Change le plateau de la partie en cours
	 */
	public void nouveauPlateau()
	{
		//On d�truit explicitement l'ancien plateau
		jm.retournePartie().reinitialise();
		jm.retournePartie().editePlateau(null);
		
		//On cr�e un nouveau plateau avec les param�tres de la partie
		jm.retournePartie().editePlateau(new PlateauModel(jm.retournePartie()));
		
		//Appel � la vue pour afficher le plateau
		setChanged();
		notifyObservers();
	}
	
	public Partie retournePartie(){return jm.retournePartie();}
	public JeuModel retourneJeuMod(){return jm;}
}
