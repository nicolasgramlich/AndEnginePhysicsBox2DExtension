package org.anddev.andengine.extension.physics.box2d.util.hull;

/**
 * @author Nicolas Gramlich
 * @since 14:05:51 - 14.09.2010
 */
public abstract class BaseHullAlgorithm implements IHullAlgorithm {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	protected Point[] mPoints;
	protected int mPointCount;
	protected int mHullPointCount;

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

	protected int indexOfLowestPoint() {
		int min = 0;
		final Point[] points = this.mPoints;
		for(int i = 1; i < this.mPointCount; i++) {
			final float dY = points[i].mY - points[min].mY;
			final float dX = points[i].mX - points[min].mX;
			if(dY < 0 || dY == 0 && dX < 0) {
				min = i;
			}
		}
		return min;
	}

	protected void swap(final int pIndexA, final int pIndexB) {
		final Point[] points = this.mPoints;
		final Point tmp = points[pIndexA];
		points[pIndexA] = points[pIndexB];
		points[pIndexB] = tmp;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
