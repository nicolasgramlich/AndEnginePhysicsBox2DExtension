/*
 * Copyright (C) 2008 aki@akjava.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.akjava.lib.android.opengl;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.KeyEvent;

import com.akjava.lib.android.opengl.GLView.EventListener;

/**
 * @deprecated
 * @author aki
 * 
 */
public abstract class AbstractSimpleRenderer implements GLView.Renderer, EventListener {

	public AbstractSimpleRenderer(Context context) {
		mContext = context;
		initializeCordinates();
	}

	public int[] getConfigSpec() {

		int[] configSpec = { EGL10.EGL_RED_SIZE, 5, EGL10.EGL_GREEN_SIZE, 6, EGL10.EGL_BLUE_SIZE, 5, EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE };
		return configSpec;
	}

	public abstract void init(GL10 gl);

	public void surfaceCreated(GL10 gl) {
		init(gl);
	}

	public void drawFrame(final GL10 gl) {

		display(gl);
	}

	public abstract void sizeChanged(GL10 gl, int w, int h);

	public Context getContext() {
		return mContext;
	}

	protected Context mContext;

	public abstract void initializeCordinates();

	public abstract void display(final GL10 gl);

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			actionUp();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			actionDown();
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			actionRight();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			actionLeft();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			actionCenter();
		}
		return true;
	}

	public abstract void actionUp();

	public abstract void actionDown();

	public abstract void actionRight();

	public abstract void actionLeft();

	public abstract void actionCenter();

	public boolean onTouchDown(float x, float y) {
		return true;
	}

	public boolean onTouchUp(float x, float y) {
		return true;
	}

	public boolean onTouchMove(float x, float y) {
		return true;
	}

}
