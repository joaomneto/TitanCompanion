package pt.joaomneto.ffgbutil.adventure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public abstract class AdventureFragment extends DialogFragment {

	public abstract void refreshScreensFromResume();

	@Override
	public void onResume() {
		super.onResume();
		refreshScreensFromResume();
	}

	public void setupIncDecButton(View rootView, Integer incButtonId, Integer decButtonId, final String getMethod, final String setMethod, final Integer maxValue) {
		setupIncDecButton(rootView, incButtonId, decButtonId, null, null, getMethod, setMethod, maxValue);
	}

	public void setupIncDecButton(View rootView, Integer incButtonId, Integer decButtonId, final Adventure adv, @SuppressWarnings("rawtypes") final Class clazz, final String getMethod,
			final String setMethod, final Integer maxValue) {
		setupIncDecButton(rootView, incButtonId, decButtonId, adv, clazz, getMethod, setMethod, maxValue, null, null);
	}

	public void setupIncDecButton(View rootView, Integer incButtonId, Integer decButtonId, final Adventure adv, @SuppressWarnings("rawtypes") final Class clazz, final String getMethod,
			final String setMethod, final Integer maxValue, final Runnable incTrigger, final Runnable decTrigger) {

		Button incButton = (Button) rootView.findViewById(incButtonId);
		Button decButton = (Button) rootView.findViewById(decButtonId);

		final Object this_ = this;

		incButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {

					incDec(adv, clazz, getMethod, setMethod, maxValue, this_, true);

					if (incTrigger != null)
						incTrigger.run();

					refreshScreensFromResume();
				} catch (Exception e) {
					Log.e("Adventure", e.getMessage());
				}
			}

		});

		decButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {

					incDec(adv, clazz, getMethod, setMethod, maxValue, this_, false);

					if (decTrigger != null)
						decTrigger.run();

					refreshScreensFromResume();
				} catch (Exception e) {
					Log.e("Adventure", e.getMessage());
				}
			}

		});

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void incDec(final Adventure adv, final Class clazz, final String getMethod, final String setMethod, final Integer maxValue, final Object this_, boolean increase)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method mGetMethod;
		Method mSetMethod;
		Object instance;

		if (adv != null) {
			mGetMethod = clazz.getMethod(getMethod);
			mSetMethod = clazz.getMethod(setMethod, int.class);
			instance = adv;
		} else {
			mGetMethod = this_.getClass().getMethod(getMethod);
			mSetMethod = this_.getClass().getMethod(setMethod, int.class);
			instance = this_;
		}

		if (increase)
			mSetMethod.invoke(instance, Math.min(((Integer) mGetMethod.invoke(instance)) + 1, maxValue));
		else
			mSetMethod.invoke(instance, Math.max(((Integer) mGetMethod.invoke(instance)) - 1, 0));
	}


//		@Override
//		public void setUserVisibleHint(boolean isVisibleToUser) {
//			super.setUserVisibleHint(isVisibleToUser);
//			if (isVisibleToUser) {
//				refreshScreensFromResume();
//			}
//			else {  }
//		}


}
