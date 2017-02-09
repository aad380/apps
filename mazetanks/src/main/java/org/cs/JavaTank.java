package org.cs;

import java.awt.*;

public class JavaTank extends Frame {

	Button		localGameButton, networkGameButton, networkControlButton, exitButton;

	public JavaTank () {
		super ("org.cs.JavaTank");
		loadImages ();
		setLayout (new GridLayout (4, 1));
		// buttons
		add (localGameButton = new Button ("Local Game"));
		add (networkGameButton = new Button ("Network Game"));
		add (networkControlButton = new Button ("Network Control"));
		add (exitButton = new Button ("Exit"));
		networkGameButton.disable ();
		networkControlButton.disable ();
		// show
		pack ();
		setResizable (false);
	} // constructor org.cs.Tank

	public boolean action (Event evt, Object obj) {
		if (evt.target instanceof Button) {
			if (evt.target == localGameButton) {
				doLocalGame ();
			} else if (evt.target == networkGameButton) {
				doNetworkGame ();
			} else if (evt.target == networkControlButton) {
				doNetworkControl ();
			} else if (evt.target == exitButton) {
				System.exit (0);
			}
		}
		return true;
	} // action

	void doLocalGame () {
		new LocalGameRunner (this);
	} // doStart

	void doNetworkGame () {
	} // doNetworkGame

	void doNetworkControl () {
	} // doNetworkControl

	public static void main (String args[]) {
		JavaTank game = new JavaTank ();
		game.show ();
	} // main

	private void loadImages () {
		ImageManager.getImage ("4x4/BigBullet_u.gif");
		ImageManager.getImage ("4x4/BigBullet_r.gif");
		ImageManager.getImage ("4x4/BigBullet_d.gif");
		ImageManager.getImage ("4x4/BigBullet_l.gif");
		ImageManager.getImage ("8x8/BigBullet_u.gif");
		ImageManager.getImage ("8x8/BigBullet_r.gif");
		ImageManager.getImage ("8x8/BigBullet_d.gif");
		ImageManager.getImage ("8x8/BigBullet_l.gif");
		ImageManager.getImage ("4x4/org.cs.BigBulletBonus.gif");
		ImageManager.getImage ("8x8/org.cs.BigBulletBonus.gif");
		ImageManager.getImage ("4x4/Bullet_u.gif");
		ImageManager.getImage ("4x4/Bullet_r.gif");
		ImageManager.getImage ("4x4/Bullet_d.gif");
		ImageManager.getImage ("4x4/Bullet_l.gif");
		ImageManager.getImage ("8x8/Bullet_u.gif");
		ImageManager.getImage ("8x8/Bullet_r.gif");
		ImageManager.getImage ("8x8/Bullet_d.gif");
		ImageManager.getImage ("8x8/Bullet_l.gif");
		ImageManager.getImage ("4x4/org.cs.BulletBonus.gif");
		ImageManager.getImage ("8x8/org.cs.BulletBonus.gif");
		ImageManager.getImage ("4x4/NukeBullet_u.gif");
		ImageManager.getImage ("4x4/NukeBullet_r.gif");
		ImageManager.getImage ("4x4/NukeBullet_d.gif");
		ImageManager.getImage ("4x4/NukeBullet_l.gif");
		ImageManager.getImage ("8x8/NukeBullet_u.gif");
		ImageManager.getImage ("8x8/NukeBullet_r.gif");
		ImageManager.getImage ("8x8/NukeBullet_d.gif");
		ImageManager.getImage ("8x8/NukeBullet_l.gif");
		ImageManager.getImage ("4x4/org.cs.NukeBulletBonus.gif");
		ImageManager.getImage ("8x8/org.cs.NukeBulletBonus.gif");
		ImageManager.getImage ("4x4/org.cs.ShieldBonus.gif");
		ImageManager.getImage ("8x8/org.cs.ShieldBonus.gif");
		ImageManager.getImage ("4x4/org.cs.Wall.gif");
		ImageManager.getImage ("8x8/org.cs.Wall.gif");
		ImageManager.getImage ("4x4/Wall50.gif");
		ImageManager.getImage ("8x8/Wall50.gif");
		ImageManager.getImage ("4x4/Wall10.gif");
		ImageManager.getImage ("8x8/Wall10.gif");
		ImageManager.getImage ("4x4/org.cs.Space.gif");
		ImageManager.getImage ("8x8/org.cs.Space.gif");
		ImageManager.getImage ("4x4/Tank_u.gif");
		ImageManager.getImage ("4x4/Tank_r.gif");
		ImageManager.getImage ("4x4/Tank_d.gif");
		ImageManager.getImage ("4x4/Tank_l.gif");
		ImageManager.getImage ("8x8/Tank_u.gif");
		ImageManager.getImage ("8x8/Tank_r.gif");
		ImageManager.getImage ("8x8/Tank_d.gif");
		ImageManager.getImage ("8x8/Tank_l.gif");
		ImageManager.getImage ("4x4/Cannon_u.gif");
		ImageManager.getImage ("4x4/Cannon_r.gif");
		ImageManager.getImage ("4x4/Cannon_d.gif");
		ImageManager.getImage ("4x4/Cannon_l.gif");
		ImageManager.getImage ("8x8/Cannon_u.gif");
		ImageManager.getImage ("8x8/Cannon_r.gif");
		ImageManager.getImage ("8x8/Cannon_d.gif");
		ImageManager.getImage ("8x8/Cannon_l.gif");
		ImageManager.getImage ("4x4/org.cs.Wall.gif");
		ImageManager.getImage ("8x8/org.cs.Wall.gif");
		ImageManager.getImage ("4x4/Wall50.gif");
		ImageManager.getImage ("8x8/Wall50.gif");
		ImageManager.getImage ("4x4/Wall10.gif");
		ImageManager.getImage ("8x8/Wall10.gif");
		ImageManager.loadAll (this);
	}

} // class ViewMaze

