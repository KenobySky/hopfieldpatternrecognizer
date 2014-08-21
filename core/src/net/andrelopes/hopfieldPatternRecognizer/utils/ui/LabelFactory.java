package net.andrelopes.hopfieldPatternRecognizer.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import net.andrelopes.hopfieldPatternRecognizer.Assets;

/**
 *
 * @author André Vinícius Lopes
 * @deprecated unused
 */
@Deprecated
public class LabelFactory {

    public static Label createLabel(String text, BitmapFont font, Color color) {
        return new Label(text, Assets.manager.get(Assets.uiskin, Skin.class));
    }

}
