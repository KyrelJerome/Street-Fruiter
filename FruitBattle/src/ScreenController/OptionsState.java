package ScreenController;

import java.awt.*;
import java.awt.event.KeyEvent;

import Main.GamePanel;
import Main.Keylogger;
import Maps.Background;
import dataStructures.ScreenManager;

public class OptionsState extends GameState{
	//	Window constants
	private final int GENERAL = 0;
	private final int CONTROLS= 1;
	private final int SETTINGS = 2;
	private final int totalWindows = 3;
	private final String ControlsOptions[] =  {"Left","Right","Up","Down","Attack", "Special", "Save new","Load Existing "}; 
	private final String GeneralOptions[] =  {"Credits","Controls","Settings"}; 
	private final String SettingsOptions[] =  {"Fullscreen"}; 
	private final String CreditsStrings [] = { "Programmer: Kyrel Jerome",
			"Game Designer: Kyrel Jerome", "General Art: Kyrel Jerome",
			"Character Sprites: Patrick Zwinkels", "Graphics Engine: Graphics2D", "Language: Java"};
	//State based data (works like a CSS script)
	private Background bg;
	private int currentChoice = 0;
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private int currentWindow = 0;
	private boolean keysPressed[];
	//Key Handler
	Keylogger optionsInputs;
	public final int ENTER = 0, UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4, ESCAPE  = 5;
	public static int [] optionsKeys = {KeyEvent.VK_ENTER, KeyEvent.VK_UP, KeyEvent.VK_DOWN , KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_ESCAPE};
	boolean hasSelected = false;
	//Window entry animation
	boolean isInitialised = true;

