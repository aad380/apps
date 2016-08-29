package org.cs.snake;

import java.applet.Applet;
import java.awt.*;

/**
 * Created by aad on 29.08.2016.
 */
class SnakeGame extends Panel {

	private final static int	X_CELLS = 32,
					Y_CELLS = 22,
					SNAKE_START_LENGTH = 4;

	// game objects
	private GameSpace	gameSpace;
	private KeyQueue	keyQueue;
	private BeastGenerator	beastGenerator;
	private SnakeBeast	snake;

	// gui components
	private Panel		gameSpacePanel;
	private Panel		buttonPanel;
	private Panel		statusPanel;
	private TextField	levelField;
	private Button		goButton, pauseButton, restartButton, aboutButton;
	private Choice		levelChoice, arrowModeChoice;

	// status line components
	private Label	maxscoreLabel;
	private Label	scoreLabel;
	private Label	livesLabel;
	private Label	lengthLabel;
	private Label	stepsLabel;

	// game properties
	private int	level, arrowMode, score, maxscore, length, lives, steps;
	private Applet applet;
	private boolean	aboutFlag;

	SnakeGame (TextArea aboutTextArea) {
		this (aboutTextArea, null);
	}

	SnakeGame (TextArea aboutTextArea, Applet applet) {
		this.applet = applet;
		aboutFlag = false;
		// button panel
		buttonPanel = new Panel ();
		buttonPanel.setLayout (new FlowLayout ());
		buttonPanel.add (goButton = new Button ("Go"));
		buttonPanel.add (pauseButton = new Button ("Pause"));
		buttonPanel.add (restartButton = new Button ("Restart"));
		buttonPanel.add (aboutButton = new Button ("About"));
		if (applet == null) {
			buttonPanel.add (new Button ("Quit"));
		}
		buttonPanel.add (new Label (" Level: ", Label.RIGHT));
		buttonPanel.add (levelChoice = new Choice ());
		for (int i = 1; i <= 10; ++ i) {
			levelChoice.addItem (Integer.toString (i));
		}
		buttonPanel.add (new Label (" Key mode: ", Label.RIGHT));
		buttonPanel.add (arrowModeChoice = new Choice ());
		arrowModeChoice.addItem ("Two arrows");
		arrowModeChoice.addItem ("Four arrows");
		pauseButton.disable ();
		restartButton.disable ();
		// game panel
		gameSpacePanel = new Panel ();
		gameSpacePanel.setLayout (new FlowLayout (FlowLayout.CENTER, 0, 0));
		gameSpace = new GameSpace (X_CELLS, Y_CELLS, 16, 16, 2, new Color (0x200080), Color.black);
		gameSpacePanel.add (gameSpace);
		// status panel
		statusPanel = new Panel ();
		statusPanel.setLayout (new FlowLayout (FlowLayout.LEFT, 4, 0));
		statusPanel.add (new Label (" hiscore: ", Label.RIGHT));
		statusPanel.add (maxscoreLabel = new Label ("0    ", Label.LEFT));
		statusPanel.add (new Label (" score: ", Label.RIGHT));
		statusPanel.add (scoreLabel = new Label ("0    ", Label.LEFT));
		statusPanel.add (new Label (" lives: ", Label.RIGHT));
		statusPanel.add (livesLabel = new Label ("0    ", Label.LEFT));
		statusPanel.add (new Label (" length: ", Label.RIGHT));
		statusPanel.add (lengthLabel = new Label ("0    ", Label.LEFT));
		statusPanel.add (new Label (" steps: ", Label.RIGHT));
		statusPanel.add (stepsLabel = new Label ("0    ", Label.LEFT));
		maxscoreLabel.setForeground (Color.red);
		scoreLabel.setForeground (Color.red);
		livesLabel.setForeground (Color.red);
		lengthLabel.setForeground (Color.red);
		stepsLabel.setForeground (Color.red);
		// game panel
		Panel gamePanel = new Panel ();
		gamePanel.setLayout (new BorderLayout ());
		gamePanel.add ("North", buttonPanel);
		gamePanel.add ("Center", gameSpacePanel);
		gamePanel.add ("South", statusPanel);
		// about panel
		Panel aboutPanel = new Panel ();
		aboutPanel.setLayout (new BorderLayout ());
		aboutPanel.add ("Center", aboutTextArea);
		aboutPanel.add ("South", new Button ("Done"));
		// add components
		setLayout (new CardLayout (0, 0));
		add ("game", gamePanel);
		add ("about", aboutPanel);
		((CardLayout)getLayout()).show (this, "game");

		// init properties
		level = 1;
		arrowMode = SnakeBeast.TWO_ARROWS;
		length = SNAKE_START_LENGTH;
		score = maxscore = lives = steps = 0;

		clearGameSpace ();

		keyQueue = new KeyQueue (16);
		snake = new SnakeBeast (length, level, arrowMode, gameSpace, keyQueue, this);
		beastGenerator = new BeastGenerator (gameSpace, snake);

		snake.init ();
		resize (600, 460);
	} // SnakeGame constructor

