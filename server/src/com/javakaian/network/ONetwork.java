package com.javakaian.network;

import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.shapes.Bullet;
import com.javakaian.shooter.shapes.Player;

public class ONetwork {

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		// messages
		kryo.register(LoginMessage.class);
		kryo.register(LogoutMessage.class);
		kryo.register(GameWorldMessage.class);
		kryo.register(Player.class);
		kryo.register(Bullet.class);
		kryo.register(PositionMessage.class);
		kryo.register(PositionMessage.DIRECTION.class);
		kryo.register(ShootMessage.class);
		// objects
		kryo.register(java.util.HashSet.class);
		kryo.register(Vector2.class);
		kryo.register(HashMap.class);
		kryo.register(Set.class);
	}

}
