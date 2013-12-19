package pt.joaomneto.ffgbutil.adventurecreation.impl.fragments.sa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventurecreation.impl.SAAdventureCreation;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SAWeaponsFragment extends Fragment {

	public SAWeaponsFragment() {

	}

	View rootView;
	TextView weaponsValue;
	ListView weaponList = null;
	Button buttonAddWeapon = null;
	String[] allWeapons = {"Electric Lash", "Assault Blaster", "Grenade", "Gravity Bomb", "Armour"};

	
	static Map<String, Float> weaponvalues = new HashMap<String, Float>();
	
	static{
		weaponvalues.put("Electric Lash", 1f);
		weaponvalues.put("Assault Blaster", 3f);
		weaponvalues.put("Grenade", 1f);
		weaponvalues.put("Gravity Bomb", 3f);
		weaponvalues.put("Armour", 0.5f);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final SAAdventureCreation adv = (SAAdventureCreation) getActivity();

		rootView = inflater.inflate(
				R.layout.fragment_12sa_adventurecreation_weapons, container,
				false);
		weaponsValue = (TextView) rootView.findViewById(R.id.weaponsValue);
		weaponList = (ListView) rootView.findViewById(R.id.weaponList);
		buttonAddWeapon = (Button) rootView.findViewById(R.id.buttonAddweapon);

		weaponList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int position = arg2;
				AlertDialog.Builder builder = new AlertDialog.Builder(adv);
				builder.setTitle("Delete weapon?")
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
								adv.getWeapons().remove(position);
								((ArrayAdapter<String>) weaponList.getAdapter())
										.notifyDataSetChanged();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();
				return true;

			}
		});

		buttonAddWeapon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(adv);

				alert.setTitle("Weapon");

				// Set an EditText view to get user input
				final Spinner input = new Spinner(adv);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(adv,
						android.R.layout.simple_list_item_1, android.R.id.text1,
						allWeapons);
				input.setAdapter(adapter);
				InputMethodManager imm = (InputMethodManager) adv
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
				input.requestFocus();
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = input.getSelectedItem().toString();
								if(getCurrentWeaponsCount(adv) + weaponvalues.get(value) > new Float(weaponsValue.getText().toString())){
									adv.showAlert("You don't have enough weapon points to add the "+value);
									return;
								}
								adv.getWeapons().add(value);
								((ArrayAdapter<String>) weaponList
										.getAdapter()).notifyDataSetChanged();
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
				android.R.layout.simple_list_item_1, android.R.id.text1,
				adv.getWeapons());
		weaponList.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		return rootView;
	}

	public TextView getWeaponsValue() {
		return weaponsValue;
	}

	public void setWeaponsValue(TextView weaponsValue) {
		this.weaponsValue = weaponsValue;
	}

	private float getCurrentWeaponsCount(SAAdventureCreation adv){
		float count = 0;
		for (String weapon : adv.getWeapons()) {
			count+=weaponvalues.get(weapon);
		}
		return count;
	}
	
}
