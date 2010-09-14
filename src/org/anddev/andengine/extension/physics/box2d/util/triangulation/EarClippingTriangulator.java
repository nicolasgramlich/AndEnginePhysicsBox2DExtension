/*******************************************************************************
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.anddev.andengine.extension.physics.box2d.util.triangulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * A simple implementation of the ear cutting algorithm to triangulate simple
 * polygons without holes. For more information see
 * http://cgm.cs.mcgill.ca/~godfried/teaching/cg-projects/97/Ian/algorithm2.html
 * 
 * @author badlogicgames@gmail.com
 * @author modified by Nicolas Gramlich
 * 
 */
public final class EarClippingTriangulator implements ITriangulationAlgoritm {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CONCAVE = 1;
	private static final int CONVEX = -1;

	// ===========================================================
	// Fields
	// ===========================================================

	private int mConcaveVertexCount;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public List<Vector2> computeTriangles(final List<Vector2> pVertices) {
		// TODO Check if LinkedList performs better
		final ArrayList<Vector2> triangles = new ArrayList<Vector2>();
		final ArrayList<Vector2> vertices = new ArrayList<Vector2>(pVertices.size());
		vertices.addAll(pVertices);

		if(vertices.size() == 3) {
			triangles.addAll(vertices);
			return triangles;
		}

		while(vertices.size() >= 3) {
			final int vertexTypes[] = this.classifyVertices(vertices);

			for(int index = 0; index < vertices.size(); index++) {
				final Vector2 previousVertex = vertices.get(EarClippingTriangulator.getPreviousIndex(vertices, index));
				final Vector2 currentVertex = vertices.get(index);
				final Vector2 nextVertex = vertices.get(EarClippingTriangulator.getNextIndex(vertices, index));

				if(this.isEar(vertices, vertexTypes, previousVertex, currentVertex, nextVertex)) {
					this.cutEar(vertices, triangles, index);
					break;
				}
			}
		}

		return triangles;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private static boolean isVerticesClockwise(final ArrayList<Vector2> pVertices) {
		float area = 0;
		for(int i = 0; i < pVertices.size(); i++) {
			final Vector2 p1 = pVertices.get(i);
			final Vector2 p2 = pVertices.get(EarClippingTriangulator.getNextIndex(pVertices, i));
			area += p1.x * p2.y - p2.x * p1.y;
		}

		if(area < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * An array of either {@link EarClippingTriangulator#CONCAVE} or
	 * {@link EarClippingTriangulator#CONVEX}
	 */
	private int[] classifyVertices(final ArrayList<Vector2> pVertices) {
		final int vertexCount = pVertices.size();

		final int[] vertexTypes = new int[vertexCount];
		this.mConcaveVertexCount = 0;

		/* Ensure vertices are in clockwise order. */
		if(!EarClippingTriangulator.isVerticesClockwise(pVertices)) {
			Collections.reverse(pVertices);
		}

		for(int index = 0; index < vertexCount; index++) {
			final int previousIndex = getPreviousIndex(pVertices, index);
			final int nextIndex = getNextIndex(pVertices, index);

			final Vector2 previousVertex = pVertices.get(previousIndex);
			final Vector2 currentVertex = pVertices.get(index);
			final Vector2 nextVertex = pVertices.get(nextIndex);

			if(EarClippingTriangulator.isConvex(previousVertex.x, previousVertex.y, currentVertex.x, currentVertex.y, nextVertex.x, nextVertex.y)) {
				vertexTypes[index] = CONVEX;
			} else {
				vertexTypes[index] = CONCAVE;
				this.mConcaveVertexCount++;
			}
		}

		return vertexTypes;
	}

	/**
	 * @return <code>true</code> when the x2/y2 vertex is convex,
	 *         <code>false</code> otherwise.
	 */
	private static boolean isConvex(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
		if(EarClippingTriangulator.calculateArea(x1, y1, x2, y2, x3, y3) < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @return Area spanned by the three vertices.
	 */
	private static float calculateArea(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
		float area = 0;

		area += x1 * (y3 - y2);
		area += x2 * (y1 - y3);
		area += x3 * (y2 - y1);

		/*
		 * For actual area, we need to multiple areaSum * 0.5, but we are only
		 * interested in the sign of the area (+/-)
		 */

		return area;
	}

	/**
	 * @return <code>true</code> when the Triangles contains one (or more
	 *         vertex), <code>false</code> otherwise.
	 */
	private static boolean isAnyVertexInTriangle(final ArrayList<Vector2> pVertices, final int[] pVertexTypes, final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
		int i = 0;
		float area1, area2, area3;

		final int vertexCount = pVertices.size();
		while(i < vertexCount - 1) {
			final Vector2 currentVertex = pVertices.get(i);

			final float currentVertexX = currentVertex.x;
			final float currentVertexY = currentVertex.y;

			if((pVertexTypes[i] == CONCAVE) && (((currentVertexX != x1) && (currentVertexY != y1)) || ((currentVertexX != x2) && (currentVertexY != y2)) || ((currentVertexX != x3) && (currentVertexY != y3)))) {

				area1 = EarClippingTriangulator.calculateArea(x1, y1, x2, y2, currentVertexX, currentVertexY);
				area2 = EarClippingTriangulator.calculateArea(x2, y2, x3, y3, currentVertexX, currentVertexY);
				area3 = EarClippingTriangulator.calculateArea(x3, y3, x1, y1, currentVertexX, currentVertexY);

				if(area1 > 0 && area2 > 0 && area3 > 0) {
					return true;
				} else if(area1 <= 0 && area2 <= 0 && area3 <= 0) {
					return true;
				}
			}
			i++;
		}
		return false;
	}

	/**
	 * @return <code>true</code> if the vertex at (x2, y2) is an ear, <code>false</code> otherwise.
	 */
	private boolean isEar(final ArrayList<Vector2> pVertices, final int[] pVertexTypes, final Vector2 pPreviousVertex, final Vector2 pVertex, final Vector2 pNextVertex) {
		if(this.mConcaveVertexCount != 0) {
			if(EarClippingTriangulator.isAnyVertexInTriangle(pVertices, pVertexTypes, pPreviousVertex.x, pPreviousVertex.y, pVertex.x, pVertex.y, pNextVertex.x, pNextVertex.y)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	private void cutEar(final ArrayList<Vector2> pVertices, final ArrayList<Vector2> pTriangles, final int pEarTipIndex) {
		final int previousIndex = EarClippingTriangulator.getPreviousIndex(pVertices, pEarTipIndex);
		final int nextIndex = EarClippingTriangulator.getNextIndex(pVertices, pEarTipIndex);

		if(!EarClippingTriangulator.isCollinear(pVertices, previousIndex, pEarTipIndex, nextIndex)) {
			pTriangles.add(new Vector2(pVertices.get(previousIndex)));
			pTriangles.add(new Vector2(pVertices.get(pEarTipIndex)));
			pTriangles.add(new Vector2(pVertices.get(nextIndex)));
		}

		pVertices.remove(pEarTipIndex);
		if(pVertices.size() >= 3) {
			removeCollinearNeighborEarsAfterRemovingEar(pVertices, pEarTipIndex);
		}
	}

	private void removeCollinearNeighborEarsAfterRemovingEar(final ArrayList<Vector2> pVertices, final int pEarTipCutIndex) {
		final int collinearCheckNext = pEarTipCutIndex % pVertices.size();
		final int collinearCheckPrevious = getPreviousIndex(pVertices, collinearCheckNext);
		if(EarClippingTriangulator.isCollinear(pVertices, collinearCheckNext)) {
			pVertices.remove(collinearCheckNext);
			
			if(pVertices.size() > 3) {
				final int collinearCheckPreviousUpdated = getPreviousIndex(pVertices, collinearCheckNext);
				if(EarClippingTriangulator.isCollinear(pVertices, collinearCheckPreviousUpdated)){
					pVertices.remove(collinearCheckPreviousUpdated);
				}
			}
		} else if(EarClippingTriangulator.isCollinear(pVertices, collinearCheckPrevious)){
			pVertices.remove(collinearCheckPrevious);
		}
	}

	private static boolean isCollinear(final ArrayList<Vector2> pVertices, final int pIndex) {
		final int previousIndex = getPreviousIndex(pVertices, pIndex);
		final int nextIndex = getNextIndex(pVertices, pIndex);

		return EarClippingTriangulator.isCollinear(pVertices, previousIndex, pIndex, nextIndex);
	}

	private static boolean isCollinear(final ArrayList<Vector2> pVertices, final int pPreviousIndex, final int pIndex, final int pNextIndex) {
		final Vector2 previousVertex = pVertices.get(pPreviousIndex);
		final Vector2 vertex = pVertices.get(pIndex);
		final Vector2 nextVertex = pVertices.get(pNextIndex);

		return EarClippingTriangulator.calculateArea(previousVertex.x, previousVertex.y, vertex.x, vertex.y, nextVertex.x, nextVertex.y) == 0;
	}

	private static int getPreviousIndex(final List<Vector2> pVertices, final int pIndex) {
		return pIndex == 0 ? pVertices.size() - 1 : pIndex - 1;
	}

	private static int getNextIndex(final List<Vector2> pVertices, final int pIndex) {
		return pIndex == pVertices.size() - 1 ? 0 : pIndex + 1;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
