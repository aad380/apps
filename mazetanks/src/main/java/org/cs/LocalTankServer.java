package org.cs;

import java.lang.System;
import java.lang.Thread;
import java.util.Vector;
import java.util.Random;

public class LocalTankServer implements GameManager, Runnable {

	private final static int	TICK_TIME = 50,
					MAX_SHIELD_BONUSES = 2,
					MAX_BULLET_BONUSES = 5,
					MAX_BIG_BULLET_BONUSES = 3,
					MAX_NUKE_BULLET_BONUSES = 1,
					MAX_KILLED_BULLETS = 20,
					MAX_KILLED_TANKS = 20,
					MAX_KILLED_BONUSES = 20;

	private Vector		bullets = new Vector (),
				bonuses = new Vector (),
				tanks = new Vector ();
	private int		killedTanks,
				killedBullets,
				killedBonuses,
				shieldBonuses,
				bulletBonuses,
				bigBulletBonuses,
				nukeBulletBonuses,
				mazeWidth, mazeHeight, mazeLines;
	private Tank		mainTank;
	private Player		mainPlayer;
	private GameSpace	space;
	private Log		log;
	private Thread		thread;
	private ObjectGenerator	generator;
	private Random		random = new Random ();

	public LocalTankServer (
		Player player, GameSpace space, Log log,
		int mazeWidth, int mazeHeight, int mazeLines
	){
		this.mainPlayer = player;
		this.space = space;
		this.log = log;
		this.mazeWidth = mazeWidth;
		this.mazeHeight = mazeHeight;
		this.mazeLines = mazeLines;
		space.stop ();
	}

	public void start () {
		if (thread != null) {
			return;
		}
		tanks = new Vector ();
		bullets = new Vector ();
		bonuses = new Vector ();
		killedTanks = killedBullets = killedBonuses = 0;
		shieldBonuses = bulletBonuses = bigBulletBonuses = nukeBulletBonuses = 0;
		space.setMaze (new LocalGameMaze (new Maze (mazeWidth, mazeHeight, mazeLines), this, (GameSpaceCanvas) space));
		generator = new ObjectGenerator (this, space, true);
		mainTank = generator.makeTank (mainPlayer, 0);
		tanks.addElement (mainTank);
		space.setMainObject (mainTank);
		mainPlayer.deviceCreated (mainTank);
		space.start ();
		thread = new Thread (this);
		thread.start ();
	}

	public void stop () {
		if (thread == null) {
			return;
		}
		if (mainTank != null) {
			mainPlayer.deviceKilled ();
		}
		space.stop ();
		thread.stop ();
		thread = null;
	}

	public void addObject (GameObject object) {
		if (object.state == GameObject.KILLED_STATE) {
			return;
		} else if (object instanceof Bullet) {
			bullets.addElement (object);
		} else if (object instanceof Bonus) {
			bonuses.addElement (object);
		} else if (object instanceof Tank) {
			tanks.addElement (object);
		}
		space.addObject (object);
	} // addObject

	public void hideObject (GameObject object) {
		if (object.state != GameObject.KILLED_STATE) {
			space.hideObject (object);
		}
	} // hideObject

	public void showObject (GameObject object) {
		if (object.state != GameObject.KILLED_STATE) {
			space.showObject (object);
		}
	} // showObject

	public GameObject getObject (int x, int y) {
		return (GameObject) space.getObject (x, y);
	} // getObject

	public void stateChanged (GameObject object) {
		if (object.getState () == GameObject.KILLED_STATE) {
			killed (object);
		}
	} // stateChanged

	public void positionChanged (GameObject object, int oldX, int oldY) {
		space.positionChanged (object, oldX, oldY);
	} // positionChanged

	public void directionChanged (GameObject object, int oldDirection) {
		space.directionChanged (object, oldDirection);
	} // directionChanged

