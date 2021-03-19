package com.javakaian.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PlayerDied;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;

public class ONetwork {

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		// messages
		kryo.register(LoginMessage.class);
		kryo.register(LogoutMessage.class);
		kryo.register(GameWorldMessage.class);
		kryo.register(PositionMessage.class);
		kryo.register(PositionMessage.DIRECTION.class);
		kryo.register(ShootMessage.class);
		kryo.register(PlayerDied.class);
		// objects
		kryo.register(int[].class);

	}

}
