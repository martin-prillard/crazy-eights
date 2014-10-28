package fr.utt.isi.lo02.huitamericain.main;

import java.io.IOException;

import fr.utt.isi.lo02.huitamericain.ihm.FenetreIntroduction;

/**
* Execution du programme 8A
* 
* @author Martin Prillard
*/
public class Main {
	
	public static void main(String[] args)
	{
		try {
			new FenetreIntroduction();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
