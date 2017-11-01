package com.vincan.circularrevealcompat.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author vincanyang
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startSecondActivityBtn = (Button) findViewById(R.id.startSecondActivityBtn);
        startSecondActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealSecondActivity(v);
            }
        });
    }

    private void revealSecondActivity(View view) {
        int revealCenterX = (int) (view.getX() + view.getWidth() / 2);
        int revealCenterY = (int) (view.getY() + view.getHeight() / 2);
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(SecondActivity.EXTRA_REVEAL_CENTER_X, revealCenterX);
        intent.putExtra(SecondActivity.EXTRA_REVEAL_CENTER_Y, revealCenterY);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
