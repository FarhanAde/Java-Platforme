package objects;

import main.Game;

public class Tokens extends Items {

	public Tokens(int x, int y, int itemType) {
		super(x, y, itemType);
		animate = true;
		createHitbox();
	}
	
	private void createHitbox() {
		init__hitbox(11, 21);
		drawOffsetX = (int)(4.5 * Game.SCALE);
		drawOffsetY = (int)(4.5 * Game.SCALE);
		hitbox.y += drawOffsetY + (int)(Game.SCALE);
		hitbox.x += drawOffsetX * 3;
	}

	public void update() {
		updateAnimeTick();
	}

}
