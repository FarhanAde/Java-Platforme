package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import misc.LoadGame;
import static misc.Constants.GUI.NavButtons.*;

public class NavButtons extends PauseButtons {
	
	private BufferedImage[] images;
	private int row, indx;
	private boolean mouseOver, mousePressed;

	public NavButtons(int x, int y, int width, int height, int row) {
		super(x, y, width, height);
		this.row = row;
		loadImages();
	}
	
	private void loadImages() {
		BufferedImage temp = LoadGame.GetSprites(LoadGame.NAV_BUTTONS);
		images = new BufferedImage[3];
		for(int i = 0; i < images.length; i++)
			images[i] = temp.getSubimage((i * DEF_NAV_SIZE), (row * DEF_NAV_SIZE), DEF_NAV_SIZE, DEF_NAV_SIZE);
	}

	public void update() {
		indx = 0;
		if(mouseOver)
			indx = 1;
		if(mousePressed)
			indx = 2;
	}
	
	public void draw(Graphics g) {
		g.drawImage(images[indx], x, y, width, height, null);
	}
	
	public void resetBooleans() {
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
	
	

}
