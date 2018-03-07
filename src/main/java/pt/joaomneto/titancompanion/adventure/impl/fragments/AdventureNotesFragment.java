package pt.joaomneto.titancompanion.adventure.impl.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;

public class AdventureNotesFragment extends AdventureFragment {

    ListView noteList = null;

    public AdventureNotesFragment() {
        setBaseLayout(R.layout.fragment_adventure_notes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(getBaseLayout(),
                container, false);


        final Adventure adv = (Adventure) getActivity();

        noteList = rootView.findViewById(R.id.noteList);
        Button buttonAddNote = rootView
                .findViewById(R.id.buttonAddNote);


        buttonAddNote.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                synchronized (adv.getNotes()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(adv);

                    alert.setTitle(R.string.note);

                    // Set an EditText view to get user input
                    final EditText input = new EditText(adv);
                    InputMethodManager imm = (InputMethodManager) adv.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                    input.requestFocus();
                    alert.setView(input);

                    alert.setPositiveButton(R.string.ok,
                            (dialog, whichButton) -> {
                                synchronized (adv.getNotes()) {
                                    String value = input.getText().toString();
                                    if (value.isEmpty())
                                        return;
                                    adv.getNotes().add(value.trim());
                                    ((ArrayAdapter<String>) noteList.getAdapter()).notifyDataSetChanged();
                                }
                            });

                    alert.setNegativeButton(R.string.cancel,
                            (dialog, whichButton) -> {
                                // Canceled.
                            });

                    alert.show();
                }
            }

        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(adv,
                android.R.layout.simple_list_item_1, android.R.id.text1, adv.getNotes());
        noteList.setAdapter(adapter);

        noteList.setOnItemLongClickListener((arg0, arg1, position, arg3) -> {
            synchronized (adv.getNotes()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(adv);
                builder.setTitle(R.string.deleteNote)
                        .setCancelable(false)
                        .setNegativeButton(R.string.close,
                                (dialog, id) -> dialog.cancel());
                builder.setPositiveButton(R.string.ok, (dialog, which) -> {
                    if (adv.getNotes().size() > position) {
                        adv.getNotes().remove(position);
                    }
                    ((ArrayAdapter<String>) noteList.getAdapter()).notifyDataSetChanged();
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

        ((ArrayAdapter<String>) noteList.getAdapter()).notifyDataSetChanged();

    }
}
