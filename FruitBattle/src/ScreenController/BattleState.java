package ScreenController;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Main.Keylogger;
import Maps.KitchenArena;
import Maps.Maps;
import Maps.finalDestination;
import characters.Pear;
import dataStructures.CharacterObject;
import dataStructures.GameObject;
import dataStructures.HUD;
import dataStructures.ScreenManager;

public class BattleState extends GameState{
	Maps currentMap;
	private int winner = 0;
	final int totalPlayers = 2;
	private int isRespawning[] = {0,0};
	final int respawnDelay = 150;
	public static final int defaultCharInputs2[] = {KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,
			KeyEvent.VK_G,KeyEvent.VK_H};
	public static final int defaultCharInputs[] = {KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN,
			KeyEvent.VK_NUMPAD5,KeyEvent.VK_ENTER};
	Keylogger Player1Inputs ;
	Keylogger Player2Inputs ;
	HUD overlay;
	public boolean isRunning = true;
	public boolean isFirstFrame = true;
	public BattleState(ScreenManager sm){
		this.sm = sm;
		Player1Inputs = new Keylogger(defaultCharInputs2);
		Player2Inputs = new Keylogger(defaultCharInputs);
	}
	public void init() {
		currentMap = new KitchenArena();
		currentMap.init();
		Player1Inputs = new Keylogger(defaultCharInputs2);
		Player2Inputs = new Keylogger(defaultCharInputs);
		int[] choices = sm.gameStates.get(sm.CHARACTERSELECTION).getData();
		CharacterObject[] loadingCharacters = new CharacterObject[totalPlayers];
		for(int i = 0; i < choices.length;i ++ )
		{
			if(choices[i] == 0){
				loadingCharacters[i] = new Pear(currentMap.spawnPointsX[i],currentMap.spawnPointsY[i],currentMap,i);
			}
			else if(choices[i] == 1){
			}
			else if(choices[i] == 2){
			}
			else if(choices[i] == 3){	
			}
		}
		currentMap.setCharacters(loadingCharacters);
		overlay = new HUD(currentMap, sm, totalPlayers, 2, this);
	}
	public void update() {
		if(isFirstFrame)
		{
			init();
			isFirstFrame = false;
		}
		if(isRunning)
		{
			Player1Inputs.update();
			Player2Inputs.update();
			currentMap.characters[0].setInputs(Player1Inputs.getInputs());
			currentMap.characters[1].setInputs(Player2Inputs.getInputs());
			currentMap.update();
			//This checks each player to see if they are dead, and respawns them according to how the map likes them to be respawned.
			for(int i = 0; i < currentMap.characters.length; i ++){
				if(isRespawning[i] >0)
				{
					isRespawning[i] ++;
					if(isRespawning[i] == respawnDelay)
					{
						currentMap.characters[i].setVulnerable(true);
						isRespawning[i] = 0;
					}
					else if(isRespawning[i] == 100 )
					{
						currentMap.characters[i].setIsAlive(true);
					}
					else if(isRespawning[i] ==60)
					{
						currentMap.characters[i].spawn(currentMap.spawnPointsX[2+i], currentMap.spawnPointsY[2+i]);
					}
				}
				else{

					if(currentMap.characters[i].getHealth() <= 0 || currentMap.characters[i].getX() < -100 || currentMap.characters[i].getX() > 2200)
					{
						currentMap.characters[i].kill();
						currentMap.characters[i].setX(500);
						currentMap.characters[i].setIsAlive(false);
						currentMap.characters[i].setVulnerable(false);
						isRespawning[i] = 1;
					}
				}

			}
		}
		if(currentMap.characters[0].getDeaths() == 2)
		{
			winner = 1;
			overlay.selectWinner(winner);
			isRunning = false;
			isFirstFrame = true;
		}
		else if(currentMap.characters[1].getDeaths() == 2)
		{
			winner = 2;
			overlay.selectWinner(winner);
			isRunning = false;
			isFirstFrame = true;
		}
		overlay.updateHUD();
	}
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		currentMap.draw(g);
		overlay.draw(g);
	}
	public void keyPressed(int k) {
		Player1Inputs.addKey(k);
		Player2Inputs.addKey(k);
	}
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		Player1Inputs.removeKey(k);
		Player2Inputs.removeKey(k);
	}

	public int[] getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
