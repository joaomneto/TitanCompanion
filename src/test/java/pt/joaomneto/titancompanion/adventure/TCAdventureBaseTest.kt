package pt.joaomneto.titancompanion.adventure

import android.content.Intent
import android.view.View
import android.widget.ListView
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.shadows.ShadowApplication
import pt.joaomneto.titancompanion.LoadAdventureActivity
import pt.joaomneto.titancompanion.adventure.twofm.TWOFMAdventure
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Properties
import kotlin.collections.set
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.primaryConstructor

abstract class TCAdventureBaseTest<T : Adventure<*, *, *>, U : AdventureFragment<*>>(
    private val adventureClass: KClass<T>,
    private val fragmentClass: KClass<U>,
    private val properties: Properties
): TCRoboElectricBaseTest() {

    companion object {
        var cachedAdventure: Adventure<*, *, *>? = null
    }

    lateinit var adventure: T
    lateinit var fragment: U

    protected fun loadSpecificvaluesToState(vararg pairs: Pair<KProperty1<*, *>, String>) {
        val newSavegame = Properties()
        newSavegame.putAll(properties.toMap())
        pairs.forEach { newSavegame[it.first.name] = it.second }
        adventure.loadSavegame(newSavegame)
        loadFragment()
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
                ShadowApplication.getInstance().applicationContext,
                TWOFMAdventure::class.java
            )
            intent.putExtra(LoadAdventureActivity.ADVENTURE_SAVEGAME_CONTENT, getPropertyAsString(properties))

            val activityController = Robolectric.buildActivity(adventureClass.java, intent)
            adventure = activityController.setup().get() as T
            cachedAdventure = adventure
        } else {
            adventure = cachedAdventure as T
        }

        adventure.loadSavegame(properties)

        loadFragment()

        fragment.refreshScreen()
    }

    fun loadFragment() {
        fragment = fragmentClass.primaryConstructor?.call() ?: throw IllegalArgumentException("No constructor for fragnment ${fragmentClass.simpleName}")
        adventure.supportFragmentManager.beginTransaction().add(fragment, null).commit()
    }

    fun <T : View> AdventureFragment<*>.findComponent(resId: Int) = this.view?.findViewById<T>(resId)
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
}
