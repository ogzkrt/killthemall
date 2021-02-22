package com.javakaian.network;

import java.util.HashMap;
import java.util.Set;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.javakaian.network.messages.GetCurrentPlayersMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.ShootMessage;

public class ONetwork {

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		// messages
		kryo.register(LoginMessage.class);
		kryo.register(PositionMessage.class);
		kryo.register(GetCurrentPlayersMessage.class);
		kryo.register(LogoutMessage.class);
		kryo.register(ShootMessage.class);

		// objects
		kryo.register(ServerPlayer.class);
		kryo.register(HashMap.class);
		kryo.register(Set.class);
	}

}
