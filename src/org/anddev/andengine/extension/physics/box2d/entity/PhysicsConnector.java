package org.anddev.andengine.extension.physics.box2d.entity;

import org.anddev.andengine.entity.IUpdateHandler;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.util.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author Nicolas Gramlich
 * @since 18:51:22 - 05.07.2010
 */
public class PhysicsConnector implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Shape mShape;
	private final Body mBody;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PhysicsConnector(final Shape pShape, final Body pBody) {
		this.mShape = pShape;
		this.mBody = pBody;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public Shape getShape() {
		return this.mShape;
	}

	public Body getBody() {
		return this.mBody;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		// TODO OPTIMIZE HARD
		final Shape shape = this.mShape;
		final Body body = this.mBody;
		
		final float angle = body.getAngle();
		shape.setRotation(MathUtils.radToDeg(angle));
		
		final Vector2 position = body.getPosition();
		shape.setPosition(position.x, position.y);
		
		final Vector2 linearVelocity = body.getLinearVelocity();
		shape.setVelocity(linearVelocity.x, linearVelocity.y);
	}

	@Override
	public void reset() {

	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
