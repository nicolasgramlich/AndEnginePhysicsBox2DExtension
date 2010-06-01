package org.anddev.andengine.extension.physics.box2d.adt;

import org.anddev.andengine.entity.shape.Shape;

/**
 * @author Nicolas Gramlich
 * @since 13:40:18 - 22.03.2010
 */
public class StaticPhysicsBody extends BasePhysicsBody {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public StaticPhysicsBody(final Shape pShape, final float pMass, final float pFricition, final float pElasticity, final PhysicsShape pPhysicsShape) {
		super(pShape, pMass, pFricition, pElasticity, pPhysicsShape);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

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
