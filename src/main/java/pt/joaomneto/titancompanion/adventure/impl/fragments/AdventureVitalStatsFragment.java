package pt.joaomneto.titancompanion.adventure.impl.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;

public class AdventureVitalStatsFragment extends AdventureFragment {

    TextView skillValue = null;
    TextView staminaValue = null;
    TextView luckValue = null;
    TextView provisionsValue = null;
    TextView provisionsText = null;


    Button increaseStaminaButton = null;
    Button increaseSkillButton = null;
    Button increaseLuckButton = null;
    Button increaseProvisionsButton = null;

    Button decreaseStaminaButton = null;
    Button decreaseSkillButton = null;
    Button decreaseLuckButton = null;
    Button decreaseProvisionsButton = null;

    Button buttonConsumeProvisions = null;
    Button buttonConsumePotion = null;


    public AdventureVitalStatsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(
                R.layout.fragment_adventure_vitalstats, container, false);

        initialize(rootView);

        return rootView;
    }

    protected void initialize(View rootView) {
        skillValue = rootView.findViewById(R.id.statsSkillValue);
        staminaValue = rootView.findViewById(R.id.statsStaminaValue);
        luckValue = rootView.findViewById(R.id.statsLuckValue);
        provisionsValue = rootView.findViewById(R.id.provisionsValue);
        provisionsText = rootView.findViewById(R.id.provisionsText);
        Button buttonConsumeProvisions = rootView.findViewById(R.id.buttonConsumeProvisions);


        increaseStaminaButton = rootView
                .findViewById(R.id.plusStaminaButton);
        increaseSkillButton = rootView
                .findViewById(R.id.plusValueButton);
        increaseLuckButton = rootView
                .findViewById(R.id.plusLuckButton);
        increaseProvisionsButton = rootView
                .findViewById(R.id.plusProvisionsButton);

        decreaseStaminaButton = rootView
                .findViewById(R.id.minusStaminaButton);
        decreaseSkillButton = rootView
                .findViewById(R.id.minusSkillButton);
        decreaseLuckButton = rootView
                .findViewById(R.id.minusLuckButton);
        decreaseProvisionsButton = rootView
                .findViewById(R.id.minusProvisionsButton);
        final Adventure adv = (Adventure) getActivity();

        buttonConsumePotion = rootView.findViewById(R.id.usePotionButton);

        String[] stringArray = getResources().getStringArray(R.array.standard_potion_list);

        if (buttonConsumePotion != null) {
            if (adv.getStandardPotion() == -1) {
                buttonConsumePotion.setVisibility(View.GONE);
            } else {
                buttonConsumePotion.setText(getString(R.string.usePotion, stringArray[adv.getStandardPotion()]));
            }
        }
        increaseStaminaButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getCurrentStamina() < adv.getInitialStamina())
                    adv.setCurrentStamina(adv.getCurrentStamina() + 1);
                refreshScreensFromResume();

            }
        });

        increaseSkillButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getCurrentSkill() < adv.getInitialSkill())
                    adv.setCurrentSkill(adv.getCurrentSkill() + 1);
                refreshScreensFromResume();

            }
        });

        staminaValue.setOnClickListener(view -> {

            AlertDialog.Builder alert = createAlertForInitialStatModification(R.string.setInitialStamina, (dialog, whichButton) -> {

                int value = getValueFromAlertTextField(rootView, (AlertDialog) dialog);

                adv.setInitialStamina(value);
            });


            alert.show();
            rootView.clearFocus();
        });

        skillValue.setOnClickListener(view -> {

            AlertDialog.Builder alert = createAlertForInitialStatModification(R.string.setInitialSkill, (dialog, whichButton) -> {

                int value = getValueFromAlertTextField(rootView, (AlertDialog) dialog);
                adv.setInitialSkill(value);
            });


            alert.show();
        });

        luckValue.setOnClickListener(view -> {

            AlertDialog.Builder alert = createAlertForInitialStatModification(R.string.setInitialLuck, (dialog, whichButton) -> {

                int value = getValueFromAlertTextField(rootView, (AlertDialog) dialog);

                adv.setInitialLuck(value);
            });


            alert.show();
        });

        increaseLuckButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getCurrentLuck() < adv.getInitialLuck())
                    adv.setCurrentLuck(adv.getCurrentLuck() + 1);
                refreshScreensFromResume();

            }
        });

        increaseProvisionsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setProvisions(adv.getProvisions() + 1);
                refreshScreensFromResume();

            }
        });

        decreaseStaminaButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getCurrentStamina() > 0)
                    adv.setCurrentStamina(adv.getCurrentStamina() - 1);
                refreshScreensFromResume();

            }
        });

        decreaseSkillButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getCurrentSkill() > 0)
                    adv.setCurrentSkill(adv.getCurrentSkill() - 1);
                refreshScreensFromResume();

            }
        });

        decreaseLuckButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getCurrentLuck() > 0)
                    adv.setCurrentLuck(adv.getCurrentLuck() - 1);
                refreshScreensFromResume();

            }
        });

        decreaseProvisionsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adv.getProvisions() > 0)
                    adv.setProvisions(adv.getProvisions() - 1);
                refreshScreensFromResume();

            }
        });

        if (adv.getProvisionsValue() == null || adv.getProvisionsValue() < 0) {
            increaseProvisionsButton.setVisibility(View.INVISIBLE);
            decreaseProvisionsButton.setVisibility(View.INVISIBLE);
            provisionsValue.setVisibility(View.INVISIBLE);
            provisionsText.setVisibility(View.INVISIBLE);
            buttonConsumeProvisions.setVisibility(View.INVISIBLE);
        }


    }

    private int getValueFromAlertTextField(View view, AlertDialog dialog) {
        EditText input = dialog.findViewById(R.id.alert_editText_field);

        int value = Integer.parseInt(input.getText().toString());

        ((Adventure) getActivity()).closeKeyboard(view);

        return value;
    }


    public void setProvisionsValue(Integer value) {
        this.provisionsValue.setText(value.toString());
    }

    protected AlertDialog.Builder createAlertForInitialStatModification(int dialogTitle, DialogInterface.OnClickListener positiveButtonListener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle(dialogTitle);

        // Set an EditText view to get user input
        final EditText input = new EditText(getContext());
        input.setId(R.id.alert_editText_field);

        final InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        input.requestFocus();
        alert.setView(input);

        alert.setNegativeButton(R.string.cancel,
                (dialog, whichButton) -> imm.hideSoftInputFromWindow(input.getWindowToken(), 0));

        alert.setPositiveButton(R.string.ok, positiveButtonListener);

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        return alert;
    }


    @Override
    public void refreshScreensFromResume() {
        Adventure adv = (Adventure) getActivity();

        skillValue.setText("" + adv.getCurrentSkill());
        staminaValue.setText("" + adv.getCurrentStamina());
        luckValue.setText("" + adv.getCurrentLuck());
        provisionsValue.setText("" + adv.getProvisions());

        if (buttonConsumePotion != null) {
            if (adv.getStandardPotion() <= 0) {
                buttonConsumePotion.setVisibility(View.GONE);
            } else {
                buttonConsumePotion.setVisibility(View.VISIBLE);
                if (adv.getStandardPotionValue() <= 0) {
                    buttonConsumePotion.setEnabled(false);
                } else {
                    buttonConsumePotion.setEnabled(true);
                }
            }
        }

    }


}
