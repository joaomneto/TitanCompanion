package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tpop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.TPOPAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment;

public class TPOPAdventureEquipmentFragment extends AdventureEquipmentFragment {

    Button minusCopperButton = null;
    Button plusCopperButton = null;
    TextView copperValue = null;

    public TPOPAdventureEquipmentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_63tpop_adventure_equipment, container, false);

        final Adventure adv = (Adventure) getActivity();

        initialize(rootView, adv);

        return rootView;
    }

    protected void initialize(View rootView, final Adventure advParam) {
        super.initialize(rootView, advParam);

        TPOPAdventure adv = (TPOPAdventure) advParam;

        minusCopperButton = rootView.findViewById(R.id.minusCopperButton);
        plusCopperButton = rootView.findViewById(R.id.plusCopperButton);


        copperValue = rootView.findViewById(R.id.copperValue);
        copperValue.setText(String.valueOf(adv.getCopper()));

        copperValue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(adv);

                alert.setTitle(R.string.setValue);

                // Set an EditText view to get user input
                final EditText input = new EditText(adv);
                final InputMethodManager imm = (InputMethodManager) adv
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.requestFocus();
                alert.setView(input);

                alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        int value = Integer.parseInt(input.getText().toString());
                        adv.setCopper(value);
                        copperValue.setText("" + value);
                    }
                });

                alert.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                            }
                        });

                alert.show();

            }
        });


        minusCopperButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setCopper(Math.max(adv.getCopper() - 1, 0));
                refreshScreensFromResume();
            }
        });

        plusCopperButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setCopper(adv.getCopper() + 1);
                refreshScreensFromResume();
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void refreshScreensFromResume() {
        super.refreshScreensFromResume();
        TPOPAdventure adv = (TPOPAdventure) getActivity();
        copperValue.setText("" + adv.getCopper());
    }

}
