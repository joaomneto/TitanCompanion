package pt.joaomneto.titancompanion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import pt.joaomneto.titancompanion.adapter.Savegame;
import pt.joaomneto.titancompanion.adapter.SavegameListAdapter;
import pt.joaomneto.titancompanion.consts.Constants;
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.lang3.StringUtils;

public class LoadAdventureActivity extends BaseActivity{

	public static final String ADVENTURE_FILE = "ADVENTURE_FILE";
	public static final String ADVENTURE_DIR = "ADVENTURE_DIR";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_adventure);
		final ListView listview = (ListView) findViewById(R.id.adventureListView);

		final File baseDir = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/ffgbutil/");

		String[] fileList = baseDir.list();

		if(fileList == null)
			fileList = new String[]{};

		final ArrayList<Savegame> files = new ArrayList<Savegame>();

		for (String string : fileList) {
			if (string.startsWith("save")) {
				File f = new File(baseDir, string);
				files.add(new Savegame(string, new Date(f.lastModified())));
			}
		}

		SavegameListAdapter adapter = new SavegameListAdapter(this, files);
		listview.setAdapter(adapter);

		final Context _this = this;

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				final String dir_ = files.get(position).getFilename();

				final File dir = new File(baseDir, dir_);
				

				File f = new File(dir, "temp.xml");
				if(f.exists())
					f.delete();
				
				final File[] savepointFiles = dir.listFiles(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String filename) {
						if(filename.startsWith("exception"))
							return false;
						return true;
					}
				});

				Arrays.sort(savepointFiles, new Comparator<File>() {
					public int compare(File f1, File f2) {
						return Long.valueOf(f1.lastModified()).compareTo(
								f2.lastModified());
					}
				});

				final String[] names = new String[savepointFiles.length];
				for (int i = 0; i < savepointFiles.length; i++) {
					names[i] = savepointFiles[i].getName().substring(0,
							savepointFiles[i].getName().length() - 4);
				}

				AlertDialog.Builder builder = new AlertDialog.Builder(_this);
				builder.setTitle(R.string.chooseSavePoint);
				builder.setItems(names, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						FightingFantasyGamebook gamebook = null;

						try {
							BufferedReader bufferedReader = new BufferedReader(
									new FileReader(savepointFiles[which]));

							while (bufferedReader.ready()) {
								String line = bufferedReader.readLine();
								if (line.startsWith("gamebook=")) {
									String gbs = line.split("=")[1];
									if (StringUtils.isNumeric(gbs))
										gamebook = FightingFantasyGamebook.values()[Integer.parseInt(gbs)-1];
									else
										gamebook = FightingFantasyGamebook.valueOf(gbs);
									break;
								}
							}

							bufferedReader.close();

							Intent intent = new Intent(_this, Constants
									.getRunActivity(_this, gamebook));

							intent.putExtra(ADVENTURE_FILE,
									savepointFiles[which].getName());
							intent.putExtra(ADVENTURE_DIR, dir_);
							startActivity(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

			}

		});

		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int position = arg2;
				AlertDialog.Builder builder = new AlertDialog.Builder(_this);
				builder.setTitle(R.string.deleteSavegame)
						.setCancelable(false)
						.setNegativeButton(R.string.close,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				builder.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							public void onClick(DialogInterface dialog,
									int which) {
								String file = files.get(position).getFilename();
								File f = new File(baseDir, file);
								if (deleteDirectory(f)) {
									files.remove(position);
									((ArrayAdapter<String>) listview
											.getAdapter())
											.notifyDataSetChanged();
								}

							}
						});

				AlertDialog alert = builder.create();
				alert.show();
				return true;

			}
		});
	}
	
	static public boolean deleteDirectory(File path) {
	    if( path.exists() ) {
	      File[] files = path.listFiles();
	      for(int i=0; i<files.length; i++) {
	         if(files[i].isDirectory()) {
	           deleteDirectory(files[i]);
				} else {
	           files[i].delete();
	         }
	      }
	    }
	    return( path.delete() );
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gamebook_list, menu);
		return true;
	}
}
