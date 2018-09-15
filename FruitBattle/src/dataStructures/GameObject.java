package dataStructures;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import Maps.Maps;

public abstract class GameObject {
	Maps map;

	protected Image imageDrawn;
	
	public int direction;
	protected int x;

	protected int y;
	protected int dy;
	protected int dx;
	protected int scale = 1;
	protected Rectangle hitbox;
	protected boolean isVulnerable;
	protected boolean isVisible = true;
	public boolean shouldExist = true;
	public abstract void update();
	public abstract void onHit();
	public abstract void onCollision();
	public abstract void interrupt();
	public abstract void kill();
	public void draw(Graphics2D g){
		if(isVisible)
		{
			if(direction == -1)
			{
				g.drawImage(imageDrawn, x + (imageDrawn.getWidth(null)*scale), y, -(imageDrawn.getWidth(null)*scale), (imageDrawn.getHeight(null)*scale), null);

			}
			else{
				g.drawImage(imageDrawn, x + (imageDrawn.getWidth(null)*scale), y, (imageDrawn.getWidth(null)*scale), (imageDrawn.getHeight(null)*scale), null);
			}
		}
	}
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
}
