package main;

import java.awt.Graphics;

import gui.Audio;
import states.Gamestates;
import states.Menu;
import states.PlayState;
import states.Settings;
import states.Instructions;
import states.Leaderboard;

public class Game implements Runnable {
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameLoop;
	private final int SET_FPS = 120;
	private final int SET_UPS = 200;
	
	private PlayState play;
	private Menu menu;
	private Audio audio;
	private Settings settings;
	private Instructions instructions;
	private Leaderboard leaderboard;
	
	public final static int DEFAULT_TILE_SIZE = 32;
	public final static float SCALE = 1f;
	public final static int TILES_WIDE = 21;
	public final static int TILES_HIGH = 25;
	public final static int TILES_SIZE = (int) (DEFAULT_TILE_SIZE * SCALE);
	public final static int GAME_WIDE = TILES_SIZE * TILES_WIDE;
	public final static int GAME_HIGH = TILES_SIZE * TILES_HIGH;
	public static int score = 0;
	
	public Game() {
		init__();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		startGameLoop();
		
	}
	
	private void init__() {
		audio = new Audio();
		menu = new Menu(this);
		play = new PlayState(this);
		instructions = new Instructions(this);
		leaderboard = new Leaderboard(this);
		settings = new Settings(this);
	}

	private void startGameLoop() {
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	public void update() {
		
		switch(Gamestates.state) {
		case MENU:
			menu.update();
			break;
		case PLAY:
			play.update();
			break;
		case INSTRUCTIONS:
			instructions.update();
			break;
		case LEADERBOARD:
			leaderboard.update();
			break;
		case SETTINGS:
			settings.update();
			break;
		case QUIT:
		default:
			System.exit(0);
			break;
		}
	}
	
	public void render(Graphics g) {
		switch(Gamestates.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAY:
			play.draw(g);
			break;
		case INSTRUCTIONS:
			instructions.draw(g);
			break;
		case LEADERBOARD:
			leaderboard.draw(g);
			break;
		case SETTINGS:
			settings.draw(g);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / SET_FPS;
		// implementing UPS (or update per second) saves the time lost between frames if the game lags
		double timePerUpdate = 1000000000.0 / SET_UPS;
		
		long prevTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long prevCheck = System.currentTimeMillis();
		
		double uDelta = 0;
		double fDelta = 0;
		
		while(true) {
			long currTime = System.nanoTime();
			// gives % of total duration between updates
			uDelta += (currTime - prevTime)/ timePerUpdate;
			fDelta += (currTime - prevTime)/ timePerFrame;
			prevTime = currTime;
			
			if(uDelta >= 1) {
				update();
				updates++;
				uDelta--;
			}
			
			// check if the time between  a frame last being painted and now is longer than should be, and repaint a new frame if so
			if(fDelta >=1) {
				gamePanel.repaint();
				frames++;
				fDelta--;
			}
			

			if(System.currentTimeMillis() - prevCheck >= 1000) {
				prevCheck = System.currentTimeMillis();
				// System.out.println("FPS: " + frames + "| UPS: " + updates);
				frames = 0;
				updates = 0;
			}
			 
		}
		
	}
	
	public void loseWindowFocus() {
		if (Gamestates.state == Gamestates.PLAY) {
			play.getPlayer1().resetDirections();
			play.getPlayer2().resetDirections();
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public PlayState getPlayState() {
		return play;
	}
	
	public Instructions getInstructions() {
		return instructions;
	}
	
	public Leaderboard getLeaderboard() {
		return leaderboard;
	}
	
	public Audio getAudio() {
		return audio;
	}
	
	public Settings getSettings() {
		return settings;
	}
}
