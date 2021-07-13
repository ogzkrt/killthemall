package com.javakaian.network.messages;

/**
 * @author oguz
 * 
 *         This should be send when player wants to shoot.
 */
public class ShootMessage {

	/** Player ID */
	private int id;
	/**
	 * An Angle which player wants to shoot. This angle accepts player as an origin
	 * point.
	 */
	private float angleDeg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAngleDeg() {
		return angleDeg;
	}

	public void setAngleDeg(float angleDeg) {
		this.angleDeg = angleDeg;
	}
}
