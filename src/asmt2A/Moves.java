package asmt2A;

import java.awt.Point;

public interface Moves {
	
	/**
	 * an array list of Pointor positions relative any given cell
	 */
	public static Point UpLeft = new Point(-1, 1);
	public static Point Up = new Point(0, 1);
	public static Point UpRight = new Point(1, 1);
	public static Point Left = new Point(-1, 0);
	public static Point Right = new Point(1, 0);
	public static Point DownLeft = new Point(-1, -1);
	public static Point Down = new Point(0, -1);
	public static Point DownRight = new Point(1, -1);

	Point[] moves = new Point[] { UpLeft, Up, UpRight, Left, Right, DownLeft, Down, DownRight };

}
