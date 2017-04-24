package pt.joaomneto.titancompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class GamebookWikiaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamebook_wikia);
		
		WebView myWebView = (WebView) findViewById(R.id.webview);
		
		Intent intent = getIntent();
		String url = intent.getStringExtra(GamebookListActivity.GAMEBOOK_URL);
		
		myWebView.loadUrl(url);
	}

}
