package pt.joaomneto.titancompanion;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class MainActivity extends Activity {

	public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(
//				MainActivity.this));
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {



				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
						MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.

		}
	}

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    showAlert(R.string.storagePermissionsRequired);
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    super.onDestroy();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
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


}
