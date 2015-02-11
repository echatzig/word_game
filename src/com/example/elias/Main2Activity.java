package com.example.elias;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class Main2Activity extends Activity {
	BoardView demoview;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		//demoview = new BoardView(this);
		//setContentView(demoview);
		
        setContentView(R.layout.activity_main2);
        
        BackgroundLabel lbBottom2 = (BackgroundLabel)findViewById(R.id.bottom_bkLabel2); 
        lbBottom2.setImage(R.drawable.southupper);

        BackgroundLabel lbTop = (BackgroundLabel)findViewById(R.id.top_bkLabel); 
        lbTop.setImage(R.drawable.north);
        
        BoardView board = (BoardView)findViewById(R.id.boardView1);
        board.setTopView(lbTop);
        
        
        //ImageView imgView = new ImageView(this);
        //InputStream is = getClass().getResourceAsStream("/drawable-mdpi/" + "center.png");
        //imgView.setImageDrawable(Drawable.createFromStream(is, ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main2, menu);
        return true;
    }
   
}
