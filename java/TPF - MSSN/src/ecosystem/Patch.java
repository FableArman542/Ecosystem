package ecosystem;

import java.util.ArrayList;

import ca.GridConstants.State;
import ca.MajorityCell;
import processing.core.PApplet;

public class Patch extends MajorityCell {
	private long eatenTime;
	private int timeToGrow;
	private ArrayList<Animal> animals;

	public Patch(PApplet p, Terrain terrain, int row, int col, int timeToGrow) {
		super(p, terrain, row, col);
		this.timeToGrow = timeToGrow;
		eatenTime = System.currentTimeMillis();
		animals = new ArrayList<Animal>();
	}

	public void setFertile() {
		state = State.FERTILE.ordinal();
		eatenTime = System.currentTimeMillis();
	}

	public void regenerate() {
		if (state == State.FERTILE.ordinal() && System.currentTimeMillis() > (eatenTime + timeToGrow))
			state = State.FOOD.ordinal();
	}

	public ArrayList<Animal> getAnimals() {
		return animals;
	}

	public void clearAnimalsList() {
		animals.clear();
	}

	public void addAnimal(Animal a) {
		animals.add(a);
	}

	public void delAnimal(Animal a) {
		animals.remove(a);
	}
}
