package com.javakaian.shooter;

import java.util.LinkedList;
import java.util.Queue;

public class LoginController {

	private Queue<Integer> idSet;

	public LoginController() {

		idSet = new LinkedList<>();

		// max 100 players allowed to play at the same time.
		for (int i = 0; i < 100; i++) {
			idSet.add(i + 1);
		}
	}

	public int getUserID() {

		return idSet.poll();
	}

	public void putUserIDBack(int id) {
		idSet.add(id);
	}

}
