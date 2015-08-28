package pt.joaomneto.ffgbutil.adventure.impl.fragments.adapter;

import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment.Combatant;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class CombatantListAdapter extends ArrayAdapter<Combatant> {

	private final Context context;
	private final List<Combatant> values;
	private Combatant currentEnemy;

	public CombatantListAdapter(Context context, List<Combatant> values) {
		super(context, -1, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View combatantView = inflater.inflate(R.layout.component_combatant, parent, false);

		final TextView combatTextStamina = (TextView) combatantView.getRootView().findViewById(R.id.combatTextStaminaValue);

		final TextView combatTextSkill = (TextView) combatantView.getRootView().findViewById(R.id.combatTextSkillValue);

		final Combatant combatPosition = values.get(position);

		RadioButton radio = (RadioButton) combatantView.getRootView().findViewById(R.id.combatSelected);
		radio.setChecked(combatPosition.equals(currentEnemy));
		
		

		combatTextSkill.setText("" + combatPosition.getCurrentSkill());
		combatTextStamina.setText("" + combatPosition.getCurrentStamina());

		Button minusCombatStamina = (Button) combatantView.findViewById(R.id.minusCombatantStaminaButton);
		Button plusCombatStamina = (Button) combatantView.findViewById(R.id.plusCombatantStaminaButton);
		Button minusCombatSkill = (Button) combatantView.findViewById(R.id.minusCombatantSkillButton);
		Button plusCombatSkill = (Button) combatantView.findViewById(R.id.plusCombatantSkillButton);

		minusCombatStamina.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				combatPosition.setCurrentStamina(Math.max(0, combatPosition.getCurrentStamina() - 1));
				combatTextStamina.setText("" + combatPosition.getCurrentStamina());
			}
		});
		plusCombatStamina.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				combatPosition.setCurrentStamina(combatPosition.getCurrentStamina() + 1);
				combatTextStamina.setText("" + combatPosition.getCurrentStamina());
			}
		});
		plusCombatSkill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				combatPosition.setCurrentSkill(combatPosition.getCurrentSkill() + 1);
				combatTextSkill.setText("" + combatPosition.getCurrentSkill());
			}
		});
		minusCombatSkill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				combatPosition.setCurrentSkill(Math.max(0, combatPosition.getCurrentSkill() - 1));
				combatTextSkill.setText("" + combatPosition.getCurrentSkill());
			}
		});

	
		return combatantView;
	}

	public Combatant getCurrentEnemy() {
		return currentEnemy;
	}

	public void setCurrentEnemy(Combatant currentEnemy) {
		this.currentEnemy = currentEnemy;
	}

	public Context getContext() {
		return context;
	}

	public List<Combatant> getValues() {
		return values;
	}

	

}
