package com.javakaian.shooter;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javakaian.network.OClient;
import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.PositionMessage.DIRECTION;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.input.PlayStateInput;
import com.javakaian.shooter.shapes.Player;

public class KillThemAll extends ApplicationAdapter implements NetworkEvents {

	SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;

	private Player player;
	private HashMap<String, Player> playerSet;

	private ShapeRenderer sr;

	private OClient myclient;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Gdx.input.setInputProcessor(new PlayStateInput(this));

		sr = new ShapeRenderer();

		myclient = new OClient(this);

		player = new Player(140, 50, 50);

		LoginMessage m = new LoginMessage();
		m.name = player.getName();
		m.x = player.getPosition().x;
		m.y = player.getPosition().y;
		myclient.getClient().sendTCP(m);

		playerSet = new HashMap<String, Player>();
	}

	@Override
	public void render() {

		batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);
		camera.update();

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		sr.begin(ShapeType.Line);
		// manyShapes.render(sr);
		// manyShapes.update(Gdx.graphics.getDeltaTime());
		playerSet.forEach((k, v) -> {
			v.render(sr);
		});

		sr.end();

		processInputs();
	}

	@Override
	public void dispose() {

		LogoutMessage m = new LogoutMessage();
		m.name = player.getName();
		myclient.getClient().sendTCP(m);

		batch.dispose();
		img.dispose();
	}

	private void processInputs() {

		PositionMessage p = new PositionMessage();
		p.name = player.getName();

		if (Gdx.input.isKeyPressed(Keys.W)) {
			p.direction = DIRECTION.UP;
			myclient.getClient().sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			p.direction = DIRECTION.DOWN;
			myclient.getClient().sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			p.direction = DIRECTION.LEFT;
			myclient.getClient().sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			p.direction = DIRECTION.RIGHT;
			myclient.getClient().sendUDP(p);
		}
	}

	public void scrolled(float amountY) {

		System.out.println(camera.zoom);
		if (amountY > 0) {
			camera.zoom += 0.2;
		} else {
			if (camera.zoom >= 0.4) {
				camera.zoom -= 0.2;
			}
		}

	}

	public void resetZoom() {
		camera.zoom = 1;
	}

	@Override
	public void addNewPlayer(float x, float y, String name) {
	}

	@Override
	public void removePlayer(String name) {
	}

	@Override
	public void gwmReceived(GameWorldMessage gwm) {
		this.playerSet = gwm.players;
	}

	public void shoot() {
		ShootMessage m = new ShootMessage();
		m.name = player.getName();
		myclient.getClient().sendUDP(m);
	}

}
