package pt.joaomneto.ffgbutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GamebookSelectionActivity extends Activity {

	String url = "";
	int imageLink = 0;

	protected static final String GAMEBOOK_URL = "GAMEBOOK_URL";
	protected static final String GAMEBOOK_COVER = "GAMEBOOK_COVER";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamebook_selection);
		ImageView img = (ImageView) findViewById(R.id.gamebookCoverImg);

		Intent intent = getIntent();
		imageLink = intent.getIntExtra(GamebookListActivity.GAMEBOOK_COVER, 0);
		url = intent.getStringExtra(GamebookListActivity.GAMEBOOK_URL);
		img.setBackgroundResource(imageLink);
	}

	public void createNewAdventure(View view) {

	}

	public void viewSite(View view) {
		Intent intent = new Intent(this, GamebookWikiaActivity.class);
		intent.putExtra(GAMEBOOK_URL, url);
		startActivity(intent);
	}

	public void showFullImage(View view) {
		Intent intent = new Intent(this, GamebookFullImageActivity.class);
		intent.putExtra(GAMEBOOK_COVER, imageLink);
		startActivity(intent);
	}

}
