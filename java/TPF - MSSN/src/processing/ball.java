package processing;

import processing.core.PApplet;
import processing.core.PConstants;

public class ball implements IProcessingApp {
	
	protected int x, y;
	
	public ball (int mouseX, int mouseY) {
		this.x = (int)mouseX;
		this.y = (int)mouseY;
	}
	
	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(0);
		p.ellipse(p.width/2 + x, p.height/2 + y, 50, 50);		
	}

	@Override
	public void keyPressed(PApplet p) {
		switch (p.keyCode) {
		case PConstants.UP:
			y -= 10;
			break;
		case PConstants.DOWN:
			y += 10;
			break;
		case PConstants.RIGHT:
			x += 10;
			break;
		case PConstants.LEFT:
			x -= 10;
			break;
		}
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}
	
	
}
