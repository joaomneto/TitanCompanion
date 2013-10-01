package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.Adventure;
import pt.joaomneto.ffgbutil.util.StableArrayAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AdventureNotesFragment extends DialogFragment {

	ListView noteList = null;

	public AdventureNotesFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_adventure_notes,
				container, false);

		noteList = (ListView) rootView.findViewById(R.id.noteList);
		Button buttonAddNote = (Button) rootView
				.findViewById(R.id.buttonAddNote);

		final Adventure adv = (Adventure) getActivity();

		buttonAddNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(adv);

				alert.setTitle("Note");

				// Set an EditText view to get user input
				final EditText input = new EditText(adv);
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = input.getText().toString();
								adv.getNotes().add(value);
								((ArrayAdapter<String>)noteList.getAdapter()).notifyDataSetChanged();
							}
						});

				alert.setNegativeButton("Cancel",
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
				adv.getNotes().remove(arg2);
				((ArrayAdapter<String>)noteList.getAdapter()).notifyDataSetChanged();
				return true;
				
			}
		});

		return rootView;
	}

	// public void setPotionValue(Integer value) {
	// this.potionValue.setText("("+value+")");
	// }
}
