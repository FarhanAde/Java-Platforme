package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import misc.LoadGame;
import static misc.Constants.GUI.VolumeSlider.*;

public class VolumeSlider extends PauseButtons {
	
	private BufferedImage[] images;
	private BufferedImage slider;
	// buttonX, min and max used for the sliding button and sets the range of  x values it can slide between
	private int indx = 0, buttonX, min, max;
	private boolean mouseOver, mousePressed;

	public VolumeSlider(int x, int y, int width, int height) {
		super((x + width/2), y, SLIDER_WIDTH, height);
		area.x -= SLIDER_WIDTH / 2;
		buttonX = x + width/2;
		this.x = x;
		this.width = width;
		min = x + SLIDER_WIDTH / 2;
		max = x + width - SLIDER_WIDTH / 2;
		loadImages();
	}
	
	private void loadImages() {
		BufferedImage temp = LoadGame.GetSprites(LoadGame.VOLUME_SLIDER);
		images = new BufferedImage[3];
		for(int i = 0; i < images.length; i++)
			images[i] = temp.getSubimage((i * DEF_SLIDER_WIDTH), 0, DEF_SLIDER_WIDTH, DEF_SLIDER_HEIGHT);
		
		slider = temp.getSubimage((3 * DEF_SLIDER_WIDTH), 0, DEF_BAR_WIDTH, DEF_SLIDER_HEIGHT);
	}

	public void update() {
		indx = 0;
		if(mouseOver)
			indx = 1;
		if(mousePressed)
			indx = 2;
	}
	
	// used to set x values when dragging slider
	public void changeX(int x) {
		if(x < min)
			buttonX = min;
		else if(x > max)
			buttonX = max;
		else
			buttonX = x;
		
		area.x = buttonX - SLIDER_WIDTH / 2;
	}
	
	public void draw(Graphics g) {
		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(images[indx], (buttonX - SLIDER_WIDTH / 2), y, SLIDER_WIDTH, height, null);
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
