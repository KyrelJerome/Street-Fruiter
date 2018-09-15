package animations.Pear;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import dataStructures.Animation;

public class PearNeutral extends Animation{
	private final String file = "/pear/PearIdle";
	private int presetFrameLengths[]={60/2,60/15,60/15,60/10,60/15};
	private final int totalsprites = 5;
	public PearNeutral(){
		SpriteSheet = new BufferedImage[5];
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
		loopstartFrame = 1;
		frameLength = presetFrameLengths;
		doesLoop = true;
	}



}
