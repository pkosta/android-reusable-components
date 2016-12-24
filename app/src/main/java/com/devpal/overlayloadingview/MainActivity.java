package com.devpal.overlayloadingview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.sample_id);
        final OverlayRelativeView overlayRelativeView = (OverlayRelativeView) findViewById(R.id.overlay_rel_layout);

        overlayRelativeView.showOverlay();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                overlayRelativeView.hideOverlay();
            }
        }, 5000);


        Button btn1 = (Button) findViewById(R.id.btn_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"jsdhsjkdfhsk",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
