package pt.joaomneto.titancompanion.adventure.impl.fragments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by joao.neto on 4/6/18.
 */
open class ArrayGeneratorAdapter<out T>(
    val context: Context,
    val resource: Int,
    private val textViewResourceId: Int,
    private val arrayGenerator: () -> List<T>
) : BaseAdapter() {


    val values: List<T>
        get() = arrayGenerator()

    override fun getCount() = arrayGenerator().size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItem(position: Int)= arrayGenerator()[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(resource, parent, false);

        view.findViewById<TextView>(textViewResourceId).text = getItem(position).toString()

        return view
    }
}
