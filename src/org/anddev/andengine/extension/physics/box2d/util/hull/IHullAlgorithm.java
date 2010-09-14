package org.anddev.andengine.extension.physics.box2d.util.hull;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Nicolas Gramlich
 * @since 13:46:22 - 14.09.2010
 */
public interface IHullAlgorithm {
	// ===========================================================
	// Final Fields
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public int computeHull(final Vector2[] pVertices);
}
