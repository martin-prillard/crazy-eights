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
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

import javax.swing.JPanel;

/**
 * (Classe modifiée par la suite pour répondre aux besoins) L'animation de la
 * courbe se fait à l'aide d'une valeur qui est incrémenté à l'aide d'un timer
 * 
 * @author Romain Guy
 */
@SuppressWarnings("serial")
public class CourbeAnim extends JPanel {

	protected RenderingHints hints;
	protected int counter = 0;
	protected Color start;
	protected Color end;
	private javax.swing.Timer timer;
	private boolean enCours = false;

	/**
	 * Constructeur, la vitesse influencera sur le timer, et donc le
	 * rafraichissement de l'animation
	 * 
	 * @param _vitesse
	 */
	public CourbeAnim(int _vitesse) {
		int vitesse = _vitesse;
		hints = createRenderingHints();
		start = new Color(255, 255, 255, 255);
		end = new Color(255, 255, 255, 0);

		ActionListener a = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animate();
				repaint();
			}
		};
		timer = new javax.swing.Timer(vitesse, a);
		timer.start();
		enCours = true;
	}

	/**
	 * Antialiasing
	 * 
	 * @return hints
	 */
	protected RenderingHints createRenderingHints() {
		RenderingHints hints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		hints.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		return hints;
	}

	/**
	 * Incrémente le compteur, et donc modifie la coube
	 */
	public void animate() {
		counter++;
	}

	/**
	 * Permet de desactiver le timer
	 */
	public void desactiver() {
		timer.stop();
		enCours = false;
		repaint();
	}

	/**
	 * Permet de relancer le timer
	 */
	public void reactiver() {
		timer.start();
		enCours = true;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	/**
	 * Affiche la courbe Actualiser en continue avec le timer
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if (enCours) {
			Graphics2D g2 = (Graphics2D) g;

			RenderingHints oldHints = g2.getRenderingHints();
			g2.setRenderingHints(hints);

			float width = getWidth();
			float height = getHeight();

			g2.translate(0, -30);

			drawCurve(g2, 20.0f, -10.0f, 20.0f, -10.0f, width / 2.0f - 40.0f,
					10.0f, 0.0f, -5.0f, width / 2.0f + 40, 1.0f, 0.0f, 5.0f,
					50.0f, 5.0f, false);

			g2.translate(0, 30);
			g2.translate(0, height - 60);

			drawCurve(g2, 30.0f, -15.0f, 50.0f, 15.0f, width / 2.0f - 40.0f,
					1.0f, 15.0f, -25.0f, width / 2.0f, 1.0f / 2.0f, 0.0f,
					25.0f, 15.0f, 9.0f, false);

			g2.translate(0, -height + 60);

			drawCurve(g2, height - 35.0f, -5.0f, height - 50.0f, 10.0f,
					width / 2.0f - 40.0f, 1.0f, height - 35.0f, -25.0f,
					width / 2.0f, 1.0f / 2.0f, height - 20.0f, 25.0f, 25.0f,
					7.0f, true);

			g2.setRenderingHints(oldHints);
		}
	}

	/**
	 * Dessine la courbe
	 * 
	 * @param g2
	 * @param y1
	 * @param y1_offset
	 * @param y2
	 * @param y2_offset
	 * @param cx1
	 * @param cx1_offset
	 * @param cy1
	 * @param cy1_offset
	 * @param cx2
	 * @param cx2_offset
	 * @param cy2
	 * @param cy2_offset
	 * @param thickness
	 * @param speed
	 * @param invert
	 */
	protected void drawCurve(Graphics2D g2, float y1, float y1_offset,
			float y2, float y2_offset, float cx1, float cx1_offset, float cy1,
			float cy1_offset, float cx2, float cx2_offset, float cy2,
			float cy2_offset, float thickness, float speed, boolean invert) {
		float width = getWidth();

		float offset = (float) Math.sin(counter / (speed * Math.PI));

		float start_x = 0.0f;
		float start_y = offset * y1_offset + y1;
		float end_x = width;
		float end_y = offset * y2_offset + y2;

		float ctrl1_x = offset * cx1_offset + cx1;
		float ctrl1_y = offset * cy1_offset + cy1;
		float ctrl2_x = offset * cx2_offset + cx2;
		float ctrl2_y = offset * cy2_offset + cy2;

		GeneralPath thickCurve = new GeneralPath();
		thickCurve.moveTo(start_x, start_y);
		thickCurve.curveTo(ctrl1_x, ctrl1_y, ctrl2_x, ctrl2_y, end_x, end_y);
		thickCurve.lineTo(end_x, end_y + thickness);
		thickCurve.curveTo(ctrl2_x, ctrl2_y + thickness, ctrl1_x, ctrl1_y
				+ thickness, start_x, start_y + thickness);
		thickCurve.lineTo(start_x, start_y);

		Rectangle bounds = thickCurve.getBounds();
		if (!bounds.intersects(g2.getClipBounds())) {
			return;
		}

		GradientPaint painter = new GradientPaint(0, bounds.y, invert ? end
				: start, 0, bounds.y + bounds.height, invert ? start : end);

		Paint oldPainter = g2.getPaint();
		g2.setPaint(painter);
		g2.fill(thickCurve);

		g2.setPaint(oldPainter);
	}

}
