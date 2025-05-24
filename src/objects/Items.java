package objects;

import static misc.Constants.ANIME_SPEED;
import static misc.Constants.ItemConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Items {
	
	protected int x, y, itemType;
	protected Rectangle2D.Float hitbox;
	protected boolean animate, active = true;
	protected int animeTick, animeIndx;
	protected int drawOffsetX, drawOffsetY;
	
	protected Items(int x, int y, int itemType) {
		this.x = x;
		this.y = y;
		this.itemType = itemType;
	}
	
	protected void init__hitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int)(width * Game.SCALE), (int)(height * Game.SCALE));
	}
	
	protected void drawHitbox(Graphics g, int levelOffset) {
		// to debug the hitbox
		g.setColor(Color.CYAN);
		g.drawRect((int)hitbox.x, (int)hitbox.y - levelOffset, (int)hitbox.width, (int)hitbox.height);

	}
	
	protected void updateAnimeTick() {
		// loops through animation array, updating image in line with animation speed
		animeTick++;
		if(animeTick >= ANIME_SPEED) {
			animeTick = 0;
			animeIndx++;
			// loops through based on no. of sub images in the specific animation array being used
			if(animeIndx >= GetSprites(itemType)) {
				animeIndx = 0;
			}
		}
	}
	
	public void reset() {
		animeIndx = 0;
		animeTick = 0;
		animate = true;
		active = true;
	}

	public int getItemType() {
		return itemType;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}

	public int getDrawOffsetX() {
		return drawOffsetX;
	}

	public int getDrawOffsetY() {
		return drawOffsetY;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getAnimeIndx() {
		return animeIndx;
	}

}
