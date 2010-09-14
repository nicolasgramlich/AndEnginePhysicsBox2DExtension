package org.anddev.andengine.extension.physics.box2d.util.hull;

/**
 * @author Nicolas Gramlich
 * @since 14:01:34 - 14.09.2010
 * @see http://www.iti.fh-flensburg.de/lang/algorithmen/geo/
 */
public class QuickHull extends BaseHullAlgorithm {
	// ===========================================================
	// Constants
	// ===========================================================

	private final static float EPSILON = 1e-3f;

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
		this.mPointCount = this.mPoints.length;
		this.mHullPointCount = 0;
		this.quickHull();
		return this.mHullPointCount;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	private void quickHull() {
		this.swap(0, this.indexOfLowestPoint());
		this.mHullPointCount++;
		final Line line = new Line(this.mPoints[0], this.mPoints[0].copyWithOffset(-EPSILON, 0));
		this.computeHullPoints(line, 1, this.mPointCount - 1);
	}

	private void computeHullPoints(final Line pLine, final int pIndexFrom, final int pIndexTo) {
		if(pIndexFrom > pIndexTo) {
			return;
		}
		final int k = this.indexOfFurthestPoint(pLine, pIndexFrom, pIndexTo);

		final Line lineA = new Line(pLine.mPointA, this.mPoints[k]);
		final Line lineB = new Line(this.mPoints[k], pLine.mPointB);
		this.swap(k, pIndexTo);

		final int i = this.partition(lineA, pIndexFrom, pIndexTo - 1);
		// alle Punkte von pIndexFrom bis i-1 liegen rechts von lineA
		// alle Punkte von i bis pIndexTo-1 liegen links von lineA
		this.computeHullPoints(lineA, pIndexFrom, i - 1);

		// alle eben rekursiv erzeugten Punkte liegen auf dem Hüllpolygonzug vor
		// p[pIndexTo]
		this.swap(pIndexTo, i);
		this.swap(i, this.mHullPointCount);
		this.mHullPointCount++;

		final int j = this.partition(lineB, i + 1, pIndexTo);
		// alle Punkte von i+1 bis j-1 liegen rechts von lineB,
		// alle Punkte von j bis pToIndex liegen im Inneren
		this.computeHullPoints(lineB, i + 1, j - 1);
	}

	private int indexOfFurthestPoint(final Line pLine, final int pFromIndex, final int pToIndex) {
		final Point[] points = this.mPoints;

		int f = pFromIndex;
		float mx = 0;
		for(int i = pFromIndex; i <= pToIndex; i++) {
			final float d = -points[i].area2(pLine);
			if(d > mx || d == mx && points[i].mX > points[f].mX) {
				mx = d;
				f = i;
			}
		}
		return f;
	}

	private int partition(final Line pLine, final int pFromIndex, final int pToIndex) {
		final Point[] points = this.mPoints;

		int i = pFromIndex;
		int j = pToIndex;
		while(i <= j) {
			while(i <= j && points[i].isRightOf(pLine)) {
				i++;
			}
			while(i <= j && !points[j].isRightOf(pLine)) {
				j--;
			}
			if(i <= j) {
				this.swap(i++, j--);
			}
		}
		return i;
	}

}