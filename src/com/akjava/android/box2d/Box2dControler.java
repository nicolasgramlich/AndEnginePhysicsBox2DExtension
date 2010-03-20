package com.akjava.android.box2d;

public interface Box2dControler {

	public static final int maxBodySize = 300;

	public void createWorld(float minX, float minY, float maxX, float maxY, float gravityX, float gravityY);

	public int createBox(float x, float y, float width, float height);

	public int createCircle(float x, float y, float radius, float weight, float restitution);

	public void step(float stepTime, int velocityIterations, int positionIterations);

	public BodyInfo getBodyInfo(BodyInfo info, int index);

	public void setGravity(float gravityX, float gravityY);

	public void destroyBody(int id);

	//

	public void getCollisions(collisionIdKeeper keeper, int index);

	// I'm not familiar with jni.same name method make problem?
	public int createBox2(float x, float y, float width, float height, float density, float restitution, float friction);

	public void setBodyXForm(int id, float x, float y, float radiant);

	public void setBodyAngularVelocity(int id, float radiant);

	public void setBodyLinearVelocity(int id, float x, float y);

	public BodyInfo getStatus(BodyInfo info, int index);

	public BodyInfo getLinerVelocity(BodyInfo info, int index);
}
