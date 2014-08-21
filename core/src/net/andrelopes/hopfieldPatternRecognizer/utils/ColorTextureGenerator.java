package net.andrelopes.hopfieldPatternRecognizer.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

/** generates a {@link Texture} of a solid color
 *  @author dermetfan */
public abstract class ColorTextureGenerator {

	private static final Color tmp = new Color();

	public static Texture generate(Color color, int width, int height) {
		Pixmap pm = new Pixmap(width, height, Format.RGBA8888);
		pm.setColor(color);
		pm.fill();
		Texture texture = new Texture(pm);
		pm.dispose();
		return texture;
	}

	public static Texture rgba(float r, float g, float b, float a, int width, int height) {
		return generate(tmp.set(r, g, b, a), width, height);
	}

	public static Texture rgba(float r, float g, float b, float a) {
		return rgba(r, g, b, a, 1, 1);
	}

	public static Texture rgb(float r, float g, float b) {
		return rgba(r, g, b, 1);
	}

}