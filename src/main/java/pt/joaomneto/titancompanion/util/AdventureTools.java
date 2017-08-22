package pt.joaomneto.titancompanion.util;

import android.text.Editable;

import java.util.regex.Pattern;

/**
 * Created by 962633 on 22-08-2017.
 */

public class AdventureTools {

    private final static Pattern signedInteger = Pattern.compile("-?[0-9]\\d*|0");

    public static boolean validateSignedInteger(Editable textSk) {
        return validateNotEmpty(textSk) && signedInteger.matcher(textSk.toString()).matches();
    }

    public static boolean validateNotEmpty(Editable textSk) {
        return textSk != null && !textSk.toString().isEmpty();
    }
}
