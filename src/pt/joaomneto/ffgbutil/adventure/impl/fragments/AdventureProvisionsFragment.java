package pt.joaomneto.ffgbutil.adventure.impl.fragments;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.AdventureFragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdventureProvisionsFragment extends DialogFragment implements AdventureFragment {

	TextView potionName = null;
	TextView potionValue = null;
	TextView provisionsValue = null;

	public AdventureProvisionsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(
				R.layout.fragment_adventure_provisions, container, false);

		potionName = (TextView) rootView.findViewById(R.id.potionName);
		potionValue = (TextView) rootView.findViewById(R.id.potionValue);
		provisionsValue = (TextView) rootView.findViewById(R.id.provisionsValue);

		
		
		
		
		
		return rootView;
	}

	
	public void setPotionValue(Integer value) {
		this.potionValue.setText("("+value+")");
	}
	
	public void setProvisionsValue(Integer value) {
		this.provisionsValue.setText(value.toString());
	}

	@Override
	public void refreshScreensFromResume() {
		
		final Adventure adv = (Adventure) getActivity();
		String[] stringArray = getResources().getStringArray(R.array.standard_potion_list);

		potionName.setText(stringArray[adv.getStandardPotion()]);
		potionValue.setText("("+adv.getStandardPotionValue()+")");
		provisionsValue.setText(adv.getProvisions().toString());
		
	}
	

}
