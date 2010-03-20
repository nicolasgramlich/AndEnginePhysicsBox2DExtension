package com.akjava.android.box2d;

public class NDKBox2dControler implements Box2dControler {

	static {
		System.loadLibrary("box2d");
	}

	@Override
	public native void createWorld(float minX, float minY, float maxX, float maxY, float gravityX, float gravityY);

	@Override
	public native int createBox(float x, float y, float width, float height);

	@Override
	public native int createCircle(float x, float y, float radius, float weight, float restitution);

	public native BodyInfo getBodyInfo(BodyInfo info, int index);

	@Override
	public native void step(float stepTime, int velocityIterations, int positionIterations);

	@Override
	public native void setGravity(float gravityX, float gravityY);

	@Override
	public native void destroyBody(int id);

	@Override
	public native void getCollisions(collisionIdKeeper keeper, int index);

	@Override
	public native int createBox2(float x, float y, float width, float height, float density, float restitution, float friction);

	@Override
	public native void setBodyXForm(int id, float x, float y, float radiant);

	@Override
	public native void setBodyAngularVelocity(int id, float radiant);

	@Override
	public native void setBodyLinearVelocity(int id, float x, float y);

	public native BodyInfo getStatus(BodyInfo info, int index);

	public native BodyInfo getLinerVelocity(BodyInfo info, int index);

}
