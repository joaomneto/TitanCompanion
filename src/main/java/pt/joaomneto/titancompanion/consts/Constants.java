package pt.joaomneto.titancompanion.consts;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.Locale;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventurecreation.AdventureCreation;

public abstract class Constants {


    private static int[] gameBookCovers = new int[]{R.drawable.ff1,
            R.drawable.ff2, R.drawable.ff3, R.drawable.ff4, R.drawable.ff5,
            R.drawable.ff6, R.drawable.ff7, R.drawable.ff8, R.drawable.ff9,
            R.drawable.ff10, R.drawable.ff11, R.drawable.ff12, R.drawable.ff13,
            R.drawable.ff14, R.drawable.ff15, R.drawable.ff16, R.drawable.ff17,
            R.drawable.ff18, R.drawable.ff19, R.drawable.ff20, R.drawable.ff21,
            R.drawable.ff22, R.drawable.ff23, R.drawable.ff24, R.drawable.ff25,
            R.drawable.ff26, R.drawable.ff27, R.drawable.ff28, R.drawable.ff29,
            R.drawable.ff30, R.drawable.ff31, R.drawable.ff32, R.drawable.ff33,
            R.drawable.ff34, R.drawable.ff35, R.drawable.ff36, R.drawable.ff37,
            R.drawable.ff38, R.drawable.ff39, R.drawable.ff40, R.drawable.ff41,
            R.drawable.ff42, R.drawable.ff43, R.drawable.ff44, R.drawable.ff45,
            R.drawable.ff46, R.drawable.ff47, R.drawable.ff48, R.drawable.ff49,
            R.drawable.ff50, R.drawable.ff51, R.drawable.ff52, R.drawable.ff53,
            R.drawable.ff54, R.drawable.ff55, R.drawable.ff56, R.drawable.ff57,
            R.drawable.ff58, R.drawable.ff59, R.drawable.ff60, R.drawable.ff61, R.drawable.ff62};

    public static int getGameBookCoverAddress(int i) {
        return gameBookCovers[i];
    }

    @SuppressWarnings("unchecked")
    public static Class<? extends AdventureCreation> getCreationActivity(
            Context context, int position) {
        Class<? extends AdventureCreation> intentClass = null;
        try {
            intentClass = (Class<? extends AdventureCreation>) Class
                    .forName("pt.joaomneto.titancompanion.adventurecreation.impl."
                            + getActivityPrefix(context, position)
                            + "AdventureCreation");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return intentClass;

    }

    //TODO refactor this
    public static String getActivityPrefix(Context context, int position) {
        Resources enResources = getLocalizedResources(context, new Locale("en"));
        String name = enResources.getStringArray(
                R.array.gamebook_list_names)[position];


        String prefix = "";

        if (name.equals(enResources.getString(R.string.spectral))) {
            prefix = "spectral";
        } else if (name.equals(enResources.getString(R.string.tower))) {
            prefix = "tower";
        } else if (name.equals(enResources.getString(R.string.siege))) {
            prefix = "siege";
        } else if (name.equals(enResources.getString(R.string.moon))) {
            prefix = "moon";
        } else if (name.equals(enResources.getString(R.string.strider))) {
            prefix = "strider";
        } else {

            String[] tokens = name.split("\\ ");


            for (String string : tokens) {
                prefix += string.charAt(0);
            }
        }
        prefix = prefix.toUpperCase();

        return prefix;
    }

    @SuppressWarnings("unchecked")
    public static Class<? extends Adventure> getRunActivity(Context context,
                                                            int position) {
        Class<? extends Adventure> intentClass = null;
        try {
            intentClass = (Class<? extends Adventure>) Class
                    .forName("pt.joaomneto.titancompanion.adventure.impl."
                            + getActivityPrefix(context, position)
                            + "Adventure");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return intentClass;

    }

    @NonNull
    public static Resources getLocalizedResources(Context context, Locale desiredLocale) {
        Configuration conf = context.getResources().getConfiguration();
        conf = new Configuration(conf);
        conf.setLocale(desiredLocale);
        Context localizedContext = context.createConfigurationContext(conf);
        return localizedContext.getResources();
    }
}
