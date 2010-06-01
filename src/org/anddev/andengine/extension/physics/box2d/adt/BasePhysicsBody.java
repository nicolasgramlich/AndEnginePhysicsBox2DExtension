package org.anddev.andengine.extension.physics.box2d.adt;

import org.anddev.andengine.entity.shape.Shape;

/**
 * @author Nicolas Gramlich
 * @since 19:58:01 - 20.03.2010
 */
public abstract class BasePhysicsBody {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public final Shape mShape;
	public final float mMass;
	public final float mFricition;
	public final float mElasticity;
	public final PhysicsShape mPhysicsShape;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BasePhysicsBody(final Shape pShape, final float pMass, final float pFricition, final float pElasticity, final PhysicsShape pPhysicsShape) {
		this.mShape = pShape;
		this.mMass = pMass;
		this.mFricition = pFricition;
		this.mElasticity = pElasticity;
		this.mPhysicsShape = pPhysicsShape;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public Shape getShape() {
		return this.mShape;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
