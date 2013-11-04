package pt.joaomneto.ffgbutil.adventure.impl.fragments.ss;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import pt.joaomneto.ffgbutil.adventure.impl.SSAdventure;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SSAdventureMapFragment extends Fragment implements AdventureFragment {

	static List<String> elements = new ArrayList<String>();
	View rootView;

	static {
		Class<R.id> clazz = R.id.class;

		for (Field field : clazz.getFields()) {
			if (field.getName().startsWith("clearing")) {
				elements.add(field.getName());
			}
			if (field.getName().startsWith("up") || field.getName().startsWith("down")
					|| field.getName().startsWith("left") || field.getName().startsWith("right")) {
				elements.add(field.getName());
			}
		}
	}

	Button addClearingButton;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		final View rootView = inflater.inflate(R.layout.fragment_08st_adventure_map, container, false);

		final SSAdventure adv = (SSAdventure) getActivity();

		addClearingButton = (Button) rootView.findViewById(R.id.addClearingButton);

		addClearingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

				alert.setTitle("Current clearing?");

				// Set an EditText view to get user input
				final EditText input = new EditText(getActivity());
				final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
						Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				input.requestFocus();
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
						String number = input.getText().toString();
						adv.addVisitedClearings(number);

					}
				});

				alert.show();
			}
		});

		refreshScreensFromResume();

		return rootView;
	}

	@Override
	public void refreshScreensFromResume() {

		List<String> showElements = new ArrayList<String>();

		SSAdventure adv = (SSAdventure) getActivity();

		for (String number : adv.getVisitedClearings()) {
			for (String link : elements) {
				if (link.endsWith("_" + number)) {
					showElements.add(link);
				}
			}
			Class<R.id> clazz = R.id.class;
			for (String field : showElements) {
				try {
					Field f = clazz.getField(field);
					int id = f.getInt(null);
					TextView currcell = (TextView) rootView.findViewById(id);
					currcell.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
