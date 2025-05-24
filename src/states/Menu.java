package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import gui.MenuButtons;
import main.Game;
import misc.LoadGame;

public class Menu extends States implements StatesMethods{
	
	private MenuButtons[] buttons = new MenuButtons[5];
	private BufferedImage menuBgImage, bgImage;
	private int xMenu, yMenu, menuWide, menuHigh;

	public Menu(Game game) {
		super(game);
		loadBG();
		loadButtons();
		bgImage = LoadGame.GetSprites(LoadGame.BG_IMAGE);
	}
	
	private void loadBG() {
		menuBgImage = LoadGame.GetSprites(LoadGame.MENU_BG);
		menuWide = (int)(menuBgImage.getWidth() * Game.SCALE);
		menuHigh = (int)(menuBgImage.getHeight() * Game.SCALE);
		xMenu = (Game.GAME_WIDE / 2) - (menuWide / 2);
		yMenu = (int)(100 * Game.SCALE);
	}

	private void loadButtons() {
		buttons[0] = new MenuButtons((Game.GAME_WIDE / 2), (int)(55 * Game.SCALE), 0, Gamestates.PLAY);
		buttons[1] = new MenuButtons((Game.GAME_WIDE / 2), (int)(145 * Game.SCALE), 1, Gamestates.SETTINGS);
		buttons[2] = new MenuButtons((Game.GAME_WIDE / 2), (int)(235 * Game.SCALE), 3, Gamestates.INSTRUCTIONS);
		buttons[3] = new MenuButtons((Game.GAME_WIDE / 2), (int)(325 * Game.SCALE), 4, Gamestates.LEADERBOARD);
		buttons[4] = new MenuButtons((Game.GAME_WIDE / 2), (int)(415 * Game.SCALE), 2, Gamestates.QUIT);
	}

	@Override
	public void update() {
		for(MenuButtons button: buttons) {
			button.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bgImage, 0, 0, Game.GAME_WIDE, Game.GAME_HIGH, null);
		g.drawImage(menuBgImage, xMenu, yMenu, menuWide, menuHigh, null);
		for(MenuButtons button: buttons) {
			button.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButtons button: buttons) {
			if(inButton(e, button)) {
				button.setMousePressed(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		for (MenuButtons button: buttons) {
			if (inButton(e, button)) {
				// also need to check if button was pressed (i.e. not if mouse was pressed elsewhere then dragged inside button)
				if (button.isMousePressed()) {
					button.changeState();
				break;
				}
			}
		}
		
		buttonReset();
	}

	private void buttonReset() {
		
		for (MenuButtons button: buttons) {
			button.resetClicks();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		for (MenuButtons button: buttons) {
			button.setMouseOver(false);
		}
		
		for (MenuButtons button: buttons) {
			if (inButton(e, button)) {
				button.setMouseOver(true);
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	
}