	protected void finalize () throws Throwable {
		if (snake.isAlive ()) {
			snake.stop ();
		}
		if (beastGenerator.isAlive ()) {
			beastGenerator.stop ();
		}
		snake = null;
		beastGenerator = null;
	}

	public boolean action (Event evt, Object obj) {
		if (evt.target instanceof Button ) {
			String label = (String) obj;
			if (label == "Go") {
				doGo ();
			} else if (label == "Pause") {
				doPause ();
			} else if (label == "Restart") {
				doRestart ();
			} else if (label == "About") {
				aboutFlag = true;
				((CardLayout)getLayout()).show (this, "about");
			} else if (label == "Done") {
				aboutFlag = false;
				((CardLayout)getLayout()).show (this, "game");
			} else if (label == "Quit") {
				doQuit ();
			}
		} else if (evt.target instanceof Choice) {
			Choice choice = (Choice) evt.target;
			String label = (String) obj;
			if (choice == levelChoice) {
				snake.setLevel (level = Integer.parseInt (label));
			} else if (choice == arrowModeChoice) {
				if (label == "Two arrows") {
					snake.setArrowMode (arrowMode = SnakeBeast.TWO_ARROWS);
				} else if (label == "Four arrows") {
					snake.setArrowMode (arrowMode = SnakeBeast.FOUR_ARROWS);
				}
			}
		}
		return true;
	}

	public boolean keyDown (Event evt, int key) {
		if (aboutFlag) {
			return false;
		}
		int	levelIndex;
		switch (key) {
		case Event.LEFT:
		case 'j':
			keyQueue.putKey (KeyQueue.LEFT);
			break;
		case Event.RIGHT:
		case 'k':
			keyQueue.putKey (KeyQueue.RIGHT);
			break;
		case Event.UP:
		case 'a':
			keyQueue.putKey (KeyQueue.UP);
			break;
		case Event.DOWN:
		case 'z':
			keyQueue.putKey (KeyQueue.DOWN);
			break;
		case '+':
			levelIndex = levelChoice.getSelectedIndex ();
			if (levelIndex < levelChoice.countItems () - 1) {
				levelChoice.hide ();
				levelChoice.select (levelIndex + 1);
				levelChoice.show ();
				snake.setLevel (levelIndex + 2);
				++ level;
			}
			break;
		case '-':
			levelIndex = levelChoice.getSelectedIndex ();
			if (levelIndex > 0) {
				levelChoice.hide ();
				levelChoice.select (levelIndex - 1);
				levelChoice.show ();
				snake.setLevel (levelIndex);
				-- level;
			}
			break;
		case 'g':
			if (goButton.isEnabled ()) {
				doGo ();
			}
			break;
		case 'p':
			if (pauseButton.isEnabled ()) {
				doPause ();
			}
			break;
		case ' ':
			if (goButton.isEnabled ()) {
				doGo ();
			} else if (pauseButton.isEnabled ()) {
				doPause ();
			} else if (restartButton.isEnabled ()) {
				doRestart ();
			}
			break;
		case 'r':
			if (restartButton.isEnabled ()) {
				doRestart ();
			}
			break;
		case 'q':
			if (applet == null) {
				doQuit ();
			}
			break;
		}
		return true;
	}

