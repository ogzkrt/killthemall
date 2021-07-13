package com.javakaian.shooter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameUtils {

	private GameUtils() {

	}

	/**
	 * Generates BitmapFont object with specified size and color parameters.
	 * 
	 * @param size  Size of the desired font
	 * @param color Color of the desired font.
	 **/
	public static BitmapFont generateBitmapFont(int size, Color color) {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.flip = true;
		parameter.size = size;
		parameter.color = color;
		parameter.magFilter = TextureFilter.Linear;
		parameter.minFilter = TextureFilter.Linear;
		return generator.generateFont(parameter);
	}

	/**
	 * Renders the given string to the center of the screen. (Horizontally center
	 * not vertically)
	 * 
	 * @param text String to be rendered.
	 * @param sb   SpriteBatch object
	 * @param font BitmapFont object
	 */
	public static void renderCenter(String text, SpriteBatch sb, BitmapFont font) {

		GlyphLayout gl = new GlyphLayout(font, text);
		font.draw(sb, text, GameConstants.SCREEN_WIDTH / 2.0f - gl.width / 2.0f,
				GameConstants.SCREEN_HEIGHT * 0.3f - gl.height / 2);

	}

	/**
	 * Renders the given string to the center of the screen. (Horizontally center
	 * not vertically). But also allows you to specify Y location of the string.
	 * 
	 * @param text String to be rendered.
	 * @param sb   SpriteBatch object
	 * @param font BitmapFont object
	 * @param y    Y location of the text.
	 */
	public static void renderCenter(String text, SpriteBatch sb, BitmapFont font, float y) {

		GlyphLayout gl = new GlyphLayout(font, text);
		font.draw(sb, text, GameConstants.SCREEN_WIDTH / 2.0f - gl.width / 2.0f,
				GameConstants.SCREEN_HEIGHT * y - gl.height / 2);

	}

}