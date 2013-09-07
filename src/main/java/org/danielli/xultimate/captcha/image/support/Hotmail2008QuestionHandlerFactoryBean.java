package org.danielli.xultimate.captcha.image.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImageOp;

import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.captcha.image.background.UniColorBackgroundProvider;
import org.danielli.xultimate.captcha.image.question.DeformedComposedQuestionHandler;
import org.danielli.xultimate.captcha.image.question.GlyphsQuestionHandler;
import org.danielli.xultimate.captcha.image.renderer.BufferedImageOpsRenderer;
import org.danielli.xultimate.captcha.image.visitor.GlyphsVisitors;
import org.danielli.xultimate.captcha.image.visitor.OverlapGlyphsUsingShapeVisitor;
import org.danielli.xultimate.captcha.image.visitor.TranslateAllToRandomPointVisitor;
import org.springframework.beans.factory.FactoryBean;

import com.jhlabs.image.SwimFilter;
import com.jhlabs.math.ImageFunction2D;

public class Hotmail2008QuestionHandlerFactoryBean implements FactoryBean<DeformedComposedQuestionHandler<String>> {

	@Override
	public DeformedComposedQuestionHandler<String> getObject() throws Exception {
		GlyphsQuestionHandler textQuestionHandler = new GlyphsQuestionHandler();
		textQuestionHandler.setColors(new Color[] { new Color(0, 0, 80) });
		textQuestionHandler.setFonts(new Font[] { new Font("Caslon",Font.BOLD, 30) } );
		textQuestionHandler.setManageFontByCharacter(false);
		textQuestionHandler.setManageColorPerGlyph(false);
		textQuestionHandler.setGlyphsVisitors(new GlyphsVisitors[] { new OverlapGlyphsUsingShapeVisitor(3), new TranslateAllToRandomPointVisitor(20, 20) });
		textQuestionHandler.setGlyphsDecorators(null);
                
        UniColorBackgroundProvider textBackground = new UniColorBackgroundProvider(new Color[] { new Color(238, 238,238) } );
        textQuestionHandler.setBackgroundProvider(textBackground);
        textQuestionHandler.setWidth(218);
        textQuestionHandler.setHeight(48);
        
        DeformedComposedQuestionHandler<String> finalQuestionHandler = new DeformedComposedQuestionHandler<>();
        finalQuestionHandler.setQuestionHandler(textQuestionHandler);
        
        // 好像是线程不安全的，不知道有没有影响。
        SwimFilter swim= new SwimFilter();
        swim.setScale(30);
        swim.setAmount(10);
        swim.setEdgeAction(ImageFunction2D.CLAMP);

        SwimFilter swim2= new SwimFilter();
        swim2.setScale(30);
        swim2.setAmount(10);
        swim2.setTime(90);
        swim2.setEdgeAction(ImageFunction2D.CLAMP);
        
        BufferedImageRenderer imageRenderer = new BufferedImageOpsRenderer(new BufferedImageOp[] { swim, swim2 });
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
