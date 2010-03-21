package org.anddev.andengine.physics.box2d;

import java.util.HashMap;

import org.anddev.andengine.entity.DynamicEntity;
import org.anddev.andengine.entity.StaticEntity;
import org.anddev.andengine.physics.IPhysicsSpace;
import org.anddev.andengine.physics.PhysicsData;

/**
 * @author Nicolas Gramlich
 * @since 10:56:23 - 21.03.2010
 */
public class Box2DPhysicsSpace implements IPhysicsSpace {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private final HashMap<StaticEntity, Integer> mPhysicsIDMapping = new HashMap<StaticEntity, Integer>();

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public Box2DPhysicsSpace() {
		
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void addDynamicEntity(final DynamicEntity pDynamicEntity, final PhysicsData pPhysicsData) {
	}

	@Override
	public void addStaticEntity(final StaticEntity pStaticEntity, final PhysicsData pPhysicsData) {
	}

	@Override
	public void setVelocity(final DynamicEntity pDynamicEntity, final float pVelocityX, final float pVelocityY) {
	}

	@Override
	public void onUpdate(final float pSecondsElapsed) {
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
