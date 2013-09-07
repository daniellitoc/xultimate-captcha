package org.danielli.xultimate.captcha.image.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImageOp;

import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.captcha.image.background.UniColorBackgroundProvider;
import org.danielli.xultimate.captcha.image.decorator.GlyphsDecorator;
import org.danielli.xultimate.captcha.image.decorator.RandomLinesGlyphsDecorator;
import org.danielli.xultimate.captcha.image.question.DeformedComposedQuestionHandler;
import org.danielli.xultimate.captcha.image.question.GlyphsQuestionHandler;
import org.danielli.xultimate.captcha.image.renderer.BufferedImageOpsRenderer;
import org.danielli.xultimate.captcha.image.visitor.GlyphsVisitors;
import org.danielli.xultimate.captcha.image.visitor.HorizontalSpaceGlyphsVisitor;
import org.danielli.xultimate.captcha.image.visitor.RotateGlyphsRandomVisitor;
import org.danielli.xultimate.captcha.image.visitor.ShearGlyphsRandomVisitor;
import org.danielli.xultimate.captcha.image.visitor.TranslateAllToRandomPointVisitor;
import org.danielli.xultimate.captcha.image.visitor.TranslateGlyphsVerticalRandomVisitor;
import org.springframework.beans.factory.FactoryBean;

import com.jhlabs.image.SwimFilter;
import com.jhlabs.math.ImageFunction2D;

public class HotmailQuestionHandlerFactoryBean implements FactoryBean<DeformedComposedQuestionHandler<String>> {

	@Override
	public DeformedComposedQuestionHandler<String> getObject() throws Exception {
		GlyphsQuestionHandler textQuestionHandler = new GlyphsQuestionHandler();
		textQuestionHandler.setColors(new Color[] { new Color(0, 0, 80) });
		textQuestionHandler.setFonts(new Font[] { new Font("Caslon",Font.BOLD, 30) } );
		textQuestionHandler.setManageFontByCharacter(false);
		textQuestionHandler.setManageColorPerGlyph(false);
		textQuestionHandler.setGlyphsVisitors(new GlyphsVisitors[] { new TranslateGlyphsVerticalRandomVisitor(5), new RotateGlyphsRandomVisitor(Math.PI/32), new ShearGlyphsRandomVisitor(0.2,0.2),  new HorizontalSpaceGlyphsVisitor(4), new TranslateAllToRandomPointVisitor() });
		textQuestionHandler.setGlyphsDecorators(new GlyphsDecorator[]{ new RandomLinesGlyphsDecorator(1.2,new Color[] { new Color(0, 0, 80) },2,25), new RandomLinesGlyphsDecorator(1,new Color[] { new Color(238, 238,238) },1,25) });
                
		UniColorBackgroundProvider backgroundProvider = new UniColorBackgroundProvider(new Color[] { new Color(238, 238,238) } );
        textQuestionHandler.setBackgroundProvider(backgroundProvider);
        textQuestionHandler.setWidth(218);
        textQuestionHandler.setHeight(48);
        
        DeformedComposedQuestionHandler<String> finalQuestionHandler = new DeformedComposedQuestionHandler<>();
        finalQuestionHandler.setQuestionHandler(textQuestionHandler);
        
        // 好像是线程不安全的，不知道有没有影响。
        SwimFilter swim= new SwimFilter();
        swim.setScale(30);
        swim.setStretch(1);
        swim.setTurbulence(1);
        swim.setAmount(2);
        swim.setTime(0);
        swim.setEdgeAction(ImageFunction2D.CLAMP);
        
        BufferedImageRenderer imageRenderer = new BufferedImageOpsRenderer(new BufferedImageOp[] { swim });
		
        finalQuestionHandler.setBufferedImageRenderers(new BufferedImageRenderer[] { imageRenderer });

        return finalQuestionHandler;
	}

	@Override
	public Class<?> getObjectType() {
		
		return UniColorBackgroundProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
