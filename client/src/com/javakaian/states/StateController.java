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

	}

	public void setState(StateEnum stateEnum) {

		currentState = stateMap.get(stateEnum.ordinal());
		if (currentState == null) {
			switch (stateEnum) {
			case PlayState:
				currentState = new PlayState(this);
				break;
			case GameOverState:
				currentState = new GameOverState(this);
				break;
			case MenuState:
				currentState = new MenuState(this);
				break;

			default:
				break;
			}
			stateMap.put(stateEnum.ordinal(), currentState);
		}
		Gdx.input.setInputProcessor(currentState.ip);
	}

	public void render() {

		currentState.render();
	}

	public void update(float deltaTime) {
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
