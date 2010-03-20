package com.akjava.android.box2dtest;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;

import com.akjava.android.box2d.Box2dControler;
import com.akjava.lib.android.opengl.AbstractRenderer;
import com.akjava.lib.android.opengl.ImageStringDrawer;

public abstract class AbstractBox2dTestRender extends AbstractRenderer {

	protected long lastStep;

	protected float stepTime = 1.0f / 15f;

	protected Box2dControler box2dControler;

	public AbstractBox2dTestRender(Context context, Box2dControler box2dControler) {
		super(context);
		this.box2dControler = box2dControler;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, 320, 480, 0);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	protected ImageStringDrawer stringDrawer;
	protected int imageFontImageId;
	protected long current;

	protected int target = (int) (1000 / 60.0f);
	protected float frameAverage = target;
	protected long lastFrame = System.currentTimeMillis();
	protected int fps;

	public class BlockInfo {
		public int half_width;
		public int half_height;
		public int id;

		public BlockInfo(int id, int half_width, int half_height) {
			this.id = id;
			this.half_width = half_width;
			this.half_height = half_height;
		}
	}

	public void fillArrayBox(final GL10 gl, final float centerX, final float centerY, final float width, final float height, final float degAngle) {

		gl.glLoadIdentity();

		gl.glTranslatef(centerX, centerY, 1);
		gl.glRotatef(degAngle, 0, 0, 1);
		gl.glScalef(width, height, 1.0f);

		// gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
		// OpenGLUtils.getBoxVertexBuffer());

		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
	}

	@Override
	public void actionCenter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchDown(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchMove(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchUp(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}
}
