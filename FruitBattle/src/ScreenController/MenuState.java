package ScreenController;

import java.awt.*;
import java.awt.event.KeyEvent;

import Main.GamePanel;
import Maps.Background;
import dataStructures.ScreenManager;

public class MenuState extends GameState{
	private Background bg;
	int fts = 0;
	int fps = 0;
	long frameStartTime = System.currentTimeMillis();
	public MenuState(ScreenManager sm)
	{
		this.sm = sm;
		try{
			bg = new Background("/backgrounds/MainBackground.png",1);
			bg.setVector(0, 0);
			titleColor = new Color(255,0,0);
			titleFont = sm.GameFont.deriveFont(100f);
			font = new Font("Arial",Font.ITALIC,90);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public  void init(){

	}
	private int currentChoice = 0;

	private Color titleColor;
	private Font titleFont;
	private Font font;
	private String[] options = {
			"• 2 Player","• Options","• Help","• Quit"
	};

	public  void update(){
		bg.update();
	}
	public  void draw(Graphics2D g){
		//draw bg
		bg.draw(g);
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Street Fruiter",GamePanel.WIDTH /2 -900,310);
		g.setFont(sm.GameFont.deriveFont(70f));
		g.setColor(Color.DARK_GRAY);
		g.drawString("Kitchen Bash",GamePanel.WIDTH /2 -775,400);
		
		g.setFont(font);
		for(int i = 0; i< options.length; i ++)
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
			g.drawString(options[i],GamePanel.WIDTH/2- 700 + xOffset , GamePanel.HEIGHT/2 + i* 85 + 200);
		}
	}
	private void select()
	{
		if(currentChoice == 0)
		{
			sm.setState(sm.CHARACTERSELECTION);
			//2Player
		}
		else if(currentChoice == 1)
		{
			sm.setState(sm.OPTIONSSTATE);
			//Options
		}

		else if(currentChoice == 2)
		{
			//Help and Credits
		}
		else if(currentChoice == 3)
		{
			System.exit(0);
			//Quit
		}
	}

	public  void keyPressed(int k){
		if(k == KeyEvent.VK_ENTER)
		{
			select();
		}
		else if(k == KeyEvent.VK_UP)
		{
			currentChoice--;
			if(currentChoice ==-1)
			{
				currentChoice = options.length - 1;
			}
		}
		else if(k == KeyEvent.VK_DOWN)
		{
			currentChoice++;
			if(currentChoice == options.length )
			{
				currentChoice = 0;
			}

		}
	}
	public  void keyReleased(int k){

	}
	public int[] getData() {
		return null;
	}
}
