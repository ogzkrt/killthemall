package com.javakaian.shooter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javakaian.network.OClient;
import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.PositionMessage.DIRECTION;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.input.PlayStateInput;
import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;

public class KillThemAll extends ApplicationAdapter implements NetworkEvents {

	SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;

	private Player player;
	private HashMap<String, Player> playerSet;
	private Set<Enemy> enemies;

	private ShapeRenderer sr;

	private OClient myclient;

	private float angle = 90;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
		enemies = new HashSet<Enemy>();
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
		enemies.stream().forEach(e -> e.render(sr));

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {

			Vector3 up = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			Vector2 pos = new Vector2(player.getPosition().x + 25, player.getPosition().y + 25);
			Vector2 mouse = new Vector2(up.x, up.y);

			angle = (float) (8 * Math.PI / 4 - mouse.sub(pos).angleRad());
			int r = 20;
			sr.line(player.getPosition().x + 25, player.getPosition().y + 25, up.x, up.y);

		} else {
			angle = 90f;
		}

		sr.end();

		processInputs();

		float lerp = 0.05f;

		camera.position.x += (player.getPosition().x - camera.position.x) * lerp;
		camera.position.y += (player.getPosition().y - camera.position.y) * lerp;
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
		this.enemies = gwm.enemies;

		Player p = playerSet.get(player.getName());
		if (p != null)
			this.player = p;
	}

	public void shoot() {
		ShootMessage m = new ShootMessage();
		m.name = player.getName();
		m.angleDeg = angle;
		myclient.getClient().sendUDP(m);
	}

}
