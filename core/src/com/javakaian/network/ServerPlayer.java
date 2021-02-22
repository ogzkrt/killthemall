package com.javakaian.network;

public class ServerPlayer {

	private String name;
	private float x, y;

	public ServerPlayer() {
	}

	public ServerPlayer(float x, float y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setName(String name) {
		this.name = name;
	}

}
