package com.javakaian.shooter.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.javakaian.states.MenuState;
import com.javakaian.states.State.StateEnum;

/**
 * Input Handles of MenuState
 * 
 * @author oguz
 *
 */
public class MenuStateInput extends InputAdapter {

	private MenuState menuState;

	public MenuStateInput(MenuState game) {
		this.menuState = game;
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.SPACE:
			menuState.getSc().setState(StateEnum.PLAY_STATE);
			break;
		case Keys.Q:
			menuState.quit();
			break;

		default:
			break;
		}

		return true;
	}

}
