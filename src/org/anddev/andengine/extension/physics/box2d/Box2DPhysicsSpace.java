package org.anddev.andengine.extension.physics.box2d;

import java.util.ArrayList;

import org.anddev.andengine.entity.IUpdateHandler;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.extension.physics.box2d.adt.DynamicPhysicsBody;
import org.anddev.andengine.extension.physics.box2d.adt.StaticPhysicsBody;
import org.anddev.andengine.extension.physics.box2d.util.BidirectionalMap;
import org.anddev.andengine.extension.physics.box2d.util.Box2DJNIProxyContactListener;
import org.anddev.andengine.util.MathUtils;

import android.os.Handler;
import android.os.Message;

/**
 * @author Nicolas Gramlich
 * @since 10:56:23 - 21.03.2010
 */
public class Box2DPhysicsSpace implements IUpdateHandler, Box2DContactListener {
	// ===========================================================
	// Constants
	// ===========================================================

	//	private static final float STEPTIME = 1 / 60f;

	private static final int ITERATIONS = 8;

	private static final int WHAT_CONTACT_NEW = 1;

	// ===========================================================
	// Fields
	// ===========================================================

	private final Box2DNativeWrapper mBox2DNativeWrapper;

	private final ArrayList<StaticPhysicsBody> mStaticPhysicsBodies = new ArrayList<StaticPhysicsBody>();
	private final BidirectionalMap<StaticPhysicsBody, Integer> mStaticPhysicsBodyToPhysicsIDMapping = new BidirectionalMap<StaticPhysicsBody, Integer>();

	private final ArrayList<StaticPhysicsBody> mStaticPhysicsBodiesToBeLoaded = new ArrayList<StaticPhysicsBody>();
	private final ArrayList<DynamicPhysicsBody> mDynamicPhysicsBodiesToBeLoaded = new ArrayList<DynamicPhysicsBody>();

	private final ArrayList<DynamicPhysicsBody> mDynamicPhysicsBodies = new ArrayList<DynamicPhysicsBody>();
	private final BidirectionalMap<DynamicPhysicsBody, Integer> mDynamicPhysicsBodyToPhysicsIDMapping = new BidirectionalMap<DynamicPhysicsBody, Integer>();

	private final Box2DBodyInfo mBodyInfo = new Box2DBodyInfo();

