package animations.Pear;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import dataStructures.Animation;

public class PearJump extends Animation{
	private final String file = "/pear/PearJump";
	private int presetFrameLengths[]={4,4, 4,5,5,6000};
	private final int totalsprites = 6;
	public PearJump(){
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
