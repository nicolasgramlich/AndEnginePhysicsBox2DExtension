package org.anddev.andengine.extension.physics.box2d.util.hull;

import com.badlogic.gdx.math.Vector2;

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
	public int computeHull(final Vector2[] pVectors) {
		this.mVertices = pVectors;
		this.mVertexCount = pVectors.length;
		this.mHullVertexCount = 0;
		this.jarvisMarch();
		return this.mHullVertexCount;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void jarvisMarch() {
		final Vector2[] vertices = this.mVertices;

		int index = this.indexOfLowestVertex();
		do {
			this.swap(this.mHullVertexCount, index);
			index = this.indexOfRightmostVertexOf(vertices[this.mHullVertexCount]);
			this.mHullVertexCount++;
		} while(index > 0);
	}

	private int indexOfRightmostVertexOf(final Vector2 pVector) {
		final Vector2[] vertices = this.mVertices;
		final int vertexCount = this.mVertexCount;

		int i = 0;
		for(int j = 1; j < vertexCount; j++) {
			if(Vector2Util.isLess(Vector2Util.copyRelativeTo(vertices[j], pVector), Vector2Util.copyRelativeTo(vertices[i], pVector))) {
				i = j;
			}
		}
		return i;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
