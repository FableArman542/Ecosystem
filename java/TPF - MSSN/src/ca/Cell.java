package ca;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Cell {

	PApplet p;
	protected CellularAutomata ca;
	protected int row, col;
	protected int state;
	protected Cell[] neighbors;
	protected int w, h;

	public Cell(PApplet p, CellularAutomata ca, int row, int col) {
		this.ca = ca;
		this.row = row;
		this.col = col;
		this.p = p;
		state = 0;
		neighbors = null;
		w = ca.getCellWidth();
		h = ca.getCellHeight();
	}

	public PVector getPos() {
		return new PVector(col * w, row * h);
	}

	public void setNeighbors(Cell[] neighbors) {
		this.neighbors = neighbors;
	}

	public Cell[] getNeighbors() {
		return neighbors;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public PVector getCenter() {
		float x = (col + 0.5f) * w;
		float y = (row + 0.5f) * h;
		return new PVector(x, y);
	}

	public void display(PImage grass) {
		p.image(grass, col * w, row * h);
	}

	public void displayDirt(PImage dirt) {
		p.image(dirt, col * w, row * h);
	}

	public void displayThorns(PImage thorns) {
		p.image(thorns, col * w, row * h);
	}

	public void displayTree(PImage tree) {
		p.image(tree, col * w, row * h);
	}

	public void displayTrap(PImage trap) {
		p.image(trap, col * w, row * h);
	}

	public void displayDead(PImage dead) {
		p.image(dead, col * w, row * h);
	}

	public void displayWall(PImage wall) {
		p.image(wall, col * w, row * h);
		
	}
}