package ca;

import processing.core.PApplet;
import processing.core.PImage;
import tools.CustomRandomGenerator;

public class CellularAutomata {
	PImage grass, dirt, thorns, tree, trap, dead, wall;
	protected int nrows, ncols;
	protected int w, h;
	protected Cell[][] cells;
	protected int radius;
	protected boolean moore;
	protected int numberOfStates;
	protected int[] colors;
	protected PApplet p;

	public CellularAutomata(PApplet p, int nrows, int ncols, int radius, boolean moore, int numberOfStates) {
		this.p = p;
		this.nrows = nrows;
		this.ncols = ncols;
		this.radius = radius;
		this.moore = moore;
		this.numberOfStates = numberOfStates;
		w = p.width / ncols;
		h = p.height / nrows;
		cells = new Cell[nrows][ncols];
		createGrid();
		colors = new int[numberOfStates];
		setRandomStateColors();
		grass = p.loadImage("grass.png");
		grass.resize(w, h);
		dirt = p.loadImage("dirt.jpg");
		dirt.resize(w, h);
		thorns = p.loadImage("thorns.png");
		thorns.resize(w, h);
		tree = p.loadImage("tree.png");
		tree.resize(w, h);
		trap = p.loadImage("trap.png");
		trap.resize(w, h);
		dead = p.loadImage("animaldead.png");
		dead.resize(w, h);
		wall = p.loadImage("wall.png");
		wall.resize(w, h);
	}

	protected void createGrid() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j] = new Cell(p, this, i, j);
			}
		}
		if (moore)
			setNeighbors();
		else
			setNeighbors4();
	}

	public Cell getCellGrid(int row, int col) {
		return cells[row][col];
	}

	public void setNeighbors() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell[] neigh = new Cell[(int) Math.pow(2 * radius + 1, 2)];
				int n = 0;
				for (int ii = -radius; ii <= radius; ii++) {
					for (int jj = -radius; jj <= radius; jj++) {
						int row = (i + ii + nrows) % nrows;
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbors(neigh);
			}
		}
	}

	public void setNeighbors4() {
		int numberOfNeighbors = 2 * (radius * radius + radius) + 1;
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				int n = 0;
				Cell[] neigh = new Cell[numberOfNeighbors];
				for (int ii = -radius; ii <= radius; ii++) {
					for (int jj = -radius + Math.abs(ii); jj <= radius - Math.abs(ii); jj++) {
						int row = (i + ii + nrows) % nrows;
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbors(neigh);
			}
		}
	}

	public void reset() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].setState(0);
			}
		}
	}

	public void setRandomStateColors() {
		for (int i = 0; i < numberOfStates; i++) {
			colors[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}

	public void setStateColors(int[] colors) {
		this.colors = colors;
	}

	public int[] getStateColors() {
		return colors;
	}

	public int getCellWidth() {
		return w;
	}

	public int getCellHeight() {
		return h;
	}

	public int getNumberOfStates() {
		return numberOfStates;
	}

	public Cell getCell(int x, int y) {
		int row = y / h;
		int col = x / w;
		if (row >= nrows)
			row = nrows - 1;
		if (col >= ncols)
			col = ncols - 1;

		return cells[row][col];
	}

	public void setRandomStates() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].setState((int) p.random(numberOfStates));
			}
		}
	}

	public void setRandomStatesCustom(double[] pmf) {
		CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].setState(crg.getRandomClass());
			}
		}
	}

	public void display() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell c = cells[i][j];
				if (c.getState() == 3)
					c.display(grass);
				else if (c.getState() == 2)
					c.displayDirt(dirt);
				else if (c.getState() == 1)
					c.displayThorns(thorns);
				else if (c.getState() == 4)
					c.displayTree(tree);
				else if (c.getState() == 5)
					c.displayTrap(trap);
				else if (c.getState() == 6)
					c.displayDead(dead);
				else if (c.getState() == 7)
					c.displayWall(wall);
			}
		}
	}
}