package com.akjava.android.box2dtest;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.physics.box2d.BodyInfo;
import org.anddev.andengine.physics.box2d.Box2DNativeWrapper;
import org.anddev.andengine.physics.box2d.util.collisionIdKeeper;

import android.content.Context;
import android.opengl.GLU;

import com.akjava.lib.android.math.MathUtils;
import com.akjava.lib.android.opengl.ImageStringDrawer;
import com.akjava.lib.android.opengl.OpenGLUtils;

public class BlockBreakRenderer extends AbstractBox2dTestRender {

	public BlockBreakRenderer(Context context, Box2DNativeWrapper box2dControler) {
		super(context, box2dControler);
	}

	private ArrayList paintQueue;

	@Override
	public void actionCenter() {

	}

	public void addGravity(int x, int y) {
		gravityX += x;
		gravityY += y;
		box2dControler.setGravity(gravityX, gravityY);
	}

	@Override
	public void actionDown() {
		addGravity(0, 1);
	}

	@Override
	public void actionLeft() {
		addGravity(-1, 0);
	}

	@Override
	public void actionRight() {
		addGravity(1, 0);
	}

	@Override
	public void actionUp() {
		addGravity(0, -1);
	}

	private boolean debug_showFps = true;

	BodyInfo info = new BodyInfo();
	int lastIndex;
	private ImageStringDrawer stringDrawer;

	int ballTextureId;
	int blockTextureId;

	public BlockInfo downBlock, upBlock, leftBlock, rightBlock;

	public void onDrawFrame(GL10 gl) {
		current = System.currentTimeMillis();

		frameAverage = (frameAverage * 10 + (current - lastFrame)) / 11;
		lastFrame = current;
		fps = (int) (1000 / frameAverage);

		gl.glLoadIdentity();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, ballTextureId);
		gl.glColor4f(1f, 0.7f, 0.7f, 1f);
		box2dControler.getBodyInfo(info, 0);// this case ball is 0
		fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, 10, 10, MathUtils.radToDeg(info.getAngle()));

		// draw balls

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

		// blocks.
		for (int i = 5; i <= lastIndex; i++) {
			box2dControler.getBodyInfo(info, i);
			if (info != null) {
				fillArrayBox(gl, info.getX() * WORLD_SCALE, info.getY() * WORLD_SCALE, 12, 8, MathUtils.radToDeg(info.getAngle()));
			} else {
				// Log.i("app","null");
			}

		}

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

		// action contacts.

		box2dControler.getCollisions(keeper, 0);

		for (int i = 0; i < keeper.contactIds.length; i++) {

			int blockId = keeper.contactIds[i];
			if (blockId == -1) {
				break;// no more
			}
			if (blockId > 4) {
				// destroy
				box2dControler.destroyBody(blockId);

			}
		}

		keeper.clear();
	}

	int WORLD_SCALE = 10;

	private collisionIdKeeper keeper = new collisionIdKeeper(100);

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, 320, 480, 0);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	/*
	 * private ArrayList<AbstractParticle> shouldRemove=new
	 * ArrayList<AbstractParticle>(); private ArrayList<AbstractParticle>
	 * blocks=new ArrayList<AbstractParticle>();
	 */

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

		box2dControler.createCircle(16, 8, 1, 10, 1);

		int down = box2dControler.createBox(0, 46, 32, 6);
		downBlock = new BlockInfo(down, 16, 3);
		int up = box2dControler.createBox(0, -4, 32, 6);
		upBlock = new BlockInfo(up, 16, 3);
		int right = box2dControler.createBox(-4, 0, 6, 46);
		rightBlock = new BlockInfo(right, 3, 23);
		int left = box2dControler.createBox(30, 0, 6, 46);
		leftBlock = new BlockInfo(left, 3, 23);

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				lastIndex = box2dControler.createBox(2f + (2.4f + 0.5f) * j, 30f + (1.6f + 0.5f) * i, 2.4f, 1.6f);
			}

		}

		/*
		 * box2dControler.setCollisionListener(new CollisionListener(){
		 * 
		 * @Override public void collisionBody(int id1, int id2) {
		 * Log.i("contact", id1+"x"+id2); //TODO use pool contactList.add(new
		 * Contact(id1,id2)); }});
		 */

	}

	private int gravityX = 1;
	private int gravityY = 10;

	/*
	 * public class BlockCollision implements CollisionListener{
	 * 
	 * @Override public void result(AbstractParticle particle1, AbstractParticle
	 * particle2) { if(particle1==ball){ if(blocks.contains(particle2)){
	 * shouldRemove.add(particle2); } } }
	 * 
	 * }
	 */

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
