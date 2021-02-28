package com.javakaian.shooter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

	private HashMap<String, Player> players;
	private List<Enemy> enemies;

	private OServer oServer;

	private float deltaTime = 0;

	private float enemyTime = 0f;

	public ServerWorld() {

		this.oServer = new OServer(this);
		this.players = new HashMap<String, Player>();
		this.enemies = new ArrayList<Enemy>();
	}

	public void update(float deltaTime) {

		this.deltaTime = deltaTime;
		this.enemyTime += deltaTime;

		GameWorldMessage gwm = new GameWorldMessage();

		players.forEach((k, v) -> {
			v.update(deltaTime);
		});
		players.forEach((k, v) -> {
			v.checkForCollisiion();
		});

		enemies.stream().forEach(e -> e.update(deltaTime));
		enemies = enemies.stream().filter(e -> e.isVisible()).collect(Collectors.toList());
		gwm.players = players;

		int[] coordinates = new int[enemies.size() * 2];

		for (int i = 0; i < enemies.size(); i++) {
			coordinates[i * 2] = (int) enemies.get(i).getX();
			coordinates[i * 2 + 1] = (int) enemies.get(i).getY();
		}
		gwm.enemies = coordinates;

		oServer.getServer().sendToAllUDP(gwm);

		if (enemyTime >= 0.4) {
			enemyTime = 0;

			Enemy e = new Enemy(new Random().nextInt(1000), new Random().nextInt(1000), 10);
			enemies.add(e);
			System.out.println("Enemie Size: " + enemies.size());
		}

		checkCollision();

	}

	private void checkCollision() {
		players.forEach((k, p) -> {
			p.getBulletSet().stream().filter(b -> b.isVisible()).forEach(b -> {

				Rectangle rb = new Rectangle(b.getPosition().x, b.getPosition().y, 10, 10);

				enemies.stream().forEach(e -> {

					Rectangle re = new Rectangle(e.getX(), e.getY(), 10, 10);

					if (rb.overlaps(re)) {
						b.setVisible(false);
						e.setVisible(false);
					}
				});

			});
		});
	}

	@Override
	public void loginReceived(LoginMessage m) {

		players.put(m.name, new Player(m.x, m.y, 50, m.name));
		System.out.println("Login Message recieved from : " + m.name);
	}

	@Override
	public void logoutReceived(LogoutMessage m) {

		players.remove(m.name);
		System.out.println("Logout Message recieved from : " + m.name + " Size: " + players.size());

	}

	@Override
	public void playerMovedReceived(PositionMessage move) {

		Player p = players.get(move.name);
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

	}

	@Override
	public void shootMessageReceived(ShootMessage pp) {
		Player p = players.get(pp.name);
		p.getBulletSet().add(new Bullet(p.getPosition().x + 25, p.getPosition().y + 25, 10, pp.angleDeg));
		System.out.println("shoot message recieved X: " + p.getPosition().x + " Y: " + p.getPosition().y);
		players.remove(pp.name);
		players.put(pp.name, p);
	}

}
