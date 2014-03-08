package org.danielli.xultimate.captcha.image.decorator;

import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.Glyphs;
import org.danielli.xultimate.captcha.image.GlyphsDecorator;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 提供点缀功能。
 * 
 * @author mag
 * @Date 11 mars 2008
 */
public class RandomLinesGlyphsDecorator implements GlyphsDecorator {
	
     private static final double SQRT_2=Math.sqrt(2);

    /**
     * Number of holes per glyph. Default : 3
     */
    private double numberOfLinesPerGlyph = 3;

    /**
     * Colors for the holes
     */
    private Color[] linesColors = null;

    private double lineWidth;
    private double lineLength;

    private int alphaCompositeType = AlphaComposite.SRC_OVER;



    public RandomLinesGlyphsDecorator(double numberOfLinesPerGlyph, Color[] linesColors, double lineWidth, double lineLength) {
        this.numberOfLinesPerGlyph = numberOfLinesPerGlyph;
        this.linesColors = linesColors;
        this.lineWidth = lineWidth;
        this.lineLength = lineLength;
    }

    public void decorate(Graphics2D g2, Glyphs glyphs, BufferedImage background) {
        Composite originalComposite = g2.getComposite();
        Stroke originalStroke = g2.getStroke();
        Color originalColor = g2.getColor();

        g2.setComposite(AlphaComposite.getInstance(alphaCompositeType));

        for (int j = 0; j < Math.round(glyphs.size()*numberOfLinesPerGlyph); j++) {
            double length = around(lineLength,0.5) /(2*SQRT_2);
            double width = around(lineWidth,0.3);

            double startX = (background.getWidth()-lineWidth) * RandomNumberUtils.nextDouble();
            double startY = (background.getHeight()-lineWidth) * RandomNumberUtils.nextDouble();
            double curveX = startX+around(length,0.5)*nextSign();
            double curveY = startY+around(length,0.5)*nextSign();
            double endX = curveX+around(length,0.5)*nextSign();
            double endY = curveY+around(length,0.5)*nextSign();
            QuadCurve2D q = new QuadCurve2D.Double(startX,startY,curveX,curveY, endX, endY);


            g2.setColor(linesColors[RandomNumberUtils.nextInt(linesColors.length)]);
            g2.setStroke(new BasicStroke((float)width));
            g2.draw(q);
            
        }

        g2.setComposite(originalComposite);
        g2.setColor(originalColor);
        g2.setStroke(originalStroke);

    }

    private double around(double from,double precision){
        double aFrom = from*precision;
        return (2*aFrom*RandomNumberUtils.nextDouble())+from-aFrom;
    }

    private double nextSign(){
        return RandomNumberUtils.nextBoolean()?1:-1;
    }


}
