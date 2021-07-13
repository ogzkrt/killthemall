package com.javakaian.network.messages;

/**
 * @author oguz
 * 
 *         This message will be sent when client close or loose the game.
 *
 */
public class LogoutMessage {

	/** Player id */
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
