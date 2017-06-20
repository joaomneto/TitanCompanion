package pt.joaomneto.titancompanion.adventure.impl.fragments.strider;

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

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;

public class STRIDERAdventureTimeOxygenFragment extends AdventureFragment {

	ListView noteList = null;

	public STRIDERAdventureTimeOxygenFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_27strider_adventure_time_oxygen,
				container, false);

		return rootView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refreshScreensFromResume() {
	}
}
