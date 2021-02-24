package com.javakaian.shooter;

import java.util.HashMap;
import java.util.Random;

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
	private HashMap<String, Enemy> enemies;

	private OServer oServer;

	private float deltaTime = 0;

	private float enemyTime = 0f;

	public ServerWorld() {

		this.oServer = new OServer(this);
		this.players = new HashMap<String, Player>();
		this.enemies = new HashMap<String, Enemy>();
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

		enemies.forEach((k, e) -> {
			e.update(deltaTime);
		});

		gwm.players = players;
		gwm.enemies = enemies;

		oServer.getServer().sendToAllUDP(gwm);

		if (enemyTime >= 5) {
			enemyTime = 0;

			Enemy e = new Enemy(new Random().nextInt(1000), new Random().nextInt(1000), 10);
			enemies.put(e.getName(), e);
			System.out.println("Enemie Size: " + enemies.size());
		}

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
			p.setPosition(v);
			break;
		case RIGHT:
			v.x += deltaTime * 200;
			p.setPosition(v);
			break;
		case UP:
			v.y -= deltaTime * 200;
			p.setPosition(v);
			break;
		case DOWN:
			v.y += deltaTime * 200;
			p.setPosition(v);
			break;

		default:
			break;
		}

	}

	@Override
	public void shootMessageReceived(ShootMessage pp) {
		Player p = players.get(pp.name);
		p.getBulletSet().add(new Bullet(p.getPosition().x, p.getPosition().y, 10, pp.angleDeg));
		System.out.println("shoot message recieved X: " + p.getPosition().x + " Y: " + p.getPosition().y);
		players.remove(pp.name);
		players.put(pp.name, p);
	}

}
