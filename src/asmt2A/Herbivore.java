package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Herbivore extends LifeForm implements CarnivoreEdible, OmnivoreEdible {

	/**
	 * facilitates versioning of serialized data
	 */
	private static final long serialVersionUID = 1861777997828847708L;

	/**
	 * standard constructor for Herbivore
	 * 
	 * @param world is the world referred to
	 * @param point refers to its position on the grid
	 * @param lastFeed sets starvation incrementor to zero
	 * @param minNullNeighbours = set this lifeform types minimum nulls to breed
	 * @param minMateNeighbours = set this lifeform types minimum mates to breed
	 * @param minFoodNeighbours = set this lifeform types minimum food to breed
	 */
	Herbivore(World world, Point position) {
		super(world, position, Colour.YELLOW);
		lastFeed = 0;
		minMateNeighbours = 1;
		minNullNeighbours = 2;
		minFoodNeighbours = 2;
		maxUnfed = 5;
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	public boolean isEdible(Point point) {
		return (world.cell[point.x][point.y].life instanceof HerbivoreEdible);
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	protected boolean isMyType(Point point) {
		return (world.cell[point.x][point.y].life instanceof Herbivore);
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	protected void giveBirth(Point newSpawnPoint) {
		world.cell[newSpawnPoint.x][newSpawnPoint.y].life = new Herbivore(world, newSpawnPoint);
	}
}
