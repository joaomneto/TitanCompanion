package pt.joaomneto.titancompanion.util;

import pt.joaomneto.titancompanion.R;

public class DiceNumberToWords {


    private static final int[] numNames = {
            R.string.number_0_word,
            R.string.number_1_word,
            R.string.number_2_word,
            R.string.number_3_word,
            R.string.number_4_word,
            R.string.number_5_word,
            R.string.number_6_word,
            R.string.number_7_word,
            R.string.number_8_word,
            R.string.number_9_word,
            R.string.number_10_word,
            R.string.number_11_word,
            R.string.number_12_word,
            R.string.number_13_word,
            R.string.number_14_word,
            R.string.number_15_word,
            R.string.number_16_word,
            R.string.number_17_word,
            R.string.number_18_word};


    public static int convert(int number) {
        // 0 to 999 999 999 999
        if (number < 0 || number > 18) {
            throw new IllegalArgumentException("Invalid dice result");
        }

        return numNames[number];
    }
}