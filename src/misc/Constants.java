package misc;

import java.awt.Color;
import java.io.File;
import java.awt.image.RescaleOp; 

import main.Game;

public class Constants {
	public static final RescaleOp INVERT_COLOURS = new RescaleOp(-1.0f, 255, null);
	
	public static final float GFORCE = .035f * Game.SCALE;
	public static final int ANIME_SPEED = 25;
	
	public static final Color MY_BLUE = new Color(102, 162, 216);
	
	public static final String SCORES_FILENAME = "scores.txt";
	public static final File MY_FILE = new File(SCORES_FILENAME);
	
	public static class ItemConstants {
		public static final int TOKEN = 0;
		public static final int SPIKES = 1;
		public static final int TELEPORTER = 2;
		public static final int ENDGAME = 3;
		
		public static final int DEF_TOKEN_WIDTH = 18;
		public static final int DEF_TOKEN_HEIGHT = 30;
		public static final int DEF_COUNTER_WIDTH = 48;
		public static final int DEF_COUNTER_HEIGHT = 51;
		public static final int DEF_SPIKES_WIDTH = 32;
		public static final int DEF_SPIKES_HEIGHT = 32;
		public static final int DEF_TP_WIDTH = 32;
		public static final int DEF_TP_HEIGHT = 32;
		public static final int DEF_EGT_WIDTH = 32;
		public static final int DEF_EGT_HEIGHT = 32;
		
		public static final int TOKEN_WIDTH = (int) (Game.SCALE * DEF_TOKEN_WIDTH);
		public static final int TOKEN_HEIGHT = (int) (Game.SCALE * DEF_TOKEN_HEIGHT);
		public static final int COUNTER_WIDTH = (int) (Game.SCALE * DEF_COUNTER_WIDTH);
		public static final int COUNTER_HEIGHT = (int) (Game.SCALE * DEF_COUNTER_HEIGHT);
		public static final int SPIKES_WIDTH = (int) (Game.SCALE * DEF_SPIKES_WIDTH);
		public static final int SPIKES_HEIGHT = (int) (Game.SCALE * DEF_SPIKES_HEIGHT);
		public static final int TP_WIDTH = (int) (Game.SCALE * DEF_TP_WIDTH);
		public static final int TP_HEIGHT = (int) (Game.SCALE * DEF_TP_HEIGHT);
		public static final int EGT_WIDTH = (int) (Game.SCALE * DEF_EGT_WIDTH);
		public static final int EGT_HEIGHT = (int) (Game.SCALE * DEF_EGT_HEIGHT);

		public static int GetSprites(int itemType) {
			switch (itemType) {
			case TOKEN:
				return 35;
			}
			return 1;
		}
	}
	
	public static class EnemyConstants {
		public static final int IDLING = 0;
		public static final int RUN = 1;
		public static final int ATTACKING = 2;
		public static final int HITTING = 3;
		public static final int UNALIVE = 4;
		
		public static final int DEF_ENEMY_WIDTH = 72;
		public static final int DEF_ENEMY_HEIGHT = 32;
		
		public static final int ENEMY_WIDTH = (int)(DEF_ENEMY_WIDTH * Game.SCALE);
		public static final int ENEMY_HEIGHT = (int)(DEF_ENEMY_HEIGHT * Game.SCALE);
		
		// hitbox starts 26px from sides of image and 9px from top of image 
		public static final int ENEMY_X_DRAWOFFSET =  (int)(26*Game.SCALE);
		public static final int ENEMY_Y_DRAWOFFSET =  (int)(9*Game.SCALE);
		
		public static int GetSprites(int enemyState) {
			switch(enemyState) {
			case IDLING:
				return 9;
			case RUN:
				return 6;
			case ATTACKING:
				return 7;
			case HITTING:
				return 4;
			case UNALIVE:
				return 5;
			}
			
			return 0;
		}
		
	}
	
	public static class GUI {
		
		public static class Buttons {
			public static final int DEF_BUTTON_WIDE = 140;
			public static final int DEF_BUTTON_HIGH = 56;
			public static final int BUTTON_WIDE = (int) (DEF_BUTTON_WIDE * Game.SCALE);
			public static final int BUTTON_HIGH = (int) (DEF_BUTTON_HIGH * Game.SCALE);
		}
		
		public static class PauseButtons {
			public static final int DEF_VOLUME_SIZE = 42;
			public static final int VOLUME_SIZE = (int)(DEF_VOLUME_SIZE * Game.SCALE * 0.7);
		}
		
		public static class NavButtons {
			public static final int DEF_NAV_SIZE = 56;
			public static final int NAV_SIZE = (int)(DEF_NAV_SIZE * Game.SCALE * 0.7);
		}
		
		public static class VolumeSlider {
			public static final int DEF_SLIDER_WIDTH = 28;
			public static final int DEF_SLIDER_HEIGHT = 44;
			public static final int DEF_BAR_WIDTH = 215;
			
			public static final int SLIDER_WIDTH = (int)(DEF_SLIDER_WIDTH * Game.SCALE * 0.7);
			public static final int SLIDER_HEIGHT = (int)(DEF_SLIDER_HEIGHT * Game.SCALE * 0.7);
			public static final int BAR_WIDTH = (int)(DEF_BAR_WIDTH * Game.SCALE);
		}
	}
	
	public static class Directions{
		public static final int UP = 0;
		public static final int RIGHT = 1;
		public static final int DOWN = 2;
		public static final int LEFT = 3;
	}
	
	public static class PlayerConstants{
		public static final int RUN = 1;
		public static final int IDLE = 0;
		public static final int JUMP = 2;
		public static final int FALL = 3;
		public static final int HIT = 5;
		public static final int ATTACK = 4;
		public static final int UNALIVE = 6;
		
		public static int GetSprites(int player_action) {
			// returns no. of sub images in each animation array
			switch(player_action) {
			case UNALIVE:
				return 8;
			case RUN:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
			case ATTACK:
				return 3;
			case FALL:
			default:
				return 1;
			
			}
			
		}
	}

}
