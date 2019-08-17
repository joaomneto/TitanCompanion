package pt.joaomneto.titancompanion

import android.app.Activity
import android.content.res.Resources
import androidx.annotation.CheckResult
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.AmbiguousViewMatcherException
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingRootException
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import android.view.View
import android.view.ViewGroup
import junit.framework.Assert
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
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import java.io.File
import java.io.IOException
import kotlin.reflect.KMutableProperty1

/**
 * Created by Joao Neto on 03-08-2017.
 */

abstract class TCBaseTest {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    protected abstract val gamebook: FightingFantasyGamebook

    protected open fun testVitalStatisticsFragment() {
        testVitalStatistics(activityInstance as Adventure)
    }

    protected val activityInstance: Activity
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
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            return targetContext.resources
        }

    protected val packageName: String
        get() {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
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

        repeat(10, { performSwipeLeft() })
        repeat(10, { performSwipeRight() })
    }

    protected fun assertInvalidAdventureCreation() {
        val button5 = onView(allOf(withText(startsWith(getString(R.string.someParametersMIssing))), isDisplayed()))
        button5.check(matches(isDisplayed()))
    }

    protected fun performSaveAdventureFromCreationScreen() {
        val button = onView(allOf(withText(getString(R.string.saveAdventure)), isDisplayed()))
        button.perform(click())
    }

    protected fun performChoosePotion() {
        val linearLayout = onData(anything())
                .inAdapterView(
                        allOf(
                                withId(R.id.potionList),
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        0
                                )
                        )
                )
                .atPosition(0)
        linearLayout.perform(click())
    }

    protected fun performVitalStatisticsRoll() {
        val button: ViewInteraction = onView(allOf(withText(getString(R.string.rollStats)), isDisplayed()))
        button.perform(click())
    }

    protected fun performFillSavegameName() {
        val editText = onView(
                allOf(withId(R.id.adventureNameInput), isDisplayed())
        )
        editText.perform(replaceText("espresso"), closeSoftKeyboard())
    }

    protected fun performStartAdventure() {
        var button = onView(allOf(withText(getString(R.string.create_new_adventure)), isDisplayed()))
        button.perform(click())

        //Clicks over the proper book using the property order of the GamebookEnum
        val textView = onData(anything())
                .inAdapterView(
                        allOf(
                                withId(R.id.gamebookListView),
                                childAtPosition(
                                        withClassName(`is`("android.widget.RelativeLayout")),
                                        0
                                )
                        )
                )
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
                allOf(
                        withId(android.R.id.button2), withText("Close"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        0
                                ),
                                0
                        ),
                        isDisplayed()
                )
        )

        if (exists(button))
            button.perform(click())
    }

    @Before
    @Throws(IOException::class)
    fun deleteSavegames() {
        val dir = activityInstance.filesDir
        File(dir, "ffgbutil").deleteRecursively()
    }

    fun performClickOnButton(buttonId: Int, nrTimes: Int = 1) {
        val button = onView(
                allOf<View>(
                        withId(buttonId),
                        isDisplayed()
                )
        )
        repeat(nrTimes, { button.perform(click()) })
    }

    fun performInsertTextInPopupAndClickOk(elementId: Int, text: String) {
        val editText = onView(
                allOf<View>(
                        withId(elementId),
                        isDisplayed()
                )
        )
        editText.perform(replaceText(text), closeSoftKeyboard())
        performClickOnButton(android.R.id.button1)
    }

    fun testStaminaStat(adventure: Adventure) {
        testIncrementalStat(
                adventure,
                R.id.minusStaminaButton,
                R.id.plusStaminaButton,
                R.id.statsStaminaValue,
                Adventure::currentStamina,
                Adventure::initialStamina,
                true
        )
    }

    fun testLuckStat(adventure: Adventure) {
        testIncrementalStat(
                adventure,
                R.id.minusLuckButton,
                R.id.plusLuckButton,
                R.id.statsLuckValue,
                Adventure::currentLuck,
                Adventure::initialLuck,
                true
        )
    }

    fun testSkillStat(adventure: Adventure) {
        testIncrementalStat(
                adventure,
                R.id.minusSkillButton,
                R.id.plusSkillButton,
                R.id.statsSkillValue,
                Adventure::currentSkill,
                Adventure::initialSkill,
                true
        )
    }

    fun <A : Adventure> testIncrementalStat(
            adventure: A,
            minusButtonId: Int,
            plusButtonId: Int,
            valueId: Int,
            adventureField: KMutableProperty1<A, Int>,
            adventureMaxField: KMutableProperty1<A, Int>? = null,
            maxValueEditable: Boolean = false
    ) {
        var currentStatValue = adventureField.get(adventure)
        val maxStatValue = adventureMaxField?.get(adventure) ?: Int.MAX_VALUE

        if (currentStatValue == maxStatValue) {
            performClickOnButton(minusButtonId, 3)
        } else if (currentStatValue == 0) {
            performClickOnButton(plusButtonId, 3)
        }

        // Test plus button
        currentStatValue = adventureField.get(adventure)
        performClickOnButton(plusButtonId)
        Assert.assertEquals(currentStatValue + 1, adventureField.get(adventure))

        // Test minus button
        currentStatValue = adventureField.get(adventure)
        performClickOnButton(minusButtonId)
        Assert.assertEquals(currentStatValue - 1, adventureField.get(adventure))

        // Test lower boundary of the stat
        currentStatValue = adventureField.get(adventure)
        performClickOnButton(minusButtonId, currentStatValue)
        Assert.assertEquals(0, adventureField.get(adventure))
        performClickOnButton(minusButtonId)
        Assert.assertEquals(0, adventureField.get(adventure))

        if (maxStatValue != Int.MAX_VALUE) {
            // Test upper boundary of the stat
            currentStatValue = adventureField.get(adventure)
            performClickOnButton(plusButtonId, maxStatValue - currentStatValue)
            Assert.assertEquals(maxStatValue, adventureField.get(adventure))
            performClickOnButton(plusButtonId)
            Assert.assertEquals(maxStatValue, adventureField.get(adventure))

            if (maxValueEditable) {
                // Test upper boundary modification of the stat
                currentStatValue = adventureField.get(adventure)
                performClickOnButton(valueId)
                performInsertTextInPopupAndClickOk(R.id.alert_editText_field, (maxStatValue + 2).toString())
                Assert.assertEquals(maxStatValue + 2, adventureMaxField?.get(adventure))
                performClickOnButton(plusButtonId, 2)
                Assert.assertEquals(currentStatValue + 2, adventureField.get(adventure))
            }
        }
    }

    companion object {

        fun childAtPosition(
                parentMatcher: Matcher<View>, position: Int
        ): Matcher<View> {

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

    private fun testVitalStatistics(adventure: Adventure) {
        testStaminaStat(adventure)
        testSkillStat(adventure)
        testLuckStat(adventure)
    }
}
