package characters;

import java.awt.Rectangle;
import java.util.ArrayList;

import Main.GamePanel;
import Maps.Maps;
import animations.Pear.PearAttack;
import animations.Pear.PearFall;
import animations.Pear.PearHitStun;
import animations.Pear.PearJump;
import animations.Pear.PearNeutral;
import animations.Pear.PearRun;
import attackObjects.PearBullet;
import dataStructures.CharacterObject;

public class Pear extends CharacterObject{
	//***CHARACTER DEPENDANT VARIABLES***
	public final String CHARACTER_NAME = "Pear";
	private final int TOTAL_JUMPS = 2;
	private final int groundMoveSpeed = 25;
	private final int airMoveSpeed = 35;
	private final int jumpSpeed = 27;
	private final int fallSpeed = 30;
	protected int jumpHeight = 300;

	//***ALL PROCEDURES START HERE***
	//Constructor
	public Pear(int x, int y, Maps map, int PlayerNumber){
		super(PlayerNumber);

		this.map = map;
		ArrayList<String> actions = new ArrayList<String>();
		animations[NEUTRAL] = new PearNeutral();
		animations[FALL] = new PearFall();
		animations[RUN] = new PearRun();
		animations[HITSTUN] = new PearHitStun();
		animations[ATTACK] = new PearAttack();
		animations[JUMP] = new PearJump();
		width = 200;
		height = 200;
		animations[ATTACK].setSize(width, height);

		animations[FALL].setSize(width, height);
		animations[JUMP].setSize(width, height);
		animations[RUN].setSize(width, height);
		animations[NEUTRAL].setSize(width, height);
		this.x = x; 
		this.y = y;
		hitbox = new Rectangle(width - 40,height);
		hitbox.setLocation(x + 60, y);
		init(x, y);
	}
	//Called when restarting the map or game
	private void init(int x, int y){
		currentAnimation = NEUTRAL;
		scale = 1;
		health = 100;
		dx = 0;
		dy = 0;
		isHitstun = false;
		isInputLagged = false;
		isVisible = true;
		isVulnerable = true;
		isJumping = false;
		isFalling = true;
		hitStunLength = 0;
		if(x < GamePanel.WIDTH/2)
		{
			direction = 1;
		}
		else
		{
			direction = -1;
		}
	}

	//UPDATE CHARACTER INPUTS	
	public void setInputs(int[] inputs) {
		this.inputs = inputs;
	}

	//COLLISION AND HIT DETECTION AND ACTION
	public void onHit(int hitstun, int damage,int dx, int dy) {
		
		isInputLagged = false;
		inputLag = 0;
		isJumping = false;
		isFalling = true;
		isHitstun = true;
		health = health - damage;
		hitStunLength = hitstun;
		animations[currentAnimation].interrupt();
		currentAnimation = HITSTUN;
		System.out.println(dx);
		animations[currentAnimation].startAnimation();
		this.dx = dx;
		this.dy = dy;
			}
	public void interrupt() {
		//Unused and unnecessary code, could be used by a programmer in the future.
		//TODO NOT FOR FINAL PROJECT
	}
	public void kill() {
		deaths = deaths + 1;
		isVisible = false;
		isVulnerable = false;
	}
	public void spawn(int x, int y) {
		init(x,y);
	}
	public void onCollision(int x, int y){
		//Set jumping to false, and reset jumps, if a 
		this.y = y- height + 1;
		jumpCounter = 0;
		usedJumps = 0;
		isFalling = false;
		isJumping = false;
		if(isHitstun)
		{
			dy = -dy;
			y = y - 3 ;//This resets the collision, allowing for the
			//bouncing effect in the game.
		}
		else
		{
			//Allow for regular walking
			isFalling = false;
			dy = 0;
			isOnPlatform = true;
		}
	}

	//MOVE AND STATE MANAGEMENT 	
	protected void useSpecial() {
		//TODO NOT FOR FINAL PROJECT
	}
	protected void useAttack() {
		animations[currentAnimation].interrupt();
		currentAnimation = ATTACK;
		animations[currentAnimation].startAnimation();
		isAttacking = true;
		isInputLagged = true;
		inputLag = 35;

	}
	protected void moveInAir() {
		dx = direction*airMoveSpeed;
	}
	protected void moveOnGround() {
		dx = direction*groundMoveSpeed;
		currentAnimation = RUN;
	}
	protected void fastfall() {
		if(!isJumping && isFalling){
			currentAnimation = FALL;
			dy = (fallSpeed);
		}
	}
	protected void fall() {
		if(!isJumping && isFalling){
			if(!isInputLagged)
			{
				currentAnimation = FALL;
			}
			dy = (fallSpeed*4)/5;
		}
	}
	protected void jump() {
		if(usedJumps < TOTAL_JUMPS & !isJumping ){
			isJumping =true;
			isFalling = false;
			dy = -jumpSpeed;
			y= y - 10;
			usedJumps ++;
			animations[currentAnimation].interrupt();
			currentAnimation = JUMP;
			animations[currentAnimation].startAnimation();
		}
		else{
			//currentAnimation = FALL;
			isFalling = true;
			dy = (fallSpeed*4)/5;

		}
	}
	protected void checkJump() {
		if(isJumping)
		{
			jumpCounter++;
			if(jumpCounter*jumpSpeed >= jumpHeight)
			{		
				if(! isInputLagged)
				{
					animations[currentAnimation].interrupt();
					currentAnimation = FALL;
					animations[currentAnimation].startAnimation();
				}
				dy = 0;
				jumpCounter = 0;
				isJumping = false;
				if(!isOnPlatform)
				{
					isFalling = true;
				}
			}

		}
	}

	public void updateSpecial() {
		//TODO NOT FOR FINAL PROJECT
	}

	public void updateAttack() {
		if(inputLag == 5)
		{
			if(getPlayerNumber() == 1)
			{
				map.P1attacks.add(new PearBullet(x +(150*direction),y - 30 + width/2,direction));

			}
			else if(getPlayerNumber() == 0)
			{
				map.P2attacks.add(new PearBullet(x +(150*direction),y - 30 + width/2,direction));

			}
		}
	}

}