package ecosystem;

import aa.FlockOverGrid;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class FlockOfPreys extends FlockOverGrid {
	public FlockOfPreys(PApplet p, int npreys, float hue) {
		
		super(p, npreys, 25, hue, false);
	}
	
	public FlockOfPreys(PApplet p, int npreys, float hue, PVector mos) {
		super(p, npreys, 25, hue, true);
		createFlock1 (p, nboids, mos);
	}
	
	
	public void createFlock1 (PApplet p, int nboids, PVector pS) {
		p.colorMode(PConstants.HSB, 360, 100, 100);
		for (int i = 0; i < nboids; i++) {
			int color = p.color(hue, p.random(50, 100), p.random(70, 100));
			Animal a = new FlockPrey(p, pS, color, this);
			a.setVel(new PVector(p.random(-2, 2), p.random(-2, 2)));
			boids.add(a);
		}
		p.colorMode(PConstants.RGB, 255);
	}
	

	@Override
	public void createFlock(PApplet p, int nboids) {
		p.colorMode(PConstants.HSB, 360, 100, 100);
		for (int i = 0; i < nboids; i++) {
			int color = p.color(hue, p.random(50, 100), p.random(70, 100));
			Animal a = new FlockPrey(p, new PVector(p.random(p.width), p.random(p.height)), color, this);
			a.setVel(new PVector(p.random(-2, 2), p.random(-2, 2)));
			boids.add(a);
		}
		p.colorMode(PConstants.RGB, 255);
	}
}
