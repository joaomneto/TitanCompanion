package pt.joaomneto.ffgbutil;

import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
	    	int d1 = DiceRoller.rollD6();
	    	int d2 = DiceRoller.rollD6();
	    	View toastView = getLayoutInflater().inflate(R.layout.d2toast, (ViewGroup)findViewById(R.id.d2ToastLayout));
	    	ImageView imageViewD1 = (ImageView)toastView.findViewById(R.id.d1);
	    	ImageView imageViewD2 = (ImageView)toastView.findViewById(R.id.d2);
	    	
	    	int d1Id = getResources().getIdentifier(
	    		    "d6"+d1,
	    		    "drawable",
	    		    this.getPackageName());
	    	
	     	int d2Id = getResources().getIdentifier(
	    		    "d6"+d2,
	    		    "drawable",
	    		    this.getPackageName());
	     	
	     	imageViewD1.setImageResource(d1Id);
	     	imageViewD2.setImageResource(d2Id);

	     	Toast toast = new Toast(this);

	     	toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	     	toast.setDuration(Toast.LENGTH_LONG);
	     	toast.setView(toastView);

	     	toast.show();
	     	
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
