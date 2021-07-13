package com.javakaian.states;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.javakaian.shooter.utils.GameConstants;
import com.javakaian.shooter.utils.GameUtils;

/**
 * 
 * This class represents the game states. Usually most of the games will have
 * states like GameOver,Option,Menu
 * 
 * Each state will have their own render and update methods.Which will be called
 * by StateController object.
 * 
 * Each state will have their own input
 * processor,shaperenderer,spritebatch,camera and also font.
 * 
 * @author oguz
 *
 */
public abstract class State {

	protected OrthographicCamera camera;
	protected InputProcessor ip;
	protected ShapeRenderer sr;
	protected SpriteBatch sb;
	protected BitmapFont bitmapFont;
	protected GlyphLayout glyphLayout;

	protected StateController sc;

	protected State(StateController sc) {

		this.sc = sc;

		camera = new OrthographicCamera(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
		camera.setToOrtho(true);

		sr = new ShapeRenderer();
		sb = new SpriteBatch();

		sr.setProjectionMatrix(camera.combined);
		sb.setProjectionMatrix(camera.combined);

		bitmapFont = GameUtils.generateBitmapFont(70, Color.WHITE);
		glyphLayout = new GlyphLayout();

	}

	/**
	 * All the rendering stuff should be made inside this method. This will be
	 * called by StateController object.
	 */
	public abstract void render();

	/**
	 * All the update stuff should be made inside this method. This will be called
	 * by StateController object. Deltatime parameter can be used for measuring
	 * time.
	 * 
	 * @param deltaTime Time between two consecutive frames.
	 *
	 */
	public abstract void update(float deltaTime);

	/**
	 * This method will be called by StateController. Allocated resources should be
	 * released here.
	 */
	public abstract void dispose();

	/**
	 * Returns the statecontroller object.
	 */
	public StateController getSc() {
		return sc;
	}

	/** Enum for each state */
	public enum StateEnum {

		PLAY_STATE, MENU_STATE, GAME_OVER_STATE, PAUSE_STATE

	}

}
