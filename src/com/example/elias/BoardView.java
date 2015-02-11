package com.example.elias;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.elias.Square.StatusEnum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

class Square
{
	public char Letter;
	public enum StatusEnum {
		unselected,
		selected
	};
	
	public StatusEnum status;
	
	public Square() 
	{
		status=StatusEnum.unselected;
		Letter=' ';
	}
}

class SelectedLetters
{
	ArrayList<Point> selectedLetters = new ArrayList<Point>();
	public ArrayList<Character>  word = new ArrayList<Character>();
	Square [][] board;
	
	public SelectedLetters(Square [][] myboard)
	{
		board = myboard;
	}

	public String getWord()
	{
		String ret = "";
		for( int i=0; i< word.size(); i++)
		{
			ret = ret + word.get(i);
		}
		return ret;
	}

	
	void toggle(Point pt)
	{
		boolean found = false;
		
		for( int i=0; i< selectedLetters.size(); i++)
		{
			Point let = selectedLetters.get(i);
			if (let.equals(pt))
			{
				for(int j=selectedLetters.size()-1; j>=i; j--)
				{
					Point rem = selectedLetters.get(j);
			    	board[rem.x][rem.y].status = StatusEnum.unselected;

					selectedLetters.remove(j);
					word.remove(j);
				}
				found = true;
				break;
			}
		}

		if (!found) {
			selectedLetters.add(new Point(pt));
			board[pt.x][pt.y].status = StatusEnum.selected;
			
			char l = board[pt.x][pt.y].Letter;
			word.add(new Character(l));
		}
	}
}

public class BoardView extends View {
	int  BOARD_X = 6;
	int  BOARD_Y = 10;
	Square [][] board = new Square[BOARD_X][BOARD_Y];
	Bitmap kangoo = null;
	Paint paint = null;
	
	int offset_X;
	int offset_Y;
	int box_W;
	int box_H;

	int widthPixels=-1;
	int heightPixels=-1;
	
	Point selected = new Point(-1,-1);

	SelectedLetters selectedLetters = null;
	
	BackgroundLabel topView;
	
	//private DrawingThread _thread;
	
	public BoardView(Context context,AttributeSet as){
		super(context,as);
		//init();
	}
	
	public BoardView(Context context){
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
	
	public void setTopView(BackgroundLabel top)
	{
		topView = top;
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
	

		
		offset_X = (int)((float)(widthPixels) / (BOARD_X * 2));
		offset_Y = (int)((float)(heightPixels) / (BOARD_Y * 2));
		
		box_W = (int)((float)(widthPixels) / BOARD_X );
		box_H = (int)((float)(heightPixels) / BOARD_Y );
		
       
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        
        if (background != null)
        	kangoo = Bitmap.createScaledBitmap(background, widthPixels, heightPixels, true);

		// prepare board
		Random rnd = new Random();
		for(int x=0; x<BOARD_X; x++)
			for (int y=0; y<BOARD_Y; y++)
			{
				board[x][y] = new Square();
				board[x][y].Letter = (char)( 'A' + rnd.nextInt('Z'-'A'));
			}

		selectedLetters = new SelectedLetters(board);
		
	}
	

    @Override 
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (kangoo==null) return;
		
		canvas.drawBitmap(kangoo, 0, 0, null);

		paint.setStyle(Paint.Style.STROKE);

		
        //canvas.drawColor(Color.BLACK);
        
		for(int x=0; x<BOARD_X; x++)
			for (int y=0; y<BOARD_Y; y++)
			{
				// draw some text using FILL style
				canvas.drawText( Character.toString(board[x][y].Letter), 
						offset_X + x * widthPixels / BOARD_X, 
						offset_Y + y * heightPixels / BOARD_Y, paint);

				if (board[x][y].status == StatusEnum.selected) {
					paint.setColor(Color.GREEN);
					
					canvas.drawRect(x*box_W, y*box_H, 
									(x+1)*box_W, (y+1)*box_H, paint);
					canvas.drawRect(x*box_W+1, y*box_H+1, 
							(x+1)*box_W-1, (y+1)*box_H-1, paint);
					canvas.drawRect(x*box_W+2, y*box_H+2, 
							(x+1)*box_W-2, (y+1)*box_H-2, paint);
					canvas.drawRect(x*box_W+3, y*box_H+3, 
							(x+1)*box_W-3, (y+1)*box_H-3, paint);
				}

				
				if (selected.equals(x,y))
				{
					paint.setColor(Color.RED);
				
					canvas.drawRect(x*box_W, y*box_H, 
							(x+1)*box_W, (y+1)*box_H, paint);
				}
				
				paint.setColor(Color.WHITE);
				
				
			}


	}
    
    private void touch_start(int x, int y) {
    	selected.set( x / box_W,
    				  y / box_H );
    }
    private void touch_move(int x, int y) {
    	selected.set( x / box_W,
				  		  y / box_H );
    }
    private void touch_up() {
    	selectedLetters.toggle(selected);
    	
    	String str = selectedLetters.getWord();
    	topView.setText( str );
    	topView.invalidate();
  	
    	selected.set( -1, -1 );
    }

    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }
    
}


