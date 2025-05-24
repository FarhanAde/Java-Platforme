package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.*;

public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private Game game;
	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		this.game = game;
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	private void setPanelSize() {
		// sets the size of the playable game window
		Dimension size = new Dimension(GAME_WIDE, GAME_HIGH);
		setPreferredSize(size);
	}
	
	public void gameUpdate() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
		
	}
	
	public Game getGame() {
		return game;
	}
	

}
