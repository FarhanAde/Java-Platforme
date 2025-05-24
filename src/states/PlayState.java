package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static misc.Constants.SCORES_FILENAME;

import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import misc.LoadGame;
import objects.ItemHandler;
import gui.GameOverMenu;
import gui.LevelCompleteMenu;
import gui.PauseMenu;

public class PlayState extends States implements StatesMethods{
	
	private Player player1, player2;
	private LevelHandler levelHandler;
	private EnemyHandler enemyHandler;
	private ItemHandler itemHandler;
	private PauseMenu pauseMenu;
	private LevelCompleteMenu levelCompleteMenu;
	private GameOverMenu gameOverMenu;
	private Leaderboard leaderboard;
	
	private DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	// playing boolean used to track whether game has been paused
	private boolean playing = true;
	private boolean timerOn = true;
	private long lastTimed = System.currentTimeMillis();
	public float timeTaken = 0;
	// gameOver boolean used to track whether game is over due to player dying
	private boolean gameOver = false;
	private boolean levelComplete = false;
	private boolean dying = false;
	
	// used for vertical scrolling (shifting level to follow player when it moves up/down past visible range)
	private int yOffset;
	private int bottomBorder = (int)(0.35*Game.GAME_HIGH);
	private int topBorder = (int)(0.65*Game.GAME_HIGH);
	private int lvlTilesHigh = LoadGame.GetLvlData().length;
	private int maxTilesOffset = lvlTilesHigh - Game.TILES_HIGH;
	private int maxLvlOffset = maxTilesOffset * Game.TILES_SIZE;
	
	private BufferedImage bgImage;
	
	public PlayState(Game game) {
		super(game);
		init__();
		
		bgImage = LoadGame.GetSprites(LoadGame.PLAYING_BG);
	}
	
	private void init__() {
		levelHandler = new LevelHandler(game);
		leaderboard = new Leaderboard(game);
		enemyHandler = new EnemyHandler(this);
		itemHandler = new ItemHandler(this);
		player1 = new Player(100*Game.SCALE, 3000*Game.SCALE, (int)(64*Game.SCALE), (int)(40*Game.SCALE), 0, this);
		player2 = new Player(120*Game.SCALE, 3000*Game.SCALE, (int)(64*Game.SCALE), (int)(40*Game.SCALE), 1, this);
		player1.loadLevelData(levelHandler.getLevel().getLvlData());
		player2.loadLevelData(levelHandler.getLevel().getLvlData());
		pauseMenu = new PauseMenu(this);
		gameOverMenu = new GameOverMenu(this);
		levelCompleteMenu = new LevelCompleteMenu(this);
	}
	
