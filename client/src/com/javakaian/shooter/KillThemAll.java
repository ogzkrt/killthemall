package com.javakaian.shooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.javakaian.states.State.StateEnum;
import com.javakaian.states.StateController;

public class KillThemAll extends ApplicationAdapter {

	private StateController sc;

	private String inetAddress;

	public KillThemAll(String inetAddress) {
		this.inetAddress = inetAddress;
	}

	@Override
	public void create() {

		sc = new StateController(inetAddress);
		sc.setState(StateEnum.MENU_STATE);

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
