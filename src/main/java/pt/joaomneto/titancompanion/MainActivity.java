package pt.joaomneto.titancompanion;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import pt.joaomneto.titancompanion.adventure.Adventure;
import pt.joaomneto.titancompanion.fragment.TCPreferenceFragment;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {

            TCPreferenceActivity.Companion.runSavegameImport(this);
        }
    }

    public void createNewAdventure(View view) {
        Intent intent = new Intent(this, GamebookListActivity.class);
        startActivity(intent);
    }

    public void loadAdventure(View view) {
        Intent intent = new Intent(this, LoadAdventureActivity.class);
        startActivity(intent);
    }

    public void showAlert(int messageId) {
        AlertDialog.Builder builder = getBuilder();
        builder.setMessage(messageId);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @NonNull
    private AlertDialog.Builder getBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.result)
                .setCancelable(false)
                .setNegativeButton(R.string.close,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        return builder;
    }

    public void quit(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void settings(View view) {
        Intent intent = new Intent(this, TCPreferenceActivity.class);
        intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, TCPreferenceFragment.class.getName());
        intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
        startActivity(intent);
    }

    public void rate(View view) {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName().replace(".debug", ""));
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }

    }

    public void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result")
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            Integer previouslyStarted = prefs.getInt(getString(R.string.currentVersion), 0);
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            Integer version = pInfo.versionCode;
            if (previouslyStarted != version) {
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt(getString(R.string.currentVersion), version);
                edit.commit();
                showHelp();
            }
        } catch (Throwable e) {
            Log.e(MainActivity.class.getSimpleName(), "Error trying to determine if it's the first launch after install/update");
            e.printStackTrace();
        }
    }

    private void showHelp() {
        Adventure.Companion.showInfoAlert(R.string.savegameImportInfo, this);
    }
}
