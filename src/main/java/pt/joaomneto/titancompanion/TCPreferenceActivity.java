package pt.joaomneto.titancompanion;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.fragment.TCPreferenceFragment;
import pt.joaomneto.titancompanion.util.LocaleHelper;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * Created by Joao Neto on 27/05/17.
 */

public class TCPreferenceActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_preference, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return TCPreferenceFragment.class.getName().equals(fragmentName);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("lang")) {
            LocaleHelper.setLocale(this, sharedPreferences.getString("lang", null));
            Intent i = getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    public static boolean runSavegameImport(Context context) {
        try {
                File oldDir = new File(Environment.getExternalStorageDirectory()
                        .getPath(), "ffgbutil");
            if (oldDir.exists()) {
                FileUtils.copyDirectory(oldDir, new File(context.getFilesDir(), "ffgbutil"));
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error importing old savegames", e);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {

            if(runSavegameImport(this))
                Adventure.showSuccessAlert(R.string.savegameConfirmMessage, this);
            else
                Adventure.showAlert(R.string.noSavegamesToImport, this);
        } else {
            Adventure.showErrorAlert(R.string.savegameImportNotExecuted, this);
        }
    }


}
