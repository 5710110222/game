

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip2 extends Sprite{

	int step = 16;
	
	public SpaceShip2(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.PINK);
		g.fillRect(x, y, width, height);
		
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 400)
			x = 400;
		if(x > 800 - width)
			x = 800 - width;
	}

}
