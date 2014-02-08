package ua.net.nt.servertroll;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyTextView extends TextView {

	public MyTextView(Context context) {
		super(context);
		setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setMovementMethod(ScrollingMovementMethod.getInstance());
	}

/*	  @Override
	  public boolean dispatchTouchEvent(MotionEvent event)
	  {
	    boolean ret;
	    ret = super.dispatchTouchEvent(event);
	    if(ret)
	    {
	        getParent().requestDisallowInterceptTouchEvent(true);
	    }
	    return ret;
	  }
*/
}
