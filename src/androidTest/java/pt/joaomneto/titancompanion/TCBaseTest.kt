package pt.joaomneto.titancompanion

import android.app.Activity
import android.content.res.Resources
import android.support.annotation.CheckResult
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.AmbiguousViewMatcherException
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingRootException
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.action.ViewActions.swipeRight
import android.support.test.espresso.action.ViewActions.swipeUp
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withClassName
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.any
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.startsWith
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import java.io.File
import java.io.IOException

/**
 * Created by Joao Neto on 03-08-2017.
 */

abstract class TCBaseTest {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    protected abstract val gamebook: FightingFantasyGamebook

    private val activityInstance: Activity
        get() {
            var currentActivity: Activity? = null

            getInstrumentation().runOnMainSync {
                val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
                val it = resumedActivity.iterator()
                currentActivity = it.next() as Activity
            }

            return currentActivity ?: throw IllegalStateException("No activity found.")
        }

    protected val resources: Resources
        get() {
            val targetContext = InstrumentationRegistry.getTargetContext()
            return targetContext.resources
        }

    protected val packageName: String
        get() {
            val targetContext = InstrumentationRegistry.getTargetContext()
            return targetContext.packageName
        }

    protected fun performSwipeLeft() {
        onView(withId(R.id.pager)).perform(swipeLeft())
    }

    protected fun performSwipeUp() {
        onView(withId(R.id.pager)).perform(swipeUp())
    }

    protected fun performSwipeRight() {
        onView(withId(R.id.pager)).perform(swipeRight())
    }

    protected fun performSwipeDown() {
        onView(withId(R.id.pager)).perform(swipeDown())
    }

    protected fun assertAdventureLoaded() {
        val button5 = onView(allOf(withId(R.id.buttonSavePoint), isDisplayed()))
        button5.check(matches(isDisplayed()))
    }

    protected fun assertInvalidAdventureCreation() {
        val button5 = onView(allOf(withText(startsWith(getString(R.string.someParametersMIssing))), isDisplayed()))
        button5.check(matches(isDisplayed()))
    }

    protected fun performSaveAdventureFromCreationScreen() {
        val button: ViewInteraction
        button = onView(allOf(withText(getString(R.string.saveAdventure)), isDisplayed()))
        button.perform(click())
    }

    protected fun performChoosePotion() {
        val linearLayout = onData(anything())
            .inAdapterView(allOf(withId(R.id.potionList),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    0)))
            .atPosition(0)
        linearLayout.perform(click())
    }

    protected fun performVitalStatisticsRoll() {
        val button: ViewInteraction
        button = onView(allOf(withText(getString(R.string.rollStats)), isDisplayed()))
        button.perform(click())
    }

    protected fun performFillSavegameName() {
        val editText = onView(
            allOf(withId(R.id.adventureNameInput), isDisplayed()))
        editText.perform(replaceText("espresso"), closeSoftKeyboard())

    }

    protected fun performStartAdventure() {
        var button = onView(allOf(withText(getString(R.string.create_new_adventure)), isDisplayed()))
        button.perform(click())

        //Clicks over the proper book using the property order of the GamebookEnum
        val textView = onData(anything())
            .inAdapterView(allOf(withId(R.id.gamebookListView),
                childAtPosition(
                    withClassName(`is`("android.widget.RelativeLayout")),
                    0)))
            .atPosition(gamebook.order - 1)
        textView.perform(click())

        button = onView(allOf(withText(getString(R.string.create_new_adventure)), isDisplayed()))
        button.perform(click())

        Espresso.closeSoftKeyboard()
    }

    protected fun getString(resourceId: Int): String {
        return mActivityTestRule.activity.getString(resourceId)
    }

    @Before
    fun dismissStartupDialog() {
        val button = onView(
            allOf(withId(android.R.id.button2), withText("Close"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0),
                    0),
                isDisplayed()))

        if (exists(button))
            button.perform(click())
    }

    @Before
    @Throws(IOException::class)
    fun deleteSavegames() {
        val dir = activityInstance.filesDir
        File(dir, "ffgbutil").deleteRecursively()
    }

    companion object {

        fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("Child at position $position in parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    val parent = view.parent
                    return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
                }
            }
        }

        @CheckResult
        fun exists(interaction: ViewInteraction): Boolean {
            try {
                interaction.perform(object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return any(View::class.java)
                    }

                    override fun getDescription(): String {
                        return "check for existence"
                    }

                    override fun perform(uiController: UiController, view: View) {
                        // no op, if this is run, then the execution will continue after .perform(...)
                    }
                })
                return true
            } catch (ex: AmbiguousViewMatcherException) {
                // if there's any interaction later with the same matcher, that'll fail anyway
                return true // we found more than one
            } catch (ex: NoMatchingViewException) {
                return false
            } catch (ex: NoMatchingRootException) {
                // optional depending on what you think "exists" means
                return false
            }

        }
    }

}
