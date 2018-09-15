package ScreenController;

import java.awt.Color;
import java.awt.image.*;
import java.awt.Font;
import java.awt.Graphics2D;
import Main.GamePanel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import ScreenController.GameState;
import animations.Pear.PearNeutral;
import dataStructures.Animation;
import dataStructures.ScreenManager;
import Maps.Background;

public class CharacterSelection extends GameState{
	//Backgrounds
	private Background bg;
	//User selection
	private int currentChoice[] = {0,0};
	private int tempCurrentChoice[] = {0,0};
	boolean hasSelected[] = {false,false};
	// Variables for Drawing Character Portraits
	//Selection Animation
	private final int radius = 275;
	private final int portraitRadius = 290;
	int circlex[][];
	int circley[][];
	int totalCharacters = 1;
	private int  animationSpeed = 2;
	final Color circleColours[] = {Color.WHITE,Color.BLUE,Color.GREEN,Color.RED};
	int animationLimiter = (360/5);
	//Character loaded points
	int tempTotalCharacters = totalCharacters;
	int animationEditor[] = {0,0};
	boolean isAnimating[] = {false,false};
	int animationDirection[] = {0,0};
	int tempAnimationDirection[] = {0,0};
	// Menu fonts and colours
	private Color titleColor;
	private Font titleFont;
	private Font font; 
	//Animation and pictures (Graphics)
	final private int xOffset = 850;
	private int currentXOffset = 0; 
	private int animationCorrection = 0;
	final private int baseXOffset = 450 ;
	final private int yOffset = 450;
	final private ArrayList <Animation> characterPortraits;
	public String[] characterOptions = {"• Pear", "• Tomato"};


