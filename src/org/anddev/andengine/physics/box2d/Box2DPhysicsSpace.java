package org.anddev.andengine.physics.box2d;

import java.util.ArrayList;

import org.anddev.andengine.entity.DynamicEntity;
import org.anddev.andengine.entity.StaticEntity;
import org.anddev.andengine.physics.DynamicPhysicsBody;
import org.anddev.andengine.physics.IPhysicsSpace;
import org.anddev.andengine.physics.StaticPhysicsBody;
import org.anddev.andengine.physics.box2d.util.BidirectionalMap;
import org.anddev.andengine.util.Debug;

import com.akjava.lib.android.math.MathUtils;


/**
 * @author Nicolas Gramlich
 * @since 10:56:23 - 21.03.2010
 */
public class Box2DPhysicsSpace implements IPhysicsSpace {
	// ===========================================================
	// Constants
	// ===========================================================

//	private static final float STEPTIME = 1 / 60f;

	private static final int VELOCITYITERATIONS = 8;
	private static final int POSITIONITERATIONS = 1;

	// ===========================================================
	// Fields
	// ===========================================================

	private final Box2DNativeWrapper mBox2DNativeWrapper;

	private final BidirectionalMap<StaticPhysicsBody, Integer> mStaticPhysicsBodyToPhysicsIDMapping = new BidirectionalMap<StaticPhysicsBody, Integer>();

	private final ArrayList<DynamicPhysicsBody> mDynamicPhysicsBodies = new ArrayList<DynamicPhysicsBody>();
	private final BidirectionalMap<DynamicPhysicsBody, Integer> mDynamicPhysicsBodyToPhysicsIDMapping = new BidirectionalMap<DynamicPhysicsBody, Integer>();

	private final BodyInfo mBodyInfo = new BodyInfo();

	// ===========================================================
	// Constructors
	// ===========================================================

	public Box2DPhysicsSpace() {
		this.mBox2DNativeWrapper = new Box2DNativeWrapper();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void createWorld(final float pX, final float pY, final float pWidth, final float pHeight) {
		this.mBox2DNativeWrapper.createWorld(pX, pY, pX + pWidth, pY + pHeight, 0, 0);
	}

	@Override
	public void addDynamicEntity(final DynamicPhysicsBody pDynamicPhysicsBody) {
		assert(pDynamicPhysicsBody.mMass != 0);
		final DynamicEntity dynamicEntity = pDynamicPhysicsBody.getEntity(); 
		dynamicEntity.setUpdatePhysicsSelf(false);
		final int physicsID = this.mBox2DNativeWrapper.createBox2(dynamicEntity.getX(), dynamicEntity.getY(), dynamicEntity.getInitialWidth(), dynamicEntity.getInitialHeight(), pDynamicPhysicsBody.mMass, pDynamicPhysicsBody.mElasticity, pDynamicPhysicsBody.mFricition);
		this.mDynamicPhysicsBodies.add(pDynamicPhysicsBody);
		this.mDynamicPhysicsBodyToPhysicsIDMapping.put(pDynamicPhysicsBody, physicsID);		
	}

	@Override
	public void addStaticEntity(final StaticPhysicsBody pStaticPhysicsBody) {
		assert(pStaticPhysicsBody.mMass == 0);
		final StaticEntity staticEntity = pStaticPhysicsBody.getEntity();
		final int physicsID = this.mBox2DNativeWrapper.createBox2(staticEntity.getX(), staticEntity.getY(), staticEntity.getWidth(), staticEntity.getHeight(), 0, pStaticPhysicsBody.mElasticity, pStaticPhysicsBody.mFricition);
		this.mStaticPhysicsBodyToPhysicsIDMapping.put(pStaticPhysicsBody, physicsID);
	}

	@Override
	public void setVelocity(final DynamicPhysicsBody pDynamicPhysicsBody, final float pVelocityX, final float pVelocityY) {
		final int physicsID = this.mDynamicPhysicsBodyToPhysicsIDMapping.get(pDynamicPhysicsBody);
		this.mBox2DNativeWrapper.setBodyLinearVelocity(physicsID, pVelocityX, pVelocityY);
	}

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		Debug.d("Before step");
		this.mBox2DNativeWrapper.step(pSecondsElapsed, VELOCITYITERATIONS, POSITIONITERATIONS);
//		this.mBox2DNativeWrapper.step(STEPTIME, VELOCITYITERATIONS, POSITIONITERATIONS);
		Debug.d("After step");

		final BidirectionalMap<DynamicPhysicsBody, Integer> dynamicPhysicsBodyToPhysicsIDMapping = this.mDynamicPhysicsBodyToPhysicsIDMapping;
		final ArrayList<DynamicPhysicsBody> dynamicPhysicsBodies = this.mDynamicPhysicsBodies;
		final BodyInfo bodyInfo = this.mBodyInfo;

		for(int i = dynamicPhysicsBodies.size() - 1; i >= 0; i--) {
			final DynamicPhysicsBody dynamicPhysicsBody = dynamicPhysicsBodies.get(i);
			this.mBox2DNativeWrapper.getBodyInfo(bodyInfo, dynamicPhysicsBodyToPhysicsIDMapping.get(dynamicPhysicsBody));
			final DynamicEntity dynamicEntity = dynamicPhysicsBody.getEntity();
			
			dynamicEntity.setPosition(bodyInfo.getX() - dynamicEntity.getInitialWidth() / 2, bodyInfo.getY() - dynamicEntity.getInitialHeight() / 2);
			dynamicEntity.setAngle(MathUtils.radToDeg(bodyInfo.getAngle()));
		}

		// TODO Collisions
	}

	@Override
	public void setGravity(final float pGravityX, final float pGravityY) {
		this.mBox2DNativeWrapper.setGravity(pGravityX, pGravityY);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
