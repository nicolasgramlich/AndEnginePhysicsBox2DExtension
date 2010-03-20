package com.akjava.android.box2d;

public class BodyInfo {
	public float x;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float y;
	public float angle;// rad

	public void setValues(float x, float y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	public boolean bullet;
	public boolean sleeping;
	public boolean frozen;
	public boolean dynamic;
	public boolean Static;

	public boolean isBullet() {
		return bullet;
	}

	public void setBullet(boolean bullet) {
		this.bullet = bullet;
	}

	public boolean isSleeping() {
		return sleeping;
	}

	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public boolean isStatic() {
		return Static;
	}

	public void setStatic(boolean static1) {
		Static = static1;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public float linearX;
	public float linearY;

	public void setLinerVelocity(float x, float y) {
		linearX = x;
		linearY = y;
	}

	public void setStatus(boolean b, boolean s, boolean f, boolean d, boolean st) {
		this.bullet = b;
		this.sleeping = s;
		this.frozen = f;
		this.dynamic = d;
		this.Static = st;
	}
}
