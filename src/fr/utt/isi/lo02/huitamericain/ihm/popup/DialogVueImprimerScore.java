package fr.utt.isi.lo02.huitamericain.ihm.popup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fr.utt.isi.lo02.huitamericain.ihm.JeuVue;

/**
* Menu graphique permettant l'impression du fichier texte du score
* 
* @author Martin Prillard
* @see AbstrDialogVue
*/
@SuppressWarnings("serial")
public class DialogVueImprimerScore extends AbstrDialogVue{
		private JButton bValide = new JButton("Imprimer");
	    private JButton bQuitter = new JButton("Retour");
	    private JPanel zoneBouton = new JPanel(new GridLayout(1,2,10,0));
	    private boolean retourSurStatVue = false;
    
	    /**
	     * Constructeur du menu
	     * @param _vJeu
	     * @param _retourSurStatVue
	     * @throws IOException
	     */
		public DialogVueImprimerScore(JeuVue _vJeu, boolean _retourSurStatVue) throws IOException {
			super(_vJeu, 700, 700, true);

			String listModel = "";
			
			retourSurStatVue = _retourSurStatVue;
			
			//Affichage du contenu du fichier pour avoir un aperçu avant impression
			 try {
	             FileReader fic = new FileReader(instanceJeu.retourneJeuCon().retournePartie().retourneFichierScore());
	             BufferedReader lecture = new BufferedReader(fic);
	             String ligne = null;

	             while ((ligne = lecture.readLine()) != null)
	            	 listModel += "\n"+ligne;
	            	 
	             fic.close();
	             lecture.close();   
	         } catch (IOException e) {
	             e.printStackTrace();
	         }

	     	bValide.setBackground(Color.BLACK);
	    	bValide.setForeground(Color.white);
	        bQuitter.setBackground(Color.BLACK);
	        bQuitter.setForeground(Color.white);
	        
	        //Quitter
	        bQuitter.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {
                	if (retourSurStatVue)
                	{
                		fadeOutSansTimer();
                		try {
							new DialogVueStatVue(instanceJeu, instanceJeu.retourneJeuCon().retournePartie().retourneStatModel(), 0, 0);
						} catch (IOException e1) {e1.printStackTrace();}
                	}else{
                		fadeOut();
                	}
				}
			});
	        
	        //Valide le choix de l'utilisateur
	        bValide.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						instanceJeu.retourneJeuCon().retournePartie().printTxt();
					} catch (FileNotFoundException e) {e.printStackTrace();}
                	if (!retourSurStatVue)
    					fadeOut();
				}
			});
			
	        JPanel centre = new JPanel(new BorderLayout());
	        JTextArea area = new JTextArea(listModel);
	        area.setEditable(false);
			JScrollPane pane = new JScrollPane(area);
			pane.setPreferredSize(new Dimension(500,500));
			centre.setOpaque(false);
			zoneBouton.setOpaque(false);
			
	    	zoneBouton.add(bValide);
	    	zoneBouton.add(bQuitter);
	    	centre.add(pane, BorderLayout.CENTER);
	    	centre.add(zoneBouton, BorderLayout.SOUTH);
			dialDetail.add(centre);
		}
}

