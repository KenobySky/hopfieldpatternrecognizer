package net.andrelopes.hopfieldPatternRecognizer.utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import net.andrelopes.hopfieldPatternRecognizer.Assets;

/**
 *
 * @author André Vinícius Lopes
 * @deprecated unused
 */
@Deprecated
public class CheckBoxFactory {

    public static CheckBox createCheckBox(String text) {
        return new CheckBox(" " + text, Assets.getSkin());
    }

}
