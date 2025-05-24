package objects;

public class EndGameTile extends Items {

	public EndGameTile(int x, int y, int itemType) {
		super(x, y, itemType);
		init__hitbox(32, 32);
		drawOffsetX = 0;
		drawOffsetY = 0;
	}

}
