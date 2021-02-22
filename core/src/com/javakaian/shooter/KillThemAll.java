package com.javakaian.shooter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javakaian.network.LogoutMessage;
import com.javakaian.network.OClient;
import com.javakaian.network.messages.GetCurrentPlayersMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.shooter.input.PlayStateInput;
import com.javakaian.shooter.shapes.Bullet;
import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;

public class KillThemAll extends ApplicationAdapter implements NetworkEvents {

	SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;

	private Player player;
	private List<Enemy> enemyList;

	private Set<Bullet> bulletSet;

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

		enemyList = new ArrayList<Enemy>();
		player = new Player(50, 50, 50, myclient);

		LoginMessage m = new LoginMessage();
		m.name = player.getName();
		m.x = player.getX();
		m.y = player.getY();
		myclient.getClient().sendTCP(m);

		bulletSet = new HashSet<Bullet>();

	}

	@Override
	public void render() {

		deneme();
		batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);
		camera.update();

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		sr.begin(ShapeType.Filled);
		// manyShapes.render(sr);
		// manyShapes.update(Gdx.graphics.getDeltaTime());

		player.render(sr);
		player.update(Gdx.graphics.getDeltaTime());

		for (Enemy enemy : enemyList) {
			enemy.render(sr);
			enemy.update(Gdx.graphics.getDeltaTime());
		}

		sr.end();
		float lerp = 1;
		camera.position.x += (player.getX() - camera.position.x) * lerp * Gdx.graphics.getDeltaTime();
		camera.position.y += (player.getY() - camera.position.y) * lerp * Gdx.graphics.getDeltaTime();
	}

	@Override
	public void dispose() {

		LogoutMessage m = new LogoutMessage();
		m.name = player.getName();
		myclient.getClient().sendTCP(m);

		batch.dispose();
		img.dispose();
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

	private void deneme() {

		int amount = 350;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.goLeft(amount);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.goRight(amount);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			this.goUp(amount);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			this.goDown(amount);
		}
		if (Gdx.input.isKeyPressed(Keys.R)) {
			camera.rotate(1);
		}
		if (Gdx.input.isKeyPressed(Keys.T)) {
			camera.rotate(-1);
		}

	}

	public void goLeft(float amount) {

		camera.translate(-1 * Gdx.graphics.getDeltaTime() * amount * camera.zoom, 0);
	}

	public void goRight(float amount) {
		camera.translate(1 * Gdx.graphics.getDeltaTime() * amount * camera.zoom, 0);
	}

	public void goUp(float amount) {
		camera.translate(0, 1 * Gdx.graphics.getDeltaTime() * amount * camera.zoom);
	}

	public void goDown(float amount) {
		camera.translate(0, -1 * Gdx.graphics.getDeltaTime() * amount * camera.zoom);
	}

	public void resetZoom() {
		camera.zoom = 1;
	}

	@Override
	public void addNewPlayer(float x, float y, String name) {
		enemyList.add(new Enemy(x, y, name));
	}

	@Override
	public void updatePoisiton(PositionMessage pm) {
		enemyList.stream().filter(e -> e.getName().equals(pm.name)).findFirst().ifPresent((e) -> {
			e.setX(pm.x);
			e.setY(pm.y);
		});

	}

	@Override
	public void updateCurrentPlayers(GetCurrentPlayersMessage gcp) {
		Set<String> enemySet = enemyList.stream().map(e -> e.getName()).collect(Collectors.toSet());
		gcp.spList.forEach((k, v) -> {
			if (!enemySet.contains(k)) {
				enemyList.add(new Enemy(v.getX(), v.getY(), v.getName()));
			}
		});
	}

	@Override
	public void removePlayer(String name) {
		enemyList.stream().filter(e -> e.getName().equals(name)).findFirst().ifPresent(e -> enemyList.remove(e));
	}

	public void shoot() {
		player.shoot();
	}

	@Override
	public void shootMessage(String name) {

		enemyList.stream().filter(e -> e.getName().equals(name)).findFirst().ifPresent(e -> e.shoot());
	}
}
