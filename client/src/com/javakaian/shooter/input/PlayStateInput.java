package com.javakaian.shooter.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.javakaian.states.PlayState;
import com.javakaian.states.State.StateEnum;

/**
 * Input handles of PlayState
 * 
 * @author oguz
 *
 */
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
			playState.shoot();
			break;
		case Keys.M:
			playState.getSc().setState(StateEnum.MENU_STATE);
			break;

		default:
			break;
		}

		return true;
	}

}
