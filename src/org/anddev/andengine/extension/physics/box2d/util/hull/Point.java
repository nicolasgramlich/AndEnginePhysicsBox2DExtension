package org.anddev.andengine.extension.physics.box2d.util.hull;

/**
 * @author Nicolas Gramlich
 * @since 14:01:26 - 14.09.2010
 * @see http://www.iti.fh-flensburg.de/lang/algorithmen/geo/
 */
public class Point {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	float mX;
	float mY;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Point(final Point pPoint) {
		this(pPoint.mX, pPoint.mY);
	}

	public Point(final float pX, final float pY) {
		this.mX = pX;
		this.mY = pY;
	}

	public Point copyWithOffset(final float pX, final float pY) {
		return new Point(this.mX + pX, this.mY + pY);
	}

	public Point copyReversed() {
		return new Point(-this.mX, -this.mY);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public Point relativeTo(final Point pPoint) {
		return new Point(this.mX - pPoint.mX, this.mY - pPoint.mY);
	}

	public void makeRelativeTo(final Point pPoint) {
		this.mX -= pPoint.mX;
		this.mY -= pPoint.mY;
	}

	public boolean isLower(final Point pPoint) {
		return this.mY < pPoint.mY || this.mY == pPoint.mY && this.mX < pPoint.mX;
	}

	public float getManhattanDistance() {
		return Math.abs(this.mX) + Math.abs(this.mY);
	}

	public float getManhattanDistance(final Point pPoint) {
		return this.relativeTo(pPoint).getManhattanDistance();
	}

	public boolean isFurther(final Point pPoint) {
		return this.getManhattanDistance() > pPoint.getManhattanDistance();
	}

	public boolean isBetween(final Point pPointA, final Point pPointB) {
		return pPointA.getManhattanDistance(pPointB) >= this.getManhattanDistance(pPointA) + this.getManhattanDistance(pPointB);
	}

	public float getCrossProduct(final Point pPoint) {
		return this.mX * pPoint.mY - pPoint.mX * this.mY;
	}

	public boolean isLess(final Point pPoint) {
		final float f = this.getCrossProduct(pPoint);
		return f > 0 || f == 0 && this.isFurther(pPoint);
	}

	public float area2(final Point pPointA, final Point pPointB) {
		return pPointA.relativeTo(this).getCrossProduct(pPointB.relativeTo(this));
	}

	public float area2(final Line pLine) {
		return this.area2(pLine.mPointA, pLine.mPointB);
	}

	public boolean isRightOf(final Line pLine) {
		return this.area2(pLine) < 0;
	}

	public boolean isConvex(final Point pPointA, final Point pPointB) {
		final float f = this.area2(pPointA, pPointB);
		return f < 0 || f == 0 && !this.isBetween(pPointA, pPointB);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
