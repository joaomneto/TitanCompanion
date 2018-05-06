package pt.joaomneto.titancompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class GamebookFullImageActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gamebook_full_image);


        ImageView img = findViewById(R.id.fullGamebookCoverImg);

        img.setScaleType(ScaleType.FIT_XY);
        Intent intent = getIntent();
        int imageLink = intent.getIntExtra(GamebookSelectionActivity.Companion.getGAMEBOOK_COVER(), 0);
        img.setImageResource(imageLink);


    }


}
