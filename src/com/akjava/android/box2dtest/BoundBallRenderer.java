package com.akjava.android.box2dtest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.physics.box2d.BodyInfo;
import org.anddev.andengine.physics.box2d.Box2DNativeWrapper;

import android.content.Context;

import com.akjava.lib.android.math.MathUtils;
import com.akjava.lib.android.opengl.ImageStringDrawer;
import com.akjava.lib.android.opengl.OpenGLUtils;

public class BoundBallRenderer extends AbstractBox2dTestRender {

	public BoundBallRenderer(Context context, Box2DNativeWrapper box2dControler) {
		super(context, box2dControler);
	}

	private boolean debug_showFps = true;

	/*
	 * @Override public void actionCenter() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Override public void actionDown() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Override public void actionLeft() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Override public void actionRight() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Override public void actionUp() { // TODO Auto-generated method stub
	 * 
	 * }
	 */

	private static final int WORLD_SCALE = 10;// what?

	BodyInfo info = new BodyInfo();

	@Override
	public void onDrawFrame(GL10 gl) {

		current = System.currentTimeMillis();

		frameAverage = (frameAverage * 10 + (current - lastFrame)) / 11;
		lastFrame = current;
		fps = (int) (1000 / frameAverage);

		gl.glLoadIdentity();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, ballTextureId);
		// gl.glDisable(GL10.GL_TEXTURE_2D);//disable textur
		gl.glColor4f(1f, 0.7f, 0.7f, 1f);

		box2dControler.getBodyInfo(info, 1);
		// TODO change circle size
		fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, 10, 10, MathUtils.radToDeg(info.getAngle()));

		gl.glBindTexture(GL10.GL_TEXTURE_2D, blockTextureId);
		gl.glColor4f(1f, 1, 1, 1);

		box2dControler.getBodyInfo(info, 0);

		fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, 32 / 2 * WORLD_SCALE, 6 / 2 * WORLD_SCALE, MathUtils.radToDeg(info.getAngle()));

		if (debug_showFps) { // object
			gl.glBindTexture(GL10.GL_TEXTURE_2D, imageFontImageId);
			// gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glLoadIdentity();
			gl.glColor4f(1f, 1f, 1f, 1);

			// stringDrawer.drawString(gl, toFPSBuffer.toFPSBuffer(fps), 16, 16,
			// 16);
			stringDrawer.drawFpsString(gl, fps, 16, 16, 16, 0.5f);
		}

		long before = System.currentTimeMillis();
		// Log.i("app",""+before+","+(lastStep+target));
		long beforeStep = before - current;
		// System.out.println(current+","+(lastStep+target));
		if (before > lastStep + target) {
			// step

			box2dControler.step(stepTime, 8);
			long end = System.currentTimeMillis();
			long endStep = end - before;
			// Log.i("app","stepped:"+beforeStep+","+endStep);
			lastStep = end;
		} else {
			// Log.i("app","-skipped");
		}
	}

	int ballTextureId;
	int blockTextureId;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glAlphaFunc(GL10.GL_GEQUAL, 0.5f);// important,it remove edge and
		// speed fast,but quality become
		// low

		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);

		gl.glShadeModel(GL10.GL_FLAT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);// alpha

		gl.glEnable(GL10.GL_ALPHA_TEST);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.getBoxTriangleVertexBuffer());
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, OpenGLUtils.getBoxTriangleTextureBuffer());

		int[] textures = new int[6];
		gl.glGenTextures(6, textures, 0);

		imageFontImageId = textures[0];
		ballTextureId = textures[1];
		blockTextureId = textures[2];
		OpenGLUtils.bindTextureImage(gl, imageFontImageId, OpenGLUtils.loadBitmap(context, R.drawable.font_image_border2));
		OpenGLUtils.bindTextureImage(gl, ballTextureId, OpenGLUtils.loadBitmap(context, R.drawable.ball));
		OpenGLUtils.bindTextureImage(gl, blockTextureId, OpenGLUtils.loadBitmap(context, R.drawable.wall));

		stringDrawer = new ImageStringDrawer(imageFontImageId, 32);

		box2dControler.createWorld(0, 0, 32, 60, 0, 10);
		int downblock = box2dControler.createBox(0, 46, 32, 6);
		// Log.i("app","block:"+downblock);
		int ball = box2dControler.createCircle(16, 20, 1, 10, 1);
		// Log.i("app","ball:"+ball);

	}

}
