package com.akjava.lib.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLUtils;
import android.util.Log;

public class OpenGLUtils {

	public static FloatBuffer allocateFloatBuffer(int capacity) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(capacity);
		vbb.order(ByteOrder.nativeOrder());
		return vbb.asFloatBuffer();
	}

	public static IntBuffer allocateInttBuffer(int capacity) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(capacity);
		vbb.order(ByteOrder.nativeOrder());
		return vbb.asIntBuffer();
	}

	public static ShortBuffer allocateShortBuffer(int capacity) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(capacity);
		vbb.order(ByteOrder.nativeOrder());
		return vbb.asShortBuffer();
	}

	public static void addVertex3f(FloatBuffer buffer, float x, float y, float z) {
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
	}

	public static void addIndex(ShortBuffer buffer, int index1, int index2, int index3) {
		buffer.put((short) index1);
		buffer.put((short) index2);
		buffer.put((short) index3);
	}

	public static void addCoord2f(FloatBuffer buffer, float x, float y) {
		buffer.put(x);
		buffer.put(y);
	}

	public static void addColorf(FloatBuffer buffer, float r, float g, float b, float a) {
		buffer.put(r);
		buffer.put(g);
		buffer.put(b);
		buffer.put(a);
	}

	public static FloatBuffer toFloatBufferPositionZero(float[] values) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(values.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer buffer = vbb.asFloatBuffer();
		buffer.put(values);
		buffer.position(0);
		return buffer;
	}

	public static ShortBuffer toShortBuffer(short[] values) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(values.length * 2);
		vbb.order(ByteOrder.nativeOrder());
		ShortBuffer buffer = vbb.asShortBuffer();
		buffer.put(values);
		buffer.position(0);
		return buffer;
	}

	public static Bitmap loadBitmap(Context mContext, int id) {
		Options opt = new Options();
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), id, opt);

		System.out.println(bitmap.getConfig());
