package entities;

import static misc.Constants.PlayerConstants.*;
import static misc.Constants.*;
import static misc.Helpers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Game;
import misc.LoadGame;
import states.PlayState;

public class Player extends Entity{
	
	private PlayState play;
	private BufferedImage[][] animes;
	private boolean standStill = true, peaceful = true;
	private boolean left, right, jump;
	private int[][] levelData;
	private int playerNo;
	// The hitbox will be smaller than the entire sprite image
	// The hitbox starts 21 pixels from the side of the image and 4 pixels from the top 
	private float xOffset = 21 * Game.SCALE;
	private float yOffset = 4 * Game.SCALE;
	// Gravity
	private float vert = -2.8f * Game.SCALE;
	private float downAccelAfterCollision = 0.5f * Game.SCALE; 
	// Health
	private BufferedImage healthImage;

	private int statusWidth = (int)(192 * Game.SCALE);
	private int statusHeight = (int)(58 * Game.SCALE);
	private int xStatus = (int)(10 * Game.SCALE);
	private int[] yStatus = {(int)(10 * Game.SCALE), (int)(45 * Game.SCALE)};

	private int healthbarWidth = (int)(150 * Game.SCALE);
	private int healthbarHeight = (int)(4 * Game.SCALE);
	private int healthbarInitX = (int)(34 * Game.SCALE);
	private int healthbarInitY = (int)(14 * Game.SCALE);

	private int maxHealth = 100;
	private int[] health = {maxHealth, maxHealth};
	private int healthWidth = healthbarWidth;
	//AttackArea
	private boolean hasCheckedAttack;
	private int xFlip = 0;
	private int widthFlip = 1;
	
	

	public Player(float x, float y, int width, int height, int playerNo, PlayState play) {
		super(x, y, width, height);
		this.play = play;
		this.state = IDLE;
		this.playerNo = playerNo;
		loadAnimes(playerNo);
		this.speed = 1.0f * Game.SCALE;
		init__hitbox(20, 27);
		init__attackArea();
		
	}
	
	private void init__attackArea() {
		attackArea = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
		
	}

	public void update(int playerNo) {
		updateHealth(playerNo);
		
		if(health[playerNo] <= 0) {
			if(state != UNALIVE) {
				state = UNALIVE;
				animeTick = 0;
				animeIndx = 0;
				play.setDying(true);
				
			} else if (animeIndx == GetSprites(UNALIVE) - 1 && animeTick >= ANIME_SPEED - 1) {
				play.setGameOver(true);
			} else {
				updateAnimeTick();
			}
			
			
			return;
		}
		
		updateAttackArea();
		updatePosition();
		
		if(!standStill) {
			checkTokenPickedUp();
			dontTouchTheSpikes();
			callTheElevator();
			endTheGame();
		}
		
		if(!peaceful)
			checkAttack();
		
		updateAnimeTick();
		setAnime();
	}
	
	private void endTheGame() {
		play.endTheGame(play.getPlayer1(), play.getPlayer2());
	}

	private void callTheElevator() {
		play.callTheElevator(this);
	}

	private void dontTouchTheSpikes() {
		play.dontTouchTheSpikes(this);
	}

	public void unalive(int playerNo) {
//		System.out.println("(" + String.valueOf(hitbox.x) + ", " + String.valueOf(hitbox.y) + ")");
		health[playerNo] = 0;
	}
	
	public void beamUp() {
//		System.out.println("(" + String.valueOf(hitbox.x) + ", " + String.valueOf(hitbox.y) + ")");
		hitbox.x -= (int)(457 * Game.SCALE);
		hitbox.y -= (int)(300 * Game.SCALE);
	}
	
	private void checkTokenPickedUp() {
		play.checkTokenPickUp(hitbox);
	}

	private void checkAttack() {
		if(animeIndx == 1 || hasCheckedAttack)
			return;
		hasCheckedAttack = true;
		play.checkEnemyHit(attackArea);
	}

	private void updateAttackArea() {
		if(right) {
			attackArea.x = hitbox.x + hitbox.width + (int)(10 * Game.SCALE);
		} else if(left) {
			attackArea.x = hitbox.x - hitbox.width - (int)(10 * Game.SCALE);
		}
		attackArea.y = hitbox.y + (int)(10 * Game.SCALE);
	}

	public void render(Graphics g, int levelOffset, int playerNo) {
		g.drawImage(animes[state][animeIndx], (int)(hitbox.x - xOffset) + xFlip, (int)(hitbox.y - yOffset) - levelOffset, width * widthFlip, height, null);
		// drawHitbox(g, levelOffset);
		// drawAttackArea(g, levelOffset);
		drawGUI(g, playerNo);
	}
	
