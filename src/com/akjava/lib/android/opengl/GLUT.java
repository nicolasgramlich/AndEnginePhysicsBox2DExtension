/*
 * This class is based glutes_shape.c,check it.
 http://glutes.sourceforge.net/
 */
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

//original code license is below
/*
 * glutes_shape.c
 *
 * Copyright (c) 2005  Joachim Pouderoux   All Rights Reserved.
 * Copyright (c) 2003  David Blythe   All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL
 * THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.akjava.lib.android.opengl;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class GLUT {

	public static class SolidCube {
		public static float v[] = new float[108]; // 108 = 6*18

		public static float cubev[] = { -1f, -1f, 1f, /* front */
		1f, -1f, 1f, -1f, 1f, 1f,

		1f, -1f, 1f, 1f, 1f, 1f, -1f, 1f, 1f,

		-1f, 1f, -1f, /* back */
		1f, -1f, -1f, -1f, -1f, -1f,

		-1f, 1f, -1f, 1f, 1f, -1f, 1f, -1f, -1f,

		-1f, -1f, -1f, /* left */
		-1f, -1f, 1f, -1f, 1f, -1f,

		-1f, -1f, 1f, -1f, 1f, 1f, -1f, 1f, -1f,

		1f, -1f, 1f, /* right */
		1f, -1f, -1f, 1f, 1f, 1f,

		1f, -1f, -1f, 1f, 1f, -1f, 1f, 1f, 1f,

		-1f, 1f, 1f, /* top */
		1f, 1f, 1f, -1f, 1f, -1f,

		1f, 1f, 1f, 1f, 1f, -1f, -1f, 1f, -1f,

		-1f, -1f, -1f, /* bottom */
		1f, -1f, -1f, -1f, -1f, 1f,

		1f, -1f, -1f, 1f, -1f, 1f, -1f, -1f, 1f, };

		static float cuben[] = { 0, 0, 1f, /* front */
		0, 0, 1f, 0, 0, 1f,

		0, 0, 1f, 0, 0, 1f, 0, 0, 1f,

		0, 0, -1f, /* back */
		0, 0, -1f, 0, 0, -1f,

		0, 0, -1f, 0, 0, -1f, 0, 0, -1f,

		-1f, 0, 0, /* left */
		-1f, 0, 0, -1f, 0, 0,

		-1f, 0, 0, -1f, 0, 0, -1f, 0, 0,

		1f, 0, 0, /* right */
		1f, 0, 0, 1f, 0, 0,

		1f, 0, 0, 1f, 0, 0, 1f, 0, 0,

		0, 1f, 0, /* top */
		0, 1f, 0, 0, 1f, 0,

		0, 1f, 0, 0, 1f, 0, 0, 1f, 0,

		0, -1f, 0, /* bottom */
		0, -1f, 0, 0, -1f, 0,

		0, -1f, 0, 0, -1f, 0, 0, -1f, 0, };

		private static FloatBuffer loadCuben() {

			cubenBuffer = OpenGLUtils.allocateFloatBuffer(108 * 4);
			for (int i = 0; i < 108; i++)
				cubenBuffer.put(cuben[i]);

			cubenBuffer.position(0);
			return cubenBuffer;
		}

		private static FloatBuffer cubevBuffer;
		private static FloatBuffer cubenBuffer;
		private static float param;

		private static FloatBuffer loadCubev(float size) {

			size /= 2;

			cubevBuffer = OpenGLUtils.allocateFloatBuffer(108 * 4);

			for (int i = 0; i < 108; i++) {
				cubevBuffer.put(cubev[i] * size);
				Log.d("", "" + cubev[i] * size);
			}
			cubevBuffer.position(0);

			return cubevBuffer;
		}

		public static void draw(GL10 gl, float size) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			if (cubevBuffer != null) {
				if (param != size) {
					cubevBuffer = null;
					cubenBuffer = null;
					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
					gl.glNormalPointer(GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
				}
			}

			if (cubenBuffer == null) {
				cubevBuffer = loadCubev(size);
				cubenBuffer = loadCuben();
				param = size;
			}

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubevBuffer);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, cubenBuffer);

			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 36);

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
	}

	public static void glutSolidCube(GL10 gl, float size) {
		SolidCube.draw(gl, size);
	}

	public static void glutSolidTorus(GL10 gl, float ir, float or, int sides, int rings) {
		SolidTorus.draw(gl, ir, or, sides, rings);
	}

	public static class SolidTorus {
		private static FloatBuffer p = null, q = null;
		private static FloatBuffer v = null, n = null;
		private static float parms[] = new float[4];// static

		/*
		 * sometime it make heap problem
		 */
		private static void draw(GL10 gl, float ir, float or, int sides, int rings) {
			int SIZEOF = 4;
			int i, j, k, triangles;
			float s, t, x, y, z, twopi, nx, ny, nz;
			float sin_s, cos_s, cos_t, sin_t, twopi_s, twopi_t;
			float twopi_sides, twopi_rings;

			// maybe clear buffer.
			if (v != null) {
				if (parms[0] != ir || parms[1] != or || parms[2] != sides || parms[3] != rings) {

					// free(v);
					// free(n);
					n = v = null; // maybe free later.

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
					gl.glNormalPointer(GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
				}
			}

			if (v == null) {
				parms[0] = ir;
				parms[1] = or;
				parms[2] = (float) sides;
				parms[3] = (float) rings;

				// this size is maybe wrong.
				p = v = OpenGLUtils.allocateFloatBuffer((int) (sides * (rings + 1) * 2 * 3 * SIZEOF));
				q = n = OpenGLUtils.allocateFloatBuffer((int) (sides * (rings + 1) * 2 * 3 * SIZEOF));

				twopi = 2.0f * (float) Math.PI;
				twopi_sides = twopi / sides;
				twopi_rings = twopi / rings;

				for (i = 0; i < sides; i++) {
					for (j = 0; j <= rings; j++) {
						for (k = 1; k >= 0; k--) {
							s = (i + k) % sides + 0.5f;
							t = (float) (j % rings);

							twopi_s = s * twopi_sides;
							twopi_t = t * twopi_rings;

							cos_s = (float) Math.cos(twopi_s);
							sin_s = (float) Math.sin(twopi_s);

							cos_t = (float) Math.cos(twopi_t);
							sin_t = (float) Math.sin(twopi_t);

							x = (or + ir * (float) cos_s) * (float) cos_t;
							y = (or + ir * (float) cos_s) * (float) sin_t;
							z = ir * (float) sin_s;

							p.put(x);
							p.put(y);
							p.put(z);

							nx = (float) cos_s * (float) cos_t;
							ny = (float) cos_s * (float) sin_t;
							nz = (float) sin_s;

							q.put(nx);
							q.put(ny);
							q.put(nz);
						}
					}
				}
			}

			v.position(0);
			n.position(0);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, v);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, n);

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			triangles = ((int) rings + 1) * 2;

			for (i = 0; i < sides; i++) {
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, triangles * i, triangles);

			}

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

		}

	}

	public static void glutSolidBox(GL10 gl, float Width, float Depth, float Height) {
		SolidBox.draw(gl, Width, Depth, Height);
	}

	public static class SolidBox {
		static float boxvec[][] = { { -1.0f, 0.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, { 1.0f, 0.0f, 0.0f }, { 0.0f, -1.0f, 0.0f }, { 0.0f, 0.0f, 1.0f }, { 0.0f, 0.0f, -1.0f } };

		static ShortBuffer boxndex[] = { OpenGLUtils.toShortBuffer(new short[] { 0, 1, 2 }), OpenGLUtils.toShortBuffer(new short[] { 0, 2, 3 }), OpenGLUtils.toShortBuffer(new short[] { 3, 2, 6 }), OpenGLUtils.toShortBuffer(new short[] { 3, 6, 7 }), OpenGLUtils.toShortBuffer(new short[] { 6, 4, 7 }), OpenGLUtils.toShortBuffer(new short[] { 6, 5, 4 }), OpenGLUtils.toShortBuffer(new short[] { 4, 5, 1 }), OpenGLUtils.toShortBuffer(new short[] { 4, 1, 0 }), OpenGLUtils.toShortBuffer(new short[] { 2, 1, 5 }), OpenGLUtils.toShortBuffer(new short[] { 2, 5, 6 }), OpenGLUtils.toShortBuffer(new short[] { 3, 7, 4 }), OpenGLUtils.toShortBuffer(new short[] { 3, 4, 0 }) };

		static FloatBuffer vBuffer;

		static float parms[] = new float[3];

		public static void draw(GL10 gl, float Width, float Depth, float Height) {

			// maybe clear buffer.
			if (vBuffer != null) {
				if (parms[0] != Width || parms[1] != Depth || parms[2] != Height) {

					// free(v);
					// free(n);
					vBuffer = null; // maybe free later.

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));

				}
			}

			int i;
			if (vBuffer == null) {
				float v[] = new float[8 * 3];
				v[0 * 3 + 0] = v[1 * 3 + 0] = v[2 * 3 + 0] = v[3 * 3 + 0] = -Width / 2.0f;
				v[4 * 3 + 0] = v[5 * 3 + 0] = v[6 * 3 + 0] = v[7 * 3 + 0] = Width / 2.0f;
				v[0 * 3 + 1] = v[1 * 3 + 1] = v[4 * 3 + 1] = v[5 * 3 + 1] = -Depth / 2.0f;
				v[2 * 3 + 1] = v[3 * 3 + 1] = v[6 * 3 + 1] = v[7 * 3 + 1] = Depth / 2.0f;
				v[0 * 3 + 2] = v[3 * 3 + 2] = v[4 * 3 + 2] = v[7 * 3 + 2] = -Height / 2.0f;
				v[1 * 3 + 2] = v[2 * 3 + 2] = v[5 * 3 + 2] = v[6 * 3 + 2] = Height / 2.0f;
				vBuffer = OpenGLUtils.toFloatBufferPositionZero(v);

				parms[0] = Width;
				parms[1] = Depth;
				parms[2] = Height;
			}

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

			for (i = 0; i < 6; i++) {
				gl.glNormal3f(boxvec[i][0], boxvec[i][1], boxvec[i][2]);
				gl.glDrawElements(GL10.GL_TRIANGLES, 3, GL10.GL_UNSIGNED_SHORT, boxndex[i * 2]);
				gl.glDrawElements(GL10.GL_TRIANGLES, 3, GL10.GL_UNSIGNED_SHORT, boxndex[i * 2 + 1]);
			}

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}

	}

	public static void glutWireBox(GL10 gl, float Width, float Depth, float Height) {
		WireBox.draw(gl, Width, Depth, Height);
	}

	public static class WireBox {
		static FloatBuffer vBuffer;
		static float parms[] = new float[3];
		static ShortBuffer wireboxndex[] = { OpenGLUtils.toShortBuffer(new short[] { 0, 1, 2, 3 }), OpenGLUtils.toShortBuffer(new short[] { 3, 2, 6, 7 }), OpenGLUtils.toShortBuffer(new short[] { 7, 6, 5, 4 }), OpenGLUtils.toShortBuffer(new short[] { 4, 5, 1, 0 }), OpenGLUtils.toShortBuffer(new short[] { 5, 6, 2, 1 }), OpenGLUtils.toShortBuffer(new short[] { 7, 4, 0, 3 }) };

		public static void draw(GL10 gl, float Width, float Depth, float Height) {
			if (vBuffer != null) {
				if (parms[0] != Width || parms[1] != Depth || parms[2] != Height) {

					// free(v);
					// free(n);
					vBuffer = null; // maybe free later.

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));

				}
			}
			int i;

			if (vBuffer == null) {
				float v[] = new float[8 * 3];
				v[0 * 3 + 0] = v[1 * 3 + 0] = v[2 * 3 + 0] = v[3 * 3 + 0] = -Width / 2.0f;
				v[4 * 3 + 0] = v[5 * 3 + 0] = v[6 * 3 + 0] = v[7 * 3 + 0] = Width / 2.0f;
				v[0 * 3 + 1] = v[1 * 3 + 1] = v[4 * 3 + 1] = v[5 * 3 + 1] = -Depth / 2.0f;
				v[2 * 3 + 1] = v[3 * 3 + 1] = v[6 * 3 + 1] = v[7 * 3 + 1] = Depth / 2.0f;
				v[0 * 3 + 2] = v[3 * 3 + 2] = v[4 * 3 + 2] = v[7 * 3 + 2] = -Height / 2.0f;
				v[1 * 3 + 2] = v[2 * 3 + 2] = v[5 * 3 + 2] = v[6 * 3 + 2] = Height / 2.0f;
				vBuffer = OpenGLUtils.toFloatBufferPositionZero(v);

				parms[0] = Width;
				parms[1] = Depth;
				parms[2] = Height;
			}

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuffer);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

			for (i = 0; i < 6; i++) {
				gl.glNormal3f(SolidBox.boxvec[i][0], SolidBox.boxvec[i][1], SolidBox.boxvec[i][2]);
				gl.glDrawElements(GL10.GL_LINE_LOOP, 4, GL10.GL_UNSIGNED_SHORT, wireboxndex[i]);
			}
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
	}

	public static void glutWireCube(GL10 gl, float size) {
		WireCube.draw(gl, size);
	}

	public static class WireCube {
		static float v[] = new float[72];

		static float cubev[] = // 72 = 3*6*4
		{ -1.0f, -1.0f, 1.0f, /* front */
		1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f,

		-1.0f, 1.0f, -1.0f, /* back */
		1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f,

		-1.0f, -1.0f, -1.0f, /* left */
		-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f,

		1.0f, -1.0f, 1.0f, /* right */
		1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f,

		-1.0f, 1.0f, 1.0f, /* top */
		1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f,

		-1.0f, -1.0f, -1.0f, /* bottom */
		1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, };

		static float cuben[] = { 0f, 0f, 1.0f, /* front */
		0f, 0f, 1.0f, 0f, 0f, 1.0f, 0f, 0f, 1.0f,

		0f, 0f, -1.0f, /* back */
		0f, 0f, -1.0f, 0f, 0f, -1.0f, 0f, 0f, -1.0f,

		-1.0f, 0f, 0f, /* left */
		-1.0f, 0f, 0f, -1.0f, 0f, 0f, -1.0f, 0f, 0f,

		1.0f, 0f, 0f, /* right */
		1.0f, 0f, 0f, 1.0f, 0f, 0f, 1.0f, 0f, 0f,

		0f, 1.0f, 0f, /* top */
		0f, 1.0f, 0f, 0f, 1.0f, 0f, 0f, 1.0f, 0f,

		0f, -1.0f, 0f, /* bottom */
		0f, -1.0f, 0f, 0f, -1.0f, 0f, 0f, -1.0f, 0f, };
		private static FloatBuffer cubenBuffer;

		private static FloatBuffer loadCuben() {
			if (cubenBuffer == null) {
				cubenBuffer = OpenGLUtils.allocateFloatBuffer(72 * 4);
				for (int i = 0; i < 72; i++)
					cubenBuffer.put(cuben[i]);

				cubenBuffer.position(0);
			}
			return cubenBuffer;
		}

		private static FloatBuffer cubevBuffer;

		private static FloatBuffer loadCubev(float size) {
			// TODO size
			if (cubevBuffer == null) {
				size /= 2;

				cubevBuffer = OpenGLUtils.allocateFloatBuffer(72 * 4);

				for (int i = 0; i < 72; i++) {
					cubevBuffer.put(cubev[i] * size);
					Log.d("", "" + cubev[i] * size);
				}
				cubevBuffer.position(0);
			}

			return cubevBuffer;
		}

		private static float param;

		public static void draw(GL10 gl, float size) {

			if (cubevBuffer != null) {
				if (param != size) {
					cubevBuffer = null;
					cubenBuffer = null;
					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
					gl.glNormalPointer(GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
				}
			}

			if (cubenBuffer == null) {
				cubevBuffer = loadCubev(size);
				cubenBuffer = loadCuben();
				param = size;
			}

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubevBuffer);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, cubenBuffer);

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			for (int i = 0; i < 6; i++)
				gl.glDrawArrays(GL10.GL_LINE_LOOP, 4 * i, 4);

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

		}
	}

	public static void glutWireTorus(GL10 gl, float dInnerRadius, float dOuterRadius, int nSides, int nRings) {
		WireTorus.draw(gl, dInnerRadius, dOuterRadius, nSides, nRings);
	}

	public static class WireTorus {
		private static FloatBuffer p = null, q = null;
		private static FloatBuffer v = null, n = null;
		private static float parms[] = new float[4];// static

		/*
		 * sometime it make heap problem
		 */
		private static void draw(GL10 gl, float ir, float or, int sides, int rings) {
			int SIZEOF = 4;
			int i, j, k, triangles;
			float s, t, x, y, z, twopi, nx, ny, nz;
			float sin_s, cos_s, cos_t, sin_t, twopi_s, twopi_t;
			float twopi_sides, twopi_rings;

			// maybe clear buffer.
			if (v != null) {
				if (parms[0] != ir || parms[1] != or || parms[2] != sides || parms[3] != rings) {

					// free(v);
					// free(n);
					n = v = null; // maybe free later.

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
					gl.glNormalPointer(GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
				}
			}

			if (v == null) {
				parms[0] = ir;
				parms[1] = or;
				parms[2] = (float) sides;
				parms[3] = (float) rings;

				// this size is maybe wrong.
				p = v = OpenGLUtils.allocateFloatBuffer((int) (sides * (rings + 1) * 2 * 3 * SIZEOF));
				q = n = OpenGLUtils.allocateFloatBuffer((int) (sides * (rings + 1) * 2 * 3 * SIZEOF));

				twopi = 2.0f * (float) Math.PI;
				twopi_sides = twopi / sides;
				twopi_rings = twopi / rings;

				for (i = 0; i < sides; i++) {
					for (j = 0; j <= rings; j++) {
						for (k = 1; k >= 0; k--) {
							s = (i + k) % sides + 0.5f;
							t = (float) (j % rings);

							twopi_s = s * twopi_sides;
							twopi_t = t * twopi_rings;

							cos_s = (float) Math.cos(twopi_s);
							sin_s = (float) Math.sin(twopi_s);

							cos_t = (float) Math.cos(twopi_t);
							sin_t = (float) Math.sin(twopi_t);

							x = (or + ir * (float) cos_s) * (float) cos_t;
							y = (or + ir * (float) cos_s) * (float) sin_t;
							z = ir * (float) sin_s;

							p.put(x);
							p.put(y);
							p.put(z);

							nx = (float) cos_s * (float) cos_t;
							ny = (float) cos_s * (float) sin_t;
							nz = (float) sin_s;

							q.put(nx);
							q.put(ny);
							q.put(nz);
						}
					}
				}
			}

			// Log.d("cap",""+v+","+p+"");

			v.position(0);
			n.position(0);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, v);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, n);

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			triangles = ((int) rings + 1) * 2;

			for (i = 0; i < sides; i++) {
				gl.glDrawArrays(GL10.GL_LINE_LOOP, triangles * i, triangles);
			}

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

		}

	}

	/*
	 * I faild from glutes_geometory.c public static class WireTorus{ static int
	 * SIZEOF=4; static FloatBuffer vertex,normal; private static float
	 * parms[]=new float[4];//static static void draw( GL10 gl,float
	 * dInnerRadius, float dOuterRadius, int nSides, int nRings ) { float
	 * iradius = dInnerRadius, oradius = dOuterRadius, phi, psi, dpsi, dphi;
	 * 
	 * int i, j; float spsi, cpsi, sphi, cphi ;
	 * 
	 * //maybe clear buffer. if (vertex!=null) { if (parms[0] != dInnerRadius ||
	 * parms[1] != dOuterRadius || parms[2] != nSides || parms[3] != nRings) {
	 * 
	 * //free(v); //free(n); normal = vertex = null; //maybe free later.
	 * 
	 * gl.glVertexPointer(3,
	 * GL10.GL_FLOAT,0,OpenGLUtils.allocateFloatBuffer(0));
	 * gl.glNormalPointer(GL10.GL_FLOAT,0,OpenGLUtils.allocateFloatBuffer(0)); }
	 * }
	 * 
	 * if(vertex==null){ parms[0] = dInnerRadius; parms[1] = dOuterRadius;
	 * parms[2] = (float)nSides; parms[3] = (float)nRings;
	 * 
	 * vertex = OpenGLUtils.allocateFloatBuffer( SIZEOF* 3 * nSides * nRings );
	 * normal = OpenGLUtils.allocateFloatBuffer( SIZEOF* 3 * nSides * nRings );
	 * 
	 * // gl.glPushMatrix();
	 * 
	 * dpsi = (float) (2.0f * Math.PI / (float)nRings) ; dphi = (float) (-2.0f *
	 * Math.PI / (float)nSides) ; psi = 0.0f;
	 * 
	 * for( j=0; j<nRings; j++ ) { cpsi = (float) Math.cos ( psi ) ; spsi =
	 * (float) Math.sin ( psi ) ; phi = 0.0f;
	 * 
	 * for( i=0; i<nSides; i++ ) {
	 * 
	 * cphi = (float) Math.cos ( phi ) ; sphi = (float) Math.sin ( phi ) ;
	 * vertex.put(cpsi * ( oradius + cphi * iradius )) ; vertex.put(spsi * (
	 * oradius + cphi * iradius )) ; vertex.put( sphi * iradius );
	 * 
	 * normal.put(cpsi * cphi ); normal.put( spsi * cphi) ; normal.put( sphi) ;
	 * phi += dphi; }
	 * 
	 * psi += dpsi; }
	 * 
	 * vertex.position(0); normal.position(0);
	 * 
	 * // glPopMatrix(); }
	 * 
	 * gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
	 * //gl.glNormalPointer(GL10.GL_FLOAT, 0, normal);
	 * 
	 * gl.glEnableClientState (GL10.GL_VERTEX_ARRAY); //gl.glEnableClientState
	 * (GL10.GL_NORMAL_ARRAY); for( j=0; j<nRings; j++ ) {
	 * gl.glDrawArrays(GL10.GL_LINE_LOOP, nRings*j, nRings); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * gl.glDisableClientState (GL10.GL_VERTEX_ARRAY); gl.glDisableClientState
	 * (GL10.GL_NORMAL_ARRAY);
	 * 
	 * } }
	 */
	public static void glutSolidSphere(GL10 gl, float radius, int slices, int stacks) {
		SolidSphere.draw(gl, radius, slices, stacks);
	}

	public static class SolidSphere {
		public static void draw(GL10 gl, float radius, int slices, int stacks) {
			int i, triangles;

			if (sphereVertex != null) {
				if (sphere_parms[0] != radius || sphere_parms[1] != slices || sphere_parms[2] != stacks) {
					sphereVertex = null;
					sphereNormal = null;

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
					gl.glNormalPointer(GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
				}
			}

			if (sphereVertex == null) {
				sphere_parms[0] = radius;
				sphere_parms[1] = (float) slices;
				sphere_parms[2] = (float) stacks;

				plotSpherePoints(radius, stacks, slices);

			}

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, sphereVertex);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, sphereNormal);

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			triangles = (slices + 1) * 2;

			for (i = 0; i < stacks; i++)
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i * triangles, triangles);

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

		}
	}

	public static void glutWireSphere(GL10 gl, float radius, int slices, int stacks) {
		WireSphere.draw(gl, radius, slices, stacks);
	}

	public static class WireSphere {
		public static void draw(GL10 gl, float radius, int slices, int stacks) {

			if (sphereVertex != null) {
				if (sphere_parms[0] != radius || sphere_parms[1] != slices || sphere_parms[2] != stacks) {
					sphereVertex = null;
					sphereNormal = null;

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
					gl.glNormalPointer(GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
				}
			}

			if (sphereVertex == null) {
				sphere_parms[0] = radius;
				sphere_parms[1] = (float) slices;
				sphere_parms[2] = (float) stacks;

				plotSpherePoints(radius, stacks, slices);

			}

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, sphereVertex);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, sphereNormal);

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			int f;
			for (int i = 0; i < stacks; ++i) {
				f = i * (slices + 1);

				for (int j = 0; j <= slices; ++j)
					gl.glDrawArrays(GL10.GL_LINE_LOOP, (f + j) * 2, 3);
			}

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

		}
	}

	static private FloatBuffer sphereVertex;
	static private FloatBuffer sphereNormal;
	static float sphere_parms[] = new float[3];

	private static void plotSpherePoints(float radius, int stacks, int slices) {
		sphereVertex = OpenGLUtils.allocateFloatBuffer(4 * 6 * stacks * (slices + 1));
		sphereNormal = OpenGLUtils.allocateFloatBuffer(4 * 6 * stacks * (slices + 1));
		int i, j;
		float slicestep, stackstep;

		stackstep = ((float) Math.PI) / stacks;
		slicestep = 2.0f * ((float) Math.PI) / slices;

		for (i = 0; i < stacks; ++i) {
			float a = i * stackstep;
			float b = a + stackstep;

			float s0 = (float) Math.sin(a);
			float s1 = (float) Math.sin(b);

			float c0 = (float) Math.cos(a);
			float c1 = (float) Math.cos(b);

			float nv;
			for (j = 0; j <= slices; ++j) {
				float c = j * slicestep;
				float x = (float) Math.cos(c);
				float y = (float) Math.sin(c);

				nv = x * s0;
				sphereNormal.put(nv);
				sphereVertex.put(nv * radius);

				nv = y * s0;
				sphereNormal.put(nv);
				sphereVertex.put(nv * radius);

				nv = c0;

				sphereNormal.put(nv);
				sphereVertex.put(nv * radius);

				nv = x * s1;

				sphereNormal.put(nv);
				sphereVertex.put(nv * radius);

				nv = y * s1;

				sphereNormal.put(nv);
				sphereVertex.put(nv * radius);

				nv = c1;

				sphereNormal.put(nv);
				sphereVertex.put(nv * radius);
			}
		}
		sphereNormal.position(0);
		sphereVertex.position(0);
	}

	public static void glutSolidCone(GL10 gl, float base, float height, int slices, int stacks) {
		SolidCone.glutCone(gl, base, height, slices, stacks, true);
	}

	public static void glutWireCone(GL10 gl, float base, float height, int slices, int stacks) {
		SolidCone.glutCone(gl, base, height, slices, stacks, false);
	}

	static float cone_parms[] = new float[4];
	static private FloatBuffer coneVertex;
	static private FloatBuffer coneNormal;

	public static class SolidCone {
		static int SIZEOF = 4;

		public static void glutCone(GL10 gl, float base, float height, int slices, int stacks, boolean isSolid) {
			int i, j;
			float twopi, nx, ny, nz;

			if (coneVertex != null) {
				if (cone_parms[0] != base || cone_parms[1] != height || cone_parms[2] != slices || cone_parms[3] != stacks) {
					coneVertex = null;
					coneNormal = null;

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
					gl.glNormalPointer(GL10.GL_FLOAT, 0, OpenGLUtils.allocateFloatBuffer(0));
				}
			}

			if ((coneVertex == null) && (height != 0.0f)) {
				float phi = (float) Math.atan(base / height);
				float cphi = (float) Math.cos(phi);
				float sphi = (float) Math.sin(phi);

				cone_parms[0] = base;
				cone_parms[1] = height;
				cone_parms[2] = (float) slices;
				cone_parms[3] = (float) stacks;
				coneVertex = OpenGLUtils.allocateFloatBuffer(stacks * (slices + 1) * 2 * 3 * SIZEOF);
				coneNormal = OpenGLUtils.allocateFloatBuffer(stacks * (slices + 1) * 2 * 3 * SIZEOF);

				twopi = 2.0f * ((float) Math.PI);

				for (i = 0; i < stacks; i++) {
					float r = base * (1.0f - (float) i / stacks);
					float r1 = base * (1.0f - (float) (i + 1.0) / stacks);
					float z = height * i / stacks;
					float z1 = height * (1.0f + i) / stacks;

					for (j = 0; j <= slices; j++) {
						float theta = j == slices ? 0.f : (float) j / slices * twopi;
						float ctheta = (float) Math.cos(theta);
						float stheta = (float) Math.sin(theta);

						nx = ctheta;
						ny = stheta;
						nz = sphi;

						coneVertex.put(r1 * nx);
						coneVertex.put(r1 * ny);
						coneVertex.put(z1);

						coneNormal.put(nx * cphi);
						coneNormal.put(ny * cphi);
						coneNormal.put(nz);

						coneVertex.put(r * nx);
						coneVertex.put(r * ny);
						coneVertex.put(z);

						coneNormal.put(nx * cphi);
						coneNormal.put(ny * cphi);
						coneNormal.put(nz);

					}
				}
				coneVertex.position(0);
				coneNormal.position(0);
			}

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, coneVertex);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, coneNormal);

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			for (i = 0; i < stacks; i++) {
				if (isSolid) {
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i * (slices + 1) * 2, (slices + 1) * 2);
				} else {
					gl.glDrawArrays(GL10.GL_LINE_LOOP, i * (slices + 1) * 2, (slices + 1) * 2);

				}
			}

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
	}
}
