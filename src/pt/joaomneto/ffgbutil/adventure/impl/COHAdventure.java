package pt.joaomneto.ffgbutil.adventure.impl;


import pt.joaomneto.ffgbutil.R;

public class COHAdventure extends TFODAdventure {

    private static final int FRAGMENT_EQUIPMENT = 2;
    private static final int FRAGMENT_NOTES = 3;


    public COHAdventure() {
        super();
        fragmentConfiguration.clear();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights, "pt.joaomneto.ffgbutil.adventure.impl.fragments.coh.COHAdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureEquipmentFragment"));
        fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes, "pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureNotesFragment"));
    }

}