	public void loseWindowFocus() {
		player1.resetDirections();
		player2.resetDirections();
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public EnemyHandler getEnemyHandler() {
		return enemyHandler;
	}
	
	public ItemHandler getItemHandler() {
		return itemHandler;
	}
	
	public LevelHandler getLevelHandler() {
		return levelHandler;
	}

	@Override
	public void update() {
		// only update pause menu if game is paused
		if (!playing) {
			pauseMenu.update();
			
		} else if (levelComplete) {
			levelCompleteMenu.update();
			
		} else if (gameOver) {
			gameOverMenu.update();
			
		} else if(dying) {
			player1.update(0);
			player2.update(1);
			
		} else {
			levelHandler.update();
			itemHandler.update();
			player1.update(0);
			player2.update(1);
			enemyHandler.update(levelHandler.getLevel().getLvlData(), player1, player2);
			scrolling(player2, player1);
			timer();
			
		}
		
	}

	private void scrolling(Player playerNo1, Player playerNo2) {
		int playerY = (int)(Math.min(playerNo1.getHitbox().y, playerNo2.getHitbox().y));
		int diff = playerY - yOffset;
		
		if (diff > topBorder)
			yOffset += diff - topBorder;
		else if (diff < bottomBorder)
			yOffset += diff - bottomBorder;
		
		if(yOffset > maxLvlOffset)
			yOffset = maxLvlOffset;
		else if (yOffset < 0)
			yOffset = 0;
	}
	
//	public void autoScrolling() {
//		// long timeLastScrolled = System.currentTimeMillis();
//		
//		if(yOffset >= (int)(5 * Game.SCALE)) {
//			if (System.currentTimeMillis() - timeLastScrolled >= 20) {
//				yOffset -= (int)(1*Game.SCALE);
//				System.out.println(String.valueOf(yOffset));
//				timeLastScrolled = System.currentTimeMillis();
//			}
//		}
//			
//		
//		// System.out.println(String.valueOf(yOffset));
//	}
	
	public void timer() {
		if (System.currentTimeMillis() - lastTimed >= 20) {
			timeTaken += 0.02f;
			lastTimed = System.currentTimeMillis();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bgImage, 0, 0, Game.GAME_WIDE, Game.GAME_HIGH, null);
		
		levelHandler.draw(g, yOffset);
		itemHandler.draw(g, yOffset);
		enemyHandler.draw(g, yOffset);
		player1.render(g, yOffset, 0);
		player2.render(g, yOffset, 1);
		
		if(!playing) {
			g.setColor(new Color(0, 0, 0, 175));
			g.fillRect(0, 0, Game.GAME_WIDE, Game.GAME_HIGH);
			pauseMenu.draw(g);
		} else if (gameOver) {
			gameOverMenu.draw(g);
		} else if (levelComplete) {
			levelCompleteMenu.draw(g);
		}
		
	}

	public void mouseDragged(MouseEvent e) {
		if(!gameOver) {
			if(!playing)
				pauseMenu.mouseDragged(e);
		} 
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			if(!playing) {
				pauseMenu.mousePressed(e);
			} else if (levelComplete) {
				levelCompleteMenu.mousePressed(e);
			}
			
		} else {
			gameOverMenu.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(!gameOver) {
			if(!playing) {
				pauseMenu.mouseReleased(e);
			} else if (levelComplete) {
				levelCompleteMenu.mouseReleased(e);
			}
			
		} else {
			gameOverMenu.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if(!playing) {
				pauseMenu.mouseMoved(e);
			} else if (levelComplete) {
				levelCompleteMenu.mouseMoved(e);
			}
			
		} else {
			gameOverMenu.mouseMoved(e);
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void unpause() {
		playing = true;
		timerOn = true;
		
	}
	
	// resets the game once a player dies or user restarts the level
	public void resetGame() {
		gameOver = false;
		levelComplete = false;
		playing = true;
		timeTaken = 0;
		timerOn = true;
		dying = false;
		player1.reset(0);
		player2.reset(1);
		enemyHandler.resetAll();
		itemHandler.resetAllItems();
		yOffset = 800;
	}
	
	public void writeScores(int score) {
		
		String date = LocalDate.now().format(formatDate);
		String time = LocalTime.now().format(formatTime);
		
	    try {
	        FileWriter myWriter = new FileWriter(SCORES_FILENAME, true);
	        myWriter.write((int)(timeTaken - (2*score)) +"\n" + date + "\n" + time + "\n");
	        myWriter.close();
	        System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    
	    leaderboard.readScoresFile();
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
		writeScores(Game.score);
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackArea) {
		enemyHandler.checkEnemyHit(attackArea);
	}
	
	public void checkTokenPickUp(Rectangle2D.Float hitbox) {
		itemHandler.pickup(hitbox);
	}
	
	public void setDying(boolean dying) {
		this.dying = dying;
	}

	public void dontTouchTheSpikes(Player player) {
		itemHandler.dontTouchTheSpikes(player);
	}
	
	public void callTheElevator(Player player) {
		itemHandler.callTheElevator(player);
	}
	
	public void endTheGame(Player playerOne, Player playerTwo) {
		itemHandler.endTheGame(playerOne, playerTwo);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverMenu.keyPressed(e);
		else
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_A:
				player1.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player1.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				player1.setPeaceful(false);
				break;
			case KeyEvent.VK_W:
				player1.setJump(true);
				break;
				
			case KeyEvent.VK_LEFT:
				player2.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
				player2.setRight(true);
				break;
			case KeyEvent.VK_ENTER:
				player2.setPeaceful(false);
				break;
			case KeyEvent.VK_UP:
				player2.setJump(true);
				break;
			
				
			case KeyEvent.VK_ESCAPE:
				playing = !playing;
				timerOn = !timerOn;
				break;
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
			switch(e.getKeyCode()) {
			
//			case KeyEvent.VK_S:
//				player1.setDown(false);
//				break;
			case KeyEvent.VK_A:
				player1.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player1.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player1.setPeaceful(true);
				break;
			case KeyEvent.VK_W:
				player1.setJump(false);
				break;
				
//			case KeyEvent.VK_DOWN:
//				player2.setDown(false);
//				break;
			case KeyEvent.VK_LEFT:
				player2.setLeft(false);
				break;
			case KeyEvent.VK_RIGHT:
				player2.setRight(false);
				break;
			case KeyEvent.VK_ENTER:
				player2.setPeaceful(true);
				break;
			case KeyEvent.VK_UP:
				player2.setJump(false);
				break;
				
			}
		
	}

}
