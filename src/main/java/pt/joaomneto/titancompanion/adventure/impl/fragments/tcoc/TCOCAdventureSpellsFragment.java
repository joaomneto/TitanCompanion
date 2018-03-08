package pt.joaomneto.titancompanion.adventure.impl.fragments.tcoc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.TCOCAdventure;

public class TCOCAdventureSpellsFragment extends AdventureFragment {

    ListView spellList = null;

    public TCOCAdventureSpellsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_02tcoc_adventure_spells, container, false);

        spellList = rootView.findViewById(R.id.spellList);

        final TCOCAdventure adv = (TCOCAdventure) getActivity();


        final ArrayAdapter<String> selectedSpellsAdapter = new SpellListAdapter(adv, adv.getSpellList());

        spellList.setAdapter(selectedSpellsAdapter);

        spellList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final int position = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                builder.setTitle(R.string.useSpellQuestion).setCancelable(false)
                        .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onClick(DialogInterface dialog, int which) {
                        String spell = adv.getSpellList().get(position);
                        if (spell.equals(getString(R.string.skill2))) {
                            adv.setCurrentSkill(adv.getCurrentSkill() + (adv.getInitialSkill() / 2));
                            if (adv.getCurrentSkill() > adv.getInitialSkill())
                                adv.setCurrentSkill(adv.getInitialSkill());
                        } else if (spell.equals(getString(R.string.stamina2))) {
                            adv.setCurrentStamina(adv.getCurrentStamina() + (adv.getInitialStamina() / 2));
                            if (adv.getCurrentStamina() > adv.getInitialStamina())
                                adv.setCurrentStamina(adv.getInitialStamina());
                        } else if (spell.equals(getString(R.string.luck2))) {
                            adv.setCurrentLuck(adv.getCurrentLuck() + (adv.getInitialLuck() / 2));
                            if (adv.getCurrentLuck() > adv.getInitialLuck())
                                adv.setCurrentLuck(adv.getInitialLuck());
                        }
                        adv.removeSpell(position);
                        ((ArrayAdapter<String>) spellList.getAdapter()).notifyDataSetChanged();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void refreshScreensFromResume() {
        ((ArrayAdapter<String>) spellList.getAdapter()).notifyDataSetChanged();

    }

}
