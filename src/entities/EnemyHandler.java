package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import misc.LoadGame;
import states.PlayState;
import static misc.Constants.EnemyConstants.*;

public class EnemyHandler{
	
	private PlayState play;
	private BufferedImage[][] enemyAnimes;
	private ArrayList<Enemies> enemies = new ArrayList<>();
	
	public EnemyHandler(PlayState play) {
		this.play = play;
		loadEnemyAnimes();
		addEnemies();
	}
	
	private void addEnemies() {
		enemies = LoadGame.GetEnemies();
		// System.out.println("No. of enemies: " + enemies.size());
	}

	public void update(int[][] levelData, Player player1, Player player2) {
		for(Enemies en : enemies)
			if(!en.isDead())
				en.update(levelData, player1, player2);
	}
	
	public void draw(Graphics g, int levelOffset) {
		for (Enemies en : enemies) {
			if(!en.isDead())
				g.drawImage(enemyAnimes[en.getState()][en.getAnimeIndx()], ((int)en.getHitbox().x - ENEMY_X_DRAWOFFSET + en.xFlip), ((int)en.getHitbox().y - levelOffset - ENEMY_Y_DRAWOFFSET), ENEMY_WIDTH * en.widthFlip, ENEMY_HEIGHT, null);
				 // en.drawHitbox(g, levelOffset);
				 // en.drawAttackArea(g, levelOffset);
		}
	
	}
	
	// checks to see if an enemy has been hit by a player
	public void checkEnemyHit(Rectangle2D.Float attackArea) {
		for(Enemies en : enemies) {
			if(!en.isDead())
				if(attackArea.intersects(en.getHitbox())) {
					en.injure(10);
					return;
			}
		}
	}
	
	
	private void loadEnemyAnimes() {
		// creates 5x9 buffered image to hold all animations
		enemyAnimes = new BufferedImage[5][9];
		BufferedImage image = LoadGame.GetSprites(LoadGame.ENEMY_SPRITES);
		for(int i = 0; i < enemyAnimes.length; i++)
			for(int j = 0; j < enemyAnimes[i].length; j++)
				enemyAnimes[i][j] = image.getSubimage((j * DEF_ENEMY_WIDTH), (i * DEF_ENEMY_HEIGHT), 
						DEF_ENEMY_WIDTH, DEF_ENEMY_HEIGHT);
	}
	
	// reset attributes and booleans for all the enemies when the game resets
	public void resetAll() {
		for(Enemies en : enemies) {
			en.resetEnemy();
		}
	}

}
