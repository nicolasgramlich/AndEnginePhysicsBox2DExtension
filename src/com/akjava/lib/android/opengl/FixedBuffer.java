package com.akjava.lib.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class FixedBuffer {

	public static FloatBuffer allocateFloatBuffer(int capacity) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(capacity);
		vbb.order(ByteOrder.nativeOrder());
		return vbb.asFloatBuffer();
	}

	public static IntBuffer allocateIntBuffer(int capacity) {
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

	public static void addVertex3i(IntBuffer buffer, int x, int y, int z) {
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
	}

	public static void addIntIndex(IntBuffer buffer, int index1, int index2, int index3) {
		buffer.put(index1);
		buffer.put(index2);
		buffer.put(index3);
	}

	public static void addCoord2f(FloatBuffer buffer, float x, float y) {
		buffer.put(x);
		buffer.put(y);
	}

	public static void addCoord2i(IntBuffer buffer, int x, int y) {
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

	public static IntBuffer toIntBuffer(int[] values) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(values.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		IntBuffer buffer = vbb.asIntBuffer();
		buffer.put(values);
		buffer.position(0);
		return buffer;
	}

	private static IntBuffer mTextureBuffer;
	private static IntBuffer mFVertexBuffer;
	private static IntBuffer mIndexBuffer;

	public static IntBuffer getIntBoxTextureBuffer() {
		if (mTextureBuffer == null) {
			mTextureBuffer = FixedBuffer.allocateIntBuffer(4 * 4 * 2);
			FixedBuffer.addCoord2i(mTextureBuffer, 0, 0);
			FixedBuffer.addCoord2i(mTextureBuffer, 0, 0);
			FixedBuffer.addCoord2i(mTextureBuffer, 1, 1);
			FixedBuffer.addCoord2i(mTextureBuffer, 1, 1);
			mTextureBuffer.position(0);
		}
		return mTextureBuffer;
	}

	public static IntBuffer getBoxIndexBuffer() {
		if (mIndexBuffer == null) {
			mIndexBuffer = FixedBuffer.allocateIntBuffer(6 * 4);
			mIndexBuffer.put(0);
			mIndexBuffer.put(1);
			mIndexBuffer.put(2);
			mIndexBuffer.put(2);
			mIndexBuffer.put(3);
			mIndexBuffer.put(1);
			mIndexBuffer.position(0);
		}
		return mIndexBuffer;
	}

	public static IntBuffer getBoxVertexBuffer() {
		if (mFVertexBuffer == null) {
			mFVertexBuffer = FixedBuffer.allocateIntBuffer(4 * 4 * 3);
			FixedBuffer.addVertex3i(mFVertexBuffer, -1, -1, 0);
			FixedBuffer.addVertex3i(mFVertexBuffer, -1, 1, 0);

			FixedBuffer.addVertex3i(mFVertexBuffer, 1, -1, 0);
			FixedBuffer.addVertex3i(mFVertexBuffer, 1, 1, 0);
			mFVertexBuffer.position(0);

		}
		return mFVertexBuffer;
	}

	/*
	 * 
	 * 
	 * if(!glInitialized){
	 * 
	 * 
	 * gl11.glGenBuffers(2, vBuffer, 0);
	 * 
	 * gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, vBuffer[0]);
	 * Log.i("my","capa:"+OpenGLUtils.getBoxIndexBuffer().capacity());
	 * gl11.glBufferData
	 * (GL11.GL_ELEMENT_ARRAY_BUFFER,OpenGLUtils.getBoxIndexBuffer().capacity()
	 * , OpenGLUtils.getBoxIndexBuffer(), GL11.GL_STATIC_DRAW);
	 * 
	 * gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vBuffer[1]);
	 * gl11.glBufferData(GL11
	 * .GL_ARRAY_BUFFER,FixedBuffer.getBoxVertexBuffer().capacity() ,
	 * FixedBuffer.getBoxVertexBuffer(), GL11.GL_STATIC_DRAW);
	 * 
	 * glInitialized=true; }
	 * 
	 * 
	 * 
	 * gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vBuffer[1]);
	 * gl.glVertexPointer(3, GL10.GL_FIXED, 0,null);
	 * 
	 * gl.glBindTexture(GL10.GL_TEXTURE_2D, backgroundTextureId);
	 * 
	 * 
	 * 
	 * gl.glTranslatef(0,0, 1); gl.glScalef (320, 480, 1.0f);
	 * 
	 * 
	 * //gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
	 * OpenGLUtils.getBoxVertexBuffer());
	 * 
	 * gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, vBuffer[0]);
	 * gl.glDrawElements(GL10.GL_TRIANGLES, 6,GL10.GL_UNSIGNED_SHORT, null);
	 */

}
