package com.example.maproot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class Top extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top);


	}


	    @Override
	    protected void onResume() {
	        super.onResume();

	        ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
	        imageButton1.setOnClickListener(this);

	        ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
	        imageButton2.setOnClickListener(this);

	    }


	    public void onClick(View v) {
	        switch(v.getId()){

	            case R.id.imageButton1:
	            	Intent M = new Intent(Top.this, Map.class);
	            	 startActivity(M);
	            break;

	            case R.id.imageButton2:
	            	Intent T = new Intent(Top.this, Time.class);
	            	 startActivity(T);
	            break;
	        }
	    }
}

