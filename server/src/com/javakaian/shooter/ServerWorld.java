package com.javakaian.shooter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.javakaian.network.OServer;
import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PlayerDied;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.shapes.Bullet;
import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;

public class ServerWorld implements OMessageListener {

	private List<Player> players;
	private List<Enemy> enemies;
	private List<Bullet> bullets;

	private OServer oServer;

	private float deltaTime = 0;

	private float enemyTime = 0f;

	private int idCounter = 0;

	private Queue<Object> messageQueue;
	private Queue<Connection> connectionQueue;

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

		oServer.parseMessage();

		GameWorldMessage gwm = new GameWorldMessage();

		players.forEach(p -> p.update(deltaTime));
		enemies.forEach(e -> e.update(deltaTime));
		bullets.forEach(b -> b.update(deltaTime));

		players.removeIf(p -> !p.isAlive());
		enemies.removeIf(e -> !e.isVisible());
		bullets.removeIf(b -> !b.isVisible());

		int[] coordinates = new int[enemies.size() * 2];

		for (int i = 0; i < enemies.size(); i++) {
			coordinates[i * 2] = (int) enemies.get(i).getX();
			coordinates[i * 2 + 1] = (int) enemies.get(i).getY();
		}

		gwm.enemies = coordinates;

		int[] pcord = new int[players.size() * 4];
		for (int i = 0; i < players.size(); i++) {

			pcord[i * 4] = (int) players.get(i).getPosition().x;
			pcord[i * 4 + 1] = (int) players.get(i).getPosition().y;
			pcord[i * 4 + 2] = players.get(i).getId();
			pcord[i * 4 + 3] = players.get(i).getHealth();
		}

		gwm.players = pcord;

		float[] barray = new float[bullets.size() * 3];
		for (int i = 0; i < bullets.size(); i++) {
			barray[i * 3] = bullets.get(i).getPosition().x;
			barray[i * 3 + 1] = bullets.get(i).getPosition().y;
			barray[i * 3 + 2] = bullets.get(i).getSize();
		}

		gwm.bullets = barray;

		oServer.sendToAllUDP(gwm);

		if (enemyTime >= 0.4 && enemies.size() <= 15) {
			enemyTime = 0;
			if (enemies.size() % 50 == 0)
				System.out.println("Number of enemies : " + enemies.size());
			Enemy e = new Enemy(new Random().nextInt(1000), new Random().nextInt(1000), 10);
			enemies.add(e);
		}

		checkCollision();

	}

	private void checkCollision() {

		for (Bullet b : bullets) {

			for (Enemy e : enemies) {

				if (b.isVisible() && e.getBoundRect().overlaps(b.getBoundRect())) {
					b.setVisible(false);
					e.setVisible(false);
					players.stream().filter(p -> p.getId() == b.getId()).findFirst().ifPresent(p -> p.increaseHealth());
				}
			}
			for (Player p : players) {
				if (b.isVisible() && p.getBoundRect().overlaps(b.getBoundRect()) && p.getId() != b.getId()) {
					b.setVisible(false);
					p.hit();
					if (!p.isAlive()) {

						PlayerDied m = new PlayerDied();
						m.id = p.getId();
						oServer.sendToAllUDP(m);
					}

				}
			}

		}

	}

	@Override
	public void loginReceived(Connection con, LoginMessage m) {

		players.add(new Player(m.x, m.y, 50, idCounter));
		System.out.println("Login Message recieved from : " + idCounter);
		m.id = idCounter;
		idCounter++;
		oServer.sendToUDP(con.getID(), m);
	}

	@Override
	public void logoutReceived(LogoutMessage m) {

		players.stream().filter(p -> p.getId() == m.id).findFirst().ifPresent(p -> players.remove(p));
		System.out.println("Logout Message recieved from : " + m.id + " Size: " + players.size());

	}

	@Override
	public void playerMovedReceived(PositionMessage move) {

		players.stream().filter(p -> p.getId() == move.id).findFirst().ifPresent(p -> {

			Vector2 v = p.getPosition();
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

		});

	}

	@Override
	public void shootMessageReceived(ShootMessage pp) {

		players.stream().filter(p -> p.getId() == pp.id).findFirst().ifPresent(p -> {
			bullets.add(new Bullet(p.getPosition().x + p.getBoundRect().width / 2,
					p.getPosition().y + p.getBoundRect().height / 2, 10, pp.angleDeg, pp.id));
		});

	}

}
