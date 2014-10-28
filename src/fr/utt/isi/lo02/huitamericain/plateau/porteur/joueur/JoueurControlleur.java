package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur;

import java.io.IOException;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVueChoixCouleur;
import fr.utt.isi.lo02.huitamericain.ihm.popup.DialogVuePiocheChezJoueur;
import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;

/**
* Controlleur d'un joueur
* 
* @author Martin Prillard
*/
public class JoueurControlleur{

	private AbstrJoueurModel ajm;
	private PlateauModel platMod;
	private JeuVue jv;
	 
	/**
	 * Constructeur du controlleur du joueur
	 * @param _ajm
	 * @param _platMod
	 * @param _jv
	 */
	public JoueurControlleur(AbstrJoueurModel _ajm, PlateauModel _platMod, JeuVue _jv)
	{
		ajm = _ajm;
		platMod = _platMod;
		jv = _jv;
	}
	
	public void clickSurCarte(CarteModel carteJouer) throws IOException{
		//Seul le joueur humain peut clicker sur une carte
		if (ajm.estJoueurCourant() && !platMod.retourneFinDeManche())
		{
			//Si le joueur doit piocher une carte chez le joueur précédent
			if (platMod.retournePiocheChezJoueur())
	    	{
				System.out.println("Vous piochez chez un joueur");
				new DialogVuePiocheChezJoueur(jv, platMod); 
				jv.validate();
			//sinon, c'est qu'il peut jouer une carte de son choix, a condition qu'elle soit valide et qu'il n'est pas à piocher
	    	}else if (platMod.peutJouerCarte(carteJouer)){
	        	//Si le joueur joue une carte alors qu'il aurait du prévenir "Carte !", il pioche deux cartes
	    		if(((JoueurHumain)ajm).retourneDerCarte())
	    		{
	    			System.out.println("Vous piochez deux cartes car vous n'avez pas cliquer sur Carte ! avant de jouer votre avans dernière carte");
	    			ajm.piocherCarteMultiple(platMod.retourneModelPioche(), 2);
	    			platMod.roulementJoueurCourant();
	    		//Sinon, sa carte est prit en compte et est joué
	    		}else{
	    			System.out.println("Vous jouez la carte : "+carteJouer.toString());
					ArrayList<CarteModel> listCarteJouable = new ArrayList<CarteModel>(); //La liste ne contient que la carte cliqué valide
					listCarteJouable.add(carteJouer);
					ajm.jouer(platMod, listCarteJouable);
					
					//Pour le huit et le 15, le passage au joueur suivant se fera après un clic sur l'IHM
					if (carteJouer.retourneTaille() == 8 || carteJouer.retourneTaille() == 15) 
						new DialogVueChoixCouleur(jv, platMod);
					else 
						platMod.roulementJoueurCourant();
	    		}
	    	}
		}
	}
	
	/**
	 * Retourne la classe du joueur model
	 * @return ajm
	 */
	public AbstrJoueurModel retourneAbstrJoueurModel(){ return ajm;}
}
