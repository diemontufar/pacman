package com.au.unimelb.comp90020.framework;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

/**
 * Extension of Rectangle class in order to control which side(s) of the object
 * was(were) hit.
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 *
 */
public class GenericRectangle extends Rectangle {

	private static final long serialVersionUID = 1954831445984396570L;

	/**
	 * Possible sides
	 */
	public enum RectangleSide {
		Right, Top, Left, Bottom
	}

	/**
	 * Class constructor
	 * @param x X position
	 * @param y Y position
	 * @param width Width of the rectangle
	 * @param height Height of the rectangle
	 */
	public GenericRectangle(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * Detects which side is overlapping
	 * @param The rectangle to compare
	 * @return A list of sides where the two rectangles overlap
	 */
	public List<RectangleSide> whichSidesOverlapMe(Rectangle r) {

		List<RectangleSide> sides = new ArrayList<RectangleSide>();

		if (x < r.x && x + width > r.x && r.x + r.width > x + width)
			sides.add(RectangleSide.Right);
		if (y < r.y && y + height > r.y && r.y + r.height > y + height)
			sides.add(RectangleSide.Top);
		if (r.x < x && r.x + r.width > x && x + width > r.x + r.width)
			sides.add(RectangleSide.Left);
		if (r.y < y && r.y + r.height > y && y + height > r.y + r.height)
			sides.add(RectangleSide.Bottom);

		return sides;
	}

}
