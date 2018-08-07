package pt.joaomneto.titancompanion.adventure

import android.content.Intent
import android.view.View
import android.widget.ListView
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.shadows.ShadowApplication
import pt.joaomneto.titancompanion.LoadAdventureActivity
import pt.joaomneto.titancompanion.adventure.impl.TWOFMAdventure
import java.io.StringReader
import java.util.Properties
import kotlin.collections.HashMap
import kotlin.collections.set
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

abstract class TCAdventureBaseTest<T : Adventure<*>, U : AdventureFragment>(
        private val adventureClass: KClass<T>,
        private val fragmentClass: KClass<U>,
        private val savegame: String
) {

    companion object {
        var cachedAdventure: Adventure<*>? = null
        var cachedFragments: HashMap<KClass<out AdventureFragment>, AdventureFragment> = HashMap()
    }

    lateinit var adventure: T
    lateinit var fragment: U
    lateinit var properties: Properties

    @Before
    fun createActivity() {

        properties = Properties()
        properties.load(StringReader(savegame))

        if (cachedAdventure == null || !adventureClass.isInstance(cachedAdventure)) {
            val intent = Intent(
                    ShadowApplication.getInstance().applicationContext,
                    TWOFMAdventure::class.java
            )
            intent.putExtra(LoadAdventureActivity.ADVENTURE_SAVEGAME_CONTENT, savegame)

            val activityController = Robolectric.buildActivity(adventureClass.java, intent)
            adventure = activityController.setup().get() as T
            cachedAdventure = adventure
        } else {
            adventure = cachedAdventure as T
        }

        adventure.loadSavegame(properties)

        if (cachedFragments[fragmentClass] == null) {
            fragment = fragmentClass.primaryConstructor?.call() ?: throw IllegalArgumentException("No constructor for fragnment ${fragmentClass.simpleName}")
            cachedFragments[fragmentClass] = fragment
            adventure.supportFragmentManager.beginTransaction().add(fragment, null).commit()
        } else {
            fragment = cachedFragments[fragmentClass] as U
        }

        fragment.refreshScreen()
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
}
