package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.tcoc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import pt.joaomneto.titancompanion.adventurecreation.impl.TCOCAdventureCreation

class SpellListAdapter(context: Context, objects: List<String>) : ArrayAdapter<String>(context, -1, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val adv = context as TCOCAdventureCreation

        val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)

        val text1 = view.rootView.findViewById<TextView>(android.R.id.text1)

        var text = adv.spellList[position]

        val number = adv.spells[text]
        if (number != null && number > 1) {
            text += " ($number)"
        }

        text1.text = text

        return view
    }
}