package fr.utt.isi.lo02.huitamericain.ihm.composant;

/*
 * Copyright (c) 2007, Romain Guy
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the TimingFramework project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.isi.lo02.huitamericain.ihm.chargement.GestionParametresAppli;

/**
 * (Classe modifie par la suite pour répondre aux besoins)
 * Affiche une image dans un JPanel avec sa réfélection partiel,
 * Ajout d'une courbe animée possible
 * 
 * @author Romain Guy
 */
@SuppressWarnings("serial")
public class PanelReflection extends JPanel{

    private BufferedImage image = null;
    private CourbeAnim curves;
    private JLabel info = new JLabel();
    
    /**
     * Constructeur PanelReflection
     * (image et texte)
     * @param img
     * @param txt
     * @throws IOException
     */
    public PanelReflection(Image img, String txt) throws IOException {
    	super();
        setLayout(new BorderLayout());
        info.setText("  "+txt);
        info.setForeground(GestionParametresAppli.getInstance().retourneCouleurTexteCarte());
        info.setFont(info.getFont().deriveFont((float)20.0));
        add(info, BorderLayout.NORTH);
        info.setOpaque(false);
        image = toBufferedImage(img);
        image = createReflection(image);
        setOpaque(false);
    }
    
    /**
     * Constructeur PanelReflection
     * (image)
     * @param img
     */
    public PanelReflection(Image img) {
    	super();
        image = toBufferedImage(img);
        image = createReflection(image);
        setOpaque(false);
    }
    
    /*
     * Affiche du texte sur l'image
     */
    public void editeTexteInfo(String txt)
    {
    	info.setText("  "+txt);
    }
    
	BufferedImage toBufferedImage(Image image) {
        /** On test si l'image n'est pas déja une instance de BufferedImage */
        if( image instanceof BufferedImage ) {
                return( (BufferedImage)image );
        } else {
                /** On s'assure que l'image est complètement chargée */
                image = new ImageIcon(image).getImage();
                
                /** On crée la nouvelle image */
                BufferedImage bufferedImage = new BufferedImage(
                            image.getWidth(null),
                            image.getHeight(null),
                            BufferedImage.TYPE_INT_RGB );
                
                Graphics g = bufferedImage.createGraphics();
                g.drawImage(image,0,0,null);
                g.dispose();
                
                return( bufferedImage );
        } 
	}
    public CourbeAnim retourneCourbe()
    {
    	return curves;
    }

    private BufferedImage createReflection(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();

        BufferedImage result = new BufferedImage(width, height * 2,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = result.createGraphics();

        // Paints original image
        g2.drawImage(image, 0, 0, null);

        // Paints mirrored image
        g2.scale(1.0, -1.0);
        g2.drawImage(image, 0, -height - height, null);
        g2.scale(1.0, -1.0);

        // Move to the origin of the clone
        g2.translate(0, height);

        // Creates the alpha mask
        GradientPaint mask;
        mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f),0, height / 2, new Color(1.0f, 1.0f, 1.0f, 0.0f));
        g2.setPaint(mask);

        // Sets the alpha composite
        g2.setComposite(AlphaComposite.DstIn);

        // Paints the mask
        g2.fillRect(0, 0, width, height);

        g2.dispose();
        return result;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    }
}

