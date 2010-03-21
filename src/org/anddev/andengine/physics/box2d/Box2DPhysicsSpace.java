package org.anddev.andengine.physics.box2d;

import java.util.ArrayList;

import org.anddev.andengine.entity.DynamicEntity;
import org.anddev.andengine.entity.StaticEntity;
import org.anddev.andengine.physics.IPhysicsSpace;
import org.anddev.andengine.physics.PhysicsData;
import org.anddev.andengine.physics.box2d.util.BidirectionalMap;


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

	private final BidirectionalMap<StaticEntity, Integer> mStaticEntityToPhysicsIDMapping = new BidirectionalMap<StaticEntity, Integer>();

	private final ArrayList<DynamicEntity> mDynamicEntities = new ArrayList<DynamicEntity>();
	private final BidirectionalMap<DynamicEntity, Integer> mDynamicEntityToPhysicsIDMapping = new BidirectionalMap<DynamicEntity, Integer>();

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
	public void addDynamicEntity(final DynamicEntity pDynamicEntity, final PhysicsData pPhysicsData) {
		assert(pPhysicsData.mMass != 0);
		pDynamicEntity.setUpdatePhysicsSelf(false);
		final int physicsID = this.mBox2DNativeWrapper.createBox2(pDynamicEntity.getX(), pDynamicEntity.getY(), pDynamicEntity.getWidth(), pDynamicEntity.getHeight(), pPhysicsData.mMass, pPhysicsData.mElasticity, pPhysicsData.mFricition);
		this.mDynamicEntities.add(pDynamicEntity);
		this.mDynamicEntityToPhysicsIDMapping.put(pDynamicEntity, physicsID);
	}

	@Override
	public void addStaticEntity(final StaticEntity pStaticEntity, final PhysicsData pPhysicsData) {
		assert(pPhysicsData.mMass == 0);
		final int physicsID = this.mBox2DNativeWrapper.createBox2(pStaticEntity.getX(), pStaticEntity.getY(), pStaticEntity.getWidth(), pStaticEntity.getHeight(), pPhysicsData.mMass, pPhysicsData.mElasticity, pPhysicsData.mFricition);
		this.mStaticEntityToPhysicsIDMapping.put(pStaticEntity, physicsID);
	}

	@Override
	public void setVelocity(final DynamicEntity pDynamicEntity, final float pVelocityX, final float pVelocityY) {
		final int physicsID = this.mDynamicEntityToPhysicsIDMapping.get(pDynamicEntity);
		this.mBox2DNativeWrapper.setBodyLinearVelocity(physicsID, pVelocityX, pVelocityY);
	}

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		this.mBox2DNativeWrapper.step(pSecondsElapsed, VELOCITYITERATIONS, POSITIONITERATIONS);

		final BidirectionalMap<DynamicEntity, Integer> dynamicEntityToPhysicsIDMapping = this.mDynamicEntityToPhysicsIDMapping;
		final ArrayList<DynamicEntity> dynamicEntities = this.mDynamicEntities;
		final BodyInfo bodyInfo = this.mBodyInfo;

		for(int i = dynamicEntities.size() - 1; i >= 0; i--) {
			final DynamicEntity dynamicEntity = dynamicEntities.get(i);
			this.mBox2DNativeWrapper.getBodyInfo(bodyInfo, dynamicEntityToPhysicsIDMapping.get(dynamicEntity));
			dynamicEntity.setPosition(bodyInfo.getX(), bodyInfo.getY());
			dynamicEntity.setAngle(bodyInfo.getAngle());
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
