package org.anddev.andengine.extension.physics.box2d.util;

import org.anddev.andengine.extension.physics.box2d.Box2DContactListener;

/**
 * @author Nicolas Gramlich
 * @since 17:59:21 - 23.03.2010
 */
public class Box2DJNIProxyContactListener {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Box2DContactListener mBox2dContactListener;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Box2DJNIProxyContactListener(final Box2DContactListener pBox2DContactListener) {
		this.mBox2dContactListener = pBox2DContactListener;
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

	public void add(final int pPhysicsIDA, final int pPhysicsIDB) {
		this.mBox2dContactListener.onNewContact(pPhysicsIDA, pPhysicsIDB);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
