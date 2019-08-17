package pt.joaomneto.titancompanion

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Environment
import android.preference.PreferenceActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.fragment.TCPreferenceFragment
import pt.joaomneto.titancompanion.util.LocaleHelper
import java.io.File
import java.io.IOException

/**
 * Created by Joao Neto on 27/05/17.
 */

class TCPreferenceActivity : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.headers_preference, target)
    }

    override fun isValidFragment(fragmentName: String): Boolean {
        return TCPreferenceFragment::class.java.name == fragmentName
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "lang") {
            LocaleHelper.setLocale(this, sharedPreferences.getString("lang", null))
            val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
            i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PERMISSION_GRANTED) {

            if (runSavegameImport(this))
                Adventure.showSuccessAlert(R.string.savegameConfirmMessage, this)
            else
                Adventure.showAlert(R.string.noSavegamesToImport, this)
        } else {
            Adventure.showErrorAlert(R.string.savegameImportNotExecuted, this)
        }
    }

    companion object {

        fun runSavegameImport(context: Context): Boolean {
            try {
                val oldDir = File(
                    Environment.getExternalStorageDirectory()
                        .path, "ffgbutil"
                )
                return if (oldDir.exists()) {
                    oldDir.copyRecursively(File(context.filesDir, "ffgbutil"), overwrite = true)
                    true
                } else {
                    false
                }
            } catch (e: IOException) {
                throw IllegalStateException("Error importing old savegames", e)
            }
        }
    }
}
