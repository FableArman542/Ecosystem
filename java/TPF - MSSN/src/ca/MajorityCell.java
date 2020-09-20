package ca;

import processing.core.PApplet;
import tools.Histogram;

public class MajorityCell extends Cell {
	private Histogram hist;

	public MajorityCell(PApplet p, CellularAutomata ca, int row, int col) {
		super(p, ca, row, col);
	}

	public void computeHistogram() {
		int[] data = new int[neighbors.length];
		for (int i = 0; i < neighbors.length; i++)
			data[i] = neighbors[i].getState();
		hist = new Histogram(data, ca.getNumberOfStates());
	}

	public boolean applyMajorityRule() {
		int mode = hist.getMode(0);
		boolean changed = false;
		if (getState() != mode) {
			setState(mode);
			changed = true;
		}
		return changed;
	}
}
