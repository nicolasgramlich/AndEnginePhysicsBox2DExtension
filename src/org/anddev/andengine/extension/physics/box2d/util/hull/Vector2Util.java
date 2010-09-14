package org.anddev.andengine.extension.physics.box2d.util.hull;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Nicolas Gramlich
 * @since 15:05:33 - 14.09.2010
 */
class Vector2Util {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public static float getCrossProduct(final Vector2 pVertexA, final Vector2 pVertexB) {
		return pVertexA.x * pVertexB.y - pVertexB.x * pVertexA.y;
	}

	public static boolean isLess(final Vector2 pVertexA, final Vector2 pVertexB) {
		final float f = Vector2Util.getCrossProduct(pVertexA, pVertexB);
		return f > 0 || f == 0 && Vector2Util.isLonger(pVertexA, pVertexB);
	}

	public static boolean isLonger(final Vector2 pVertexA, final Vector2 pVertexB) {
		return Vector2Util.getManhattanLength(pVertexA) > Vector2Util.getManhattanLength(pVertexB);
	}

	public static float getManhattanLength(final Vector2 pVertex) {
		return Math.abs(pVertex.x) + Math.abs(pVertex.y);
	}

	public static float getManhattanDistance(final Vector2 pVertexA, final Vector2 pVertexB) {
		return Vector2Util.getManhattanLength(Vector2Util.copyRelativeTo(pVertexA, pVertexB));
	}

	public static boolean isConvex(final Vector2 pVertexTest, final Vector2 pVertexA, final Vector2 pVertexB) {
		final float f = Vector2Util.area2(pVertexTest, pVertexA, pVertexB);
		return f < 0 || f == 0 && !Vector2Util.isBetween(pVertexTest, pVertexA, pVertexB);
	}

	public static float area2(final Vector2 pVertexTest, final Vector2 pVertexA, final Vector2 pVertexB) {
		return Vector2Util.getCrossProduct(Vector2Util.copyRelativeTo(pVertexA, pVertexTest), Vector2Util.copyRelativeTo(pVertexB, pVertexTest));
	}

	public static float area2(final Vector2 pVertexTest, final Vector2Line pLine) {
		return Vector2Util.area2(pVertexTest, pLine.mVertexA, pLine.mVertexB);
	}

	public static Vector2 copyRelativeTo(final Vector2 pVertexA, final Vector2 pVertexB) {
		return new Vector2(pVertexA.x - pVertexB.x, pVertexA.y - pVertexB.y);
	}

	public static boolean isBetween(final Vector2 pVertexTest, final Vector2 pVertexA, final Vector2 pVertexB) {
		return Vector2Util.getManhattanDistance(pVertexA, pVertexB) >= Vector2Util.getManhattanDistance(pVertexTest, pVertexA) + Vector2Util.getManhattanDistance(pVertexTest, pVertexB);
	}

	public static boolean isRightOf(final Vector2 pVertexTest, final Vector2Line pLine) {
		return Vector2Util.area2(pVertexTest, pLine) < 0;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
