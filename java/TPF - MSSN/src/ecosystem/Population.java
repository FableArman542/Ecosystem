package ecosystem;

import java.util.ArrayList;

import aa.Boid;
import aa.Flock;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Population {

	PImage sheep, horse, wolf, rabbit, eagle, DeadAnimal, abutre;
	private ArrayList<Animal> allAnimals;
	private Flock flock;
	private boolean stochastic = WorldConstants.STOCHASTIC;
	private PApplet p;

	private int numberOfPreys;
	private int numberOfCleverPreys;
	private int numberOfFlockPreys;
	private int numberOfPredators;
	private int numberOfEaglePredators;

	public Population(PApplet p) {
		this.p = p;
		allAnimals = new ArrayList<Animal>();

		flock = new FlockOfPreys(p, WorldConstants.INI_PREY_POPULATION / 3,
				p.hue(WorldConstants.FLOCK_PREY_COLOR) / 255 * 360);
		for (int i = 0; i < WorldConstants.INI_PREY_POPULATION / 3; i++) {
			Animal a = (Animal) flock.getBoid(i);
			allAnimals.add(a);
		}

		for (int i = 0; i < WorldConstants.INI_PREY_POPULATION / 3; i++) {
			PVector pos = new PVector(p.random(p.width), p.random(p.height));
			Animal a = new Prey(p, pos, WorldConstants.PREY_COLOR);
			allAnimals.add(a);
		}

		for (int i = 0; i < WorldConstants.INI_PREY_POPULATION / 3; i++) {
			PVector pos = new PVector(p.random(p.width), p.random(p.height));
			Animal a = new CleverPrey(p, pos, WorldConstants.CLEVER_PREY_COLOR);
			allAnimals.add(a);
		}

		for (int i = 0; i < WorldConstants.INI_PREDATOR_POPULATION; i++) {
			PVector pos = new PVector(p.random(p.width), p.random(p.height));
			Animal a = new Predator(p, pos, WorldConstants.PREDATOR_COLOR);
			allAnimals.add(a);
		}

		for (int i = 0; i < WorldConstants.INI_PREDATOR_POPULATION; i++) {
			PVector pos = new PVector(p.random(p.width), p.random(p.height));
			Animal a = new EaglePredator(p, pos, WorldConstants.PREDATOR_COLOR);
			allAnimals.add(a);
		}
		for (int i = 0; i < WorldConstants.INI_PREDATOR_POPULATION; i++) {
			PVector pos = new PVector(p.random(p.width), p.random(p.height));
			Animal a = new Abutre(p, pos, WorldConstants.PREDATOR_COLOR);
			allAnimals.add(a);
		}

		sheep = p.loadImage("sheep.png");
		wolf = p.loadImage("wolf.png");
		rabbit = p.loadImage("rabbit.png");
		horse = p.loadImage("horse.png");
		eagle = p.loadImage("eagle.png");
		abutre = p.loadImage("abutre.png");
		DeadAnimal = p.loadImage("dead.png");
	}

	public void update(float dt, Terrain terrain) {
		immigration(dt);
		emigration(dt);

		reproduce(dt, stochastic);

		applyBehaviour(terrain);

		move(dt);
		obstaclesPenalty(terrain, dt);
		Tree(terrain, dt);
		EatDeadAnimal(terrain, dt);
		stepTrapandWall(terrain, dt);
		die(dt, stochastic);

		terrain.setAnimalLists(allAnimals);
		eat(terrain);
		terrain.clearAnimalLists();
	}

	private void applyBehaviour(Terrain terrain) {
		for (Animal a : allAnimals) {
			switch (a.getType()) {
			case "Prey":
				a.applyBehaviour(terrain);
				break;
			case "CleverPrey":
				((CleverPrey) a).applyBehaviour(terrain);
				break;
			case "FlockPrey":
				((FlockPrey) a).applyBehaviour(terrain);
				break;
			case "Predator":
				((Predator) a).applyBehavior(terrain, getNearest(a, false));
				break;
			case "EaglePredator":
				((EaglePredator) a).applyBehavior(terrain, getNearest(a, true));
				break;
			case "Abutre":
				((Abutre) a).applyBehaviour(terrain);
				break;
			default:
				System.out.println("ERRO!");
				break;
			}
		}
	}

	private PVector getNearest(Boid self, boolean t) {
		PVector target = new PVector(0, 0);
		for (Animal a : allAnimals) {
			if (t) {
				if (self.inSight(a.getPos(), 200) && (a.getType() == "CleverPrey" || a.getType() == "FlockPrey"
						|| a.getType() == "Prey" || a.getType() == "Predator")) {
					target = a.getPos();
					break;
				}
			} else {
				if (self.inSight(a.getPos(), 100)
						&& (a.getType() == "CleverPrey" || a.getType() == "FlockPrey" || a.getType() == "Prey")) {
					target = a.getPos();
					break;
				}
			}
		}
		return target;
	}

	private void move(float dt) {
		for (Animal a : allAnimals)
			a.move(dt);
	}

	private void EatDeadAnimal(Terrain terrain, float dt) {
		for (Animal a : allAnimals) {
			if (a.getType() == "Abutre") {
				a.eatDeadAnimal(terrain, dt);
			}
		}
	}

	private void Tree(Terrain terrain, float dt) {
		for (Animal a : allAnimals)
			if (a.getType().equals("EaglePredator") || a.getType().equals("Abutre"))
				a.Tree(terrain, dt);
	}

	private void stepTrapandWall(Terrain terrain, float dt) {
		for (Animal a : allAnimals) {
			a.stepTrap(terrain, dt);
			a.stepWall(terrain, dt);
		}
	}

	private void obstaclesPenalty(Terrain terrain, float dt) {
		for (Animal a : allAnimals)
			if (!a.getType().equals("EaglePredator") && !a.getType().equals("Abutre"))
				a.obstaclesPenalty(terrain, dt);
	}

	private void eat(Terrain terrain) {
		for (int i = allAnimals.size() - 1; i >= 0; i--) {
			Animal a = allAnimals.get(i);
			a.eat(terrain, allAnimals);
		}
	}

	private void die(float dt, boolean stochastic) {
		for (int i = allAnimals.size() - 1; i >= 0; i--) {
			Animal a = allAnimals.get(i);
			if (a.die(dt, stochastic)) {
				allAnimals.remove(a);
			}
		}
	}

	private void reproduce(float dt, boolean stochastic) {
		for (int i = allAnimals.size() - 1; i >= 0; i--) {
			Animal a = allAnimals.get(i);
			Animal child = a.reproduce(dt, stochastic);
			if (child != null)
				allAnimals.add(child);
		}
	}

	private void emigration(float dt) {
		int listSize = allAnimals.size();
		if (listSize == 0)
			return;

		int n = (int) (WorldConstants.EMIGRATION_FLOW * dt);
		float f = WorldConstants.EMIGRATION_FLOW * dt - n;

		int nn = Math.min(n, listSize);
		for (int i = 0; i < nn; i++) {
			int rnd = (int) p.random(listSize--);
			allAnimals.remove(rnd);
		}

		if ((listSize > 0) && (p.random(1) < f)) {
			int rnd = (int) p.random(listSize);
			allAnimals.remove(rnd);
		}
	}

	private void immigration(float dt) {
		int n = (int) (WorldConstants.IMMIGRATION_FLOW * dt);
		float f = WorldConstants.IMMIGRATION_FLOW * dt - n;

		for (int i = 0; i < n; i++) {
			Animal a = getRandomBreed(0.5f);
			allAnimals.add(a);
		}

		if (p.random(1) < f) {
			Animal a = getRandomBreed(0.5f);
			allAnimals.add(a);
		}
	}

	private Animal getRandomBreed(float prob) {
		Animal a;
		PVector pos;
		if (p.random(1) < prob) {
			pos = new PVector(0, p.random(p.height));
			a = new Prey(p, pos, WorldConstants.PREY_COLOR);
			a.setVel(new PVector(20, 0));
		} else {
			pos = new PVector(p.width - 1, p.random(p.height));
			a = new Predator(p, pos, WorldConstants.PREDATOR_COLOR);
			a.setVel(new PVector(-20, 0));
		}
		return a;
	}
	
	public void addAnimals(int num, int x, int y) {
		if (num == 49) {
			PVector pos = new PVector(x, y);
			Animal a = new EaglePredator(p, pos, WorldConstants.PREDATOR_COLOR);
			System.out.println("Aguia");
			allAnimals.add(a);
		} else if (num == 50) {
			PVector pos = new PVector(x, y);
			Animal a = new Prey(p, pos, WorldConstants.PREDATOR_COLOR);
			System.out.println("Ovelha");
			allAnimals.add(a);
		} else if (num == 51) {
			PVector pos = new PVector(x, y);
			Animal a = new CleverPrey(p, pos, WorldConstants.PREDATOR_COLOR);
			System.out.println("Coelho");
			allAnimals.add(a);
		} else if (num == 52) {
			PVector pos = new PVector(x, y);
			Animal a = new Predator(p, pos, WorldConstants.PREDATOR_COLOR);
			System.out.println("Lobo");
			allAnimals.add(a);
		} else if (num == 53){
			PVector pos = new PVector(x, y);
			Animal a = new Abutre(p, pos, WorldConstants.PREDATOR_COLOR);
			System.out.println("Abutre");
			allAnimals.add(a);
		} else if (num == 54){
			flock = new FlockOfPreys(p, WorldConstants.INI_PREY_POPULATION / 3,
					p.hue(WorldConstants.FLOCK_PREY_COLOR) / 255 * 360,
					new PVector(x, y));
			
			for (int i = 0; i < WorldConstants.INI_PREY_POPULATION / 3; i++) {
				Animal a = (Animal) flock.getBoid(i);
				allAnimals.add(a);
			}
			System.out.println("Cavalo");
		}
	}

	public void countAnimals() {
		numberOfPreys = 0;
		numberOfPredators = 0;
		numberOfFlockPreys = 0;
		numberOfCleverPreys = 0;
		numberOfEaglePredators = 0;
		for (Animal a : allAnimals) {
			switch (a.getType()) {
			case "Prey":
				numberOfPreys++;
				break;
			case "FlockPrey":
				numberOfFlockPreys++;
				break;
			case "CleverPrey":
				numberOfCleverPreys++;
				break;
			case "Predator":
				numberOfPredators++;
				break;
			case "EaglePredator":
				numberOfEaglePredators++;
				break;
			default:
				System.out.println("Error!");
				break;
			}
		}
	}

	public int getNumberOfPreys() {
		return numberOfPreys;
	}

	public int getNumberOfPredators() {
		return numberOfPredators;
	}

	public int getNumberOfCleverPreys() {
		return numberOfCleverPreys;
	}

	public int getNumberOfFlockPreys() {
		return numberOfFlockPreys;
	}

	public int getNumberOfEaglePredator() {
		return numberOfEaglePredators;
	}

	public void display() {
		for (Animal a : allAnimals) {
			String type = a.getType();
			if (type.equals("Prey")) {
				a.displayPrey(a.energy, sheep);
			} else if (type.equals("Predator")) {
				a.displayPredator(a.energy, wolf);
			} else if (type.equals("CleverPrey")) {
				a.displayClever(a.energy, rabbit);
			} else if (type.equals("FlockPrey")) {
				a.displayFlock(a.energy, horse);
			} else if (type.equals("EaglePredator")) {
				a.displayEagle(a.energy, eagle);
			} else if (type.equals("Abutre")) {
				a.displayAbutre(a.energy, abutre);
			}
		}
	}
}
