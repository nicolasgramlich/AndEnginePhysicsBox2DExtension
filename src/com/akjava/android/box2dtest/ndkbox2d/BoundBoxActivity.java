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

package com.akjava.android.box2dtest.ndkbox2d;

import org.anddev.andengine.physics.box2d.NDKBox2dControler;

import android.app.Activity;
import android.os.Bundle;

import com.akjava.android.box2d.Box2dControler;
import com.akjava.android.box2dtest.BoundBoxRenderer;
import com.akjava.lib.android.opengl.AbstractGLSurfaceView;
import com.akjava.lib.android.opengl.SimpleGameGLSurfaceView;

public class BoundBoxActivity extends Activity {

	/**
	 * Set to true to enable checking of the OpenGL error code after every
	 * OpenGL call. Set to false for faster code.
	 * 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("ndk bound box(center key add ball)");

		mGLView = new SimpleGameGLSurfaceView(this);
		Box2dControler controler = new NDKBox2dControler();
		setContentView(mGLView);

		BoundBoxRenderer render = new BoundBoxRenderer(this, controler);
		mGLView.setAbstractRender(render);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();

	}

	private AbstractGLSurfaceView mGLView;
}
