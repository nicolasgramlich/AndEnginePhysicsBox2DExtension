package org.anddev.andengine.extension.physics.box2d.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicolas Gramlich
 * @since 11:55:22 - 21.03.2010
 */
public class BidirectionalMap<K, V> extends HashMap<K, V> {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = 7321372069320408621L;

	// ===========================================================
	// Fields
	// ===========================================================

	private final HashMap<V, K> mInverseMap = new HashMap<V, K>();

	// ===========================================================
	// Constructors
	// ===========================================================

	public BidirectionalMap() {

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public boolean containsValue(final Object pValue) {
		return this.mInverseMap.containsKey(pValue);
	}

	@Override
	public V put(final K pKey, final V pValue) {
		final V put = super.put(pKey, pValue);
		this.mInverseMap.put(pValue, pKey);
		return put;
	}

	@Override
	@Deprecated
	public void putAll(final Map<? extends K, ? extends V> pMap) {
		super.putAll(pMap);
	}

	@Override
	public V remove(final Object pKey) {
		final V remove = super.remove(pKey);
		this.mInverseMap.remove(remove);
		return remove;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public K getKeyByValue(final V pValue) {
		return this.mInverseMap.get(pValue);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
