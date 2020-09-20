package ecosystem;

import ca.GridConstants;
import processing.core.PApplet;

public class WorldConstants {
	// Terrain
	public final static float[] REGENERATION_TIME = { 5.f, 15.f }; // seconds
	public final static float OBSTACLE_PENALTY_FACTOR = 25f;

	public static int[] TERRAIN_COLORS;

	// Global Population (all rates are per second)
	public final static float IMMIGRATION_FLOW = 0.f;
	public final static float EMIGRATION_FLOW = 0.f;

	// Prey Population (all rates are per second)
	public final static int INI_PREY_POPULATION = 20;
	public final static float INI_PREY_ENERGY = 50f;
	public final static float ENERGY_FROM_PLANT = 10f;
	public static int PREY_COLOR;
	public static int CLEVER_PREY_COLOR;
	public static int FLOCK_PREY_COLOR;
	// Deterministic version
	public final static float PREY_ENERGY_TO_REPRODUCE = 100f;
	// Stochastic version
	public final static float[] PREY_BIRTH_RATE = { 0.02f, 0.5f };
	public final static float PREY_BIRTH_FACTOR = 0.03f;
	public final static float PREY_DEATH_RATE = 0.15f;

	// Predator Population (all rates are per second)
	public final static int INI_PREDATOR_POPULATION = 5;
	public final static float INI_PREDATOR_ENERGY = 100f;
	public final static float ENERGY_FROM_PREY = 10f;
	public static int PREDATOR_COLOR;
	// Deterministic version
	public final static float PREDATOR_ENERGY_TO_REPRODUCE = 100f;
	// Stochastic version
	public final static float[] PREDATOR_BIRTH_RATE = { 0.02f, 0.5f };
	public final static float PREDATOR_BIRTH_FACTOR = 0.03f;
	public final static float PREDATOR_DEATH_RATE = 0.15f;
	
	//Eagle
	public final static float EAGLE_DEATH_RATE = 0.3f;
	public final static float TREE_DEATH_RATE = 0.1f;
	public final static float INI_EAGLE_ENERGY = 100f;
	//Eagle Stochastic
	public static final float EAGLE_BIRTH_FACTOR = 0.03f;
	public static final float[] EAGLE_BIRTH_RATE = { 0.02f, 0.5f };
	public static final float EAGLE_ENERGY_TO_REPRODUCE = 100f;
	//Abutre
	public final static float ABUTRE_DEATH_RATE = 0.1f;
	public final static float ENERGY_FORM_DEATH = 30f;
	public final static float ABUTRE_ENERGY_TO_REPRODUCE = 100f;
	public final static float INI_ABUTRE_ENERGY = 100f;
	//Abutre Stochastic
	public final static float[] ABUTRE_BIRTH_RATE = { 0.02f, 0.5f };
	public static final float ABUTRE_BIRTH_FACTOR = 0.03f;
	// Simulation mode
	public final static boolean STOCHASTIC = false;

	

	public WorldConstants(PApplet p) {
		PREY_COLOR = p.color(0, 0, 255);
		CLEVER_PREY_COLOR = p.color(255);
		FLOCK_PREY_COLOR = p.color(255, 255, 0);
		PREDATOR_COLOR = p.color(255, 0, 0);

		TERRAIN_COLORS = new int[GridConstants.NSTATES];
		TERRAIN_COLORS[0] = p.color(180, 180, 180);
		TERRAIN_COLORS[1] = p.color(200, 20, 80);
		TERRAIN_COLORS[2] = p.color(150, 100, 15);
		TERRAIN_COLORS[3] = p.color(60, 160, 60);
	}
}
