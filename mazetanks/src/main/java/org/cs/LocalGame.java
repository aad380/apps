package org.cs;

import java.awt.*;

/*
class statusLabel extends Component {

	public statusLabel (
		String description, String message,
//		Font descriptionFont, Font messageFont,
		Color descriptionColor, Color messageColor,
		int messageMaxLength
	){
		statusPanel.setLayout (new FlowLayout (FlowLayout.LEFT, 4, 0));
		statusPanel.add (descriptionLabel Label (description, Label.RIGHT));
		statusPanel.add (messageLabel = new Label (message, Label.LEFT));
		descriptionLabel.setForeground (descriptionColor);
		messageLabel.setForeground (messageColor);
	}

}
*/

public class LocalGame extends Frame implements Player {

	private static String	bulletString =		"bullet     ",
				bigBulletString =	"big bullet ",
				nukeBulletString =	"nuke bullet";
	GameSpaceCanvas	space = null;
	Panel		controlPanel, statusPanel;
	GameManager	server;
	long		pressTime = 0;
	TextAreaLog	log;
	int		cellSize = 8;
	GuidedDevice	tank = null;
	Button		startButton, stopButton, pauseButton;
	Label		shieldLabel, bulletLabel, bigBulletLabel,
			nukeBulletLabel, weaponLabel;
	int		bullets, bigBullets, nukeBullets, shield, weapon = GuidedDevice.BULLET;

	public LocalGame (int width, int height, int lines) {
		super ("org.cs.JavaTank: org.cs.LocalGame");
		space = new GameSpaceCanvas (50, 50, cellSize, Color.black, Color.white);
		log = new TextAreaLog (1, 30);
		log.setEditable (false);
		server = new LocalTankServer (this, space, log, width, height, lines);
		controlPanel = new Panel ();
		statusPanel = new Panel ();
		setLayout (new BorderLayout ());
		add ("Center", space);
		add ("East", log);
		// buttons
		controlPanel.add (startButton = new Button ("Start"));
		controlPanel.add (stopButton = new Button ("Stop"));
		controlPanel.add (pauseButton = new Button ("Pause"));
		controlPanel.add (new Button ("Done"));
		stopButton.disable ();
		pauseButton.disable ();
		CheckboxGroup	group = new CheckboxGroup ();
		controlPanel.add (new Checkbox ("8x8", group, true));
		controlPanel.add (new Checkbox ("4x4", group, false));
		add ("North", controlPanel);
		// status
		statusPanel.setLayout (new FlowLayout (FlowLayout.LEFT, 4, 0));
		statusPanel.add (new Label (" shield: ", Label.RIGHT));
		statusPanel.add (shieldLabel = new Label ("0  ", Label.LEFT));
		statusPanel.add (new Label (" bullets: ", Label.RIGHT));
		statusPanel.add (bulletLabel = new Label ("0  ", Label.LEFT));
		statusPanel.add (new Label (" big bullets: ", Label.RIGHT));
		statusPanel.add (bigBulletLabel = new Label ("0  ", Label.LEFT));
		statusPanel.add (new Label (" nukes: ", Label.RIGHT));
		statusPanel.add (nukeBulletLabel = new Label ("0  ", Label.LEFT));
		statusPanel.add (new Label (" current weapon: ", Label.RIGHT));
		statusPanel.add (weaponLabel = new Label (bulletString, Label.LEFT));
		shieldLabel.setForeground (Color.red);
		bulletLabel.setForeground (Color.red);
		bigBulletLabel.setForeground (Color.red);
		nukeBulletLabel.setForeground (Color.red);
		weaponLabel.setForeground (Color.red);
		//
		add ("South", statusPanel);
		//resize (640, 480);
		pack ();
		show ();
	} // constructor org.cs.Tank

	public boolean action (Event evt, Object obj) {
		if (evt.target instanceof Button) {
			String label = (String) obj;
			if (label == "Start") {
				doStart ();
			} else if (label == "Stop") {
				doStop ();
			} else if (label == "Pause") {
				doPause ();
			} else if (label == "Done") {
				doQuit ();
			}
		} else if (evt.target instanceof Checkbox) {
			String label = ((Checkbox) evt.target).getLabel ();
			if (label == "8x8" && cellSize != 8) {
				cellSize = 8;
				space.setCellSize (cellSize);
			} else if (label == "4x4" && cellSize != 4) {
				cellSize = 4;
				space.setCellSize (cellSize);
			}
		}
		return true;  // not reached
	} // action

