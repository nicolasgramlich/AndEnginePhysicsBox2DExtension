package org.anddev.andengine.extension.physics.box2d;


import static org.anddev.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.constants.Constants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
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
		return PhysicsFactory.createFixtureDef(pDensity, pElasticity, pFriction, false);
	}

	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction, final boolean pSensor) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = pDensity;
		fixtureDef.restitution = pElasticity;
		fixtureDef.friction = pFriction;
		fixtureDef.isSensor = pSensor;
		return fixtureDef;
	}

	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction, final boolean pSensor, final short pCategoryBits, final short pMaskBits, final short pGroupIndex) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = pDensity;
		fixtureDef.restitution = pElasticity;
		fixtureDef.friction = pFriction;
		fixtureDef.isSensor = pSensor;
		final Filter filter = fixtureDef.filter;
		filter.categoryBits = pCategoryBits;
		filter.maskBits = pMaskBits;
		filter.groupIndex = pGroupIndex;
		return fixtureDef;
	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createBoxBody(pPhysicsWorld, pShape, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pShape.getSceneCenterCoordinates();
		boxBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / pPixelToMeterRatio;
		boxBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / pPixelToMeterRatio;

		boxBodyDef.linearVelocity.set(pShape.getVelocityX(), pShape.getVelocityY());
		boxBodyDef.angularVelocity = pShape.getAngularVelocity();

		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);

		final PolygonShape boxPoly = new PolygonShape();

		final float halfWidth = pShape.getWidthScaled() * 0.5f / pPixelToMeterRatio;
		final float halfHeight = pShape.getHeightScaled() * 0.5f / pPixelToMeterRatio;

		boxPoly.setAsBox(halfWidth, halfHeight);
		pFixtureDef.shape = boxPoly;

		boxBody.createFixture(pFixtureDef);

		boxPoly.dispose();

		boxBody.setTransform(boxBody.getWorldCenter(), MathUtils.degToRad(pShape.getRotation()));

		return boxBody;
	}

	public static Body createCircleBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createCircleBody(pPhysicsWorld, pShape, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createCircleBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {

		final BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pShape.getSceneCenterCoordinates();
		circleBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / pPixelToMeterRatio;
		circleBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / pPixelToMeterRatio;

		circleBodyDef.angle = MathUtils.degToRad(pShape.getRotation());
		circleBodyDef.linearVelocity.set(pShape.getVelocityX(), pShape.getVelocityY());
		circleBodyDef.angularVelocity = pShape.getAngularVelocity();

		final Body circleBody = pPhysicsWorld.createBody(circleBodyDef);

		final CircleShape circlePoly = new CircleShape();
		pFixtureDef.shape = circlePoly;

		final float radius = pShape.getWidthScaled() * 0.5f / pPixelToMeterRatio;
		circlePoly.setRadius(radius);

		circleBody.createFixture(pFixtureDef);

		circlePoly.dispose();

		return circleBody;
	}

	public static Body createLineBody(final PhysicsWorld pPhysicsWorld, final Line pLine, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createLineBody(pPhysicsWorld, pLine, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
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

	/**
	 * @param pPhysicsWorld
	 * @param pShape
	 * @param pVertices are to be defined relative to the center of the pShape.
	 * @param pBodyType
	 * @param pFixtureDef
	 * @return
	 */
	public static Body createPolygonBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final Vector2[] pVertices, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createPolygonBody(pPhysicsWorld, pShape, pVertices, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}

	/**
	 * @param pPhysicsWorld
	 * @param pShape
	 * @param pVertices are to be defined relative to the center of the pShape.
	 * @param pBodyType
	 * @param pFixtureDef
	 * @return
	 */
	public static Body createPolygonBody(final PhysicsWorld pPhysicsWorld, final Shape pShape, final Vector2[] pVertices, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pShape.getSceneCenterCoordinates();
		boxBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / pPixelToMeterRatio;
		boxBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / pPixelToMeterRatio;

		boxBodyDef.linearVelocity.set(pShape.getVelocityX(), pShape.getVelocityY());
		boxBodyDef.angularVelocity = pShape.getAngularVelocity();

		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);

		final PolygonShape boxPoly = new PolygonShape();

		boxPoly.set(pVertices);
		pFixtureDef.shape = boxPoly;

		boxBody.createFixture(pFixtureDef);

		boxPoly.dispose();

		return boxBody;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
