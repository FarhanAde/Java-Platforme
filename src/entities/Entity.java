package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

// abstract class since objects of this class won't be created
// (only objects of classes that extend from it)
public abstract class Entity {
	
	protected float x, y;
	protected int width, height;
	protected int animeTick, animeIndx;
	protected int state;
	protected Rectangle2D.Float hitbox;
	protected Rectangle2D.Float attackArea;
	protected float downAccel;
	protected boolean airborne = false;
	protected float speed = .75f * Game.SCALE;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void drawHitbox(Graphics g, int levelOffset) {
		// to debug the hitbox
		g.setColor(Color.CYAN);
		g.drawRect((int)hitbox.x, (int)hitbox.y - levelOffset, (int)hitbox.width, (int)hitbox.height);

	}
	
	protected void drawAttackArea(Graphics g, int levelOffset) {
		g.setColor(Color.CYAN);
		g.drawRect((int)attackArea.x, (int)(attackArea.y - levelOffset), (int)attackArea.width, (int)attackArea.height);
	}
	
	protected void init__hitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int)(width * Game.SCALE), (int)(height * Game.SCALE));
	}
	
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	public int getState() {
		return state;
	}
	
	public int getAnimeIndx() {
		return animeIndx;
	}

}
