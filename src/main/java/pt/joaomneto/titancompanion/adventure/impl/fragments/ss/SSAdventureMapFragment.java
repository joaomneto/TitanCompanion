package pt.joaomneto.titancompanion.adventure.impl.fragments.ss;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.SSAdventure;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SSAdventureMapFragment extends AdventureFragment {

    static List<String> elements = new ArrayList<String>();

    static {
        Class<R.id> clazz = R.id.class;

        for (Field field : clazz.getFields()) {
            if (field.getName().startsWith("clearing")) {
                elements.add(field.getName());
            }
            if (field.getName().startsWith("up") || field.getName().startsWith("down")
                    || field.getName().startsWith("left") || field.getName().startsWith("right")) {
                elements.add(field.getName());
            }
        }
    }

    View rootView;
    Button addClearingButton;
    private SSAdventure adv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_08st_adventure_map, container, false);

        adv = (SSAdventure) this.getContext();

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setClearingLayoutSizes(rootView, adv);
            }
        });


        final SSAdventure adv = (SSAdventure) getActivity();

        addClearingButton = rootView.findViewById(R.id.addClearingButton);

        addClearingButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle(R.string.currentClearing);

                // Set an EditText view to get user input
                final EditText input = new EditText(getActivity());
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                input.setInputType(InputType.TYPE_CLASS_PHONE);
                input.requestFocus();
                alert.setView(input);

                alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        String number = input.getText().toString();
                        adv.addVisitedClearings(number);
                        refreshScreensFromResume();

                    }
                });

                alert.show();
            }
        });

        refreshScreensFromResume();

        return rootView;
    }

    @Override
    public void refreshScreensFromResume() {

        List<String> showElements = new ArrayList<String>();

        SSAdventure adv = (SSAdventure) getActivity();

        for (String number : adv.getVisitedClearings()) {
            try {
                revealField(number);

            } catch (Exception e) {
                throw (new RuntimeException(e));
            }
        }


        if (rootView.findViewById(R.id.clearing_17).getVisibility() == View.VISIBLE
                && rootView.findViewById(R.id.clearing_24).getVisibility() == View.VISIBLE) {
            rootView.findViewById(R.id.clearing_18_24).setVisibility(View.VISIBLE);
        }


        if (rootView.findViewById(R.id.clearing_29).getVisibility() == View.VISIBLE
                && rootView.findViewById(R.id.clearing_33).getVisibility() == View.VISIBLE) {
            rootView.findViewById(R.id.clearing_29_33).setVisibility(View.VISIBLE);
        }


        if (rootView.findViewById(R.id.clearing_10).getVisibility() == View.VISIBLE
                && rootView.findViewById(R.id.clearing_28).getVisibility() == View.VISIBLE) {
            rootView.findViewById(R.id.clearing_28_10).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.clearing_10_w).setVisibility(View.VISIBLE);
        }

        int visibles = 0;

        visibles += rootView.findViewById(R.id.clearing_7).getVisibility() == View.VISIBLE ? 1 : 0;
        visibles += rootView.findViewById(R.id.clearing_15).getVisibility() == View.VISIBLE ? 1 : 0;
        visibles += rootView.findViewById(R.id.clearing_19).getVisibility() == View.VISIBLE ? 1 : 0;
        visibles += rootView.findViewById(R.id.clearing_32).getVisibility() == View.VISIBLE ? 1 : 0;

        if (visibles > 1) {
            rootView.findViewById(R.id.clearing_7_15_19_32).setVisibility(View.VISIBLE);
        }
    }

    private void revealField(String field) throws NoSuchFieldException, IllegalAccessException {

        SSClearing clearing = SSClearing.getIfExists("CLEARING_" + field);
        if (clearing != null) {
            TextView currcell = rootView.findViewById(clearing.getResource());
            currcell.setVisibility(View.VISIBLE);
        }
    }

    public void setClearingLayoutSizes(View rootView, Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        dpHeight -= addClearingButton.getHeight();

        dpHeight /= 9;
        dpWidth /= 7;


        int finalValue = (int) Math.min(dpHeight, dpWidth);

        TableLayout table = rootView.findViewById(R.id.clearingGrid);
        final int childcount = table.getChildCount();
        for (int i = 0; i < childcount; i++) {
            TableRow row = (TableRow) table.getChildAt(i);

            final int cellCount = row.getChildCount();
            for (int j = 0; j < cellCount; j++) {
                TextView cell = (TextView) row.getChildAt(j);
                cell.setHeight(finalValue);
                cell.setWidth(finalValue);
            }


        }
    }
}
