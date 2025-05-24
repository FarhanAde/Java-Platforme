package objects;

import main.Game;

public class Spikes extends Items {

	public Spikes(int x, int y, int itemType) {
		super(x, y, itemType);
		init__hitbox(32, 16);
		drawOffsetX = 0;
		drawOffsetY = (int)(Game.SCALE * 16);
		hitbox.y += drawOffsetY;
	}

}
