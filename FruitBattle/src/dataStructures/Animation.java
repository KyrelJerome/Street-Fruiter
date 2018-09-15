package dataStructures;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
/**
 * <P>
 * This class handles all Animations. It allows for a sprite sheet to be
 * handled and used, although it is inefficient due to the fact that it renders its own spritesheet.
 * Uses bufferedImage <code>KeyListener</code>.
 *
 *@author Kyrel Jerome
 *@version 2.1
 *@Stability 
 *@see Image, BufferedImage
 *
 */
public abstract class Animation {
	protected BufferedImage SpriteSheet[];
	protected int x = 0;
	protected int y = 0;
	int width = 150, height = 150;
	protected double scale = 1;
	protected boolean isVisible = true;
	protected int currentFrame = 0;
	protected int loopstartFrame = 0;
	protected int frameLength[];
	protected int framesElapsed;
	protected int direction = 1;
	protected boolean doesLoop;
	protected int totalFrames;
	protected boolean isRunning = true;
	protected boolean isSizeSet = false;
	/**
	 *  VOIDS
	 */
	public void startAnimation(){
		// TODO Auto-generated method stub
		framesElapsed = 0;
		isRunning = true;
		currentFrame = 0;

	}
	public void setSize(int width, int height)
	{
		isSizeSet = true;
		this.width = width;
		this.height = height;
	}
	public void update() {
		// TODO Auto-generated method stub
		if(isRunning)
		{
			if(framesElapsed >= frameLength[currentFrame])
			{
				framesElapsed = 0;
				currentFrame ++ ;
				if(doesLoop && currentFrame == totalFrames)
				{
					currentFrame = loopstartFrame;
				}
				else if(!doesLoop&& currentFrame == totalFrames)
				{
					System.out.println("did not loop");
					isRunning = false;
					currentFrame --;
				}
			}
			else
			{
				framesElapsed++;	
			}
		}
	}
	public void interrupt() {
		// TODO Auto-generated method stub
		framesElapsed = 0;
		isRunning = true;
		currentFrame = 0;
	}
	public void draw(Graphics2D g)
	{
		if(isVisible)
		{
			Image drawnImage = SpriteSheet[currentFrame].getScaledInstance(width, height, Image.SCALE_FAST);
			if(!isSizeSet)
			{
				if(direction == -1)
				{
					g.drawImage(drawnImage,(int) (x + (drawnImage.getWidth(null)*scale)), (int) (y),(int) ( -(drawnImage.getWidth(null)*scale)),(int) ( (drawnImage.getHeight(null)*scale)), null);

				}
				else{
					g.drawImage(drawnImage, (int) x  , (int) (y), (int) ((drawnImage.getWidth(null)*scale)), (int) ((drawnImage.getHeight(null)*scale)), null);
				}
			}
			else{
				if(direction == -1)
				{
					g.drawImage(drawnImage,(int) (x +(width*scale)) , (int) (y),(int) -(width*scale),(int) ( height*scale), null);

				}
				else{
					g.drawImage(drawnImage, (int) x, (int) (y), (int) (width*scale), (int) (height*scale), null);
				}
			}
		}
	}
	public void setCoordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public void setDirection(int Direction)// Returns the direction,  1 being right, -1 being forward.
	{
		direction = Direction;
	}
	public void setFrame(int currentFrame)
	{
		if(currentFrame < totalFrames)
		{
			this.currentFrame = currentFrame;
		}
	}
	public int getDirection(){
		return direction;
	}
	public int getFrame()
	{
		return currentFrame;
	}
	public void setScale(double scale){
		this.scale = scale;
	}
	public void freeze(){
		isRunning = false;
	}
	public void unfreeze(){
		isRunning = true;
	}
}
