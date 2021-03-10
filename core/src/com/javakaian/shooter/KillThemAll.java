package com.javakaian.shooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.javakaian.states.State.StateEnum;
import com.javakaian.states.StateController;

public class KillThemAll extends ApplicationAdapter {

	private StateController sc;

	@Override
	public void create() {

		sc = new StateController();
		sc.setState(StateEnum.MenuState);

	}

	@Override
	public void render() {

		sc.render();
		sc.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		super.dispose();
		sc.dispose();
	}

}
