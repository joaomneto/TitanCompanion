package pt.joaomneto.titancompanion.adventure.impl.fragments.ss

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_08st_adventure_map.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.SSAdventure
import java.util.ArrayList

class SSAdventureMapFragment : AdventureFragment() {

    private var elements: MutableList<String> = ArrayList()

    init {
        val clazz = R.id::class.java

        for (field in clazz.fields) {
            if (field.name.startsWith("clearing")) {
                elements.add(field.name)
            }
            if (field.name.startsWith("up") || field.name.startsWith("down")
                || field.name.startsWith("left") || field.name.startsWith("right")) {
                elements.add(field.name)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        val rootView = inflater!!.inflate(R.layout.fragment_08st_adventure_map, container, false)

        val adv = this.context as SSAdventure

        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                setClearingLayoutSizes(adv)
            }
        })

        refreshScreensFromResume()

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = this.context as SSAdventure

        addClearingButton.setOnClickListener {
            val alert = AlertDialog.Builder(activity)

            alert.setTitle(R.string.currentClearing)

            // Set an EditText view to get user input
            val input = EditText(activity)
            val imm = activity.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            input.inputType = InputType.TYPE_CLASS_PHONE
            input.requestFocus()
            alert.setView(input)

            alert.setPositiveButton(R.string.ok) { _, _ ->
                imm.hideSoftInputFromWindow(input.windowToken, 0)
                val number = input.text.toString()
                adv.addVisitedClearings(number)
                refreshScreensFromResume()
            }

            alert.show()
        }
    }

    override fun refreshScreensFromResume() {

        val adv = activity as SSAdventure

        for (number in adv.visitedClearings) {
            try {
                revealField(number)

            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }


        if (clearing_17.visibility == VISIBLE && clearing_24.visibility == VISIBLE) {
            clearing_18_24.visibility = VISIBLE
        }


        if (clearing_29.visibility == VISIBLE && clearing_33.visibility == VISIBLE) {
            clearing_29_33.visibility = VISIBLE
        }


        if (clearing_10.visibility == VISIBLE && clearing_28.visibility == VISIBLE) {
            clearing_28_10.visibility = VISIBLE
            clearing_10_w.visibility = VISIBLE
        }

        var visibles = 0

        visibles += if (clearing_7.visibility == VISIBLE) 1 else 0
        visibles += if (clearing_15.visibility == VISIBLE) 1 else 0
        visibles += if (clearing_19.visibility == VISIBLE) 1 else 0
        visibles += if (clearing_32.visibility == VISIBLE) 1 else 0

        if (visibles > 1) {
            clearing_7_15_19_32.visibility = VISIBLE
        }
    }

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    private fun revealField(field: String) {

        val clearing = SSClearing.getIfExists("clearing_" + field)
        if (clearing != null) {
            val currcell: View? = view?.findViewById(clearing.resource)
            currcell?.visibility = VISIBLE
        }
    }

    fun setClearingLayoutSizes(context: Context?) {

        val displayMetrics = context!!.resources.displayMetrics
        var dpHeight = displayMetrics.heightPixels / displayMetrics.density
        var dpWidth = displayMetrics.widthPixels / displayMetrics.density

        dpHeight -= addClearingButton.height.toFloat()

        dpHeight /= 9f
        dpWidth /= 7f

        val finalValue = Math.min(dpHeight, dpWidth).toInt()

        val childcount = clearingGrid.childCount
        for (i in 0 until childcount) {
            val row = clearingGrid.getChildAt(i) as TableRow

            val cellCount = row.childCount
            for (j in 0 until cellCount) {
                val cell = row.getChildAt(j) as TextView
                cell.height = finalValue
                cell.width = finalValue
            }
        }
    }
}
