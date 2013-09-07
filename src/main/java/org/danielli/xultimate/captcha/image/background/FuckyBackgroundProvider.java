package org.danielli.xultimate.captcha.image.background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.BackgroundProvider;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 上下和左右颜色渐变的背景提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class FuckyBackgroundProvider implements BackgroundProvider {
	
    private Color[] leftUpColors;
    private Color[] leftDownColors;
    private Color[] rightUpColors;
    private Color[] rightDownColors;
    private float perturbationlevel;
    
    public FuckyBackgroundProvider() {
		this(new Color[] { Color.YELLOW }, new Color[] { Color.RED }, new Color[] { Color.YELLOW }, new Color[] { Color.GREEN }, 0.1f);
	}
    
    public FuckyBackgroundProvider(Color[] leftUpColors, Color[] leftDownColors, Color[] rightUpColors, Color[] rightDownColors, float perturbationlevel) {
    	this.leftUpColors = leftUpColors;
    	this.leftDownColors = leftDownColors;
    	this.rightUpColors = rightUpColors;
    	this.rightDownColors = rightDownColors;
    	this.perturbationlevel = perturbationlevel;
    }

	@Override
	public BufferedImage createBackground(int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = (Graphics2D) img.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, width, height);
        
        Color leftUpColor = leftUpColors[RandomNumberUtils.nextInt(leftUpColors.length)];
        Color leftDownColor = leftDownColors[RandomNumberUtils.nextInt(leftDownColors.length)];
        Color rightUpColor = rightUpColors[RandomNumberUtils.nextInt(rightUpColors.length)];
        Color rightDownColor = rightDownColors[RandomNumberUtils.nextInt(rightDownColors.length)];
        
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                float leftUpRatio = (1 - i / width) * (1 - j / height);
                float leftDownRatio = (1 - i / width) * (j / height);
                float rightUpRatio = (i / width) * (1 - j / height);
                float rightDownRatio = (i / width) * (j / height);

                float red = leftUpColor.getRed() / 255.0f * leftUpRatio + leftDownColor.getRed() / 255.0f  * leftDownRatio + rightUpColor.getRed() / 255.0f * rightUpRatio
                        + rightDownColor.getRed() / 255.0f * rightDownRatio;

                float green = leftUpColor.getGreen() / 255.0f * leftUpRatio + leftDownColor.getGreen() / 255.0f * leftDownRatio + rightUpColor.getGreen() / 255.0f * rightUpRatio
                        + rightDownColor.getGreen() / 255.0f * rightDownRatio;

                float blue = leftUpColor.getBlue() / 255.0f * leftUpRatio + leftDownColor.getBlue() / 255.0f * leftDownRatio + rightUpColor.getBlue() / 255.0f * rightUpRatio
                        + rightDownColor.getBlue() / 255.0f * rightDownRatio;

                if (RandomNumberUtils.nextFloat() > perturbationlevel)
                	graphics.setColor(new Color(red, green,  blue, 1.0f));
                else
                	graphics.setColor(new Color(compute(red), compute(green),  compute(blue), 1.0f));
                graphics.drawLine(i, j, i, j);
            }
        }
        
        graphics.dispose();
        return img;
	}
	
    private float compute(float f) {
        //we take the smallest range of variation
        float range = (1 - f < f) ? 1 - f : f;
        if (RandomNumberUtils.nextFloat() > 0.5f)
            return f - RandomNumberUtils.nextFloat() * range;
        else
            return f + RandomNumberUtils.nextFloat() * range;
    }

	public Color[] getLeftUpColors() {
		return leftUpColors;
	}

	public void setLeftUpColors(Color[] leftUpColors) {
		this.leftUpColors = leftUpColors;
	}

	public Color[] getLeftDownColors() {
		return leftDownColors;
	}

	public void setLeftDownColors(Color[] leftDownColors) {
		this.leftDownColors = leftDownColors;
	}

	public Color[] getRightUpColors() {
		return rightUpColors;
	}

	public void setRightUpColors(Color[] rightUpColors) {
		this.rightUpColors = rightUpColors;
	}

	public Color[] getRightDownColors() {
		return rightDownColors;
	}

	public void setRightDownColors(Color[] rightDownColors) {
		this.rightDownColors = rightDownColors;
	}

	public float getPerturbationlevel() {
		return perturbationlevel;
	}

	public void setPerturbationlevel(float perturbationlevel) {
		this.perturbationlevel = perturbationlevel;
	}
    
}
