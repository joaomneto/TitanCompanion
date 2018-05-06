package pt.joaomneto.titancompanion

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import java.util.ArrayList

class GamebookListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamebook_list)
        val listview = findViewById<ListView>(R.id.gamebookListView)

        val list = FightingFantasyGamebook.values().map { getString(it.nameResourceId) }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, android.R.id.text1, list
        )

        listview.adapter = adapter

        val intent = Intent(this, GamebookSelectionActivity::class.java)

        listview.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            intent.putExtra(GAMEBOOK_POSITION, position)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.gamebook_list, menu)
        return true
    }

    companion object {

        protected val GAMEBOOK_COVER = "GAMEBOOK_COVER"
        val GAMEBOOK_URL = "GAMEBOOK_URL"
        val GAMEBOOK_POSITION = "GAMEBOOK_POSITION"
    }
}
