package com.example.elias;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btn = (Button)this.findViewById(R.id.button1);
        
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Toast.makeText(MainActivity.this, "You clicked the button", Toast.LENGTH_SHORT).show();
            	// setContentView(R.layout.activity_main2);
            	

                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                MainActivity.this.startActivity(i);   
               
            }

          });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
