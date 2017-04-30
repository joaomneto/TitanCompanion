package pt.joaomneto.titancompanion.adventure.impl.fragments.aod;

import java.util.ArrayList;

/**
 * Created by joao on 26/04/17.
 */

public class Army extends ArrayList<SoldiersDivision> {
    public static Army getInstanceFromSavedString(String savedArmy) {
        Army result = new Army();

        String[] armyList = savedArmy.split("#");
        for (String division : armyList) {
            String[] divisionParameters = division.split("§");
            result.add(new SoldiersDivision(divisionParameters[0], Integer.parseInt(divisionParameters[1])));
        }

        return result;
    }

    public String getStringToSaveGame() {
        StringBuilder sb = new StringBuilder();

        boolean first = true;

        for (SoldiersDivision division : this) {
            sb.append(!first ? '#' : "");
            sb.append(division.getType() + "§" + division.getQuantity());
            first = false;
        }

        return sb.toString();
    }
}