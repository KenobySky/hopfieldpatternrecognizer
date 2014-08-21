package net.andrelopes.hopfieldPatternRecognizer;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author André Vinícius Lopes
 */
public abstract class Assets {

    public static final AssetManager manager = new AssetManager();

    public static final String uiskin = "assets/uiskin.json";

    public static Skin getSkin() {
        return manager.get(uiskin);
    }

    public static void load() {
        manager.load(uiskin, Skin.class);
        manager.finishLoading();

    }

}
