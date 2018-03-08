package pt.joaomneto.titancompanion.adventure.impl.fragments.hotw;

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
import pt.joaomneto.titancompanion.adventure.impl.HOTWAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment;

public class HOTWAdventureNotesFragment extends AdventureNotesFragment {

    ListView keywordList = null;

    public HOTWAdventureNotesFragment() {
        setBaseLayout(R.layout.fragment_62hotw_adventure_notes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setBaseLayout(R.layout.fragment_62hotw_adventure_notes);

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        HOTWAdventure adv = (HOTWAdventure) getActivity();

        keywordList = rootView.findViewById(R.id.keywordList);
        Button buttonAddKeyword = rootView
                .findViewById(R.id.buttonAddKeyword);


        buttonAddKeyword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(adv);

                alert.setTitle(R.string.note);

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
                                if (value.isEmpty())
                                    return;
                                adv.getKeywords().add(value.trim());
                                ((ArrayAdapter<String>) keywordList.getAdapter()).notifyDataSetChanged();
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
                android.R.layout.simple_list_item_1, android.R.id.text1, adv.getKeywords());
        keywordList.setAdapter(adapter);

        keywordList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                final int position = arg2;
                AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                builder.setTitle(R.string.deleteKeyword)
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
                        adv.getKeywords().remove(position);
                        ((ArrayAdapter<String>) keywordList.getAdapter()).notifyDataSetChanged();
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
        super.refreshScreensFromResume();
        ((ArrayAdapter<String>) keywordList.getAdapter()).notifyDataSetChanged();

    }
}
