package ca;

public class GridConstants {
	public final static int NROWS = 20;
	public final static int NCOLS = 30;
	public static enum State {EMPTY, OBSTACLE, FERTILE, FOOD, TREE, TRAP, DEAD, WALL}
	public final static int NSTATES = State.values().length;
}