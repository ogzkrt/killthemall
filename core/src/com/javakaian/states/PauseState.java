package com.javakaian.states;

public class PauseState extends State {

	public PauseState(StateController sc) {
		super(sc);

		System.out.println("PAUSE STATE ");
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		System.out.println("PAUSE RENDERING..");
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
