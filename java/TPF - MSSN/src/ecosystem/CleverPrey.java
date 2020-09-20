package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;

public class CleverPrey extends Prey {
	public CleverPrey(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		type = "CleverPrey";
	}

	public CleverPrey(PApplet p, CleverPrey cleverPrey) {
		super(p, cleverPrey);
	}

	@Override
	public void applyBehaviour(Terrain t) {
		super.applyBehaviour(t);
		PVector f = avoidObstacle(t);
		PVector c = avoidTrees(t);
		applyForce(f.add(c));
	}

	@Override
	public Animal reproduce(float dt, boolean stochastic) {
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new CleverPrey(p, this);
		}
		return child;
	}
}
