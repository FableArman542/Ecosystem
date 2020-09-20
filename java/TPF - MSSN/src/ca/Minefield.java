package ca;

import java.util.ArrayList;

import ca.GridConstants.State;
import processing.core.PApplet;

public class Minefield extends MajorityCA {
	private ArrayList<Cell> obstacles;
	private ArrayList<Cell> trees;

	public Minefield(PApplet p, int nrows, int ncols, int radius, boolean moore, int numberOfStates) {
		super(p, nrows, ncols, radius, moore, numberOfStates);
		obstacles = new ArrayList<Cell>();
		trees = new ArrayList<Cell>();
	}

	public void setObstacles() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell c = cells[i][j];
				if (c.getState() == State.OBSTACLE.ordinal())
					obstacles.add(c);
				if (c.getState() == State.TREE.ordinal())
					trees.add(c);
			}
		}
	}

	public Cell getDead() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell c = cells[i][j];
				if(c.getState() == State.DEAD.ordinal())
					return c;
			}
		}
		return null;
	}

	public ArrayList<Cell> getTrees() {
		return trees;
	}
	
	public void setTrees (ArrayList<Cell> t) {
		this.trees = t;
	}

	public ArrayList<Cell> getObstacles() {
		return obstacles;
	}

	public void addObstacle(Cell c) {
		obstacles.add(c);
		c.setState(State.OBSTACLE.ordinal());
	}
}