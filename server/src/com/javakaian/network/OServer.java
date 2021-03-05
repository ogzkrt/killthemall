package com.javakaian.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.javakaian.shooter.ClientMessageObserver;

public class OServer {

	private Server server;

	private int TCP_PORT = 1234;
	private int UDP_PORT = 1235;

	private ClientMessageObserver cmo;

	private Queue<Object> messageQueue;
	private Queue<Connection> connectionQueue;

	public OServer(ClientMessageObserver cmo) {

		this.cmo = cmo;
		server = new Server();

		messageQueue = new LinkedList<Object>();
		connectionQueue = new LinkedList<Connection>();

		ONetwork.register(server);

		server.addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				messageQueue.add(object);
				connectionQueue.add(connection);

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

	public Queue<Object> getMessageQueue() {
		return messageQueue;
	}

	public Queue<Connection> getConnectionQueue() {
		return connectionQueue;
	}
}
