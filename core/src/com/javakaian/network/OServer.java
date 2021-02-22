package com.javakaian.network;

import java.io.IOException;
import java.util.HashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.javakaian.network.messages.GetCurrentPlayersMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.shooter.shapes.Player;

public class OServer {

	private Server server;

	private HashMap<String, ServerPlayer> spList;

	public OServer() {

		spList = new HashMap<String, ServerPlayer>();

		server = new Server();

		ONetwork.register(server);

		server.addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				if (object instanceof LoginMessage) {

					GetCurrentPlayersMessage gcp = new GetCurrentPlayersMessage();
					gcp.spList = spList;

					connection.sendTCP(gcp);

					LoginMessage l = (LoginMessage) object;
					System.out.println("Login message recived.." + l.name);
					server.sendToAllExceptUDP(connection.getID(), l);

					spList.put(l.name, new ServerPlayer(l.x, l.y, l.name));
				}
				if (object instanceof PositionMessage) {

					PositionMessage move = (PositionMessage) object;

					ServerPlayer sp = spList.get(move.name);
					sp.setX(move.x);
					sp.setY(move.y);

					server.sendToAllExceptUDP(connection.getID(), move);
				}
				if (object instanceof LogoutMessage) {

					LogoutMessage pp = (LogoutMessage) object;

					spList.remove(pp.name);

					server.sendToAllExceptTCP(connection.getID(), pp);

				}

			}
		});
		server.start();
		try {
			server.bind(1234, 1235);
			System.out.println("server created..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class PlayerConnection extends Connection {
		public Player player;
	}

	public static void main(String[] args) {

		OServer server = new OServer();

	}
}
