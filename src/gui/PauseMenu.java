package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import states.Gamestates;
import states.PlayState;
import main.Game;
import misc.LoadGame;
import static misc.Constants.GUI.PauseButtons.*;
import static misc.Constants.GUI.NavButtons.*;
import static misc.Constants.GUI.VolumeSlider.*;

public class PauseMenu {
	
	private PlayState play;
	private BufferedImage background;
	private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
	private Audio audio;
	private NavButtons home, restart, pressPlay;
	
	public PauseMenu(PlayState play) {
		this.play = play;
		loadBG();
		audio = play.getGame().getAudio();
		init__navButtons();
	}

	private void init__navButtons() {
		// define co-ords for where buttons are displayed (they all have the same y value, navY)
		int homeX = (int)(315 * Game.SCALE);
		int restartX = (int)(390 * Game.SCALE);
		int playX = (int)(240 * Game.SCALE);
		int navY = (int)(490 * Game.SCALE);
		
		pressPlay = new NavButtons(playX, navY, NAV_SIZE, NAV_SIZE, 0);
		restart = new NavButtons(restartX, navY, NAV_SIZE, NAV_SIZE, 1);
		home = new NavButtons(homeX, navY, NAV_SIZE, NAV_SIZE, 2);
	}

	private void loadBG() {
		background = LoadGame.GetSprites(LoadGame.PAUSE_BG);
		backgroundWidth = (int)(background.getWidth() * Game.SCALE);
		backgroundHeight = (int)(background.getHeight() * Game.SCALE);
		backgroundX = (Game.GAME_WIDE / 2) - (backgroundWidth / 2);
		backgroundY = (int)(175 * Game.SCALE);
	}
	
	public void update() {
		pressPlay.update();
		restart.update();
		home.update();
		audio.update();
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(background, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);
		pressPlay.draw(g);
		restart.draw(g);
		home.draw(g);
		audio.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		audio.mouseDragged(e);
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		if(inButton(e, pressPlay))
			pressPlay.setMousePressed(true);
		else if(inButton(e, restart))
			restart.setMousePressed(true);
		else if(inButton(e, home))
			home.setMousePressed(true);
		else
			audio.mousePressed(e);
	}

	
	public void mouseReleased(MouseEvent e) {
		if(inButton(e, pressPlay)) {
			if(pressPlay.isMousePressed())
				play.unpause();	
			
		} else if(inButton(e, restart)) {
			if(restart.isMousePressed()) {
				play.resetGame();
				play.unpause();
			}
			
		} else if(inButton(e, home)) {
			if(home.isMousePressed()) {
				play.resetGame();
				Gamestates.state = Gamestates.MENU;
			}
			
		} else {
			audio.mouseReleased(e);
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
		else
			audio.mouseMoved(e);
		
	}

	
	public void keyPressed(KeyEvent e) {

	}

	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public boolean inButton(MouseEvent e, PauseButtons button) {
		return button.getArea().contains(e.getX(), e.getY());
	}
	
}
