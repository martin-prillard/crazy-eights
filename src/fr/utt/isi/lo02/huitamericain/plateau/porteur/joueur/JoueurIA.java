package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur;

import java.io.IOException;
import java.util.ArrayList;

import fr.utt.isi.lo02.huitamericain.plateau.PlateauModel;
import fr.utt.isi.lo02.huitamericain.plateau.carte.CarteModel;
import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.comportement.Jouable;

/**
* Classe d'un joueur IA
* 
* @author Martin Prillard
*/
@SuppressWarnings("serial")
public class JoueurIA extends AbstrJoueurModel{
	
	private Jouable strategyJouable;
	
	/**
	 * Constructeur du joueur IA
	 */
	public JoueurIA()
	{
		defNom();
	}
	
	/**
	 * Attribut un nom au joueur IA
	 */
	public void defNom()
	{
		try {
			//Tirage au sort
			idProfil = TirageNomAleatoire.getInstance().retourneIdProfil();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		switch(idProfil)
		{
		case 0:
			nom = "Marc";
			break;
		case 1:
			nom = "Victor";
			break;
		case 2:
			nom = "Jérémy";
			break;
		case 3:
			nom = "Judith";
			break;
		case 4:
			nom = "Emeline";
			break;
		case 5:
			nom = "Zoé";
			break;
		case 6:
			nom = "Claire";
			break;
		}
	}
	
    /**
     * Joueur IA : Soit piocher dans la pioche, 
     * soit piocher chez un joueur, soit jouer une carte. 
     * S'applique exlusivement pour le joueur ia
     */
	public void jouer(PlateauModel pm, ArrayList<CarteModel> listCarteJouable){
    	if (pm.retournePiocheChezJoueur())
    	{
    		CarteModel cartePiocherHasard = pm.retourneJoueurCourantAncien().retourneCarteAuHasard();
    		pm.editePiocheChezJoueur(false);
    		piocherCarteChezJoueur(pm.retourneJoueurCourantAncien(), cartePiocherHasard);
    		System.out.println("Le joueur "+this.retourneNom()+" pioche une carte chez le joueur : "+pm.retourneJoueurCourantAncien().retourneNom());
    	}
    	else if (pm.retourneNbCarteAPiocher() > 1)
    	{
    		piocherCarteMultiple(pm.retourneModelPioche(), pm.retourneNbCarteAPiocher());
    		System.out.println("Le joueur "+this.retourneNom()+" pioche "+pm.retourneNbCarteAPiocher()+" cartes");
    		pm.reinitialiserNbCarteAPiocher();
    	}
    	else if(listCarteJouable.size() != 0)
    	{
    		strategyJouable.jouerCarte(pm, listCarteJouable); //On utilise les objets de façon polymorphe
    		pm.actionCarteSurPlateau(this, strategyJouable.retourneCarteJoue(), strategyJouable.retourneCouleur());
    		System.out.println("Le joueur "+this.retourneNom()+" joue la carte : "+strategyJouable.retourneCarteJoue().toString());
    	}
    	else
    	{
    		piocherCarte(pm.retourneModelPioche());
    		pm.reinitialiserNbCarteAPiocher();
    		System.out.println("Le joueur "+this.retourneNom()+" pioche une cartes car il ne peut pas faire autrement");
    	}
		pm.roulementJoueurCourant();
	}
	
    
    /**
     * Edite la stratégie d'un joueur
     * @param _strategyJouable
     */
	public void editeStrategie(Jouable _strategyJouable) {
		this.strategyJouable = _strategyJouable;
	}
	
    /**
     * Retourne la stratégie du joueur
     * @return strategyJouable
     */
    public Jouable retourneStrategy(){return strategyJouable;}
    
}


