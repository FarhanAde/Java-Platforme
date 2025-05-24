package misc;

import static misc.Constants.ItemConstants.*;
import static misc.Constants.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Enemies;
import main.Game;
import objects.EndGameTile;
import objects.Spikes;
import objects.Teleporter;
import objects.Tokens;

public class LoadGame {
	
	public static final String[] PLAYER_POSES = {"spriteActionv7.png", "spriteActionv6.png"};
	public static final String LEVEL_OUTLAYS = "levelBordersv2.png";
	public static final String LVL_ONE_DATA = "lvlOneDataTallv5.png";
	public static final String MENU_BUTTONS = "menuButtons.png";
	public static final String MENU_BG = "menuBackgroundv2.png";
	public static final String PAUSE_BG = "pauseBackground.png";
	public static final String INSTRUCT_BG = "instructions.png";
	public static final String SETTINGS_BG = "settingsBG.png";
	public static final String LB_BG = "leaderboardBackgroundv2.png";
	public static final String VOLUME_ON_OFF = "volumeButtons.png";
	public static final String NAV_BUTTONS = "navigationButtons.png";
	public static final String VOLUME_SLIDER = "volumeSlider.png";
	public static final String BG_IMAGE = "vertical-game-background.png";
	public static final String PLAYING_BG = "playingBackgroundv6-transformed.png";
	public static final String ENEMY_SPRITES = "enemy_sprite.png";
	public static final String[] HEALTHBAR = {"health-bar-red.png", "health-bar-blue.png"};
	public static final String TOKENS = "tokens.png";
	public static final String COUNTER = "counter.png";
	public static final String LOSE_SCREEN = "loss-screen.png";
	public static final String TRAP = "spikes.png";
	public static final String UP_ARROW = "teleporter.png";
	public static final String LEVEL_COMPLETE = "levelCompleted.png";
	public static final String END_LEVEL = "finish.png";
	
	
	// loads images for sprite and levels as required 
	public static BufferedImage GetSprites(String filename) {
		BufferedImage image = null;
		InputStream is = LoadGame.class.getResourceAsStream("/" + filename);
		// validation for loading in images (throws error if file unavailable for example)
		try {
			image = ImageIO.read(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// BufferedImage newImage = INVERT_COLOURS.filter(image, null);
		
		return image;
		
	}
	
	public static ArrayList<Enemies> GetEnemies() {
		BufferedImage image = GetSprites(LVL_ONE_DATA);
		ArrayList<Enemies> list = new ArrayList<>();
		
		for(int i = 0; i < image.getHeight(); i++) 
			for(int j = 0; j < image.getWidth(); j++) {
				Color colour = new Color(image.getRGB(j,  i));
				
				// using the RGB value of pixels in the image file to denote position of enemies
				int num = colour.getGreen();
				if(num == 0)
					list.add(new Enemies((j * Game.TILES_SIZE), (i * Game.TILES_SIZE)));
			}
		
		return list;
	}
	
	public static ArrayList<Tokens> GetTokens() {
		BufferedImage image = GetSprites(LVL_ONE_DATA);
		ArrayList<Tokens> list = new ArrayList<>();
		
		for(int i = 0; i < image.getHeight(); i++) 
			for(int j = 0; j < image.getWidth(); j++) {
				Color colour = new Color(image.getRGB(j,  i));
				
				// using the RGB value of pixels in the image file to denote position of tokens
				int num = colour.getBlue();
				if(num == TOKEN)
					list.add(new Tokens((j * Game.TILES_SIZE), (i * Game.TILES_SIZE), num));
			}
		
		return list;
	}
	
	public static ArrayList<Spikes> GetSpikes() {
		BufferedImage image = GetSprites(LVL_ONE_DATA);
		ArrayList<Spikes> list = new ArrayList<>();
		
		for(int i = 0; i < image.getHeight(); i++) 
			for(int j = 0; j < image.getWidth(); j++) {
				Color colour = new Color(image.getRGB(j,  i));
				
				// using the RGB value of pixels in the image file to denote position of spikes
				int num = colour.getBlue();
				if(num == SPIKES)
					list.add(new Spikes((j * Game.TILES_SIZE), (i * Game.TILES_SIZE), SPIKES));
			}
		
		return list;
	}
	
	public static ArrayList<Teleporter> GetTeleporter() {
		BufferedImage image = GetSprites(LVL_ONE_DATA);
		ArrayList<Teleporter> list = new ArrayList<>();
		
		for(int i = 0; i < image.getHeight(); i++) 
			for(int j = 0; j < image.getWidth(); j++) {
				Color colour = new Color(image.getRGB(j,  i));
				
				// using the RGB value of pixels in the image file to denote position of spikes
				int num = colour.getBlue();
				if(num == TELEPORTER)
					list.add(new Teleporter((j * Game.TILES_SIZE), (i * Game.TILES_SIZE), TELEPORTER));
			}
		
		return list;
	}
	
	public static ArrayList<EndGameTile> GetEndgameTile() {
		BufferedImage image = GetSprites(LVL_ONE_DATA);
		ArrayList<EndGameTile> list = new ArrayList<>();
		
		for(int i = 0; i < image.getHeight(); i++) 
			for(int j = 0; j < image.getWidth(); j++) {
				Color colour = new Color(image.getRGB(j,  i));
				
				// using the RGB value of pixels in the image file to denote position of spikes
				int num = colour.getBlue();
				if(num == ENDGAME)
					list.add(new EndGameTile((j * Game.TILES_SIZE), (i * Game.TILES_SIZE), ENDGAME));
			}
		
		return list;
	}
	
	// Build the level  using an image file where each pixel is a position on the level
	public static int[][] GetLvlData() {
		
		BufferedImage image = GetSprites(LVL_ONE_DATA);
		int[][] levelData = new int[image.getHeight()][image.getWidth()];
		
		for(int i = 0; i < image.getHeight(); i++) 
			for(int j = 0; j < image.getWidth(); j++) {
				Color colour = new Color(image.getRGB(j,  i));
				
				// validation to ensure RGB value is in range of no. of tiles
				int num = colour.getRed();
				if(num >= 48)
					num = 0;
				levelData[i][j] = num;
			}
		
		return levelData;
	}
	
	
	
}
	
