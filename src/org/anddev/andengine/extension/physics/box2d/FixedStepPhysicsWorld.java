package org.anddev.andengine.extension.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A subclass of {@link PhysicsWorld} that tries to achieve a specific amount of steps per second. 
 * When the time since the last step is bigger long the steplength, additional updates are executed.
 * 
 * @author Nicolas Gramlich
 * @since 12:39:42 - 25.07.2010
 */
public class FixedStepPhysicsWorld extends PhysicsWorld {
	// ===========================================================
	// Constants
	// ===========================================================
	
	public static final int STEPSPERSECOND_DEFAULT = 60;

	// ===========================================================
	// Fields
	// ===========================================================
	
	private final float mStepLength;
	private float mSecondsElapsedAccumulator;

	// ===========================================================
	// Constructors
	// ===========================================================

	public FixedStepPhysicsWorld(final int pStepsPerSecond, final Vector2 pGravity, final boolean pAllowSleep) {
		super(pGravity, pAllowSleep);
		this.mStepLength = 1.0f / pStepsPerSecond;
	}

	public FixedStepPhysicsWorld(final int pStepsPerSecond, final Vector2 pGravity, final boolean pAllowSleep, final int pVelocityIterations, final int pPositionIterations) {
		super(pGravity, pAllowSleep, pVelocityIterations, pPositionIterations);
		this.mStepLength = 1.0f / pStepsPerSecond;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	public void onUpdate(final float pSecondsElapsed) {
		this.mRunnableHandler.onUpdate(pSecondsElapsed);
		this.mSecondsElapsedAccumulator += pSecondsElapsed;

		final World world = this.mWorld;
		final float stepLength = this.mStepLength;
		while(this.mSecondsElapsedAccumulator >= stepLength) {
			world.step(stepLength, this.mVelocityIterations, this.mPositionIterations);
			this.mSecondsElapsedAccumulator -= stepLength;
		}
		this.mPhysicsConnectorManager.onUpdate(pSecondsElapsed);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
