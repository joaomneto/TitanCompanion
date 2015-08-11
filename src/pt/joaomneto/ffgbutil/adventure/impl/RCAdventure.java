package pt.joaomneto.ffgbutil.adventure.impl;


import pt.joaomneto.ffgbutil.R;

public class RCAdventure extends TFODAdventure {

    private static final int FRAGMENT_ROBOTCOMBAT = 2;


    public RCAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.ffgbutil.adventure.impl.fragments.rc.RCAdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_ROBOTCOMBAT, new AdventureFragmentRunner(R.string.robotFights, "pt.joaomneto.ffgbutil.adventure.impl.fragments.RCAdventureRobotCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment"));
        fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureNotesFragment"));
    }

}
