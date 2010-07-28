package org.anddev.andengine.extension.physics.box2d;


import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.anddev.andengine.util.MathUtils;

import com.badlogic.gdx.math.Vector2;
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
public class PhysicsFactory implements PhysicsConstants {
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
		return createBoxBody(pPhysicsWorld, pShape, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final float halfWidth = pShape.getBaseWidth() * 0.5f / pPixelToMeterRatio;
		final float halfHeight = pShape.getBaseHeight() * 0.5f / pPixelToMeterRatio;

		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;
		boxBodyDef.position.x = pShape.getX() / pPixelToMeterRatio + halfWidth;
		boxBodyDef.position.y = pShape.getY() / pPixelToMeterRatio + halfHeight;
		boxBodyDef.linearVelocity.set(pShape.getVelocityX(), pShape.getVelocityY());
		boxBodyDef.angularVelocity = pShape.getAngularVelocity();

		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);

		final PolygonShape boxPoly = new PolygonShape();
		
		final float localRotationCenterX = pShape.getRotationCenterX() / pPixelToMeterRatio - halfWidth;
		final float localRotationCenterY = pShape.getRotationCenterY() / pPixelToMeterRatio - halfHeight;
		boxPoly.setAsBox(halfWidth, halfHeight, new Vector2(localRotationCenterX, localRotationCenterY), MathUtils.degToRad(pShape.getRotation()));
		pFixtureDef.shape = boxPoly;

		boxBody.createFixture(pFixtureDef);

		boxPoly.dispose();

		return boxBody;
	}

	public static Body createCircleBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return createCircleBody(pPhysicsWorld, pShape, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createCircleBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final float halfWidth = pShape.getBaseWidth() * 0.5f / pPixelToMeterRatio;
		final float halfHeight = pShape.getBaseHeight() * 0.5f / pPixelToMeterRatio;

		final BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = pBodyType;
		circleBodyDef.position.x = pShape.getX() / pPixelToMeterRatio + halfWidth;
		circleBodyDef.position.y = pShape.getY() / pPixelToMeterRatio + halfHeight;
		circleBodyDef.angle = MathUtils.degToRad(pShape.getRotation());
		circleBodyDef.linearVelocity.set(pShape.getVelocityX(), pShape.getVelocityY());
		circleBodyDef.angularVelocity = pShape.getAngularVelocity();

		final Body circleBody = pPhysicsWorld.createBody(circleBodyDef);

		final CircleShape circlePoly = new CircleShape();
		circlePoly.setRadius(halfWidth);
		pFixtureDef.shape = circlePoly;

		circleBody.createFixture(pFixtureDef);

		circlePoly.dispose();

		return circleBody;
	}

	public static Body createLineBody(final PhysicsWorld pPhysicsWorld, final Line pLine, final FixtureDef pFixtureDef) {
		return createLineBody(pPhysicsWorld, pLine, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}
	
	public static Body createLineBody(final PhysicsWorld pPhysicsWorld, final Line pLine, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef lineBodyDef = new BodyDef();
		lineBodyDef.type = BodyType.StaticBody;

		final Body boxBody = pPhysicsWorld.createBody(lineBodyDef);

		final PolygonShape linePoly = new PolygonShape();
		
		linePoly.setAsEdge(new Vector2(pLine.getX1() / pPixelToMeterRatio, pLine.getY1() / pPixelToMeterRatio), new Vector2(pLine.getX2() / pPixelToMeterRatio, pLine.getY2() / pPixelToMeterRatio));
		pFixtureDef.shape = linePoly;

		boxBody.createFixture(pFixtureDef);

		linePoly.dispose();

		return boxBody;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
