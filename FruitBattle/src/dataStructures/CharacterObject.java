package dataStructures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import Maps.Maps;

public abstract class CharacterObject {
	//Used to interface with the game engine
	protected Maps map;
	private int playerNumber;
	private String playerName;
	private String characterName;
	//inputs and input handling
	public static final int KEY_LEFT = 0;
	public static final int KEY_RIGHT = 1;
	public static final int KEY_UP = 2;
	public static final int KEY_DOWN = 3;
	public static final int KEY_ATTACK = 4;
	public static final int KEY_SPECIAL = 5;
	protected int registeredInputs[] = new int[3];
	protected int inputs[] = new int[5];
	final int maxInput = 5;
	//Animation handling
	protected final int NEUTRAL = 0;
	protected final int ATTACK = 1;
	protected final int SPECIAL = 2;
	protected final int RUN = 3;
	protected final int JUMP = 4;
	protected final int FALL = 5;
	protected final int LANDING = 6;
	protected final int HITSTUN = 7;
	protected ArrayList <Integer> hitframelog = new ArrayList<Integer>(0);
	protected ArrayList <String> hitIDlog = new ArrayList<String>(0);
	protected int health = 100;
	protected Animation[] animations = new Animation[8];
	public int direction;
	protected int currentAnimation = 0;
	//Movement and game engine/physics
	protected int x;
	protected int y;
	protected double scale;
	protected int width;
	protected int height;
	protected int dy;
	protected int dx;
	protected Rectangle hitbox;

