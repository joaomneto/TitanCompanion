package pt.joaomneto.titancompanion.adventure.impl.fragments.sa;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.SAAdventure;
import pt.joaomneto.titancompanion.adventurecreation.impl.adapter.TranslatableEnumAdapter;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa.SAWeapon;

public class SAAdventureWeaponsFragment extends AdventureFragment {

    ListView weaponsList = null;
    Spinner weaponsDropdown = null;
    TranslatableEnumAdapter weaponsDropdownAdapter = null;

    public SAAdventureWeaponsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_12sa_adventure_weapons, container, false);

        final SAAdventure adv = (SAAdventure) getActivity();

        Button buttonAddWeapon = rootView.findViewById(R.id.buttonAddweapon);
        weaponsList = rootView.findViewById(R.id.weaponList);


        buttonAddWeapon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(adv);

                alert.setTitle(R.string.saWeapon);

                // Set an EditText view to get user input
                final Spinner input = new Spinner(adv);
                TranslatableEnumAdapter adapter = new TranslatableEnumAdapter(adv, android.R.layout.simple_list_item_1,
                        SAWeapon.values());
                input.setAdapter(adapter);
                InputMethodManager imm = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                input.requestFocus();
                alert.setView(input);

                alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SAWeapon selectedWeapon = SAWeapon.values()[input.getSelectedItemPosition()];
                        adv.getWeapons().add(selectedWeapon);
                        ((TranslatableEnumAdapter) weaponsList.getAdapter()).notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }

        });


        TranslatableEnumAdapter adapter = new TranslatableEnumAdapter(adv, android.R.layout.simple_list_item_1, adv.getWeapons());
        weaponsList.setAdapter(adapter);

        weaponsList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final int position = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                builder.setTitle(R.string.saDeleteWeapon).setCancelable(false)
                        .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onClick(DialogInterface dialog, int which) {
                        adv.getWeapons().remove(position);
                        ((TranslatableEnumAdapter) weaponsList.getAdapter()).notifyDataSetChanged();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;

            }
        });

        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void refreshScreensFromResume() {
        ((TranslatableEnumAdapter) weaponsList.getAdapter()).notifyDataSetChanged();
    }

}
