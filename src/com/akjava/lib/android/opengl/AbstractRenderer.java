package com.akjava.lib.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;

public abstract class AbstractRenderer implements GLSurfaceView.Renderer {

	protected Context context;

	public AbstractRenderer(Context context) {
		this.context = context;
	}

	public void onKeyDown(int keyCode, KeyEvent event) {

	}

	public abstract void actionUp();

	public abstract void actionDown();

	public abstract void actionRight();

	public abstract void actionLeft();

	public abstract void actionCenter();

	public abstract boolean onTouchDown(float x, float y);

	public abstract boolean onTouchUp(float x, float y);

	public abstract boolean onTouchMove(float x, float y);
}
