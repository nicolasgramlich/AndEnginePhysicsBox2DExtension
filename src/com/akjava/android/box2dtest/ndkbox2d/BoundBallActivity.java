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

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.akjava.android.box2d.Box2dControler;
import com.akjava.android.box2d.NDKBox2dControler;
import com.akjava.android.box2dtest.AbstractBox2dTestRender;
import com.akjava.android.box2dtest.BoundBallRenderer;

public class BoundBallActivity extends Activity {

	/**
	 * Set to true to enable checking of the OpenGL error code after every
	 * OpenGL call. Set to false for faster code.
	 * 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("ndk bound ball");

		glView = new GLSurfaceView(this);
		setContentView(glView);

		Box2dControler controler = new NDKBox2dControler();
		AbstractBox2dTestRender render = new BoundBallRenderer(this, controler);
		glView.setRenderer(render);

	}

	@Override
	protected void onPause() {
		super.onPause();
		glView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		glView.onResume();
	}

	private GLSurfaceView glView;
}
