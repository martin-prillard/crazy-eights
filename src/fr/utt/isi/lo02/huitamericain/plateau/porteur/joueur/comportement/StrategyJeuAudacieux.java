package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement;

import java.io.Serializable;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.ihm.composant.PanelEditionIA;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Stratégie audacieux
* Se défausse des cartes sans fonctions
* 
* @author Martin Prillard
*/
public class StrategyJeuAudacieux implements Jouable, Serializable {
	private static final long serialVersionUID = 1L;
	private CarteModel carteJoue;
	private int couleur = -1;
	
	@Override
	public void jouerCarte(PlateauModel pm, ArrayList<CarteModel> listCarteJouable) {
		CarteModel carteMeilleur = listCarteJouable.get((int)(Math.random() * listCarteJouable.size())); //Par defaut, on prend une carte au hasard
		boolean carteTrouve = false;
		
		for (CarteModel carte : listCarteJouable)
		{
			//Premier passage, on prend une carte neutre
			if (!carteTrouve && carte.retourneTaille() != CarteModel.DEUX && carte.retourneTaille() != CarteModel.SEPT && carte.retourneTaille() != CarteModel.HUIT && carte.retourneTaille() != CarteModel.DIX && carte.retourneTaille() != CarteModel.JOKER)
			{
				carteMeilleur = carte;
				carteTrouve = true;
			}
			//Deuxième passage, on prend un 10
			else if (!carteTrouve && carte.retourneTaille() != CarteModel.DEUX && carte.retourneTaille() != CarteModel.SEPT && carte.retourneTaille() != CarteModel.HUIT && carte.retourneTaille() != CarteModel.JOKER)
			{
				carteMeilleur = carte;
				carteTrouve = true;
			}
			//Deuxième passage, on prend un 2
			else if (!carteTrouve && carte.retourneTaille() != CarteModel.SEPT && carte.retourneTaille() != CarteModel.HUIT && carte.retourneTaille() != CarteModel.JOKER)
			{
				carteMeilleur = carte;
				carteTrouve = true;
			}
			//troisième passage, on prend un 8
			else if (!carteTrouve && carte.retourneTaille() != CarteModel.SEPT && carte.retourneTaille() != CarteModel.JOKER)
			{
				carteMeilleur = carte;
				carteTrouve = true;
			}
			//Quatrième passage, on prend un 15
			else if (!carteTrouve && carte.retourneTaille() != CarteModel.SEPT)
			{
				carteMeilleur = carte;
				carteTrouve = true;
			}
			//Cinquième passage, on prend un 7
			else if (!carteTrouve)
			{
				carteMeilleur = carte;
				carteTrouve = true;
			}
		}
		
		
		if (carteMeilleur.retourneTaille() == CarteModel.HUIT || carteMeilleur.retourneTaille() == CarteModel.JOKER)
			couleur = (int)(Math.random() * 3);
		else
			couleur = carteMeilleur.retourneCouleur();
		
		carteJoue = carteMeilleur;
	}

	@Override
	public CarteModel retourneCarteJoue() {return carteJoue;}
	@Override
	public int retourneCouleur() {return couleur;}
	@Override
	public String toString(){return PanelEditionIA.AUDACIEUX;};
}
