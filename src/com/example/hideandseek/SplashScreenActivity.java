package com.example.hideandseek;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.morehideandseek.R;

public class SplashScreenActivity extends ActionBarActivity {

	private static SwipeGestureDetector swipeGestureDetector;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
        	
        	ActionBar actionBar = getSupportActionBar();
        	actionBar.hide();
        }

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		swipeGestureDetector=new SwipeGestureDetector();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}
	private void onLeftSwipe() {
	    
	    Intent intent = new Intent(this,MainActivity.class);
		startActivityForResult(intent, 0);
		overridePendingTransition(R.anim.rightoleft,R.anim.leftoright);
	   
	}

	private void onRightSwipe() {
	  
	  
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ImageView swipeButton;
		GestureDetector gestureDetector;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_splash_screen,
					container, false);
			swipeButton= (ImageView) rootView.findViewById(R.id.swipeLeft);
			gestureDetector = new GestureDetector(getActivity(),swipeGestureDetector);
			 View.OnTouchListener gestureListener = new View.OnTouchListener() {
			        public boolean onTouch(View v, MotionEvent event) {
			            gestureDetector.onTouchEvent(event);
			            return true;
			        }
			    };
			swipeButton.setOnTouchListener(gestureListener);    
			return rootView;
		}
	}
	private class SwipeGestureDetector extends SimpleOnGestureListener {
	    private static final int SWIPE_MIN_DISTANCE = 50;
	    private static final int SWIPE_MAX_OFF_PATH = 200;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	            float velocityY) {
	        try {
	            float diffAbs = Math.abs(e1.getY() - e2.getY());
	            float diff = e1.getX() - e2.getX();

	            if (diffAbs > SWIPE_MAX_OFF_PATH)
	                return false;

	            // Left swipe
	            if (diff > SWIPE_MIN_DISTANCE
	                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                SplashScreenActivity.this.onLeftSwipe();
	            } 
	            // Right swipe
	            else if (-diff > SWIPE_MIN_DISTANCE
	                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                SplashScreenActivity.this.onRightSwipe();
	            }
	        } catch (Exception e) {
	            Log.e("Home", "Error on gestures");
	        }
	        return false;
	    }

	}

}
