package ecosystem;

import java.util.ArrayList;
import ca.GridConstants.State;
import processing.core.PApplet;
import processing.core.PVector;

public class EaglePredator extends Animal {

	public EaglePredator(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.EAGLE_DEATH_RATE;
		minBirthRate = WorldConstants.EAGLE_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.EAGLE_BIRTH_RATE[1];
		birthFactor = WorldConstants.EAGLE_BIRTH_FACTOR;
		energy = WorldConstants.INI_EAGLE_ENERGY;
		energyToReproduce = WorldConstants.EAGLE_ENERGY_TO_REPRODUCE;
		type = "EaglePredator";
	}

	protected EaglePredator(PApplet p, Animal a) {
		super(p, a);
	}
	
	public void applyBehavior(Terrain terrain, PVector target) {
		Patch patch = (Patch) terrain.getCell((int) getPos().x, (int) getPos().y);
		if (patch.getState() == State.TREE.ordinal() && energy > 50) {
			applyRest(getPos());
		} else {
			if (energy < 50) {
				applyPursuit(target);
			} else {
				applyBehaviour(terrain);
			}
		}
	}
	
	@Override
	protected Animal reproduce(float dt, boolean stochastic) {
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new EaglePredator(p, this);
		}
		return child;
	}

	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
		Patch patch = (Patch) terrain.getCell((int) pos.x, (int) pos.y);
		ArrayList<Animal> patchAnimals = patch.getAnimals();
		for (int i = 0; i < patchAnimals.size(); i++) {
			Animal a = patchAnimals.get(i);
			if (a.type == "Prey" || a.type == "FlockPrey" || a.type == "CleverPrey" || a.type == "Predator") {
				patchAnimals.remove(a);
				allAnimals.remove(a);
				if (energy <= 100)
					energy += WorldConstants.ENERGY_FROM_PREY;
				else
					energy = 100;
				break;
			}
		}
	}

}