	public OptionsState(ScreenManager sm)
	{
		optionsInputs = new Keylogger(optionsKeys);
		this.sm = sm;
		try{
			bg = new Background("/backgrounds/MainBackground.png",1);
			bg.setVector(0, 0);
			titleColor = new Color(255,255,0);
			titleFont = sm.GameFont.deriveFont(100f);
			font = new Font("Arial",Font.ITALIC,90);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		currentWindow = GENERAL;
		currentChoice = 0;
	}
	public  void init(){
		optionsInputs = new Keylogger(optionsKeys);
		currentWindow = GENERAL;
		currentChoice = 0;
	
	}
	public  void update(){
		optionsInputs.update();
		keysPressed = optionsInputs.isFirstFrame();
		if(isInitialised)
		{
			if(currentWindow == GENERAL)
			{
				updateGeneralWindow();
			}
			else if(currentWindow == SETTINGS)
			{
				updateSettingsWindow();
			}
			else if(currentWindow == CONTROLS)
			{
				updateControlsWindow();
			}
		}
		else
		{

		}
		bg.update();

	}
	public  void draw(Graphics2D g){
		//draw bg
		bg.draw(g);
		g.setColor(titleColor);
		g.setFont(titleFont.deriveFont(40));
		g.drawString("Street Fruiter",GamePanel.WIDTH /2 -600, 100);
		g.setFont(sm.GameFont.deriveFont(40f));
		g.setColor(Color.RED);
		g.drawString("Press Escape to go back", 1220,900);
		g.setColor(Color.RED);
		g.setFont(font.deriveFont(90f));
		if(currentWindow == GENERAL)
		{
			g.drawString("General Menu",GamePanel.WIDTH /2 -900,220);

			g.setFont(font);
			for(int i = 0; i< GeneralOptions.length; i ++)
			{
				int xOffset = 0;
				if(i == currentChoice){
					g.setColor(Color.BLACK);
					xOffset = 75;
				}
				else {
					g.setColor(Color.WHITE);
					xOffset = 0;
				}
				g.drawString(GeneralOptions[i],GamePanel.WIDTH/2- 900 + xOffset , GamePanel.HEIGHT/2- 200 + i* 85);
			}
			if(currentChoice == 0)
			{
				for(int i = 0; i< CreditsStrings.length; i ++)
				{
				g.setFont(font.deriveFont(50f));
				g.drawString(CreditsStrings[i], GamePanel.WIDTH/2- 900 , GamePanel.HEIGHT/2 + 100 + i *60);

				}
			}
		}
		else if(currentWindow == CONTROLS)
		{
			g.drawString("Controls Selecter",GamePanel.WIDTH /2 -900,220);

			g.setFont(font);
			for(int i = 0; i< ControlsOptions.length; i ++)
			{
				int xOffset = 0;
				if(i == currentChoice){
					g.setColor(Color.BLACK);
					xOffset = 75;
				}
				else {
					g.setColor(Color.WHITE);
					xOffset = 0;
				}		
				g.drawString(ControlsOptions[i],GamePanel.WIDTH/2- 900 + xOffset , GamePanel.HEIGHT/2- 200 + i* 85);
			}
		}
		else if(currentWindow == SETTINGS)
		{
			g.drawString("Settings Menu",GamePanel.WIDTH /2 -900,220);

			g.setFont(font);
			for(int i = 0; i< SettingsOptions.length; i ++)
			{
				int xOffset = 0;
				if(i == currentChoice){
					g.setColor(Color.BLACK);
					xOffset = 75;
				}
				else {
					g.setColor(Color.WHITE);
					xOffset = 0;
				}
				g.drawString(SettingsOptions[i],GamePanel.WIDTH/2- 900 + xOffset , GamePanel.HEIGHT/2 + 200 + i* 85);
			}
		}
	}
	public  void keyPressed(int k){
		if(!hasSelected)
		{
			optionsInputs.addKey(k);
		}
		else if(hasSelected)
		{

		}
	}
	public  void keyReleased(int k){
		if(!hasSelected)
		{
			optionsInputs.removeKey(k);
		}
		else if(currentWindow == CONTROLS &&  hasSelected)
		{ 

			hasSelected = false;
		}

	}

	public int[] getData() {

		return null;
	}
	//*****************WINDOW CODE************************
	public void updateGeneralWindow(){
		if(keysPressed[ENTER]){
			if(currentChoice == 0)
			{
				currentChoice = 0;

			}
			else if(currentChoice == 1)
			{
				currentChoice = 0;
				currentWindow = CONTROLS;
			}
			else if(currentChoice == 2)
			{
				currentChoice = 0;
				currentWindow = SETTINGS;
			}
		}
		else if(keysPressed[DOWN])
		{
				currentChoice++;
			if(currentChoice == GeneralOptions.length)
			{
				currentChoice = 0;
			}
		}
		else if(keysPressed[UP]){
			currentChoice--;
			if(currentChoice ==-1)
			{
				currentChoice = GeneralOptions.length - 1;
			}
		}
		else if(keysPressed[ESCAPE]){
			sm.setState(sm.MENUSTATE);
		}

	}
	public void updateControlsWindow(){
		if(keysPressed[ENTER]){
			if(currentChoice == 0)
			{
				

			}
			else if(currentChoice == 1)
			{

			}
			else if(currentChoice == 2)
			{
				
			}
		}
		else if(keysPressed[DOWN])
		{
				currentChoice++;
			if(currentChoice == ControlsOptions.length)
			{
				currentChoice = 0;
			}
		}
		else if(keysPressed[UP]){
			currentChoice--;
			if(currentChoice ==-1)
			{
				currentChoice = ControlsOptions.length - 1;
			}
		}
		else if(keysPressed[ESCAPE]){
			currentChoice = 0;
			currentWindow = GENERAL;
		}
	}
	public void updateSettingsWindow(){
		if(keysPressed[ENTER]){
			if(currentChoice == 0)
			{
				

			}
			else if(currentChoice == 1)
			{

			}
			else if(currentChoice == 2)
			{
				
			}
		}
		else if(keysPressed[DOWN])
		{
				currentChoice++;
			if(currentChoice == SettingsOptions.length)
			{
				currentChoice = 0;
			}
		}
		else if(keysPressed[UP]){
			currentChoice--;
			if(currentChoice ==-1)
			{
				currentChoice = SettingsOptions.length - 1;
			}
		}
		else if(keysPressed[ESCAPE]){
			currentChoice = 0;
			currentWindow = GENERAL;
		}
	}
	
}
