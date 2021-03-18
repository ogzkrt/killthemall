package com.javakaian.states;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.javakaian.states.State.StateEnum;

public class StateController {

	private Map<Integer, State> stateMap;

	private State currentState;

	private String ip;

	public StateController(String ip) {

		this.ip = ip;
		stateMap = new HashMap<Integer, State>();

		stateMap.put(StateEnum.MenuState.ordinal(), new MenuState(this));
		stateMap.put(StateEnum.GameOverState.ordinal(), new GameOverState(this));

	}

	public void setState(StateEnum stateEnum) {

		currentState = stateMap.get(stateEnum.ordinal());
		if (currentState == null) {
			switch (stateEnum) {
			case PlayState:
				currentState = new PlayState(this);
				System.out.println("CURRENT STATE IS PLAY");
				break;
			case GameOverState:
				currentState = new GameOverState(this);
				System.out.println("CURRENT STATE IS GAME OVER");
				break;
			case MenuState:
				currentState = new MenuState(this);
				System.out.println("CURRENT STATE IS MENU");
				break;
			case PauseState:
				currentState = new PauseState(this);
				System.out.println("CURRENT STATE IS POUSE ");
				break;

			default:
				break;
			}
			stateMap.put(stateEnum.ordinal(), currentState);
		}
		Gdx.input.setInputProcessor(currentState.ip);
	}

	public void render() {

		if (currentState == null)
			return;
		currentState.render();
	}

	public void update(float deltaTime) {
		if (currentState == null)
			return;
		currentState.update(deltaTime);
	}

	public void dispose() {
		stateMap.forEach((k, v) -> {
			v.dispose();
		});
	}

	public Map<Integer, State> getStateMap() {
		return stateMap;
	}

	public String getIp() {
		return ip;
	}

}
