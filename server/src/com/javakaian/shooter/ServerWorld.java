package com.javakaian.shooter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.javakaian.network.OServer;
import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.shapes.Bullet;
import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;

public class ServerWorld implements ClientMessageObserver {

	private List<Player> players;
	private List<Enemy> enemies;
	private List<Bullet> bullets;

	private OServer oServer;

	private float deltaTime = 0;

	private float enemyTime = 0f;

	private int idCounter = 0;

	private BlockingQueue<Object> messageQueue;
	private BlockingQueue<Connection> connectionQueue;

	public ServerWorld() {

		this.oServer = new OServer(this);
		this.players = new ArrayList<Player>();
		this.enemies = new ArrayList<Enemy>();
		this.bullets = new ArrayList<Bullet>();

		this.messageQueue = oServer.getMessageQueue();
		this.connectionQueue = oServer.getConnectionQueue();
	}

	public void update(float deltaTime) {

		this.deltaTime = deltaTime;
		this.enemyTime += deltaTime;

		parseMessage();

		GameWorldMessage gwm = new GameWorldMessage();

		players.forEach(p -> {
			p.update(deltaTime);
		});

		enemies.stream().forEach(e -> e.update(deltaTime));

		enemies.removeIf(e -> !e.isVisible());
		bullets.forEach(b -> b.update(deltaTime));
		bullets.removeIf(b -> !b.isVisible());

		int[] coordinates = new int[enemies.size() * 2];

		for (int i = 0; i < enemies.size(); i++) {
			coordinates[i * 2] = (int) enemies.get(i).getX();
			coordinates[i * 2 + 1] = (int) enemies.get(i).getY();
		}

		gwm.enemies = coordinates;

		int[] pcord = new int[players.size() * 3];
		for (int i = 0; i < players.size(); i++) {

			pcord[i * 3] = (int) players.get(i).getPosition().x;
			pcord[i * 3 + 1] = (int) players.get(i).getPosition().y;
			pcord[i * 3 + 2] = (int) players.get(i).getId();
		}

		gwm.players = pcord;

		int[] barray = new int[bullets.size() * 2];
		for (int i = 0; i < bullets.size(); i++) {
			barray[i * 2] = (int) bullets.get(i).getPosition().x;
			barray[i * 2 + 1] = (int) bullets.get(i).getPosition().y;
		}

		gwm.bullets = barray;

		oServer.getServer().sendToAllUDP(gwm);

		if (enemyTime >= 0.4) {
			enemyTime = 0;

			Enemy e = new Enemy(new Random().nextInt(1000), new Random().nextInt(1000), 10);
			enemies.add(e);
		}

		checkCollision();

	}

	private void checkCollision() {

		for (Bullet b : bullets) {

			Rectangle rb = new Rectangle(b.getPosition().x, b.getPosition().y, 10, 10);
			enemies.removeIf(e -> e.getBoundRect().overlaps(rb));

		}

	}

	private void parseMessage() {

		Connection con = connectionQueue.poll();
		Object message = messageQueue.poll();

		if (con == null || message == null)
			return;

		if (message instanceof LoginMessage) {

			LoginMessage m = (LoginMessage) message;
			loginReceived(con, m);

		} else if (message instanceof LogoutMessage) {
			LogoutMessage m = (LogoutMessage) message;
			logoutReceived(m);

		} else if (message instanceof PositionMessage) {
			PositionMessage m = (PositionMessage) message;
			playerMovedReceived(m);

		} else if (message instanceof ShootMessage) {
			ShootMessage m = (ShootMessage) message;
			shootMessageReceived(m);
		}
	}

	@Override
	public void loginReceived(Connection con, LoginMessage m) {

		players.add(new Player(m.x, m.y, 50, idCounter));
		System.out.println("Login Message recieved from : " + idCounter);
		m.id = idCounter;
		idCounter++;
		oServer.getServer().sendToUDP(con.getID(), m);
	}

	@Override
	public void logoutReceived(LogoutMessage m) {

		players.stream().filter(p -> p.getId() == m.id).findFirst().ifPresent(p -> players.remove(p));
		System.out.println("Logout Message recieved from : " + m.id + " Size: " + players.size());

	}

	@Override
	public void playerMovedReceived(PositionMessage move) {

		Player player = null;

		for (Player p : players) {
			if (p.getId() == move.id)
				player = p;
		}

		Vector2 v = player.getPosition();
		switch (move.direction) {
		case LEFT:
			v.x -= deltaTime * 200;
			break;
		case RIGHT:
			v.x += deltaTime * 200;
			break;
		case UP:
			v.y -= deltaTime * 200;
			break;
		case DOWN:
			v.y += deltaTime * 200;
			break;

		default:
			break;
		}

	}

	@Override
	public void shootMessageReceived(ShootMessage pp) {

		players.stream().filter(p -> p.getId() == pp.id).findFirst().ifPresent(p -> {
			bullets.add(new Bullet(p.getPosition().x + 25, p.getPosition().y + 25, 10, pp.angleDeg));
			bullets.add(new Bullet(p.getPosition().x + 35, p.getPosition().y + 25, 10, pp.angleDeg));
			bullets.add(new Bullet(p.getPosition().x + 45, p.getPosition().y + 25, 10, pp.angleDeg));
			bullets.add(new Bullet(p.getPosition().x + 25, p.getPosition().y + 35, 10, pp.angleDeg));
			bullets.add(new Bullet(p.getPosition().x + 35, p.getPosition().y + 35, 10, pp.angleDeg));
			bullets.add(new Bullet(p.getPosition().x + 45, p.getPosition().y + 35, 10, pp.angleDeg));
			bullets.add(new Bullet(p.getPosition().x + 25, p.getPosition().y + 45, 10, pp.angleDeg));
			bullets.add(new Bullet(p.getPosition().x + 35, p.getPosition().y + 45, 10, pp.angleDeg));
			bullets.add(new Bullet(p.getPosition().x + 45, p.getPosition().y + 45, 10, pp.angleDeg));
		});

	}

}
