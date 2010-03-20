package com.akjava.android.box2d;

public class collisionIdKeeper {
	public int[] contactIds;
	private int index = 0;

	public collisionIdKeeper(int max) {
		contactIds = new int[max];
		clear();
	}

	public void clear() {
		for (int i = 0; i < contactIds.length; i++) {
			contactIds[i] = -1;
		}
		index = 0;
	}

	public void add(int id) {
		contactIds[index] = id;
		if (index < contactIds.length - 1) {
			index++;
		}
	}

}
