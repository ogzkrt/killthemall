package com.javakaian.network;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.javakaian.shooter.ClientMessageObserver;

public class OServer {

	private Server server;

	private int TCP_PORT = 1234;
	private int UDP_PORT = 1235;

	private ClientMessageObserver cmo;

	private BlockingQueue<Object> messageQueue;
	private BlockingQueue<Connection> connectionQueue;

	public OServer(ClientMessageObserver cmo) {

		this.cmo = cmo;
		server = new Server();

		messageQueue = new ArrayBlockingQueue<Object>(100);
		connectionQueue = new ArrayBlockingQueue<Connection>(100);

		ONetwork.register(server);

		server.addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				try {

					if (messageQueue.size() == 10)
						return;

					messageQueue.put(object);
					connectionQueue.put(connection);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

	public BlockingQueue<Object> getMessageQueue() {
		return messageQueue;
	}

	public BlockingQueue<Connection> getConnectionQueue() {
		return connectionQueue;
	}
}
