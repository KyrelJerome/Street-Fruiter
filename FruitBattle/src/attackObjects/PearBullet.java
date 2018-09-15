package attackObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataStructures.AttackObject;

public class PearBullet extends AttackObject{

		public PearBullet (int x, int y, int direction)
		{
			this.direction = direction;
			damage = 8;
			vectorx = 20;
			vectory = 30;
			hitstun = 10;
			width = 95;
			height = 45;
			isVisible = true;
			this.x = x;
			this.y = y;
			dx = 45 * direction;
			dy = 0;
			hitbox = new Rectangle(x + 20, y, width - 30, height);
			mainAnimation = new PearBulletAnimation();
			mainAnimation.setSize(100, 50);
 		}
		public void update() {
			// TODO Auto-generated method stub
			x += dx;
			mainAnimation.update();
			hitbox.setLocation(x + 20, y);
			mainAnimation.setCoordinates(x, y);
		}

		public void onHit() {
			// TODO Auto-generated method stub
			shouldExist = false;
		}

		@Override
		public void onCollision() {
			shouldExist = false;
		}

		@Override
		public void interrupt() {
			shouldExist = false;
		}

		@Override
		public void kill() {
			isVisible = false;
			shouldExist = false;
		}

		@Override
		public void draw(Graphics2D g) {
			g.draw(hitbox);
			mainAnimation.draw(g);
		}

	

}
