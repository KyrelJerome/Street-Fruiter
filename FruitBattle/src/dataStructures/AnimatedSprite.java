package dataStructures;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
/**
 * <P>
 * This class handles all AnimatedSprites that are drawn in the foreground. It allows for a spritesheet to be
 * handled and used, although it is innefficient due to the fact that it renders its own spritesheet.
 * Uses bufferedImage <code>KeyListener</code>.
 *
 *@author Kyrel Jerome
 *@version 1
 *@Stability Unknown
 *@see GameObject
 *
 */
public class AnimatedSprite {
	
	private int x;
	private int y;
	private int scale;
	private boolean isVisible = true;
	private Image currentImg;
	private String direction = "R";
	private boolean isAnimating;
	private BufferedImage[][] sprites;
	protected int animationFrameTimes[][];
	private BufferedImage startImg;
	private boolean doesLoop;
	private int currentAnimation = 0;
	private int currentPartitionFrame = 0;
	private int currentAnimationPartition = 0;
	public AnimatedSprite(int animationFrameTimes[][],String Direction,int spriteimgX[][],int spriteimgY[][],int spriteHeights[],int spriteWidths[],int imgrows, int imgcols,int X, int Y,int initrow,int initcol, int Scale,String spritesheet)		
	{
		//Do direct updates
		scale = Scale;
		direction = Direction;
		x = X;
		y = Y;
		this.animationFrameTimes = animationFrameTimes;
		//load spritesheet
		try 
		{
			startImg = ImageIO.read(new File(spritesheet));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("File Not Found. Filename Inputted : " + spritesheet);
		}
		//organise spritesheet and control it
		sprites = new BufferedImage[imgrows][imgcols];
		//this code is cancer and is an entire program on its own please dont make me comment this
		for(int i = 0; i < imgrows; i++)
		{
			for (int j = 0; j < imgcols; j++)
			{
				if(animationFrameTimes[i][j] != 0)
				{
					sprites[i][j] = startImg.getSubimage(spriteimgX[i][j] , spriteimgY[i][j],spriteWidths[i],spriteHeights[j]);
				}
			}
		}
	}
	public Image getImage() {
		return currentImg;
	}
	public void draw(Graphics2D g)
	{
		Image tempImage = currentImg.getScaledInstance(currentImg.getWidth(null)*scale, currentImg.getWidth(null)*scale, java.awt.Image.SCALE_SMOOTH);
		if(isVisible)
		{
			g.drawImage(tempImage, x, y,  null);
		} 
	}
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	private void update(){
		currentPartitionFrame ++ ;//Up the partition Frame
		if(isAnimating)//Checks if image is animating
		{
			if(animationFrameTimes[currentAnimation][currentAnimationPartition] <= currentPartitionFrame)//If the animation is within it's frame times
			{
				currentImg = sprites[currentAnimation][currentAnimationPartition];
				
			}
			else//If not
			{
				if(animationFrameTimes[currentAnimation].length >  currentAnimationPartition + 1)//if the array can go further
				{
					//If the array can, make it go further(Avoids null Pointer exception)
					currentAnimationPartition ++;
					if(animationFrameTimes[currentAnimation][currentAnimationPartition ] == 0)//If it actually couldnt go further due to 
					{	//the animation being done and not having more partitions
						
						if(doesLoop)
						{
							currentAnimationPartition = 0;
						}
						else
						{
							currentAnimation = 0;
							currentAnimationPartition = 0;
							isAnimating = false;
						}
					}
					else
					{
						
						currentPartitionFrame = 0;
						currentImg = sprites[currentAnimation][currentAnimationPartition];
					}
				}
			}
		}

	}
	private void setCurrentAnimation(int AnimationNumber)
	{
		currentAnimation = AnimationNumber;
	}
	private void interrupt()
	{
		isAnimating = false;
		isVisible = false;
	}
	private void initSprite(){
		ImageIcon ii = new ImageIcon(sprites[0][0]);
		currentImg = ii.getImage();
	}
}
