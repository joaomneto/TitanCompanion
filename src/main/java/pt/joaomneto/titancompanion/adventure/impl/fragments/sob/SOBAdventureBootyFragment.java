package pt.joaomneto.titancompanion.adventure.impl.fragments.sob;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.SOBAdventure;

public class SOBAdventureBootyFragment extends AdventureFragment {

    ListView bootyList = null;

    public SOBAdventureBootyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_16sob_adventure_booty,
                container, false);

        bootyList = rootView.findViewById(R.id.bootyList);
        Button buttonAddBooty = rootView
                .findViewById(R.id.buttonAddBooty);

        final SOBAdventure adv = (SOBAdventure) getActivity();

        buttonAddBooty.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(adv);

                alert.setTitle(getString(R.string.booty));

                // Set an EditText view to get user input
                final EditText input = new EditText(adv);
                InputMethodManager imm = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                input.requestFocus();
                alert.setView(input);

                alert.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @SuppressWarnings("unchecked")
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                String value = input.getText().toString();
                                adv.getBooty().add(value);
                                ((ArrayAdapter<String>) bootyList.getAdapter()).notifyDataSetChanged();
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(adv,
                android.R.layout.simple_list_item_1, android.R.id.text1, adv.getBooty());
        bootyList.setAdapter(adapter);

        bootyList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                final int position = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                builder.setTitle(R.string.deleteBooty)
                        .setCancelable(false)
                        .setNegativeButton(R.string.close,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onClick(DialogInterface dialog, int which) {
                        adv.getBooty().remove(position);
                        ((ArrayAdapter<String>) bootyList.getAdapter()).notifyDataSetChanged();
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

        ((ArrayAdapter<String>) bootyList.getAdapter()).notifyDataSetChanged();

    }
}
