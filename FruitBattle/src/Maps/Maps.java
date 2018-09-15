package Maps;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.GamePanel;
import dataStructures.AttackObject;
import dataStructures.CharacterObject;
import dataStructures.GameObject;
import dataStructures.HUD;
/**
 * <P>
 * This class handles all Collisions between two players. The code here can be bettered
 * through the use of 2D ArrayLists. Due to Java's Graphics2D 
 * innefficiencies, the game is incapable of running at a full 60fps on most devices.
 *  <code>KeyListener</code>.
 *
 *@author Kyrel Jerome
 *@version 5.3
 *@Stability 80%
 *@see Graphics2D, Image, HUD, KeyLogger, CharacterObject 
 *
 */
public abstract class Maps {
	//For knowing what objects the collisions are happening with
	//non-Game Affected Graphics
	public int spawnPointsX[];
	public int spawnPointsY[]; 
	ArrayList<Background> backgroundLayers = new ArrayList<Background>();
	ArrayList<Background> foregroundLayers = new ArrayList<Background>();
	//CODE FOR CAMERA USABILITY
	private BufferedImage constructionImage  = new BufferedImage(GamePanel.WIDTH,GamePanel.HEIGHT,BufferedImage.TYPE_INT_ARGB);
	private Graphics2D canvas = (Graphics2D) constructionImage.getGraphics();
	//private final int TOTAL_WORKING_PLAYERS = 2;
	//GAME OBJECTS
	public CharacterObject[] characters = new CharacterObject [2];
	public ArrayList<AttackObject> P1attacks = new ArrayList<AttackObject>(0);
	public ArrayList<AttackObject> P2attacks = new ArrayList<AttackObject>(0);

	//public ArrayList<ArrayList<AttackObject>> attacks = new ArrayList<ArrayList<AttackObject>>();
	public ArrayList <GameObject> MapObjects = new ArrayList<GameObject>();
	// **********PENDING USE PARTICLE CODE *****************
	//ArrayList<ParticleObject>[] particles = (ArrayList<ParticleObject>[])new ArrayList[3];
	//PARTICLE CODE END
	//COMPLETE
	public Maps(int players)
	{
	}
	public abstract void init();
	//COMPLETE
	public void setCharacters(CharacterObject[] loadingCharacters){
		characters = loadingCharacters;
	}
	public void cleanArrayLists(){
		//remove excess Map

		//remove excess Map
		if(MapObjects.size()> 0)
		{
			for(int i = 0; i < MapObjects.size(); i ++ )
			{
				if(!MapObjects.get(i).shouldExist)
				{
					MapObjects.remove(i);
					i = i- 1;
				}
			}
		}
		if(P2attacks.size()> 0)
		{
			for(int i = 0; i < P2attacks.size(); i ++ )
			{
				if(!P2attacks.get(i).shouldExist){
					P2attacks.remove(i);
					i --;
				}
			}
		}
		if(P1attacks.size()> 0)
		{
			for(int i = 0; i < P1attacks.size(); i ++ )
			{
				if(!P1attacks.get(i).shouldExist){
					P1attacks.remove(i);
					i --;
				}
			}
		}
	}	
	public void draw(Graphics2D g){
		//Draws backgroundLayers 
		drawbackgroundLayers(g);
		//Draw Map
		drawMapObjects(g);
		//Draw Characters
		drawCharacters(g);

		//Draw Attacks
		drawAttacks(g);

		//Draws foregroundLayers
		drawforegroundLayers(g);
	}
	public void drawAttacks(Graphics2D g){
		if(P2attacks.size()> 0)
		{
			for(int i = 0; i < P2attacks.size(); i ++ )
			{
				P2attacks.get(i).draw(g);
			}
		}
		if(P1attacks.size()> 0)
		{
			for(int i = 0; i < P1attacks.size(); i ++ )
			{
				P1attacks.get(i).draw(g);
			}
		}
	}
	public void drawMapObjects(Graphics2D g){
		if(MapObjects.size()> 0)
		{
			for(int i = 0; i < MapObjects.size(); i ++ )
			{
				MapObjects.get(i).draw(g);
			}
		}
	}
	public void drawCharacters(Graphics2D g){
		if(characters.length> 0)
		{
			for(int i = 0; i < characters.length; i ++ )
			{
				characters[i].draw(g);
			}
		}

	}

