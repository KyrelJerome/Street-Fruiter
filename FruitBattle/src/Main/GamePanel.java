package Main;
import javax.swing.JFrame;
import javax.swing.JPanel;
import dataStructures.ScreenManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	// Main window
	JFrame window;
	//Dimensions
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static  int systemWindowWIDTH;
	public static  int systemWindowHeight;
	public static double SCALEWIDTH = 1;
	public static double SCALEHEIGHT = 1;
	//Game Thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;

	//Fonts
	public static Color dataColor;
	public static Font dataFont ;
	private long frameStartTime = System.currentTimeMillis();
	private int fts = 0;
	private int fps = 0;
	//Image and Drawing
	private BufferedImage image;
	private Graphics2D g;
	//GameModeManager
	private ScreenManager sm;
	public GamePanel(JFrame window){
		super();
		this.window = window;
		setPreferredSize(new Dimension((int)(WIDTH/2), (int)(HEIGHT/2)));
		setFocusable(true);
		requestFocus();
	}
	public void addNotify() {
		super.addNotify();
		if(thread == null)
		{
			thread = new Thread(this);
			
			thread.start();
		}
	}

	private void init()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		//SCALEWIDTH = (width/WIDTH);
		//SCALEHEIGHT = (height/HEIGHT);
		setPreferredSize(new Dimension((int)(WIDTH*SCALEWIDTH), (int)(HEIGHT* SCALEHEIGHT)));
		dataColor = new Color(255,255,255);
		dataFont = new Font("Arial",Font.ITALIC,40);
		//Sets the image for the canvas to be loaded to
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) image.getGraphics();//  makes the graphics object where the graphics are written to
		/*BufferedImage buffer;
		try {
			buffer = ImageIO.read(getClass().getResourceAsStream("/backgrounds/MainBackground.png"));

				g.drawImage(buffer, 0, 0, GamePanel.WIDTH,GamePanel.HEIGHT , null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
		
		//drawToScreen();
		try {
			thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = true;// makes the JFrame begin to run its loops
		sm = new ScreenManager();
		addKeyListener(this);
	}
	public void run()
	{
		long start;
		long elapsed;
		long wait;
		init();
		while(running){
			start = System.nanoTime();
			update();
			draw();
			frameBasedUpdate();	
			drawToScreen();
			elapsed = System.nanoTime() - start; 
			wait = targetTime - elapsed/ 1000000;
			if(wait <0)
			{

			}
			else{
				try{
					Thread.sleep(wait);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private void update(){
		sm.update();
	}
	private void draw(){
		sm.draw(g);
	}
	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(image,0,0,(int)(WIDTH/2), (int)(HEIGHT/2),null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key){		
	}
	public void keyPressed(KeyEvent key){
		
		sm.keyPressed(key.getKeyCode());

	}
	public void keyReleased(KeyEvent key){
		
		sm.keyReleased(key.getKeyCode());
	}
	private void outputFPS(Graphics g) {
		g.setColor(dataColor);
		g.setFont(dataFont);
		g.drawString("Fps: " + fps,100,100);
		g.drawString("Fts: " + fts,100,150);

		System.out.println("FPS: " + fps);
		System.out.println("FTS: " + fts);
	}
	public void frameBasedUpdate()
	{
		fts++ ;
		if(System.currentTimeMillis() > frameStartTime + 1000)
		{
			fps = fts;
			fts = 0;
			frameStartTime = System.currentTimeMillis();
		}
	}
}
