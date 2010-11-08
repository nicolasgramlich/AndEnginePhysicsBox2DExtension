package org.anddev.andengine.extension.physics.box2d.util;

import org.anddev.andengine.util.pool.GenericPool;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Nicolas Gramlich
 * @since 16:22:23 - 14.09.2010
 */
public class Vector2Pool {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private static final GenericPool<Vector2> POOL = new GenericPool<Vector2>() {
		@Override
		protected Vector2 onAllocatePoolItem() {
			return new Vector2();
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	public static Vector2 obtain() {
		return POOL.obtainPoolItem();
	}
	
	public static Vector2 obtain(final Vector2 pCopyFrom) {
		return POOL.obtainPoolItem().set(pCopyFrom);
	}
	
	public static void recycle(final Vector2 pVector2) {
		POOL.recyclePoolItem(pVector2);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
