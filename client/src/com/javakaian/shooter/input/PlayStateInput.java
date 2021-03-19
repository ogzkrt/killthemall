package com.javakaian.shooter.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.javakaian.states.PlayState;
import com.javakaian.states.State.StateEnum;

public class PlayStateInput extends InputAdapter {

	private PlayState playState;

	public PlayStateInput() {
	}

	public PlayStateInput(PlayState playState) {
		this.playState = playState;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {

		playState.scrolled(amountY);

		return super.scrolled(amountX, amountY);
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.SPACE:
			// game.resetZoom();
			playState.shoot();
			break;
		case Keys.M:
			// game.resetZoom();
			playState.getSc().setState(StateEnum.MenuState);
			break;

		default:
			break;
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return super.keyUp(keycode);
	}
}
