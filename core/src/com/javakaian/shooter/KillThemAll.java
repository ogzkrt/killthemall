package com.javakaian.shooter;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.javakaian.shooter.shapes.Bullet;
import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;

public class KillThemAll extends ApplicationAdapter implements NetworkEvents {

	private OrthographicCamera camera;

	private Player player;
	private List<Player> playerSet;
	private List<Enemy> enemies;
	private List<Bullet> bullets;

	private ShapeRenderer sr;

	private OClient myclient;

	private float angle = 90;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Gdx.input.setInputProcessor(new PlayStateInput(this));

		sr = new ShapeRenderer();

		myclient = new OClient(this);

		player = new Player(140, 50, 50);

		LoginMessage m = new LoginMessage();
		m.x = player.getPosition().x;
		m.y = player.getPosition().y;
		myclient.getClient().sendTCP(m);

		playerSet = new ArrayList<Player>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();

	}

	@Override
	public void render() {

		sr.setProjectionMatrix(camera.combined);
		camera.update();

		ScreenUtils.clear(0, 0, 0, 1);

		sr.begin(ShapeType.Line);
		// manyShapes.render(sr);
		// manyShapes.update(Gdx.graphics.getDeltaTime());

		player.render(sr);
		playerSet.forEach(p -> {
			p.render(sr);
		});
		enemies.stream().forEach(e -> e.render(sr));

		bullets.stream().forEach(b -> b.render(sr));

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {

			Vector3 up = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			Vector2 pos = new Vector2(player.getPosition().x + 25, player.getPosition().y + 25);
			Vector2 mouse = new Vector2(up.x, up.y);

			angle = (float) (8 * Math.PI / 4 - mouse.sub(pos).angleRad());
			int r = 20;
			sr.line(player.getPosition().x + 25, player.getPosition().y + 25, up.x, up.y);

		} else {
			angle = (float) (Math.PI / 2);
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
		m.id = player.getId();
		myclient.getClient().sendTCP(m);

	}

	private void processInputs() {

		PositionMessage p = new PositionMessage();
		p.id = player.getId();

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
	public void addNewPlayer(float x, float y, int id) {
		this.player = new Player(x, y, 50);
		this.player.setId(id);
	}

	@Override
	public void removePlayer(int id) {
	}

	@Override
	public void gwmReceived(GameWorldMessage gwm) {

		if (this.player.getId() == -1)
			return;

		int[] temp = gwm.enemies;

		List<Enemy> elist = new ArrayList<Enemy>();
		for (int i = 0; i < temp.length / 2; i++) {

			int x = temp[i * 2];
			int y = temp[i * 2 + 1];

			Enemy e = new Enemy(x, y, 10);
			elist.add(e);

		}

		this.enemies = elist;

		int[] tb = gwm.bullets;

		List<Bullet> blist = new ArrayList<Bullet>();
		for (int i = 0; i < tb.length / 2; i++) {
			float x = tb[i * 2];
			float y = tb[i * 2 + 1];

			Bullet b = new Bullet(x, y, 10, 0);

			blist.add(b);
		}
		this.bullets = blist;

		int[] tp = gwm.players;
		List<Player> plist = new ArrayList<Player>();
		for (int i = 0; i < tp.length / 3; i++) {

			int x = tp[i * 3];
			int y = tp[i * 3 + 1];
			int id = tp[i * 3 + 2];
			Player p = new Player(x, y, 50);
			p.setId(id);

			if (player.getId() != -1 && (id == player.getId())) {
				player = p;
			} else {
				plist.add(p);
			}

		}
		this.playerSet = plist;

	}

	public void shoot() {
		ShootMessage m = new ShootMessage();
		m.id = player.getId();
		m.angleDeg = angle;
		myclient.getClient().sendUDP(m);
	}

}
