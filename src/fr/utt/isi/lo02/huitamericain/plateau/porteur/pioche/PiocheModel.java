package fr.utt.isi.lo02.huitamericain.plateau.porteur.pioche;

import java.io.IOException;
import java.util.Collections;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.Porteur;

/**
* Model de la pioche
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class PiocheModel extends Porteur{
	
	private int nbPaquetCarte = 1;
	private int[] tabCarteTaille = new int[]{CarteModel.AS, CarteModel.DEUX, CarteModel.TROIS, CarteModel.QUATRE, CarteModel.CINQ, CarteModel.SIX, CarteModel.SEPT, CarteModel.HUIT, CarteModel.NEUF, CarteModel.DIX, CarteModel.VALET, CarteModel.DAME, CarteModel.ROI};
	private int[] tabCarteCouleur = new int[]{CarteModel.TREFLE, CarteModel.CARREAU, CarteModel.COEUR, CarteModel.PIQUE}; 
	
	/**
	 * Constructeur de la pioche en fonction du nombre de paquet de carte qu'elle contiendra
	 * @param _nbPaquetCarte
	 */
	public PiocheModel(int _nbPaquetCarte)
	{
		nbPaquetCarte = _nbPaquetCarte;
		try {
			creationPioche();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Création de la pioche
	 * @throws IOException
	 */
	public void creationPioche() throws IOException
	{	
		//Initialisation des cartes en fonction du nombre de paquets de carte necessaire
		for (int n = 1; n <=nbPaquetCarte; n++)
			for (int taille : tabCarteTaille)
				for (int couleur : tabCarteCouleur)
					vectCartes.add(new CarteModel(taille, GestionParametresAppli.getInstance().retourneValCarte(taille), couleur));
		
		//On ajoute les joker au jeu
		vectCartes.add(new CarteModel(CarteModel.JOKER, GestionParametresAppli.getInstance().retourneValCarte(CarteModel.JOKER), CarteModel.INCOLORE));
		vectCartes.add(new CarteModel(CarteModel.JOKER, GestionParametresAppli.getInstance().retourneValCarte(CarteModel.JOKER), CarteModel.INCOLORE));
		
		//On mélange la pioche
		melangerPioche();
		
		//On test si la première carte est un Joker, si c'est le cas, on retourne la carte suivante
		while (retourneDerniereCarte().retourneTaille() == CarteModel.JOKER || retourneDerniereCarte().retourneTaille() == CarteModel.HUIT || retourneDerniereCarte().retourneTaille() == CarteModel.AS)
		{
			CarteModel carteTop =  this.retournePremiereCarte();
			this.supprimerPremiereCarte();
			this.insererCarte(carteTop);
		}
	}
	
	@Override
	public void insererCarte(CarteModel _idCarte){
		vectCartes.add(_idCarte);
		setChanged();
		notifyObservers(estNonVide());
	}
	
	@Override
	public void supprimerCarte(CarteModel _idCarte){}
	
	/**
	 * Mélange la pioche
	 */
	public void melangerPioche(){Collections.shuffle(vectCartes);}

	public void supprimerPremiereCarte(){
		vectCartes.remove(vectCartes.firstElement());
		setChanged();
		notifyObservers(estNonVide());
	}
	
	/**
	 * Teste si la pioche est vide ou non
	 * @return true si elle n'est pas vide
	 */
	public boolean estNonVide(){
		boolean visible = true;
		if (vectCartes.size() <= 1)
			visible = false; //Pioche vide
		else
			visible = true;
		return visible;
	}
}