	public boolean keyDown (Event evt, int key) {
		if (tank == null) {
			return true ;
		}
		switch (key) {
		case Event.LEFT:
			tank.turnLeft ();
			break;
		case Event.RIGHT:
			tank.turnRight ();
			break;
		case Event.UP:
			if ((evt.modifiers & Event.CTRL_MASK) == 0) {
				tank.goForward ();
			} else {
				tank.runForward ();
			}
			break;
		case Event.DOWN:
			if ((evt.modifiers & Event.CTRL_MASK) == 0) {
				tank.goBackward ();
			} else {
				tank.runBackward ();
			}
			break;
		case 'a':
			tank.runForward ();
			break;
		case 'z':
			tank.runBackward ();
			break;
		case ' ':
			tank.fire ();
			break;
		case 'q':
			tank.stop ();
			break;
		case 'e':
			doQuit ();
			break;
		case '\t':
			switch (weapon) {
			case GuidedDevice.BULLET:
				weapon = GuidedDevice.BIG_BULLET;
				tank.selectBigBullet ();
				break;
			case GuidedDevice.BIG_BULLET:
				weapon = GuidedDevice.NUKE_BULLET;
				tank.selectNukeBullet ();
				break;
			case GuidedDevice.NUKE_BULLET:
				weapon = GuidedDevice.BULLET;
				tank.selectBullet ();
				break;
			}
			setWeaponLabel ();
		}
		return true;
	} // keyDown

	void doStart () {
		startButton.disable ();
		server.start ();
		stopButton.enable ();
		pauseButton.enable ();
	} // doStart

	void doStop () {
		bulletLabel.setText ("0");
		bigBulletLabel.setText ("0");
		nukeBulletLabel.setText ("0");
		shieldLabel.setText ("0");
		weaponLabel.setText (bulletString);
		pauseButton.disable ();
		stopButton.disable ();
		server.stop ();
		startButton.enable ();
	} // doStop

	void doPause () {
		//
	} // doPause

	void doQuit () {
		tank = null;
		server = null;
		space = null;
		super.dispose ();
	} // doQuit

	private void setWeaponLabel () {
		switch (weapon) {
		case GuidedDevice.BULLET:
			weaponLabel.setText (bulletString);
			statusPanel.layout ();
			break;
		case GuidedDevice.BIG_BULLET:
			weaponLabel.setText (bigBulletString);
			statusPanel.layout ();
			break;
		case GuidedDevice.NUKE_BULLET:
			weaponLabel.setText (nukeBulletString);
			statusPanel.layout ();
			break;
		}
	}

// + org.cs.Player interface

	public void deviceCreated (GuidedDevice tank) {
		this.tank = tank;
		bulletLabel.setText (Integer.toString (tank.getBullets ()));
		bigBulletLabel.setText (Integer.toString (tank.getBigBullets ()));
		nukeBulletLabel.setText (Integer.toString (tank.getNukeBullets ()));
		shieldLabel.setText (Integer.toString (tank.getShield ()));
		weapon = tank.getCurrentWeapon ();
	}

	public void step () {
		// not implemented
	}

	public void bulletsChanged (int number) {
		if (this.bullets != number) {
			this.bullets = number;
			bulletLabel.setText (Integer.toString (number));
		}
	}

	public void bigBulletsChanged (int number) {
		if (this.bigBullets != number) {
			this.bigBullets = number;
			bigBulletLabel.setText (Integer.toString (number));
		}
	}

	public void nukeBulletsChanged (int number) {
		if (this.nukeBullets != number) {
			this.nukeBullets = number;
			nukeBulletLabel.setText (Integer.toString (number));
		}
	}

	public void shieldChanged (int number) {
		if (this.shield != number) {
			this.shield = number;
			shieldLabel.setText (Integer.toString (number));
		}
	}

	public void deviceKilled () {
System.err.println ("org.cs.JavaTank: tank killed");
		this.tank = null;
	}

	public String getName () {
		return "San Sanych";
	}

// - org.cs.Player interface

} // class ViewMaze

