package states;

import static misc.Constants.GUI.NavButtons.NAV_SIZE;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gui.Audio;
import gui.NavButtons;
import main.Game;
import misc.LoadGame;

public class Settings extends States implements StatesMethods{
	
	private Audio audio;
	private BufferedImage settingsBgImage, bgImage;
	private int xSettings, ySettings, settingsWide, settingsHigh;
	private NavButtons home;

	public Settings(Game game) {
		super(game);
		loadBG();
		loadButtons();
		audio = game.getAudio();
	}
	
	private void loadButtons() {
		// define co-ords for where button is displayed
		int homeX = (int)(315 * Game.SCALE);
		int homeY = (int)(515 * Game.SCALE);
		home = new NavButtons(homeX, homeY, NAV_SIZE, NAV_SIZE, 2);
		
	}
	
	private void loadBG() {
		bgImage = LoadGame.GetSprites(LoadGame.BG_IMAGE);
		settingsBgImage = LoadGame.GetSprites(LoadGame.SETTINGS_BG);
		
		settingsWide = (int)(settingsBgImage.getWidth() * Game.SCALE);
		settingsHigh = (int)(settingsBgImage.getHeight() * Game.SCALE);
		xSettings = Game.GAME_WIDE / 2 - settingsWide / 2;
		ySettings = (int)(185 * Game.SCALE);
	}

	@Override
	public void update() {
		home.update();
		audio.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bgImage, 0, 0, Game.GAME_WIDE, Game.GAME_HIGH, null);
		g.drawImage(settingsBgImage, xSettings, ySettings, settingsWide, settingsHigh, null);
		
		home.draw(g);
		audio.draw(g);;
	}
	
	public void mouseDragged(MouseEvent e) {
		audio.mouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (inButton(e, home)) {
			home.setMousePressed(true);
		} else {
			audio.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (inButton(e, home)) {
			if (home.isMousePressed())
				Gamestates.state = Gamestates.MENU;
		} else {
			audio.mouseReleased(e);
		}
		
		home.resetBooleans();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		home.setMouseOver(false);
		
		if (inButton(e, home)) {
			home.setMouseOver(true);
		} else {
			audio.mouseMoved(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	
	public boolean inButton(MouseEvent e, NavButtons button) {
		return button.getArea().contains(e.getX(), e.getY());
	}


}