	private void updateHealth(int playerNo) {
		healthWidth = (int)((health[playerNo] / (float)maxHealth) * healthbarWidth);
	}
	
	public void alterHealth(int value, int playerNo) {
		health[playerNo] += value;
		
		if (health[playerNo] <= 0) {
			health[playerNo] = 0;
		} else if (health[playerNo] >= maxHealth){
			health[playerNo] = maxHealth;
		}
	}

	private void drawGUI(Graphics g, int playerNo) {
		g.drawImage(healthImage, xStatus, yStatus[playerNo], statusWidth, statusHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthbarInitX + xStatus, healthbarInitY + yStatus[playerNo], healthWidth, healthbarHeight);
		
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
				peaceful = true;
				hasCheckedAttack = false;
			}
		}
		
	}
	
	// Determines animation based on players' current action
	private void setAnime() {
		int initialAnime = state;
		
		if(!standStill)
			state = RUN;
		else
			state = IDLE;
		
		if(airborne) {
			if (downAccel < 0)
				state = JUMP;
			else
				state = FALL;
		}
		
		if(!peaceful) {
			state = ATTACK;
			if(initialAnime != ATTACK) {
				animeIndx = 1;
				animeTick = 0;
			}
		}
		
		if(initialAnime != state)
			resetAnimeTick();
	}
	
	private void resetAnimeTick() {
		animeTick = 0;
		animeIndx = 0;
	}
	
	private void updatePosition() {
		
		standStill = true;
		if (jump)
			jump();
		
		if(!airborne && !left && !right)
			return;
		
		float speedX = 0;
		
		if (right) {
			speedX += speed;
			xFlip = 0;
			widthFlip = 1;
		}
		
		if (left) {
			speedX -= speed;
			xFlip = width;
			widthFlip = -1;
		}
		
		// set airborne to true if the player isn't on the ground
		if (!airborne) {
			if(!OnFloor(hitbox, levelData))
				airborne = true;
		}
		
		if(airborne) {
			// if CanMove() returns true move w/ appropriate speed based on gravity constraints
			if (CanMove(hitbox.x, hitbox.y + downAccel, hitbox.width, hitbox.height, levelData)) {
				hitbox.y += downAccel;
				downAccel += GFORCE;
				xUpdate(speedX);
			// don't move if CanMove() returns false
			} else {
				hitbox.y = GetYPosition(hitbox, downAccel);
				if (downAccel > 0)
					onFloorReset();
				else
					downAccel = downAccelAfterCollision;
				xUpdate(speedX);
			}
			
		} else {
			xUpdate(speedX);
		} 
		standStill = false;
		
	}
	
	private void jump() {
		if(airborne)
			return;
		airborne = true;
		downAccel = vert;
	}
	
	private void onFloorReset() {
		airborne = false;
		downAccel = 0;
	}
	
	private void xUpdate(float speedX) {
		if(CanMove(hitbox.x + speedX, hitbox.y, hitbox.width, hitbox.height, levelData)) {
			hitbox.x += speedX;
		} else {
			hitbox.x = GetXPosition(hitbox, speedX);
		}
	}
	
	private void loadAnimes(int playerNum) {
		
		
		BufferedImage image = LoadGame.GetSprites(LoadGame.PLAYER_POSES[playerNum]);
		// creates 7x8 buffered image to hold all animations
		animes = new BufferedImage[7][8];
		for(int i = 0; i < animes.length; i++)
			for(int j = 0; j < animes[i].length; j++)
				animes[i][j] = image.getSubimage(j*64, i*40, 64, 40);
		
		healthImage = LoadGame.GetSprites(LoadGame.HEALTHBAR[playerNum]);		
	}
	
	public void loadLevelData(int[][] levelData) {
		this.levelData = levelData;
		if(!OnFloor(hitbox, levelData))
			airborne = true;
	}
	
	//sets player to peaceful if not attacking
	public void setPeaceful(boolean peaceful) {
		this.peaceful = peaceful;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void resetDirections() {
		left = false;
		right = false;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	public int getPlayerNo(Player player) {
		return playerNo;
	}

	// reset all player attributes and booleans when the game resets
	public void reset(int playerNo) {
		resetDirections();
		airborne = false;
		peaceful = true;
		standStill = false;
		state = IDLE;
		health[playerNo] = maxHealth;
		
		hitbox.x = x;
		hitbox.y = y;
		
		if(!OnFloor(hitbox, levelData))
			airborne = true;
		
	}
	
}
