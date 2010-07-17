package org.anddev.andengine.extension.physics.box2d;


import org.anddev.andengine.entity.shape.Shape;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * @author Nicolas Gramlich
 * @since 13:59:03 - 15.07.2010
 */
public class PhysicsFactory {
	// ===========================================================
	// Constants
	// ===========================================================

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

	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction) {
		return createFixtureDef(pDensity, pElasticity, pFriction, false);
	}

	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction, final boolean pSensor) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = pDensity;
		fixtureDef.restitution = pElasticity;
		fixtureDef.friction = pFriction;
		fixtureDef.isSensor = pSensor;
		return fixtureDef;
	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		final float halfWidth = pShape.getBaseWidth() * 0.5f;
		final float halfHeight = pShape.getBaseHeight() * 0.5f;

		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;
		boxBodyDef.position.x = pShape.getX() + halfWidth;
		boxBodyDef.position.y = pShape.getY() + halfHeight;

		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);

		final PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(halfWidth, halfHeight);
		pFixtureDef.shape = boxPoly;

		boxBody.createFixture(pFixtureDef);

		boxPoly.dispose();

		return boxBody;
	}

	public static Body createCircleBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		final float halfWidth = pShape.getBaseWidth() * 0.5f;
		final float halfHeight = pShape.getBaseHeight() * 0.5f;

		final BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = pBodyType;
		circleBodyDef.position.x = pShape.getX() + halfWidth;
		circleBodyDef.position.y = pShape.getY() + halfHeight;

		final Body circleBody = pPhysicsWorld.createBody(circleBodyDef);

		final CircleShape circlePoly = new CircleShape();
		circlePoly.setRadius(halfWidth);
		pFixtureDef.shape = circlePoly;

		circleBody.createFixture(pFixtureDef);

		circlePoly.dispose();

		return circleBody;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
