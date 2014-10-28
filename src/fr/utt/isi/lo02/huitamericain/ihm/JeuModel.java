package fr.utt.isi.lo02.huitamericain.ihm;

import fr.utt.isi.lo02.huitamericain.partie.Partie;

/**
* Model du jeu, 
* Le jeu contient la partie en cours,
* cette derni�re est donc unique.
* 
* @author Martin Prillard
*/
public class JeuModel{

	private Partie partie;

	/**
	 * Constructeur appel� au lancement du programme
	 */
	public JeuModel(){}
	
	public void editePartie(Partie _partie){partie = _partie;}
	public Partie retournePartie(){return partie;}
}
