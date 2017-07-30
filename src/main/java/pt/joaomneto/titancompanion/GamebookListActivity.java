package pt.joaomneto.titancompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GamebookListActivity extends BaseActivity{
	
	

	protected static final String GAMEBOOK_COVER = "GAMEBOOK_COVER";
	protected static final String GAMEBOOK_URL = "GAMEBOOK_URL";
	protected static final String GAMEBOOK_POSITION = "GAMEBOOK_POSITION";	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamebook_list);
		final ListView listview = (ListView) findViewById(R.id.gamebookListView);
		String[] values = getResources().getStringArray(R.array.gamebook_list_names);

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1, android.R.id.text1, list);
		
		listview.setAdapter(adapter);

		final Intent intent = new Intent(this, GamebookSelectionActivity.class);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
			    intent.putExtra(GAMEBOOK_POSITION, position);
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

	
}
