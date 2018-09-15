package dataStructures;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import Maps.Maps;

public abstract class AttackObject{

	int player;
	Maps map;
	protected Rectangle hitbox;
	protected int damage;
	public Animation mainAnimation;
	public int direction;
	protected int x;
	protected int width;
	protected int height;
	protected int y;
	protected int dy;
	protected int dx;
	protected int vectorx;
	protected int vectory;
	protected int damagedealt;
	protected int hitstun;
	protected boolean isVulnerable;
	protected boolean canCollide = true;
	protected boolean isVisible = true;
	public boolean shouldExist = true;
	public abstract void update();
	public abstract void onHit();
	public abstract void onCollision();
	public abstract void interrupt();
	public abstract void kill();
	public abstract void draw(Graphics2D g);
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Rectangle getHitbox(){
		return hitbox;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	public int getDx() {
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
	public int getDamageDealt()
	{
		return damage;
	}
	public boolean getCanCollide()
	{
		return canCollide;
	}
	public int getVectorX(){
		return vectorx*direction;
	}
	public int getVectorY(){
		return vectory ;
	}
	public int getHitStun(){
		return hitstun;
	}

}
