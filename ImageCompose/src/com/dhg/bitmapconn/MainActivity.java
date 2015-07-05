package com.dhg.bitmapconn;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
	private Handler handler=new Handler();
	private ImageView splash;
	private int[] imgs={R.drawable.bgbg,R.drawable.bg2,R.drawable.bg3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        splash=(ImageView) findViewById(R.id.splash);
        int a=new Random().nextInt(3);
        splash.setImageResource(imgs[a]);
        Runnable r=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(MainActivity.this,HomeActivity.class);
				startActivity(i);
				MainActivity.this.finish();
				
			}
		};
		handler.postDelayed(r, 1000);
        
        
    }
    


    
}
