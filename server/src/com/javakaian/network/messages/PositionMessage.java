package com.javakaian.network.messages;

public class PositionMessage {

	public int id;
	public DIRECTION direction;

	public enum DIRECTION {
		LEFT, RIGHT, DOWN, DOWN_LEFT, DOWN_RIGHT, UP, UP_LEFT, UP_RIGHT
	}

}
