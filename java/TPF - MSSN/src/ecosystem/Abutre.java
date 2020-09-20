package ecosystem;

import java.util.ArrayList;

import ca.Cell;
import processing.core.PApplet;
import processing.core.PVector;

public class Abutre extends Animal{

	public Abutre(PApplet p, PVector pos, int color) {
		super(p, pos, color);
		deathRate = WorldConstants.ABUTRE_DEATH_RATE;
		minBirthRate = WorldConstants.ABUTRE_BIRTH_RATE[0];
		maxBirthRate = WorldConstants.ABUTRE_BIRTH_RATE[1];
		birthFactor = WorldConstants.ABUTRE_BIRTH_FACTOR;
		energy = WorldConstants.INI_ABUTRE_ENERGY;
		energyToReproduce = WorldConstants.ABUTRE_ENERGY_TO_REPRODUCE;
		type = "Abutre";
	}

	public Abutre(PApplet p, Abutre Abutre) {
		super(p, Abutre);
	}
	
	public void applyBehaviour(Terrain terrain) {
			ArrayList<Cell> trees = terrain.getTrees();
			Cell c;
			if((c = terrain.getDead()) != null) {
				applyPursuit(new PVector(c.getPos().x+terrain.getCellWidth()/2,c.getPos().y+terrain.getCellHeight()));
			} else {
				PVector v = (trees.get((int) p.random(trees.size())).getPos());
				applyPursuit(new PVector(v.x+terrain.getCellWidth()/2,v.y-terrain.getCellHeight()/2));
		}
	}
	
	@Override
	public Animal reproduce(float dt, boolean stochastic) {
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Abutre(p, this);
		}
		return child;
	}

	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
		Patch patch = (Patch) terrain.getCell((int) pos.x, (int) pos.y);
		ArrayList<Animal> patchAnimals = patch.getAnimals();
		for (int i = 0; i < patchAnimals.size(); i++) {
			Animal a = patchAnimals.get(i);
			if (a.type == "DeadAnimal") {
				patchAnimals.remove(a);
				allAnimals.remove(a);
				if (energy <= 100)
					energy += WorldConstants.ENERGY_FORM_DEATH;
				else
					energy = 100;
				break;
			}
		}
	}

}
