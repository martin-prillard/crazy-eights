package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import fr.utt.isi.lo02.huitamericain.ihm.composant.Avatar;

/**
* Classe abstraite de la vue d'un joueur
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class AbstrJoueurVue extends JPanel implements Observer{

	protected Avatar avat;
	protected JPanel donne;
	protected JoueurControlleur jc;
	
	/**
	 * Constructeur
	 * @param _jc
	 */
	public AbstrJoueurVue(JoueurControlleur _jc){
		jc = _jc;
		jc.retourneAbstrJoueurModel().addObserver(this);
	}
	
	/**
	 * Pivote la vue
	 */
	public void pivoterVertical(){}
	
    /**
     * Retourne l'avatar du joueur
     * @return avat
     */
	public Avatar retourneAvatar(){return avat;}

	/**
	 * Mis a jour
	 */
	@Override
	public void update(Observable o, Object arg) {}

}
