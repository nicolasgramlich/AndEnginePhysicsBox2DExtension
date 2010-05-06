package org.anddev.andengine.extension.physics.box2d;

import org.anddev.andengine.extension.physics.box2d.util.Box2DJNIProxyContactListener;

public class Box2DNativeWrapper {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int MAXBODYSIZE = 300;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	static {
		System.loadLibrary("andenginephysicsbox2d");
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

	public native void createWorld(final float pMinX, final float pMinY, final float pMaxX, final float pMaxY, final float pGravityX, final float pGravityY);

	public native void step(final Box2DJNIProxyContactListener pBox2DJNIProxyContactListener, final float pStepTime, final int pIterations);

	public native int createCircle(final float pX, final float pY, final float pRadius, final float pDensity, final float pRestitution, final float pFriction, final boolean pFixedRotation, final boolean pHandleContacts);

	public native int createBox(final float pX, final float pY, final float pWidth, final float pHeight, final float pDensity, final float pRestitution, final float pFriction, final boolean pFixedRotation, final boolean pHandleContacts);

	public native void destroyBody(final int pPhysicsID);

	public native void getBodyInfo(final Box2DBodyInfo pBox2DBodyInfo, final int pPhysicsID);

	public native void setGravity(final float pGravityX, final float pGravityY);

	public native void setBodyXForm(final int pPhysicsID, final float pX, final float pY, final float pRadiant);

	public native void setBodyAngularVelocity(final int pPhysicsID, final float pRadiant);

	public native void setBodyLinearVelocity(final int pPhysicsID, final float pX, final float pY);

	public native void getStatus(final Box2DBodyInfo pBox2DBodyInfo, final int pPhysicsID);

	public native void getLinearVelocity(final Box2DBodyInfo pBox2DBodyInfo, final int pPhysicsID);

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
