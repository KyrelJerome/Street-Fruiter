package attackObjects;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import dataStructures.Animation;

public class PearBulletAnimation extends Animation{
	private final String file = "/pear/Bullet";
	private int presetFrameLengths[]={20/5,20/5, 100000};
	private final int totalsprites = 3;
	public PearBulletAnimation(){
		SpriteSheet = new BufferedImage[totalsprites];
		totalFrames = totalsprites;
		for(int i = 0; i < totalFrames; i ++)
		{
			
			String tempFile = file + (i+1) + ".png";
			//System.out.println(tempFile);
			try {
				SpriteSheet[i] = ImageIO.read(getClass().getResourceAsStream(tempFile));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		isVisible = true;
		currentFrame = 0;
		loopstartFrame = 0;
		frameLength = presetFrameLengths;
		doesLoop = true;
	}



}
