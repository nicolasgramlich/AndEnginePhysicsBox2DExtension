package com.akjava.android.box2dtest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;

import com.akjava.android.box2d.BodyInfo;
import com.akjava.android.box2d.Box2dControler;
import com.akjava.lib.android.math.MathUtils;
import com.akjava.lib.android.opengl.ImageStringDrawer;
import com.akjava.lib.android.opengl.OpenGLUtils;

public class MoveBoxRenderer extends AbstractBox2dTestRender {

	private int gravityX = 0;
	private int gravityY = 10;

	public MoveBoxRenderer(Context context, Box2dControler box2dControler) {
		super(context, box2dControler);

	}

	// TODO sometime error hapeend.if call it still processing step()
	public void moveItem(int x, int y) {
		box2dControler.getBodyInfo(info, moveId);
		box2dControler.setBodyXForm(moveId, info.getX() + x * 2, info.getY() + y * 2, 0);
	}

	int lastIndex;
	int moveId;

	@Override
	public void actionCenter() {
		// move forward
		for (int i = 5; i <= lastIndex; i++) {
			box2dControler.setBodyLinearVelocity(i, MathUtils.random(-20, 20), -50);
		}
	}

	@Override
	public void actionDown() {
		moveItem(0, 1);
	}

	@Override
	public void actionLeft() {
		moveItem(-1, 0);
	}

	@Override
	public void actionRight() {
		moveItem(1, 0);
	}

	@Override
	public void actionUp() {
		moveItem(0, -1);
	}

	BodyInfo info = new BodyInfo();

	boolean debug_showFps = true;

	@Override
	public void onDrawFrame(GL10 gl) {
		current = System.currentTimeMillis();

		frameAverage = (frameAverage * 10 + (current - lastFrame)) / 11;
		lastFrame = current;
		fps = (int) (1000 / frameAverage);

		gl.glLoadIdentity();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, ballTextureId);
		// gl.glDisable(GL10.GL_TEXTURE_2D);//disable texture not work on my
		// ADP1
		gl.glColor4f(1f, 0.7f, 0.7f, 1f);

		// draw balls

		for (int i = 5; i <= lastIndex; i++) {
			box2dControler.getBodyInfo(info, i);
			fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, 10, 10, MathUtils.radToDeg(info.getAngle()));
		}

		// draw wall
		gl.glBindTexture(GL10.GL_TEXTURE_2D, blockTextureId);
		gl.glColor4f(1f, 1, 1, 1);

		box2dControler.getBodyInfo(info, downBlock.id);
		fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, downBlock.half_width * WORLD_SCALE, downBlock.half_height * WORLD_SCALE, MathUtils.radToDeg(info.getAngle()));

		box2dControler.getBodyInfo(info, upBlock.id);
		fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, upBlock.half_width * WORLD_SCALE, upBlock.half_height * WORLD_SCALE, MathUtils.radToDeg(info.getAngle()));

		box2dControler.getBodyInfo(info, rightBlock.id);
		fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, rightBlock.half_width * WORLD_SCALE, rightBlock.half_height * WORLD_SCALE, MathUtils.radToDeg(info.getAngle()));

		box2dControler.getBodyInfo(info, leftBlock.id);
		fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, leftBlock.half_width * WORLD_SCALE, leftBlock.half_height * WORLD_SCALE, MathUtils.radToDeg(info.getAngle()));

		// move item
		box2dControler.getBodyInfo(info, moveId);
		fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, 2 * WORLD_SCALE, 2 * WORLD_SCALE, MathUtils.radToDeg(info.getAngle()));

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

			box2dControler.step(stepTime, 8, 2);
			long end = System.currentTimeMillis();
			long endStep = end - before;
			// Log.i("app","stepped:"+beforeStep+","+endStep);
			lastStep = end;
		} else {
			// Log.i("app","-skipped");
		}

		// draw wall

		// draw ball

		// draw Gravity
		gl.glLoadIdentity();

		stringDrawer.drawString(gl, new StringBuffer("Gravity:" + gravityX + "," + gravityY), 16, 40, 16);
		// stringDrawer.drawString(gl, new StringBuffer("Ball:"+(lastIndex-3)),
		// 16, 60, 16);

	}

	int WORLD_SCALE = 10;

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, 320, 480, 0);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	private ImageStringDrawer stringDrawer;

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

		initializeBox2d();

	}

	public void initializeBox2d() {

		box2dControler.createWorld(0, 0, 32, 60, gravityX, gravityY);
		int down = box2dControler.createBox(0, 46, 32, 6);
		downBlock = new BlockInfo(down, 16, 3);
		int up = box2dControler.createBox(0, -4, 32, 6);
		upBlock = new BlockInfo(up, 16, 3);
		int right = box2dControler.createBox(-4, 0, 6, 46);
		rightBlock = new BlockInfo(right, 3, 23);
		int left = box2dControler.createBox(30, 0, 6, 46);
		leftBlock = new BlockInfo(left, 3, 23);

		moveId = box2dControler.createBox2(16, 20, 2 * 2, 2 * 2, 5f, 1f, 0.3f);

		for (int i = 0; i < 10; i++) {
			lastIndex = box2dControler.createBox2(MathUtils.random(1, 31), MathUtils.random(1, 20), 1, 1, 1f, 1, 1);

			box2dControler.setBodyAngularVelocity(lastIndex, 0.5f);
		}

	}

	public BlockInfo downBlock, upBlock, leftBlock, rightBlock;

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
