package gui;

import static misc.Constants.GUI.Buttons.BUTTON_HIGH;
import static misc.Constants.GUI.Buttons.BUTTON_WIDE;

import java.awt.Rectangle;

public class PauseButtons {
	
	protected int x, y, width, height;
	protected Rectangle area;
	
	public PauseButtons(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init__area();
		
	}
	
	// defines bounds of the buttons
	private void init__area() {
		area = new Rectangle(x, y, width, height);
	}
	
	public Rectangle getArea() {
		return area;
	}

	public void setArea(Rectangle area) {
		this.area = area;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


}