//		if (!sizeCheck(bitmap)) {
//			throw new RuntimeException("width or height not 2x size,it make invalid error on OpenGL:" + id);
//		}
		return bitmap;
	}

	private static boolean sizeCheck(Bitmap bitmap) {
		boolean ret = true;
		int t = 2;
		int w = bitmap.getWidth();

		while (w != t) {
			if (w % t == 1) {
				Log.e("glutils w=", w + "," + t);
				return false;
			}
			t *= 2;
			if (t > w) {
				return false;
			}
		}

		t = 2;
		int h = bitmap.getHeight();
		while (h != t) {
			if (h % t == 1) {
				Log.e("glutils h=", h + "," + t);
				return false;
			}
			t *= 2;
			if (t > h) {
				return false;
			}
		}

		return ret;
	}

	/**
	 * this is for resized GLU.gluOrtho2D (gl,-1f, 1.0f, -1f, 1.0f);
	 * 
	 * @param x
	 * @param y
	 * @param screenWidth
	 * @param screenHeight
	 * @return
	 */
	public static float[] toOpenGLCordinate(float x, float y, int screenWidth, int screenHeight) {
		float sx = ((float) x / screenWidth) * 2 - 1.0f;
		float sy = ((float) y / screenHeight) * 2 - 1.0f;
		sy *= -1;
		return new float[] { sx, sy };
	}

	/*
	 * y should *=-1;
	 */
	public static float toOpenGLCordinate(float x, int screenWidth) {
		Log.i("myapp", "x=" + x + "," + screenWidth);
		float sx = ((float) x / screenWidth) * 2 - 1.0f;
		return sx;
	}

	public static float realToVirtialValue(int x, int real, float virtial) {
		return virtial / real * x;
	}

	public static int virtualToRealvalue(float x, int real, float virtial) {
		// using DecimalFormat make gc
		return (int) ((float) x / (virtial / real));
	}

	private static FloatBuffer mTextureBuffer;
	private static FloatBuffer mFVertexBuffer;
	private static ShortBuffer mIndexBuffer;

	public static FloatBuffer getBoxTriangleTextureBuffer() {
		if (mTextureBuffer == null) {
			mTextureBuffer = OpenGLUtils.allocateFloatBuffer(4 * 6 * 2);
			OpenGLUtils.addCoord2f(mTextureBuffer, 0.0f, 1.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 0.0f, 0.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 1.0f, 1.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 1.0f, 1.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 1.0f, 0.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 0.0f, 0.0f);
			mTextureBuffer.position(0);
		}
		return mTextureBuffer;
	}

	public static FloatBuffer getBoxTriangleFlipVerticalTextureBuffer() {
		if (mTextureBuffer == null) {
			mTextureBuffer = OpenGLUtils.allocateFloatBuffer(4 * 6 * 2);
			OpenGLUtils.addCoord2f(mTextureBuffer, 0.0f, 0.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 0.0f, 1.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 1.0f, 0.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 1.0f, 0.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 1.0f, 1.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 0.0f, 1.0f);
			mTextureBuffer.position(0);
		}
		return mTextureBuffer;
	}

	public static FloatBuffer getBoxTextureBuffer() {
		if (mTextureBuffer == null) {
			mTextureBuffer = OpenGLUtils.allocateFloatBuffer(4 * 4 * 2);
			OpenGLUtils.addCoord2f(mTextureBuffer, 0.0f, 0.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 0.0f, 1.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 1.0f, 0.0f);
			OpenGLUtils.addCoord2f(mTextureBuffer, 1.0f, 1.0f);
			mTextureBuffer.position(0);
		}
		return mTextureBuffer;
	}

	public static ShortBuffer getBoxIndexBuffer() {
		if (mIndexBuffer == null) {
			mIndexBuffer = OpenGLUtils.allocateShortBuffer(6 * 2);
			mIndexBuffer.put((short) 0);
			mIndexBuffer.put((short) 1);
			mIndexBuffer.put((short) 2);
			mIndexBuffer.put((short) 2);
			mIndexBuffer.put((short) 3);
			mIndexBuffer.put((short) 1);
			mIndexBuffer.position(0);
		}
		return mIndexBuffer;
	}

	public static FloatBuffer getBoxVertexBuffer() {
		if (mFVertexBuffer == null) {
			mFVertexBuffer = OpenGLUtils.allocateFloatBuffer(4 * 4 * 3);
			OpenGLUtils.addVertex3f(mFVertexBuffer, -1, -1f, 0);
			OpenGLUtils.addVertex3f(mFVertexBuffer, -1, 1f, 0);

			OpenGLUtils.addVertex3f(mFVertexBuffer, 1, -1f, 0);
			OpenGLUtils.addVertex3f(mFVertexBuffer, 1, 1f, 0);
			mFVertexBuffer.position(0);

		}
		return mFVertexBuffer;
	}

	public static FloatBuffer getBoxTriangleVertexBuffer() {
		if (mFVertexBuffer == null) {
			mFVertexBuffer = OpenGLUtils.allocateFloatBuffer(4 * 6 * 3);
			OpenGLUtils.addVertex3f(mFVertexBuffer, -1, -1f, 0);
			OpenGLUtils.addVertex3f(mFVertexBuffer, -1, 1f, 0);
			OpenGLUtils.addVertex3f(mFVertexBuffer, 1, -1f, 0);

			OpenGLUtils.addVertex3f(mFVertexBuffer, 1, -1f, 0);
			OpenGLUtils.addVertex3f(mFVertexBuffer, 1, 1f, 0);
			OpenGLUtils.addVertex3f(mFVertexBuffer, -1, 1f, 0);

			mFVertexBuffer.position(0);

		}
		return mFVertexBuffer;
	}

	public static void bindTextureImage(GL10 gl, final int id, final Bitmap bitmap) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, id);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
	}

}
