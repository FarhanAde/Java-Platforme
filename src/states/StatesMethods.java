package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

// using interface ensure Menu class uses all the methods,
// and throws an error if they aren't all used
public interface StatesMethods {
	
	public void update();
	public void draw(Graphics g);
	
	public void mouseClicked(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);

}



