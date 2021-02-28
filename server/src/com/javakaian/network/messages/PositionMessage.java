package com.javakaian.network.messages;

public class PositionMessage {

	public int id;
	public DIRECTION direction;

	public enum DIRECTION {
		LEFT, RIGHT, DOWN, UP
	}

}
