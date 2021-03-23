package com.javakaian.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javakaian.network.OClient;
import com.javakaian.network.messages.GameWorldMessage;
import com.javakaian.network.messages.LoginMessage;
import com.javakaian.network.messages.LogoutMessage;
import com.javakaian.network.messages.PlayerDied;
import com.javakaian.network.messages.PositionMessage;
import com.javakaian.network.messages.PositionMessage.DIRECTION;
import com.javakaian.network.messages.ShootMessage;
import com.javakaian.shooter.OMessageListener;
import com.javakaian.shooter.input.PlayStateInput;
import com.javakaian.shooter.shapes.Bullet;
import com.javakaian.shooter.shapes.Enemy;
import com.javakaian.shooter.shapes.Player;
import com.javakaian.shooter.utils.GameConstants;
import com.javakaian.shooter.utils.GameUtils;
import com.javakaian.shooter.utils.OMessageParser;

/**
 * This is the state where gameplay happens.
 * 
 * @author oguz
 *
 */
public class PlayState extends State implements OMessageListener {

	private Player player;
	private List<Player> players;
	private List<Enemy> enemies;
	private List<Bullet> bullets;

	private float angle = 90;

	private OClient myclient;

	private BitmapFont healthFont;

	public PlayState(StateController sc) {
		super(sc);

		init();
		ip = new PlayStateInput(this);
		healthFont = GameUtils.generateBitmapFont(20, Color.WHITE);
	}

	private void init() {

		myclient = new OClient(sc.getInetAddress(), this);

		players = new ArrayList<Player>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();

		LoginMessage m = new LoginMessage();
		m.x = new Random().nextInt(GameConstants.SCREEN_WIDTH);
		m.y = new Random().nextInt(GameConstants.SCREEN_HEIGHT);
		myclient.sendTCP(m);

	}

	@Override
	public void render() {

		sr.setProjectionMatrix(camera.combined);
		camera.update();

		ScreenUtils.clear(0, 0, 0, 1);

		sr.begin(ShapeType.Line);

		players.forEach(p -> p.render(sr));
		enemies.forEach(e -> e.render(sr));
		bullets.forEach(b -> b.render(sr));

		// if player is null return and end the shaperenderer.
		if (player == null) {
			sr.end();
			return;
		}

		player.render(sr);
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {

			Vector3 up = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			Vector2 pos = new Vector2(player.getPosition().x + 25, player.getPosition().y + 25);
			Vector2 mouse = new Vector2(up.x, up.y);

			angle = (float) (8 * Math.PI / 4 - mouse.sub(pos).angleRad());
			sr.line(player.getPosition().x + 25, player.getPosition().y + 25, up.x, up.y);

		} else {
			angle = (float) (Math.PI / 2);

		}
		float lerp = 0.05f;

		camera.position.x += (player.getPosition().x - camera.position.x) * lerp;
		camera.position.y += (player.getPosition().y - camera.position.y) * lerp;

		sr.end();

		sb.begin();

		GameUtils.renderTopRight("HEALTH: " + String.valueOf(player.getHealth()), sb, healthFont);

		sb.end();

	}

	@Override
	public void update(float deltaTime) {
		if (player == null)
			return;
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

	/***/
	public void shoot() {

		ShootMessage m = new ShootMessage();
		m.id = player.getId();
		m.angleDeg = angle;
		myclient.sendUDP(m);

	}

	/**
	 * Whenever player press a button, this creates necessary position message and
	 * sends it to the server.
	 */
	private void processInputs() {

		PositionMessage p = new PositionMessage();
		p.id = player.getId();
		if (Gdx.input.isKeyPressed(Keys.S)) {
			p.direction = DIRECTION.DOWN;
			myclient.sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			p.direction = DIRECTION.UP;
			myclient.sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			p.direction = DIRECTION.LEFT;
			myclient.sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			p.direction = DIRECTION.RIGHT;
			myclient.sendUDP(p);
		}

	}

	@Override
	public void loginReceieved(LoginMessage m) {

		System.out.println("LOGIN RECEIVED BY CLIENT");
		player = new Player(m.x, m.y, 50);
		player.setId(m.id);
	}

	@Override
	public void logoutReceieved(LogoutMessage m) {

	}

	@Override
	public void playerDiedReceived(PlayerDied m) {
		if (player.getId() != m.id)
			return;

		LogoutMessage mm = new LogoutMessage();
		mm.id = player.getId();
		myclient.sendTCP(mm);
		myclient.close();
		this.getSc().setState(StateEnum.GameOverState);

	}

	@Override
	public void gwmReceived(GameWorldMessage m) {

		enemies = OMessageParser.getEnemiesFromGWM(m);
		bullets = OMessageParser.getBulletsFromGWM(m);

		players = OMessageParser.getPlayersFromGWM(m);

		// Find yourself.
		players.stream().filter(p -> p.getId() == player.getId()).findFirst().ifPresent(p -> player = p);
		// Remove yourself from playerlist.
		players.removeIf(p -> p.getId() == player.getId());

	}

	public void restart() {
		init();
	}

	@Override
	public void dispose() {

		LogoutMessage m = new LogoutMessage();
		m.id = player.getId();
		myclient.sendTCP(m);
	}

}
