package com.javakaian.shooter;

import com.javakaian.network.messages.GameWorldMessage;

public interface NetworkEvents {

	public void addNewPlayer(float x, float y, String name);

	public void removePlayer(String name);

	public void gwmReceived(GameWorldMessage gwm);

}
