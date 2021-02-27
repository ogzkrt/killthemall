package com.javakaian.network.messages;

import java.util.HashMap;
import java.util.Set;

import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;

public class GameWorldMessage {

	public HashMap<String, Player> players;
	public Set<Enemy> enemies;

}
