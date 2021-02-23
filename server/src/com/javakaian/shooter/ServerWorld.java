package com.javakaian.shooter;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.javakaian.network.OServer;
import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.shapes.Bullet;
import com.javakaian.shooter.shapes.Player;

public class ServerWorld implements ClientMessageObserver {

	private HashMap<String, Player> players;
	private OServer oServer;

	public ServerWorld() {

		this.oServer = new OServer(this);
		this.players = new HashMap<String, Player>();
	}

	public void update(float deltaTime) {

		GameWorldMessage gwm = new GameWorldMessage();

		players.forEach((k, v) -> {
			v.update(deltaTime);
		});

		gwm.players = players;
		oServer.getServer().sendToAllUDP(gwm);
	}

	@Override
	public void loginReceived(LoginMessage m) {

		players.put(m.name, new Player(m.x, m.y, 50));
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
			v.x -= 2;
			p.setPosition(v);
			break;
		case RIGHT:
			v.x += 2;
			p.setPosition(v);
			break;
		case UP:
			v.y += 2;
			p.setPosition(v);
			break;
		case DOWN:
			v.y -= 2;
			p.setPosition(v);
			break;

		default:
			break;
		}

	}

	@Override
	public void shootMessageReceived(ShootMessage pp) {
		Player p = players.get(pp.name);
		p.getBulletSet().add(new Bullet(p.getPosition().x, p.getPosition().y, 10));
		System.out.println("shoot message recieved");
		players.remove(pp.name);
		players.put(pp.name, p);
	}

}
