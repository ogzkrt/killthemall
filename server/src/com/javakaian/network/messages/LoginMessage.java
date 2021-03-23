package com.javakaian.network.messages;

/**
 * @author oguz
 * 
 *         This message will only be sent by clients when they want to join the
 *         game.
 *
 */
public class LoginMessage {

	/** Player id */
	public int id;
	/** Players current X,Y coordinates */
	public float x, y;
}
