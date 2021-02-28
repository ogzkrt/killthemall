package com.javakaian.shooter;

import com.javakaian.network.messages.GameWorldMessage;

public interface NetworkEvents {

	public void addNewPlayer(float x, float y, int id);

	public void removePlayer(int id);

	public void gwmReceived(GameWorldMessage gwm);

}
