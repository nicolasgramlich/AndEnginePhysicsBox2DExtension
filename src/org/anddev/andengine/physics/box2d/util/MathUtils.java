package org.anddev.andengine.physics.box2d.util;

import java.math.BigDecimal;

public final class MathUtils {
	/*
	 * these code come from jme
	 */
	public static final float PI = (float) Math.PI;
	public static final float DEG_TO_RAD = PI / 180.0f;
	public static final float RAD_TO_DEG = 180.0f / PI;

	public static float radToDeg(final float rad) {
		return RAD_TO_DEG * rad;
	}

	public static float degToRad(final float deg) {
		return DEG_TO_RAD * deg;
	}

	public static float random(final float min, final float max) {
		return (float) Math.random() * (max - min) + min;
	}

	public static int randomInt(final int min, final int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	public static float toRoundHalfUp(float value, int index) {
		BigDecimal ret = new BigDecimal(value);
		try {

			return ret.setScale(index, BigDecimal.ROUND_HALF_UP).floatValue();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return 0;// ret direct

	}
}
