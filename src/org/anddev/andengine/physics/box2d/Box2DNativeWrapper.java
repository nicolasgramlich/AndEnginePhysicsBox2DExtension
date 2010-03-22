package org.anddev.andengine.physics.box2d;

import org.anddev.andengine.physics.box2d.util.collisionIdKeeper;

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

	public native int createBox(final float pX, final float pY, final float pWidth, final float pHeight);

	public native int createCircle(final float pX, final float PY, final float pRadius, final float weight, final float restitution);

	public native void getBodyInfo(final BodyInfo pBodyInfo, final int pPhysicsID);

	public native void step(final float pStepTime, final int pVelocityIterations, final int pPositionIterations);

	public native void setGravity(final float pGravityX, final float pGravityY);

	public native void destroyBody(final int pPhysicsID);

	public native void getCollisions(final collisionIdKeeper pKeeper, final int pPhysicsID);

	public native int createBox2(final float pX, final float pY, final float pWidth, final float pHeight, final float pDensity, final float pRestitution, final float pFriction);

	public native void setBodyXForm(final int pPhysicsID, final float pX, final float pY, final float pRadiant);

	public native void setBodyAngularVelocity(final int pPhysicsID, final float pRadiant);

	public native void setBodyLinearVelocity(final int pPhysicsID, final float pX, final float pY);

	public native void getStatus(final BodyInfo pBodyInfo, final int pPhysicsID);

	public native void getLinearVelocity(final BodyInfo pBodyInfo, final int pPhysicsID);

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
