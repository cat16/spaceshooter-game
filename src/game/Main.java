package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.entities.AIShip;
import game.entities.Asteroid;
import game.entities.Beam;
import game.entities.Board;
import game.entities.Button;
import game.entities.Cannon;
import game.entities.Chunk;
import game.entities.Entity;
import game.entities.Item;
import game.entities.Killable;
import game.entities.Laser;
import game.entities.Moveable;
import game.entities.Projectile;
import game.entities.Ship;
import game.entities.Star;
import game.entities.Text;
import game.scores.Team;
import game.tools.Rectangle;

@SuppressWarnings("serial")
public class Main extends JPanel implements MouseListener, MouseWheelListener {

	private JFrame frame = new JFrame("Game Test");
	public static final int framex = 1200;
	public static final int framey = 1000;
	public static float defaultsize = 100;
	public static double scale = 1;
	public final double fscale = 2000;

	private static boolean running = true;

	private static int FPS = 100;
	public static final int UPS = 100;
	public static boolean paused = true;
	public static boolean pause = false;

	public static boolean inGame = false;

	public static boolean hitboxed = false;
	public static boolean hitbox = false;
	public static boolean velo = false;
	public static boolean veloed = false;

	public static ArrayList<String> keys = new ArrayList<String>();
	private GameKeyListener listener;

	public static BufferedImage noimage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
	public static BufferedImage laser;
	public static BufferedImage button_pressed;
	public static BufferedImage button_highlighted;
	public static BufferedImage button_unpressed;
	public static BufferedImage board0;
	public static BufferedImage asteroid;
	public static BufferedImage ship;
	public static BufferedImage cannon;

	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Text> text = new ArrayList<Text>();
	public static ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	public static ArrayList<Button> buttons = new ArrayList<Button>();
	public static ArrayList<Board> boards = new ArrayList<Board>();
	public static ArrayList<Item> items = new ArrayList<Item>();

	public static double mousex = 0;
	public static double mousey = 0;
	public static double mousexn = 0;
	public static double mouseyn = 0;

	public static Button mouseb;

	public static double xoffset = 0;
	public static double yoffset = 0;

	public static int starspc = 10;

	public static int alcount = 1;

	public static Ship playerLoad;
	public static Ship player;
	public static AIShip enemyLoad;

	public void release(int m) {
		switch (m) {
		case 1:
			if (!paused) {
				player.setAttack(false);
			}
			if (mouseb.getText() != null && !mouseb.getText().getText().equals("label")) {
				mouseb.setState(Button.HIGHLIGHTED);
				switch (mouseb.getText().getText()) {
				case "Start Game":
					inGame = true;
					paused = false;
					mouseb.setHidden(true);
					break;
				case "Restart":
					restart();
					inGame = false;
					paused = true;
					mouseb.setHidden(true);
					break;
				case "Respawn":
					paused = false;
					respawn();
					mouseb.setHidden(true);
					break;
				case "Continue Game":
					paused = false;
					mouseb.setHidden(true);
					break;
				}
			}
			break;
		case 2:
			break;
		case 3:
			if (player.getBeams().size() == 1) { // look at the comment below
				player.getBeams().remove(0);
			}
			break;
		}
	}

	public void press(int m) {
		switch (m) {
		case 1:
			if (!paused) {
				if (mouseb.getText() == null || mouseb.getText().getText().equals("label")) {
					player.setAttack(true);
				}
			}
			mouseb.setState(Button.PRESSED);
			break;
		case 2:

			break;
		case 3:
			if (!paused && inGame) {
				if (player.getBeams().size() == 0) { // this is a crappy patch for switching to arraylists because I'm
														// too lazy right now
					player.getBeams().add(new Beam(player.getX(), player.getY(), mousex, mousey, 0.2, laser));
				}
			}
			break;
		}
	}

	public void paused() {

	}

	public void buttonUpdate() {
		for (int i = 0; i < boards.size(); i++) {
			Board b = boards.get(i);
			b.setHidden(!paused);
			b.setX(player.getX());
			b.setY(player.getY() + 100);
		}
		for (int s = 0; s < buttons.size(); s++) {
			Button b = (Button) buttons.get(s);
			switch (b.getText().getText()) {
			case "Restart":
				if (inGame && (player.getHealth() <= 0 || paused)) {
					b.setX(player.getX());
					b.setY(player.getY() - 0);
					b.setHidden(false);
				} else {
					b.setHidden(true);
				}
				break;
			case "Respawn":
				if (inGame && (player.getHealth() <= 0 || paused)) {
					b.setX(player.getX());
					b.setY(player.getY() + 100);
					b.setHidden(false);
				} else {
					b.setHidden(true);
				}
				break;
			case "Continue Game":
				if (inGame && paused && player.getHealth() > 0) {
					b.setX(player.getX());
					b.setY(player.getY() - 100);
					b.setHidden(false);
				} else {
					b.setHidden(true);
				}
				break;
			case "Start Game":
				b.setX(player.getX());
				b.setY(player.getY() - 100);
				if (inGame) {
					b.setHidden(true);
				} else {
					b.setHidden(false);
				}
				break;
			}
		}
	}

