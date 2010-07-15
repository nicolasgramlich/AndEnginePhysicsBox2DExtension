package org.anddev.andengine.extension.physics.box2d.entity;


import org.anddev.andengine.entity.shape.Shape;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
	
	public static Body createBoxBody(final World pWorld, final Shape pShape, final BodyType pBodyType) {
		final float halfWidth = pShape.getBaseWidth() * 0.5f;
		final float halfHeight = pShape.getBaseHeight() * 0.5f;
		
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;
		boxBodyDef.position.x = pShape.getX() + halfWidth;
		boxBodyDef.position.y = pShape.getY() + halfHeight;
		
		final Body boxBody = pWorld.createBody(boxBodyDef);
		
		final PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(halfWidth, halfHeight);
		
		boxBody.createFixture(boxPoly, 10); // TODO Instead of 10 pass a variable parameter
		
		boxPoly.dispose();
		
		return boxBody;
	}
	
	public static Body createCircleBody(final World pWorld, final Shape pShape, final BodyType pBodyType) {
		final float halfWidth = pShape.getBaseWidth() * 0.5f;
		final float halfHeight = pShape.getBaseHeight() * 0.5f;
		
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;
		boxBodyDef.position.x = pShape.getX() + halfWidth;
		boxBodyDef.position.y = pShape.getY() + halfHeight;
		
		final Body boxBody = pWorld.createBody(boxBodyDef);
		
		final CircleShape circlePoly = new CircleShape();
		circlePoly.setRadius(halfWidth);
		
		boxBody.createFixture(circlePoly, 10); // TODO Instead of 10 pass a variable parameter
		
		circlePoly.dispose();
		
		return boxBody;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
