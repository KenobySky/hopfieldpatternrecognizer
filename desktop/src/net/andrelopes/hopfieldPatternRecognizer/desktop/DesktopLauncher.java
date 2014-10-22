package net.andrelopes.hopfieldPatternRecognizer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import net.andrelopes.hopfieldPatternRecognizer.HopfieldPatternRecognizer;

/**
 * @author dermetfan
 */
public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 640;
        config.height = 480;
        config.vSyncEnabled = false;
        config.title = "Hopfield Pattern Recognizer";
        config.initialBackgroundColor = Color.WHITE;
        new LwjglApplication(new HopfieldPatternRecognizer(), config);
    }

}
