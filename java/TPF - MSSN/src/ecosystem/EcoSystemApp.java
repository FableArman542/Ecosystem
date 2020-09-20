package ecosystem;

import processing.IProcessingApp;
import processing.core.PApplet;
import processing.core.PConstants;

public class EcoSystemApp implements IProcessingApp {
	private World world;
	@Override
	public void setup(PApplet p) {
		new WorldConstants(p);
		world = new World(p);
	}

	@Override
	public void draw(PApplet p, float dt) {
		world.update(dt);
		world.display();
	}

	@Override
	public void keyPressed(PApplet p) {
		world.addAnimals((int)p.keyCode, p.mouseX, p.mouseY);
	}

	@Override
	public void mousePressed(PApplet p) {
		if(p.mousePressed && (p.mouseButton == PConstants.LEFT)) {
			world.setStateMouse(p.mouseX, p.mouseY);
		}else if(p.mousePressed && (p.mouseButton == PConstants.RIGHT)) {
			world.setStateRMouse(p.mouseX, p.mouseY);
		}
	}
}
