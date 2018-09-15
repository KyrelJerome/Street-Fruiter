package Maps;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import dataStructures.GameObject;

public class TableTop extends GameObject {
	String filePath = "/MapObjects/TableTop.png";
	private int width = 1920;
	private int height = 420;
	
	public TableTop(){
		x = 0;
		y = 900;
		direction = 1;
		hitbox = new Rectangle( x ,y +75,x +width,y + height);
		try {
			imageDrawn = ImageIO.read(getClass().getResourceAsStream(filePath)).getScaledInstance(width, height,Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public void update() {
		shouldExist = true;
	}


	public void onHit() {
		// TODO Auto-generated method stub

	}


	public void onCollision() {
		// TODO Auto-generated method stub

	}


	public void interrupt() {
		// TODO Auto-generated method stub

	}


	public void kill() {
		shouldExist = false;
	}


	public void draw(Graphics2D g) {

		if(isVisible)
		{
			if(direction == -1)
			{
				g.drawImage(imageDrawn, x + (imageDrawn.getWidth(null)*scale), y, -(imageDrawn.getWidth(null)*scale), (imageDrawn.getHeight(null)*scale), null);

			}
			else{
				g.drawImage(imageDrawn, x, y, (imageDrawn.getWidth(null)*scale), (imageDrawn.getHeight(null)*scale), null);
			}
		}
	}

}
