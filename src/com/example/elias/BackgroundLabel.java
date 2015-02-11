package com.example.elias;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BackgroundLabel extends View {

	private String text = "";
	private Color color;
	private int offset; // Used for offsetting text from the picture

	Paint paint = null;
	Bitmap image = null;
	int image_id;

	int widthPixels=-1;
	int heightPixels=-1;
	
	public void setText(String txt)
	{
		text = txt;
	}
	
	public BackgroundLabel(Context context,AttributeSet as){
		super(context,as);
		//init();
	}
	
	public BackgroundLabel(Context context){
		super(context);
		//init();
	}

	@Override
	protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
	     super.onSizeChanged(xNew, yNew, xOld, yOld);

	     widthPixels = xNew;
	     heightPixels = yNew;
	     
	     init();
	}	
	
	private void init()
	{
		// setup pain object
		paint = new Paint();
		
		paint.setStyle(Paint.Style.FILL);
		//turn anti-aliasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.setColor(Color.WHITE);

		Bitmap background = BitmapFactory.decodeResource(getResources(), image_id);

        if (background != null)
        	image = Bitmap.createScaledBitmap(background, widthPixels, heightPixels, true);
	}

	public void setImage(int id)
	{
		image_id = id;
        init();
	}

	
    @Override 
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (image==null) return;
		
		canvas.drawBitmap(image, 0, 0, null);

		//paint.setColor(color);
		/*
		switch ( offset ){
			case 0:
				canvas.drawText(text, 42, 25, paint);
				break;
			case 1:
				canvas.drawText(text, 0, 15, paint);
				break;
			case 2:
			*/ 
				canvas.drawText(text, image.getWidth()/2-2, image.getHeight()-8, paint);
				/*
				break;
			case 3:
				canvas.drawText(text, image.getWidth()/2+6, image.getHeight()-8, paint);
				break;
			case 4:
				canvas.drawText(text, image.getWidth()/2-10, image.getHeight()-8, paint);
				break;
		}
		*/
	}
    

}
