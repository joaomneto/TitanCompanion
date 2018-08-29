package pt.joaomneto.titancompanion

import android.app.Application
import android.content.Context
import pt.joaomneto.titancompanion.util.LocaleHelper

class MainApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"))
    }


}
