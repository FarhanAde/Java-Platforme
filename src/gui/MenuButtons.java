package gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import misc.LoadGame;
import states.Gamestates;
import static misc.Constants.GUI.Buttons.*;

public class MenuButtons {
	
	private int x, y, row, indx;
	private int centreOffset = BUTTON_WIDE / 2;
	private Gamestates state;
	private BufferedImage[] images;
	private boolean mouseOver, mousePressed;
	private Rectangle area;
	
	public MenuButtons(int x, int y, int row, Gamestates state) {
		this.x = x;
		this.y = y;
		this.row = row;
		this.state = state;
		loadImages();
		init___area();
	}

	// defines area of buttons
	private void init___area() {
		area = new Rectangle((x - centreOffset), (int)(y+(150*Game.SCALE)), BUTTON_WIDE, BUTTON_HIGH);
	}

	private void loadImages() {
		images = new BufferedImage[3];
		BufferedImage temp = LoadGame.GetSprites(LoadGame.MENU_BUTTONS);
		for(int i = 0; i < images.length; i++) {
			images[i] = temp.getSubimage((i * DEF_BUTTON_WIDE), (row * DEF_BUTTON_HIGH), DEF_BUTTON_WIDE, DEF_BUTTON_HIGH);
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(images[indx], (x - centreOffset), (int)(y+(150*Game.SCALE)), BUTTON_WIDE, BUTTON_HIGH, null);
	}
	
	// return image based on mouse action (if button only hovered over show as slightly shaded etc.)
	public void update() {
		indx = 0;
		if (mouseOver)
			indx = 1;
		if (mousePressed)
			indx = 2;
	}
	
	public void changeState() {
		Gamestates.state = state;
	}
	
	// reset whether button has been hovered over/ pressed
	public void resetClicks() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Rectangle getArea() {
		return area;
	}
	

}
