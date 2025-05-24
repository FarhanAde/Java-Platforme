package entities;

import static misc.Constants.EnemyConstants.*;
import static misc.Constants.Directions.*;
import static misc.Constants.*;
import static misc.Helpers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Enemies extends Entity {
	
	public int xFlip = 0;
	public int widthFlip = 1;
	
	private boolean initialUpdate = true;
	private float speed = 0.25f * Game.SCALE;
	
	private float attackRange = Game.TILES_SIZE;
	private int direction = LEFT;
	private int yTile;
	private int attackAreaOffset;
	private int maxHealth;
	private int health;
	private boolean dead = false;
	private boolean hasCheckedAttack;

	public Enemies(float x, float y) {
		super(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
		init__hitbox(22, 19);
		init__attackArea();
		maxHealth = 10;
		health = maxHealth;
		speed = 0.25f * Game.SCALE;
	}
	
	private void init__attackArea() {
		attackArea = new Rectangle2D.Float(x, y, (int)(82 * Game.SCALE), (int)(19 * Game.SCALE));
		attackAreaOffset = (int)(30 * Game.SCALE);
	}

	private void updateAnimeTick() {
		// loops through animation array, updating image in line with animation speed
		animeTick++;
		if(animeTick >= ANIME_SPEED) {
			animeTick = 0;
			animeIndx++;
			// loops through based on no. of sub images in the specific animation array being used
			if(animeIndx >= GetSprites(state)) {
				animeIndx = 0;
				
				switch(state) {
				case ATTACKING:
				case HITTING:
					state = IDLING;
					break;
				case UNALIVE:
					dead = true;
					break;
				}
			}
		}
	}
	
	protected void resetAnimeTick(int enemyState) {
		this.state = enemyState;
		animeTick = 0;
		animeIndx = 0;
	}
	
	public void update(int[][] levelData, Player player1, Player player2) {
		updateMove(levelData, player1, player2);
		updateAnimeTick();
		updateAttackArea();
	}
	
	private void updateAttackArea() {
		attackArea.x = hitbox.x - attackAreaOffset;
		attackArea.y = hitbox.y;
	}
	
	// if a player hits an enemy remove some of that enemy's health
	public void injure(int num) {
		health -= num;
		if(health <= 0)
			resetAnimeTick(UNALIVE);
		else
			resetAnimeTick(HITTING);
	}
	
	// check if an enemy has hit a player and subtract the necessary health from that player
	private void checkPlayerHit(Player player, int playerNo) {
		if(attackArea.intersects(player.hitbox))
			player.alterHealth(-20, playerNo);
		hasCheckedAttack = true;
	}

	private void updateMove(int[][] levelData, Player player1, Player player2) {
		// check if enemy is in the air when the game starts
		if(initialUpdate) {
			if(!OnFloor(hitbox, levelData))
				airborne = true;
			initialUpdate = false;
		}
		
		// cause enemy to fall to the ground if it is in the air
		if(airborne) {
			if(CanMove(hitbox.x, hitbox.y + downAccel, hitbox.width, hitbox.height, levelData)) {
				hitbox.y += downAccel;
				downAccel += GFORCE;
			} else {
				airborne = false;
				hitbox.y = GetYPosition(hitbox, downAccel);
				yTile = (int)(hitbox.y / Game.TILES_SIZE);
			}
			
		} else {
			
			switch(state) {
			case IDLING:
				resetAnimeTick(RUN);
				break;
			case RUN:
				
				if(playerInSight(levelData, player1)) {
					moveTowardsPlayer(player1);
					if(inAttackRange(player1))
						resetAnimeTick(ATTACKING);
				} else if(playerInSight(levelData, player2)) {
					moveTowardsPlayer(player2);
					if(inAttackRange(player2))
						resetAnimeTick(ATTACKING);
				}
				
//				if(playerInSight(levelData, player1))
//					moveTowardsPlayer(player1);
//				else if(playerInSight(levelData, player2))
//					moveTowardsPlayer(player2);
//				
//				if(inAttackRange(player1))
//					resetAnimeTick(ATTACKING);
//				else if(inAttackRange(player2))
//					resetAnimeTick(ATTACKING);
				
				// while enemy is running about, check if it is on an edge
				// and change direction if so
				float speedX = 0;
				if(direction == RIGHT) {
					speedX += speed;
					xFlip = width;
					widthFlip = -1;
				} else {
					speedX -= speed;
					xFlip = 0;
					widthFlip = 1;
				}
				
				if(CanMove(hitbox.x + speedX, hitbox.y, hitbox.width, hitbox.height, levelData))
					if(!OnEdge(hitbox, speedX, direction, levelData)) {
						hitbox.x += speedX;
						return;
					}
				
				changeDirection();
				
				break;
			
			case ATTACKING:
				if(animeIndx == 0)
					hasCheckedAttack = false;
				if(animeIndx == 3 && !hasCheckedAttack) {
					checkPlayerHit(player1, 0);
					checkPlayerHit(player2, 1);
				}
				break;
			case HITTING:
				break;
			}
		}
		
	}
	
	private void changeDirection() {
		if(direction == RIGHT)
			direction = LEFT;
		else
			direction = RIGHT;
	}
	
	// checks to see if either player is in an enemy's line of sight
	// i.e. if the enemy is on the same y-plane as either player and within a suitable x-value range 
	private boolean playerInSight(int[][] levelData, Player player) {
		int playerY = (int) (player.getHitbox().y / Game.TILES_SIZE);
		if (playerY == yTile)
			if (inVisualRange(player)) {
				if (ClearPath(levelData, hitbox, player.hitbox, yTile))
					return true;
			}
		return false;
	}
	
	// check is player is to the left or right of the enemy
	private void moveTowardsPlayer(Player player) {
		if(player.hitbox.x > hitbox.x)
			direction = RIGHT;
		else
			direction = LEFT;
	}
	
	private boolean inVisualRange(Player player) {
		int distanceBetween = (int) (Math.abs(player.hitbox.x - hitbox.x));
		return distanceBetween <= attackRange * 5;
	}
	
	private boolean inAttackRange(Player player) {
		int distanceBetween = (int) (Math.abs(player.hitbox.x - hitbox.x));
		return distanceBetween <= attackRange;
	}
	
	public boolean isDead() {
		return dead;
	}

	// reset all the enemy attributes and booleans
	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		initialUpdate = true;
		health = maxHealth;
		resetAnimeTick(IDLING);
		dead = false;
		downAccel = 0;
	}
	
}
