package objects;

import static misc.Constants.*;
import static misc.Constants.ItemConstants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import levels.Level;
import main.Game;
import misc.LoadGame;
import states.PlayState;

public class ItemHandler {
	
	private PlayState play;
	private BufferedImage[] tokenImages;
	private BufferedImage counterImage;
	private BufferedImage spikesImage;
	private BufferedImage teleporterImage;
	private BufferedImage endgameTileImage;
	private ArrayList<Tokens> tokens;
	private ArrayList<Spikes> spikes;
	private ArrayList<Teleporter> teleporters;
	private ArrayList<EndGameTile> endgameTile;
	
	private Font  f2  = new Font("Consolas",  Font.BOLD | Font.ITALIC, (int)(20 * Game.SCALE));
	
	public ItemHandler(PlayState play) {
		this.play = play;
		loadImages();
		addItems();
	}
	
	private void addItems() {
		tokens = new ArrayList<>(LoadGame.GetTokens());
		spikes = LoadGame.GetSpikes();
		teleporters = LoadGame.GetTeleporter();
		endgameTile = LoadGame.GetEndgameTile();
	}

	private void loadImages() {
		BufferedImage tokenSprites = LoadGame.GetSprites(LoadGame.TOKENS);
		tokenImages = new BufferedImage[35];
		
		for(int i = 0; i < tokenImages.length; i++)
			tokenImages[i] = tokenSprites.getSubimage(12 * i, 0, 12, 20);
		
		counterImage = LoadGame.GetSprites(LoadGame.COUNTER);
		spikesImage = LoadGame.GetSprites(LoadGame.TRAP);
		teleporterImage = LoadGame.GetSprites(LoadGame.UP_ARROW);
		endgameTileImage = LoadGame.GetSprites(LoadGame.END_LEVEL);
	
	}
	
	public void update() {
		for(Tokens t: tokens)
			if(t.isActive())
				t.update();
		
	}
	
	public void draw(Graphics g, int levelOffset) {
		drawTokens(g, levelOffset);
		drawSpikes(g, levelOffset);
		drawTeleporters(g, levelOffset);
		drawCounter(g);
		drawEndgameTile(g, levelOffset);
	}
	
	private void drawSpikes(Graphics g, int levelOffset) {
		for(Spikes s: spikes) {
			g.drawImage(spikesImage, (int)(s.getHitbox().x),
					(int)(s.getHitbox().y - s.getDrawOffsetY() - levelOffset), SPIKES_WIDTH, SPIKES_HEIGHT, null);
		}
	}

	private void drawTokens(Graphics g, int levelOffset) {
		for(Tokens t: tokens) {
			if(t.isActive())
				g.drawImage(tokenImages[t.getAnimeIndx()], (int)(t.getHitbox().x - t.getDrawOffsetX()),
						(int)(t.getHitbox().y - t.getDrawOffsetY() - levelOffset), TOKEN_WIDTH, TOKEN_HEIGHT, null);
				// t.drawHitbox(g, levelOffset);
		}
	}
	
	private void drawTeleporters(Graphics g, int levelOffset) {
		for(Teleporter te: teleporters) {
			g.drawImage(teleporterImage, (int)(te.getHitbox().x),
					(int)(te.getHitbox().y - te.getDrawOffsetY() - levelOffset), TP_WIDTH, TP_HEIGHT, null);
		}
	}
	
	private void drawEndgameTile(Graphics g, int levelOffset) {
		for(EndGameTile end: endgameTile) {
			g.drawImage(endgameTileImage, (int)(end.getHitbox().x),
					(int)(end.getHitbox().y - end.getDrawOffsetY() - levelOffset), TP_WIDTH, TP_HEIGHT, null);
		}
	}

	private void drawCounter(Graphics g) {
		g.drawImage(counterImage, (int)(620 * Game.SCALE), (int)(10 * Game.SCALE), COUNTER_WIDTH, COUNTER_HEIGHT, null);
		g.setColor(MY_BLUE);
		g.setFont(f2);
		g.drawString(String.valueOf(Game.score), (int)(630 * Game.SCALE), (int)(40 * Game.SCALE));
	}
	
	// checks if the token has come into contact w/ player
	// (and so has been 'picked up')
	public void pickup(Rectangle2D.Float hitbox) {
		for(Tokens t: tokens) {
			if(t.isActive()) {
				if(hitbox.intersects(t.getHitbox())) {
					t.setActive(false);
					Game.score += 1;
					return;
				}
			}
		}
	}
	
	// check if the player has touched the spikes
	public void dontTouchTheSpikes(Player player) {
		for(Spikes s: spikes) {
			if(s.getHitbox().intersects(player.getHitbox())) {
				player.unalive(player.getPlayerNo(player));
			}
		}
	}
	
	// change coordinates of player if they touch the teleporter arrow
	public void callTheElevator(Player player) {
		for(Teleporter te: teleporters) {
			if(te.getHitbox().intersects(player.getHitbox())) {
				player.beamUp();
			}
		}
	}
	
	// end the game when the player touches the end tile
	public void endTheGame(Player playerOne, Player playerTwo) {
		for(EndGameTile end: endgameTile) {
			if(end.getHitbox().intersects(playerOne.getHitbox()) && end.getHitbox().intersects(playerTwo.getHitbox())) {
				play.setLevelComplete(true);
			}
		}
	}

	public void resetAllItems() {
		Game.score = 0;
		for(Tokens t: tokens)
			t.reset();
	}

}
