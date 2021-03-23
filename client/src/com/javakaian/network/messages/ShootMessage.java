package com.javakaian.network.messages;

/**
 * @author oguz
 * 
 *         This should be send when player wants to shoot.
 */
public class ShootMessage {

	/** Player ID */
	public int id;
	/**
	 * An Angle which player wants to shoot. This angle accepts player as an origin
	 * point.
	 */
	public float angleDeg;
}
