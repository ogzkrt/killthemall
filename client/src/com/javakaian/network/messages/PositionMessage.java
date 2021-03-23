package com.javakaian.network.messages;

/***
 * Whenever player press a button from a keyboard this message should be send.
 */
public class PositionMessage {

	/** Player ID */
	public int id;
	/**
	 * The direction that player wants to move. Server will check the direction and
	 * let player move if its possible.
	 */
	public DIRECTION direction;

	public enum DIRECTION {
		LEFT, RIGHT, DOWN, UP
	}

}
