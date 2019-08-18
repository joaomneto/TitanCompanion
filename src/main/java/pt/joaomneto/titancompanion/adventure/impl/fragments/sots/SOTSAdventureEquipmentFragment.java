package pt.joaomneto.titancompanion.adventure.impl.fragments.sots;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.SOTSAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots.SOTSMartialArt;

public class SOTSAdventureEquipmentFragment extends AdventureEquipmentFragment {


    Button minusHummingButton = null;
    Button plusHummingButton = null;
    TextView hummingValue = null;
    Button minusArmourPButton = null;
    Button plusArmourPButton = null;
    TextView armourPValue = null;
    Button minusWillowButton = null;
    Button plusWillowButton = null;
    TextView willowValue = null;
    Button minusBowelButton = null;
    Button plusBowelButton = null;
    TextView bowelValue = null;


    TextView hummingLabel = null;
    TextView armourPLabel = null;
    TextView willowLabel = null;
    TextView bowelLabel = null;

    public SOTSAdventureEquipmentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_20sots_adventure_equipment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        hummingValue = rootView.findViewById(R.id.hummingValue);
        armourPValue = rootView.findViewById(R.id.armourPValue);
        willowValue = rootView.findViewById(R.id.willowValue);
        bowelValue = rootView.findViewById(R.id.bowelValue);

        hummingLabel = rootView.findViewById(R.id.hummingLabel);
        armourPLabel = rootView.findViewById(R.id.armourPLabel);
        willowLabel = rootView.findViewById(R.id.willowLabel);
        bowelLabel = rootView.findViewById(R.id.bowelLabel);

        minusHummingButton = rootView.findViewById(R.id.minusHummingButton);
        plusHummingButton = rootView.findViewById(R.id.plusHummingButton);

        minusArmourPButton = rootView.findViewById(R.id.minusArmourPButton);
        plusArmourPButton = rootView.findViewById(R.id.plusArmourPButton);

        minusWillowButton = rootView.findViewById(R.id.minusWillowButton);
        plusWillowButton = rootView.findViewById(R.id.plusWillowButton);

        minusBowelButton = rootView.findViewById(R.id.minusBowelButton);
        plusBowelButton = rootView.findViewById(R.id.plusBowelButton);

        final SOTSAdventure adv = (SOTSAdventure) getActivity();

        minusHummingButton.setOnClickListener(v -> {
            adv.setHummingBulbArrows(Math.max(adv.getHummingBulbArrows() - 1, 0));
            refreshScreensFromResume();
        });

        plusHummingButton.setOnClickListener(v -> {
            adv.setHummingBulbArrows(adv.getHummingBulbArrows() + 1);
            refreshScreensFromResume();
        });

        minusArmourPButton.setOnClickListener(v -> {
            adv.setArmourPiercerArrows(Math.max(adv.getArmourPiercerArrows() - 1, 0));
            refreshScreensFromResume();
        });

        plusArmourPButton.setOnClickListener(v -> {
            adv.setArmourPiercerArrows(adv.getArmourPiercerArrows() + 1);
            refreshScreensFromResume();
        });

        minusWillowButton.setOnClickListener(v -> {
            adv.setWillowLeafArrows(Math.max(adv.getWillowLeafArrows() - 1, 0));
            refreshScreensFromResume();
        });

        plusWillowButton.setOnClickListener(v -> {
            adv.setWillowLeafArrows(adv.getWillowLeafArrows() + 1);
            refreshScreensFromResume();
        });

        minusBowelButton.setOnClickListener(v -> {
            adv.setBowelRakerArrows(Math.max(adv.getBowelRakerArrows() - 1, 0));
            refreshScreensFromResume();
        });

        plusBowelButton.setOnClickListener(v -> {
            adv.setBowelRakerArrows(adv.getBowelRakerArrows() + 1);
            refreshScreensFromResume();
        });

        if (!adv.getSkill().equals(SOTSMartialArt.KYUJUTSU)) {
            minusHummingButton.setVisibility(View.GONE);
            plusHummingButton.setVisibility(View.GONE);
            hummingValue.setVisibility(View.GONE);
            minusArmourPButton.setVisibility(View.GONE);
            plusArmourPButton.setVisibility(View.GONE);
            armourPValue.setVisibility(View.GONE);
            minusWillowButton.setVisibility(View.GONE);
            plusWillowButton.setVisibility(View.GONE);
            willowValue.setVisibility(View.GONE);
            minusBowelButton.setVisibility(View.GONE);
            plusBowelButton.setVisibility(View.GONE);
            bowelValue.setVisibility(View.GONE);

            willowLabel.setVisibility(View.GONE);
            bowelLabel.setVisibility(View.GONE);
            armourPLabel.setVisibility(View.GONE);
            hummingLabel.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void refreshScreensFromResume() {
        super.refreshScreensFromResume();
        SOTSAdventure adv = (SOTSAdventure) getActivity();

        hummingValue.setText("" + adv.getHummingBulbArrows());
        armourPValue.setText("" + adv.getArmourPiercerArrows());
        willowValue.setText("" + adv.getWillowLeafArrows());
        bowelValue.setText("" + adv.getBowelRakerArrows());

    }

}
