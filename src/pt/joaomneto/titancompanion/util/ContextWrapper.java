package pt.joaomneto.titancompanion.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

public class ContextWrapper extends android.content.ContextWrapper {

public ContextWrapper(Context base) {
    super(base);
}

public static ContextWrapper wrap(Context context, Locale newLocale) {

    Resources res = context.getResources();
    Configuration configuration = res.getConfiguration();

    if (BuildUtils.isAtLeast24Api()) {
        configuration.setLocale(newLocale);

        LocaleList localeList = new LocaleList(newLocale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);

        context = context.createConfigurationContext(configuration);

    } else if (BuildUtils.isAtLeast17Api()) {
        configuration.setLocale(newLocale);
        context = context.createConfigurationContext(configuration);

    } else {
        configuration.locale = newLocale;
        res.updateConfiguration(configuration, res.getDisplayMetrics());
    }

    return new ContextWrapper(context);
}}