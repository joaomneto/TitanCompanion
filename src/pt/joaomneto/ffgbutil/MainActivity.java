package pt.joaomneto.ffgbutil;

import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(
//				MainActivity.this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.rolld6:
	    	showAlert(DiceRoller.rollD6()+"");
	        return true;
	    case R.id.roll2d6:
	    	showAlert(DiceRoller.roll2D6()+"");
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
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

	public void showAlert(String message){
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
	


}
