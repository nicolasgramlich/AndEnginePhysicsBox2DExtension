package org.anddev.andengine.physics.box2d.util;


/**
 * @author Nicolas Gramlich
 * @since 12:47:21 - 24.03.2010
 */
public final class MathUtils {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final float PI = (float) Math.PI;
	public static final float DEG_TO_RAD = PI / 180.0f;
	public static final float RAD_TO_DEG = 180.0f / PI;


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

	public static float radToDeg(final float pRad) {
		return RAD_TO_DEG * pRad;
	}

	public static float degToRad(final float pDegree) {
		return DEG_TO_RAD * pDegree;
	}

	public static float random(final float pMin, final float pMax) {
		return (float) Math.random() * (pMax - pMin) + pMin;
	}

	public static int random(final int pMin, final int max) {
		return (int) (Math.random() * (max - pMin) + pMin);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
