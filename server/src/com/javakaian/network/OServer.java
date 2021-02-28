package com.javakaian.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.ClientMessageObserver;

public class OServer {

	private Server server;

	private int TCP_PORT = 1234;
	private int UDP_PORT = 1235;

	private ClientMessageObserver cmo;

	public OServer(ClientMessageObserver cmo) {

		this.cmo = cmo;
		server = new Server();

		ONetwork.register(server);

		server.addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				if (object instanceof LoginMessage) {

					LoginMessage l = (LoginMessage) object;
					cmo.loginReceived(connection, l);
				} else if (object instanceof PositionMessage) {

					PositionMessage move = (PositionMessage) object;
					cmo.playerMovedReceived(move);

				} else if (object instanceof LogoutMessage) {

					LogoutMessage pp = (LogoutMessage) object;
					cmo.logoutReceived(pp);

				} else if (object instanceof ShootMessage) {

					ShootMessage pp = (ShootMessage) object;
					cmo.shootMessageReceived(pp);
				}

			}
		});
		server.start();
		try {
			server.bind(TCP_PORT, UDP_PORT);
			System.out.println("Server has ben started on TCP_PORT: " + TCP_PORT + " UDP_PORT: " + UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Server getServer() {
		return server;
	}

}
