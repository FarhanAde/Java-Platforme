package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import misc.LoadGame;
import static misc.Constants.*;

public class LevelHandler {
	
	private Game game;
	private BufferedImage[] levelMap;
	private Level lvlOne;
	
	public LevelHandler(Game game) {
		this.game = game;
		importLevelBorders();
		lvlOne = new Level(LoadGame.GetLvlData());
	}
	
	// takes all the blocks needed to build level from the image file and puts them into an array
	private void importLevelBorders() {
		BufferedImage image = LoadGame.GetSprites(LoadGame.LEVEL_OUTLAYS);
		levelMap = new BufferedImage[48];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 12; j++) {
				int indx = i*12 + j;
				levelMap[indx] = image.getSubimage(j*32, i*32, 32, 32);
			}
		
	}

	public void draw(Graphics g, int levelOffset) {
		for(int i = 0; i < lvlOne.getLvlData().length; i++)
			for(int j = 0; j < Game.TILES_WIDE; j++) {
				int indx = lvlOne.getTileIndx(j,  i);
				g.drawImage(levelMap[indx], (Game.TILES_SIZE * j), (Game.TILES_SIZE * i - levelOffset), Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}
	
	public void update() {
		
	}
	
	public Level getLevel() {
		return lvlOne;
	}
	
}
