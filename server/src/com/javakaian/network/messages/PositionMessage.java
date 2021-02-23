package com.javakaian.network.messages;

public class PositionMessage {

	public String name;
	public DIRECTION direction;

	public enum DIRECTION {
		LEFT, RIGHT, DOWN, UP
	}

}
