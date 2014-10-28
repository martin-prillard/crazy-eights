package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur;

import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Classe d'un joueur humain
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class JoueurHumain extends AbstrJoueurModel{
	
    protected boolean derCarte = false;
    
    /**
     * Constructeur du joueur humain
     * @param _nom
     * @param _idProfil
     */
	public JoueurHumain(String _nom, int _idProfil){
		nom = _nom;
		idProfil = _idProfil;
	}
	
	/**
	 * Méthode jouer
	 */
	@Override
	public void jouer(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		int couleur = listCarteJouable.get(0).retourneCouleur();
	    pm.actionCarteSurPlateau(this, listCarteJouable.get(0), couleur);
	}
	
	/**
	 * Edite "Cartes !" ou non si il ne reste que deux cartes au joueur
	 * @param b
	 */
   	public void editeDerCarte(boolean b)
   	{
   		derCarte = b;
   		setChanged();
   		notifyObservers(b);
   	}
   	
   	/**
   	 * Réinitialise le joueur entre deux manches
   	 */
   	@Override
    public void reinitialise() 
    {
    	masqueCarte(true);
    	derCarte = false;
        vectCartes.removeAllElements();
        joueurCourant = false;
    }

   	/**
   	 * Retourne si il reste deux cartes au joueur ou non
   	 * @return derCarte
   	 */
   	public boolean retourneDerCarte(){ return derCarte;}
}
