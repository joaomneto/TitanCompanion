package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
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
import android.widget.TextView;

public class AdventureEquipmentFragment extends AdventureFragment {

	ListView equipmentList = null;
	Button minusGoldButton = null;
	Button plusGoldButton = null;
	TextView goldValue = null;

	public AdventureEquipmentFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_adventure_equipment, container, false);

		final Adventure adv = (Adventure) getActivity();

		equipmentList = (ListView) rootView.findViewById(R.id.equipmentList);
		minusGoldButton = (Button) rootView.findViewById(R.id.minusGoldButton);
		plusGoldButton = (Button) rootView.findViewById(R.id.plusGoldButton);

		Button buttonAddNote = (Button) rootView.findViewById(R.id.buttonAddEquipment);
		
		TextView goldLabel = (TextView) rootView.findViewById(R.id.goldLabel);
		goldLabel.setText(adv.getCurrencyName());

		goldValue = (TextView) rootView.findViewById(R.id.goldValue);
		goldValue.setText(adv.getGold().toString());
		
		goldValue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(adv);

				alert.setTitle("Set Value");

				// Set an EditText view to get user input
				final EditText input = new EditText(adv);
				final InputMethodManager imm = (InputMethodManager) adv
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				input.requestFocus();
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

							int value = Integer.parseInt(input.getText().toString());
							adv.setGold(value);
							goldValue.setText(""+value);
					}
				});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
							}
						});

				alert.show();
				
			}
		});

		buttonAddNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(adv);

				alert.setTitle("Equipment");

				// Set an EditText view to get user input
				final EditText input = new EditText(adv);
				InputMethodManager imm = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
				input.requestFocus();
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@SuppressWarnings("unchecked")
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = input.getText().toString();
						adv.getEquipment().add(value);
						((ArrayAdapter<String>) equipmentList.getAdapter()).notifyDataSetChanged();
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
			}

		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(adv, android.R.layout.simple_list_item_1,
				android.R.id.text1, adv.getEquipment());
		equipmentList.setAdapter(adapter);

		equipmentList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final int position = arg2;
				AlertDialog.Builder builder = new AlertDialog.Builder(adv);
				builder.setTitle("Delete equipment?").setCancelable(false)
						.setNegativeButton("Close", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@SuppressWarnings("unchecked")
					public void onClick(DialogInterface dialog, int which) {
						adv.getEquipment().remove(position);
						((ArrayAdapter<String>) equipmentList.getAdapter()).notifyDataSetChanged();
					}
				});

				AlertDialog alert = builder.create();
				alert.show();
				return true;

			}
		});

		minusGoldButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setGold(Math.max(adv.getGold() - 1, 0));
				refreshScreensFromResume();
			}
		});

		plusGoldButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adv.setGold(adv.getGold() + 1);
				refreshScreensFromResume();
			}
		});

		return rootView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refreshScreensFromResume() {
		Adventure adv = (Adventure) getActivity();
		((ArrayAdapter<String>) equipmentList.getAdapter()).notifyDataSetChanged();
		goldValue.setText("" + adv.getGold());
	}

}