	synchronized void printStatus (int newLength, int newScore, int newLives, int  newSteps) {
		if (length != newLength) {
			length = newLength;
			lengthLabel.setText (Integer.toString (length));
		}
		if (score != newScore) {
			score = newScore;
			scoreLabel.setText (Integer.toString (score));
		}
		if (lives != newLives) {
			lives = newLives;
			livesLabel.setText (Integer.toString (lives));
		}
		if (steps != newSteps) {
			steps = newSteps;
			stepsLabel.setText (Integer.toString (steps));
		}
	}

	void gameOver () {
		// called by snake and stop snake
		//
		// buttons
		goButton.disable ();
		pauseButton.disable ();
		restartButton.enable ();
		restartButton.requestFocus ();
		// stop beasts
		beastGenerator.stop ();
		snake.stop ();		// must be last command
	}

	void clearGameSpace () {
		gameSpace.deleteAll ();
		gameSpace.addCell (0, 0, Cell.get (Cell.BORDER_LU));
		gameSpace.addCell (X_CELLS - 1, 0, Cell.get (Cell.BORDER_RU));
		gameSpace.addCell (0, Y_CELLS - 1, Cell.get (Cell.BORDER_LD));
		gameSpace.addCell (X_CELLS - 1, Y_CELLS - 1, Cell.get (Cell.BORDER_RD));
		for (int x = 1; x < X_CELLS - 1; ++ x) {
			gameSpace.addCell (x, 0, Cell.get (Cell.BORDER_U));
			gameSpace.addCell (x, Y_CELLS - 1, Cell.get (Cell.BORDER_D));
		}
		for (int y = 1; y < Y_CELLS - 1; ++ y) {
			gameSpace.addCell (0, y, Cell.get (Cell.BORDER_L));
			gameSpace.addCell (X_CELLS - 1, y, Cell.get (Cell.BORDER_R));
		}
	}

	public synchronized void doPause () {
		//snake.suspend ();
		//beastGenerator.suspend ();
		snake.pause = true;
		beastGenerator.pause = true;
		// buttons
		restartButton.enable ();
		pauseButton.disable ();
		goButton.enable ();
		aboutButton.enable ();
		goButton.requestFocus ();
	}

	public synchronized void doGo () {
		if (snake.isAlive ()) {
			//snake.resume ();
			snake.pause = false;
		} else {
			if (applet == null) {
				snake.setDaemon (true);
			}
			snake.start ();
		}
		if (beastGenerator.isAlive ()) {
			//beastGenerator.resume ();
			beastGenerator.pause = false;
		} else {
			if (applet == null) {
				beastGenerator.setDaemon (true);
			}
			beastGenerator.start ();
		}
		// buttons
		restartButton.disable ();
		goButton.disable ();
		pauseButton.enable ();
		aboutButton.disable ();
		pauseButton.requestFocus ();
	}

	public synchronized void doRestart () {
		// stop old beasts
		if (snake.isAlive ()) {
			snake.stop ();
		}
		if (beastGenerator.isAlive ()) {
			beastGenerator.stop ();
		}
		clearGameSpace ();
		keyQueue.clear ();
		// init properties
		length = SNAKE_START_LENGTH;
		if (score > maxscore) {
			maxscore = score;
		}
		score = lives = steps = 0;
		maxscoreLabel.setText (Integer.toString (maxscore));
		scoreLabel.setText (Integer.toString (score));
		lengthLabel.setText (Integer.toString (length));
		livesLabel.setText (Integer.toString (lives));
		stepsLabel.setText (Integer.toString (steps));
		// create new beasts
		snake = new SnakeBeast (length, level, arrowMode, gameSpace, keyQueue, this);
		beastGenerator = new BeastGenerator (gameSpace, snake);
		snake.init ();
		// buttons
		restartButton.disable ();
		goButton.enable ();
		pauseButton.disable ();
		aboutButton.enable ();
		goButton.requestFocus ();
	}

	void doQuit () {
		if (applet == null) {
			System.exit (0);
		}
	}

} // class SnakeGame
