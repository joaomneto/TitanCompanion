package pt.joaomneto.titancompanion.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import pt.joaomneto.titancompanion.LoadAdventureActivity
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import java.text.SimpleDateFormat

class SavegameListAdapter(private val ctx: Context, private val values: List<Savegame>) : ArrayAdapter<Savegame>(
    ctx,
    -1,
    values
), View.OnCreateContextMenuListener {
    private val adv: LoadAdventureActivity = ctx as LoadAdventureActivity

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val robotView = inflater.inflate(R.layout.component_load_dventure, parent, false)

        val nameValue = robotView.rootView.findViewById<TextView>(R.id.nameValue)
        val gamebookValue = robotView.rootView.findViewById<TextView>(R.id.gamebookValue)
        val dateValue = robotView.rootView.findViewById<TextView>(R.id.dateValue)
        val gamebookIcon = robotView.rootView.findViewById<ImageView>(R.id.gamebookIcon)

        val value = values[position].getFilename()

        val tokens = value.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        nameValue.text = tokens[2]

        val gamebookNameId = adv.resources.getIdentifier(
            tokens[1].toLowerCase(),
            "string",
            adv.applicationContext.packageName
        )
        val gamebookCoverId = adv.resources.getIdentifier(
            "ff" + FightingFantasyGamebook.gamebookFromInitials(tokens[1].toLowerCase()),
            "drawable",
            adv.applicationContext.packageName
        )

        gamebookValue.setText(gamebookNameId)
        dateValue.text = df.format(values[position].getLastUpdated())
        gamebookIcon.setImageResource(gamebookCoverId)

        return robotView
    }

    override fun onCreateContextMenu(
        contextMenu: ContextMenu,
        view: View,
        contextMenuInfo: ContextMenu.ContextMenuInfo
    ) {
        println()
    }

    companion object {

        private val df = SimpleDateFormat("yyyy-MM-dd HH:mm")
    }
}
