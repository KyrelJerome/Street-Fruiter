package dataStructures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Maps.Maps;
import ScreenController.BattleState;
import ScreenController.GameState;

public class HUD {
	private final String overlayDirectory = "/HUD/Overlay.png";
	protected Maps gameMap;
	protected int winner;
	int frameCounter = 0;
	protected boolean isPaused = false;
	protected boolean isVisible = true;
	protected ScreenManager sm;
	protected boolean inputs[];
	protected int RoundsWon[] = new int [2];
	protected int totalRounds = 0;
	protected int characterhealth[] = new int [2];
	protected int TotalLives = 2;
	protected BufferedImage characterPortraits[];
	protected BufferedImage overlay;
	protected int respawnCounter[] = new int[2];
	private  BattleState battle;
	protected int remainingLives[] = {TotalLives,TotalLives};
	public HUD(Maps map, ScreenManager sm, int TotalLives, int totalRounds, BattleState battle){
		characterPortraits = new BufferedImage[map.characters.length];
		gameMap = map;
		this.sm = sm;
		try {
			overlay = ImageIO.read(getClass().getResourceAsStream(overlayDirectory));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < map.characters.length; i ++)
		{
			characterhealth[i] = map.characters[i].health;
			remainingLives[i] = TotalLives;
			this.TotalLives = TotalLives;
			try {
				characterPortraits[i] = ImageIO.read(getClass().getResourceAsStream("/HUD/PearPortrait.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void updateHUD()
	{
		for(int i = 0; i < gameMap.characters.length; i ++){
			characterhealth[i] = gameMap.characters[i].health;

		}
		if(frameCounter >= 150)
		{
			battle.isRunning = true;
			sm.setState(sm.MENUSTATE);
		}
	}
	public void draw(Graphics2D g){
		drawCharacterNames(g);
		drawOverlay(g);
		drawtoOverlay(g);
		if(isPaused)
		{
			g.setColor(Color.red);
			g.setFont(GamePanel.dataFont.deriveFont(70));
			g.drawString("Press Enter to Continue...",GamePanel.WIDTH/3,GamePanel.HEIGHT/2 );
			g.drawString("P + Enter + Spacebar to Quit",GamePanel.WIDTH/4,GamePanel.HEIGHT/2 + 100 );
		}


	}
	private void drawCharacterNames(Graphics2D g){
		g.setColor(Color.white);
		g.setFont(GamePanel.dataFont.deriveFont(100));
		for(int i = 0;i < characterPortraits.length; i ++)
		{
			if(gameMap.characters[i].isAlive)
			{
				g.setColor(new Color((1-i)*255, 0, i*255));
				g.drawString("P " + (i + 1), (int) (gameMap.characters[i].getX() + gameMap.characters[i].width/3), (int) (gameMap.characters[i].y - 40));

			}
		}
	}
	public void selectWinner(int winner)
	{
		this.winner = winner;
	}
	private void drawOverlay(Graphics2D g){

		g.drawImage(overlay, 0, 0, null);
	}
	private void drawtoOverlay(Graphics2D g){
		for(int i = 0; i < gameMap.characters.length; i ++)
		{
			
			g.setColor(Color.black);
			g.setFont(GamePanel.dataFont.deriveFont(100));
			g.drawImage(characterPortraits[i],(1920- 300)* i + 75, 60, 150, 150, null);
			g.drawString("Lives: " + (TotalLives - gameMap.characters[i].deaths), (1920-730) * i + 275, 100);
			g.drawString("%" + gameMap.characters[i].health, (1920-280) * i + 90, (int) 295);

		}
		g.setColor(Color.white);
		
		if(winner == 1)
		{
			g.fillRect(GamePanel.WIDTH/4,GamePanel.HEIGHT/3 , GamePanel.WIDTH/2,(GamePanel.HEIGHT*2)/3);
			g.setColor(Color.black);
			g.setFont(sm.GameFont.deriveFont(70f));
			frameCounter ++;
			g.drawString(" PLAYER " + (1) + " wins!", GamePanel.WIDTH/3,GamePanel.HEIGHT/2 );
		}
		else if(winner == 2)
		{	
			g.fillRect(GamePanel.WIDTH/4,GamePanel.HEIGHT/3, GamePanel.WIDTH/2,(GamePanel.HEIGHT*1)/3);
			g.setColor(Color.black);
			g.setFont(sm.GameFont.deriveFont(70f));
			frameCounter ++;
			g.drawString("PLAYER " + (2) + " wins!", GamePanel.WIDTH/3,GamePanel.HEIGHT/2 );
		
		}

	}
	public void updateInputs(boolean[] inputs){

	}
}
