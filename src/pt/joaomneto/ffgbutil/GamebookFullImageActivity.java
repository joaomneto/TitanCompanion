package pt.joaomneto.ffgbutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class GamebookFullImageActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gamebook_full_image);

	
		ImageView img = (ImageView) findViewById(R.id.fullGamebookCoverImg);

		img.setScaleType(ScaleType.FIT_XY);
		Intent intent = getIntent();
		int imageLink = intent.getIntExtra(GamebookSelectionActivity.GAMEBOOK_COVER, 0);
		img.setBackgroundResource(imageLink);

	
	}


}
