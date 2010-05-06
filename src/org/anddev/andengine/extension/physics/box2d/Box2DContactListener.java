package org.anddev.andengine.extension.physics.box2d;

/**
 * @author Nicolas Gramlich
 * @since 21:20:42 - 23.03.2010
 */
public interface Box2DContactListener {
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Methods
	// ===========================================================
	
	public void onNewContact(final int pPhysicsIDA, final int pPhysicsIDB);
}
