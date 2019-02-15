package asmt2A;

import java.awt.Point;
import java.io.Serializable;

public class Plant extends LifeForm implements HerbivoreEdible, OmnivoreEdible, Serializable {

	/**
	 * facilitates versioning of serialized data
	 */
	private static final long serialVersionUID = 6962607688490863396L;
	/**
	 * a particular plant's horizontal and vertical, a Pointor of those variables,
	 * and a moved boolean to tag if a plant has been instantiated this turn. also
	 * including constants for seed function and a colour associated with plants
	 */
	public static final int MINIMUM_NULL = 3;
	public static final int MINIMUM_PLANT = 2;

	/**
	 * standard constructor for Plant
	 * 
	 * @param world is the world referred to
	 * @param point refers to its position on the grid
	 */
	Plant(World world, Point position) {
		super(world, position, Colour.GREEN);
	}

	/**
	 * first implements neighborCheck, then picks a random number based on the
	 * number of nullNeighbors. it then pull a Pointor from the fertile viable array
	 * based on the index position of plantNeighbors. puts a new plant in the chosen
	 * cell, changes that cell's colour, and sets moved to true
	 */
	public void seed() {
		neighbourCheck(position);
		if (nullMoves.size() > 0 && nullNeighbours >= MINIMUM_NULL && myNeighbours >= MINIMUM_PLANT) {
			int randomPositionInt = RandomGenerator.nextNumber(nullMoves.size());
			Point seedPoint = new Point(nullMoves.get(randomPositionInt));
			world.cell[seedPoint.x][seedPoint.y].life = new Plant(world, seedPoint);
			world.cell[seedPoint.x][seedPoint.y].life.setMoved(true);
		}
	}
	
	/**
	 * overriding the LifeForm's live function to do a special kind of live function,
	 * in this case seed
	 */
	public void live() {
		neighbourCheck(position);
		seed();
	}

	/**
	 * locally defined version of parent class abstract method
	 * this function has no application to this empty so blank
	 */
	protected boolean hasFed() {
		return false;
	}

	/**
	 * locally defined version of parent class abstract method
	 * this function has no application to this empty so blank
	 */
	protected boolean isEdible(Point Point) {
		return false;
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	protected boolean isMyType(Point point) {
		return (world.cell[point.x][point.y].life instanceof Plant);
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	protected void giveBirth(Point newSpawnPoint) {
		world.cell[newSpawnPoint.x][newSpawnPoint.y].life = new Plant(world, position);
		
	}
}