	private final Handler mHandler;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Box2DPhysicsSpace() {
		this.mBox2DNativeWrapper = new Box2DNativeWrapper();
		this.mHandler = new Handler(){
			@Override
			public void handleMessage(final Message pMsg) {
				if(pMsg.what == WHAT_CONTACT_NEW) {
					Box2DPhysicsSpace.this.onHandleCollision(pMsg.arg1, pMsg.arg2);
				}
				super.handleMessage(pMsg);
			}
		};
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void reset() {
		// TODO ???
	}

	@Override
	public void onNewContact(final int pPhysicsIDA, final int pPhysicsIDB) {
		this.mHandler.sendMessage(this.mHandler.obtainMessage(WHAT_CONTACT_NEW, pPhysicsIDA, pPhysicsIDB));
	}

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		//		this.ensureBodiesUnloaded(); // TODO
		this.ensureBodiesLoaded();

		final Box2DNativeWrapper box2DNativeWrapper = this.mBox2DNativeWrapper;
		box2DNativeWrapper.step(new Box2DJNIProxyContactListener(this), pSecondsElapsed, ITERATIONS);

		final BidirectionalMap<DynamicPhysicsBody, Integer> dynamicPhysicsBodyToPhysicsIDMapping = this.mDynamicPhysicsBodyToPhysicsIDMapping;
		final ArrayList<DynamicPhysicsBody> dynamicPhysicsBodies = this.mDynamicPhysicsBodies;
		final Box2DBodyInfo bodyInfo = this.mBodyInfo;

		for(int i = dynamicPhysicsBodies.size() - 1; i >= 0; i--) {
			final DynamicPhysicsBody dynamicPhysicsBody = dynamicPhysicsBodies.get(i);
			box2DNativeWrapper.getBodyInfo(bodyInfo, dynamicPhysicsBodyToPhysicsIDMapping.get(dynamicPhysicsBody));
			final Shape shape = dynamicPhysicsBody.getShape();

			shape.setPosition(bodyInfo.getX() - shape.getBaseWidth() * 0.5f, bodyInfo.getY() - shape.getBaseHeight() * 0.5f);
			shape.setRotation(MathUtils.radToDeg(bodyInfo.getRotation()));
			shape.setVelocity(bodyInfo.getVelocityX(), bodyInfo.getVelocityY());
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void createWorld(final float pX, final float pY, final float pWidth, final float pHeight) {
		this.mBox2DNativeWrapper.createWorld(pX, pY, pX + pWidth, pY + pHeight, 0, 0);
	}

	public void addDynamicBody(final DynamicPhysicsBody pDynamicPhysicsBody) {
		assert(pDynamicPhysicsBody.mMass != 0);

		this.mDynamicPhysicsBodiesToBeLoaded.add(pDynamicPhysicsBody);
	}

	public void removeDynamicBody(final DynamicPhysicsBody pDynamicPhysicsBody) {
		if(this.mDynamicPhysicsBodies.contains(pDynamicPhysicsBody)) {
			this.mDynamicPhysicsBodies.remove(pDynamicPhysicsBody);
			final int physicsID = this.mDynamicPhysicsBodyToPhysicsIDMapping.remove(pDynamicPhysicsBody);
			this.mBox2DNativeWrapper.destroyBody(physicsID);
		} else if(this.mDynamicPhysicsBodiesToBeLoaded.contains(pDynamicPhysicsBody)) {
			this.mDynamicPhysicsBodiesToBeLoaded.remove(pDynamicPhysicsBody);
		}
	}

	public void removeStaticBody(final StaticPhysicsBody pStaticPhysicsBody) {
		if(this.mStaticPhysicsBodies.contains(pStaticPhysicsBody)) {
			this.mStaticPhysicsBodies.remove(pStaticPhysicsBody);
			final int physicsID = this.mStaticPhysicsBodyToPhysicsIDMapping.remove(pStaticPhysicsBody);
			this.mBox2DNativeWrapper.destroyBody(physicsID);
		} else if(this.mStaticPhysicsBodiesToBeLoaded.contains(pStaticPhysicsBody)) {
			this.mStaticPhysicsBodiesToBeLoaded.remove(pStaticPhysicsBody);
		}
	}

	public DynamicPhysicsBody findDynamicBodyByShape(final Shape pShape) {
		// TODO Also check this.mDynamicPhysicsBodiesToBeLoaded if it contains this object

		// TODO Instead of a linear search the lookup could be done through a HashMap
		final ArrayList<DynamicPhysicsBody> dynamicPhysicsBodies = this.mDynamicPhysicsBodies;
		for(int i = dynamicPhysicsBodies.size() - 1; i >= 0; i--) {
			final DynamicPhysicsBody dynamicPhysicsBody = dynamicPhysicsBodies.get(i);
			if(dynamicPhysicsBody.mShape == pShape){
				return dynamicPhysicsBody;
			}
		}
		return null;
	}

	public StaticPhysicsBody findStaticBodyByShape(final Shape pShape) {
		// TODO Also check this.mStaticPhysicsBodiesToBeLoaded if it contains this object

		// TODO Instead of a linear search the lookup could be done through a HashMap
		final ArrayList<StaticPhysicsBody> staticPhysicsBodies = this.mStaticPhysicsBodies;
		for(int i = staticPhysicsBodies.size() - 1; i >= 0; i--) {
			final StaticPhysicsBody staticPhysicsBody = staticPhysicsBodies.get(i);
			if(staticPhysicsBody.mShape == pShape){
				return staticPhysicsBody;
			}
		}
		return null;
	}

	public void addStaticBody(final StaticPhysicsBody pStaticPhysicsBody) {
		assert(pStaticPhysicsBody.mMass == 0);

		this.mStaticPhysicsBodiesToBeLoaded.add(pStaticPhysicsBody);
	}

	public void setVelocity(final DynamicPhysicsBody pDynamicPhysicsBody, final float pVelocityX, final float pVelocityY) {
		final int physicsID = this.mDynamicPhysicsBodyToPhysicsIDMapping.get(pDynamicPhysicsBody);
		this.mBox2DNativeWrapper.setBodyLinearVelocity(physicsID, pVelocityX, pVelocityY);
	}

	public void setGravity(final float pGravityX, final float pGravityY) {
		this.mBox2DNativeWrapper.setGravity(pGravityX, pGravityY);
	}

	private void onHandleCollision(final int pPhysicsIDA, final int pPhysicsIDB) {
		final DynamicPhysicsBody dynamicPhysicsBodyA = this.mDynamicPhysicsBodyToPhysicsIDMapping.getKeyByValue(pPhysicsIDA);
		final DynamicPhysicsBody dynamicPhysicsBodyB = this.mDynamicPhysicsBodyToPhysicsIDMapping.getKeyByValue(pPhysicsIDB);

		if(dynamicPhysicsBodyA != null && dynamicPhysicsBodyB != null){
			if(dynamicPhysicsBodyA.hasCollisionCallback()){
				dynamicPhysicsBodyA.getCollisionCallback().onCollision(dynamicPhysicsBodyA.getShape(), dynamicPhysicsBodyB.getShape());
			}

			if(dynamicPhysicsBodyB.hasCollisionCallback()){
				dynamicPhysicsBodyB.getCollisionCallback().onCollision(dynamicPhysicsBodyB.getShape(), dynamicPhysicsBodyA.getShape());
			}
		} else if(dynamicPhysicsBodyA != null) {
			if(dynamicPhysicsBodyA.hasCollisionCallback()){
				final StaticPhysicsBody staticPhysicsBodyB = this.mStaticPhysicsBodyToPhysicsIDMapping.getKeyByValue(pPhysicsIDB);
				if(staticPhysicsBodyB != null) {
					dynamicPhysicsBodyA.getCollisionCallback().onCollision(dynamicPhysicsBodyA.getShape(), staticPhysicsBodyB.getShape());
				}
			}
		} else if(dynamicPhysicsBodyB != null) {
			if(dynamicPhysicsBodyB.hasCollisionCallback()){
				final StaticPhysicsBody staticPhysicsBodyA = this.mStaticPhysicsBodyToPhysicsIDMapping.getKeyByValue(pPhysicsIDA);
				if(staticPhysicsBodyA != null) {
					dynamicPhysicsBodyB.getCollisionCallback().onCollision(dynamicPhysicsBodyB.getShape(), staticPhysicsBodyA.getShape());
				}
			}
		}
	}

	private void ensureBodiesLoaded() {
		this.ensureStaticBodiesLoaded();
		this.ensureDynamicBodiesLoaded();
	}

	private void ensureStaticBodiesLoaded() {
		final ArrayList<StaticPhysicsBody> staticPhysicsBodiesToBeLoaded = this.mStaticPhysicsBodiesToBeLoaded;
		for(int i = staticPhysicsBodiesToBeLoaded.size() - 1; i >= 0; i--) {
			this.loadPendingStaticBody(staticPhysicsBodiesToBeLoaded.get(i));
		}
		staticPhysicsBodiesToBeLoaded.clear();
	}

	private void loadPendingStaticBody(final StaticPhysicsBody pStaticPhysicsBody) {
		final Shape shape = pStaticPhysicsBody.getShape();

		final int physicsID = this.loadStaticShape(pStaticPhysicsBody, shape);
		this.mStaticPhysicsBodyToPhysicsIDMapping.put(pStaticPhysicsBody, physicsID);
	}

	private int loadStaticShape(final StaticPhysicsBody pStaticPhysicsBody, final Shape pShape) {
		switch(pStaticPhysicsBody.mPhysicsShape) {
			case CIRCLE:
				return this.loadStaticCircle(pStaticPhysicsBody, pShape);
			case RECTANGLE:
				return this.loadStaticBox(pStaticPhysicsBody, pShape);
			default:
				throw new IllegalArgumentException("Invalid PhysicsShape supplied.");
		}
	}

	private int loadStaticCircle(final StaticPhysicsBody pStaticPhysicsBody, final Shape pShape) {
		final float width = pShape.getBaseWidth();
		final float height = pShape.getBaseHeight();

		if(width != height) {
			throw new IllegalArgumentException("The width and height of the pShape must be equal.");
		}

		final float rotation = MathUtils.degToRad(pShape.getRotation());
		final float velocityX = pShape.getVelocityX();
		final float velocityY = pShape.getVelocityY();

		final float radius = width * 0.5f;
		final float x = pShape.getX() + radius;
		final float y = pShape.getY() + radius;

		return this.mBox2DNativeWrapper.createCircle(x, y, radius, rotation, velocityX, velocityY, 0, pStaticPhysicsBody.mElasticity, pStaticPhysicsBody.mFricition, false, false);
	}

	private int loadStaticBox(final StaticPhysicsBody pStaticPhysicsBody, final Shape pShape) {
		final float width = pShape.getBaseWidth();
		final float height = pShape.getBaseHeight();
		final float rotation = MathUtils.degToRad(pShape.getRotation());
		final float velocityX = pShape.getVelocityX();
		final float velocityY = pShape.getVelocityY();
		final float x = pShape.getX() + width * 0.5f;
		final float y = pShape.getY() + height * 0.5f;

		return this.mBox2DNativeWrapper.createBox(x, y, width, height, rotation, velocityX, velocityY, 0, pStaticPhysicsBody.mElasticity, pStaticPhysicsBody.mFricition, false, false);
	}

	private void ensureDynamicBodiesLoaded() {
		final ArrayList<DynamicPhysicsBody> dynamicPhysicsBodiesToBeLoaded = this.mDynamicPhysicsBodiesToBeLoaded;
		for(int i = dynamicPhysicsBodiesToBeLoaded.size() - 1; i >= 0; i--) {
			this.loadDynamicBody(dynamicPhysicsBodiesToBeLoaded.get(i));
		}
		dynamicPhysicsBodiesToBeLoaded.clear();
	}

	private void loadDynamicBody(final DynamicPhysicsBody pDynamicPhysicsBody) {
		final Shape shape = pDynamicPhysicsBody.getShape();
		shape.setUpdatePhysicsSelf(false);

		final int physicsID = this.loadDynamicShape(pDynamicPhysicsBody, shape);
		this.mDynamicPhysicsBodies.add(pDynamicPhysicsBody);
		this.mDynamicPhysicsBodyToPhysicsIDMapping.put(pDynamicPhysicsBody, physicsID);
	}

	private int loadDynamicShape(final DynamicPhysicsBody pDynamicPhysicsBody, final Shape pShape) {
		switch(pDynamicPhysicsBody.mPhysicsShape) {
			case CIRCLE:
				return this.loadDynamicCircle(pDynamicPhysicsBody, pShape);
			case RECTANGLE:
				return this.loadDynamicBox(pDynamicPhysicsBody, pShape);
			default:
				throw new IllegalArgumentException("Invalid PhysicsShape supplied.");
		}
	}

	private int loadDynamicCircle(final DynamicPhysicsBody pDynamicPhysicsBody, final Shape pShape) {
		final float width = pShape.getBaseWidth();
		final float height = pShape.getBaseHeight();

		if(width != height) {
			throw new IllegalArgumentException("The width and height of the pShape must be equal.");
		}

		final float rotation = MathUtils.degToRad(pShape.getRotation());
		final float velocityX = pShape.getVelocityX();
		final float velocityY = pShape.getVelocityY();

		final float radius = width * 0.5f;
		final float x = pShape.getX() + radius;
		final float y = pShape.getY() + radius;

		return this.mBox2DNativeWrapper.createCircle(x, y, radius, rotation, velocityX, velocityY, pDynamicPhysicsBody.mMass, pDynamicPhysicsBody.mElasticity, pDynamicPhysicsBody.mFricition, pDynamicPhysicsBody.isFixedRotation(), pDynamicPhysicsBody.hasCollisionCallback());
	}

	private int loadDynamicBox(final DynamicPhysicsBody pDynamicPhysicsBody, final Shape pShape) {
		final float width = pShape.getBaseWidth();
		final float height = pShape.getBaseHeight();
		final float rotation = MathUtils.degToRad(pShape.getRotation());
		final float velocityX = pShape.getVelocityX();
		final float velocityY = pShape.getVelocityY();
		final float x = pShape.getX() + width * 0.5f;
		final float y = pShape.getY() + height * 0.5f;

		return this.mBox2DNativeWrapper.createBox(x, y, width, height, rotation, velocityX, velocityY, pDynamicPhysicsBody.mMass, pDynamicPhysicsBody.mElasticity, pDynamicPhysicsBody.mFricition, pDynamicPhysicsBody.isFixedRotation(), pDynamicPhysicsBody.hasCollisionCallback());
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
