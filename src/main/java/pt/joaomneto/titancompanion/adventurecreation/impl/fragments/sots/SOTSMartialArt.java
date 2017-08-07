package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sots;

import pt.joaomneto.titancompanion.R;

/**
 * Created by Joao Neto on 24-05-2017.
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

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }
}
