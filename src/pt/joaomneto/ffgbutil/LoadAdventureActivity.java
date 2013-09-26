package pt.joaomneto.ffgbutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.joaomneto.ffgbutil.consts.Constants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LoadAdventureActivity extends Activity {
	
	
	protected static final String ADVENTURE_FILE = "ADVENTURE_FILE";	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_adventure);
		final ListView listview = (ListView) findViewById(R.id.adventureListView);

		
		final File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/ffgbutil/");
		
		String[] fileList = dir.list();
		
		final ArrayList<String> files = new ArrayList<String>();
		
		for (String string : fileList) {
			if(string.endsWith(".xml")){
				files.add(string);
			}
		}
		
		
		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, files);
		listview.setAdapter(adapter);

		final Context _this = this;
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				
				
				try {
					String file = files.get(position);
					int gamebook = -1;
					
					BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(dir, file)));
					
					while(bufferedReader.ready()){
						String line = bufferedReader.readLine();
						if(line.startsWith("gamebook=")){
							gamebook = Integer.parseInt(line.split("=")[1]);
						}
					}
					
					bufferedReader.close();

					Intent intent = new Intent(_this, Constants.getRunActivity(gamebook));
					
					intent.putExtra(ADVENTURE_FILE, file);
					startActivity(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
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
