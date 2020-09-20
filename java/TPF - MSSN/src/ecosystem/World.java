package ecosystem;

import java.util.ArrayList;

import ca.Cell;
import ca.GridConstants.State;
import processing.core.PApplet;

public class World {
	private Terrain terrain;
	private Population population;

	public World(PApplet p) {
		terrain = new Terrain(p);
		terrain.setStateColors(WorldConstants.TERRAIN_COLORS);

		double[] pmf = { 0., 0.1, 0.4, 0.3, 0.2 };
		terrain.setRandomStatesCustom(pmf);
		for (int i = 0; i < 1; i++)
			terrain.majorityRule();
		terrain.setObstacles();

		population = new Population(p);
	}


	public void setStateMouse(int x, int y) {
		Cell c = terrain.getCell(x, y);
		if(c.getState() == State.FERTILE.ordinal() || c.getState() == State.FOOD.ordinal())
			c.setState(State.TRAP.ordinal());
		else if(c.getState() == State.TRAP.ordinal())
			c.setState(State.FERTILE.ordinal());
	}
	
	public void setStateRMouse (int x, int y) {
		Cell c = terrain.getCell(x, y);
		if (!(c.getState() == State.WALL.ordinal())) {
			if (c.getState() == State.TREE.ordinal()) {
				ArrayList<Cell> t = terrain.getTrees();
				t.remove(c);
				terrain.setTrees(t);
				System.out.println("Here");
			}
			c.setState(State.WALL.ordinal());
		}
		else
			c.setState(State.FERTILE.ordinal());
	} 
	
	public void addAnimals (int n, int mouseX, int mouseY) {
		population.addAnimals(n, mouseX, mouseY);
	}

	public void update(float dt) {
		terrain.regenerate();
		population.update(dt, terrain);
//		 System.out.println("Basic | Clever | Flock | Predators = " +
//		 population.getNumberOfPreys() + " , "
//		 + population.getNumberOfCleverPreys() + " , " +
//		 population.getNumberOfFlockPreys() + " , "
//		 + population.getNumberOfPredators());
	}

	public void display() {
		terrain.display();
		population.display();
	}
}
