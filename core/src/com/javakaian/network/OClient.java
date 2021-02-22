package com.javakaian.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.javakaian.network.messages.GetCurrentPlayersMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.NetworkEvents;

public class OClient {

	private Client client;
	private NetworkEvents game;

	public OClient(NetworkEvents game) {

		this.game = game;

		client = new Client();
		client.start();

		ONetwork.register(client);

		client.addListener(new ThreadedListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				if (object instanceof LoginMessage) {

					LoginMessage newPlayer = (LoginMessage) object;
					addNew(newPlayer.x, newPlayer.y, newPlayer.name);

				}
				if (object instanceof PositionMessage) {

					PositionMessage pp = (PositionMessage) object;
					updatePosition(pp);
				}
				if (object instanceof GetCurrentPlayersMessage) {

					GetCurrentPlayersMessage pp = (GetCurrentPlayersMessage) object;
					updateCurrentPlayers(pp);
					pp.spList.forEach((k, v) -> System.out.println(k));
					System.out.println("SEND CURRENT PLAYERS RECIEVED..");
				}
				if (object instanceof LogoutMessage) {

					LogoutMessage pp = (LogoutMessage) object;
					removePlayer(pp);
				}
				if (object instanceof ShootMessage) {

					ShootMessage pp = (ShootMessage) object;
					shootMessageRecieved(pp);
				}

			}

		}));

		try {
			client.connect(5000, "localhost", 1234, 1235);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void shootMessageRecieved(ShootMessage pp) {
		game.shootMessage(pp.name);
	}

	public void removePlayer(LogoutMessage pp) {
		game.removePlayer(pp.name);
	}

	public void addNew(float x, float y, String name) {
		game.addNewPlayer(x, y, name);
	}

	public void updatePosition(PositionMessage pp) {

		game.updatePoisiton(pp);
	}

	public void updateCurrentPlayers(GetCurrentPlayersMessage gcp) {
		game.updateCurrentPlayers(gcp);
	}

	public Client getClient() {
		return client;
	}

}
