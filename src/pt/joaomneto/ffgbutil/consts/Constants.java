package pt.joaomneto.ffgbutil.consts;

import java.util.Locale;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventurecreation.AdventureCreation;
import android.content.Context;

public abstract class Constants {

	private static int[] gameBookCovers = new int[] { R.drawable.ff1,
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
			R.drawable.ff58, R.drawable.ff59 };

	public static int getGameBookCoverAddress(int i) {
		return gameBookCovers[i];
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends AdventureCreation> getCreationActivity(
			Context context, int position) {
		Class<? extends AdventureCreation> intentClass = null;
		try {
			intentClass = (Class<? extends AdventureCreation>) Class
					.forName("pt.joaomneto.ffgbutil.adventurecreation.impl."
							+ getActivityPrefix(context, position)
							+ "AdventureCreation");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return intentClass;

	}

	public static String getActivityPrefix(Context context, int position) {
		Locale l = Locale.getDefault();
		String name = context.getResources().getStringArray(
				R.array.gamebook_list_names)[position];

		String[] tokens = name.split("\\ ");

		String prefix = "";

		for (String string : tokens) {
			prefix += string.charAt(0);
		}
		prefix = prefix.toUpperCase(l);

		return prefix;
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends Adventure> getRunActivity(Context context,
			int position) {
		Class<? extends Adventure> intentClass = null;
		try {
			intentClass = (Class<? extends Adventure>) Class
					.forName("pt.joaomneto.ffgbutil.adventure.impl."
							+ getActivityPrefix(context, position)
							+ "Adventure");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return intentClass;

	}
}
