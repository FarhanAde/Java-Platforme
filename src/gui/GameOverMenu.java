package gui;

import static misc.Constants.GUI.NavButtons.NAV_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import misc.LoadGame;
import states.Gamestates;
import states.PlayState;

public class GameOverMenu {
	private PlayState play;
	private BufferedImage image;
	private int xImage, yImage, imageWidth, imageHeight;
	private NavButtons home, pressPlay;
	
	public GameOverMenu (PlayState play) {
		this.play = play;
		loadImage();
		init__buttons();
	}
	
	private void init__buttons() {
		int homeX = (int)(260 * Game.SCALE);
		int playX = (int)(370 * Game.SCALE);
		int navY = (int)(300 * Game.SCALE);
		
		pressPlay = new NavButtons(playX, navY, NAV_SIZE, NAV_SIZE, 0);
		home = new NavButtons(homeX, navY, NAV_SIZE, NAV_SIZE, 2);
	}

	private void loadImage() {
		image = LoadGame.GetSprites(LoadGame.LOSE_SCREEN);
		imageWidth = (int)(image.getWidth() * Game.SCALE);
		imageHeight = (int)(image.getHeight() * Game.SCALE);
		xImage = Game.GAME_WIDE / 2 - imageWidth / 2;
		yImage = (int)(200 * Game.SCALE);
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDE, Game.GAME_HIGH);
		g.drawImage(image, xImage, yImage, imageWidth, imageHeight, null);
		home.draw(g);
		pressPlay.draw(g);

	}
	
	public void update() {
		home.update();
		pressPlay.update();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			play.resetGame();
			Gamestates.state = Gamestates.MENU;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(inButton(e, pressPlay))
			pressPlay.setMousePressed(true);
		else if(inButton(e, home))
			home.setMousePressed(true);
	}

	
	public void mouseReleased(MouseEvent e) {
		if(inButton(e, pressPlay)) {
			if(pressPlay.isMousePressed())
				play.resetGame();
			
		} else if(inButton(e, home)) {
			if(home.isMousePressed()) {
				play.resetGame();
				Gamestates.state = Gamestates.MENU;
			}
		}
		
		pressPlay.resetBooleans();
		home.resetBooleans();
	}

	
	public void mouseMoved(MouseEvent e) {
		
		pressPlay.setMouseOver(false);
		home.setMouseOver(false);
		
		if(inButton(e, pressPlay))
			pressPlay.setMouseOver(true);
		else if(inButton(e, home))
			home.setMouseOver(true);
		
	}
	
	public boolean inButton(MouseEvent e, PauseButtons button) {
		return button.getArea().contains(e.getX(), e.getY());
	}

}
