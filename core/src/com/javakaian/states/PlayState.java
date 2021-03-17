package com.javakaian.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
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
import com.javakaian.shooter.NetworkEvents;
import com.javakaian.shooter.input.PlayStateInput;
import com.javakaian.shooter.shapes.Bullet;
import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;
import com.javakaian.shooter.utils.GameConstants;

public class PlayState extends State implements NetworkEvents {

	private Player player;
	private List<Player> playerSet;
	private List<Enemy> enemies;
	private List<Bullet> bullets;

	private float angle = 90;

	private OClient myclient;

	public PlayState(StateController sc) {
		super(sc);

		init();
		ip = new PlayStateInput(this);
	}

	private void init() {

		myclient = new OClient(sc.getIp(), this);

		player = new Player(new Random().nextInt(GameConstants.SCREEN_WIDTH),
				new Random().nextInt(GameConstants.SCREEN_HEIGHT), 50);

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

		player.render(sr);
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {

			Vector3 up = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			Vector2 pos = new Vector2(player.getPosition().x + 25, player.getPosition().y + 25);
			Vector2 mouse = new Vector2(up.x, up.y);

			angle = (float) (8 * Math.PI / 4 - mouse.sub(pos).angleRad());
			int r = 20;
			sr.line(player.getPosition().x + 25, player.getPosition().y + 25, up.x, up.y);

		} else {
			angle = (float) (Math.PI / 2);

			float lerp = 0.05f;

			camera.position.x += (player.getPosition().x - camera.position.x) * lerp;
			camera.position.y += (player.getPosition().y - camera.position.y) * lerp;

		}

		playerSet.forEach(p ->

		{
			p.render(sr);
		});
		enemies.stream().forEach(e -> e.render(sr));

		bullets.stream().forEach(b -> b.render(sr));

		sr.end();

	}

	@Override
	public void update(float deltaTime) {
		processInputs();
	}

	public void scrolled(float amountY) {
		if (amountY > 0) {
			camera.zoom += 0.2;
		} else {
			if (camera.zoom >= 0.4) {
				camera.zoom -= 0.2;
			}
		}
	}

	public void shoot() {

		ShootMessage m = new ShootMessage();
		m.id = player.getId();
		m.angleDeg = angle;
		myclient.getClient().sendUDP(m);

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

			int x = tp[i * 4];
			int y = tp[i * 4 + 1];
			int id = tp[i * 4 + 2];
			int health = tp[i * 4 + 3];
			Player p = new Player(x, y, 50);
			p.setHealth(health);
			p.setId(id);

			if (player != null && player.getId() != -1 && (id == player.getId())) {
				player = p;
			} else {
				plist.add(p);
			}

		}
		this.playerSet = plist;
	}

	private void processInputs() {

		PositionMessage p = new PositionMessage();
		p.id = player.getId();

		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A)) {
			p.direction = DIRECTION.UP_LEFT;
			myclient.getClient().sendUDP(p);
			return;
		}
		if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)) {
			p.direction = DIRECTION.UP_RIGHT;
			myclient.getClient().sendUDP(p);
			return;
		}
		if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A)) {
			p.direction = DIRECTION.DOWN_LEFT;
			myclient.getClient().sendUDP(p);
			return;
		}
		if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.D)) {
			p.direction = DIRECTION.DOWN_RIGHT;
			myclient.getClient().sendUDP(p);
			return;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			p.direction = DIRECTION.DOWN;
			myclient.getClient().sendUDP(p);
			return;
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			p.direction = DIRECTION.UP;
			myclient.getClient().sendUDP(p);
			return;
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			p.direction = DIRECTION.LEFT;
			myclient.getClient().sendUDP(p);
			return;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			p.direction = DIRECTION.RIGHT;
			myclient.getClient().sendUDP(p);
			return;
		}

	}

	@Override
	public void dispose() {

		LogoutMessage m = new LogoutMessage();
		m.id = player.getId();
		myclient.getClient().sendTCP(m);
	}

	@Override
	public void playerDied(int id) {

		if (player.getId() != id)
			return;

		LogoutMessage m = new LogoutMessage();
		m.id = player.getId();
		myclient.getClient().sendTCP(m);

		this.getSc().setState(StateEnum.GameOverState);

	}

	public void restart() {
		System.out.println("Game should be restarted from PLAYSTATE");
		init();
	}

}
