package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Stratégie composite, composé d'un filtre et d'une stratégie
* 
* @author Martin Prillard
*/
public class StrategyComposite implements Jouable, Serializable {
	private static final long serialVersionUID = 1L;
	private Filtrable strategieCouleur;
	private Jouable strategieJeu;
	
	/**
	 * Constructeur de la stratégie
	 * @param _strategieCouleur
	 * @param _strategieJeu
	 */
	public StrategyComposite(Filtrable _strategieCouleur, Jouable _strategieJeu)
	{
		strategieCouleur = _strategieCouleur;
		strategieJeu = _strategieJeu;
	}
	
	@Override
	public void jouerCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {

		if (strategieCouleur.filtrerListCarte(pm, listCarteJouable).size() != 0) //Si des cartes jouables existent après l'application du filtre de couleur
			listCarteJouable = strategieCouleur.filtrerListCarte(pm, listCarteJouable);	
		
		strategieJeu.jouerCarte(pm, listCarteJouable);
	}
	
	@Override
	public CarteModel retourneCarteJoue() {return strategieJeu.retourneCarteJoue();}
	@Override
	public int retourneCouleur() {return strategieJeu.retourneCouleur();}
	
	public Filtrable retourneFiltre(){ return strategieCouleur;}
	public Jouable retourneStrategyJeu(){ return strategieJeu;}
}
