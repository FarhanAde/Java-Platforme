package states;

import static misc.Constants.GUI.NavButtons.NAV_SIZE;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gui.NavButtons;
import main.Game;
import misc.LoadGame;
import static misc.Constants.GUI.NavButtons.*;

public class Instructions extends States implements StatesMethods {
	
	private BufferedImage instructionsBgImage, bgImage;
	private int xInstructions, yInstructions, instructionsWide, instructionsHigh;
	private NavButtons home;

	public Instructions(Game game) {
		super(game);
		loadBG();
		loadButtons();
	}
	
	private void loadButtons() {
		// define co-ords for where button is displayed
		int homeX = (int)(315 * Game.SCALE);
		int homeY = (int)(535 * Game.SCALE);
		home = new NavButtons(homeX, homeY, NAV_SIZE, NAV_SIZE, 2);
		
	}
	
	private void loadBG() {
		bgImage = LoadGame.GetSprites(LoadGame.BG_IMAGE);
		instructionsBgImage = LoadGame.GetSprites(LoadGame.INSTRUCT_BG);
		
		instructionsWide = (int)(instructionsBgImage.getWidth() * Game.SCALE);
		instructionsHigh = (int)(instructionsBgImage.getHeight() * Game.SCALE);
		xInstructions = Game.GAME_WIDE / 2 - instructionsWide / 2;
		yInstructions = (int)(200 * Game.SCALE);
	}

	@Override
	public void update() {
		home.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bgImage, 0, 0, Game.GAME_WIDE, Game.GAME_HIGH, null);
		g.drawImage(instructionsBgImage, xInstructions, yInstructions, instructionsWide, instructionsHigh, null);
		
		home.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (inButton(e, home)) {
			home.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (inButton(e, home)) {
			if (home.isMousePressed())
				Gamestates.state = Gamestates.MENU;
		}
		
		home.resetBooleans();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		home.setMouseOver(false);
		
		if (inButton(e, home)) {
			home.setMouseOver(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
//			Gamestates.state = Gamestates.MENU;
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	
	public boolean inButton(MouseEvent e, NavButtons button) {
		return button.getArea().contains(e.getX(), e.getY());
	}

}
