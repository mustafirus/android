package ua.net.nt.servertroll;

import android.os.Bundle;
import android.app.Activity;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	sshexec se;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView textview = (TextView) findViewById(R.id.textView);
		textview.setText("Fuck");
		textview.append("yuo");
		textview.setMovementMethod(new ScrollingMovementMethod());
		textview.setHorizontallyScrolling(true);
		textview.setTextIsSelectable(true);
		
		textview.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	int l = getCurrentCursorLine((TextView)v);
	        	Log.i("OnClick line:", String.valueOf(l));
	        	CharSequence str = getCurrentCursorLineString((TextView)v);
	        	Log.i("OnClick line:", str.toString());
	        }
	    });
		
		(se = new sshexec(textview)).execute();
	}

	protected void onStop() {
		se.cancel(true);
		super.onStop();
	}
	
	public CharSequence getCurrentCursorLineString(TextView textview){
/*		CharSequence txt = textview.getText();
		Spannable sp = (Spannable)txt;
		Selection.extendToLeftEdge  (sp, textview.getLayout());
		Selection.extendToRightEdge (sp, textview.getLayout());
		CharSequence ret = sp.subSequence(Selection.getSelectionStart(sp), Selection.getSelectionEnd(sp));
		return ret;*/
		CharSequence txt = textview.getText();
		Layout lo = textview.getLayout();
		int li = getCurrentCursorLine(textview);
		CharSequence ret = txt.subSequence(lo.getLineStart(li), lo.getLineEnd(li));
		return ret;
	}
	public int getCurrentCursorLine(TextView editText)
	{    
	    int selectionStart = Selection.getSelectionStart(editText.getText());
	    Layout layout = editText.getLayout();

	    if (!(selectionStart == -1)) {
	        return layout.getLineForOffset(selectionStart);
	    }

	    return -1;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
