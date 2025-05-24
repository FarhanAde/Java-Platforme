package gui;

import static misc.Constants.SCORES_FILENAME;
import static misc.Constants.GUI.NavButtons.NAV_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

import main.Game;
import misc.LoadGame;
import states.Gamestates;
import states.PlayState;
import states.Leaderboard;

public class LevelCompleteMenu {
	
	private PlayState play;
	private NavButtons pressPlay, home, restart;
	private BufferedImage image;
	private int xImage, yImage, imageWidth, imageHeight;
	
	public LevelCompleteMenu(PlayState play) {
		this.play = play;
		loadBG();
		init__buttons();
		
	}

	private void init__buttons() {
		// define co-ords for where buttons are displayed (they all have the same y value, navY)
		int homeX = (int)(255 * Game.SCALE);
		int restartX = (int)(375 * Game.SCALE);
		int playX = (int)(310 * Game.SCALE);
		int navY = (int)(380 * Game.SCALE);
		
		pressPlay = new NavButtons(playX, (int)(310 * Game.SCALE), (int)(1.5 * NAV_SIZE), (int)(1.5 * NAV_SIZE), 0);
		restart = new NavButtons(restartX, navY, NAV_SIZE, NAV_SIZE, 1);
		home = new NavButtons(homeX, navY, NAV_SIZE, NAV_SIZE, 2);
	}

	private void loadBG() {
		image = LoadGame.GetSprites(LoadGame.LEVEL_COMPLETE);
		imageWidth = (int) (image.getWidth() * Game.SCALE);
		imageHeight = (int) (image.getHeight() * Game.SCALE);
		xImage = Game.GAME_WIDE / 2 - imageWidth / 2;
		yImage = (int) (230 * Game.SCALE);
	}
	
	public void update() {
		pressPlay.update();
		restart.update();
		home.update();
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDE, Game.GAME_HIGH);
		g.drawImage(image, xImage, yImage, imageWidth, imageHeight, null);
		
		pressPlay.draw(g);
		restart.draw(g);
		home.draw(g);
	}
	
	public void mousePressed(MouseEvent e) {
		if(inButton(e, pressPlay))
			pressPlay.setMousePressed(true);
		else if(inButton(e, restart))
			restart.setMousePressed(true);
		else if(inButton(e, home))
			home.setMousePressed(true);
	}

	
	public void mouseReleased(MouseEvent e) {
		
		if(inButton(e, pressPlay)) {
			if(pressPlay.isMousePressed()) {
				play.resetGame();
			}
			
		} else if(inButton(e, restart)) {
			if(restart.isMousePressed()) {
				play.resetGame();
			}
			
		} else if(inButton(e, home)) {
			if(home.isMousePressed()) {
				play.resetGame();
				Gamestates.state = Gamestates.MENU;
			}
		}
	
		pressPlay.resetBooleans();
		restart.resetBooleans();
		home.resetBooleans();
	}

	
	public void mouseMoved(MouseEvent e) {
		
		pressPlay.setMouseOver(false);
		restart.setMouseOver(false);
		home.setMouseOver(false);
		
		if(inButton(e, pressPlay))
			pressPlay.setMouseOver(true);
		else if(inButton(e, restart))
			restart.setMouseOver(true);
		else if(inButton(e, home))
			home.setMouseOver(true);
		
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("'ENTER' pressed!");
		}
	}
	
	public boolean inButton(MouseEvent e, PauseButtons button) {
		return button.getArea().contains(e.getX(), e.getY());
	}

}
