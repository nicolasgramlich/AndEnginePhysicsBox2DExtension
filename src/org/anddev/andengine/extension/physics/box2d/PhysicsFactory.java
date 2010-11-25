package org.anddev.andengine.extension.physics.box2d;


import static org.anddev.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import java.util.List;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.constants.Constants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

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

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createBoxBody(pPhysicsWorld, pIShape, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pIShape.getSceneCenterCoordinates();
		boxBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / pPixelToMeterRatio;
		boxBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / pPixelToMeterRatio;

		boxBodyDef.linearVelocity.set(pIShape.getVelocityX(), pIShape.getVelocityY());
		boxBodyDef.angularVelocity = pIShape.getAngularVelocity();

		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);

		final PolygonShape boxPoly = new PolygonShape();

		final float halfWidth = pIShape.getWidthScaled() * 0.5f / pPixelToMeterRatio;
		final float halfHeight = pIShape.getHeightScaled() * 0.5f / pPixelToMeterRatio;

		boxPoly.setAsBox(halfWidth, halfHeight);
		pFixtureDef.shape = boxPoly;

		boxBody.createFixture(pFixtureDef);

		boxPoly.dispose();

		boxBody.setTransform(boxBody.getWorldCenter(), MathUtils.degToRad(pIShape.getRotation()));

		return boxBody;
	}

	public static Body createCircleBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createCircleBody(pPhysicsWorld, pIShape, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createCircleBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {

		final BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pIShape.getSceneCenterCoordinates();
		circleBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / pPixelToMeterRatio;
		circleBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / pPixelToMeterRatio;

		circleBodyDef.angle = MathUtils.degToRad(pIShape.getRotation());
		circleBodyDef.linearVelocity.set(pIShape.getVelocityX(), pIShape.getVelocityY());
		circleBodyDef.angularVelocity = pIShape.getAngularVelocity();

		final Body circleBody = pPhysicsWorld.createBody(circleBodyDef);

		final CircleShape circlePoly = new CircleShape();
		pFixtureDef.shape = circlePoly;

		final float radius = pIShape.getWidthScaled() * 0.5f / pPixelToMeterRatio;
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
	 * @param pIShape
	 * @param pVertices are to be defined relative to the center of the pIShape and have the {@link PhysicsConstants#PIXEL_TO_METER_RATIO_DEFAULT} applied.
	 * @param pBodyType
	 * @param pFixtureDef
	 * @return
	 */
	public static Body createPolygonBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final Vector2[] pVertices, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createPolygonBody(pPhysicsWorld, pIShape, pVertices, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}

	/**
	 * @param pPhysicsWorld
	 * @param pIShape
	 * @param pVertices are to be defined relative to the center of the pIShape.
	 * @param pBodyType
	 * @param pFixtureDef
	 * @return
	 */
	public static Body createPolygonBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final Vector2[] pVertices, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pIShape.getSceneCenterCoordinates();
		boxBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / pPixelToMeterRatio;
		boxBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / pPixelToMeterRatio;

		boxBodyDef.linearVelocity.set(pIShape.getVelocityX(), pIShape.getVelocityY());
		boxBodyDef.angularVelocity = pIShape.getAngularVelocity();

		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);

		final PolygonShape boxPoly = new PolygonShape();

		boxPoly.set(pVertices);
		pFixtureDef.shape = boxPoly;

		boxBody.createFixture(pFixtureDef);

		boxPoly.dispose();

		return boxBody;
	}
	

	/**
	 * @param pPhysicsWorld
	 * @param pIShape
	 * @param pTriangleVertices are to be defined relative to the center of the pIShape and have the {@link PhysicsConstants#PIXEL_TO_METER_RATIO_DEFAULT} applied.
	 * @param pBodyType
	 * @param pFixtureDef
	 * @return
	 */
	public static Body createTrianglulatedBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final List<Vector2> pTriangleVertices, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createTrianglulatedBody(pPhysicsWorld, pIShape, pTriangleVertices, pBodyType, pFixtureDef, PIXEL_TO_METER_RATIO_DEFAULT);
	}
	
	/**
	 * @param pPhysicsWorld
	 * @param pIShape
	 * @param pTriangleVertices are to be defined relative to the center of the pIShape and have the {@link PhysicsConstants#PIXEL_TO_METER_RATIO_DEFAULT} applied. 
	 * 					The vertices will be triangulated and for each triangle a {@link Fixture} will be created.
	 * @param pBodyType
	 * @param pFixtureDef
	 * @return
	 */
	public static Body createTrianglulatedBody(final PhysicsWorld pPhysicsWorld, final IShape pIShape, final List<Vector2> pTriangleVertices, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final Vector2[] TMP_TRIANGLE = new Vector2[3];
		
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;

		final float[] sceneCenterCoordinates = pIShape.getSceneCenterCoordinates();
		boxBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] / pPixelToMeterRatio;
		boxBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] / pPixelToMeterRatio;

		boxBodyDef.linearVelocity.set(pIShape.getVelocityX(), pIShape.getVelocityY());
		boxBodyDef.angularVelocity = pIShape.getAngularVelocity();

		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);

		final int vertexCount = pTriangleVertices.size();
		for(int i = 0; i < vertexCount; /* */) {
			final PolygonShape boxPoly = new PolygonShape();
			
			TMP_TRIANGLE[2] = pTriangleVertices.get(i++);
			TMP_TRIANGLE[1] = pTriangleVertices.get(i++);
			TMP_TRIANGLE[0] = pTriangleVertices.get(i++);
			
			boxPoly.set(TMP_TRIANGLE);
			pFixtureDef.shape = boxPoly;

			boxBody.createFixture(pFixtureDef);

			boxPoly.dispose();
		}

		return boxBody;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
