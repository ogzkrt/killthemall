package com.javakaian.states;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.javakaian.states.State.StateEnum;

/**
 * * This class is responsible for controlling states in a game. It invokes
 * render and update functions of current state and binds current states input
 * processor as a processor.
 * 
 * It keeps all the states of a game inside a HashMap. If player demands a state
 * which is not already in that hashmap, it creates that state and puts it to
 * the hashmap.
 * 
 * @author oguz
 * 
 */
public class StateController {

	/** A hashmap which stores states inside. */
	private Map<Integer, State> stateMap;
	/** State object to store current state */
	private State currentState;
	/** Ip address of the server */
	private String inetAddress;

	public StateController(String ip) {

		this.inetAddress = ip;
		stateMap = new HashMap<Integer, State>();

	}

	/**
	 * Sets the current state of the game to the given state. Takes StateEnum as a
	 * parameter.
	 * 
	 * @param stateEnum
	 **/
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

	/**
	 * Renders the current state.
	 */
	public void render() {

		currentState.render();
	}

	/**
	 * Updates the current state.
	 */
	public void update(float deltaTime) {
		currentState.update(deltaTime);
	}

	/**
	 * Calls the dispose method of each state in the hashmap.
	 */
	public void dispose() {
		stateMap.forEach((k, v) -> {
			v.dispose();
		});
	}

	/**
	 * HashMap object which stores states of the game as key value pair.
	 */
	public Map<Integer, State> getStateMap() {
		return stateMap;
	}

	/** Returns the ip address of a server. */
	public String getInetAddress() {
		return inetAddress;
	}

}
