package net.andrelopes.hopfieldPatternRecognizer;

import com.badlogic.gdx.Game;
import net.andrelopes.hopfieldPatternRecognizer.screens.MainViewScreen;

/** @author dermetfan */
public class HopfieldPatternRecognizer extends Game {

	@Override
	public void create () {
		Assets.load();
		setScreen(new MainViewScreen());
	}

}
