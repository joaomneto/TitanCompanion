package pt.joaomneto.ffgbutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GamebookSelectionActivity extends Activity {

	private String url = "";
	private int imageLink = 0;
	private int position = 0;

	protected static final String GAMEBOOK_URL = "GAMEBOOK_URL";
	protected static final String GAMEBOOK_COVER = "GAMEBOOK_COVER";

	private String[] urls;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamebook_selection);

		urls = getResources().getStringArray(R.array.gamebook_list_urls);

		ImageView img = (ImageView) findViewById(R.id.gamebookCoverImg);

		Intent intent = getIntent();
		imageLink = intent.getIntExtra(GamebookListActivity.GAMEBOOK_COVER, 0);
		url = intent.getStringExtra(GamebookListActivity.GAMEBOOK_URL);
		position = intent.getIntExtra(GamebookListActivity.GAMEBOOK_POSITION, 0);
		img.setImageResource(imageLink);
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



//	@Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//
//		Intent intent = new Intent(this, GamebookSelectionActivity.class);
//
//		if (e1.getRawY() < e2.getRawY()) {
//			if (position > 0) {
//				intent.putExtra(GAMEBOOK_COVER, GamebookCoverConstants.getGameBookCoverAddress(position));
//				intent.putExtra(GAMEBOOK_URL, urls[position]);
//				startActivity(intent);
//			}
//		} else {
//			if (position < 59) {
//				intent.putExtra(GAMEBOOK_COVER, GamebookCoverConstants.getGameBookCoverAddress(position + 2));
//				intent.putExtra(GAMEBOOK_URL, urls[position]);
//				startActivity(intent);
//			}
//		}
//		return true;
//	}

}
