package pt.joaomneto.ffgbutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import consts.GamebookCoverConstants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GamebookListActivity extends Activity {
	
	

	protected static final String GAMEBOOK_COVER = "GAMEBOOK_COVER";
	protected static final String GAMEBOOK_URL = "GAMEBOOK_URL";	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamebook_list);
		final ListView listview = (ListView) findViewById(R.id.gamebookListView);
		String[] values = getResources().getStringArray(R.array.gamebook_list_names);
		final String[] urls = getResources().getStringArray(R.array.gamebook_list_urls);

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		final Intent intent = new Intent(this, GamebookSelectionActivity.class);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
			    intent.putExtra(GAMEBOOK_COVER, GamebookCoverConstants.getGameBookCoverAddress(position+1));
			    intent.putExtra(GAMEBOOK_URL, urls[position]);
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gamebook_list, menu);
		return true;
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

}
