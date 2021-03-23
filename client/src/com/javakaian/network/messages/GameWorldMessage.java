package com.javakaian.network.messages;

/**
 * 
 * This message will be broadcast to all clients 60 times per second. It
 * contains position arrays of enemies,players and bullets.
 * 
 * @author oguz
 *
 */
public class GameWorldMessage {

	/** X,Y Coordinates of all the enemies.-> x1,y1,x2,y2,x3,y3.. */
	public int[] enemies;
	/**
	 * X,Y,ID,Health of all the players.->
	 * x1,y1,ID1,Health1,x2,y2,ID2,Health2,x3,y3,ID3,Health3..
	 */
	public int[] players;
	/** X,Y,Size of all the bullets.-> x1,y1,s1,x2,y2,s2,x3,y3,s3.. */
	public float[] bullets;

}