	public void killed (GameObject object) {
		space.deleteObject (object);
		if (object instanceof Bullet) {
			++ killedBullets;
		} else if (object instanceof BulletBonus) {
			-- bulletBonuses;
			++ killedBonuses;
		} else if (object instanceof BigBulletBonus) {
			-- bigBulletBonuses;
			++ killedBonuses;
		} else if (object instanceof NukeBulletBonus) {
			-- nukeBulletBonuses;
			++ killedBonuses;
		} else if (object instanceof ShieldBonus) {
			-- shieldBonuses;
			++ killedBonuses;
		} else if (object instanceof Tank) {
			++ killedTanks;
			if (object instanceof GuidedDevice && object.getPlayer () != null) {
				object.getPlayer().deviceKilled ();
				if (object == mainTank) {
					mainTank = null;
				}
			}
		}
	} // killed

	public void killed (GameObject object, GameObject killer) {
		killed (object);
		// write to log user name
	} // killed

	public GameSpace getGameSpace () {
		return space;
	} // getGameSpace

	public void run () {
		long	beginTime, sleepTime;
		for (int step = 0; true; ++step) {
			beginTime = System.currentTimeMillis ();
			synchronized (this) {
				if (step % 3 == 0) {
					moveBullets ();
					//mainTank.step ();
					moveTanks ();
					vacuumVectors ();
					generateObject ();
				} else {
					moveBullets ();
				}
			}
			sleepTime = TICK_TIME - (System.currentTimeMillis () - beginTime);
			if (sleepTime > 0) {
				try {
					Thread.sleep (sleepTime);
				} catch (InterruptedException e) {}
			} else {
				Thread.yield ();
			}
		}
	} // run

	private void moveBullets () {
		int	size = bullets.size ();
		for (int i = 0; i < size; ++ i) {
			Bullet bullet = (Bullet) bullets.elementAt (i);
			if (bullet.getState () != GameObject.KILLED_STATE) {
				bullet.step ();
			}
		}
	} // moveBullets

	private void moveTanks () {
		int	size = tanks.size ();
		for (int i = 0; i < size; ++ i) {
			Tank tank = (Tank) tanks.elementAt (i);
			if (tank.getState () != GameObject.KILLED_STATE) {
				tank.step ();
			}
		}
	} // moveTanks

	private void vacuumVectors () {
		if (killedBullets >= MAX_KILLED_BULLETS) {
			bullets = vacuumVector (bullets);
			killedBullets = 0;
		}
		if (killedBonuses >= MAX_KILLED_BONUSES) {
			bonuses = vacuumVector (bonuses);
			killedBonuses = 0;
		}
		if (killedTanks >= MAX_KILLED_TANKS) {
			tanks = vacuumVector (tanks);
			killedTanks = 0;
		}
	} // vacuumVectors

	private Vector vacuumVector (Vector vector) {
		int	size = vector.size ();
		Vector	newVector = new Vector (size);
		for (int i = 0; i < size; ++ i) {
			GameObject o = (GameObject) vector.elementAt (i);
			if (o.getState () != GameObject.KILLED_STATE) {
				newVector.addElement (o);
			}
		}
		return newVector;
	} // vacuumVector

	private void generateObject () {
		int rnd = rand (100);
		if (rnd < 1 && nukeBulletBonuses < MAX_NUKE_BULLET_BONUSES) {
			NukeBulletBonus bonus = generator.makeNukeBulletBonus (0);
			++ nukeBulletBonuses;
			if (bonus != null) addObject (bonus);
		} else if (rnd < 2 && bigBulletBonuses < MAX_BIG_BULLET_BONUSES) {
			BigBulletBonus bonus = generator.makeBigBulletBonus (0);
			++ bigBulletBonuses;
			if (bonus != null) addObject (bonus);
		} else if (rnd < 3 && bulletBonuses < MAX_BULLET_BONUSES) {
			BulletBonus bonus = generator.makeBulletBonus (0);
			++ bulletBonuses;
			if (bonus != null) addObject (bonus);
		} else if (rnd < 4 && shieldBonuses < MAX_SHIELD_BONUSES) {
			ShieldBonus bonus = generator.makeShieldBonus (0);
			++ shieldBonuses;
			if (bonus != null) addObject (bonus);
		} else if (rnd < 5) {
			// tank
		}
	} // generateObject

	private int rand (int max) {
		return Math.abs (random.nextInt ()) % max;
	} // rand

}

