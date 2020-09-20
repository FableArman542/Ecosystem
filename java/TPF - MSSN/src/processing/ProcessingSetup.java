package processing;

import ecosystem.EcoSystemApp;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {

	private static IProcessingApp processingApp;
	private float lastUpdateTime;

	@Override
	public void settings() {
		size(1020, 720);
	}

	@Override
	public void setup() {
		processingApp.setup(this);
		lastUpdateTime = 0;
	}

	@Override
	public void draw() {
		float now = millis();
		float dt = (float) ((now - lastUpdateTime) / 1000.);
		processingApp.draw(this, dt);
		lastUpdateTime = now;
	}

	@Override
	public void mousePressed() {
		processingApp.mousePressed(this);
	}

	@Override
	public void keyPressed() {
		processingApp.keyPressed(this);
	}

	public static void main(String[] args) {
		/// -------- PHYSICS
		// processingApp = new SolarSystemApp();
		// processingApp = new ParticleTestApp();
		// processingApp = new ParticleSystemApp();
		// processingApp = new ExplosivePlanetsApp();
		// processingApp = new FallingBodyApp();

		/// -------- AUTONOMOUS AGENTS
		// processingApp = new FlockingApp();
		// processingApp = new FlockingObstaclesApp();
		// processingApp = new BoidObstaclesApp();
		// processingApp = new FlockObstaclesApp();
		// processingApp = new ExplosiveBoidApp();

		/// -------- CELLULAR AUTOMATA
		// processingApp = new TestMajorityCA();
		// processingApp = new TestGOL();
		// processingApp = new TestCA();
		// processingApp = new EditMinefieldApp();

		/// -------- FRACTALS
		// processingApp = new LSystemApp();
		// processingApp = new FractalSetsApp();

		/// -------- ECOSYSTEM
		//processingApp = new EcoSystemApp();
		
		processingApp = new idk ();
		
		
		PApplet.main(ProcessingSetup.class);
	}
}