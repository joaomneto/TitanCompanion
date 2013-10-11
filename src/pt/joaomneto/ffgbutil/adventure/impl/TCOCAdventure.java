package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class TCOCAdventure extends Adventure {

	List<String> spells;
	Integer spellValue;

	protected static final int FRAGMENT_SPELLS = 2;

	public TCOCAdventure() {
		super();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS,
				new AdventureFragmentRunner(R.string.vitalStats,
						"AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(
				R.string.fights, "AdventureCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_SPELLS, new AdventureFragmentRunner(
				R.string.spells, "TCOCAdventureSpellsFragment"));
		fragmentConfiguration.put(FRAGMENT_EQUIPMENT,
				new AdventureFragmentRunner(R.string.goldEquipment,
						"AdventureEquipmentFragment"));
		fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(
				R.string.notes, "AdventureNotesFragment"));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tcoc_adventure);

			gold = Integer.valueOf(savedGame.getProperty("gold"));
			spellValue = Integer.valueOf(savedGame.getProperty("spellValue"));
			String spellsS = new String(savedGame.getProperty("spells").getBytes(
					java.nio.charset.Charset.forName("ISO-8859-1")));
			
			if (spellsS != null) {
				spells = new ArrayList<String>();
				List<String> list = Arrays.asList(spellsS.split("#"));
				for (String string : list) {
					if (!string.isEmpty())
						spells.add(string);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adventure, menu);
		return true;
	}

	public void savepoint(View v) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Current Reference?");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		input.requestFocus();
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				try {
					String ref = input.getText().toString();
					File file = new File(dir, ref + ".xml");

					BufferedWriter bw = new BufferedWriter(new FileWriter(file));

					String equipmentS = "";
					String notesS = "";

					if (!notes.isEmpty()) {
						for (String note : notes) {
							notesS += note + "#";
						}
						notesS = notesS.substring(0, notesS.length() - 1);
					}

					if (!equipment.isEmpty()) {
						for (String eq : equipment) {
							equipmentS += eq + "#";
						}
						equipmentS = equipmentS.substring(0,
								equipmentS.length() - 1);
					}
					bw.write("gamebook=" + gamebook + "\n");
					bw.write("name=" + name + "\n");
					bw.write("initialSkill=" + initialSkill + "\n");
					bw.write("initialLuck=" + initialSkill + "\n");
					bw.write("initialStamina=" + initialStamina + "\n");
					bw.write("currentSkill=" + currentSkill + "\n");
					bw.write("currentLuck=" + currentLuck + "\n");
					bw.write("currentStamina=" + currentStamina + "\n");
					bw.write("standardPotion=" + standardPotion + "\n");
					bw.write("standardPotionValue=" + standardPotionValue
							+ "\n");
					bw.write("provisions=" + provisions + "\n");
					bw.write("gold=" + gold + "\n");
					bw.write("currentReference=" + ref + "\n");
					bw.write("equipment=" + equipmentS + "\n");
					bw.write("notes=" + notesS + "\n");

					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}

	public List<String> getSpells() {
		return spells;
	}

	public void setSpells(List<String> spells) {
		this.spells = spells;
	}

	public Integer getSpellValue() {
		return spellValue;
	}

	public void setSpellValue(Integer spellValue) {
		this.spellValue = spellValue;
	}
	
	

}
