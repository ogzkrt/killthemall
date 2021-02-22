package com.javakaian.shooter;

import com.javakaian.network.messages.GetCurrentPlayersMessage;
import com.javakaian.network.messages.PositionMessage;

public interface NetworkEvents {

	public void addNewPlayer(float x, float y, String name);

	public void updatePoisiton(PositionMessage pm);

	public void updateCurrentPlayers(GetCurrentPlayersMessage gcp);

	public void removePlayer(String name);

}
