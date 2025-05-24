package objects;

public class Teleporter extends Items {

	public Teleporter(int x, int y, int itemType) {
		super(x, y, itemType);
		init__hitbox(32, 32);
		drawOffsetX = 0;
		drawOffsetY = 0;
	}

}
