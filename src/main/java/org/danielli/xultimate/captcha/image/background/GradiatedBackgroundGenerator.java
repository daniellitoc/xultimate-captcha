package org.danielli.xultimate.captcha.image.background;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.BackgroundGenerator;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 颜色渐变的背景提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class GradiatedBackgroundGenerator implements BackgroundGenerator {
	
	protected Color[] firstColors;
	protected Color[] secondColors;
    
    public GradiatedBackgroundGenerator() {
        this(new Color[] { Color.BLACK },  new Color[] { Color.WHITE });
    }
    
    public GradiatedBackgroundGenerator(Color[] firstColors, Color[] secondColors) {
        this.firstColors = firstColors;
        this.secondColors = secondColors;
    }

	@Override
	public BufferedImage createBackground(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = (Graphics2D) img.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint ytow = new GradientPaint(0, height, firstColors[RandomNumberUtils.nextInt(firstColors.length)], width, 0, secondColors[RandomNumberUtils.nextInt(secondColors.length)]);
        graphics.setPaint(ytow);
        graphics.fillRect(0, 0, width, height);
        
        graphics.dispose();
        return img;
	}

}
