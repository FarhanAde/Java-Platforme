package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;
import main.GamePanel;
import states.Gamestates;

import static misc.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {
	
	private GamePanel gamePanel;
	
	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	public void keyReleased(KeyEvent e) {
		switch(Gamestates.state) {
		case MENU: {
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		}
		case PLAY: {
			gamePanel.getGame().getPlayState().keyReleased(e);
			break;
		}
//		case INSTRUCTIONS: {
//			gamePanel.getGame().getInstructions().keyReleased(e);
//			break;
//		}
//		case LEADERBOARD: {
//			gamePanel.getGame().getLeaderboard().keyReleased(e);
//			break;
//		}
//		case SETTINGS: {
//			gamePanel.getGame().getSettings().keyReleased(e);
//			break;
//		}
		default:
			break;
		
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(Gamestates.state) {
		case MENU: {
			gamePanel.getGame().getMenu().keyPressed(e);
		}
		case PLAY: {
			gamePanel.getGame().getPlayState().keyPressed(e);
		}
//		case INSTRUCTIONS: {
//			gamePanel.getGame().getInstructions().keyPressed(e);
//		}
//		case LEADERBOARD: {
//			gamePanel.getGame().getLeaderboard().keyPressed(e);
//		}
//		case SETTINGS: {
//			gamePanel.getGame().getSettings().keyPressed(e);
//		}
		default:
			break;
		
		}
	}


}
