package Maps;
import Main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.Image;

public class Background {
	private BufferedImage image;

	private double x;
	private double y;
	private double dx;
	private double dy;

	private double moveScale;

	public Background(String s, double ms)
	{
		moveScale = ms;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			if(image == null)
			{
				System.out.println("Image not found");
			}
		}
		catch(Exception e)
		{

		}
	}
	public void changewidth(int width){
		image = (BufferedImage) image.getScaledInstance(width, image.getHeight(),Image.SCALE_SMOOTH);
	}
	public void setPosition(double x1, double y1)
	{		x = (x1* moveScale) % GamePanel.WIDTH ;
	y = (y1* moveScale) % GamePanel.HEIGHT;
	}
	public void setVector(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	public void update()
	{
		if(image.getWidth() > GamePanel.WIDTH)
		{
			x = (dx + x)% image.getWidth();
		}
		else{
			x = (dx + x)% GamePanel.WIDTH ;

		}
		if(image.getHeight() > GamePanel.HEIGHT)
		{
			y = (dy + y)%image.getHeight();
		}
		else{
			y = (dy + y)% GamePanel.HEIGHT ;

		}
	}
	public void draw(Graphics2D g)
	{
		g.drawImage(image, (int)x, (int) y, null);
		if(image.getWidth() > GamePanel.WIDTH){
			if(x  < 0 )
			{
				g.drawImage(image, (int)x + image.getWidth(), (int) y, null);

			}
			else if(x > 0)
			{
				g.drawImage(image, (int)x - image.getWidth(), (int) y, null);

			}
		}
		if(image.getWidth() <= GamePanel.WIDTH){
			if(x  < 0 )
			{
				g.drawImage(image, (int)x + GamePanel.WIDTH, (int) y, null);

			}
			else if(x > 0)
			{
				g.drawImage(image, (int)x - GamePanel.WIDTH, (int) y, null);

			}
		}
	}
}
