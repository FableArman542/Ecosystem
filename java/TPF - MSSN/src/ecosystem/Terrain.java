package ecosystem;

import java.util.ArrayList;

import ca.GridConstants;
import ca.Minefield;
import processing.core.PApplet;
import processing.core.PVector;

public class Terrain extends Minefield {
	public Terrain(PApplet p) {
		super(p, GridConstants.NROWS, GridConstants.NCOLS, 1, true, GridConstants.NSTATES);
	}

	@Override
	protected void createGrid() {
		int minRT = (int) (WorldConstants.REGENERATION_TIME[0] * 1000);
		int maxRT = (int) (WorldConstants.REGENERATION_TIME[1] * 1000);
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				int timeToGrow = (int) (minRT + (maxRT - minRT) * Math.random());
				cells[i][j] = new Patch (p, this, i, j, timeToGrow);
			}
		}
		setNeighbors();
	}

	public void regenerate() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				((Patch) cells[i][j]).regenerate();
			}
		}
	}

	public void setAnimalLists(ArrayList<Animal> animals) {
		for (Animal a : animals) {
			PVector pos = a.getPos();
			Patch patch = (Patch) getCell((int) pos.x, (int) pos.y);
			patch.addAnimal(a);
		}
	}

	void clearAnimalLists() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				((Patch) cells[i][j]).clearAnimalsList();
			}
		}
	}
}