	public void unpaused() {
		if (keys.contains("d") && !keys.contains("a")) {
			if (!(player.getRVel() + 0.02 >= player.getMaxrvel())) {
				player.setRVel(player.getRVel() + 0.02);
			}
		} else if (keys.contains("a") && !keys.contains("d")) {
			if (!(player.getRVel() - 0.02 <= -player.getMaxrvel())) {
				player.setRVel(player.getRVel() - 0.02);
			}
		} else {
			if (keys.contains("shift")) {
				if (player.getRVel() > 0.2) {
					player.setRVel(player.getRVel() - 0.2);
				} else if (player.getRVel() < -0.2) {
					player.setRVel(player.getRVel() + 0.2);
				} else {
					player.setRVel(0);
				}
			}
		}
		player.setAngle(player.getAngle() + Math.toRadians(player.getRVel()));
		double pAngle = player.getAngle();
		if (keys.contains("w") && !keys.contains("s")) {
			player.setXVel(player.getXVel() + (0.1 * Math.cos(pAngle)));
			player.setYVel(player.getYVel() + (0.1 * Math.sin(pAngle)));
		} else if (keys.contains("s") && !keys.contains("w")) {
			player.setXVel(player.getXVel() - (0.1 * Math.cos(pAngle)));
			player.setYVel(player.getYVel() - (0.1 * Math.sin(pAngle)));
		} else {
			if (keys.contains("shift")) {
				if (player.getVel() > 0.5) {
					player.setXVel(player.getXVel() - (0.1 * Math.cos(Math.atan2(player.getYVel(), player.getXVel()))));
					player.setYVel(player.getYVel() - (0.1 * Math.sin(Math.atan2(player.getYVel(), player.getXVel()))));
				} else {
					player.setXVel(0);
					player.setYVel(0);
				}
			} else {

			}
		}
		Chunk c3 = player.getChunk();
		for (int x = -2; x <= 2; x++) {
			for (int y = -2; y <= 2; y++) {
				Chunk n = new Chunk(c3.getX() + (c3.getSide() * x), c3.getY() + (c3.getSide() * y), c3.getSide());
				boolean flag = true;
				for (int s2 = 0; s2 < chunks.size(); s2++) {
					Chunk c2 = chunks.get(s2);
					if (c2.getX() == n.getX() && c2.getY() == n.getY()) {
						flag = false;
						break;
					}
				}
				if (flag) {
					for (int a = starspc; a > 0; a--) {
						n.getStars().add(new Star(random(5, n.getSide() - 5), random(5, n.getSide() - 5), 5));
					}
					chunks.add(n);
					if (random(1, 4) == 1) {
						int r = random(1, 5);
						entities.add(new Asteroid(n.getX() + random(0, n.getSide()), n.getY() + random(0, n.getSide()),
								0, 10, r * 100, random(-2, 2), r, asteroid));
					}
				}
			}
		}
		for (int s = 0; s < player.getBeams().size(); s++) {
			Beam b = player.getBeams().get(s);
			b.update(player.getX(), player.getY(), mousex, mousey);
			for (int s2 = 0; s2 < entities.size(); s2++) {
				if (entities.get(s2) instanceof Asteroid) {
					Asteroid a = (Asteroid) entities.get(s2);
					BufferedImage image = a.getImage();
					double scale = a.getScale();
					if (image.getWidth() > image.getHeight()) {
						scale = (float) ((defaultsize * a.getScale()) / image.getWidth());
					} else {
						scale = (float) ((defaultsize * a.getScale()) / image.getHeight());
					}
					double newW = (image.getWidth() * scale);
					double newH = (image.getHeight() * scale);
					double newX = (a.getX()) - (newW / 2);
					double newY = (a.getY()) - (newH / 2);
					if (b.getXd() > newX && b.getXd() < newX + newW && b.getYd() > newY && b.getYd() < newY + newH) {
						if (a.getHealth() > 30) {
							a.setHealth(a.getHealth() - 0.5);
						} else {
							a.setHealth(0);
						}
						player.setBeam(player.getBeam() + 1);
						if (player.getBeam() >= 200) {
							player.getCargo().add(items.get(0));
							player.setBeam(0);
						}
					}
				}
			}
		}
		if (random(0, 1000) == 1) {
			AIShip add = new AIShip(enemyLoad);
			add.getTeam().setAlliance(3);
			add.setX(random(-1000, 1000));
			add.setY(random(-1000, 1000));
			for (int cs = 0; cs < add.getCannons().size(); cs++) {
				Cannon c = add.getCannons().get(cs);
				c.setOwner(add.getTeam().getAlliance());
			}
			entities.add(add);
		}
		for (int s = 0; s < entities.size(); s++) {
			Entity e = entities.get(s);
			if (e instanceof Moveable) {
				Moveable m = (Moveable) e;

				m.setX(m.getX() + m.getXVel());
				m.setY(m.getY() + m.getYVel());

				if (m instanceof Ship) {
					Ship es = (Ship) m;
					if (es instanceof AIShip) {
						AIShip ai = (AIShip) es;
						ArrayList<Killable> ks = new ArrayList<Killable>();
						for (int s2 = 0; s2 < entities.size(); s2++) {
							if (entities.get(s2) instanceof Killable) {
								Killable k = (Killable) entities.get(s2);
								if (k.getTeam().getAlliance() != -1
										&& k.getTeam().getAlliance() != ai.getTeam().getAlliance()
										&& distance(ai, k) <= ai.getRange()) {
									ks.add(k);
								}
							}
						}
						ai.setSlow(true);
						if (ks.size() > 0) {
							Killable target = closest(ai, ks);
							for (int s3 = 0; s3 < ai.getCannons().size(); s3++) {
								Cannon c = ai.getCannons().get(s3);
								c.setAngle(
										rotate(ai.getX(), ai.getY(), target.getX(), target.getY()) + c.getBaseAngle());
							}
							if (distance(ai, target) <= 500) {
								ai.setXVel(ai.getXVel() - (0.1
										* Math.cos(Math.atan2(target.getY() - ai.getY(), target.getX() - ai.getX()))));
								ai.setYVel(ai.getYVel() - (0.1
										* Math.sin(Math.atan2(target.getY() - ai.getY(), target.getX() - ai.getX()))));
								ai.setSlow(false);
							} else if (distance(ai, target) >= (ai.getRange() - ai.getRange() / 2)) {
								ai.setXVel(ai.getXVel() - (0.1
										* Math.cos(Math.atan2(ai.getY() - target.getY(), ai.getX() - target.getX()))));
								ai.setYVel(ai.getYVel() - (0.1
										* Math.sin(Math.atan2(ai.getY() - target.getY(), ai.getX() - target.getX()))));
								ai.setSlow(false);
							} else {
								ai.setSlow(true);
							}
							ai.setAngle(Math.atan2(target.getY() - ai.getY(), target.getX() - ai.getX()));
							ai.setAttack(true);
						} else {
							ai.setAttack(false);
						}
						if (ai.isSlow()) {
							ai.setXVel(ai.getXVel() - (0.1 * Math.cos(Math.atan2(ai.getYVel(), ai.getXVel()))));
							ai.setYVel(ai.getYVel() - (0.1 * Math.sin(Math.atan2(ai.getYVel(), ai.getXVel()))));
						}
					}
					for (int s2 = 0; s2 < es.getCannons().size(); s2++) {
						Cannon c = es.getCannons().get(s2);
						c.setMove(es);
						c.setCooldown(c.getCooldown() - 1);
						Projectile p = c.getType();
						if (veloed) {
							p.setXVel(Math.cos(c.getAngle()) * p.getVel2() + es.getXVel());
							p.setYVel(Math.sin(c.getAngle()) * p.getVel2() + es.getYVel());
						} else {
							p.setXVel(Math.cos(c.getAngle()) * p.getVel2());
							p.setYVel(Math.sin(c.getAngle()) * p.getVel2());
						}
						if (es.isAttack()) {
							if (c.getCooldown() <= 0) {
								entities.add(c.createProjectile(laser));
								c.setCooldown(c.getStartcool());
							}
						}
					}
				}
				if (m instanceof Projectile) {
					Projectile p = (Projectile) m;
					if (p.getTime() <= 0) {
						entities.remove(p);
					} else {
						p.setTime(p.getTime() - 0.1);
						for (int s2 = 0; s2 < entities.size(); s2++) {
							if (entities.get(s2) instanceof Killable) {
								Killable k = (Killable) entities.get(s2);
								if (overlaps(p.getBounds(), k.getBounds())) {
									if (p.getOwner() != k.getTeam().getAlliance()) {
										double d = p.getDamage();
										double h = k.getHealth();
										p.setDamage(d - h);
										k.setHealth(h - d);
										if (p.getDamage() < 0) {
											p.setDamage(0);
										}
										text.add(new Text(Double.toString(d - p.getDamage()), k.getX(), k.getY(),
												"damage", 50, Color.red, 30));
									}
								}
							}
							if (entities.get(s2) instanceof Projectile) {
								Projectile p2 = (Projectile) entities.get(s2);
								if (overlaps(p.getBounds(), p2.getBounds())) {
									if (p.getOwner() != p2.getOwner()) {
										double d = p2.getDamage();
										double h = p2.getDamage();
										p2.setDamage(d - h);
										p2.setDamage(h - d);
										if (p.getDamage() < 0) {
											p.setDamage(0);
										}
										text.add(new Text(Double.toString(d - p.getDamage()), p2.getX(), p2.getY(),
												"damage", 50, Color.gray, 30));
									}
								}
							}
						}
					}
				}
				if (m instanceof Killable) {
					Killable k = (Killable) m;
					if (k.getHealth() <= 0) {
						entities.remove(k);
					} else if (k instanceof Asteroid) {
						Asteroid a = (Asteroid) k;
						a.setScale(a.getHealth() / 50);
					}
				}
				if (m instanceof Projectile) {
					Projectile p = (Projectile) m;
					if (p.getDamage() <= 0) {
						entities.remove(p);
					}
				}
			}
		}
		for (int s = 0; s < chunks.size(); s++) {
			Chunk c = chunks.get(s);
			if (overlaps(player.getBounds(), c.getRect())) {
				player.setChunk(c);
			}
		}
		for (int s = 0; s < text.size(); s++) {
			Text t = text.get(s);
			if (t.getType().equals("damage")) {
				t.setY(t.getY() - 1);
				t.setAlpha(t.getTime() / t.getAlphaTime());
				t.setTime(t.getTime() - 1);
				if (t.getTime() == 0) {
					text.remove(t);
				}
			}
		}
	}

