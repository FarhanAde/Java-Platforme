package states;

import java.awt.event.MouseEvent;

import gui.MenuButtons;
import main.Game;

// separates code for each gamestate so only the code for the state we are currently in is run
public class States {
	
	protected Game game;
	
	public States(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	public boolean inButton(MouseEvent e, MenuButtons button) {
		return button.getArea().contains(e.getX(), e.getY());
	}

}


