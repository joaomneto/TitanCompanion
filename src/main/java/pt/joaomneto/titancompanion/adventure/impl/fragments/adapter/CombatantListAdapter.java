package pt.joaomneto.titancompanion.adventure.impl.fragments.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment.Combatant;

import java.util.List;

public class CombatantListAdapter extends ArrayAdapter<Combatant> {

    private final Context context;
    private final List<Combatant> values;

    public CombatantListAdapter(Context context, List<Combatant> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View combatantView = inflater.inflate(R.layout.component_combatant, parent, false);

        final TextView combatTextStamina = combatantView.getRootView().findViewById(R.id.combatTextStaminaValue);

        final TextView combatTextSkill = combatantView.getRootView().findViewById(R.id.combatTextSkillValue);

        final Combatant combatPosition = values.get(position);

        RadioButton radio = combatantView.getRootView().findViewById(R.id.combatSelected);
        radio.setChecked(combatPosition.isActive());


        combatTextSkill.setText("" + combatPosition.getCurrentSkill());
        combatTextStamina.setText("" + combatPosition.getCurrentStamina());

        Button minusCombatStamina = combatantView.findViewById(R.id.minusCombatantStaminaButton);
        Button plusCombatStamina = combatantView.findViewById(R.id.plusCombatantStaminaButton);
        Button minusCombatSkill = combatantView.findViewById(R.id.minusCombatantSkillButton);
        Button plusCombatSkill = combatantView.findViewById(R.id.plusCombatantSkillButton);

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


    public Context getContext() {
        return context;
    }

    public List<Combatant> getValues() {
        return values;
    }


}