	//character states and stats
	protected int usedJumps = 0;
	protected int jumpCounter = 0;
	protected boolean isFalling = false;
	protected int hitStunLength;
	protected boolean isVulnerable;
	protected boolean isVisible;
	public boolean shouldExist = true;
	protected boolean isHitstun;
	protected int deaths = 0;
	protected boolean isAlive = true;
	protected boolean isInputLagged;
	protected boolean isOnPlatform = false;
	protected boolean isJumping = false;
	//Move states
	protected boolean isAttacking;
	protected boolean isSpecial;
	protected int inputLag = 0;
	//SUPERCLASS
	public CharacterObject(int playerNumber){
		this.playerNumber = playerNumber;
	}
	//MOVES and Character Specific updates
	protected abstract void useSpecial();
	protected abstract void useAttack();
	protected abstract void moveInAir();
	protected abstract void moveOnGround();
	protected abstract void fastfall();
	protected abstract void fall();
	protected abstract void jump();
	protected abstract void checkJump();
	//Runs a specific animation and etc according to what
	public abstract void spawn(int x, int y);
	public abstract void setInputs(int inputs[]);
	public abstract void onHit(int hitstun, int damage,int dx, int dy);
	public abstract void onCollision(int x, int y);
	public abstract void interrupt();
	public abstract void kill();
	//Updates style independant of character
	public void update() {

		if(isAlive)
		{
			handleInputs();
			animations[currentAnimation].update();
			System.out.println(dx);
			if(isInputLagged)
			{				
				inputLag --;
				if(inputLag <= 0)
				{
					isInputLagged = false;
					isAttacking = false;
					isSpecial = false;
				}
				if(isAttacking)
				{
					updateAttack();
				}
				if(isSpecial)
				{
					updateSpecial();
				}
				if(!isOnPlatform){
					if(isJumping  && !isFalling)
					{
						checkJump();
					}
					else if(isFalling && !isJumping)
					{
						isFalling = true;
						fall();
					}
				}
			}
			else if(isHitstun)
			{
				isJumping = false;
				isFalling = false;
				isInputLagged = false;
				inputLag = 0;
				isAttacking = false;
				isSpecial = false;
				hitStunLength -- ;
				currentAnimation = HITSTUN;
				if(hitStunLength == 0)
				{
					isHitstun = false;
					animations[currentAnimation].interrupt();

					isFalling = true;
					currentAnimation = FALL;
					animations[currentAnimation].startAnimation();

				}
			}
			else
			{
				checkJump();
				if(isOnPlatform)
				{

					if(registeredInputs[0] == KEY_LEFT)
					{
						currentAnimation = RUN;
						direction = -1;
						moveOnGround();

					}
					else if(registeredInputs[0] == KEY_RIGHT)
					{
						currentAnimation = RUN;
						direction = 1;
						moveOnGround();

					}
					else
					{
						currentAnimation = NEUTRAL;
						dx = 0;
					}
					if(!isInputLagged)
					{
						if(registeredInputs[2] == KEY_SPECIAL)
						{
							useSpecial();
						}
						else if(registeredInputs[2] == KEY_ATTACK)
						{ 
							useAttack();
						}


						if(!isJumping){
							if(registeredInputs[1] == KEY_UP)
							{
								jump();

							}
						}
					}
				}
				else
				{
					if(registeredInputs[0] == KEY_LEFT)
					{
						direction = -1;
						moveInAir();
					}
					else if(registeredInputs[0] == KEY_RIGHT)
					{
						direction = 1;
						moveInAir();

					}
					else
					{

						dx = 0;
					}
					if(registeredInputs[2] == KEY_SPECIAL)
					{
						useSpecial();
					}
					else if(registeredInputs[2] == KEY_ATTACK)
					{ 
						useAttack();
					}
					if(!isJumping){
						if(registeredInputs[1] == KEY_UP)
						{

							jump();

						}
						else if(registeredInputs[1] == KEY_DOWN )
						{
							fastfall();
						}
						else{
							fall();
						}
					}


				}
			}

			isOnPlatform = false;
			animations[currentAnimation].setCoordinates((int)x,(int) y);
			hitbox.setFrame(x+ 30, y, (width*scale) - 40, height*scale);
			x +=dx;
			y +=dy;
		}
	}
	public void draw(Graphics2D g) {
		if(isVisible)
		{
			g.setPaint(Color.WHITE);
			g.setFont(null);
			//g.drawRect(hitbox.x, hitbox.y, width, height);
			animations[currentAnimation].setScale(scale);
			animations[currentAnimation].setDirection(direction);
			animations[currentAnimation].draw(g);
		}
	}
	public abstract void updateSpecial();
	public abstract void updateAttack();
	private void handleInputs(){
		//This code makes sure that the character only acts upon the most recent inputs
		//  Horizontal Direction input management
		if(inputs[KEY_LEFT] == inputs[KEY_RIGHT])
		{
			registeredInputs[0] = -1;//No movement input
		}
		else if(inputs[KEY_LEFT] > inputs[KEY_RIGHT]&& inputs[KEY_RIGHT] > -1 || inputs[KEY_RIGHT] > inputs[KEY_LEFT] && inputs[KEY_LEFT] ==-1)
		{
			registeredInputs[0] = KEY_RIGHT;//Right facing input
		}
		else if(inputs[KEY_LEFT] < inputs[KEY_RIGHT] && inputs[KEY_LEFT] > -1 ||inputs[KEY_LEFT] > inputs[KEY_RIGHT] && inputs[KEY_RIGHT]==-1)
		{
			registeredInputs[0] = KEY_LEFT;//Left facing input
		}

		// Vertical input management
		if(inputs[KEY_DOWN] == inputs[KEY_UP])
		{
			registeredInputs[1] = -1;//No movement input
		}
		else if(inputs[KEY_UP] > inputs[KEY_DOWN]&& inputs[KEY_DOWN] > -1 ||inputs[KEY_DOWN] > inputs[KEY_UP] && inputs[KEY_UP]==-1 )
		{
			registeredInputs[1] = KEY_DOWN;//Downwards input
		}
		else if(inputs[KEY_UP] < inputs[KEY_DOWN] && inputs[KEY_UP] > -1||inputs[KEY_UP] > inputs[KEY_DOWN] && inputs[KEY_DOWN]==-1 && inputs[KEY_UP] < maxInput)
		{
			registeredInputs[1] = KEY_UP;//Upwards input
		}
		else
		{
			registeredInputs[1]= -1;
		}
		// ATTACK input management
		if(inputs[KEY_SPECIAL] > inputs[KEY_ATTACK]&& inputs[KEY_ATTACK] > -1||inputs[KEY_ATTACK] > inputs[KEY_SPECIAL] && inputs[KEY_SPECIAL]==-1)
		{
			registeredInputs[2] = KEY_ATTACK;//Basic Attack input
		}
		else if(inputs[KEY_SPECIAL] < inputs[KEY_ATTACK] && inputs[KEY_SPECIAL] > -1||inputs[KEY_SPECIAL] > inputs[KEY_ATTACK] && inputs[KEY_ATTACK]==-1)
		{
			registeredInputs[2] = KEY_SPECIAL;//Special input
		}
		else if(inputs[KEY_SPECIAL] == inputs[KEY_ATTACK] && inputs[KEY_SPECIAL] > 0)
		{
			registeredInputs[2] = KEY_ATTACK;
		}
		else
		{
			registeredInputs[2] = -1;//No attack input
		}
	}

	//Information setters and getters
	public int getDeaths(){
		return deaths;
	}
	public int getPlayerNumber(){
		return playerNumber;
	}
	public void setX(int x){
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getHealth()
	{
		return health;
	}
	public String getCharacterName(){
		return characterName;
	}
	public Rectangle getHitbox(){
		return hitbox;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	public double getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public boolean isVulnerable() {
		return isVulnerable;
	}
	public void setVulnerable(boolean isVulnerable) {
		this.isVulnerable = isVulnerable;
	}
	public void setIsAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}
	public void getIsAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}
}