	public CharacterSelection(ScreenManager sm)
	{
		animationSpeed = animationLimiter/totalCharacters;
		this.sm = sm;
		characterPortraits = new ArrayList<Animation>();
		characterPortraits.add(new PearNeutral());
		//characterPortraits.add(new GrantNeutral());
		//characterPortraits.add(new GrantNeutral());
		//characterPortraits.add(new GrantNeutral());
		try{
			/*for(int i = 0;i < characterPortraits.length;i ++)
			{
				characterPortraits[i] = ImageIO.read(getClass().getResourceAsStream(portraitDirectories[i]));
			}*/ 
			bg = new Background("/backgrounds/Background2.jpg",1);
			bg.setVector(1,0);
			bg.setPosition(0, 0);
			titleColor = new Color(255,255,102);
			titleFont = sm.GameFont.deriveFont(35f);
			font = new Font("Arial",Font.ITALIC,70);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		characterPortraits.get(0).setDirection(-1);
		characterPortraits.get(0).setSize((int)(150*1.5),(int)(150*1.5 ));


	}
	private void startBattle(){
		sm.setState(sm.BATTLESTATE);
	}
	public  void keyPressed(int k){
		if(k == KeyEvent.VK_ESCAPE)
		{
			init();
			sm.setState(sm.MENUSTATE);
		}
		else if( k == KeyEvent.VK_ADD)
		{
			if (tempTotalCharacters < 4)
			{
				tempTotalCharacters ++;
			}
		}
		else if(k == KeyEvent.VK_BACK_SPACE)
		{
			if (tempTotalCharacters > 1)
			{
				tempTotalCharacters --;
			}
		}
		else if(hasSelected[1] && hasSelected[0] && k == KeyEvent.VK_NUM_LOCK)
		{
			startBattle();
		}
		else
		{

			if(!hasSelected[0])
			{
				if(!isAnimating[0])
				{
					if(k == KeyEvent.VK_SPACE)
					{
						hasSelected[0] = true;
					}
					else if(k == KeyEvent.VK_D)
					{
						tempAnimationDirection[0] = -1;
						isAnimating[0] = true;
						tempCurrentChoice[0]--;
						if(tempCurrentChoice[0] ==-1)
						{
							tempCurrentChoice[0] = totalCharacters - 1;
						}
					}
					else if(k == KeyEvent.VK_A)
					{
						isAnimating[0] = true;
						tempAnimationDirection[0] = +1;
						tempCurrentChoice[0]++;
						if(tempCurrentChoice[0] == totalCharacters )
						{
							tempCurrentChoice[0] = 0;
						}

					}
				}
			}
			else if(hasSelected[0])
			{
				if(k == KeyEvent.VK_SPACE)
				{
					hasSelected[0] = false;
				}
			}
			//PLAYER TWO SCREEN INTERACTIONS
			if(!hasSelected[1])
			{
				if(!isAnimating[1])
				{
					if(k == KeyEvent.VK_ENTER)
					{
						hasSelected[1] = true;
					}
					else if(k == KeyEvent.VK_LEFT)
					{
						isAnimating[1] = true;
						tempCurrentChoice[1]--;
						tempAnimationDirection[1] = -1;
						if(tempCurrentChoice[1] ==-1)
						{
							tempCurrentChoice[1] = totalCharacters - 1;
						}
					}
					else if(k == KeyEvent.VK_RIGHT)
					{
						tempCurrentChoice[1]++;
						isAnimating[1] = true;
						tempAnimationDirection[1] = +1;
						if(tempCurrentChoice[1] == totalCharacters )
						{
							tempCurrentChoice[1] = 0;
						}

					}
				}
			}
			else if(hasSelected[1])
			{
				if(k == KeyEvent.VK_ENTER)
				{
					hasSelected[1] = false;
				}
			}

		}

	}
	public  void keyReleased(int k){
	}
	public void init() {

		circlex = new int[totalCharacters][2];
		circley = new int[totalCharacters][2];
	}
	public void update() {
		updateAnimations();
		bg.update();
		animateGui();
	}
	public void updateAnimations(){
		for(int i = 0; i < totalCharacters; i ++)
		{
			characterPortraits.get(i).update();
		}
	}
	public void animateGui()
	{
		totalCharacters = tempTotalCharacters;
		animationDirection = tempAnimationDirection;
		circlex = new int[totalCharacters][2];
		circley = new int[totalCharacters][2];
		double rotationElement = 0;
		double rotationAngle = 0;
		animationLimiter = (360/totalCharacters);
		animationCorrection = 90 - animationLimiter;
		animationSpeed = 2 +  4/totalCharacters;
		//if(moving)
		for(int i = 0; i < 2; i ++)
		{
			currentChoice[i] = tempCurrentChoice[i];
			if(isAnimating[i]){
				animationEditor[i] += animationSpeed;
				if(animationEditor[i] >= animationLimiter){
					isAnimating[i] = false;
					animationEditor[i] = 0;
					animationDirection[i] = 0;
				}
				if(animationDirection[i] == -1)
				{	
					for(int j = 0; j < totalCharacters; j ++)
					{
						rotationElement =  (-currentChoice[i] + j );
						rotationAngle = (animationEditor[i]) + animationCorrection + rotationElement* (360/totalCharacters);
						circlex[j][i] =  (int) ((radius *Math.cos(Math.toRadians(rotationAngle))));
						circley[j][i] =  (int) ((radius *Math.sin(Math.toRadians(rotationAngle))));
					}
				}
				else if(animationDirection[i] == 1)
				{
					for(int j = 0; j < totalCharacters; j ++)
					{
						rotationElement =  (-currentChoice[i] + j + 2 );
						rotationAngle = -(animationEditor[i]) + animationCorrection + rotationElement* (360/totalCharacters);
						circlex[j][i] =  (int) ((radius *Math.cos(Math.toRadians(rotationAngle))));
						circley[j][i] =  (int) ((radius *Math.sin(Math.toRadians(rotationAngle))));
					}
				}
			}
			if(!isAnimating[i]){
				for(int j = 0; j < totalCharacters; j ++)
				{
					rotationElement =   (-currentChoice[i] + j + 1); 
					circlex[j][i] =  (int) ((radius *Math.cos(Math.toRadians(animationCorrection + rotationElement* (360/totalCharacters)))));
					circley[j][i] =  (int) ((radius *Math.sin(Math.toRadians(animationCorrection +  rotationElement* (360/totalCharacters)))));
				}
			}
		}
	}
	public void drawPortraits(Graphics2D g) {
		//Draw circles
		//Set offset 
		if(hasSelected[0] && hasSelected[1]){
			font = sm.GameFont.deriveFont(55f);
			g.setFont(font );
			g.setColor(Color.RED);
			g.drawString("READY!", GamePanel.WIDTH/2 - 100, 900);
			font = sm.GameFont.deriveFont(35f);
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString("Press Num Lock to continue...", GamePanel.WIDTH/2 - 245, 100);
		}
		for(int i = 0; i < hasSelected.length; i ++)
		{
			currentXOffset = xOffset*(i + 1) - baseXOffset;
			g.setColor(Color.lightGray);
			g.fillOval(
					(int) (radius *Math.cos(Math.toRadians( animationCorrection + (360/totalCharacters)	))) + currentXOffset - 25,
					(int) (radius *Math.sin(Math.toRadians(animationCorrection + (360/totalCharacters)))) + yOffset - 25,
					portraitRadius + 50,portraitRadius + 50);
			g.setFont(titleFont);
			g.setColor(titleColor);
			g.drawString("Player " + (i + 1),currentXOffset+ 60, yOffset + 110);
			if(hasSelected[i])	
			{
				g.setColor(Color.RED);
			}
			else if(!hasSelected[i])
			{
				g.setColor(Color.white);
			}
			g.drawString(characterOptions[currentChoice[i]],currentXOffset + 60, yOffset + 150);
			for(int j = 0; j < totalCharacters;j++)
			{
				g.setColor(circleColours[j]);
				g.fillOval(circlex[j][i] + currentXOffset,circley[j][i] + yOffset, portraitRadius, portraitRadius);
				characterPortraits.get(j).setCoordinates(circlex[j][i] + currentXOffset + 35, circley[j][i] + yOffset + 25);
				characterPortraits.get(j).draw(g);

			}	
		}


	}
	public void draw(Graphics2D g) {
		bg.draw(g); 
		//g.drawImage(menuLayer, 0, 0, GamePanel.WIDTH,GamePanel.HEIGHT  ,null);
		//Draw circles
		drawPortraits(g);

	}
	public int[]getChoices()
	{
		return currentChoice;
	}
	
	public int[] getData() {
		return getChoices();
	}
}
