package pt.joaomneto.titancompanion.adventure.impl.fragments.sl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_33sl_adventure_starshipstats.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.adventure.Adventure.getResId
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.SLAdventure

class SLStarshipStatsFragment : AdventureFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        return inflater!!.inflate(R.layout.fragment_33sl_adventure_starshipstats,
            container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = this.context as SLAdventure

        plusLasersButton.setOnClickListener({
            adv.currentLasers = minOf(4, adv.currentLasers + 1)
            refreshScreensFromResume()
        })
        minusLasersButton.setOnClickListener({
            adv.currentLasers = maxOf(0, adv.currentLasers - 1)
            refreshScreensFromResume()
        })
        plusShieldsButton.setOnClickListener({
            adv.currentShields = minOf(12, adv.currentShields + 1)
            refreshScreensFromResume()
        })
        minusShieldsButton.setOnClickListener({
            adv.currentShields = maxOf(0, adv.currentShields - 1)
            refreshScreensFromResume()
        })
        abandonStarsprayButton.setOnClickListener({
            Adventure.showConfirmation(R.string.abandonStarspray, R.string.confirmAbandonStarspray, adv, { _, _ ->
                adv.starspray = false
                refreshScreensFromResume()
            })
        })

    }

    override fun refreshScreensFromResume() {
        val adv = this.context as SLAdventure

        statsLasersValue.text = adv.currentLasers.toString()
        statsShieldsValue.text = adv.currentShields.toString()

        if(adv.starspray) {
            starshipLayout.setImageBitmap(generateStarshipLayout(adv))
        }else{
            starshipLayout.setImageResource(R.drawable.blaster)
            abandonStarsprayButton.visibility = INVISIBLE
        }


    }

    private fun generateStarshipLayout(adv: SLAdventure): Bitmap {
        return overlay(
            getResId(adv, "img_33sl_ship_s${12 - minOf(12, adv.currentShields)}", "drawable"),
            getResId(adv, "img_33sl_ship_l${4 - minOf(4, adv.currentLasers)}", "drawable")
        )
    }

    fun overlay(id1: Int, id2: Int): Bitmap {
        val bmp1 = BitmapFactory.decodeResource(resources, id1)
        val bmp2 = BitmapFactory.decodeResource(resources, id2)
        val bmOverlay = Bitmap.createBitmap(bmp1.width, bmp1.height, bmp1.config)
        val canvas = Canvas(bmOverlay)

        val m = Matrix()
        canvas.drawBitmap(bmp1, m, null)

        canvas.drawBitmap(bmp2, m, null)
        return bmOverlay
    }
}
