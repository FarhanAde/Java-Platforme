package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.GamePanel;
import states.Gamestates;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private GamePanel gamePanel;
	
	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestates.state) {
		case PLAY:
			gamePanel.getGame().getPlayState().mouseDragged(e);
			break;
		case SETTINGS:
			gamePanel.getGame().getSettings().mouseDragged(e);
			break;
		default:
			break;

		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestates.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseMoved(e);
			break;
		case PLAY:
			gamePanel.getGame().getPlayState().mouseMoved(e);
			break;
		case INSTRUCTIONS: {
			gamePanel.getGame().getInstructions().mouseMoved(e);
		}
		case LEADERBOARD: {
			gamePanel.getGame().getLeaderboard().mouseMoved(e);
		}
		case SETTINGS: {
			gamePanel.getGame().getSettings().mouseMoved(e);
		}
		default:
			break;

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (Gamestates.state) {
		case PLAY:
			gamePanel.getGame().getPlayState().mouseClicked(e);
			break;
		default:
			break;

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestates.state) {
		case MENU:
			gamePanel.getGame().getMenu().mousePressed(e);
			break;
		case PLAY:
			gamePanel.getGame().getPlayState().mousePressed(e);
			break;
		case INSTRUCTIONS: {
			gamePanel.getGame().getInstructions().mousePressed(e);
		}
		case LEADERBOARD: {
			gamePanel.getGame().getLeaderboard().mousePressed(e);
		}
		case SETTINGS: {
			gamePanel.getGame().getSettings().mousePressed(e);
		}
		default:
			break;

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestates.state) {
		case MENU:
			gamePanel.getGame().getMenu().mouseReleased(e);
			break;
		case PLAY:
			gamePanel.getGame().getPlayState().mouseReleased(e);
			break;
		case INSTRUCTIONS: {
			gamePanel.getGame().getInstructions().mouseReleased(e);
		}
		case LEADERBOARD: {
			gamePanel.getGame().getLeaderboard().mouseReleased(e);
		}
		case SETTINGS: {
			gamePanel.getGame().getSettings().mouseReleased(e);
		}
		default:
			break;

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
