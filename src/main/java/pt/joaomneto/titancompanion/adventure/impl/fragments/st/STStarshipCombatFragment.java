package pt.joaomneto.titancompanion.adventure.impl.fragments.st;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.adventure.impl.STAdventure;
import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll;
import pt.joaomneto.titancompanion.util.DiceRoller;

public class STStarshipCombatFragment extends AdventureFragment {

    TextView shipWeaponsValue = null;
    TextView shipShieldsValue = null;
    TextView enemyWeaponsValue = null;
    TextView enemyShieldsValue = null;

    Button attackButton = null;

    Button minusWeaponsButton = null;
    Button minusShieldsButton = null;
    Button plusWeaponsButton = null;
    Button plusShieldsButton = null;

    Button minusEnemyWeaponsButton = null;
    Button minusEnemyShieldsButton = null;
    Button plusEnemyWeaponsButton = null;
    Button plusEnemyShieldsButton = null;

    int enemyWeapons = 0;
    int enemyShields = 0;
    TextView combatResult = null;

    public STStarshipCombatFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_04st_adventure_starshipcombat, container, false);

        final STAdventure adv = (STAdventure) getActivity();

        shipWeaponsValue = rootView.findViewById(R.id.shipWeaponsValue);
        shipShieldsValue = rootView.findViewById(R.id.shipShieldsValue);
        enemyWeaponsValue = rootView.findViewById(R.id.enemyWeaponsValue);
        enemyShieldsValue = rootView.findViewById(R.id.enemyShieldsValue);

        shipWeaponsValue.setText("" + adv.getCurrentShipWeapons());
        shipShieldsValue.setText("" + adv.getCurrentShipShields());

        combatResult = rootView.findViewById(R.id.combatResult);

        minusWeaponsButton = rootView.findViewById(R.id.minusWeaponsButton);
        minusShieldsButton = rootView.findViewById(R.id.minusShieldsButton);
        plusWeaponsButton = rootView.findViewById(R.id.plusWeaponsButton);
        plusShieldsButton = rootView.findViewById(R.id.plusShieldsButton);

        minusEnemyWeaponsButton = rootView.findViewById(R.id.minusEnemyWeaponsButton);
        minusEnemyShieldsButton = rootView.findViewById(R.id.minusEnemyShieldsButton);
        plusEnemyWeaponsButton = rootView.findViewById(R.id.plusEnemyWeaponsButton);
        plusEnemyShieldsButton = rootView.findViewById(R.id.plusEnemyShieldsButton);

        minusWeaponsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setCurrentShipWeapons(Math.max(adv.getCurrentShipWeapons() - 1, 0));
                refreshScreensFromResume();
            }
        });

        minusShieldsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setCurrentShipShields(Math.max(adv.getCurrentShipShields() - 1, 0));
                refreshScreensFromResume();

            }
        });

        plusWeaponsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setCurrentShipWeapons(Math.min(adv.getCurrentShipWeapons() + 1, adv.getInitialShipWeapons()));
                refreshScreensFromResume();

            }
        });

        plusShieldsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adv.setCurrentShipWeapons(Math.min(adv.getCurrentShipShields() + 1, adv.getInitialShipShields()));
                refreshScreensFromResume();

            }
        });

        minusEnemyWeaponsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                enemyWeapons--;
                refreshScreensFromResume();

            }
        });

        minusEnemyShieldsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                enemyShields--;
                refreshScreensFromResume();

            }
        });
        plusEnemyWeaponsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                enemyWeapons++;
                refreshScreensFromResume();

            }
        });
        plusEnemyShieldsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                enemyShields++;
                refreshScreensFromResume();

            }
        });

        attackButton = rootView.findViewById(R.id.buttonAttack);

        attackButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (enemyShields == 0 || adv.getCurrentShipShields() == 0)
                    return;

                combatResult.setText("");

                DiceRoll me = DiceRoller.roll2D6();
                DiceRoll enemy = DiceRoller.roll2D6();

                if (me.getSum() < adv.getCurrentShipWeapons()) {
                    enemyShields -= 2;
                    if (enemyShields <= 0) {
                        enemyShields = 0;
                        Adventure.Companion.showAlert(R.string.ffDirectHitDefeat, adv);
                    } else {
                        combatResult.setText(R.string.directHit);
                    }
                } else {
                    combatResult.setText(R.string.missedTheEnemy);
                }

                if (enemy.getSum() < enemyWeapons) {
                    adv.setCurrentShipShields(adv.getCurrentShipShields() - 2);
                    if (adv.getCurrentShipShields() <= 0) {
                        adv.setCurrentShipShields(0);
                        Adventure.Companion.showAlert(getString(R.string.enemyDestroyedShip), adv);
                    } else {
                        combatResult.setText(combatResult.getText().toString() + "\n" + getString(R.string.enemyHitYourShip));
                    }
                } else {
                    combatResult.setText(combatResult.getText().toString() + "\n" + getString(R.string.enemyShipMissed));
                }

                refreshScreensFromResume();
            }
        });

        refreshScreensFromResume();

        return rootView;
    }

    @Override
    public void refreshScreensFromResume() {

        STAdventure adv = (STAdventure) getActivity();

        enemyShieldsValue.setText("" + enemyShields);
        enemyWeaponsValue.setText("" + enemyWeapons);
        shipShieldsValue.setText("" + adv.getCurrentShipShields());
        shipWeaponsValue.setText("" + adv.getCurrentShipWeapons());
    }
}
