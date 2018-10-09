package dataStructures;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ScreenController.BattleState;
import ScreenController.CharacterSelection;
import ScreenController.GameState;
import ScreenController.MenuState;
import ScreenController.OptionsState;
public class ScreenManager {
	public ArrayList<GameState> gameStates;
	private int currentState;
	private int newState;
	private boolean isInitialized = true;
	public static final int MENUSTATE = 0;
	public static final int CHARACTERSELECTION = 1;
	public static final int OPTIONSSTATE = 2;
	public static final int BATTLESTATE = 3;
	
	public Font GameFont ;
	public ScreenManager()
	{
		//Game Font loading
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/pdark.ttf");
			GameFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameFont = GameFont.deriveFont(40f);
		gameStates = new ArrayList<GameState>();
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new CharacterSelection(this));
		gameStates.add(new OptionsState(this));
		gameStates.add(new BattleState(this));
	}
	public Font getFont()
	{
		return GameFont;
	}
	public void setState(int state)
	{
		isInitialized = false;
		newState = state;
		if (currentState == BATTLESTATE)
		{
			//gameStates.remove(BATTLESTATE);
			//gameStates.add(new BattleState(this));
		}
		gameStates.get(currentState).init();
		isInitialized = true;
	}
	public void update() {

		currentState = newState;
		gameStates.get(newState).update();
	
	}

	public void draw(Graphics2D g)
	{
		if(isInitialized)
		{
			gameStates.get(currentState).draw(g);
		}
	}
	public void keyPressed(int k)
	{
		if(isInitialized)
		{
			gameStates.get(currentState).keyPressed(k);

		}
	}
	public void keyReleased(int k)
	{

		if(isInitialized)
		{
			gameStates.get(currentState).keyReleased(k);
		}
	}
}