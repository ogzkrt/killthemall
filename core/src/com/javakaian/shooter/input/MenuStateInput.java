package com.javakaian.shooter.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.javakaian.states.MenuState;
import com.javakaian.states.State.StateEnum;

public class MenuStateInput extends InputAdapter {

	private MenuState menuState;

	public MenuStateInput() {
	}

	public MenuStateInput(MenuState game) {
		this.menuState = game;
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.SPACE:
			menuState.getSc().setState(StateEnum.PlayState);
			break;

		case Keys.Q:
			menuState.quit();
			break;
		case Keys.R:
			menuState.restart();
			break;

		default:
			break;
		}

		return true;
	}

}
