package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots;

import android.content.Context;

import java.util.Objects;

import pt.joaomneto.titancompanion.R;

/**
 * Created by 962633 on 24-05-2017.
 */

public enum SOTSMartialArt {

    KYUJUTSU(R.string.kyujutsu),
    IAIJUTSU(R.string.iaijutsu),
    KARUMIJUTSU(R.string.karumijutsu),
    NITOKENJUTSU(R.string.nitoKenjutsu);

    int nameId;

    SOTSMartialArt(int nameId) {
        this.nameId = nameId;
    }

    public static boolean isIdValid(int id) {
        for (SOTSMartialArt art : SOTSMartialArt.values()) {
            if (id == art.nameId) return true;
        }
        return false;
    }

    public static SOTSMartialArt getArtFromId(int id) {
        for (SOTSMartialArt art : SOTSMartialArt.values()) {
            if (id == art.nameId) return art;
        }
        return null;
    }

    public int getNameId() {
        return this.nameId;
    }

}
