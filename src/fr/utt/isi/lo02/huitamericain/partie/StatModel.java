package fr.utt.isi.lo02.huitamericain.partie;

import java.io.Serializable;
import java.util.ArrayList;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import fr.utt.isi.lo02.huitamericain.plateau.porteur.joueur.AbstrJoueurModel;

/**
* Model des statistiques de la partie
* 
* @author Martin Prillard
*/
public class StatModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int nbPartiesJouees;
    private ArrayList<AbstrJoueurModel> listeJoueurStat;
    private PieDataset datasetParties;
    private PieDataset datasetPoints;
    private int[][] tabClassement;

    
    public StatModel(ArrayList<AbstrJoueurModel> _listeJoueurStat)
    {
        nbPartiesJouees = 0;
        listeJoueurStat = _listeJoueurStat;
        tabClassement = new int[listeJoueurStat.size()][listeJoueurStat.size()];
        
        //Initialisation nécessaire au classement
        for (AbstrJoueurModel m : listeJoueurStat)
        {
            tabClassement[listeJoueurStat.indexOf(m)][0] = listeJoueurStat.indexOf(m);
            tabClassement[listeJoueurStat.indexOf(m)][1] = m.retourneScoreTotal();
        }
    }

    /* --------------- Update --------------- */
    
    public void traitementPartieTermine() 
    {
        nbPartiesJouees++;
        
        for (AbstrJoueurModel m : listeJoueurStat)
        {
            m.traitementFinPartie();
            tabClassement[listeJoueurStat.indexOf(m)][0] = listeJoueurStat.indexOf(m);
            tabClassement[listeJoueurStat.indexOf(m)][1] = m.retourneScoreTotal();
        }
        
        //Méthode de trie pour la création d'un classement
        trieABulle();
        
        //Attribution de la valeur du classement pour chaque joueur
        for (AbstrJoueurModel m : listeJoueurStat)
			for (int i = 0; i < tabClassement.length; i++)
				if(m.retourneScoreTotal() == tabClassement[i][1])
					m.editeClassement(i+1);
        
        //Update des données
        updateDatasetParties();
        updateDatasetPoints();
    }
	
    /**
     * Méthode du trie à bulle pour réaliser le classement des joueurs
     */
    public void trieABulle()
    {
    	boolean tabEnOrdre = false;
    	int taille = tabClassement.length;
    	
        while(!tabEnOrdre)
        {
        	tabEnOrdre = true;
            for(int i=0 ; i < taille-1 ; i++)
            {
                if(tabClassement[i][1] > tabClassement[i+1][1])
                {
                	int tempTabIndJ = tabClassement[i][0];
                	int tempTabIndI = tabClassement[i][1];
                	
                    tabClassement[i][0] = tabClassement[i+1][0];
                    tabClassement[i][1] = tabClassement[i+1][1];
                    
                    tabClassement[i+1][0] = tempTabIndJ;
                    tabClassement[i+1][1] = tempTabIndI;
                    tabEnOrdre = false;
                }
            }
            taille--;
        }
    }
    
    /**
     * Retourne le tableau multi des scores triés
     * @return tabClassement
     */
    public int[][] retourneTabClassement()
    {
    	return tabClassement;
    }
    
    /**
     * Méthode appelé lors de la consultation des scores en cours de partie
     */
    public void consultationScore() 
    {
        updateDatasetParties();
        updateDatasetPoints();
    }
    
    /**
     * Update des statistiques points
     */
    private void updateDatasetPoints() 
    {
        DefaultPieDataset result = new DefaultPieDataset();
        for (AbstrJoueurModel m : listeJoueurStat)
            result.setValue(m.retourneNom(), new Integer(m.retourneScoreTotal()));
        datasetPoints = result;
    }
    
    /**
     * Update des statistiques parties
     */
    private  void updateDatasetParties() 
    {
        DefaultPieDataset result = new DefaultPieDataset();
        for (AbstrJoueurModel m : listeJoueurStat)
            result.setValue(m.retourneNom(), new Integer(m.retournePartieGagne()));
        datasetParties = result;
    }
    
    public ArrayList<AbstrJoueurModel> retourneJoueursStat() {return listeJoueurStat;}
    public PieDataset retourneDatasetPoints() {return datasetPoints;}
    public int retourneNbPartiesJouees() {return nbPartiesJouees;}
    public PieDataset retourneDatasetParties(){return datasetParties;}
}

