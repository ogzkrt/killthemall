package com.javakaian.network.messages;

/***
 * Whenever player press a button from a keyboard this message should be send.
 */
public class PositionMessage {

	/** Player ID */
	private int id;
	/**
	 * The direction that player wants to move. Server will check the direction and
	 * let player move if its possible.
	 */
	private DIRECTION direction;

	public enum DIRECTION {
		LEFT, RIGHT, DOWN, UP
	}

	public DIRECTION getDirection() {
		return direction;
	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
