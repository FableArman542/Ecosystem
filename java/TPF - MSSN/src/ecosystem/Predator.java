package ecosystem;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

public class Predator extends Animal {
	public Predator(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.PREDATOR_DEATH_RATE;
		minBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.PREDATOR_BIRTH_RATE[1];
		birthFactor = WorldConstants.PREDATOR_BIRTH_FACTOR;
		energy = WorldConstants.INI_PREDATOR_ENERGY;
		energyToReproduce = WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE;
		type = "Predator";
	}

	public Predator(PApplet p, Predator predator) {
		super(p, predator);
	}

	public void applyBehavior(Terrain terrain, PVector target) {
		PVector f = avoidTrees(terrain);
		if (energy < 50) {
			applyPursuit(target.add(f));
		} else {
			applyBehaviour(terrain);
			applyForce(f);
		}
	}
	@Override
	public Animal reproduce(float dt, boolean stochastic) {
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Predator(p, this);
		}
		return child;
	}

	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
		Patch patch = (Patch) terrain.getCell((int) pos.x, (int) pos.y);
		ArrayList<Animal> patchAnimals = patch.getAnimals();
		for (int i = 0; i < patchAnimals.size(); i++) {
			Animal a = patchAnimals.get(i);
			if (a.type == "Prey" || a.type == "FlockPrey" || a.type == "CleverPrey") {
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
