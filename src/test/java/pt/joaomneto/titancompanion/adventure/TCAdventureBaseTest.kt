package pt.joaomneto.titancompanion.adventure

import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.children
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import pt.joaomneto.titancompanion.LoadAdventureActivity
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Properties
import kotlin.collections.set
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

@Config(sdk = [Build.VERSION_CODES.O_MR1])
abstract class TCAdventureBaseTest<T : Adventure, U : AdventureFragment>(
        private val adventureClass: KClass<T>,
        private val fragmentClass: KClass<U>,
        private val properties: Properties
) {

    companion object {
        var cachedAdventure: Adventure? = null
    }

    lateinit var adventure: T
    lateinit var fragment: U

    protected fun loadSpecificvaluesToState(vararg pairs: Pair<String, String>) {
        val newSavegame = Properties()
        newSavegame.putAll(properties.toMap())
        pairs.forEach { newSavegame[it.first] = it.second }
        adventure.savedGame = newSavegame
        adventure.loadGameFromProperties()
    }

    private fun getPropertyAsString(prop: Properties): String {
        val modifiedProps = Properties()
        prop.keys.forEach {
            modifiedProps[it] = prop[it].toString()
        }
        val writer = StringWriter()
        modifiedProps.list(PrintWriter(writer))
        return writer.buffer.toString()
    }

    @Before
    fun createActivity() {

        if (cachedAdventure == null || !adventureClass.isInstance(cachedAdventure)) {
            val intent = Intent(
                    ApplicationProvider.getApplicationContext(),
                    TWOFMAdventure::class.java
            )
            intent.putExtra(LoadAdventureActivity.ADVENTURE_SAVEGAME_CONTENT, getPropertyAsString(properties))
            intent.putExtra(LoadAdventureActivity.ADVENTURE_NAME, "test")

            val activityController = Robolectric.buildActivity(adventureClass.java, intent)
            adventure = activityController.setup().get() as T
            cachedAdventure = adventure
        } else {
            adventure = cachedAdventure as T
        }

        adventure.savedGame = properties
        adventure.loadGameFromProperties()

        loadFragment()

        fragment.refreshScreensFromResume()
    }

    fun loadFragment() {
        fragment = fragmentClass.primaryConstructor?.call()
                ?: throw IllegalArgumentException("No constructor for fragnment ${fragmentClass.simpleName}")
        adventure.supportFragmentManager.beginTransaction().add(fragment, null).commit()
    }

    fun <T : View> AdventureFragment.findComponent(resId: Int) = this.view?.findViewById<T>(resId)
            ?: throw IllegalStateException("View not found")

    fun getViewByPosition(pos: Int, listView: ListView): View {
        val firstListItemPosition = listView.firstVisiblePosition
        val lastListItemPosition = firstListItemPosition + listView.childCount - 1

        return if (pos < firstListItemPosition || pos > lastListItemPosition) {
            listView.adapter.getView(pos, null, listView)
        } else {
            val childIndex = pos - firstListItemPosition
            listView.getChildAt(childIndex)
        }
    }

    fun ListView.getPositionByText(text: String) = this
            .children
            .first {
                (it as TextView).text == text
            }
}
