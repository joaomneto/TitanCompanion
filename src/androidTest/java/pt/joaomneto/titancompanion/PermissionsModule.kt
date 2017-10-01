package pt.joaomneto.titancompanion

import android.app.Activity
import android.content.Context

interface PermissionsModule {
    fun isLocationGranted(context: Context): Boolean
    fun shouldShowLocationPermissionRationale(activity: Activity): Boolean
    fun requestLocationPermission(activity: Activity, requestCode: Int)
}