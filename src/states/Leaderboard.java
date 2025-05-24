package states;

import static misc.Constants.GUI.NavButtons.NAV_SIZE;
import static misc.Constants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import gui.NavButtons;
import main.Game;
import misc.LoadGame;

public class Leaderboard extends States implements StatesMethods {

	private BufferedImage lbBgImage, bgImage;
	private int xLB, yLB, lbWide, lbHigh;
	private NavButtons home;
	
	private ArrayList<String> allScoresList = new ArrayList<String>();
	private String[][] scores;
	
	private Font  f3  = new Font("Consolas",  Font.ITALIC, (int)(20 * Game.SCALE));
	
	public Leaderboard(Game game) {
		super(game);
		loadBG();
		loadButtons();
		createScoresFile();
		readScoresFile();
		// writeScores();
	}
	
	private void loadButtons() {
		// define co-ords for where button is displayed
		int homeX = (int)(315 * Game.SCALE);
		int homeY = (int)(690 * Game.SCALE);
		home = new NavButtons(homeX, homeY, NAV_SIZE, NAV_SIZE, 2);
		
	}
	
	private void loadBG() {
		bgImage = LoadGame.GetSprites(LoadGame.BG_IMAGE);
		lbBgImage = LoadGame.GetSprites(LoadGame.LB_BG);
		
		lbWide = (int)(lbBgImage.getWidth() * Game.SCALE);
		lbHigh = (int)(lbBgImage.getHeight() * Game.SCALE);
		xLB = Game.GAME_WIDE / 2 - lbWide / 2;
		yLB = (int)(50 * Game.SCALE);
	}

	@Override
	public void update() {
		home.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bgImage, 0, 0, Game.GAME_WIDE, Game.GAME_HIGH, null);
		g.drawImage(lbBgImage, xLB, yLB, lbWide, lbHigh, null);
		
		drawScores(g);
		home.draw(g);
	}
	
	public void createScoresFile() {
	    try {
	        if (MY_FILE.createNewFile()) {
	          System.out.println("File created: " + MY_FILE.getName());
	        } else {
	          // System.out.println("File already exists.");
	        }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}
	
	
	public void readScoresFile() {
		
	    try {
	    	Scanner myReader = new Scanner(MY_FILE);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				allScoresList.add(data);
			}
	        myReader.close();
	    } catch (FileNotFoundException e) {
	    	System.out.println("An error occured.");
	    	e.printStackTrace();
	    }
	    
//	    for (String score: allScoresList) {
//	    	System.out.println(score);
//	    }
	    
	    scores = new String[allScoresList.size()/3][3];
	    for (int i = 0; i < scores.length; i++)
	    	for (int j = 0; j < scores[i].length; j++)
	    		scores[i][j] = allScoresList.get((3 * i) + j);
	    
	    bubbleSortScores(scores);
	    
//	    System.out.println(Arrays.toString(scores[0]));
//	    System.out.println(Arrays.toString(scores[1]));
//	    System.out.println(Arrays.toString(scores[2]));
	}
	
	public void drawScores(Graphics g) {
		g.setColor(Color.black);
		g.setFont(f3);
		
		// define co-ords for where score info is displayed (they all have the same y value, navY)
		int scoreX = (int)(185 * Game.SCALE);
		int dateX = (int)(285 * Game.SCALE);
		int timeX = (int)(430 * Game.SCALE);
		int leaderboardY = (int)(230 * Game.SCALE);
		
		int limit = Math.min((allScoresList.size()/3), 10);
		
		for (int i = 0; i < limit; i++) {
			g.drawString(scores[i][0], scoreX, leaderboardY);
			g.drawString(scores[i][1], dateX, leaderboardY);
			g.drawString(scores[i][2], timeX, leaderboardY);
			leaderboardY += (int)(45 * Game.SCALE); 
		}
	}
	
//	public String[][] insertSortScores(String[][] scores) {
//	    for (int i = 1; i < scores.length; i++) {
//	    	float elem = Float.parseFloat(scores[i][0]);
//	    	int j = (i - 1);
//	    	while (j > -1 && Float.parseFloat(scores[j][0]) > elem) {
//	    		scores[j+1] = scores[j];
//	    		j -= 1;
//	    	}
//	    	scores[j+1][0] = String.valueOf(elem);
//	    }
//	    return scores;
//	}
	
	public String[][] bubbleSortScores(String[][] scores) {
	    for (int i = 0; i < scores.length; i++) {
	    	boolean noSwap = true;
	    	for (int j = 0; j < ((scores.length) - (i + 1)); j++) {
	    		if (Float.parseFloat(scores[j][0]) > Float.parseFloat(scores[j+1][0])) {
	    			String[] temp = scores[j];
	    			scores[j] = scores[j+1];
	    			scores[j+1] = temp;
	    			noSwap = false;
	    		}
	    	}
	    	if (noSwap)
	    		return scores;
	    }
	    return scores;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (inButton(e, home)) {
			home.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (inButton(e, home)) {
			if (home.isMousePressed())
				Gamestates.state = Gamestates.MENU;
		}
		
		home.resetBooleans();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		home.setMouseOver(false);
		
		if (inButton(e, home)) {
			home.setMouseOver(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub 
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	
	public boolean inButton(MouseEvent e, NavButtons button) {
		return button.getArea().contains(e.getX(), e.getY());
	}

}
