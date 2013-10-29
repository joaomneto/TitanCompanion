package pt.joaomneto.ffgbutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import pt.joaomneto.ffgbutil.consts.Constants;
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

public class LoadAdventureActivity extends Activity {

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

		final ArrayList<String> files = new ArrayList<String>();

		for (String string : fileList) {
			if (string.startsWith("save")) {
				files.add(string);
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, files);
		listview.setAdapter(adapter);

		final Context _this = this;

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				final String dir_ = files.get(position);

				final File dir = new File(baseDir, dir_);

				File f = new File(dir, "temp.xml");
				if (f.exists())
					f.delete();

				final File[] savepointFiles = dir.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String filename) {
						if (filename.startsWith("exception"))
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
				builder.setTitle("Choose savepoint:");
				builder.setItems(names, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						int gamebook = -1;

						try {
							BufferedReader bufferedReader = new BufferedReader(
									new FileReader(savepointFiles[which]));

							while (bufferedReader.ready()) {
								String line = bufferedReader.readLine();
								if (line.startsWith("gamebook=")) {
									gamebook = Integer.parseInt(line.split("=")[1]);
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
				builder.setTitle("Delete save?")
						.setCancelable(false)
						.setNegativeButton("Close",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							public void onClick(DialogInterface dialog,
									int which) {
								String file = files.get(position);
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
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gamebook_list, menu);
		return true;
	}
}
