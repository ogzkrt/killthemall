package com.javakaian.network.messages;

/**
 * @author oguz
 *
 *         This message will be sent only when player died.
 */
public class PlayerDied {

	/** Player id */
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
