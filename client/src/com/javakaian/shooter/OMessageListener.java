package com.javakaian.shooter;

import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PlayerDied;

/**
 * Listener class. A class who wants to listen events like, login,logout etc.
 * should implement this class.
 * 
 * Methods of this class should be called by server.
 * 
 * @author oguz
 *
 */
public interface OMessageListener {

	/**
	 * Should be invoked when login message received. Things that are going to
	 * happen after player logs in should be done under the implementation of this
	 * function.
	 */
	public void loginReceieved(LoginMessage m);

	/**
	 * Should be invoked when logout message received. Things that are going to
	 * happen after player logs out in should be done under the implementation of
	 * this function.
	 */
	public void logoutReceieved(LogoutMessage m);

	/**
	 * Should be invoked when game world message received. Clients should update
	 * themselves according to GWM message sent by server. That update process
	 * should be done under the implementation of this function.
	 */
	public void gwmReceived(GameWorldMessage m);

	/**
	 * Should be invoked when player died message received. Things that are going to
	 * happen after player is dead, should be done under the implementation of this
	 * function.
	 */
	public void playerDiedReceived(PlayerDied m);

}
