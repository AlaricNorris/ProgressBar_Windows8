package com.alaric.norris.widget.progressbar ;

import android.app.Activity ;
import android.os.Bundle ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.widget.Button ;
import com.alaric.norris.widget.progressbar_win8.R ;

public class MainActivity extends Activity {

	@ Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.activity_main) ;
		final Win8ProgressBar p1 = (Win8ProgressBar) findViewById(R.id.p1) ;
		final Win8ProgressBar p2 = (Win8ProgressBar) findViewById(R.id.p2) ;
		final Win8ProgressBar p3 = (Win8ProgressBar) findViewById(R.id.p3) ;
		final Button button = (Button) findViewById(R.id.button) ;
		button.setOnClickListener(new OnClickListener() {

			@ Override
			public void onClick(View v) {
				if(button.getText().equals("Begin")) {
					button.setClickable(false) ;
					p1.startAnimation() ;
					p2.startAnimation() ;
					p3.startAnimation() ;
					button.setText("Stop") ;
					button.setClickable(true) ;
				}
				else {
					button.setClickable(false) ;
					p1.stopAnimation() ;
					p2.stopAnimation() ;
					p3.stopAnimation() ;
					button.setText("Begin") ;
					button.setClickable(true) ;
				}
			}
		}) ;
	}
}
