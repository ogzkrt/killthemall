package com.javakaian.shooter.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.javakaian.shooter.KillThemAll;

public class PlayStateInput extends InputAdapter{
	
	private KillThemAll game;
	public PlayStateInput() {
	}
	
	public PlayStateInput(KillThemAll game) {
		this.game=game;
	}
	
	@Override
	public boolean scrolled(float amountX, float amountY) {
		
		game.scrolled(amountY);
		
		return super.scrolled(amountX, amountY);
	}
	
	
	
	@Override
	public boolean keyDown(int keycode) {
		
		switch (keycode) {
		case Keys.SPACE:
			game.resetZoom();
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
