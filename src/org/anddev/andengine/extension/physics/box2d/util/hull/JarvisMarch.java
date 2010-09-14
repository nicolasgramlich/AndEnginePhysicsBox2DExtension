package org.anddev.andengine.extension.physics.box2d.util.hull;

/**
 * @author Nicolas Gramlich
 * @since 14:01:18 - 14.09.2010
 * @see http://www.iti.fh-flensburg.de/lang/algorithmen/geo/
 */
public class JarvisMarch extends BaseHullAlgorithm {
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

	@Override
	public int computeHull(final Point[] pPoints) {
		this.mPoints = pPoints;
		this.mPointCount = pPoints.length;
		this.mHullPointCount = 0;
		this.jarvisMarch();
		return this.mHullPointCount;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void jarvisMarch() {
		final Point[] points = this.mPoints;
		
		int index = this.indexOfLowestPoint();
		do {
			this.swap(this.mHullPointCount, index);
			index = this.indexOfRightmostPointFrom(points[this.mHullPointCount]);
			this.mHullPointCount++;
		} while(index > 0);
	}

	private int indexOfRightmostPointFrom(final Point pPoint) {
		final Point[] points = this.mPoints;
		
		int i = 0;
		final int pointCount = this.mPointCount;
		for(int j = 1; j < pointCount; j++) {
			if(points[j].relativeTo(pPoint).isLess(points[i].relativeTo(pPoint))) {
				i = j;
			}
		}
		return i;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
