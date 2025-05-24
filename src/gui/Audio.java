package gui;

import static misc.Constants.GUI.PauseButtons.VOLUME_SIZE;
import static misc.Constants.GUI.VolumeSlider.BAR_WIDTH;
import static misc.Constants.GUI.VolumeSlider.SLIDER_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;
import states.Gamestates;

public class Audio {
	
	private VolumeButtons music, sfx;
	private VolumeSlider volumeSlider;
	
	public Audio() {
		init__volumeButtons();
		init__volumeSlider();
	}
	
	private void init__volumeSlider() {
		// define co-ords for where slider is displayed
		int sliderX = (int)(230 * Game.SCALE);
		int sliderY = (int)(435 * Game.SCALE);
		volumeSlider = new VolumeSlider(sliderX, sliderY, BAR_WIDTH, SLIDER_HEIGHT);
		
	}
	
	private void init__volumeButtons() {
		// define co-ords for where buttons are displayed (the both have the same x value, volumeX)
		int volumeX = (int)(380*Game.SCALE);
		int musicY = (int)(297*Game.SCALE);
		int sfxY = (int)(345*Game.SCALE);
		music = new VolumeButtons(volumeX, musicY, VOLUME_SIZE, VOLUME_SIZE);
		sfx = new VolumeButtons(volumeX, sfxY, VOLUME_SIZE, VOLUME_SIZE);
	}
	
	public void update() {
		music.update();
		sfx.update();
		volumeSlider.update();
	}
	
	public void draw(Graphics g) {
		music.draw(g);
		sfx.draw(g);
		volumeSlider.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(volumeSlider.isMousePressed())
			volumeSlider.changeX(e.getX());
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		if(inButton(e,music))
			music.setMousePressed(true);
		else if(inButton(e, sfx))
			sfx.setMousePressed(true);
		else if (inButton(e, volumeSlider))
			volumeSlider.setMousePressed(true);
	}

	
	public void mouseReleased(MouseEvent e) {
		if(inButton(e,music)) {
			if(music.isMousePressed())
				music.setMute(!music.isMute());
			
		} else if(inButton(e, sfx)) {
			if(sfx.isMousePressed())
				sfx.setMute(!sfx.isMute());
			
		}
		
		music.resetBooleans();
		sfx.resetBooleans();
		
		volumeSlider.resetBooleans();
	}

	
	public void mouseMoved(MouseEvent e) {
		music.setMouseOver(false);
		sfx.setMouseOver(false);
		
		volumeSlider.setMouseOver(false);
		
		if(inButton(e,music))
			music.setMouseOver(true);
		else if(inButton(e, sfx))
			sfx.setMouseOver(true);
		else if(inButton(e, volumeSlider))
			volumeSlider.setMouseOver(true);
		
	}
	
	public boolean inButton(MouseEvent e, PauseButtons button) {
		return button.getArea().contains(e.getX(), e.getY());
	}

}

