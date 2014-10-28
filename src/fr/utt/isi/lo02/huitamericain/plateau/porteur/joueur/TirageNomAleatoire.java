package fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

/**
* Classe permettant un tirage au sort des joueur aléatoire lors de la création d'une partie
* 
* (Singleton)
* @author Martin Prillard
*/
public class TirageNomAleatoire {

	private static TirageNomAleatoire Instance = null;
	private Vector<Integer> listNombre;	
	
	/**
	 * Constructeur
	 */
	private TirageNomAleatoire(){
		listNombre = new Vector<Integer>();
		for(int i = 0; i<7; i++)
		{
			listNombre.add(i);
		}
		Collections.shuffle(listNombre);
	}
	
	/**
	 * Retourne l'id du profil du joueur
	 * @return idProfil
	 */
	public int retourneIdProfil()
	{
		int idProfil = listNombre.firstElement();
		listNombre.remove(listNombre.firstElement());
		return idProfil;
	}
	
	/**
	 * Remise à zero du tirage au sort
	 */
	public void reset()
	{
		Instance = null;
		Instance = new TirageNomAleatoire();
	}
	
	/**
	 * Singleton, retourne l'instance unique de l'objet
	 * @return Instance
	 * @throws IOException
	 */
    public static TirageNomAleatoire getInstance() throws IOException {
        if (Instance == null) {
        	Instance = new TirageNomAleatoire();
        }
        return Instance;
    }
}