	public void second() {

	}

	public static void main(String[] args) {
		new Main().start();
	}

	public Main() {
		System.out.println("Loading...");
		frame.setSize(framex, framey);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);

		load();

		frame.setVisible(true);
	}

	public void start() {
		long lastUpdateTime = System.currentTimeMillis();
		long lastFrameTime = System.currentTimeMillis();
		long lastSecond = System.currentTimeMillis();
		while (running) {
			long startTime = System.currentTimeMillis();
			if (startTime - lastUpdateTime >= 1000 / UPS) {
				xoffset = -player.getX();
				yoffset = -player.getY();
				if (keys.contains("esc") && pause && inGame) {
					pause = false;
					paused = !paused;
				} else if (!keys.contains("esc")) {
					pause = true;
				}
				if (!paused) {
					unpaused();
				} else {
					paused();
				}

				buttonUpdate();

				if (keys.contains("p")) {
					BufferedImage bi = new BufferedImage(frame.getWidth(), frame.getHeight(),
							BufferedImage.TYPE_INT_ARGB);
					frame.paint(bi.getGraphics());
					String timeStamp = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss")
							.format(Calendar.getInstance().getTime());
					File outputfile = new File("screenshots/" + timeStamp + ".png");
					try {
						ImageIO.write(bi, "png", outputfile);
					} catch (IOException e1) {
						System.out.println("Screenshot " + timeStamp + " didn't save!");
					}
				}

				if (keys.contains("h") && hitbox) {
					hitbox = false;
					hitboxed = !hitboxed;
				} else if (!keys.contains("h")) {
					hitbox = true;
				}
				if (keys.contains("v") && velo) {
					velo = false;
					veloed = !veloed;
				} else if (!keys.contains("v")) {
					velo = true;
				}
				mouseb = new Button();
				for (int s = 0; s < buttons.size(); s++) {
					Button b = (Button) buttons.get(s);
					if (!b.isHidden()) {
						BufferedImage image = b.getImage();
						double scale = b.getScale();
						if (image.getWidth() > image.getHeight()) {
							scale = (float) ((defaultsize * b.getScale()) / image.getWidth());
						} else {
							scale = (float) ((defaultsize * b.getScale()) / image.getHeight());
						}
						double newW = (image.getWidth() * scale);
						double newH = (image.getHeight() * scale);
						double newX = (b.getX()) - (newW / 2);
						double newY = (b.getY()) - (newH / 2);
						if (mousexn > newX && mousexn < newX + newW && mouseyn > newY && mouseyn < newY + newH) {
							if (b.getState() != Button.PRESSED) {
								b.setState(Button.HIGHLIGHTED);
							}
							mouseb = b;
						} else {
							b.setState(Button.UNPRESSED);
						}
					}
					b.update();
				}

				AffineTransform t = new AffineTransform();
				t.translate(framex / 2, framey / 2);
				t.scale(scale, scale);
				mousex = (MouseInfo.getPointerInfo().getLocation().x - frame.getX() - 3 - framex / 2) / scale;
				mousey = (MouseInfo.getPointerInfo().getLocation().y - frame.getY() - 27 - framey / 2) / scale;
				mousex -= xoffset;
				mousey -= yoffset;
				mousexn = MouseInfo.getPointerInfo().getLocation().x - frame.getX() - 3 - framex / 2;
				mouseyn = MouseInfo.getPointerInfo().getLocation().y - frame.getY() - 27 - framey / 2;
				mousexn -= xoffset;
				mouseyn -= yoffset;
				if (!paused) {
					for (int s = 0; s < player.getCannons().size(); s++) {
						Cannon c = player.getCannons().get(s);
						c.setAngle(rotate(player.getX(), player.getY(), mousex, mousey) + c.getBaseAngle());
					}
				}

				lastUpdateTime = startTime;
			}
			if (startTime - lastSecond >= 1000) {
				second();
				lastSecond = startTime;
			}
			if (startTime - lastFrameTime >= 1000 / FPS) {
				repaint();
				lastFrameTime = startTime;
			}
			long timeTaken = System.currentTimeMillis() - startTime;
			if (timeTaken >= 10) {
				System.out.println("Updating took " + timeTaken + " ms!");
			}
		}
	}

	public void load() {
		GameKeyListener.setSpecials();
		listener = new GameKeyListener();
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(listener);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		loadtextures();
		playerLoad = new Ship(0, 0, 0, new ArrayList<Cannon>(), 2, 100, new Team(Color.blue, 2), 2);
		enemyLoad = new AIShip(0, 0, 0, new ArrayList<Cannon>(), 2, 100, 2000, new Team(Color.red, 2), 2);
		enemyLoad.addCannon(new Cannon(0, 0, Math.PI, new Laser(15, 15, 15, 0.2, laser), 50, 1, cannon));
		enemyLoad.setImage(ship);
		restart();
		buttons.add(new Button(0, 0, 0, "Start Game", false, 0.5, 5));
		buttons.add(new Button(0, 0, 0, "Respawn", true, 0.5, 5));
		buttons.add(new Button(0, 0, 0, "Restart", true, 0.5, 5));
		buttons.add(new Button(0, 0, 0, "Continue Game", true, 0.5, 5));
		boards.add(new Board(100, 100, false, 5, board0));
		items.add(new Item("Iron", false, 1, "/textures/asteroid.png"));
		System.out.println("Loading complete!");
	}

	public void loadtextures() {
		Graphics2D g2 = noimage.createGraphics();
		g2.setColor(Color.cyan);
		g2.fillRect(0, 0, 100, 100);
		laser = getImage("/textures/laser.png");
		button_unpressed = getImage("/textures/button0.png");
		button_highlighted = getImage("/textures/button1.png");
		button_pressed = getImage("/textures/button2.png");
		board0 = getImage("/textures/board0.png");
		frame.setIconImage(getImage("/textures/icon1.png"));
		asteroid = getImage("/textures/asteroid.png");
		ship = getImage("/textures/spaceship.png");
		cannon = getImage("/textures/cannon.png");
		for (int s = items.size(); s > 0; s--) {
			Item i = items.get(s);
			i.setImage(getImage(i.getLocation()));
		}
	}

	public void restart() {
		text.clear();
		chunks.clear();
		Chunk n = new Chunk(0, 0, framex);
		for (int a = starspc; a > 0; a--) {
			n.getStars().add(new Star(random(5, n.getSide() - 5), random(5, n.getSide() - 5), 5));
		}
		chunks.add(n);
		entities.clear();
		respawn();
	}

	public void respawn() {
		entities.remove(player);
		player = new Ship(playerLoad);
		player.getCannons().clear();
		player.addCannon(new Cannon(0, 0, Math.PI, new Laser(15, 15, 2, 0.2, laser), 10, 1, cannon));
		player.setImage(ship);
		player.setChunk(chunks.get(0));
		entities.add(player);
		scale = 1;
	}

	public void paint(Graphics g) {

		long now = System.currentTimeMillis();

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.black);
		g2.fillRect(0, 0, framex, framey);
		AffineTransform old = new AffineTransform();
		AffineTransform old2 = new AffineTransform();
		AffineTransform old3 = new AffineTransform();
		old.translate(framex / 2, framey / 2);
		old.scale(scale, scale);
		old.translate(xoffset, yoffset);
		old2.translate(framex / 2, framey / 2);
		old2.translate(xoffset, yoffset);
		double scale;
		double newW;
		double newH;
		double newX;
		double newY;
		AffineTransform trans = new AffineTransform(old);
		BufferedImage image = noimage;
		// chunks
		for (int s = 0; s < chunks.size(); s++) {
			Chunk c = chunks.get(s);
			if (hitboxed) {
				g2.setColor(Color.white);
				g2.drawRect(c.getX(), c.getY(), c.getSide(), c.getSide());
			}
			for (int s2 = 0; s2 < c.getStars().size(); s2++) {
				Star a = c.getStars().get(s2);
				final int STAR_DIST_FACTOR = 5;
				if (c.getX() + a.getX() + xoffset / STAR_DIST_FACTOR + fscale > 0 // idk where this number came from tbh
																					// (this isn't original code)
						&& c.getX() + a.getX() + xoffset / STAR_DIST_FACTOR - fscale < framex
						&& c.getY() + a.getY() + yoffset / STAR_DIST_FACTOR + fscale > 0
						&& c.getY() + a.getY() + yoffset / STAR_DIST_FACTOR - fscale < framey) {
					old3 = new AffineTransform();
					old3.translate(framex / 2, framey / 2);
					old3.scale(Main.scale, Main.scale);
					old3.translate(xoffset / STAR_DIST_FACTOR, yoffset / STAR_DIST_FACTOR);
					g2.setTransform(old3);
					g2.setColor(Color.white);
					g2.fillOval((int) (c.getX() + a.getX()), (int) (c.getY() + a.getY()), (int) (a.getSize()),
							(int) (a.getSize()));
				}
			}
			g2.setTransform(old);
		}
		// entities
		for (int s = 0; s < entities.size(); s++) {
			Entity e = entities.get(s);
			image = e.getImage();
			if (image == null) {
				image = noimage;
			}
			if (image.getWidth() > image.getHeight()) {
				scale = (float) ((defaultsize * e.getScale()) / image.getWidth());
			} else {
				scale = (float) ((defaultsize * e.getScale()) / image.getHeight());
			}
			newW = (image.getWidth() * scale);
			newH = (image.getHeight() * scale);
			newX = (e.getX()) - (newW / 2);
			newY = (e.getY()) - (newH / 2);
			if (newX + xoffset + fscale > 0 && newX + xoffset - fscale < framex && newY + yoffset + fscale > 0
					&& newY + yoffset - fscale < framey) {
				trans = new AffineTransform(old);
				trans.rotate(e.getAngle(), e.getX(), e.getY());
				g2.setTransform(trans);
				g2.drawImage(image, (int) newX, (int) newY, (int) newW, (int) newH, null);
				if (hitboxed) {

					g2.drawRect((int) newX, (int) newY, (int) newW, (int) newH);

					g2.setColor(Color.white);
					g2.setTransform(old);
					g2.drawRect((int) e.getBounds().getX(), (int) e.getBounds().getY(), (int) e.getBounds().getWidth(),
							(int) e.getBounds().getHeight());
					g2.setTransform(trans);
				}
				g2.setTransform(old);
				if (e instanceof Ship) {
					Ship es = (Ship) e;
					g2.setColor(Color.green);
					g2.fillRect((int) (es.getX() - es.getImage().getWidth() * es.getScale()), (int) (es.getY() - 100),
							(int) es.getHealth(), 5);
					for (int s2 = 0; s2 < es.getBeams().size(); s2++) {
						Beam b = es.getBeams().get(s2);
						if (b.getImage() == null) {
							image = noimage;
						} else {
							image = b.getImage();
						}
						if (image.getWidth() > image.getHeight()) {
							scale = (float) ((defaultsize * b.getScale()) / image.getWidth());
						} else {
							scale = (float) ((defaultsize * b.getScale()) / image.getHeight());
						}
						newW = b.getDistance();
						newH = (image.getHeight() * scale);
						newX = b.getX();
						newY = b.getY();
						trans = new AffineTransform(old);
						trans.rotate(b.getAngle() + Math.PI, b.getX(), b.getY());
						g2.setTransform(trans);
						g2.drawImage(image, (int) newX, (int) newY, (int) newW, (int) newH, null);
						g2.setTransform(old);
					}
					for (int s2 = 0; s2 < es.getCannons().size(); s2++) {
						Cannon c = es.getCannons().get(s2);
						if (c.getImage() == null) {
							image = noimage;
						} else {
							image = c.getImage();
						}
						if (image.getWidth() > image.getHeight()) {
							scale = (float) ((defaultsize * c.getScale()) / image.getWidth());
						} else {
							scale = (float) ((defaultsize * c.getScale()) / image.getHeight());
						}
						newW = (image.getWidth() * scale);
						newH = (image.getHeight() * scale);
						newX = (c.getX()) - (newW / 2);
						newY = (c.getY()) - (newH / 2);
						trans = new AffineTransform(old);
						trans.rotate(c.getAngle(), c.getX(), c.getY());
						g2.setTransform(trans);
						g2.drawImage(image, (int) newX, (int) newY, (int) newW, (int) newH, null);
						if (hitboxed) {

							g2.drawRect((int) newX, (int) newY, (int) newW, (int) newH);

							g2.setColor(Color.white);
							g2.setTransform(old);
							g2.drawRect((int) c.getBounds().getX(), (int) c.getBounds().getY(),
									(int) c.getBounds().getWidth(), (int) c.getBounds().getHeight());
							g2.setTransform(trans);
						}
						g2.setTransform(old);
					}
				}
			}
		}
		for (int s = 0; s < text.size(); s++) {
			Text t = text.get(s);
			newX = t.getX() - (int) t.getScale() * 3;
			newY = t.getY();
			if (newX + xoffset + fscale > 0 && newX + xoffset - fscale < framex && newY + yoffset + fscale > 0
					&& newY + yoffset - fscale < framey) {
				g2.setColor(t.getColor());
				g2.setFont(new Font("Monospaced", Font.PLAIN, (int) t.getScale()));
				g2.setComposite(makeComposite(t.getAlpha()));
				g2.drawString(t.getText(), (int) newX, (int) newY);
				g2.setComposite(makeComposite(1));
			}
		}
		old = old2;
		for (int s = 0; s < boards.size(); s++) {
			Board b = boards.get(s);
			if (!b.isHidden()) {
				image = b.getImage();
				if (image.getWidth() > image.getHeight()) {
					scale = (float) ((defaultsize * b.getScale()) / image.getWidth());
				} else {
					scale = (float) ((defaultsize * b.getScale()) / image.getHeight());
				}
				newW = (image.getWidth() * scale);
				newH = (image.getHeight() * scale);
				newX = (b.getX()) - (newW / 2);
				newY = (b.getY()) - (newH / 2);
				trans = new AffineTransform(old);
				trans.rotate(b.getAngle(), b.getX(), b.getY());
				g2.setTransform(trans);
				g2.drawImage(image, (int) newX, (int) newY, (int) newW, (int) newH, null);
				for (int s2 = 0; s2 < b.getButtons().size(); s2++) {
					Button b2 = b.getButtons().get(s2);
					image = b2.getImage();
					if (image.getWidth() > image.getHeight()) {
						scale = (float) ((defaultsize * b2.getScale()) / image.getWidth());
					} else {
						scale = (float) ((defaultsize * b2.getScale()) / image.getHeight());
					}
					newW = (image.getWidth() * scale);
					newH = (image.getHeight() * scale);
					newX = (b2.getX()) - (newW / 2);
					newY = (b2.getY()) - (newH / 2);
					trans = new AffineTransform(old);
					trans.rotate(b2.getAngle(), b2.getX(), b2.getY());
					g2.setTransform(trans);
					g2.setComposite(makeComposite(b2.getAlpha()));
					g2.drawImage(image, (int) newX, (int) newY, (int) newW, (int) newH, null);
					Text t = b2.getText();
					newX = t.getX() - (int) t.getScale() * 3;
					newY = t.getY() + (int) t.getScale() / 5;
					if (newX + xoffset + fscale > 0 && newX + xoffset - fscale < framex && newY + yoffset + fscale > 0
							&& newY + yoffset - fscale < framey) {
						g2.setColor(t.getColor());
						g2.setFont(new Font("Monospaced", Font.PLAIN, (int) t.getScale()));
						g2.setComposite(makeComposite(t.getAlpha()));
						g2.drawString(t.getText(), (int) newX, (int) newY);
						g2.setComposite(makeComposite(1));
					}
				}
			}
		}
		for (int s = 0; s < buttons.size(); s++) {
			Button b = buttons.get(s);
			if (!b.isHidden()) {
				image = b.getImage();
				if (image.getWidth() > image.getHeight()) {
					scale = (float) ((defaultsize * b.getScale()) / image.getWidth());
				} else {
					scale = (float) ((defaultsize * b.getScale()) / image.getHeight());
				}
				newW = (image.getWidth() * scale);
				newH = (image.getHeight() * scale);
				newX = (b.getX()) - (newW / 2);
				newY = (b.getY()) - (newH / 2);
				trans = new AffineTransform(old);
				trans.rotate(b.getAngle(), b.getX(), b.getY());
				g2.setTransform(trans);
				g2.setComposite(makeComposite(b.getAlpha()));
				g2.drawImage(image, (int) newX, (int) newY, (int) newW, (int) newH, null);
				Text t = b.getText();
				newX = t.getX() - (int) t.getScale() * 3;
				newY = t.getY() + (int) t.getScale() / 5;
				if (newX + xoffset + fscale > 0 && newX + xoffset - fscale < framex && newY + yoffset + fscale > 0
						&& newY + yoffset - fscale < framey) {
					g2.setColor(t.getColor());
					g2.setFont(new Font("Monospaced", Font.PLAIN, (int) t.getScale()));
					g2.setComposite(makeComposite(t.getAlpha()));
					g2.drawString(t.getText(), (int) newX, (int) newY);
					g2.setComposite(makeComposite(1));
				}
			}
		}
	}

	public AlphaComposite makeComposite(double alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, (float) alpha));
	}

	@Override
	public void mouseClicked(MouseEvent m) {
	}

	@Override
	public void mouseEntered(MouseEvent m) {
	}

	@Override
	public void mouseExited(MouseEvent m) {
	}

	@Override
	public void mousePressed(MouseEvent m) {
		press(m.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		release(m.getButton());
	}

	public static double rotate(double x1, double y1, double x2, double y2) {
		double xd = x1 - x2;
		double yd = y1 - y2;
		double rotation = Math.atan2(yd, xd);
		return rotation;
	}

	public BufferedImage getImage(String location) {
		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getResource(location));
			return image;
		} catch (Exception ex) {
			System.out.println("Couldn't find " + location + "!?");
			return noimage;
		}
	}

	public boolean overlaps(Rectangle r1, Rectangle r2) {
		double r1x1 = r1.getX();
		double r1x2 = r1.getX() + (r1.getWidth());
		double r1y1 = r1.getY();
		double r1y2 = r1.getY() + (r1.getHeight());
		double r2x1 = r2.getX();
		double r2x2 = r2.getX() + (r2.getWidth());
		double r2y1 = r2.getY();
		double r2y2 = r2.getY() + (r2.getHeight());
		if (r1x1 >= r2x2 || r1x2 <= r2x1 || r1y1 >= r2y2 || r1y2 <= r2y1) {
			return false;
		} else {
			return true;
		}
	}

	public static double pythag(double s1, double s2) {
		return Math.sqrt((s1 * s1) + (s2 * s2));
	}

	public double distance(Entity e1, Entity e2) {
		return pythag(e1.getX() - e2.getX(), e1.getY() - e2.getY());
	}

	public int random(int min, int max) {
		if (min > max) {
			return 0;
		} else {
			Random r = new Random();
			return r.nextInt(max - min) + min;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double r = e.getWheelRotation();
		if (inGame) {
			if (!(scale - (r / 5) < 0.4) && !(scale - (r / 5) > 1.6)) {
				scale = scale - (r / 10);
			}
		}
	}

	public static double least(ArrayList<Double> list, boolean greatest) {
		if (list.isEmpty()) {
			return 0;
		}
		double n = list.get(1);
		if (list.size() == 1) {
			return n;
		}
		list.remove(1);
		for (int l = 0; l < list.size(); l++) {
			double ln = list.get(l);
			if (greatest) {
				if (ln >= n) {
					n = ln;
				}
			} else {
				if (ln <= n) {
					n = ln;
				}
			}
		}
		return n;
	}

	public Killable closest(Killable e, ArrayList<Killable> list) {
		if (list.isEmpty()) {
			return e;
		}
		Killable n = list.get(0);
		if (list.size() == 1) {
			return n;
		}
		list.remove(1);
		for (int l = 0; l < list.size(); l++) {
			Killable nn = list.get(l);
			if (distance(e, nn) < distance(e, n)) {
				n = nn;
			}
		}
		return n;
	}

	public double add(double a, double b) {
		int r = ((int) (a * 100)) + ((int) (b * 100));
		return (double) r * 100;
	}
}
