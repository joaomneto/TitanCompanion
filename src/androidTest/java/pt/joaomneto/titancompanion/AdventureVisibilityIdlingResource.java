package pt.joaomneto.titancompanion;

import android.support.test.espresso.IdlingResource;
import android.view.View;

import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;

public class AdventureVisibilityIdlingResource implements IdlingResource {

    private final View mView;

    private boolean mIdle;
    private ResourceCallback mResourceCallback;

    public AdventureVisibilityIdlingResource(Adventure adventure) {
        AdventureVitalStatsFragment fragment = (AdventureVitalStatsFragment) adventure.getFragments().get(0);
        this.mView = fragment.getView().findViewById(R.id.buttonSavePoint);
        this.mIdle = false;
        this.mResourceCallback = null;
    }

    @Override
    public final String getName() {
        return AdventureVisibilityIdlingResource.class.getSimpleName();
    }

    @Override
    public final boolean isIdleNow() {
        mIdle = mIdle || mView.getVisibility() == View.VISIBLE;

        if (mIdle) {
            if (mResourceCallback != null) {
                mResourceCallback.onTransitionToIdle();
            }
        }

        return mIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        mResourceCallback = resourceCallback;
    }

}