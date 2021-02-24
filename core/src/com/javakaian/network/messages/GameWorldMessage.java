package com.javakaian.network.messages;

import java.util.HashMap;

import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;

public class GameWorldMessage {

	public HashMap<String, Player> players;
	public HashMap<String, Enemy> enemies;

}
