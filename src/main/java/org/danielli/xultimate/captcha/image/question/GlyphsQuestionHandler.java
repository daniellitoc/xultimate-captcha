package org.danielli.xultimate.captcha.image.question;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import org.danielli.xultimate.captcha.Question;
import org.danielli.xultimate.captcha.image.Glyphs;
import org.danielli.xultimate.captcha.image.decorator.GlyphsDecorator;
import org.danielli.xultimate.captcha.image.visitor.GlyphsVisitors;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.math.RandomNumberUtils;

public class GlyphsQuestionHandler extends AbstractQuestionHandler<String> {

	private Color[] colors;
	private Font[] fonts;
	
	private boolean manageFontByCharacter; 
	private boolean manageColorPerGlyph;
	private GlyphsVisitors[] glyphsVisitors;
	private GlyphsDecorator[] glyphsDecorators;
	
	private BufferedImage copyBackground(BufferedImage background) {
	  BufferedImage out = new BufferedImage(background.getWidth(), background.getHeight(), background.getType());
	  return out;
	}
	
	private Graphics2D pasteBackgroundAndSetTextColor(BufferedImage out, BufferedImage background) {
	  Graphics2D pie = (Graphics2D) out.getGraphics();
	  pie.drawImage(background, 0, 0, out.getWidth(), out.getHeight(), null);
	  pie.setColor(colors[RandomNumberUtils.nextInt(colors.length)]);
	  return pie;
	}
	
	private void customizeGraphicsRenderingHints(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	private  AttributedString setWordFont(String word) {
		AttributedString as = new AttributedString(word);
		Font font = fonts[RandomNumberUtils.nextInt(fonts.length)];
		for (int i = 0; i < word.length(); i++) {
			as.addAttribute(TextAttribute.FONT, font, i, i + 1);
			if (manageFontByCharacter) {
				font = fonts[RandomNumberUtils.nextInt(fonts.length)];
			}
		}
		return as;
	}
	
	@Override
	public BufferedImage render(Question<String> question, BufferedImage sourceBufferedImage) {
		BufferedImage out = copyBackground(sourceBufferedImage);
		Graphics2D g2 = pasteBackgroundAndSetTextColor(out, sourceBufferedImage);
		customizeGraphicsRenderingHints(g2);
		
		//build glyphs
		AttributedCharacterIterator acit = setWordFont(question.getValue()).getIterator();
		
		Glyphs glyphs = new Glyphs();
		
        for(int i = 0 ; i < acit.getEndIndex(); i++){
            Font font = (Font) acit.getAttribute(TextAttribute.FONT);
            g2.setFont(font);
            final FontRenderContext frc = g2.getFontRenderContext();
            glyphs.addGlyphVector(font.createGlyphVector(frc,new char[]{acit.current()}));
            acit.next();
        }
        
        Rectangle2D backgroundBounds = new Rectangle2D.Float(0, 0, out.getWidth(), out.getHeight());
        
        if (ArrayUtils.isNotEmpty(glyphsVisitors)) {
        	for (GlyphsVisitors visitor : glyphsVisitors) {
        		visitor.visit(glyphs, backgroundBounds);
        	}
        }
        
        for(int i = 0 ; i < glyphs.size(); i++){
            g2.drawGlyphVector(glyphs.get(i), 0, 0);
             if(manageColorPerGlyph) 
            	 g2.setColor(colors[RandomNumberUtils.nextInt(colors.length)]);
        }
        
        
        if (ArrayUtils.isNotEmpty(glyphsDecorators)) {
        	for (GlyphsDecorator decorator : glyphsDecorators) {
        		decorator.decorate(g2, glyphs, sourceBufferedImage);
        	}
        }
        g2.dispose();
        return out;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public Font[] getFonts() {
		return fonts;
	}

	public void setFonts(Font[] fonts) {
		this.fonts = fonts;
	}

	public boolean isManageFontByCharacter() {
		return manageFontByCharacter;
	}

	public void setManageFontByCharacter(boolean manageFontByCharacter) {
		this.manageFontByCharacter = manageFontByCharacter;
	}

	public boolean isManageColorPerGlyph() {
		return manageColorPerGlyph;
	}

	public void setManageColorPerGlyph(boolean manageColorPerGlyph) {
		this.manageColorPerGlyph = manageColorPerGlyph;
	}

	public GlyphsVisitors[] getGlyphsVisitors() {
		return glyphsVisitors;
	}

	public void setGlyphsVisitors(GlyphsVisitors[] glyphsVisitors) {
		this.glyphsVisitors = glyphsVisitors;
	}

	public GlyphsDecorator[] getGlyphsDecorators() {
		return glyphsDecorators;
	}

	public void setGlyphsDecorators(GlyphsDecorator[] glyphsDecorators) {
		this.glyphsDecorators = glyphsDecorators;
	}

}
