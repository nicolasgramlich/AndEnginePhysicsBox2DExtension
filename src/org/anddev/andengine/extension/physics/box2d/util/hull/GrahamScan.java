package org.anddev.andengine.extension.physics.box2d.util.hull;

/**
 * @author Nicolas Gramlich
 * @since 14:00:50 - 14.09.2010
 * @see http://www.iti.fh-flensburg.de/lang/algorithmen/geo/
 */
public class GrahamScan extends BaseHullAlgorithm {
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
		if(this.mPointCount < 3) {
			return this.mPointCount;
		}
		this.mHullPointCount = 0;
		this.grahamScan();
		return this.mHullPointCount;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void grahamScan() {
		this.swap(0, this.indexOfLowestPoint());
		final Point pl = new Point(this.mPoints[0]);
		this.makeRelativeTo(pl);
		this.sort();
		this.makeRelativeTo(pl.copyReversed());
		int i = 3, k = 3;
		while(k < this.mPointCount) {
			this.swap(i, k);
			while(!this.isConvex(i - 1)) {
				this.swap(i - 1, i--);
			}
			k++;
			i++;
		}
		this.mHullPointCount = i;
	}

	private void makeRelativeTo(final Point pPoint) {
		int i;
		final Point pointCopy = new Point(pPoint); // notwendig, weil pPoint in mPoints[] sein kann
		for(i = 0; i < this.mPointCount; i++) {
			this.mPoints[i].makeRelativeTo(pointCopy);
		}
	}

	private boolean isConvex(final int pIndex) {
		return this.mPoints[pIndex].isConvex(this.mPoints[pIndex - 1], this.mPoints[pIndex + 1]);
	}

	private void sort() {
		this.quicksort(1, this.mPointCount - 1); // ohne Punkt 0
	}

	private void quicksort(final int pFromIndex, final int pToIndex) {
		int i = pFromIndex, j = pToIndex;
		final Point q = this.mPoints[(pFromIndex + pToIndex) / 2];
		while(i <= j) {
			while(this.mPoints[i].isLess(q)) {
				i++;
			}
			while(q.isLess(this.mPoints[j])) {
				j--;
			}
			if(i <= j) {
				this.swap(i++, j--);
			}
		}
		if(pFromIndex < j) {
			this.quicksort(pFromIndex, j);
		}
		if(i < pToIndex) {
			this.quicksort(i, pToIndex);
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
