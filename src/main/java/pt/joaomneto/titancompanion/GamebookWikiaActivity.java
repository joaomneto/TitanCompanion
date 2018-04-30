package pt.joaomneto.titancompanion;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class GamebookWikiaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamebook_wikia);

        WebView myWebView = findViewById(R.id.webview);

        Intent intent = getIntent();
        String url = intent.getStringExtra(GamebookListActivity.Companion.getGAMEBOOK_URL());

        myWebView.loadUrl(url);
    }

}
