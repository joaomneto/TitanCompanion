package pt.joaomneto.titancompanion.adventure.impl.fragments.sota

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_32sota_adventure_time.*
import pt.joaomneto.titancompanion.R
import pt.joaomneto.titancompanion.adventure.Adventure.Companion.getResId
import pt.joaomneto.titancompanion.adventure.AdventureFragment
import pt.joaomneto.titancompanion.adventure.impl.SOTAAdventure

class SOTAAdventureTimeFragment : AdventureFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(
            R.layout.fragment_32sota_adventure_time,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adv = this.context as SOTAAdventure

        plusTimeButton.setOnClickListener({
            adv.time = minOf(20, adv.time + 1)
            refreshScreensFromResume()
        })
        minusTimeButton.setOnClickListener({
            adv.time = maxOf(0, adv.time - 1)
            refreshScreensFromResume()
        })
    }

    override fun refreshScreensFromResume() {
        val adv = this.context as SOTAAdventure

        statsTimeValue.text = adv.time.toString()

        if(adv.time>0)
            hourglassLayout.setImageBitmap(generateHourglassLayout(adv))
        else
            hourglassLayout.setImageResource(R.drawable.img_32sota_hourglass)
    }

    private fun generateHourglassLayout(adv: SOTAAdventure): Bitmap {
        return overlay(
            getResId(adv, "img_32sota_hourglass", "drawable"),
            getResId(adv, "img_32sota_hourglass_${(this.activity as SOTAAdventure).time}", "drawable")
        )
    }

    private fun overlay(id1: Int, id2: Int): Bitmap {
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