	//COMPLETE
	public BufferedImage drawtoCamera(){
		draw(canvas);
		return constructionImage;
	}

	//COMPLETE
	public void update()
	{
		updateLayers();
		updateObjects();
		updateCollisions();
		updateHitboxCollisions();
		cleanArrayLists();
	}
	private void updateHitboxCollisions(){
		if(P2attacks.size()> 0)
		{
			for(int i = 0; i < P2attacks.size(); i ++ )
			{
				if(characters[1].getHitbox().intersects(P2attacks.get(i).getHitbox())){
					characters[1].onHit(P2attacks.get(i).getHitStun(), P2attacks.get(i).getDamageDealt(), P2attacks.get(i).getVectorX(),P2attacks.get(i).getVectorY());
					P2attacks.get(i).onHit();
				}
			}
		}
		if(P1attacks.size()> 0)
		{
			for(int i = 0; i < P1attacks.size(); i ++ )
			{
				if(characters[0].getHitbox().intersects(P1attacks.get(i).getHitbox())){
					characters[0].onHit(P1attacks.get(i).getHitStun(), P1attacks.get(i).getDamageDealt(), P1attacks.get(i).getVectorX(),P1attacks.get(i).getVectorY());
					P1attacks.get(i).onHit();
				}
			}
		}

	}
	//COMPLETE
	private void updateObjects() {
		if(characters.length > 0)
		{
			for(int i = 0; i < characters.length; i ++)
			{
				characters[i].update();
				if(characters[i].getY() < 0)
				{
					characters[i].kill();
				}
			}
		}

		if(MapObjects.size()> 0)
		{
			for(int i = 0; i < MapObjects.size(); i ++)
			{
				MapObjects.get(i).update();
			}
		}
		if(P2attacks.size()> 0)
		{
			for(int i = 0; i < P2attacks.size(); i ++ )
			{
				P2attacks.get(i).update();
				if(P2attacks.get(i).getX() > 2020 || P2attacks.get(i).getX() < -100 ){
					P2attacks.remove(i);
					i --;
				}
			}
		}
		if(P1attacks.size()> 0)
		{
			for(int i = 0; i < P1attacks.size(); i ++ )
			{
				P1attacks.get(i).update();
				if(P1attacks.get(i).getX() > 2020 || P1attacks.get(i).getX() < -100 ){
					P1attacks.remove(i);
					i --;
				}
			}
		}
	}
	//INCOMPLETE
	private void updateCollisions() {
		if(characters.length > 0)
		{
			for(int i = 0; i < characters.length; i ++)
			{
				if(MapObjects.size()> 0)
				{
					for(int k = 0; k < MapObjects.size(); k ++)
					{
						if(MapObjects.get(k).getHitbox().intersects(characters[i].getHitbox()))
						{
							characters[i].onCollision(MapObjects.get(k).getHitbox().x,MapObjects.get(k).getHitbox().y);
						}
					}
				}
			}
		}
	}
	//COMPLETE
	public void updateLayers()
	{
		if(backgroundLayers.size() > 0)
		{
			for(int i = 0; i < backgroundLayers.size(); i++)
			{
				backgroundLayers.get(i).update();
			}
		}		
		if(foregroundLayers.size() > 0)
		{
			for(int i = 0; i < foregroundLayers.size(); i++)
			{
				foregroundLayers.get(i).update();
			}
		}
	}
	//COMPLETE
	public void drawbackgroundLayers(Graphics2D gm)
	{
		if(backgroundLayers.size() > 0)
		{
			for(int i = 0; i < backgroundLayers.size(); i ++)
			{
				backgroundLayers.get(i).draw(gm);
			}
		}
	}
	//COMPLETE
	public void drawforegroundLayers(Graphics2D gm)
	{
		if(foregroundLayers.size() > 0)
		{
			for(int i = 0; i < foregroundLayers.size(); i ++)
			{
				foregroundLayers.get(i).draw(gm);
			}
		}
	}
}
