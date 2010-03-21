package com.akjava.android.box2dtest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.akjava.android.box2dtest.ndkbox2d.BlockBreakActivity;
import com.akjava.android.box2dtest.ndkbox2d.BoundBallActivity;
import com.akjava.android.box2dtest.ndkbox2d.BoundBoxActivity;
import com.akjava.android.box2dtest.ndkbox2d.MoveBoxActivity;

public class NDKBox2dActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("NDK Box2d");

		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTitles()));
		getListView().setTextFilterEnabled(true);
	}

	private Class[] actives = { BoundBallActivity.class, BoundBoxActivity.class, BlockBreakActivity.class, MoveBoxActivity.class, };

	public String[] listTitles() {
		String titles[] = new String[actives.length];
		for (int i = 0; i < titles.length; i++) {
			String t = actives[i].getSimpleName();
			t = t.substring(0, t.length() - "Activity".length());
			titles[i] = t;
		}
		return titles;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = null;
		i = new Intent(this, actives[position]);
		if (i != null)
			startActivity(i);
	}
}