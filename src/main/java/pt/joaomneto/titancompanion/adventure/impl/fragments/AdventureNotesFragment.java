package pt.joaomneto.titancompanion.adventure.impl.fragments;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AdventureNotesFragment extends AdventureFragment {

	ListView noteList = null;

	public AdventureNotesFragment() {
		setBaseLayout(R.layout.fragment_adventure_notes);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(getBaseLayout(),
				container, false);


		final Adventure adv = (Adventure) getActivity();

		noteList = (ListView) rootView.findViewById(R.id.noteList);
		Button buttonAddNote = (Button) rootView
				.findViewById(R.id.buttonAddNote);


		buttonAddNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(adv);

				alert.setTitle(R.string.note);

				// Set an EditText view to get user input
				final EditText input = new EditText(adv);
				InputMethodManager imm = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
				input.requestFocus();
				alert.setView(input);

				alert.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = input.getText().toString();
								if(value.isEmpty())
									return;
								adv.getNotes().add(value.trim());
								((ArrayAdapter<String>)noteList.getAdapter()).notifyDataSetChanged();
							}
						});

				alert.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

				alert.show();
			}

		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(adv,
	            android.R.layout.simple_list_item_1, android.R.id.text1, adv.getNotes());
		noteList.setAdapter(adapter);

		noteList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int position = arg2;
				AlertDialog.Builder builder = new AlertDialog.Builder(adv);
				builder.setTitle(R.string.deleteNote)
						.setCancelable(false)
						.setNegativeButton(R.string.close,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});
						builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							public void onClick(DialogInterface dialog, int which) {
								adv.getNotes().remove(position);
								((ArrayAdapter<String>)noteList.getAdapter()).notifyDataSetChanged();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();
				return true;

			}
		});

		return rootView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refreshScreensFromResume() {

		((ArrayAdapter<String>) noteList.getAdapter()).notifyDataSetChanged();
		
	}
}
