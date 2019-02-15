package asmt2A;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class LifeForm implements Moves, Serializable {

	/**
	 * facilitates versioning of serialized data
	 */
	private static final long serialVersionUID = 2250474596054143565L;
	/**
	 * declaration of constructor variables
	 */
	protected Point position;
	protected Colour colour;
	protected World world; 

	/**
	 * setting moved initially to false for lifeforms created during a turn
	 */
	protected boolean moved = false;

	/**
	 * variables specific to individual life forms declared here in parent class
	 */
	protected int moveAttempts;
	protected int lastFeed;
	protected int myNeighbours;
	protected int myEdibleCount;
	protected int nullNeighbours;

	/**
	 * variables defined in children constructors for comparison operations
	 */
	public int minMateNeighbours;
	public int minNullNeighbours;
	public int minFoodNeighbours;
	public int maxUnfed;

	/**
	 * an arraylist to check for fertile positions around a particular grid location
	 */
	ArrayList<Point> nullMoves = new ArrayList<Point>();
	ArrayList<Point> eatMoves = new ArrayList<Point>();

	/**
	 * the parent constructor all children use
	 * 
	 * @param world    refers to the world this lifeform belongs to
	 * @param position refers the location on a world grid
	 * @param colour   refers to the colour this particular life form should paint a
	 *                 cell
	 */
	protected LifeForm(World world, Point position, Colour colour) {
		this.world = world;
		this.position = position;
		this.colour = colour;
	}

	/**
	 * an abstract method (therefore forced to be defined by children) to check if a
	 * target cell is edible forced to be defined by children
	 * 
	 * @param point is the location on the grid
	 * @return whether or not it is indeed edible by the given life form
	 */
	protected abstract boolean isEdible(Point point);

	/**
	 * an abstract method (therefore forced to be defined by children) to check if a
	 * target cell is the same type as host. forced to be defined by children
	 * 
	 * @param point is the location on the grid
	 * @return whether or not the target is indeed the same life type
	 */
	protected abstract boolean isMyType(Point point);

	/**
	 * an abstract method (therefore forced to be defined by children) which defines
	 * in the child class the type of life to be born in a target sale
	 * 
	 * @param newSpawnPoint is the location on the grid to create a new life
	 */
	protected abstract void giveBirth(Point newSpawnPoint);

	/**
	 * checks if a lifeform has eaten sufficiently recently
	 * 
	 * @return whether the lastFeed count is less than the maximum allowed
	 */
	protected boolean hasFed() {
		return (lastFeed < maxUnfed);
	}

	/**
	 * when a new animal is created in a turn, this is used to set its moved value
	 * to false so it doesn't move multiple times per turn
	 * 
	 * @param moved set the determinant of whether or not this animal has moved this
	 *              turn
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	/**
	 * gets the variable which indicates whether or not a lifeform has moved or been
	 * born this turn
	 * 
	 * @return the moved variable
	 */
	public boolean getMoved() {
		return moved;
	}

	/**
	 * used when colourizing the grid to check what colour a life form has
	 * associated with it
	 * 
	 * @return the colour a life form should be painting its cell
	 */
	public Colour getColour() {
		return colour;
	}

	/**
	 * method to check if a step the animal tries to move to is within the
	 * boundaries of the world
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isInBounds(Point point) {
		return (point.x < world.worldBounds.x && point.y < world.worldBounds.y) && (point.x >= 0 && point.y >= 0);
	}

	/**
	 * method to check if a cell the animal tries to move to is null
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isNull(Point point) {
		return (world.cell[point.x][point.y].life == null);
	}

	/**
	 * method to check if the contents of a cell moving into are edible
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean canGiveBirth(Point point) {
		return (myNeighbours >= minMateNeighbours && nullNeighbours >= minNullNeighbours
				&& myEdibleCount >= minFoodNeighbours);
	}

	/**
	 * 
	 * @param Point the target Pointor orchestrates a move of an Herbivore to a new
	 *              cell
	 */
	void moveSpace(Point point) {
		world.cell[point.x][point.y].life = world.cell[position.x][position.y].life;
		world.cell[position.x][position.y].life = null;
		position = point;
		moved = true;
	}

	/**
	 * destroys the animal by making it's own cell null and sets the cell's colour
	 * to null's sienna
	 */
	public void die() {
		world.cell[position.x][position.y].life = null;
	}

	/**
	 * kills the lifeform in a target cell and implements a move
	 * 
	 * @param pointFrom animal's original location
	 * @param pointTo   animal's target location
	 */
	public void eat(Point pointFrom, Point pointTo) {
		world.cell[pointTo.x][pointTo.y].life.die();
		moveSpace(pointTo);
	}

	/**
	 * goes through all positions around a passed in cell position and checks what
	 * is around it
	 * 
	 * @param tempPoint the position to check around
	 */
	public void neighbourCheck(Point tempPoint) {
		myNeighbours = 0;
		myEdibleCount = 0;
		nullNeighbours = 0;
		nullMoves.clear();
		eatMoves.clear();
		for (int i = 0; i < moves.length; i++) {
			Point possibleMove = new Point(position.x + moves[i].x, position.y + moves[i].y);
			possibleMoveAdder(possibleMove);
		}
	}

	/**
	 * adds a position to a list of viable move positions if it satisfies
	 * requirements
	 * 
	 * @param point the point to add if it meets the checks
	 */
	public void possibleMoveAdder(Point point) {
		if (isInBounds(point)) {
			if (isEdible(point)) {
				myEdibleCount++;
				eatMoves.add(point);
			}
			if (isMyType(point))
				myNeighbours++;
			else if (world.cell[point.x][point.y].life == null) {
				nullNeighbours++;
				nullMoves.add(point);
			}
		}
	}

	/**
	 * implements a move, by either moving to a null space or eating the lifeform at
	 * a target space and moving there
	 * 
	 * @param point the point to move/eat to
	 */
	public void move(Point point) {
		moveSpace(point);
		lastFeed++;

	}

	public void eat(Point point) {
		eat(position, point);
		lastFeed = 0;
		moved = true;
	}

	/**
	 * the general live function for life forms, implements a move and possibly a
	 * spawn
	 */
	public void live() {
		if (!hasFed()) {
			die();
		} else {
			neighbourCheck(position);
			moveAttempts = 0;
		}
		if (eatMoves.size() != 0) {
			int randomPositionInt = RandomGenerator.nextNumber(eatMoves.size());
			Point moveTempPoint = eatMoves.get(randomPositionInt);
			eat(moveTempPoint);
			eatMoves.remove(randomPositionInt);
			spawn();
		} else if (nullMoves.size() != 0) {
			int randomPositionInt = RandomGenerator.nextNumber(nullMoves.size());
			Point moveTempPoint = nullMoves.get(randomPositionInt);
			move(moveTempPoint);
			nullMoves.remove(randomPositionInt);
			spawn();
		} else
			lastFeed++;
			return;
	}

	/**
	 * a spawn function which does checks and if those checks are met gives birth in
	 * a target cell
	 */
	public void spawn() {
		while (nullMoves.size() > 0) {
			int randomPositionInt = RandomGenerator.nextNumber(nullMoves.size());
			Point moveTempPoint = nullMoves.get(randomPositionInt);
			if ((!isNull(moveTempPoint)) || (!canGiveBirth(moveTempPoint)))
				nullMoves.remove(randomPositionInt);
			else {
				giveBirth(moveTempPoint);
				world.cell[moveTempPoint.x][moveTempPoint.y].life.setMoved(true);
				break;
			}
		}
	}
}
