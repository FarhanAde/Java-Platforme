package misc;

import java.awt.geom.Rectangle2D;
import static misc.Constants.Directions.*;

import main.Game;

public class Helpers {
	
	// Uses IsntSolid to check whether each corner of the player's hitbox should be allowed to overlap
	// (i.e. if the player should be allowed to move there)
	public static boolean CanMove(float x, float y, float width, float height, int[][] levelData) {
		if (IsntSolid(x, y, levelData)) {
			if (IsntSolid(x + width, y + height, levelData)) {
				if (IsntSolid(x + width, y, levelData)) {
					if (IsntSolid(x, y + height, levelData)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Checks if a tile is solid
	private static boolean IsntSolid(float x, float y, int[][] levelData) {
		int heightMaxxing = (int) (levelData.length * Game.TILES_SIZE);
		if (x >= Game.GAME_WIDE || x<0) 
			return false;
		if (y >= heightMaxxing || y<0) {
			return false;
		}

		float indxX = x / Game.TILES_SIZE;
		float indxY = y / Game.TILES_SIZE;

		return IsTileEmpty((int)indxX, (int)indxY, levelData);
	}
	
	private static boolean IsTileEmpty(int xTile, int yTile, int[][] levelData) {
		int value = levelData[yTile][xTile];

		if (value != 11 || value < 0 || value >= 48)
			return false;
		return true;
	}
	
	// gets the entity's y position in relation to the floor
	public static float GetYPosition(Rectangle2D.Float hitbox, float flySpeed) {
		int tile = (int)(hitbox.y / Game.TILES_SIZE);
		// falling down
		if (flySpeed > 0) {
			int YPos = tile * Game.TILES_SIZE;
			int offsetY = (int)(Game.TILES_SIZE - hitbox.height);
			return YPos + offsetY - 1;
		// Jumping
		} else {
			return tile * Game.TILES_SIZE;
		}

	}
	
	// gets the entity's x position in relation to the walls
	public static float GetXPosition(Rectangle2D.Float hitbox, float speedX) {
		int tile = (int)(hitbox.x / Game.TILES_SIZE);
		// going right
		if (speedX > 0) {
			int xTile = tile * Game.TILES_SIZE;
			int offsetX = (int)(Game.TILES_SIZE - hitbox.width);
			return xTile + offsetX - 1;
		// going left
		} else {
			return tile * Game.TILES_SIZE;
		}
	}
	
	public static boolean OnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
		// checks if the bottom corners of the hitbox are touching the ground
		if (IsntSolid(hitbox.x, (hitbox.y + hitbox.height + 1), levelData)) {
			if(IsntSolid((hitbox.x + hitbox.width), (hitbox.y + hitbox.height + 1), levelData)) {
				return false;
			}
		}
		return true;
	}
	
	// used for enemy to check if it is about to walk off an edge
	public static boolean OnEdge(Rectangle2D.Float hitbox, float speed, int direction, int[][] levelData) {
		if (direction == RIGHT)
			return IsntSolid((hitbox.x + hitbox.width + speed), (hitbox.y + hitbox.height + 1), levelData);
		else
			return IsntSolid((hitbox.x - speed), (hitbox.y + hitbox.height + 1), levelData);
	}
	
	// checks if all tiles between two points can be walked on
	public static boolean CanMoveBetween(int initX, int finalX, int y, int[][] levelData) {
		for (int i = 0; i < finalX - initX; i++) {
			if (!IsTileEmpty(initX + i, y, levelData))
				return false;
			if (IsTileEmpty(initX + i, y + 1, levelData))
				return false;
		}
		
		return true;
	}
	
	// checks if there is a clear path between two objects
	public static boolean ClearPath(int[][] levelData, Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2, int yTile) {
		int xTile1 = (int) (hitbox1.x / Game.TILES_SIZE);
		int xTile2 = (int) (hitbox2.x / Game.TILES_SIZE);
		
		// checks tiles in between hitboxes for any obstacles
		if (xTile1 > xTile2)
			return CanMoveBetween(xTile2, xTile1, yTile, levelData);
		else
			return CanMoveBetween(xTile1, xTile2, yTile, levelData);
	}
	

}
