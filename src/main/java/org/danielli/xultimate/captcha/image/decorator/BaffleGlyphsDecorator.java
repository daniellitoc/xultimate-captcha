/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package org.danielli.xultimate.captcha.image.decorator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.Glyphs;
import org.danielli.xultimate.captcha.image.GlyphsDecorator;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * <p/>
 * text decorator that paint holes on the string (erase some parts) </p> You may specify the number of holes per glyph :
 * 3 by default. You may specify the color of holes : white by default.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 * @see {http://www.parc.xerox.com/research/istl/projects/captcha/default.html}
 */
public class BaffleGlyphsDecorator implements GlyphsDecorator {

    /**
     * circleXRatio
     */
    private static double circleXRatio = 0.7;

    /**
     * circleYRatio
     */
    private static double circleYRatio = 0.5;

    /**
     * Number of holes per glyph. Default : 3
     */
    private int numberOfHolesPerGlyph = 3;

    /**
     * Colors for the holes
     */
    private Color[] holesColors = null;

    private int alphaCompositeType = AlphaComposite.SRC_OVER;


    /**
     * @param holesColor Color of the holes
     */
    public BaffleGlyphsDecorator(Integer numberOfHolesPerGlyph, Color holesColor) {
        this.numberOfHolesPerGlyph = numberOfHolesPerGlyph;
        this.holesColors = new Color[] { holesColor };
    }

    /**
     * @param numberOfHolesPerGlyph Number of holes around glyphes
     * @param holesColorGenerator   The colors for holes
     */
    public BaffleGlyphsDecorator(int numberOfHolesPerGlyph, Color[] holesColors) {

        this.numberOfHolesPerGlyph = numberOfHolesPerGlyph;
        this.holesColors = holesColors;
    }


    /**
     * @param numberOfHolesPerGlyph Number of holes around glyphes
     * @param holesColorGenerator   The colors for holes
     */
    public BaffleGlyphsDecorator(int numberOfHolesPerGlyph, Color[] holesColors, int alphaCompositeType) {
        this(numberOfHolesPerGlyph, holesColors);
        this.alphaCompositeType = alphaCompositeType;
    }




    public void decorate(Graphics2D g2, Glyphs glyphs, BufferedImage backround) {
           Color oldColor = g2.getColor();
        Composite oldComp = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(alphaCompositeType));

        for (int j = 0; j < glyphs.size(); j++) {
            g2.setColor(holesColors[RandomNumberUtils.nextInt(holesColors.length)]);

            Rectangle2D bounds = glyphs.get(j).getVisualBounds().getFrame();
            double circleMaxSize = bounds.getWidth() / 2;
            for (int i = 0; i < numberOfHolesPerGlyph; i++) {
                double circleSize = circleMaxSize * (1 + RandomNumberUtils.nextDouble()) / 2;
                double circlex = bounds.getMinX() + bounds.getWidth() * circleXRatio * RandomNumberUtils.nextDouble();
                double circley = bounds.getMinY() - bounds.getHeight() * circleYRatio * RandomNumberUtils.nextDouble();
                Ellipse2D circle = new Ellipse2D.Double(circlex, circley, circleSize, circleSize);
                g2.fill(circle);
            }
        }
        g2.setColor(oldColor);
        g2.setComposite(oldComp);
    }
}