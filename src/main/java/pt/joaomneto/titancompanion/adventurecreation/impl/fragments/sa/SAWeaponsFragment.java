package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventurecreation.impl.SAAdventureCreation;

public class SAWeaponsFragment extends Fragment {

    View rootView;
    TextView weaponsValue;
    ListView weaponList = null;
    Button buttonAddWeapon = null;

    public SAWeaponsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SAAdventureCreation adv = (SAAdventureCreation) getActivity();

        rootView = inflater.inflate(
                R.layout.fragment_12sa_adventurecreation_weapons, container,
                false);
        weaponsValue = rootView.findViewById(R.id.weaponsValue);
        weaponList = rootView.findViewById(R.id.weaponList);
        buttonAddWeapon = rootView.findViewById(R.id.buttonAddweapon);

        weaponList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                final int position = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                builder.setTitle(R.string.saDeleteWeapon)
                        .setCancelable(false)
                        .setNegativeButton(R.string.close,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @SuppressWarnings("unchecked")
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                adv.getWeapons().remove(position);
                                ((SAWeaponSpinnerAdapter) weaponList.getAdapter())
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

                alert.setTitle(R.string.saWeapon);

                // Set an EditText view to get user input
                final Spinner input = new Spinner(adv);
                SAWeaponSpinnerAdapter adapter = new SAWeaponSpinnerAdapter(adv,
                        adv.getWeapons().isEmpty() ? SAWeapon.INITIALWEAPONS : SAWeapon.values());
                input.setAdapter(adapter);
                InputMethodManager imm = (InputMethodManager) adv
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                input.requestFocus();
                alert.setView(input);

                alert.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @SuppressWarnings("unchecked")
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                SAWeapon selectedWeapon = SAWeapon.values()[input.getSelectedItemPosition()];
                                if (getCurrentWeaponsCount(adv) + selectedWeapon.getWeaponPoints() > adv.getCurrentWeapons()) {
                                    Adventure.showAlert(getString(R.string.saNoWeaponPoints, getString(selectedWeapon.getLabelId())), adv);
                                    return;
                                }
                                adv.getWeapons().add(selectedWeapon);
                                ((SAWeaponSpinnerAdapter) weaponList
                                        .getAdapter()).notifyDataSetChanged();
                            }
                        });

                alert.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // Canceled.
                            }
                        });

                alert.show();
            }

        });


        SAWeaponSpinnerAdapter adapter = new SAWeaponSpinnerAdapter(adv,
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

    private float getCurrentWeaponsCount(SAAdventureCreation adv) {
        float count = 0;
        for (SAWeapon weapon : adv.getWeapons()) {
            count += weapon.getWeaponPoints();
        }
        return count;
    }

}
