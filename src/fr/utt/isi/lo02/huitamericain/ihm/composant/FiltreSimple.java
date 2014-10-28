package fr.utt.isi.lo02.huitamericain.ihm.composant;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
* Classe de filtre pour les JFileChooser
* 
* @author Martin Prillard
*/
public class FiltreSimple extends FileFilter{
	   //Description et extension acceptée par le filtre
	   private String description;
	   private String extension;
	   
	   /**
	    * Constructeur à partir de la description et de l'extension acceptée
	    * @param description
	    * @param extension
	    */
	   public FiltreSimple(String description, String extension){
	      if(description == null || extension ==null){
	         throw new NullPointerException("La description (ou extension) ne peut être null.");
	      }
	      this.description = description;
	      this.extension = extension;
	   }
	   
	   /**
	    * Accepte ou nom le fichier
	    */
	   @Override
	   public boolean accept(File file){
	      if(file.isDirectory())
	         return true; 
	      
	      String nomFichier = file.getName().toLowerCase(); 
	      return nomFichier.endsWith(extension);
	   }
	   
	   /**
	    * Retourne la description
	    */
	   public String getDescription(){ return description;}
	}