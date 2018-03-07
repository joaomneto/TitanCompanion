package pt.joaomneto.titancompanion.util;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by 962633 on 22-08-2017.
 */

public class AdventureTools {

    private final static Pattern signedInteger = Pattern.compile("-?[0-9]\\d*|0");

    public static boolean validateSignedInteger(EditText textField) {
        return validateNotEmpty(textField) && signedInteger.matcher(textField.getText().toString()).matches();
    }

    public static boolean validateNotEmpty(EditText textField) {
        return textField != null && textField.getText() != null && !textField.getText().toString().isEmpty();
    }
}
