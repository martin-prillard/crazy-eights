package fr.utt.isi.lo02.huitamericain.ihm.audio;

import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
* Gestion de la lecture d'un fichier mp3 par un thread dédié
* 
* @author Yoann Laissus
*/
public class GestionSonMP3Appli extends Thread
{
    private boolean lecture = false;
    private URL urlSon;
    
    /**
     * Constructeur
     * @param _fileInputStream
     */
	public GestionSonMP3Appli(URL _fileInputStream) 
    {
		urlSon = _fileInputStream;
    }

        @Override
        public void run()
        {
            lecture = true;
            try {
            	AudioInputStream din = null;
                while(lecture)
                {

                	AudioInputStream in= AudioSystem.getAudioInputStream(urlSon);
                    
                	din = null;
                    AudioFormat baseFormat = in.getFormat();
                    AudioFormat decodedFormat =
                        new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                baseFormat.getSampleRate(),
                                16,
                                baseFormat.getChannels(),
                                baseFormat.getChannels() * 2,
                                baseFormat.getSampleRate(),
                                false);
                    din = AudioSystem.getAudioInputStream(decodedFormat, in);
            
                    byte[] data = new byte[4096];
                    SourceDataLine res = null;
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
                    res = (SourceDataLine) AudioSystem.getLine(info);
                    res.open(decodedFormat);
                    SourceDataLine line = res;
                    if (line != null)
                    {
                      // Start
                      line.start();
                      int nBytesRead = 0; 
                      @SuppressWarnings("unused")
                      int nBytesWritten = 0;
                      while (nBytesRead != -1 && lecture)
                      {
                          nBytesRead = din.read(data, 0, data.length);
                          if (nBytesRead != -1)
                              nBytesWritten = line.write(data, 0, nBytesRead);
                      }
                      // Stop
                      line.drain();
                      line.stop();
                     }
                }
                din.close();
            } 
            catch (Exception ex) 
            {
            	ex.printStackTrace();
                System.out.println("Lecture de sons impossible avec cette version du JDK");
            }
        }

        /**
         * Arrete la lecture de la musique
         */
        public void arret()
        {
            lecture = false;
        }

        /**
         * Retourne l'état de la lecture
         * @return lecture
         */
        public boolean etat()
        {
            return lecture;
        }
        
        /**
         * Retourne le fichier lu
         * @return urlSon
         */
        public URL retourneUrlSon()
        {
            return urlSon;
        }
}
