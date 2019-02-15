package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Omnivore extends LifeForm implements CarnivoreEdible {

	/**
	 * facilitates versioning of serialized data
	 */
	private static final long serialVersionUID = 8993032073101314255L;

	/**
	 * standard constructor for Omnivore
	 * 
	 * @param world is the world referred to
	 * @param point refers to its position on the grid
	 * @param lastFeed sets starvation incrementor to zero
	 * @param minNullNeighbours = set this lifeform types minimum nulls to breed
	 * @param minMateNeighbours = set this lifeform types minimum mates to breed
	 * @param minFoodNeighbours = set this lifeform types minimum food to breed
	 */
	Omnivore(World world, Point position) {
		super(world, position, Colour.BLUE);
		lastFeed = 0;
		minMateNeighbours = 1;
		minNullNeighbours = 3;
		minFoodNeighbours = 1;
		maxUnfed = 5;
	}
	
	/**
	 * locally defined version of parent class abstract method
	 */
	public boolean isEdible(Point point) {
		return (world.cell[point.x][point.y].life instanceof OmnivoreEdible);
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	protected boolean isMyType(Point point) {
		return (world.cell[point.x][point.y].life instanceof Omnivore);
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	protected void giveBirth(Point newSpawnPoint) {
		world.cell[newSpawnPoint.x][newSpawnPoint.y].life = new Omnivore(world, newSpawnPoint);
		
	}
}

