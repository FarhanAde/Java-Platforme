package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import misc.LoadGame;
import static misc.Constants.GUI.PauseButtons.*;

public class VolumeButtons extends PauseButtons {

	private BufferedImage[][] volumeImages;
	// mute, row and column are used to decided whether to display 🚫 symbols
	private boolean mouseOver, mousePressed, mute;
	private int row, column;
	
	public VolumeButtons(int x, int y, int width, int height) {
		super(x, y, width, height);
		loadVolumeImages();
	}
	
	private void loadVolumeImages() {
		BufferedImage temp = LoadGame.GetSprites(LoadGame.VOLUME_ON_OFF);
		volumeImages = new BufferedImage[2][3];
		for (int i = 0; i < volumeImages.length; i++)
			for (int j = 0; j < volumeImages[i].length; j++)
				volumeImages[i][j] = temp.getSubimage((j * DEF_VOLUME_SIZE), (i * DEF_VOLUME_SIZE), DEF_VOLUME_SIZE, DEF_VOLUME_SIZE);
	}
	
	public void update() {
		if(mute)
			row = 1;
		else
			row = 0;
		
		column = 0;
		if(mouseOver)
			column = 1;
		if(mousePressed)
			column = 2;
	}
	
	public void resetBooleans() {
		mouseOver = false;
		mousePressed = false;
	}
	
	public void draw(Graphics g) {
		g.drawImage(volumeImages[row][column], x, y, width, height, null);
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

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}
	
	

}
