package org.anddev.andengine.extension.physics.box2d;

import org.anddev.andengine.engine.handler.IUpdateHandler;
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

	final Shape mShape;
	final Body mBody;
	
	private final float mShapeHalfBaseWidth;
	private final float mShapeHalfBaseHeight;

	private final boolean mUpdatePosition;
	private final boolean mUpdateLinearVelocity;
	private final boolean mUpdateRotation;
	private final boolean mUpdateAngularVelocity;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PhysicsConnector(final Shape pShape, final Body pBody) {
		this(pShape, pBody, true, true, true, true);
	}

	public PhysicsConnector(final Shape pShape, final Body pBody, final boolean pUdatePosition, final boolean pUpdateRotation, final boolean pUpdateLinearVelocity, final boolean pUpdateAngularVelocity) {
		this.mShape = pShape;
		this.mBody = pBody;
		
		this.mUpdatePosition = pUdatePosition;
		this.mUpdateRotation = pUpdateRotation;
		this.mUpdateLinearVelocity = pUpdateLinearVelocity;
		this.mUpdateAngularVelocity = pUpdateAngularVelocity;

		this.mShapeHalfBaseWidth = pShape.getBaseWidth() * 0.5f;
		this.mShapeHalfBaseHeight = pShape.getBaseHeight() * 0.5f;
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
		final Shape shape = this.mShape;
		final Body body = this.mBody;

		if(this.mUpdatePosition) {
			final Vector2 position = body.getPosition();
			shape.setPosition(position.x - this.mShapeHalfBaseWidth, position.y - this.mShapeHalfBaseHeight);			
		}
		
		if(this.mUpdateRotation) {
			final float angle = body.getAngle();
			shape.setRotation(MathUtils.radToDeg(angle));
		}

		if(this.mUpdateLinearVelocity) {
			final Vector2 linearVelocity = body.getLinearVelocity();
			shape.setVelocity(linearVelocity.x, linearVelocity.y);
		}
		
		if(this.mUpdateAngularVelocity) {
			final float angularVelocity = body.getAngularVelocity();
			shape.setAngularVelocity(angularVelocity);
		}
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
